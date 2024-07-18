package com.accion.service;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class MavenVersionService {

    public List<String> fetchVersions(String coordinates) throws Exception {
        // Split the input to get groupId and artifactId
        String[] parts = coordinates.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid coordinates format. Expected format is 'groupId:artifactId'.");
        }
        String groupId = parts[0];
        String artifactId = parts[1];

        // Construct the URL for the maven-metadata.xml
        String url = String.format("https://repo1.maven.org/maven2/%s/%s/maven-metadata.xml",
                groupId.replace('.', '/'), artifactId);

        // Create an HTTP client
        HttpClient client = HttpClient.newHttpClient();

        // Create an HTTP request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Check if the response is successful
        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch versions. HTTP error code: " + response.statusCode());
        }

        // Parse the XML response
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new java.io.ByteArrayInputStream(response.body().getBytes()));

        // Extract versions from the XML
        NodeList versionNodes = document.getElementsByTagName("version");
        List<String> versions = new ArrayList<>();
        for (int i = 0; i < versionNodes.getLength(); i++) {
            versions.add(versionNodes.item(i).getTextContent());
        }

        return versions;
    }
}

