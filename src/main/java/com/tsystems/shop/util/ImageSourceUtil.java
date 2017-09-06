package com.tsystems.shop.util;

import java.io.File;

public class ImageSourceUtil {
    private static final String IMAGES = "images";
    private static final String TOMCAT_HOME_PROPERTY = "catalina.home";
    private static final String TOMCAT_HOME_PATH = System.getProperty(TOMCAT_HOME_PROPERTY);
    private static final String IMAGES_PATH = TOMCAT_HOME_PATH + File.separator + IMAGES;

    private static final File IMAGES_DIR = new File(IMAGES_PATH);
    private static final String IMAGES_DIR_ABSOLUTE_PATH = IMAGES_DIR.getAbsolutePath() + File.separator;

    public static String getImagesDirectoryAbsolutePath() {
        return IMAGES_DIR_ABSOLUTE_PATH;
    }

    public static File getImagesDirectory() {
        return IMAGES_DIR;
    }
}
