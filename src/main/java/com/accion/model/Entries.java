package com.accion.model;

import java.util.Date;

public class Entries {
    private Date dateReported;
    private String reportedBy;
    private String entryNumber1;
    private String artifactID;
    private String libraryName;
    private String assignee;
    private String vulnerabilityIdentifier;
    private String assignmentStatus;

    public Entries(Date dateReported, String reportedBy, String entryNumber1, String artifactID, String libraryName, String assignee, String vulnerabilityIdentifier, String assignmentStatus) {
        this.dateReported = dateReported;
        this.reportedBy = reportedBy;
        this.entryNumber1 = entryNumber1;
        this.artifactID = artifactID;
        this.libraryName = libraryName;
        this.assignee = assignee;
        this.vulnerabilityIdentifier = vulnerabilityIdentifier;
        this.assignmentStatus = assignmentStatus;
    }

    public Entries() {
    }

    public Date getDateReported() {
        return dateReported;
    }

    public void setDateReported(Date dateReported) {
        this.dateReported = dateReported;
    }

    public String getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(String reportedBy) {
        this.reportedBy = reportedBy;
    }

    public String getEntryNumber1() {
        return entryNumber1;
    }

    public void setEntryNumber1(String entryNumber1) {
        this.entryNumber1 = entryNumber1;
    }

    public String getArtifactID() {
        return artifactID;
    }

    public void setArtifactID(String artifactID) {
        this.artifactID = artifactID;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getVulnerabilityIdentifier() {
        return vulnerabilityIdentifier;
    }

    public void setVulnerabilityIdentifier(String vulnerabilityIdentifier) {
        this.vulnerabilityIdentifier = vulnerabilityIdentifier;
    }

    public String getAssignmentStatus() {
        return assignmentStatus;
    }

    public void setAssignmentStatus(String assignmentStatus) {
        this.assignmentStatus = assignmentStatus;
    }
}
