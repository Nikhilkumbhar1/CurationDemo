package com.accion.service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GitHubApiUrlGeneratorService {

    public String generateCommitApiUrl(String commitUrl) {
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