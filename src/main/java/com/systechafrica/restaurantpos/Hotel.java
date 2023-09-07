package com.systechafrica.restaurantpos;

import java.util.Scanner;

import com.systechafrica.commonoperations.Operations;

public class Hotel {
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {
        Operations opp = new Operations();
        boolean isLogin = opp.login();
        Hotel app = new Hotel();
        if (isLogin) {
            System.out.println("Welcome " + opp.userName.toUpperCase());
            boolean isRepeat = false;
            MenuItem chai = new MenuItem(1, "CHAI", 15.0);
            MenuItem andazi = new MenuItem(2, "ANDAZI", 10.0);
            MenuItem tosti = new MenuItem(3, "TOSTI", 12.0);
            MenuItem ndengu = new MenuItem(4, "NDENGU AND ACCOMPLISHMENTS", 70.0);
            MenuItem beans = new MenuItem(5, "BEANS AND ACCOMPLISHMENTS", 70.0);
            MenuItem pilau = new MenuItem(6, "PILAU VEG", 90.0);

            Order order = new Order();

            do {
                // menu displays here
                app.displayMenu();
                try {
                    int choice = app.scanner.nextInt();
                    switch (choice) {
                        case 1:
                            order.addItem(chai);
                            break;
                        case 2:
                            order.addItem(andazi);
                            break;
                        case 3:
                            order.addItem(tosti);
                            break;
                        case 4:
                            order.addItem(ndengu);
                            break;
                        case 5:
                            order.addItem(beans);
                            break;
                        case 6:
                            order.addItem(pilau);
                            break;
                        case 7:
                            return;
                        default:
                            System.out.println("Please enter a valid option");

                    }
                    System.out.println("Add another item? \n 1: Y    \n 2: N");
                    int repeat = app.scanner.nextInt();
                    if (repeat == 1) {
                        isRepeat = true;
                    } else if (repeat == 2) {
                        System.out.println("Proceed to payment? \n 1: Yes    \n 2: No");
                        int makePay = app.scanner.nextInt();
                        if (makePay == 1) {
                            // payment process
                            isRepeat = false;
                        } else if (makePay == 2) {
                            // show the menu;
                            isRepeat = true;
                        }
                        
                    }

                } catch (Exception e) {
                    app.scanner.nextLine();
                    System.out.println("Only numeric values accepted");
                    isRepeat = true;
                }
            } while (isRepeat);
        } else {
            System.out.println("You have exceeded your maximum attempts!");
        }

    }

    public void displayMenu() {
        System.out.println("***************************************************************");
        System.out.println();
        System.out.println("                       SYSTECH RESTAURANT                      \n\n");

        System.out.println("DRINKS \n");
        System.out.println("***************************************************************");
        System.out.println("1. CHAI------------------------------------------------------15\n");
        System.out.println("2. ANDAZI----------------------------------------------------10\n");
        System.out.println("3. TOSTI-----------------------------------------------------12\n");
        System.out.println("MEALS \n");
        System.out.println("***************************************************************");
        System.out.println("4. NDENGU AND ACCOMPLISHMENTS--------------------------------70\n");
        System.out.println("5. BEANS AND ACCOMPLISHMENTS---------------------------------70\n");
        System.out.println("6. PILAU VEG-------------------------------------------------90\n");
        System.out.println("7. QUIT");
        System.out.println("Choose an option: ");
    }
}
