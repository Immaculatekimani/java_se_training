package com.systechafrica.AtmMachine;

import java.util.Scanner;

public class atmMachine {
    private static final String DB_PASSWORD = "Admin123";
    private static final double INITIAL_BAL = 1000.00;
    private static final double WITHDRAW_PERCENT = 0.02;
    private static double balance = INITIAL_BAL;
    static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        atmMachine app = new atmMachine();

        int trials = 0;
        String userPassword;
        String userName;

        while (trials < 3) {
            System.out.print("Please enter you username: ");
            userName = scanner.nextLine();
            System.out.print("Please enter your password: ");
            userPassword = scanner.nextLine();
            if (userPassword.equals(DB_PASSWORD)) {
                app.atmMenu();
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

    public void atmMenu() {
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
            switch (option) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    atmDeposit();
                    break;
                case 3:
                    atmWithdraw();;
                    break;
                case 4:
                    System.out.println("Make a transfer");
                    break;
                case 5:
                    System.out.println("Sad to let you go");
                    return;
                default:
                    System.out.println("Please enter a valid option");
            }
        }

    }

    public static void checkBalance() {
        System.out.println("Your balance is Ksh: " + balance);
    }

    public static void atmDeposit(){
        System.out.println("Enter amount to deposit");
        double userDeposit = scanner.nextDouble();
        balance+= userDeposit;
        System.out.println("Success! Your balance is now: "+ balance);

    }
    public static void atmWithdraw(){
        System.out.println("Please enter the amount to withdraw");
        double withdrawAmount = scanner.nextDouble();
        double withdrawCharge = withdrawAmount * WITHDRAW_PERCENT;
        double withdrawable = withdrawAmount + withdrawCharge;
        if(withdrawable > balance){
            System.out.println("Sorry insufficient funds to withdraw Ksh: " + withdrawAmount);
        }else if(withdrawAmount < 0){
            System.out.println("Please enter a positive value");
        }else{
            balance -= withdrawable ;
            System.out.println("Withdrawal of Ksh "+withdrawAmount+" successful new balance: " + balance+ "charges: "+ withdrawCharge);
        }
        
    }

}
