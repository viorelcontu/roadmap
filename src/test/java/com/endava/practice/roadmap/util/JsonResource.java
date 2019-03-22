package com.endava.practice.roadmap.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;

public enum JsonResource {
    JSON_USER_ALL ("json/post-request-200-user-all.json"),
    JSON_USER_NEW ("json/post-request-201-user-new.json"),
    JSON_USER_MALFORMED ("json/post-request-400-user-malformed.json"),
    JSON_USER_MISSING_NAME("json/post-request-400-user-null-name.json"),
    JSON_USER_NULL_NAME("json/post-request-400-user-null-name.json"),
    JSON_USER_UPDATE ("json/put-request-202-user-update.json"),
    JSON_USER_NOT_FOUND ("json/put-request-404-user-not-found.json");

    private Resource resource;

    public File getFile() {
        try {
            return resource.getFile();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    JsonResource(String path) {
        resource = new ClassPathResource(path);
    }
}
