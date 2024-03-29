package com.systechafrica.librarysystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;

import com.systechafrica.commonoperations.Operations;
import com.systechafrica.exceptionhandling.CustomException;
import com.systechafrica.logging.LibraryLogging;
import com.systechafrica.possystemupdate.PosDatabase;
import com.systechafrica.userdatabeseauthentication.UserDatabaseOperation;

public class Library {
    private static final Logger LOGGER = Logger.getLogger(LibraryLogging.class.getName());

    Scanner scanner = new Scanner(System.in);
    Operations opp = new Operations();

    public static void main(String[] args) {
        LibraryLogging.logging();
        boolean isLogin;
        Library app = new Library();

        Connection connection = app.databaseConnection();
        UserDatabaseOperation users = new UserDatabaseOperation(LOGGER);
        users.createUsersTable(connection);

        isLogin = app.isAuthenticated(connection);

        if (isLogin) {
            System.out.println("Welcome ");
            boolean showMenu = true;
            while (showMenu) {
                app.mainMenu();
                try {
                    int option = app.scanner.nextInt();
                    app.scanner.nextLine();
                    System.out.println("LOADING...");
                    Thread.sleep(1500);

                    switch (option) {
                        case 1: // borrow book
                            boolean isBorrow = false;
                            app.borrowBook(connection);
                            do {

                                System.out.println("Borrow another book? \n 1: Yes    \n 2: No");
                                int choice = app.scanner.nextInt();
                                app.scanner.nextLine();
                                if (choice == 1) {
                                    isBorrow = true;
                                    app.borrowBook(connection);
                                } else if (choice == 2) {
                                    isBorrow = false;
                                } else {
                                    System.out.println("Please select either 1 or 2");
                                    isBorrow = true;

                                }

                            } while (isBorrow);
                            break;
                        case 2: // view borrowed books
                            System.out.println(
                                    "Select an option: \n 1: All Books Borrowed    \n 2: Books Borrowed by a student");
                            int selection = app.scanner.nextInt();
                            app.scanner.nextLine();
                            if (selection == 1) {
                                app.viewBorrowedBooks(connection);
                            } else if (selection == 2) {
                                app.viewBooksBorrowedByStudent(connection);
                            } else {
                                System.out.println("Please select either 1 or 2");
                            }

                            break;
                        case 3: // return book
                            app.returnBook(connection);
                            break;
                        case 4: // add books to library
                            boolean isRepeat = false;
                            app.addBook(connection);
                            do {

                                System.out.println("Add another item? \n 1: Yes    \n 2: No");
                                int choice = app.scanner.nextInt();
                                app.scanner.nextLine();
                                if (choice == 1) {
                                    isRepeat = true;
                                    app.addBook(connection);
                                } else if (choice == 2) {
                                    isRepeat = false;
                                } else {
                                    System.out.println("Please select either 1 or 2");
                                    isRepeat = true;

                                }

                            } while (isRepeat);
                            break;
                        case 5:
                            showMenu = false;
                            return;
                        default:
                            System.out.println("Please select from options above");
                    }
                } catch (InputMismatchException e) {
                    app.scanner.nextLine();
                    System.out.println("Please use numeric values");
                } catch (SQLException e) {
                    LOGGER.severe("Something wrong with your database connection: " + e.getMessage());
                } catch (InterruptedException e) {
                    LOGGER.severe("Oops! the program is interrupted: " + e.getMessage());
                } catch (NullPointerException e) {
                    LOGGER.severe("Sorry an item cannot be found: " + e.getMessage());
                } catch (CustomException e) {
                    LOGGER.severe((e.getMessage()));
                }
            }
        } else {
            System.out.println("You have exceeded your maximum attempts!");
        }

    }

    public void mainMenu() {
        System.out.println("***************************************************************");
        System.out.println();
        System.out.println("                       SYSTECH POS SYSTEM                      ");
        System.out.println();
        System.out.println("***************************************************************");
        System.out.println();
        System.out.println("1. Borrow a book");
        System.out.println("2. View borrowed books");
        System.out.println("3. Return a book");
        System.out.println("4. Add book to library");
        System.out.println("5. Quit");

        System.out.println();
        System.out.println("Choose an option: ");

    }

