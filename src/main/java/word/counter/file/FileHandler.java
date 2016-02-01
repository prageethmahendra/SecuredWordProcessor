package word.counter.file;

import java.io.*;
import java.security.Timestamp;

public class FileHandler {
    private static final String LOG_ARCHIVE_PATH = "log\\archive\\";

    /**
     * This method generates a new file name and a bufferdwriter
     * @return
     */
    public static BufferedWriter getNewFileWriter(String clientId)
    {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append(LOG_ARCHIVE_PATH).append(clientId).append("_").append(System.nanoTime()).append(".txt");
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sb.toString(), false)));
            return bufferedWriter;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * This method closes the buffered writer
     * @param bufferedWriter
     */
    public static void close(BufferedWriter bufferedWriter) {
        if(bufferedWriter != null)
        {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method generates a BufferedReader for a given input stream
     * @param attachmentInputStream
     * @return
     */
    public static BufferedReader getNewStreamReader(InputStream attachmentInputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(attachmentInputStream));
        return bufferedReader;
    }

    /**
     * Thid method closes the buffered reader
     * @param bufferedReader
     */
    public static void close(BufferedReader bufferedReader) {
        if(bufferedReader != null)
        {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
