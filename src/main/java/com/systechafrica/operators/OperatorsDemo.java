package com.systechafrica.operators;

import java.util.logging.Logger;

public class OperatorsDemo {
    private static final Logger LOGGER = Logger.getLogger(OperatorsDemo.class.getName());

    public void arithmeticOperators() {

        int studentOneAge = 22;
        int studentTwoAge = 19;

        // Addition operator
        int totalClassAge = studentOneAge + studentTwoAge;
        LOGGER.info("Total class Age: " + totalClassAge);

        // subtraction
        int ageDifference = studentOneAge - studentTwoAge;
        LOGGER.info("Age difference: " + ageDifference);

        // product of class ages => multiplication
        int ageProduct = studentOneAge * studentTwoAge;
        LOGGER.info("Age product: " + ageProduct);

        // division operator
        // ? division between integers result to an integer value otherwise double value
        int divisionResult = studentOneAge / studentTwoAge;
        LOGGER.info("Division: " + divisionResult);

        // ! To ensure correct values with precision
        double divisionResultInDouble = (double) studentOneAge / studentTwoAge;
        LOGGER.info("Division with precision: " + divisionResultInDouble);

        // modulus operator => remainder of division result
        int remainder = studentOneAge % studentTwoAge;
        LOGGER.info("Remainder: " + remainder);

    }

    public void assignmentOperators() {
        // simple assignment operator
        int quantity = 35;

        // addition assignment operator
        quantity += 10; // > quantity = quantity + 10;
        LOGGER.info("Quantity: " + quantity);

        // multiplication assignment operator
        quantity *= 100; // > quantity = quantity * 100;
        LOGGER.info("Quantity: " + quantity);
        // multiplication assignment operator
        quantity /= 20; // > quantity = quantity / 20;
        LOGGER.info("Quantity: " + quantity);
    }

    public void comparisonOperators() {
        int studentOneAge = 20;
        int studentTwoAge = 25;

        // equality operator
        boolean isStudentOneAgeEqualToStudentTwoAge = studentOneAge == studentTwoAge;
        LOGGER.info("isStudentOneAgeEqualToStudentTwoAge: " + isStudentOneAgeEqualToStudentTwoAge);

        // Inequality operator
        boolean isStudentOneAgeNotEqualToStudentTwoAge = studentOneAge != studentTwoAge;
        LOGGER.info("isStudentOneAgeNotEqualToStudentTwoAge: " + isStudentOneAgeNotEqualToStudentTwoAge);

        // less than operator
        boolean isStudentOneAgeLessThanStudentTwoAge = studentOneAge < studentTwoAge;
        LOGGER.info("isStudentOneAgeLessThanStudentTwoAge: " + isStudentOneAgeLessThanStudentTwoAge);

        // greater than operator
        boolean isStudentOneAgeGreaterThanStudentTwoAge = studentOneAge > studentTwoAge;
        LOGGER.info("isStudentOneAgeGreaterThanStudentTwoAge: " + isStudentOneAgeGreaterThanStudentTwoAge);

        // TODO: Do one for less than or equal to operator
        // code here

        // TODO: Do one for greater than or equal to operator
        // code here
    }

    public void andLogicalOperators() {
        int studentOneAge = 20;
        int studentTwoAge = 25;

        boolean isStudentOneAllowed = studentOneAge >= 18;
        boolean isStudentTwoAllowed = studentTwoAge >= 18;
        // and operator
        if (isStudentOneAllowed && isStudentTwoAllowed) {
            LOGGER.info("All students allowed  for ID registration");
        } else {
            LOGGER.info("Not allowed  for ID registration");
        }

    }

    public void orLogicalOperators() {
        int studentOneAge = 17;
        int studentTwoAge = 25;

        boolean isStudentOneAllowed = studentOneAge >= 18;
        boolean isStudentTwoAllowed = studentTwoAge >= 18;
        // and operator
        if (isStudentOneAllowed || isStudentTwoAllowed) {
            LOGGER.info("Students allowed  for ID registration");
        } else {
            LOGGER.info("Not allowed  for ID registration");
        }

    }

    public void notLogicalOperators() {
        int studentOneAge = 17;
        boolean isStudentOneAllowed = studentOneAge >= 18;
        // and operator
        if (!isStudentOneAllowed) {
            LOGGER.info("Students allowed  for ID registration");
        } else {
            LOGGER.info("Not allowed  for ID registration");
        }

    }

    public static void main(String[] args) {
        OperatorsDemo app = new OperatorsDemo();
        // app.arithmeticOperators();
        // app.assignmentOperators();
        // app.comparisonOperators();
        // app.andLogicalOperators();
        // app.orLogicalOperators();
        app.notLogicalOperators();
    }
}
