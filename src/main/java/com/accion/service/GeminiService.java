package com.accion.service;

import com.accion.model.CurationData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


@Service
public class GeminiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeminiService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("AIzaSyCkjqa3shvxFbNPYH9TclncJPedgrjc-bM")
    private String apiKey;

    private final String API_URL_TEMPLATE = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=%s";

    public CurationData callApi(CurationData curationData) throws JsonProcessingException {
        String apiUrl = String.format(API_URL_TEMPLATE, apiKey);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        LOGGER.warn(curationData.getDescription());

        String textJava = "summarize the following text:" + curationData.getDescription() +
                ". The format of the first sentence must be: [library name] is vulnerable to [vulnerability]. " +
                "The second sentence must be of the format: The vulnerability is due to [vulnerability] due to [brief description].";


        //System.out.println("Text :"+textJava);
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode contentNode = objectMapper.createObjectNode();
        ObjectNode partsNode = objectMapper.createObjectNode();
        partsNode.put("text", textJava);
        contentNode.set("parts", objectMapper.createArrayNode().add(partsNode));
        ObjectNode requestBodyNode = objectMapper.createObjectNode();
        requestBodyNode.set("contents", objectMapper.createArrayNode().add(contentNode));

        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(requestBodyNode);
        } catch (Exception e) {
            throw new RuntimeException("Failed to construct JSON request body", e);
        }

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, request, String.class);

        String body = response.getBody();

        JsonNode jsonNode = objectMapper.readTree(body);
        JsonNode path = jsonNode.path("candidates").get(0).path("content").path("parts").get(0).path("text");
        if (path.isTextual()) {
            String text = path.asText();
           // System.out.println("Gemini AI description :"+text );
            curationData.setDescription(text);
          //  System.out.println("Extracted text: " + text);
        } else {
          //  System.out.println("Text node not found");
        }

        //System.out.println(body);
        return curationData;
    }

}
