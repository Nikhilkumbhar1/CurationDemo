/*
package com.accion.service;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

@Service
public class FetchCommitChangesService {
    private String githubToken;

    public void fetchCommitDetails(String githubApiUrl) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + githubToken);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(githubApiUrl);

        ResponseEntity<String> response = restTemplate.getForEntity(builder.toUriString(), String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            System.err.println("Unexpected response status: " + response.getStatusCode());
            return;
        }

        String responseBody = response.getBody();
        System.out.println("Response Body: " + responseBody);  // Print the response body for debugging

        // Check if the response body is a valid JSON object
        Object json = new JSONTokener(responseBody).nextValue();
        if (json instanceof JSONObject) {
            JSONObject commitDetails = (JSONObject) json;

            // Check for errors in the JSON response
            if (commitDetails.has("message") && commitDetails.getString("message").equals("Not Found")) {
                System.out.println("Error: Commit not found.");
            } else if (commitDetails.has("files")) {
                JSONArray files = commitDetails.getJSONArray("files");

                for (int i = 0; i < files.length(); i++) {
                    JSONObject file = files.getJSONObject(i);
                    String filename = file.getString("filename");
                    String status = file.getString("status");
                    String patch = file.has("patch") ? file.getString("patch") : "No changes";

                    System.out.println("File: " + filename);
                    System.out.println("Status: " + status);
                    System.out.println("Changes:\n" + patch);
                    System.out.println("---------------------------------------------------");
                }
            } else {
                System.out.println("The JSON response does not contain the expected 'files' field.");
            }
        } else {
            System.out.println("The response is not a valid JSON object.");
        }
    }



    public String generateCommitApiUrl(String commitUrl) {
        // Regular expression to match the GitHub commit URL
        String regex = "https://github\\.com/(.*?)/(.*?)/commit/(.*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(commitUrl);

        if (matcher.find()) {
            String owner = matcher.group(1);
            String repo = matcher.group(2);
            String sha = matcher.group(3);

            String baseUrl = "https://api.github.com/repos/";
            return baseUrl + owner + "/" + repo + "/commits/" + sha;
        } else {
            throw new IllegalArgumentException("Invalid GitHub commit URL");
        }
    }

}
*/
