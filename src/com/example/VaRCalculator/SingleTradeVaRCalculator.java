package com.example.VaRCalculator;

import java.util.Arrays;
import java.util.Scanner;

public class SingleTradeVaRCalculator {

    // Calculate the VaR (Value at Risk) for a single trade
    public static double calculateVaR(double[] pnlVector, double pnl, double confidenceLevel) {
        // Check for valid input
        if (pnlVector == null || pnlVector.length == 0) {
            throw new IllegalArgumentException("Error: Historical values cannot be null or empty");
        }
        if (confidenceLevel < 0 || confidenceLevel > 1) {
            throw new IllegalArgumentException("Error: Confidence level must be between 0 and 1");
        }

        // Sort the historical values in ascending order if necessary.
        if (!isSorted(pnlVector)) {
            Arrays.sort(pnlVector);
        }

        // Check if the P&L value is present in the historical values.
        if (Arrays.binarySearch(pnlVector, pnl) < 0) {
            throw new IllegalArgumentException("Error: P&L value must be included in the historical values");
        }

        // Calculate the VaR using the historical simulation method.
        int n = pnlVector.length;
        int percentileIndex = (int) ((n * (1 - confidenceLevel)));
        double VaR = pnl - pnlVector[percentileIndex];

        return VaR;
    }

    public static void main(String[] args) {
        System.out.println("VaR Calculator Application");
        System.out.println("==========================");

        try (Scanner scanner = new Scanner(System.in)) {
            // Read input from the user
            System.out.print("Enter the confidence level (between 0 and 1): ");
            double confidenceLevel = scanner.nextDouble();
            if (confidenceLevel < 0 || confidenceLevel > 1) {
                System.out.println("Invalid input! Confidence level must be between 0 and 1.");
                return;
            }

            System.out.print("Enter the P&L value: ");
            double pnl = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Enter the number of historical values: ");
            int vectorSize = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Enter the historical values separated by semicolons (;):");
            String historicalValuesStr = scanner.nextLine();

            try {
                // Parse the historical values from the input string
                double[] historicalValues = parseHistoricalValues(historicalValuesStr, vectorSize, pnl);

                // Calculate the VaR
                double VaR = calculateVaR(historicalValues, pnl, confidenceLevel);
                System.out.println("VaR for the trade: " + VaR);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    // Parse the historical values from the input string
    private static double[] parseHistoricalValues(String valuesStr, int numValues, double pnl) {
        String[] valuesArray = valuesStr.split(";");
        double[] values = new double[numValues];

        // Check if the number of values matches the expected length
        if (valuesArray.length != numValues) {
            throw new IllegalArgumentException("Error: Number of historical values does not match the expected length");
        }

        boolean pnlIncluded = false;
        for (int i = 0; i < numValues; i++) {
            double value = Double.parseDouble(valuesArray[i]);
            values[i] = value;
            if (value == pnl) {
                pnlIncluded = true;
            }
        }

        // Check if the P&L value is included in the historical values
        if (!pnlIncluded) {
            throw new IllegalArgumentException("Error: P&L value must be included in the historical values");
        }

        return values;
    }

    // Check if the array is sorted in ascending order
    private static boolean isSorted(double[] values) {
        for (int i = 1; i < values.length; i++) {
            if (values[i] < values[i - 1]) {
                return false;
            }
        }
        return true;
    }
}
