package com.accion.controller;

import com.accion.model.AdvisoryData;
import com.accion.service.GitHubService;
import com.accion.service.NVDFetchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class GitHubController {

    private final GitHubService gitHubService;
    private final NVDFetchService nvdFetchService;

    public GitHubController(GitHubService gitHubService, NVDFetchService nvdFetchService) {
        this.gitHubService = gitHubService;
        this.nvdFetchService = nvdFetchService;
    }

    /*@GetMapping("/advisories")
    public AdvisoryData getAdvisories() {
        return gitHubService.getAdvisories();
    }*/



}

