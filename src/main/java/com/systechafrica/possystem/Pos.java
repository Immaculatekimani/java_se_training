package com.systechafrica.possystem;

import com.systechafrica.commonoperations.Operations;

public class Pos {
    public static void main(String[] args) throws InterruptedException {
        Operations opp = new Operations();
        boolean isLogin = opp.login();
        if (isLogin){
            System.out.println("Welcome " + opp.userName.toUpperCase()+"!!!" );
        }else{
            System.out.println("You have exceeded your maximum attempts!");
        }
        
    }
    
}
