package org.researchobject.roshow.manifest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ManifestJsonReader {

    private File file;
    private JSONObject jsonObject;
    public ManifestJsonReader() {

    }

    public ManifestJsonReader(File file) {
        this.file = file;
        JSONParser jsonParser = new JSONParser();
        try {
            Object object = jsonParser
                    .parse(new FileReader(file));

            this.jsonObject = (JSONObject) object;
        }
        catch(FileNotFoundException fe)
        {
            fe.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public List<String> getAuthors() {
        List<String> authorsList = new ArrayList<>();
        String authors = " ";
        try{
            JSONArray authoredBy = (JSONArray) jsonObject.get("authoredBy");

            for(Object author : authoredBy)
            {
                if (author instanceof JSONObject){
                    JSONObject jsonObject = (JSONObject)author;
                    authors = (String) jsonObject.get("name");
                }
                authorsList.add(authors);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return authorsList;
    }

    public String getViewer() {
        String roProfile = " ";
        String recommendedViewer = " ";
        try{
            JSONObject profile = (JSONObject) jsonObject.get("createdBy");
            recommendedViewer = (String) profile.get("name");

            if(recommendedViewer.equals("Common Workflow Language Viewer")){
                roProfile += "Workflow Research Object";
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return roProfile + " (can be viewed by " + recommendedViewer + ")";
    }

    public String getDateCreated(){
        String dateCreated = " ";
        try{
            dateCreated = (String) jsonObject.get("createdOn");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return dateCreated;
    }

    public String getRetrievedFrom(){
        StringBuilder sb = new StringBuilder();
        String retrievedFr = "retrievedFrom";
        return retrievedFr;
    }

    public String getAggregates(){
        StringBuilder sb = new StringBuilder();
        String aggregates = "aggregates";
        return aggregates;
    }

    public List<String> getAnnotations(){
        List<String> annotations = new ArrayList<>();
        String annHolder = "";

        JSONArray annotationsArray = (JSONArray) jsonObject.get("annotations");
        for(Object annotation : annotationsArray)
        {
            if (annotation instanceof JSONObject){
                StringBuilder sb = new StringBuilder();
                JSONObject jsonObject = (JSONObject) annotation;
                annHolder = sb.append(jsonObject.get("uri")).append(" about-> ").append(jsonObject.get("content")).toString();
            }
            annotations.add(annHolder);
        }

        return annotations;
    }

}
