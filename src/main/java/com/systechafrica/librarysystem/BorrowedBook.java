package com.systechafrica.librarysystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BorrowedBook {
    private int id;
    private String isbn;
    private int studentNumber;
    // private String bookTitle;
    private static final Logger LOGGER = Logger.getLogger(Book.class.getName());

    public BorrowedBook(String isbn, int studentNumber) {
        this.isbn = isbn;
        this.studentNumber = studentNumber;
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

    public void saveBook(Connection connection) throws SQLException {
        String createBorrowedBooksTable = "CREATE TABLE IF NOT EXISTS borrowed_books (book_id INT AUTO_INCREMENT PRIMARY KEY,isbn VARCHAR(255) UNIQUE NOT NULL,student_number INT NOT NULL)  ENGINE=INNODB;";
        Statement statement = connection.createStatement();
        int status = statement.executeUpdate(createBorrowedBooksTable);
        LOGGER.info("Borrowed books table created. Status: " + status);

        String insertBorrowedBook = "insert into borrowed_books(isbn,student_number)values(?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(insertBorrowedBook);
        preparedStatement.setString(1, isbn);
        preparedStatement.setInt(2, studentNumber);
        // preparedStatement.setString(3, bookTitle);
        preparedStatement.executeUpdate();

    }

    public static BorrowedBook[] getBorrowedBooks(Connection connection) throws SQLException {
        String getBorrowedBooks = "SELECT * FROM borrowed_books;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(getBorrowedBooks);

        List<BorrowedBook> borrowedBooks = new ArrayList<>();

        while (resultSet.next()) {
            int studentNumber = resultSet.getInt("student_number");
            String isbn = resultSet.getString("isbn");
            // String bookTitle = resultSet.getString("book_title");

            BorrowedBook borrowedBook = new BorrowedBook(isbn, studentNumber);
            borrowedBooks.add(borrowedBook);

        }
        return borrowedBooks.toArray(new BorrowedBook[0]);
    }

    public static BorrowedBook[] getBooksBorrowedByStudent(Connection connection, int registrationNumber) throws SQLException {
        String getBorrowedBooks = "SELECT bb.* FROM borrowed_books bb "+"JOIN books b on bb.isbn = b.isbn "+"WHERE student_number = ? ";
        PreparedStatement preparedStatement = connection.prepareStatement(getBorrowedBooks);
        preparedStatement.setInt(1, registrationNumber);
        ResultSet resultSet = preparedStatement.executeQuery();
        
        List<BorrowedBook> borrowedBooks = new ArrayList<>();

        while (resultSet.next()) {
            int studentNumber = resultSet.getInt("student_number");
            String isbn = resultSet.getString("isbn");
            // String bookTitle = resultSet.getString("book_title");

            BorrowedBook borrowedBook = new BorrowedBook(isbn, studentNumber);
            borrowedBooks.add(borrowedBook);

        }
        return borrowedBooks.toArray(new BorrowedBook[0]);
    }

}
