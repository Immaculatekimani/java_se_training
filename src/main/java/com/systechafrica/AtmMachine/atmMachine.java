package com.systechafrica.AtmMachine;

import java.util.Scanner;

public class atmMachine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        atmMachine app = new atmMachine();

        final String DB_PASSWORD = "Admin123";
        final double INITIAL_BAL = 1000.00;
        int trials = 0;
        String userPassword;
        String userName;

        
        while(trials < 3){
            System.out.print("Please enter you username: ");
            userName = scanner.nextLine();
            System.out.print("Please enter your password: ");
            userPassword = scanner.nextLine();
            if(userPassword.equals(DB_PASSWORD)){
                app.atmMenu();
                break;
            }else{
                trials++;
                if (trials < 3){
                    System.out.println("Wrong username or password," );
                }else{
                    System.out.println("You have exhausted your login attempts");
                }
                
            }
            
            
        }
        
        scanner.close();
    }
    public void atmMenu(){
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
    }
    
}