    private Connection databaseConnection() {

        try {
            String connectionUrl = "jdbc:mysql://localhost:3307/library";
            String user = "javase";
            String password = "javase";
            return DriverManager.getConnection(connectionUrl, user, password);
        } catch (SQLException e) {
            LOGGER.severe("Unable to connect to the database: " + e.getMessage());
            return null;
        }

    }

    public boolean isAuthenticated(Connection connection) {
        try {
            PosDatabase posDatabase = new PosDatabase();
            System.out.println("***************************************************************");
            System.out.println();
            System.out.println("                       SYSTECH LIBRARY SYSTEM                  ");
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

    public void addBook(Connection connection) throws SQLException {
        System.out.print("Enter book ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter book Title: ");
        String title = scanner.nextLine();

        Book book = new Book(isbn, title, true);
        book.insertBook(connection);

        LOGGER.info(" book has been added to database successfully ");
    }

    public void borrowBook(Connection connection) throws SQLException, CustomException, InterruptedException {

        System.out.print("Enter book ISBN to search: ");
        String isbn = scanner.next();
        scanner.nextLine();

        Book book = Book.findBook(connection, isbn);
        if (book != null && book.isAvailable()) {
            System.out.println("Searching...");
            Thread.sleep(1500);
            System.out.println("Book is available and can be borrowed");

            System.out.print("Enter student number: ");
            int studentNumber = scanner.nextInt();
            scanner.nextLine();
            opp.checkIfNegative(studentNumber);

            System.out.print("Enter book title: ");
            String bookTitle = scanner.nextLine();
            if (book.getBookTitle().toLowerCase().equals(bookTitle.toLowerCase())) {
                BorrowedBook borrowedBook = new BorrowedBook(isbn, studentNumber);
                borrowedBook.saveBook(connection);
                book.setAvailable(false);
                book.updateAvailability(connection, isbn);
                LOGGER.info("Book successfully borrowed!");
            } else {
                LOGGER.severe("Sorry the book title does not match with the ISBN, please try again");
            }

        } else {
            LOGGER.info("Sorry book is not available!");
        }

    }

    public void viewBorrowedBooks(Connection connection) throws SQLException {
        BorrowedBook[] borrowedBooks = BorrowedBook.getBorrowedBooks(connection);
        if (borrowedBooks.length > 0) {
            System.out.println("Borrowed Books");
            System.out.println("Student Registration Number\t\tBook ISBN\t\tBook Title\t\t");
            for (BorrowedBook bk : borrowedBooks) {
                Book book = Book.findBook(connection, bk.getIsbn());

                System.out
                        .println(bk.getStudentNumber() + "\t\t\t\t\t" + bk.getIsbn() + "\t\t\t" + book.getBookTitle());
            }
        } else {
            System.out.println("No books borrowed yet.");
        }

    }

    public void viewBooksBorrowedByStudent(Connection connection) throws SQLException, CustomException {
        System.out.print("Enter Student Registration Number: ");
        int registrationNumber = scanner.nextInt();
        opp.checkIfNegative(registrationNumber);

        BorrowedBook[] borrowedBooks = BorrowedBook.getBooksBorrowedByStudent(connection, registrationNumber);

        if (borrowedBooks.length > 0) {
            System.out.println("Books Borrowed");
            System.out.println("Student Registration Number\t\tBook ISBN\t\tBook Title\t\t");
            for (BorrowedBook bk : borrowedBooks) {
                Book book = Book.findBook(connection, bk.getIsbn());

                System.out
                        .println(bk.getStudentNumber() + "\t\t\t\t\t" + bk.getIsbn() + "\t\t\t" + book.getBookTitle());
            }
        } else {
            LOGGER.info("No books borrowed by that student.");
        }

    }

    public void returnBook(Connection connection) throws SQLException, InterruptedException {

        System.out.print("Enter book ISBN to search: ");
        String isbn = scanner.next();
        scanner.nextLine();

        Book book = Book.findBook(connection, isbn);
        if (book != null && !book.isAvailable()) {
            System.out.println("Searching...");
            Thread.sleep(1500);
            System.out.println("Book is borrowed");

            BorrowedBook borrowedBook = BorrowedBook.findBorrowedBook(connection, isbn);
            borrowedBook.deleteBorrowedBook(connection, isbn);
            book.setAvailable(true);
            book.updateAvailability(connection, isbn);
            LOGGER.info("Book successfully returned!");

        } else {
            LOGGER.info("Book not found or not borrowed");
        }

    }

}
