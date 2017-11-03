package org.researchobject.roshow.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JsonReader {

    private File file;
    private JSONObject jsonObject;
    public JsonReader() {

    }

    public JsonReader(File file) {
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

    public String getAuthors() {

        StringBuilder sb = new StringBuilder();
        String authors = "";
        try{
            JSONArray authoredBy = (JSONArray) jsonObject.get("authoredBy");

            for(Object author : authoredBy)
            {
                if (author instanceof JSONObject){
                    JSONObject jsonObject1 = (JSONObject)author;
                    authors = sb.append(jsonObject1.get("name")).append(" | ").toString();
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "authoredBy: " + authors;
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

        return "profile: " + roProfile + "; viewer: " + recommendedViewer;
    }

    public String getDateCreated(){
        String dateCreated = "";
        try{
            dateCreated = (String) jsonObject.get("createdOn");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return "createdOn: " + dateCreated;
    }
}

