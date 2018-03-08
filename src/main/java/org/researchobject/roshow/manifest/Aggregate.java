package org.researchobject.roshow.manifest;

import java.util.List;

public class Aggregate {
    private String uri;
    private String mediatype;
    private String createdon;
    private List<String> authors;
    private String retrievedfrom;
    private String retrievedby;
    private String conformsto;
    private String folderlocation;
    private String bundleuri;

    /* TODO: implement types for aggregates and specify attributes of each type */
    /*public enum {
        png, svg, cwl;
    }*/

    void setUri(String uri) {
        this.uri = uri;
    }

    void setMediatype(String mediatype) {
        this.mediatype = mediatype;
    }

    void setCreatedon(String createdon) {
        this.createdon = createdon;
    }

    void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    void setRetrievedfrom(String retrievedfrom) {
        this.retrievedfrom = retrievedfrom;
    }

    void setRetrievedby(String retrievedby) {
        this.retrievedby = retrievedby;
    }

    void setConformsto(String conformsto) {
        this.conformsto = conformsto;
    }

    void setFolderlocation(String folderlocation) {
        this.folderlocation = folderlocation;
    }

    void setBundleuri(String bundleuri) {
        this.bundleuri = bundleuri;
    }

    public String getUri() {
        return uri;
    }

    public String getMediatype() {
        return mediatype;
    }

    public String getCreatedon() {
        return createdon;
    }

    public String getAuthors() {
        StringBuilder allAuthors = new StringBuilder();
        for(String author : authors){
            allAuthors.append(author).append(", ");
        }
        return allAuthors.toString();
    }

    public String getRetrievedfrom() {
        return retrievedfrom;
    }

    public String getRetrievedby() {
        return retrievedby;
    }

    public String getConformsto() {
        return conformsto;
    }

    public String getFolderlocation() {
        return folderlocation;
    }

    public String getBundleuri() {
        return bundleuri;
    }
}