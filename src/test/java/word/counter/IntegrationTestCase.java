package word.counter;

import com.sun.xml.internal.messaging.saaj.util.Base64;
import org.glassfish.jersey.SslConfigurator;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.HttpUrlConnectorProvider;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import word.counter.web.resources.pojo.CounterResults;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.DatatypeConverter;
import java.io.*;

/**
 * Created by prageeth.g on 29/1/2016.
 */
public class IntegrationTestCase {

    private static String TEXT_FILE_PATH = "test.txt";
    private static WebTarget target;

    public static void main(String[] args) {
        int chuckedSize = 10240;
        // process the programe parameters
        if (args != null) {
            // identify the sentence file
            if (args.length >= 1 && args[0] != null && args[0].trim().length() > 0) {
                TEXT_FILE_PATH = args[0];
            } else if (args.length >= 2 && args[1] != null && args[1].trim().length() > 0) {
                // identify the file stream chunck size
                try {
                    chuckedSize = Integer.parseInt(args[1].trim());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder()
                .nonPreemptive()
                .credentials("user", "password")
                .build();

        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(feature) ;
        JerseyClient client = (JerseyClient) ClientBuilder.newClient(clientConfig);
        // set the stream connection chucked size to 10KB
        client.getConfiguration().property(ClientProperties.CHUNKED_ENCODING_SIZE, chuckedSize);
        target = client.target(RestServiceApplication.BASE_URI);
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(new File(TEXT_FILE_PATH));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        CounterResults response = null;
        response = target.path("count/").request(MediaType.APPLICATION_JSON).
                header("auth", DatatypeConverter.printBase64Binary("TestClient:admin123".getBytes())).
                header("encriptedWords","false").
                post(Entity.entity(fileInputStream, MediaType.APPLICATION_OCTET_STREAM), CounterResults.class);

        System.out.println(response);
    }
}
