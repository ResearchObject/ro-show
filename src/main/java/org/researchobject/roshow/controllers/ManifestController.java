package org.researchobject.roshow.controllers;

import org.json.simple.parser.ParseException;
import org.researchobject.roshow.manifest.ManifestFile;
import org.researchobject.roshow.manifest.ManifestJsonReader;
import org.researchobject.roshow.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        ManifestJsonReader jsonReader;
        if (file.exists()){
            jsonReader = new ManifestJsonReader(file);
        }
        else {
            file = ResourceUtils.getFile("classpath:demo/manifest.json");
            jsonReader = new ManifestJsonReader(file);
        }

        /* TODO: replace with manifestFile */
        String previewLink = storageService.load(manifest.toString()).toString().concat("/visualisation.png");
        model.addAttribute("previewLink", previewLink);

        model.addAttribute("authors", jsonReader.getAuthors());
        model.addAttribute("retrievedFrom", jsonReader.getRetrievedFrom());
        model.addAttribute("annotations", jsonReader.getAnnotations());
        model.addAttribute("aggregates", jsonReader.getAggregates());
        return "display";
    }

    @GetMapping("/upload-dir/{filename:.+}/visualisation.png")
    @ResponseBody
    public byte [] serveFile(@PathVariable String filename) throws IOException {
        Path path = Paths.get(storageService.load(filename).toString().concat("/visualisation.png"));
        return Files.readAllBytes(path);
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