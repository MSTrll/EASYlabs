package com.example.xiaomi.test.API_for_server.requests;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class Request {

    // default request config
    private static String SERVER_URL;
    private static String USER_AGENT;
    private String REQUEST_METHOD = "GET";
    private URL URL;
    private HttpURLConnection connection = null;
    private String rqstValues = "";
    private int responseCode;
    private BufferedReader BR = null;

    public Request(String REQUEST_METHOD) throws MalformedURLException {
        if (REQUEST_METHOD != null)
            this.REQUEST_METHOD = REQUEST_METHOD;
    }

    public void Connection() throws IOException {

//        Properties request_config = new Properties();
//        request_config.load(new FileInputStream(
//                "com/example/xiaomi/test/API_for_server/properties/request_config.properties"
//        ));
        SERVER_URL = "http://169.254.83.212/";
        USER_AGENT = "Mozilla/5.0";
        URL = new URL(SERVER_URL + rqstValues);
        connection = (HttpURLConnection) URL.openConnection();
        connection.setRequestMethod(REQUEST_METHOD);
        connection.setRequestProperty("User-Agent", USER_AGENT);
        this.responseCode = connection.getResponseCode();
        rqstCondition();

    }

    // Connection condition to the LOG
    public void rqstCondition() throws IOException {
        Log.d("RC", "\nSending 'GET' request to URL : " + URL);
        Log.d("RC", "Response Code : " + getResponseCode());
    }

    // Set request body
    public void setRqstValues(String rqstValues) {
        this.rqstValues = rqstValues;
    }

    // Check connection
    public int getResponseCode() throws IOException {
        if (connection != null)
            return connection.getResponseCode();
        return responseCode;
    }

    // Set reader for input stream from server
    private void setBR() throws IOException {
        BR = new BufferedReader(new InputStreamReader
                (connection.getInputStream()));
    }

    // Just get some response from server
    public StringBuffer getResponse() throws IOException {

        String inputLine;
        StringBuffer response = new StringBuffer();

        setBR();

        while ((inputLine = BR.readLine()) != null) {
            response.append(inputLine);
        }

        BR.close();

        return response;
    }
}
