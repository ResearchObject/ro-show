package org.researchobject.roshow.controllers;

import org.json.simple.parser.ParseException;
import org.researchobject.roshow.manifest.ManifestJsonReader;
import org.researchobject.roshow.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
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

        ManifestJsonReader jsonReader = new ManifestJsonReader(file);

        model.addAttribute("authors", jsonReader.getAuthors());
        model.addAttribute("retrievedFrom", jsonReader.getRetrievedFrom());
        model.addAttribute("annotations", jsonReader.getAnnotations());
        model.addAttribute("aggregates", jsonReader.getAggregates());
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
