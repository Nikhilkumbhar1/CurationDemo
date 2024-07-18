package com.accion.service;


import com.accion.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NVDFetchService {
    private static final Logger logger = LoggerFactory.getLogger(NVDFetchService.class);
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;



    public NVDFetchService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public Response getCVEData(String cveId) {
        String url = "https://services.nvd.nist.gov/rest/json/cves/2.0?cveId=" + cveId;
        try {
            String jsonString = restTemplate.getForObject(url, String.class);
            logger.info("Fetched data: " + jsonString);
            Response response = objectMapper.readValue(jsonString, Response.class);
            return response;
        } catch (Exception e) {
            logger.error("Error mapping JSON to POJO: ", e);
            throw new RuntimeException(e);
        }
    }
}