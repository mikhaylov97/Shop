package com.tsystems.shop.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;


/**
 * This Util exists for helping developers to save different images into
 * server physical memory inside apache tomcat catalog
 */
public class ImageUtil {

    /**
     * Apache log4j object is used to logging all important info.
     */
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(ImageUtil.class);

    /**
     * Name of the folder where image will be saved
     */
    private static final String IMAGES = "images";

    /**
     * Property of the system which helps us to find tomcat root folder
     */
    private static final String TOMCAT_HOME_PROPERTY = "catalina.home";

    /**
     * Absolute path to the tomcat root folder
     */
    private static final String TOMCAT_HOME_PATH = System.getProperty(TOMCAT_HOME_PROPERTY);

    /**
     * Path to the images folder inside the tomcat root catalog
     */
    private static final String IMAGES_PATH = TOMCAT_HOME_PATH + File.separator + IMAGES;

    /**
     * Field which stores Java API of images folder
     */
    private static final File IMAGES_DIR = new File(IMAGES_PATH);

    /**
     * Absolute path to the images folder inside the tomcat root catalog
     */
    private static final String IMAGES_DIR_ABSOLUTE_PATH = IMAGES_DIR.getAbsolutePath() + File.separator;

    /**
     * Empty constructor
     */
    private ImageUtil() {

    }

    /**
     * Method saves some file into images folder(it is assumed the this will be a picture)
     * @param name - Future name of the saving file
     * @param file - Directly, the file itself
     */
    public static void uploadImage(String name, MultipartFile file) {
        File image = new File(IMAGES_DIR_ABSOLUTE_PATH + name);
        try(BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(image))) {
            stream.write(file.getBytes());
            stream.close();
        } catch (Exception e) {
            log.error("Something was wrong during uploading image.", e);
        }
    }

    /**
     * Method creates images directory if it doesn't exist
     */
    public static void createImagesDirectoryIfNeeded() {
        if (!IMAGES_DIR.exists()) {
            IMAGES_DIR.mkdirs();
        }
    }

    /**
     * Method give absolute path to the image folder
     * @return path to the folder.
     */
    public static String getImagesDirectoryAbsolutePath() {
        return IMAGES_DIR_ABSOLUTE_PATH;
    }
}
