package com.user.service.commons;

import com.user.Environment;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;

@Service
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class FileStorageService {

    final Path fileStorageLocation = Paths.get(Environment.getInstance().DATA_FOLDER);

    public String storeFile(MultipartFile file) {
        //TODO handle exception
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public void deleteFile(String file) {
        //TODO handle exception
        try {
            Files.deleteIfExists(Paths.get(Environment.getInstance().DATA_FOLDER + file));
        } catch(NoSuchFileException e) {
            System.out.println("No such file/directory exists");
        } catch(DirectoryNotEmptyException e) {
            System.out.println("Directory is not empty.");
        } catch(IOException e) {
            System.out.println("Invalid permissions.");
        }
    }

}
