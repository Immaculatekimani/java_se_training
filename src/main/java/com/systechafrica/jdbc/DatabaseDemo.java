package com.systechafrica.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
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

            // load the driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // connect to the database
            String connectionUrl = "jdbc:mysql://localhost:3308/javase";
            String user = "javase";
            String password = "javase";
            Connection connection = DriverManager.getConnection(connectionUrl, user, password);
            LOGGER.info("Connected successfully!");

            // create a statement
            Statement statement = connection.createStatement();
            String createTasksTable = "CREATE TABLE IF NOT EXISTS tasks (task_id INT AUTO_INCREMENT PRIMARY KEY,title VARCHAR(255) NOT NULL,start_date DATE,due_date DATE,status TINYINT NOT NULL,priority TINYINT NOT NULL,description TEXT,created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)  ENGINE=INNODB;";

            // execute queries
            int updateStatus = statement.executeUpdate(createTasksTable);
            LOGGER.info("update status: " + updateStatus);
            String insertQuery = "insert into tasks (title,start_date,due_date,status,priority,description)values('Add driver to the pom.xml','2023-09-22','2023-09-25',0,1,'updating drivers to the class path');";
            int numberOfRowsInserted = statement.executeUpdate(insertQuery);
            LOGGER.info("Number of rows inserted: " + numberOfRowsInserted);

        } catch (Exception e) {
            LOGGER.severe("Unable to perform operation" + e.getMessage());
        }

    }

}
