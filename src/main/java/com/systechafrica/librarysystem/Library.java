package com.systechafrica.librarysystem;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;

import com.systechafrica.commonoperations.Operations;

public class Library {
    private static final Logger LOGGER = Logger.getLogger(Library.class.getName());

    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Operations opp = new Operations();
        boolean isLogin;
        try {
            isLogin = opp.login();
            // !create db object
            Library app = new Library();
            // !do file logging

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
                                // !borrow book
                                do {

                                    System.out.println("Add another item? \n 1: Yes    \n 2: No");
                                    int choice = app.scanner.nextInt();
                                    if (choice == 1) {
                                        isRepeat = true;
                                        // !borrow book
                                    } else if (choice == 2) {
                                        isRepeat = false;
                                    } else {
                                        System.out.println("Please select either 1 or 2");
                                        isRepeat = true;

                                    }

                                } while (isRepeat);
                                break;
                            case 2:
                                // !view borrowed book
                                break;
                            case 3:
                                // ! return book
                            case 4:
                                // !add book to library
                            case 5:
                                showMenu = false;
                                return;
                            default:
                                System.out.println("Please select from options above");
                        }
                    } catch (InputMismatchException e) {
                        // ! db.scanner.nextLine();
                        System.out.println("Please use numeric values");
                    }
                }
            } else {
                System.out.println("You have exceeded your maximum attempts!");
            }

        } catch (Exception e) {
            // !impliment custom exceptions
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
}
