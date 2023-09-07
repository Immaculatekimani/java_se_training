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
            do {
                // menu displays here
                System.out.println("Add another item? \n 1: Yes    \n 2: No");
                int choice = app.scanner.nextInt();
                if (choice == 1) {
                    isRepeat = true;
                } else if (choice == 2) {
                    System.out.println("Proceed to payment? \n 1: Yes    \n 2: No");
                    if (choice == 1) {
                        // payment process
                    } else if (choice == 2) {
                        // show the menu;
                    }
                    isRepeat = false;
                }

            } while (isRepeat);
        } else {
            System.out.println("You have exceeded your maximum attempts!");
        }

    }
}
