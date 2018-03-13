package org.researchobject.roshow.manifest;

import java.util.List;

public class ManifestFile {

    private String context;
    private String createdBy;
    private String createdOn;
    private List<String> authors;
    private String retrievedFrom;
    private String retrievedOn;
    private String retrievedBy;
    private List<String> history;
    private List<Aggregate> aggregates;
    private List<String> annotations;
    private String bundle_name;

    ManifestFile(String name) {
        bundle_name = name;
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
