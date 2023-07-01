package com.example.VaRCalculator;

import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class PortfolioVaRCalculatorTest {

    @Test
    public void testCalculateVaR_ValidInput() {
        // Create a list of trades
        List<PortfolioVaRCalculator.Trade> trades = new ArrayList<>();

        // Create Trade 1
        List<Double> pnlVector1 = new ArrayList<>();
        pnlVector1.add(100.0);
        pnlVector1.add(200.0);
        pnlVector1.add(250.0);
        pnlVector1.add(300.0);
        pnlVector1.add(400.0);
        pnlVector1.add(500.0);
        double pnl1 = 250.0;
        PortfolioVaRCalculator.Trade trade1 = new PortfolioVaRCalculator.Trade("T1", pnlVector1, pnl1);
        trades.add(trade1);

        // Create Trade 2
        List<Double> pnlVector2 = new ArrayList<>();
        pnlVector2.add(150.0);
        pnlVector2.add(250.0);
        pnlVector2.add(300.0);
        pnlVector2.add(350.0);
        pnlVector2.add(450.0);
        pnlVector2.add(550.0);
        double pnl2 = 300.0;
        PortfolioVaRCalculator.Trade trade2 = new PortfolioVaRCalculator.Trade("T2", pnlVector2, pnl2);
        trades.add(trade2);

        double confidenceLevel = 0.95;

        double expectedVaR = 25.0 + 25.0; // Expected VaR = VaR of Trade 1 + VaR of Trade 2

        double actualVaR = PortfolioVaRCalculator.calculateVaR(trades, confidenceLevel);

        Assert.assertEquals(expectedVaR, actualVaR, 0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateVaR_EmptyTradeList() {
        // Create an empty list of trades
        List<PortfolioVaRCalculator.Trade> trades = new ArrayList<>();

        double confidenceLevel = 0.95;

        // This should throw an IllegalArgumentException
        PortfolioVaRCalculator.calculateVaR(trades, confidenceLevel);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateVaR_InvalidConfidenceLevel() {
        // Create a list of trades
        List<PortfolioVaRCalculator.Trade> trades = new ArrayList<>();

        // Create a trade
        List<Double> pnlVector = new ArrayList<>();
        pnlVector.add(100.0);
        pnlVector.add(200.0);
        pnlVector.add(300.0);
        double pnl = 200.0;
        PortfolioVaRCalculator.Trade trade = new PortfolioVaRCalculator.Trade("T1", pnlVector, pnl);
        trades.add(trade);

        double confidenceLevel = 1.5; // Invalid confidence level

        // This should throw an IllegalArgumentException
        PortfolioVaRCalculator.calculateVaR(trades, confidenceLevel);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateVaR_InconsistentPnLVectorSizes() {
        // Create a list of trades
        List<PortfolioVaRCalculator.Trade> trades = new ArrayList<>();

        // Create Trade 1
        List<Double> pnlVector1 = new ArrayList<>();
        pnlVector1.add(100.0);
        pnlVector1.add(200.0);
        pnlVector1.add(300.0);
        double pnl1 = 200.0;
        PortfolioVaRCalculator.Trade trade1 = new PortfolioVaRCalculator.Trade("T1", pnlVector1, pnl1);
        trades.add(trade1);

        // Create Trade 2
        List<Double> pnlVector2 = new ArrayList<>();
        pnlVector2.add(150.0);
        pnlVector2.add(250.0);
        pnlVector2.add(350.0);
        pnlVector2.add(450.0);
        double pnl2 = 300.0;
        PortfolioVaRCalculator.Trade trade2 = new PortfolioVaRCalculator.Trade("T2", pnlVector2, pnl2);
        trades.add(trade2);

        double confidenceLevel = 0.95;

        // This should throw an IllegalArgumentException
        PortfolioVaRCalculator.calculateVaR(trades, confidenceLevel);
    }
}

