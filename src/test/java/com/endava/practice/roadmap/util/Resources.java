package com.endava.practice.roadmap.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

public enum Resources {
    FILE_JSON_USER_MALFORMED("json/post-request-400-user-malformed.json"),
    FILE_JSON_USER_MISSING_NAME("json/post-request-400-user-null-name.json");

    private Resource resource;

    public File getFile() {
        try {
            return resource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    Resources(String path) {
        resource = new ClassPathResource(path);
    }
}
