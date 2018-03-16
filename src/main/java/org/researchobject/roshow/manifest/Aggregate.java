package org.researchobject.roshow.manifest;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Aggregate {
    private String uri = "null";
    private String mediatype = "null";
    private String createdon = "null";
    private List<String> authors = Collections.singletonList("null");
    private String retrievedfrom = "null";
    private String retrievedby = "null";
    private String conformsto = "null";
    private String folderlocation = "null";
    private String bundleuri = "null";

    Aggregate() { }

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

    void setRetrievedby(Optional<String> retrievedby) {
        this.retrievedby = retrievedby.get();
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