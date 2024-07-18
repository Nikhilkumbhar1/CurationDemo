package com.accion.service;

import com.accion.model.AdvisoryData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GitHubService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitHubService.class);

    @Value("${github.token}")
    private String githubToken;

    private final RestTemplate restTemplate;

    public GitHubService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AdvisoryData getAdvisories(String ghsaId) {
        String url = "https://api.github.com/advisories/" + ghsaId;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/vnd.github+json");
        headers.set("Authorization", "Bearer " + "");
        headers.set("X-GitHub-Api-Version", "2022-11-28");
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<AdvisoryData> response = restTemplate.exchange(url, HttpMethod.GET, entity, AdvisoryData.class);


        if (response.getStatusCode().is2xxSuccessful()) {

            return response.getBody();
        } else {
            throw new RuntimeException("Failed to retrieve data: " + response.getStatusCode());
        }
    }
}

