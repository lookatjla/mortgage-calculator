package com.mortgagecalculator;
import java.text.NumberFormat;
import java.util.Scanner;

public class Main {
    static final byte MONTHS_IN_YEAR = 12;
    static final byte PERCENT = 100;

    public static void main(String[] args) {
        int principal;
        float annualInterest;
        byte years;

        principal = (int) readNumber("Principal ($1K - $1M): ", 1_000, 1_000_000);
        annualInterest = (float) readNumber("Annual Interest Rate: ", 1, 30);
        years = (byte) readNumber("Period (Years): ", 1, 30);

        double mortgage = calculateMortgage(principal, annualInterest, years);
        String mortgageFormatted = NumberFormat.getCurrencyInstance().format(mortgage);
        System.out.println();
        System.out.println("MORTGAGE");
        System.out.println("---------------");
        System.out.println("Monthly Payments: " + mortgageFormatted);

        System.out.println();
        System.out.println("PAYMENT SCHEDULE");
        System.out.println("---------------");
        for (short month = 1; month <= years * MONTHS_IN_YEAR; month++) {
            double balance = calculateBalance(principal,annualInterest,years,month);
            System.out.println(NumberFormat.getCurrencyInstance().format(balance));
        }
    }

    public static double calculateMortgage(
            int principal,
            float annualInterest,
            byte years) {

        float monthlyInterest = annualInterest / PERCENT / MONTHS_IN_YEAR;
        short numberOfPayments = (short) (years * MONTHS_IN_YEAR);

        double mortgage = principal
                * (monthlyInterest * Math.pow(1 + monthlyInterest, numberOfPayments))
                / (Math.pow(1 + monthlyInterest, numberOfPayments) -1);

        return mortgage;
    }

    public static double readNumber(String prompt, double min, double max) {
        Scanner scanner = new Scanner(System.in);
        double value;

        while (true) {
            System.out.print(prompt);
            value = scanner.nextFloat();
            if (value >= min && value <= max) {
                break;
            } else {
                System.out.println("Enter a value between " + min + " and " + max + ":");
            }
        }
        return value;
    }

    public static double calculateBalance(int principal,
                                          float annualInterest,
                                          byte years,
                                          short numberOfPaymentsMade) {

        float monthlyInterest = annualInterest / PERCENT / MONTHS_IN_YEAR;
        short numberOfPayments = (short) (years * MONTHS_IN_YEAR);

        double balance = principal
                * (Math.pow(1 + monthlyInterest, numberOfPayments) - Math.pow(1 + monthlyInterest, numberOfPaymentsMade))
                / (Math.pow(1 + monthlyInterest, numberOfPayments) - 1);
        return balance;
    }
}