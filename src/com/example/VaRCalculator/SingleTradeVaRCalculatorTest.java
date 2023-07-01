package com.example.VaRCalculator;


import org.junit.Assert;
import org.junit.Test;

public class SingleTradeVaRCalculatorTest {

	@Test
	public void testCalculateVaR_ValidInput() {
	    // Test case with valid input
	    double[] pnlVector = { 100.0, 200.0, 250.0, 300.0, 400.0, 500.0 }; // Include pnl value in the pnlVector
	    double pnl = 250.0;
	    double confidenceLevel = 0.95;

	    double expectedVaR = 150.0; // Updated expected VaR value based on the calculation

	    double actualVaR = SingleTradeVaRCalculator.calculateVaR(pnlVector, pnl, confidenceLevel);

	    Assert.assertEquals(expectedVaR, actualVaR, 0.0001); // Assert the expected and actual VaR values
	}

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateVaR_NullPnlVector() {
        // Test case with null pnlVector
        double[] pnlVector = null;
        double pnl = 250.0;
        double confidenceLevel = 0.95;

        SingleTradeVaRCalculator.calculateVaR(pnlVector, pnl, confidenceLevel);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateVaR_EmptyPnlVector() {
        // Test case with empty pnlVector
        double[] pnlVector = {};
        double pnl = 250.0;
        double confidenceLevel = 0.95;

        SingleTradeVaRCalculator.calculateVaR(pnlVector, pnl, confidenceLevel);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateVaR_InvalidConfidenceLevel() {
        // Test case with invalid confidence level
        double[] pnlVector = { 100.0, 200.0, 250.0, 300.0, 400.0, 500.0 };
        double pnl = 250.0;
        double confidenceLevel = -0.5; // Invalid confidence level

        SingleTradeVaRCalculator.calculateVaR(pnlVector, pnl, confidenceLevel);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCalculateVaR_PnlNotInHistoricalValues() {
        // Test case where pnl value is not included in the historical values
        double[] pnlVector = { 100.0, 200.0, 300.0, 400.0, 500.0 };
        double pnl = 2500.0; // Pnl value not present in the pnlVector
        double confidenceLevel = 0.95;

        SingleTradeVaRCalculator.calculateVaR(pnlVector, pnl, confidenceLevel);
    }

    // Test cases for other methods can be added similarly

}
