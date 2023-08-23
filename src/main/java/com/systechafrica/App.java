package com.systechafrica;

import java.util.logging.Logger;

public class App 
{
    private static final Logger LOGGER = Logger.getLogger(App.class.getName());
    public static void main( String[] args )
    {
        LOGGER.info( "Hello World!" );
    }
    
    public int add(int a, int b){
        return a + b;
    }
}
