package com.user.service;

import com.user.model.entities.Image;
import com.user.model.repositories.ImageRepository;
import com.user.service.commons.AbstractService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class ImageService extends AbstractService<Image, ImageRepository> {
}
