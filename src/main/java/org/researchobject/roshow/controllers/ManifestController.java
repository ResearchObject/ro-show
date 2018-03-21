package org.researchobject.roshow.controllers;

import org.json.simple.parser.ParseException;
import org.researchobject.roshow.manifest.ManifestFile;
import org.researchobject.roshow.manifest.ManifestJsonReader;
import org.researchobject.roshow.model.UUIDdb;
import org.researchobject.roshow.repository.UUIDrepository;
import org.researchobject.roshow.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

@Controller
public class ManifestController {

    private final StorageService storageService;
    private boolean validation_result = true;

    @Autowired
    UUIDrepository uuiDrepository;

    @Autowired
    public ManifestController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/displayManifest")
    public String displayManifest(@RequestParam UUID manifest, Model model)
            throws IOException, ParseException, NullPointerException {

        List<UUIDdb> uuidbList = storageService.getUUIDdbList();
        Map<UUID, ManifestFile> manifestMap = new HashMap<>();
        List<UUID> uuidList = new ArrayList<UUID>();
        String previewLink = storageService.load(manifest.toString()).toString().concat("/visualisation.png");
        File file = new File(storageService.load(manifest.toString()).toString().concat("/.ro/manifest.json"));
        validation_result = !validation_result;


        ManifestJsonReader jsonReader;
        if (file.exists()){
            jsonReader = new ManifestJsonReader(file);
        }
        else {
            file = ResourceUtils.getFile("classpath:demo/manifest.json");
            jsonReader = new ManifestJsonReader(file);
        }

        Map<UUID, String> uuidMap = new HashMap<>();
        for (UUIDdb uuiddb : uuidbList) {
            uuidList.add(uuiddb.getUuid());
            uuidMap.put(uuiddb.getUuid(), uuiddb.getRoName());
        }

        model.addAttribute("uuid", manifest);
        model.addAttribute("validation_result", validation_result);
        model.addAttribute("previewLink", previewLink);
        model.addAttribute("uuidMap", uuidMap);
        model.addAttribute("manifest", jsonReader.getManifest(uuiDrepository.findOne(manifest).getRoName()));
        model.addAttribute("manifests", manifestMap);
        model.addAttribute("uuids", uuidList);
        return "display";
    }

    @GetMapping("/upload-dir/{filename:.+}/visualisation.png")
    @ResponseBody
    public byte [] serveFile(@PathVariable String filename) throws IOException {
        Path path = Paths.get(storageService.load(filename).toString().concat("/visualisation.png"));
        return Files.readAllBytes(path);
    }

    /* backup method that displays the JSON correctly on screen */
    @GetMapping("/rawManifest")
    public ResponseEntity<byte []> displayManifest(@RequestParam UUID manifest) throws IOException{
        File file = new File(storageService.load(manifest.toString()).toString().concat("/.ro/manifest.json"));
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Files.readAllBytes(file.toPath()));
    }

    @GetMapping("/roStructure")
    public String printROStructure(@RequestParam UUID manifest, Model model){

        File folder = new File(storageService.load(manifest.toString()).toString());

        model.addAttribute("structure", printDirectoryTree(folder));

        return "structure";

    }

    public static String printDirectoryTree(File folder) {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("folder is not a Directory");
        }
        int indent = 0;
        StringBuilder sb = new StringBuilder();
        printDirectoryTree(folder, indent, sb);
        return sb.toString();
    }

    private static void printDirectoryTree(File folder, int indent,
                                           StringBuilder sb) {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("folder is not a Directory");
        }
        sb.append(getIndentString(indent));
        sb.append("+--");
        sb.append(folder.getName());
        sb.append("/");
        sb.append("<br/>");
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                printDirectoryTree(file, indent + 1, sb);
            } else {
                printFile(file, indent + 1, sb);
            }
        }

    }

    private static void printFile(File file, int indent, StringBuilder sb) {
        sb.append(getIndentString(indent));
        sb.append("+--");
        sb.append(file.getName());
        sb.append("<br/>");
    }

    private static String getIndentString(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("|  ");
        }
        return sb.toString();
    }

}