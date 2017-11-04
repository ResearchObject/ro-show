package org.researchobject.roshow.controllers;

import org.researchobject.roshow.model.UUIDdb;
import org.researchobject.roshow.manifest.ManifestJsonReader;
import org.researchobject.roshow.storage.StorageFileNotFoundException;
import org.researchobject.roshow.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Controller
public class RoController {

    private final StorageService storageService;

    @Autowired
    public RoController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(RoController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));


        List<UUIDdb> list = storageService.getUUIDdbList();
        List<UUID> uuidList = new ArrayList<>();
        List<String> authorsList;
        List<String> profileList;
        List<String> dateCreatedList;

        ManifestJsonReader jreader;
        Map<UUID, List> authorsMap = new HashMap<>();
        Map<UUID, List> profileMap = new HashMap<>();
        Map<UUID, List> dateCreatedMap = new HashMap<>();


        for (UUIDdb uuiDdb : list) {
            UUID uuid = uuiDdb.getUuid();
            uuidList.add(uuid);

            File file = new File(storageService.load(uuiDdb.getUuid().toString()).toString().concat("/.ro/manifest.json"));
            jreader = new ManifestJsonReader(file);

            profileList = new ArrayList<>();
            dateCreatedList = new ArrayList<>();

            authorsList = jreader.getAuthors();
            profileList.add(jreader.getViewer());
            dateCreatedList.add(jreader.getDateCreated());

            authorsMap.put(uuid, authorsList);
            profileMap.put(uuid, profileList);
            dateCreatedMap.put(uuid, dateCreatedList);

        }

        model.addAttribute("manifests", uuidList);
        model.addAttribute("authorsInfo", authorsMap);
        model.addAttribute("profileInfo", profileMap);
        model.addAttribute("dateCreatedInfo", dateCreatedMap);
        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
