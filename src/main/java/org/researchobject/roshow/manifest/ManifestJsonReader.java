package org.researchobject.roshow.manifest;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ManifestJsonReader {

    private JSONObject jsonObject;

    public ManifestJsonReader(File file) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        Object object = jsonParser.parse(new FileReader(file));
        this.jsonObject = (JSONObject) object;
    }

    public ManifestFile getManifest(){
        ManifestFile manifest = new ManifestFile();
        manifest.setAggregates(this.getAggregates());
        manifest.setAnnotations(this.getAnnotations());
        manifest.setAuthors(this.getAuthors());
        manifest.setContext(this.getContext());
        manifest.setRetrievedFrom(this.getRetrievedFrom());
        manifest.setCreatedBy(this.getViewer());
        manifest.setCreatedOn(this.getDateCreated());
        return manifest;
    }

    private JSONArray getJsonArray(String arrayTag, JSONObject aJsonObject){
        return (JSONArray) aJsonObject.get(arrayTag);
    }

    private String getAttributeValue(String attribute, JSONObject aJsonObject){
        return (String) aJsonObject.get(attribute);
    }

    private JSONObject getAttributeObject(String attribute, JSONObject aJsonObject){
        return (JSONObject) aJsonObject.get(attribute);
    }

    public List<String> getAuthors() {
        List<String> authorsList = new ArrayList<>();
        for(Object author : getJsonArray("authoredBy", jsonObject)) {
            authorsList.add(getAttributeValue("name", (JSONObject) author));
        }
        return authorsList;
    }

    private String getViewer() {
        String roProfile = " ";
        String recommendedViewer = getAttributeValue("name", getAttributeObject("createdBy", jsonObject));
        if(recommendedViewer.equals("Common Workflow Language Viewer")) {
                roProfile += "Workflow Research Object";
        }
        return roProfile + " (can be viewed by " + recommendedViewer + ")";
    }

    private String getDateCreated(){
        return getAttributeValue("createdOn", jsonObject);
    }

    public String getRetrievedFrom(){
        return getAttributeValue("retrievedFrom", jsonObject);
    }

    public List<Aggregate> getAggregates(){
        List<Aggregate> aggregates= new ArrayList<>();
        Aggregate aggregateHolder;

        for(Object aggr : getJsonArray("aggregates", jsonObject)) {
            aggregateHolder = new Aggregate();
            aggregateHolder.setUri(getAttributeValue("uri", (JSONObject) aggr));
            aggregateHolder.setMediatype(getAttributeValue("mediatype", (JSONObject) aggr));
            aggregateHolder.setCreatedon(getAttributeValue("createdOn", (JSONObject)aggr));
            /*
            aggregateHolder.setRetrievedby(getAttributeValue("name", getAttributeObject("retrievedBy", (JSONObject) aggr)));
            List<String> authors = new ArrayList<>();
            JSONObject authorsObject= (JSONObject) aggr;
            JSONArray authorsArray = (JSONArray) authorsObject.get("authoredBy");
            if(authorsArray != null){
                for (Object author : authorsArray){
                    String anAuthor = (String) ((JSONObject) author).get("uri");
                    authors.add(anAuthor);
                }
            }

            for (Object author : getJsonArray("authoredBy", (JSONObject) aggr)){
                authors.add(getAttributeValue("uri", (JSONObject) author));
            }
            aggregateHolder.setAuthors(authors);
            aggregateHolder.setRetrievedby(getAttributeValue("name", getAttributeObject("retrievedBy", (JSONObject) aggr)));
            aggregateHolder.setRetrievedfrom(getAttributeValue("retrievedFrom", (JSONObject) aggr));
            aggregateHolder.setConformsto(getAttributeValue("conformsTo", (JSONObject) aggr));
            aggregateHolder.setFolderlocation(getAttributeValue("folder",
                    getAttributeObject("bundledAs", (JSONObject) aggr)));
            aggregateHolder.setBundleuri(getAttributeValue("uri",
                    getAttributeObject("bundledAs", (JSONObject) aggr)));*/
            aggregates.add(aggregateHolder);
        }
        return aggregates;
    }

    public List<String> getAnnotations(){
        List<String> annotations = new ArrayList<>();
        for(Object annotation : getJsonArray("annotations", jsonObject))
        {
            StringBuilder sb = new StringBuilder();
            annotations.add(sb.append(getAttributeValue("uri", (JSONObject) annotation))
                    .append(":").append(getAttributeValue("content", (JSONObject) annotation)).toString());
        }
        return annotations;
    }

    private String getContext(){
        return "ww3";
    }

}
