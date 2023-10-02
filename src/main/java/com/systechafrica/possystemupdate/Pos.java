package com.systechafrica.possystemupdate;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import com.systechafrica.commonoperations.Operations;
import com.systechafrica.exceptionhandling.CustomException;
import com.systechafrica.logging.CustomFormatter;

public class Pos {
    private static final Logger LOGGER = Logger.getLogger(Pos.class.getName()); // to get the file handler and
                                                                                // customization

    Scanner scanner = new Scanner(System.in);

    double totalAmount = 0.0;
    int noOfItems = 0;
    Operations opp = new Operations();

    public static void main(String[] args) {
        fileLogging();

        boolean isLogin;

        PosDatabase db = new PosDatabase();
        Pos app = new Pos();

        Connection connection = db.databaseConnection();
        db.createUsersTable(connection);

        isLogin = app.isAuthenticated(connection);

        if (isLogin) {
            System.out.println("Welcome  ");
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
                            app.addItem(connection);
                            do {

                                System.out.println("Add another item? \n 1: Yes    \n 2: No");
                                int choice = app.scanner.nextInt();
                                if (choice == 1) {
                                    isRepeat = true;
                                    app.addItem(connection);
                                } else if (choice == 2) {
                                    isRepeat = false;
                                } else {
                                    System.out.println("Please select either 1 or 2");
                                    isRepeat = true;

                                }

                            } while (isRepeat);
                            break;
                        case 2:
                            app.displayReceipt(connection);
                            app.makePayments(connection);
                            break;
                        case 3:
                            app.displayReceipt(connection);
                            break;
                        case 4:
                            app.clearItems(connection);
                            db.closeResources(connection);
                            app.scanner.close();
                            showMenu = false;
                            return;
                        default:
                            System.out.println("Please select only from options above");
                    }
                } catch (InputMismatchException e) {
                    app.scanner.nextLine();
                    System.out.println("Please use numeric values");
                } catch (SQLException e) {
                    LOGGER.severe("Something wrong with your database operation: " + e.getMessage());
                } catch (CustomException e) {
                    LOGGER.severe(e.getMessage());
                } catch (InterruptedException e) {
                    System.out.println("Ooops! interrupted exception: " + e.getMessage());
                } catch (SecurityException e) {
                    LOGGER.severe("Unable to obtain security permissions for the log file: " + e.getMessage());

                } catch (NullPointerException e) {
                    LOGGER.severe("You are referring to a non existing value: " + e.getMessage());
                }
            }
        } else {
            System.out.println("You have exceeded your maximum attempts!");
        }

    }

    public static void fileLogging() {

        try {
            FileHandler fileHandler = new FileHandler("pos-log-file.txt", true); // true, allows append mode for the log
                                                                                 // file
            CustomFormatter formatter = new CustomFormatter();
            fileHandler.setFormatter(formatter);
            LOGGER.addHandler(fileHandler);
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

    public boolean isAuthenticated(Connection connection) {
        try {
            PosDatabase posDatabase = new PosDatabase();
            System.out.println("***************************************************************");
            System.out.println();
            System.out.println("                       SYSTECH POS SYSTEM                      ");
            System.out.println();
            System.out.println("***************************************************************");
            System.out.println();
            System.out.println("Choose an option below: \n1: Register \n2: Login");
            int programEntry = scanner.nextInt();
            switch (programEntry) {
                case 1:
                    return posDatabase.registerUser(connection);
                case 2:
                    return posDatabase.authenticateDatabaseUser(connection);
                default:
                    System.out.println("Please select only the above");
                    return false;

            }
        } catch (SQLException | InterruptedException e) {
            LOGGER.severe("Unable to authenticate the user: " + e.getMessage());
            return false;
        }
    }

    public void addItem(Connection connection) throws SQLException, CustomException {

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

        opp.checkIfNegative(itemPrice, itemQuantity);

        String insertItem = "insert into items (item_code,item_quantity,item_price)values(?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(insertItem);

        preparedStatement.setString(1, itemCode);
        preparedStatement.setInt(2, itemQuantity);
        preparedStatement.setDouble(3, itemPrice);
        int rowsAffected = preparedStatement.executeUpdate();
        LOGGER.info(rowsAffected + " item has been added ");
        noOfItems++;

    }

    public void makePayments(Connection connection) throws SQLException, CustomException {
        if (noOfItems == 0) {
            System.out.println("Please select an item then make payment.");
        } else {
            double change = 0.0;
            System.out.println("Enter the amount given by customer:");
            System.out.println();
            double payment = scanner.nextDouble();

            opp.checkIfNegative(payment);

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
                clearItems(connection);

            } else {
                LOGGER.warning("Insufficient funds");
            }

        }

    }

    public void displayReceipt(Connection connection) throws SQLException {
        if (noOfItems == 0) {
            System.out.println("Sorry no items have been selected");
        } else {
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

    public void clearItems(Connection connection) throws SQLException {
        // make the table empty
        Statement statement = connection.createStatement();
        String deleteItems = "DELETE FROM items;";
        statement.executeUpdate(deleteItems);
        noOfItems = 0;
        LOGGER.info("Items cleared successfully");

    }

}
