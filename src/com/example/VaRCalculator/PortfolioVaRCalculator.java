package com.example.VaRCalculator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PortfolioVaRCalculator {

    // Trade class represents a single trade
    static class Trade {
        private final String tradeId;
        private final List<Double> pnlVector;
        private final double pnl;

        public Trade(String tradeId, List<Double> pnlVector, double pnl) {
            this.tradeId = tradeId;
            this.pnlVector = pnlVector;
            this.pnl = pnl;
        }

        public String getTradeId() {
            return tradeId;
        }

        public List<Double> getPnlVector() {
            return pnlVector;
        }

        public double getPnl() {
            return pnl;
        }
    }

    /**
     * Calculates the portfolio Value at Risk (VaR) based on a list of trades and a confidence level.
     *
     * @param trades           List of trades
     * @param confidenceLevel  Confidence level (between 0 and 1)
     * @return                 Portfolio VaR
     */
    public static double calculateVaR(List<Trade> trades, double confidenceLevel) {
        validateTrades(trades);
        validateConfidenceLevel(confidenceLevel);

        List<Double> portfolioPnLs = extractPnLs(trades);

        List<Double> tradeVaRs = new ArrayList<>();
        for (Trade trade : trades) {
            double tradeVaR = calculateSingleTradeVaR(portfolioPnLs, trade.getPnl(), confidenceLevel);
            tradeVaRs.add(tradeVaR);
        }

        return sumList(tradeVaRs);
    }

    /**
     * Validates the list of trades.
     *
     * @param trades  List of trades
     */
    private static void validateTrades(List<Trade> trades) {
        if (trades == null || trades.isEmpty()) {
            throw new IllegalArgumentException("Error: Trade list cannot be null or empty");
        }

        int vectorSize = trades.get(0).getPnlVector().size();
        for (Trade trade : trades) {
            if (trade.getPnlVector().size() != vectorSize) {
                throw new IllegalArgumentException("Error: P&L vector sizes are inconsistent");
            }
        }
    }

    /**
     * Validates the confidence level.
     *
     * @param confidenceLevel  Confidence level
     */
    private static void validateConfidenceLevel(double confidenceLevel) {
        if (confidenceLevel < 0 || confidenceLevel > 1) {
            throw new IllegalArgumentException("Error: Confidence level must be between 0 and 1");
        }
    }

    /**
     * Extracts the P&L values from the list of trades.
     *
     * @param trades  List of trades
     * @return        List of P&L values
     */
    private static List<Double> extractPnLs(List<Trade> trades) {
        List<Double> portfolioPnLs = new ArrayList<>();
        for (Trade trade : trades) {
            portfolioPnLs.add(trade.getPnl());
        }
        return portfolioPnLs;
    }

    /**
     * Calculates the VaR for a single trade based on the historical values, P&L value, and confidence level.
     *
     * @param historicalValues  List of historical values
     * @param pnl               P&L value of the trade
     * @param confidenceLevel   Confidence level
     * @return                  VaR for the trade
     */
    private static double calculateSingleTradeVaR(List<Double> historicalValues, double pnl, double confidenceLevel) {
        validateHistoricalValues(historicalValues);
        validateConfidenceLevel(confidenceLevel);

        List<Double> sortedValues = new ArrayList<>(historicalValues);
        sortedValues.sort(Double::compareTo);

        if (!sortedValues.contains(pnl)) {
            throw new IllegalArgumentException("Error: P&L value must be included in the historical values");
        }

        int percentileIndex = (int) (sortedValues.size() * (1 - confidenceLevel));
        double VaR = pnl - sortedValues.get(percentileIndex);
        return VaR;
    }

    /**
     * Validates the list of historical values.
     *
     * @param historicalValues  List of historical values
     */
    private static void validateHistoricalValues(List<Double> historicalValues) {
        if (historicalValues == null || historicalValues.isEmpty()) {
            throw new IllegalArgumentException("Error: Historical values cannot be null or empty");
        }
    }

    /**
     * Calculates the sum of values in a list.
     *
     * @param values  List of values
     * @return        Sum of values
     */
    private static double sumList(List<Double> values) {
        return values.stream().mapToDouble(Double::doubleValue).sum();
    }

    /**
     * Reads trades from a CSV file.
     *
     * @param filename  CSV file name
     * @return          List of trades
     * @throws IOException  If an error occurs while reading the file
     */
    private static List<Trade> readTradesFromCSV(String filename) throws IOException {
        List<Trade> trades = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            br.readLine(); // Skip the header row
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String tradeId = data[0];
                String[] pnlValues = data[1].split(";");
                double pnl = parseDoubleValue(data[2]);

                List<Double> pnlVector = new ArrayList<>();
                for (String value : pnlValues) {
                    pnlVector.add(parseDoubleValue(value));
                }

                Trade trade = new Trade(tradeId, pnlVector, pnl);
                trades.add(trade);
            }
        }

        return trades;
    }

    /**
     * Parses a string value to a double.
     *
     * @param value  String value
     * @return       Parsed double value
     * @throws IllegalArgumentException  If the value is not a valid number
     */
    private static double parseDoubleValue(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: Invalid numeric value found in the CSV file");
        }
    }

    /**
     * Entry point of the VaR Calculator application.
     *
     * @param args  Command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("VaR Calculator Application");
        System.out.println("==========================");

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the filename: ");
            String filename = scanner.nextLine();

            List<Trade> trades;
            try {
                trades = readTradesFromCSV(filename);
            } catch (IOException e) {
                System.out.println("Error: Failed to read trades from the CSV file");
                return;
            }

            System.out.print("Enter the confidence level (between 0 and 1): ");
            double confidenceLevel = scanner.nextDouble();
            if (confidenceLevel < 0 || confidenceLevel > 1) {
                System.out.println("Invalid input! Confidence level must be between 0 and 1.");
                return;
            }

            try {
                double portfolioVaR = calculateVaR(trades, confidenceLevel);
                System.out.println("Portfolio VaR: " + portfolioVaR);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

