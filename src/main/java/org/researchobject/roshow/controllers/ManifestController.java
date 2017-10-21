package org.researchobject.roshow.controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.researchobject.roshow.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class ManifestController {


    private final StorageService storageService;

    @Autowired
    public ManifestController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/displayManifest")
    public String displayManifest(@RequestParam UUID manifest, Model model) throws IOException, ParseException, NullPointerException {
        File file = new File(storageService.load(manifest.toString()).toString().concat("/.ro/manifest.json"));


        JSONParser parser = new JSONParser();

        Object obj = parser.parse(new FileReader(storageService.load(manifest.toString()).toString().concat("/.ro/manifest.json")));

        JSONObject jsonObject = (JSONObject) obj;
        String retrievedFrom = (String) jsonObject.get("retrievedFrom");
        JSONArray authoredBy = (JSONArray) jsonObject.get("authoredBy");
        JSONArray aggregates = (JSONArray) jsonObject.get("aggregates");
        JSONArray annotations = (JSONArray) jsonObject.get("annotations");

        List<String> jsonValues = new ArrayList<>();
        jsonValues.add(authoredBy.toJSONString());
        jsonValues.add(aggregates.toJSONString());
        jsonValues.add(annotations.toJSONString());
        jsonValues.add(retrievedFrom);
        model.addAttribute("jsonDump", jsonValues);

        return "display";

    }


    /* backup method that displays the JSON correctly on screen */
//    @GetMapping("/displayManifest")
//    public ResponseEntity<byte []> displayManifest(@RequestParam UUID manifest) throws IOException{
//        File file = new File(storageService.load(manifest.toString()).toString().concat("/.ro/manifest.json"));
//        return ResponseEntity
//                .ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(Files.readAllBytes(file.toPath()));
//    }

}
