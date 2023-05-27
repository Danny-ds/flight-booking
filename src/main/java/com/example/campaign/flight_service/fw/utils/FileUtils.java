package com.example.campaign.flight_service.fw.utils;

import com.example.campaign.flight_service.fw.constants.Constants;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.DateUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * @author cb-dhana
 */
@Component
public class FileUtils {

    public FileUtils() {
        createDirIfNotExists(Constants.File.INPUT_DIR);
        createDirIfNotExists(Constants.File.OUTPUT_DIR);
    }

    private static final SimpleDateFormat DD_MM_YYYY_HH_MM_SS = new SimpleDateFormat("dd_mm_YYY_HH_MM_SS");

    public String storeFile(MultipartFile input, String name, String extension) throws Exception {
        name = generateRandomName(name, extension);
        File file = new File(Constants.File.INPUT_DIR + name);
        Files.copy(input.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return file.getPath();
    }

    public String createTempOutputFile(String name, String extension) {
        name = generateRandomName(name, extension);
        File file = new File(Constants.File.OUTPUT_DIR + name);

        return file.getPath();
    }

    private static String generateRandomName(String name, String extension) {
        return name + "_" + new Random().nextInt(0, 9999999) + "_" + DD_MM_YYYY_HH_MM_SS.format(DateUtils.createNow().getTime()) + extension;
    }

    private void createDirIfNotExists(String directoryPath) {
        Path directory = Paths.get(directoryPath);

        try {
            // Check if the directory already exists
            if (!Files.exists(directory)) {
                // Create the directory
                Files.createDirectories(directory);
                System.out.println("Directory created!");
            } else {
                System.out.println("Directory already exists!");
            }
        } catch (IOException e) {
            // Handle directory creation failure
            e.printStackTrace();
        }
    }
}
