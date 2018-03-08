package org.researchobject.roshow.storage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.researchobject.roshow.model.UUIDdb;
import org.researchobject.roshow.repository.UUIDrepository;
import org.researchobject.roshow.service.Unzipper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    private static final int BUFFER_SIZE = 4096;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Autowired
    UUIDrepository uuiDrepository;


    private static String getCurrentLocalTime() {
        LocalDateTime time = LocalDateTime.now();
        return time.toString();
    }

    @Override
    public void store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            if (filename.endsWith(".zip")) {
                UUID uuid = UUID.randomUUID();
                uuiDrepository.save(new UUIDdb(uuid, getCurrentLocalTime(), filename));

                filename = uuid.toString() + ".zip";
                Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);

                Unzipper unzipper = new Unzipper();
                unzipper.unzip(this.rootLocation.resolve(filename), this.rootLocation.resolve(uuid.toString()));
            }
            else {
                Files.copy(file.getInputStream(), this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    public void downloadFileFromURL(String fileURL)
            throws IOException {
        URL url = new URL(fileURL);
        if(url.getPath().contains("rodetails")){
            fileURL = "http://sandbox.rohub.org/rodl/zippedROs/" + url.getPath().replace("/rodetails/", "").replace("/overview","") + "/";
            url = new URL(fileURL);
        }
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();

        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String filename = "";
            String disposition = httpConn.getHeaderField("Content-Disposition");
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();

            if (disposition != null) {
                // extracts file name from header field
                int index = disposition.indexOf("filename=");
                if (index > 0) {
                    filename = disposition.substring(index + 9,
                            disposition.length() - 1);
                }
            } else {
                // extracts file name from URL
                filename = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                        fileURL.length());
            }

            UUID uuid = UUID.randomUUID();
            if (filename.endsWith(".zip")) {
                uuiDrepository.save(new UUIDdb(uuid, getCurrentLocalTime(), filename));
                filename = uuid.toString() + ".zip";
            }
            else {
                System.out.println("not a zip file");
            }

            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = this.rootLocation.resolve(filename).toString();

            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);

            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            outputStream.close();
            inputStream.close();


            Unzipper unzipper = new Unzipper();
            unzipper.unzip(this.rootLocation.resolve(filename), this.rootLocation.resolve(uuid.toString()));

            System.out.println("File downloaded");
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode);
        }
        httpConn.disconnect();
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public List<UUIDdb> getUUIDdbList() {
        List<UUIDdb> uuiDdbList = new ArrayList<>();
        uuiDrepository.findAll();
        for (UUIDdb uuiDdb : uuiDrepository.findAll()) {
            uuiDdbList.add(uuiDdb);
        }
        return uuiDdbList;
    }


}
