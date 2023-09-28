package com.systechafrica.possystemupdate;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import com.systechafrica.logging.CustomFormatter;

public class PosDatabase {
    private static final Logger LOGGER = Logger.getLogger(Pos.class.getName());
    Scanner scanner = new Scanner(System.in);
    double totalAmount = 0.0;
    int noOfItems = 0;

    public static void fileLogging() {

        try {
            FileHandler fileHandler = new FileHandler("pos-log-file.txt", true);
            CustomFormatter formatter = new CustomFormatter();
            fileHandler.setFormatter(formatter);
            LOGGER.addHandler(fileHandler);
        } catch (SecurityException e) {
            LOGGER.severe("Unable to obtain security permissions for the log file: " + e.getMessage());
        } catch (IOException e) {
            LOGGER.info("Ooops! read/write permissions denied: " + e.getMessage());
        }

    }

    // connect to the database
    private Connection databaseConnection() throws SQLException {
        String connectionUrl = "jdbc:mysql://localhost:3309/pos";
        String user = "javase";
        String password = "javase";
        return DriverManager.getConnection(connectionUrl, user, password);

    }

    public void clearItems() throws SQLException {
        // make the table empty
        Connection connection = databaseConnection();
        Statement statement = connection.createStatement();
        String deleteItems = "DELETE FROM items;";
        statement.executeUpdate(deleteItems);
        noOfItems = 0;
        LOGGER.info("Items cleared successfully");

    }

    public void addItem() throws SQLException {

        Connection connection = databaseConnection();
        // create the table
        Statement statement = connection.createStatement();
        String createItemsTable = "CREATE TABLE IF NOT EXISTS items (item_id INT AUTO_INCREMENT PRIMARY KEY,item_code VARCHAR(255) NOT NULL,item_quantity INT,item_price DOUBLE)  ENGINE=INNODB;";
        int tableStatus = statement.executeUpdate(createItemsTable);
        if (tableStatus == 0) {
            LOGGER.info("table created and status is: " + tableStatus);

        }

        // insert items

        System.out.print("Enter item unit code: ");
        String itemCode = scanner.next();
        System.out.print("Enter item quantity: ");
        int itemQuantity = scanner.nextInt();
        System.out.print("Enter item price: ");
        double itemPrice = scanner.nextDouble();

        String insertItem = "insert into items (item_code,item_quantity,item_price)values(?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(insertItem);

        preparedStatement.setString(1, itemCode);
        preparedStatement.setInt(2, itemQuantity);
        preparedStatement.setDouble(3, itemPrice);
        int rowsAffected = preparedStatement.executeUpdate();
        LOGGER.info(rowsAffected + " item has been added ");
        noOfItems++;

    }

    public void makePayments() throws SQLException {
        if (noOfItems == 0) {
            System.out.println("Please select an item then make payment.");
        } else {
            double change = 0.0;
            System.out.println("Enter the amount given by customer:");
            System.out.println();
            double payment = scanner.nextDouble();
            if (payment >= totalAmount) {
                change = payment - totalAmount;
                totalAmount = 0;
                System.out.print("Change:   " + change);
                System.out.println();
                System.out.println("***************************************************************");
                System.out.println();
                System.out.println("THANK YOU FOR SHOPPING WITH US \n");
                System.out.println("***************************************************************");
                LOGGER.info("payment successful");
                clearItems();

            } else {
                LOGGER.warning("Insufficient funds");
            }

        }

    }

    public void displayReceipt() throws SQLException {
        if (noOfItems == 0) {
            System.out.println("Sorry no items have been selected");
        } else {
            Connection connection = databaseConnection();
            Statement statement = connection.createStatement();
            totalAmount = 0;
            System.out.println("Item code \t item quantity \t unit price \t Total Value");

            // get items from database
            String selectItems = "SELECT * FROM items;";
            ResultSet results = statement.executeQuery(selectItems);
            while (results.next()) {
                String itemCode = results.getString("item_code");
                int quantity = results.getInt("item_quantity");
                double price = results.getDouble("item_price");
                double totalValue = quantity * price;
                System.out.println(itemCode + "\t\t  " + quantity + "\t\t  " + price + "\t\t  " + totalValue);
                totalAmount += totalValue;
            }
            System.out.println("***************************************************************");
            System.out.print("Total: " + totalAmount);
            System.out.println();
            System.out.println("***************************************************************");
        }

    }
    // working with users

    public void createUsersTable() throws SQLException {
        Connection connection = databaseConnection();
        Statement statement = connection.createStatement();
        String createTable = "CREATE TABLE IF NOT EXISTS users (user_id INT AUTO_INCREMENT PRIMARY KEY,user_name VARCHAR(255) UNIQUE,user_password VARCHAR(255) NOT NULL)  ENGINE=INNODB;";
        int tableStatus = statement.executeUpdate(createTable);
        if (!checkDefaultUserExists()) {

            String insertUser = "INSERT INTO users(user_name,user_password)values('Admin', 'Admin123');";
            PreparedStatement preparedStatement = connection.prepareStatement(insertUser);
            int rowsAffected = preparedStatement.executeUpdate();
            LOGGER.info("Default user Admin has been added");
        }

    }

    public boolean checkDefaultUserExists() throws SQLException {
        Connection connection = databaseConnection();
        Statement statement = connection.createStatement();

        String adminUser = "select count(*) from users where user_name = 'Admin';";
        ResultSet results = statement.executeQuery(adminUser);
        if(results.next()){
            return true;
        }else{
            return false;
        }

    }

    public User getUser(String username) throws SQLException {
        Connection connection = databaseConnection();
        String findUser = "SELECT * from users WHERE user_name = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(findUser);
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt("user_id");
            String userName = resultSet.getString("user_name");
            String userPassword = resultSet.getString("user_password");

            User foundUser = new User(id, userName, userPassword);
            return foundUser;
        }
        LOGGER.severe("user not found");
        return null;

    }

    // authenticate the found user
    public boolean authenticateDatabaseUser() throws SQLException, InterruptedException {
        int trials = 0;
        boolean loggedIn = false;
        while (trials < 3) {
            System.out.print("Please enter your username: ");
            String userName = scanner.nextLine();
            System.out.print("Please enter your password: ");
            String userPassword = scanner.nextLine();
            System.out.println("Loading...");
            Thread.sleep(1500);

            User userToCheck = getUser(userName);

            if (userToCheck != null) {
                if (userToCheck.authentication(userPassword) == true) {
                    loggedIn = true;
                    break;
                }
            }
            System.out.println("Wrong user name or password please try again");
            trials++;

        }
        return loggedIn;

    }

}
