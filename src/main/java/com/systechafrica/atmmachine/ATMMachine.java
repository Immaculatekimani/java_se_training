package com.systechafrica.atmmachine;

import java.util.Scanner;

public class ATMMachine {
    private static final String DB_PASSWORD = "Admin123";
    private static final double INITIAL_BAL = 1000.00;
    private static final double WITHDRAW_PERCENT = 0.02;
    private static double balance = INITIAL_BAL;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {
        int trials = 0;
        String userPassword;
        String userName;

        while (trials < 3) {
            System.out.print("Please enter you username: ");
            userName = scanner.nextLine();
            System.out.print("Please enter your password: ");
            userPassword = scanner.nextLine();
            if (userPassword.equals(DB_PASSWORD)) {
                System.out.println("WELCOME " + userName.toUpperCase());
                Thread.sleep(2000);
                atmMenu();
                break;
            } else {
                trials++;
                if (trials < 3) {
                    System.out.println("Wrong username or password,");
                } else {
                    System.out.println("You have exhausted your login attempts");
                }

            }

        }

        scanner.close();
    }

    public static void atmMenu() throws InterruptedException {
        while (true) {
            // the main display area
            System.out.println("***************************************************************");
            System.out.println();
            System.out.println("                       ATM SIMULATOR                           ");
            System.out.println();
            System.out.println("***************************************************************");
            System.out.println();
            System.out.println("                       ATM SERVICES                            ");
            System.out.println("_______________________________________________________________");
            System.out.println();
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer Cash");
            System.out.println("5. Quit");
            System.out.println("***************************************************************");
            System.out.println("Choose your option: ");
            // Dealing with options
            int option = scanner.nextInt();
            System.out.println("LOADING...");
            Thread.sleep(1500);
            switch (option) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    atmDeposit();
                    break;
                case 3:
                    atmWithdraw();
                    break;
                case 4:
                    atmTransfer();
                    break;
                case 5:
                    System.out.println("Thank you for banking with us!");
                    return;
                default:
                    System.out.println("Please enter a valid option");
            }
            Thread.sleep(2000);
        }

    }

    public static void checkBalance() {
        System.out.println("DATE: " + java.time.LocalDate.now());
        System.out.println("TIME: " + java.time.LocalTime.now());
        System.out.println("ACCOUNT BALANCE: " + balance);
    }

    public static void atmDeposit() throws InterruptedException {
        System.out.println("Enter amount to deposit");
        double userDeposit = scanner.nextDouble();
        System.out.println("LOADING...");
        Thread.sleep(1500);
        balance += userDeposit;
        System.out.println("Success! Your balance is now: " + balance);

    }

    public static void atmWithdraw() throws InterruptedException {
        System.out.print("Please enter the amount to withdraw: ");
        double withdrawAmount = scanner.nextDouble();
        double withdrawCharge = withdrawAmount * WITHDRAW_PERCENT;
        double withdrawable = withdrawAmount + withdrawCharge;
        System.out.println("LOADING...");
        Thread.sleep(1500);
        if (withdrawable > balance) {
            System.out.println("Sorry insufficient funds to withdraw Ksh: " + withdrawAmount);
        } else if (withdrawAmount < 0) {
            System.out.println("Please enter a positive value");
        } else {
            balance -= withdrawable;
            System.out.println("Withdrawal of Ksh " + withdrawAmount + " successful new balance: " + balance
                    + " charges: " + withdrawCharge);
        }

    }

    public static void atmTransfer() throws InterruptedException {
        System.out.print("Please enter recepient account: ");
        String recepientAccount = scanner.next();
        System.out.print("Please enter the amount you want to transfer: ");
        double transferAmount = scanner.nextDouble();
        System.out.println("LOADING...");
        Thread.sleep(1500);
        if (transferAmount > balance) {
            System.out.println("Sorry insufficient funds to transfer Ksh: " + transferAmount);
        } else if (transferAmount < 0) {
            System.out.println("Please enter a positive value");
        } else {

            balance -= transferAmount;
            System.out.println("Transer of Ksh " + transferAmount + " to account: " + recepientAccount
                    + " successful new balance: " + balance);
        }
    }

}
