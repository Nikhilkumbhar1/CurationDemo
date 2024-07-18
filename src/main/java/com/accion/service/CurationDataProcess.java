package com.accion.service;

import com.accion.model.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CurationDataProcess {



    @Autowired
    private GitHubService gitHubService;

    @Autowired
    private GitHubApiUrlGeneratorService gitHubApiUrlGeneratorService;

    @Autowired
    private GithubFetchService githubFetchService;

    @Autowired
    private MavenVersionService mavenVersionService;

    @Autowired
    private MavenSourceService mavenSourceService;

    public CurationData getCurationData(String ghsId) throws Exception {
        AdvisoryData advisories = gitHubService.getAdvisories(ghsId);
        return getCurationData(advisories);
    }

    @NotNull
    private CurationData getCurationData(AdvisoryData advisories) throws Exception {
        List<String> vulnerableVersions = new ArrayList<>();
        List<String> fixedVersions = new ArrayList<>();
        CurationData curationData = new CurationData();
        curationData.setCveId(advisories.getCveId());
        curationData.setGhsaId(advisories.getGhsaId());
        curationData.setTitle(advisories.getTitle());
        curationData.setLanguage(advisories.getVulnerabilities().get(0).getPackageName().getPackageType());
        curationData.setPackageName(advisories.getVulnerabilities().get(0).getPackageName().getName());
        curationData.setDescription(advisories.getDescription());
       // curationData.setVulnerableVersions(Collections.singletonList(advisories.getVulnerabilities().get(0).getVulnerableVersionRange()));
        //curationData.setFixedVersions(Collections.singletonList(advisories.getVulnerabilities().get(0).getFirstPatchedVersion()));
        curationData.setCvssVector(advisories.getSeverityScore().get("vector_string"));
        curationData.setCvss3Score(advisories.getSeverityScore().get("score"));
        //curationData.setCvssVector(advisories.getSeverityScore());
        // curationData.setCvss3Score();

        for (Vulnerabilities vulnerability : advisories.getVulnerabilities()) {
            vulnerableVersions.add(vulnerability.getVulnerableVersionRange());
            fixedVersions.add(vulnerability.getFirstPatchedVersion());
        }
        curationData.setVulnerableVersions(vulnerableVersions);
        curationData.setFixedVersions(fixedVersions);
        curationData.setFixedCommit(findFixedCommit(advisories));
      //  fetchCommitChangesService.generateCommitApiUrl(advisories.f)
        List<String> fixedCommit = curationData.getFixedCommit();
        for(String commit:fixedCommit){
            List<CommitChange> commitChanges = githubFetchService.fetchCommitDetails(gitHubApiUrlGeneratorService.generateCommitApiUrl(commit));
            System.out.println("Commit Changes : "+commitChanges.toString());
        }


        //Here I have doubt which version I need to pass
        System.out.println("All versions for "+advisories.getVulnerabilities().get(0).getPackageName().getName()+ "package are "+mavenVersionService.fetchVersions(advisories.getVulnerabilities().get(0).getPackageName().getName()) );
        List<String> list = mavenVersionService.fetchVersions(advisories.getVulnerabilities().get(0).getPackageName().getName());


        curationData.setIntroCommit(mavenSourceService.downloadMavenSource(advisories.getVulnerabilities().get(0).getPackageName().getName(),"4.8.173"));

        return curationData;
    }

    public static List<String> findFixedCommit(AdvisoryData advisories) {
        List<String> references = advisories.getReferences();
        List<String> commitReferences = new ArrayList<>();
        String keyword = "commit";

        for (String reference : references) {
            if (reference.contains(keyword)) {
                System.out.println("Commit: " + reference);
                commitReferences.add(reference);
            }
        }

        // Return the list of commit references, or null if no commits were found
        return commitReferences.isEmpty() ? null : commitReferences;
    }


}
