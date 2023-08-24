package com.systechafrica;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

public class AppTest 
{
    App app = new App();

    @Test
    void add(){
        //when
        int results =  app.add(2,3);
        //then
        int expected = 5;
        //verify the expected = results
        Assertions.assertEquals(expected, results, "the sum of 2 + 3 should be 5");

    }
}
