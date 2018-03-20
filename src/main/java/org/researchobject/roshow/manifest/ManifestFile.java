package org.researchobject.roshow.manifest;

import java.util.Collections;
import java.util.List;

public class ManifestFile {

    private String context  = "null";
    private String createdBy = "null";
    private String createdOn = "null";
    private List<String> authors = Collections.singletonList("null");
    private String retrievedFrom = "null";
    private String retrievedOn = "null";
    private String retrievedBy = "null";
    private List<String> history = Collections.singletonList("null");
    private List<Aggregate> aggregates = Collections.singletonList(new Aggregate());
    private List<String> annotations = Collections.singletonList(getBundle_name() + " contains no additional annotation");
    private String bundle_name = "null";
    private String profile = "null";

    ManifestFile() { }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getBundle_name() {
        return bundle_name;
    }

    public void setBundle_name(String bundle_name) {
        this.bundle_name = bundle_name;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        if(createdBy.equals("Common Workflow Language Viewer")) {
            this.createdBy = getBundle_name() + " was created by " + createdBy;
            this.profile = "Workflow-centric Research Object";
        }
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getRetrievedFrom() {
        return retrievedFrom;
    }

    public void setRetrievedFrom(String retrievedFrom) {
        this.retrievedFrom = retrievedFrom;
    }

    public String getRetrievedOn() {
        return retrievedOn;
    }

    public void setRetrievedOn(String retrievedOn) {
        this.retrievedOn = retrievedOn;
    }

    public String getRetrievedBy() {
        return retrievedBy;
    }

    public void setRetrievedBy(String retrievedBy) {
        this.retrievedBy = retrievedBy;
        if (retrievedBy.equals("https://view.commonwl.org")) {
            this.profile = "Workflow-centric Research Object";
        }
    }

    public List<String> getHistory() {
        return history;
    }

    public void setHistory(List<String> history) {
        this.history = history;
    }

    public List<Aggregate> getAggregates() {
        return aggregates;
    }

    public void setAggregates(List<Aggregate> aggregates) {
        this.aggregates = aggregates;
    }

    public List<String> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<String> annotations) {
        this.annotations = annotations;
    }
}
