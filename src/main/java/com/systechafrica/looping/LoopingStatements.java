package com.systechafrica.looping;

import java.util.logging.Logger;

public class LoopingStatements {
    private static final Logger LOGGER = Logger.getLogger(LoopingStatements.class.getName());

    public void forLoop() {
        // initialize condition update
        for (int counter = 0; counter <= 10; counter = counter + 1) {
            LOGGER.info("Count = " + counter);
        }
        for (int counter = 0; counter <= 10; counter += 1) {
            LOGGER.info("Count = " + counter);
        }
        for (int counter = 0; counter <= 10; counter++) {
            LOGGER.info("Count = " + counter);
        }
    }

    public void whileLoop() {
        int studentCount = 0;
        while (studentCount < 10) {
            LOGGER.info("Student Count = " + studentCount);
            studentCount++; // studentCount = studentCount + 1
        }

    }

    public static void main(String[] args) {
        LoopingStatements app = new LoopingStatements();
        // app.forLoop();
        app.whileLoop();

    }
}
