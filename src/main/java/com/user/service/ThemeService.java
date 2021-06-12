package com.user.service;

import com.user.model.entities.Theme;
import com.user.model.repositories.ThemeRepository;
import com.user.service.commons.AbstractService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

@Service
// Lombok
@FieldDefaults(level = AccessLevel.PRIVATE)
@Log
public class ThemeService extends AbstractService<Theme, ThemeRepository> {
}
