package word.counter.web.resources;

import word.counter.web.resources.security.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by prageeth_2 on 2/1/2016.
 */
@Path("authtocken")
public class AuthTocken {

    @GET
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/auth")
    public String authenticateUser(@HeaderParam("username") String username, @HeaderParam("password") String password) {
        try {
            System.out.println("username = " + username);
            System.out.println("password = " + password);
            // Authenticate the user using the credentials provided
            if (authenticate(username, password)) {
                // Issue a token for the user
                String token = issueToken(username);
                System.out.println("token = " + token);
                // Return the token on the response
                return Response.ok(token).build().toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build().toString();
    }

    private boolean authenticate(String username, String password) throws Exception {
        // Authenticate against a database, LDAP, file or whatever
        // Throw an Exception if the credentials are invalid

        return username != null && password != null && username.equals("TestClient") && password.equals("admin");
    }

    private String issueToken(String username) {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token
        return "abc";
    }
}