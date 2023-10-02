package com.systechafrica.librarysystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class Book {
    private int bookId;
    private String bookTitle;
    private String isbn;
    private boolean isAvailable;
    private static final Logger LOGGER = Logger.getLogger(Book.class.getName());

    public Book(int bookId, String isbn, String bookTitle, boolean isAvailable) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.isbn = isbn;
        this.isAvailable = isAvailable;
    }

    public Book(String isbn, String bookTitle, boolean isAvailable) {
        this.bookTitle = bookTitle;
        this.isbn = isbn;
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

    public String getisbn() {
        return isbn;
    }

    public void setisbn(String isbn) {
        this.isbn = isbn;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void insertBook(Connection connection) throws SQLException {
        // initialise books table
        String createBooksTable = "CREATE TABLE IF NOT EXISTS books (book_id INT AUTO_INCREMENT PRIMARY KEY,isbn VARCHAR(255) UNIQUE NOT NULL,book_title VARCHAR(255) NOT NULL,is_available BOOLEAN)  ENGINE=INNODB;";
        Statement statement = connection.createStatement();
        int tableStatus = statement.executeUpdate(createBooksTable);
        if (tableStatus != 0) {
            LOGGER.info("table created and status is: " + tableStatus);

        }
        // insert books
        String insertBooks = "insert into books (isbn,book_title,is_available)values(?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(insertBooks);

        preparedStatement.setString(1, isbn);
        preparedStatement.setString(2, bookTitle);
        preparedStatement.setBoolean(3, isAvailable);
        int rowsAffected = preparedStatement.executeUpdate();
        LOGGER.info("books has been added");

    }

    public static Book findBook(Connection connection, String isbn) throws SQLException {
        String findBook = "select * from books where isbn = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(findBook);
        preparedStatement.setString(1, isbn);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String foundIsbn = resultSet.getString("isbn");
            String foundBookTitle = resultSet.getString("book_title");
            Boolean foundAvailability = resultSet.getBoolean("is_available");

            Book foundBook = new Book(foundIsbn, foundBookTitle, foundAvailability);
            return foundBook;

        }
        return null;

    }

    public void updateAvailability(Connection connection, String isbn) throws SQLException {
        String bookUpdate = "UPDATE books SET is_available = ? WHERE isbn = ?;";
        PreparedStatement preparedStatement = connection.prepareStatement(bookUpdate);
        preparedStatement.setBoolean(1, isAvailable);
        preparedStatement.setString(2, isbn);
        int updateStatus = preparedStatement.executeUpdate();
        LOGGER.info("Book updated, status: " + updateStatus);
    }
}
