package com.systechafrica.librarysystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class Book {
    private int bookId;
    private String bookTitle;
    private String author;
    private boolean isAvailable;
    private static final Logger LOGGER = Logger.getLogger(Book.class.getName());

    public Book(int bookId, String bookTitle, String author, boolean isAvailable) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.author = author;
        this.isAvailable = isAvailable;
    }

    public Book(String bookTitle, String author, boolean isAvailable) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.isAvailable = isAvailable;
    }

    public Book() {
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void insertBook(Connection connection) throws SQLException {
        // initialise books table
        String createBooksTable = "CREATE TABLE IF NOT EXISTS books (book_id INT AUTO_INCREMENT PRIMARY KEY,book_title VARCHAR(255) NOT NULL,author VARCHAR(255),is_available BOOLEAN)  ENGINE=INNODB;";
        Statement statement = connection.createStatement();
        int tableStatus = statement.executeUpdate(createBooksTable);
        if (tableStatus != 0) {
            LOGGER.info("table created and status is: " + tableStatus);

        }
        // insert books
        String insertBooks = "insert into books (book_title,author,is_available)values(?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(insertBooks);

        preparedStatement.setString(1, bookTitle);
        preparedStatement.setString(2, author);
        preparedStatement.setBoolean(3, isAvailable);
        int rowsAffected = preparedStatement.executeUpdate();
        

    }
}
