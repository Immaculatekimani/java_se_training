package com.systechafrica.commonoperations;

import java.util.Scanner;

public class Operations {

    Scanner scanner = new Scanner(System.in);
    final String DEFAULT_PASSWORD = "Admin123";
    public String userName;
    public static void main(String[] args) throws InterruptedException {

        Operations opp = new Operations();
        opp.login();
    }
    public boolean login() throws InterruptedException{
        int trials = 0;
        boolean loggedIn = false;
    
        while(trials < 3){
            System.out.print("Please enter your username: ");
            userName = scanner.nextLine();
            System.out.print("Please enter your password: ");
            String userPassword = scanner.nextLine();
            System.out.println("Loading...");
            Thread.sleep(1500);
            if(userPassword.equals(DEFAULT_PASSWORD)){
                loggedIn = true;
                break;
            }
            System.out.println("Wrong Password! Try again");
            trials++;
        }
        return loggedIn;
    }

}


