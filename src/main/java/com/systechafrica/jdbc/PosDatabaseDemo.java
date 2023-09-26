package com.systechafrica.jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import com.systechafrica.logging.CustomFormatter;
import com.systechafrica.possystemupdate.Item;

public class PosDatabaseDemo {
    private static final Logger LOGGER = Logger.getLogger(PosDatabaseDemo.class.getName());
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            PosDatabaseDemo posDatabase = new PosDatabaseDemo();

            FileHandler fileHandler = new FileHandler("pos-log-file.txt");
            CustomFormatter formatter = new CustomFormatter();
            fileHandler.setFormatter(formatter);
            LOGGER.addHandler(fileHandler);

            // load the driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // connect to the database
            String connectionUrl = "jdbc:mysql://localhost:3309/pos";
            String user = "javase";
            String password = "javase";
            Connection connection = DriverManager.getConnection(connectionUrl, user, password);
            LOGGER.info("Connected successfully!");

            // create the table
            Statement statement = connection.createStatement();
            String createItemsTable = "CREATE TABLE IF NOT EXISTS items (item_id INT AUTO_INCREMENT PRIMARY KEY,item_code VARCHAR(255) NOT NULL,item_quantity INT,item_price DECIMAL)  ENGINE=INNODB;";
            int tableStatus = statement.executeUpdate(createItemsTable);
            LOGGER.info("table created and status is: " + tableStatus);

            // insert items
            String insertItem = "insert into items (item_code,item_quantity,item_price)values(?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(insertItem);

            Item item = posDatabase.addItemToDb();
            preparedStatement.setString(1, item.getItemCode());
            preparedStatement.setInt(2, item.getQuantity());
            preparedStatement.setDouble(3, item.getPrice());
            int rowsAffected = preparedStatement.executeUpdate();
            LOGGER.info("Number of rows added: " + rowsAffected);

        } catch (ClassNotFoundException e) {
            LOGGER.severe("Sorry class not found");
        } catch (IOException e) {
            LOGGER.info("Ooops! read/write permissions denied: " + e.getMessage());
        } catch (SQLException e) {
            LOGGER.severe("Something wrong with your database operation: " + e.getMessage());
        }
    }

    public Item addItemToDb() {

        System.out.print("Enter item unit code: ");
        String itemCode = scanner.next();
        System.out.print("Enter item quantity: ");
        int itemQuantity = scanner.nextInt();
        System.out.print("Enter item price: ");
        double itemPrice = scanner.nextDouble();

        Item newItem = new Item(itemCode, itemQuantity, itemPrice);

        return newItem;

    }

}
