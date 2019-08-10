import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Request {

    private Logger logger = LoggerFactory.getLogger(Request.class);

    public String POSTRequest(String url) throws IOException {

        // todo Can POST_PARAMS be left empty?
        final String POST_PARAMS = "";

        URL urlForPostRequest = new URL(url);
        HttpURLConnection postConnection = (HttpURLConnection) urlForPostRequest.openConnection();
        postConnection.setRequestMethod("POST");

        postConnection.setDoOutput(true);
        OutputStream os = postConnection.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();

        int responseCode = postConnection.getResponseCode();

        StringBuilder response = new StringBuilder();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            logger.info(response.toString());
        } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
            InputStream errorStream = postConnection.getErrorStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(errorStream, StandardCharsets.UTF_8));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            logger.info(response.toString());
        }

        return response.toString();
    }

    public String GETRequest(String url) throws IOException {
        URL urlForGetRequest = new URL(url);
        HttpURLConnection getConnection = (HttpURLConnection) urlForGetRequest.openConnection();
        getConnection.setRequestMethod("GET");
        int responseCode = getConnection.getResponseCode();
        StringBuilder response = new StringBuilder();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(getConnection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            logger.info("GET JSON String Result " + responseCode + response.toString());
        } else {
            logger.info("GET DID NOT WORK");
        }

        return response.toString();
    }
}
