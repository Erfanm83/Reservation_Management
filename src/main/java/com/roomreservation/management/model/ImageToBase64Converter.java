package com.roomreservation.management.model;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

// encode an image to base64
public class ImageToBase64Converter {
    public static String convertToBase64(String imagePath) throws Exception {
        Path path = Paths.get(imagePath);
        byte[] imageBytes = Files.readAllBytes(path);
        return Base64.getEncoder().encodeToString(imageBytes);
    }
}

