package com.systechafrica.jdbc;

import java.util.logging.FileHandler;
import java.util.logging.Logger;

import com.systechafrica.logging.CustomFormatter;

public class DatabaseDemo {
    private static final Logger LOGGER = Logger.getLogger(DatabaseDemo.class.getName());

    public static void main(String[] args) {
        try {
            FileHandler fileHandler = new FileHandler("working-with-database.txt");
            CustomFormatter formatter = new CustomFormatter();
            fileHandler.setFormatter(formatter);
            LOGGER.addHandler(fileHandler);

        } catch (Exception e) {
            LOGGER.severe("Unable to perform operation" + e.getMessage());
        }

    }

}
