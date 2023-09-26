package com.systechafrica.possystemupdate;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import com.systechafrica.commonoperations.Operations;
import com.systechafrica.logging.CustomFormatter;

public class Pos {
    private static final Logger LOGGER = Logger.getLogger(Pos.class.getName());

    Scanner scanner = new Scanner(System.in);
    final int MAX_ITEMS = 100;
    int noOfItems = 0;
    Item[] items = new Item[MAX_ITEMS];
    double totalAmount = 0.0;

    public static void main(String[] args) {
        Operations opp = new Operations();
        boolean isLogin;
        try {
            FileHandler fileHandler = new FileHandler("pos-log-file.txt", true);
            CustomFormatter formatter = new CustomFormatter();
            fileHandler.setFormatter(formatter);
            LOGGER.addHandler(fileHandler);

            isLogin = opp.login();
            Pos app = new Pos();
            if (isLogin) {
                System.out.println("Welcome " + opp.userName.toUpperCase());
                boolean showMenu = true;
                while (showMenu) {
                    app.mainMenu();
                    try {
                        int option = app.scanner.nextInt();
                        System.out.println("LOADING...");
                        Thread.sleep(1500);

                        switch (option) {
                            case 1:
                                boolean isRepeat = false;
                                app.addItem();
                                do {

                                    System.out.println("Add another item? \n 1: Yes    \n 2: No");
                                    int choice = app.scanner.nextInt();
                                    if (choice == 1) {
                                        isRepeat = true;
                                        app.addItem();
                                    } else if (choice == 2) {
                                        isRepeat = false;
                                    } else {
                                        System.out.println("Please select either 1 or 2");
                                        isRepeat = true;

                                    }

                                } while (isRepeat);
                                break;
                            case 2:
                                app.displayReceipt();
                                app.makePayments();
                                break;
                            case 3:
                                app.displayReceipt();
                                break;
                            case 4:
                                app.clearItems();
                                showMenu = false;
                                return;
                            default:
                                System.out.println("Please select from options above");
                        }
                    } catch (InputMismatchException e) {
                        app.scanner.nextLine();
                        System.out.println("Please use numeric values");
                    } catch (SQLException e) {
                        LOGGER.severe("Something wrong with your database operation: " + e.getMessage());
                    }
                }
            } else {
                System.out.println("You have exceeded your maximum attempts!");
            }

        } catch (InterruptedException e) {
            System.out.println("Ooops! interrupted exception: " + e.getMessage());
        } catch (SecurityException e) {
            LOGGER.severe("Unable to obtain security permissions for the log file: " + e.getMessage());
        } catch (IOException e) {
            LOGGER.info("Ooops! read/write permissions denied: " + e.getMessage());
        }

    }

    public void mainMenu() {
        System.out.println("***************************************************************");
        System.out.println();
        System.out.println("                       SYSTECH POS SYSTEM                      ");
        System.out.println();
        System.out.println("***************************************************************");
        System.out.println();
        System.out.println("1. ADD ITEM");
        System.out.println("2. MAKE PAYMENT");
        System.out.println("3. DISPLAY RECEIPT");
        System.out.println("4. QUIT");
        System.out.println();
        System.out.println("Choose an option: ");

    }

    public void addItem() throws SQLException {

        Connection connection = databaseConnection();
        // create the table
        Statement statement = connection.createStatement();
        String createItemsTable = "CREATE TABLE IF NOT EXISTS items (item_id INT AUTO_INCREMENT PRIMARY KEY,item_code VARCHAR(255) NOT NULL,item_quantity INT,item_price DOUBLE)  ENGINE=INNODB;";
        int tableStatus = statement.executeUpdate(createItemsTable);
        if (tableStatus != 0) {
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
                clearItems();

            } else {
                System.out.println("Insufficient Funds!");
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

    }

}
