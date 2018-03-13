package org.researchobject.roshow.controllers;

import org.json.simple.parser.ParseException;
import org.researchobject.roshow.manifest.ManifestFile;
import org.researchobject.roshow.manifest.ManifestJsonReader;
import org.researchobject.roshow.model.UUIDdb;
import org.researchobject.roshow.repository.UUIDrepository;
import org.researchobject.roshow.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.util.*;

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

        File file = new File(storageService.load(manifest.toString()).toString().concat("/.ro/manifest.json"));
        ManifestFile manifestfile = new ManifestJsonReader(file).getManifest(uuiDrepository.findOne(manifest).getRoName());

        model.addAttribute("manifest", manifestfile);
        model.addAttribute("validation_result", validation_result);
        validation_result = !validation_result;

        List<UUIDdb> uuidbList = storageService.getUUIDdbList();
        Map<UUID, ManifestFile> manifestMap = new HashMap<>();
        List<UUID> uuidList = new ArrayList<UUID>();
        for (UUIDdb uuidb : uuidbList) {
            ManifestFile manifestFile = new ManifestJsonReader(new File(storageService.load(uuidb.getUuid().toString())
                    .toString().concat("/.ro/manifest.json"))).getManifest(uuidb.getRoName());
            manifestMap.put(uuidb.getUuid(), manifestFile);
            uuidList.add(uuidb.getUuid());
        }

        model.addAttribute("manifests", manifestMap);
        model.addAttribute("uuids", uuidList);
        return "display";
    }

}