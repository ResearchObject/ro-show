package org.researchobject.roshow.controllers;

import com.github.jsonldjava.core.JsonLdError;
import org.json.simple.parser.ParseException;
import org.researchobject.roshow.manifest.ManifestFile;
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
import org.springframework.util.ResourceUtils;
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
    public String listUploadedFiles(Model model) throws IOException, JsonLdError, ParseException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(RoController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        List<UUIDdb> list = storageService.getUUIDdbList();
        List<UUID> uuidList = new ArrayList<>();
        Map<UUID, ManifestFile> manifestMap = new HashMap<>();

        //Use uuid as index for manifestFiles and add as model attribute
        //keep ManifestMap indexes in list of uuid's
        for (UUIDdb uuiDdb : list) {
            UUID uuid = uuiDdb.getUuid();
            uuidList.add(uuid);

            //if storageService.load is .json continue, if anything else, don't load

            File file = new File(storageService.load(uuiDdb.getUuid().toString()).toString().concat("/.ro/manifest.json"));
            if (file.exists()){
            ManifestFile manifestFile = new ManifestJsonReader(file).getManifest();
            File file = new File(storageService.load(uuid.toString()).toString().concat("/.ro/manifest.json"));
            String bundle_name = uuiDdb.getRoName();
            ManifestFile manifestFile = new ManifestJsonReader(file).getManifest(bundle_name);
            manifestMap.put(uuid, manifestFile);
            }
            else {
                file = ResourceUtils.getFile("classpath:demo/manifest.json");
                ManifestFile manifestFile = new ManifestJsonReader(file).getManifest();
                manifestMap.put(uuid, manifestFile);
            }

        }
        model.addAttribute("manifestsUuid", uuidList);
        model.addAttribute("manifests", manifestMap);
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

    @GetMapping("/external")
    public String externalRedirects() {

        return "external";
    }

    @PostMapping("/urlUpload")
    public String handleURLUpload(@ModelAttribute("url") String url,
                                   RedirectAttributes redirectAttributes) throws IOException {

        storageService.downloadFileFromURL(url);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + url.substring(url.lastIndexOf("/") + 1,
                        url.length()) + "!");

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
