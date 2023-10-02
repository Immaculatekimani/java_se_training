package com.systechafrica.librarysystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class BorrowedBook {
    private int id;
    private String isbn;
    private int studentNumber;
    private String bookTitle;
    private static final Logger LOGGER = Logger.getLogger(Book.class.getName());

    public BorrowedBook(String isbn, int studentNumber, String bookTitle) {
        this.isbn = isbn;
        this.studentNumber = studentNumber;
        this.bookTitle = bookTitle;
    }

    public BorrowedBook() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void saveBook(Connection connection) throws SQLException {
        String createBorrowedBooksTable = "CREATE TABLE IF NOT EXISTS borrowed_books (book_id INT AUTO_INCREMENT PRIMARY KEY,isbn VARCHAR(255) UNIQUE NOT NULL,student_number INT NOT NULL, book_title VARCHAR(255) NOT NULL)  ENGINE=INNODB;";
        Statement statement = connection.createStatement();
        int status = statement.executeUpdate(createBorrowedBooksTable);
        LOGGER.info("Borrowed books table created. Status: " + status);

        String insertBorrowedBook = "insert into borrowed_books(isbn,student_number,book_title)values(?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(insertBorrowedBook);
        preparedStatement.setString(1, isbn);
        preparedStatement.setInt(2, studentNumber);
        preparedStatement.setString(3, bookTitle);
        preparedStatement.executeUpdate();

    }

}
