package com.systechafrica.AtmMachine;

import java.util.Scanner;

public class atmMachine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
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
                System.out.println("Welcome to Tatu's Atm!");
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
        

    }
    
}
