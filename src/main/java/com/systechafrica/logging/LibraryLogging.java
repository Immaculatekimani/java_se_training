package com.systechafrica.logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class LibraryLogging {
    private static final Logger LOGGER = Logger.getLogger(LibraryLogging.class.getName());

    public static void logging() {

        try {
            FileHandler fileHandler = new FileHandler("library-log-file.txt", true);
            CustomFormatter formatter = new CustomFormatter();
            fileHandler.setFormatter(formatter);
            LOGGER.addHandler(fileHandler);
        } catch (SecurityException e) {
            LOGGER.severe("Unable to obtain security permissions for the log file: " + e.getMessage());
        } catch (IOException e) {
            LOGGER.info("Ooops! read/write permissions denied: " + e.getMessage());
        }
    }

}
