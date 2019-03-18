package com.jonatnie.facturacionapp.model.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * UploadFileServiceImpl
 */
@Service
public class UploadFileServiceImpl implements IUploadFileService {

    /* This is only for developing remove before deploy */
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final static String UPLOAD_FOLDER = "upload";

    @Override
    public Resource load(String filename) throws MalformedURLException {
        Path photoPath = this.getPath(filename);
        Resource resource = null;
        resource = new UrlResource(photoPath.toUri());
        if (!resource.exists() || !resource.isReadable()) {
            throw new RuntimeException("Error: no se puede cargar la image => " + photoPath.toString());
        }
        return resource;
    }

    @Override
    public String copy(MultipartFile file) throws IOException {
        String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path rootPath = this.getPath(uniqueFilename);
        Path rootAbsolutePath = rootPath.toAbsolutePath();
        /* This is only for developing remove before deploy */
        log.info("rootPath: " + rootPath);
        log.info("rootAbsolutePath: " + rootAbsolutePath);
        Files.copy(file.getInputStream(), rootAbsolutePath);

        return uniqueFilename;
    }

    @Override
    public boolean delete(String filename) {
        Path rootPath = this.getPath(filename);
        File file = rootPath.toFile();

        if (file.exists() && file.canRead()) 
            if (file.delete()) 
                return true;
        
        return false;
    }

    protected Path getPath(String filename) {
        return Paths.get(UPLOAD_FOLDER).resolve(filename).toAbsolutePath();
    }

}