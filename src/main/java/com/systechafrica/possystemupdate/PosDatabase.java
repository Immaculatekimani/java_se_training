package com.systechafrica.possystemupdate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Logger;

public class PosDatabase {
    private static final Logger LOGGER = Logger.getLogger(Pos.class.getName());
    Scanner scanner = new Scanner(System.in);

    // connect to the database
    public Connection databaseConnection() throws SQLException {
        String connectionUrl = "jdbc:mysql://localhost:3309/pos";
        String user = "javase";
        String password = "javase";
        return DriverManager.getConnection(connectionUrl, user, password);

    }

    // WORKING WITH USERS

    public void createUsersTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String createTable = "CREATE TABLE IF NOT EXISTS users (user_id INT AUTO_INCREMENT PRIMARY KEY,user_name VARCHAR(255) UNIQUE,user_password VARCHAR(255) NOT NULL)  ENGINE=INNODB;";
        int tableStatus = statement.executeUpdate(createTable);
        LOGGER.info("Users table has been created, status: " + tableStatus);
        boolean defaultUserExists = checkDefaultUserExists(connection);

        if (!defaultUserExists) {

            String insertUser = "INSERT INTO users(user_name,user_password)values('Admin', 'Admin123');";
            PreparedStatement preparedStatement = connection.prepareStatement(insertUser);
            preparedStatement.executeUpdate();
            LOGGER.info("Default user Admin has been added");
        }

    }

    public boolean checkDefaultUserExists(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        String adminUser = "select count(*) from users where user_name = 'Admin';";
        ResultSet results = statement.executeQuery(adminUser);
        return results.next() && results.getInt(1) > 0; // checks if the first column of count row is one
    }

    // register user
    public boolean registerUser(Connection connection) throws SQLException, InterruptedException {
        System.out.println("Please enter your username;");
        String userName = scanner.nextLine();
        System.out.println("Please enter your password");
        String password = scanner.next();
        scanner.nextLine();

        String createUser = "insert into users(user_name, user_password)values(?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(createUser);
        preparedStatement.setString(1, userName);
        preparedStatement.setString(2, password);
        preparedStatement.executeUpdate();

        User addedUser = getUserByName(userName, connection);
        if (addedUser != null) {
            System.out.println("You have been added successfully and are being redirected to login");
            Thread.sleep(1500);
            return authenticateDatabaseUser(connection);
        } else {
            System.out.println("Sorry your data does not exist please try again later");
            return false;
        }

    }

    // find user
    public User getUserByName(String username, Connection connection) throws SQLException {
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

    // authenticate user
    public boolean authenticateDatabaseUser(Connection connection) throws SQLException, InterruptedException {
        int trials = 0;
        boolean loggedIn = false;
        while (trials < 3) {
            System.out.print("Please enter your username: ");
            String userName = scanner.nextLine();
            System.out.print("Please enter your password: ");
            String userPassword = scanner.next();
            scanner.nextLine();
            System.out.println("Loading...");
            Thread.sleep(1500);

            User userToCheck = getUserByName(userName, connection);

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

    // CLOSING RESOURCES METHOD OVERLOAD
    public void closeResources(Connection connection) {
        try {
            connection.close();
            LOGGER.info("Resources cleared successfully!");
        } catch (SQLException e) {
            LOGGER.severe("Ooops! An error occurred while releasing resources: " + e.getMessage());
        }
    }

    public void closeResources(Connection connection, Statement statement, PreparedStatement preparedStatement,
            ResultSet resultSet) {
        try {
            resultSet.close();
            preparedStatement.close();
            statement.close();
            connection.close();
            LOGGER.info("Resources cleared successfully!");
        } catch (SQLException e) {
            LOGGER.severe("Ooops! An error occurred while releasing resources: " + e.getMessage());
        }
    }

    public void closeResources(Connection connection, Statement statement) {
        try {
            statement.close();
            connection.close();
            LOGGER.info("Resources cleared successfully!");
        } catch (SQLException e) {
            LOGGER.severe("Ooops! An error occurred while releasing resources: " + e.getMessage());
        }
    }

    public void closeResources(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            resultSet.close();
            statement.close();
            connection.close();
            LOGGER.info("Resources cleared successfully!");
        } catch (SQLException e) {
            LOGGER.severe("Ooops! An error occurred while releasing resources: " + e.getMessage());
        }
    }

}
