package com.systechafrica.possystemupdate;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Logger;

import com.systechafrica.exceptionhandling.CustomException;

public class Pos {
    private static final Logger LOGGER = Logger.getLogger(PosDatabase.class.getName()); // to get the file handler and
                                                                                        // customization

    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        PosDatabase db = new PosDatabase();

        boolean isLogin;
        PosDatabase.fileLogging();

        try {
            db.createUsersTable();
            isLogin = db.authenticateDatabaseUser();
            Pos app = new Pos();

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
                                db.addItem();
                                do {

                                    System.out.println("Add another item? \n 1: Yes    \n 2: No");
                                    int choice = app.scanner.nextInt();
                                    if (choice == 1) {
                                        isRepeat = true;
                                        db.addItem();
                                    } else if (choice == 2) {
                                        isRepeat = false;
                                    } else {
                                        System.out.println("Please select either 1 or 2");
                                        isRepeat = true;

                                    }

                                } while (isRepeat);
                                break;
                            case 2:
                                db.displayReceipt();
                                db.makePayments();
                                break;
                            case 3:
                                db.displayReceipt();
                                break;
                            case 4:
                                db.clearItems();
                                showMenu = false;
                                return;
                            default:
                                System.out.println("Please select from options above");
                        }
                    } catch (InputMismatchException e) {
                        db.scanner.nextLine();
                        System.out.println("Please use numeric values");
                    } catch (SQLException e) {
                        LOGGER.severe("Something wrong with your database operation: " + e.getMessage());
                    } catch (CustomException e) {
                        LOGGER.severe(e.getMessage());
                    }
                }
            } else {
                System.out.println("You have exceeded your maximum attempts!");
            }

        } catch (InterruptedException e) {
            System.out.println("Ooops! interrupted exception: " + e.getMessage());
        } catch (SecurityException e) {
            LOGGER.severe("Unable to obtain security permissions for the log file: " + e.getMessage());
        } catch (SQLException e1) {
            LOGGER.severe("Sorry, database operation failed: " + e1.getMessage());
        } catch (NullPointerException e){
            LOGGER.severe("You are referring to a non existing value: "+e.getMessage());
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

}
