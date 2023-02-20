package com.mycompany;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestTest {
    public static void main(String[] args) throws IOException {
        // Step a: Call login endpoint to get the token
        String loginUrl = "https://reqres.in/api/login";
        String email = "eve.holt@reqres.in";
        String password = "cityslicka";
        String token = "";

        System.setProperty("http.agent", "Chrome");

        URL url = new URL(loginUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        String requestBody = "{\"email\": \"" + email + "\", \"password\": \"" + password + "\"}";
        con.setDoOutput(true);
        con.getOutputStream().write(requestBody.getBytes());
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String responseJson = response.toString();

        // Parse the response to get the token
        token = responseJson.substring(responseJson.indexOf(":\"") + 2, responseJson.indexOf("\"}"));

        // Step b: Call the post endpoint
        String postUrl = "https://reqres.in/api/users";
        String name = "morpheus";
        String job = "leader";

        url = new URL(postUrl);
        con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer " + token);
        requestBody = "{\"name\": \"" + name + "\", \"job\": \"" + job + "\"}";
        con.setDoOutput(true);
        con.getOutputStream().write(requestBody.getBytes());
        in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        responseJson = response.toString();

        // Print the response
        System.out.println(responseJson);
    }
}
