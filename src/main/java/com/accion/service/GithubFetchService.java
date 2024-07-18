package com.accion.service;

//import org.example.reduce.model.CommitChange;
import com.accion.model.CommitChange;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class GithubFetchService {

    @Value("${github.token}")
    private String githubToken;

    public List<CommitChange> fetchCommitDetails(String githubApiUrl) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "token " + githubToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(githubApiUrl, HttpMethod.GET, entity, String.class);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Unexpected response status: " + response.getStatusCode());
        }

        String responseBody = response.getBody();
      //  System.out.println("Response Body: " + responseBody);  // Print the response body for debugging

        List<CommitChange> changes = new ArrayList<>();

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

                    changes.add(new CommitChange(filename, status, patch));
                }
            } else {
                System.out.println("The JSON response does not contain the expected 'files' field.");
            }
        } else {
            System.out.println("The response is not a valid JSON object.");
        }

        return changes;
    }
}
