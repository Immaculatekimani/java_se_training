package com.systechafrica.possystemupdate;

import java.util.Scanner;

import com.systechafrica.commonoperations.Operations;

public class Pos {
    Scanner scanner = new Scanner(System.in);
    final int MAX_ITEMS = 100;
    int noOfItems = 0;
    Item[] items = new Item[MAX_ITEMS];
    double totalAmount = 0.0;

    public static void main(String[] args) throws InterruptedException {
        Operations opp = new Operations();
        boolean isLogin = opp.login();
        Pos app = new Pos();
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
                            app.addItem();
                            do {

                                System.out.println("Add another item? \n 1: Yes    \n 2: No");
                                int choice = app.scanner.nextInt();
                                if (choice == 1) {
                                    isRepeat = true;
                                    app.addItem();
                                } else if (choice == 2) {
                                    isRepeat = false;
                                } else {
                                    System.out.println("Please select either 1 or 2");
                                    isRepeat = true;

                                }

                            } while (isRepeat);
                            break;
                        case 2:
                            app.displayReceipt();
                            app.makePayments();
                            break;
                        case 3:
                            app.displayReceipt();
                            break;
                        case 4:
                            showMenu = false;
                            return;
                        default:
                            System.out.println("Please select from options above");
                    }
                } catch (Exception e) {
                    app.scanner.nextLine();
                    System.out.println("Please use numeric values");
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
        System.out.println("1. ADD ITEM");
        System.out.println("2. MAKE PAYMENT");
        System.out.println("3. DISPLAY RECEIPT");
        System.out.println("4. QUIT");
        System.out.println();
        System.out.println("Choose an option: ");

    }

    public void addItem() {

        System.out.print("Enter item unit code: ");
        String itemCode = scanner.next();
        System.out.print("Enter item quantity: ");
        int itemQuantity = scanner.nextInt();
        System.out.print("Enter item price: ");
        double itemPrice = scanner.nextDouble();

        Item newItem = new Item(itemCode, itemQuantity, itemPrice);

        items[noOfItems] = newItem; // add the item to the array
        noOfItems++;

    }

    public void makePayments() {
        if (noOfItems == 0) {
            System.out.println("Please select an item then make payment.");
        } else {
            double change = 0.0;
            System.out.println("Enter the amount given by customer:");
            System.out.println();
            double payment = scanner.nextDouble();
            if (payment >= totalAmount) {
                change = payment - totalAmount;
                totalAmount = 0;
                noOfItems = 0; // make the list empty after pay
                System.out.print("Change:   " + change);
                System.out.println();
                System.out.println("***************************************************************");
                System.out.println();
                System.out.println("THANK YOU FOR SHOPPING WITH US \n");
                System.out.println("***************************************************************");

            } else {
                System.out.println("Insufficient Funds!");
            }

        }

    }

    public void displayReceipt() {
        if (noOfItems == 0) {
            System.out.println("Sorry no items have been selected!");
        } else {
            System.out.println("Item code \t item quantity \t unit price \t Total Value");
            totalAmount = 0;
            for (int i = 0; i < noOfItems; i++) {
                Item item = items[i];
                double totalValue = item.getQuantity() * item.getPrice();
                System.out.println(item.getItemCode() + "\t\t  " + item.getQuantity() + "\t\t  " + item.getPrice()
                        + "\t\t  " + totalValue);
                totalAmount += totalValue;
            }

            System.out.println("***************************************************************");
            System.out.print("Total: " + totalAmount);
            System.out.println();
            System.out.println("***************************************************************");

        }

    }

}
