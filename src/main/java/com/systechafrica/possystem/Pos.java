package com.systechafrica.possystem;

import java.util.Scanner;

import com.systechafrica.commonoperations.Operations;

public class Pos {
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws InterruptedException {
        Operations opp = new Operations();
        boolean isLogin = opp.login();
        Pos app = new Pos();
        if (isLogin) {
            System.out.println("Welcome " + opp.userName.toUpperCase() + "!!!");
            while (true) {
                app.mainMenu();
                try {
                    int option = app.scanner.nextInt();
                    System.out.println("LOADING...");
                    Thread.sleep(1500);

                    switch (option) {
                        case 1:
                            System.out.println("add");
                            break;
                        case 2:
                            System.out.println("pay");
                            break;
                        case 3:
                            System.out.println("receipt");
                            break;
                        case 4:
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

}
