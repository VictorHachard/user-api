package com.user.controller;

import com.user.Authorisation;
import com.user.Environment;
import com.user.model.entities.enums.RoleEnum;
import com.user.service.commons.FileStorageService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/image/")
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class ImageController {

    @Autowired
    FileStorageService fileStorageService;

    @Authorisation(roles = {RoleEnum.ROLE_USER})
    @ResponseBody
    @GetMapping("{url}")
    public ResponseEntity<FileSystemResource> getImageAsResource(@PathVariable("url") String url) {
        FileSystemResource res = new FileSystemResource(Environment.getInstance().DATA_FOLDER + url);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Authorisation(roles = {RoleEnum.ROLE_USER})
    @PostMapping("upload")
    public void add(MultipartFile file) {
        fileStorageService.storeFile(file);
    }

}
