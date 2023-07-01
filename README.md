# vaRCalculatorApp
The application consists of two components: the SingleTradeVaRCalculator and the PortfolioVaRCalculator.

Introduction: This document provides documentation for the VaR Calculator application, which is written in Java. The application consists of two components: the SingleTradeVaRCalculator and the PortfolioVaRCalculator.

# SingleTradeVaRCalculator: 
The SingleTradeVaRCalculator calculates the Value at Risk (VaR) for a single trade using historical values. It takes as input the historical profit and loss (P&L) values, the P&L value for the trade of interest, and the desired confidence level. The VaR is calculated using the historical simulation method. The application ensures that the provided inputs are valid, including checking for null or empty historical values, validating the confidence level range, and verifying that the P&L value is included in the historical values.

# PortfolioVaRCalculator: 
The PortfolioVaRCalculator calculates the portfolio-level VaR by aggregating the VaR values of multiple trades. It takes as input a list of Trade objects, where each Trade represents a trade with its associated historical P&L values and the P&L value for the trade. The confidence level is also provided as input. The application validates the input trades for consistency in the length of P&L vectors and checks the confidence level range. The portfolio VaR is calculated by summing the VaR values of each individual trade.

SingleTradeVaRCalculator: To use the application, follow these steps:

1. Open Eclipse IDE: Launch the Eclipse IDE on your computer.

2. Import the project: From the Eclipse menu, go to File > Import. In the "Import" window, select Existing Projects into Workspace and click Next. Then, choose the root directory of the VaR Calculator project and click Finish to import the project into your Eclipse workspace.

3. Open the SingleTradeVaRCalculator class: In the Package Explorer, expand the project folder and locate the SingleTradeVaRCalculator.java file. Double-click on it to open the file in the editor

4. Run the class directly, provide input values during run time. I took one sample input from the dataset provided.

Here are the steps : Sample input taken from the dataset provided

I handled exceptions carefully.

# Algorithm for the singleTradeCalculation:

1)Start the VaR calculation algorithm.

2)Check if the provided list of historical P&L values is null or empty. If so, throw an exception with the error message "Historical values cannot be null or empty."

3)Check if the confidence level is less than 0 or greater than 1. If so, throw an exception with the error message "Confidence level must be between 0 and 1."

4)Sort the historical P&L values in ascending order. If the values are not already sorted, use the Collections.sort() method to sort them.

5)Check if the P&L value (pnl) is present in the sorted historical values. If not, throw an exception with the error message "P&L value must be included in the historical values." 
• Calculate the VaR using the historical simulation method: 
• Get the total number of historical values (n). 
• Calculate the index of the percentile value based on the confidence level: percentileIndex = (n * (1 - confidenceLevel)) 
• Retrieve the historical value at the percentile index: percentileValue = pnl_vector.get(percentileIndex) 
• Calculate the VaR as the difference between the pnl and the percentile value: VaR = pnl - percentileValue 
• Return the calculated VaR value.

6)End the VaR calculation algorithm.

In addition included Junit testing as well inside the same package, SingleTradeCalculatorTest, Run as JunitTest

PortfolioVaRCalculator Usage: The PortfolioVaRCalculator class calculates the Value at Risk (VaR) for a portfolio of trades using historical values. Trade The Trade class represents a trade with its associated trade ID, P&L vector, and P&L value.

public Trade(String tradeId, List pnlVector, double pnl)

tradeId: The trade ID. pnlVector: The list of historical P&L values for the trade. pnl: The current P&L value for the trade

public String getTradeId() --> Returns the trade ID.

public List getPnlVector() --> Returns the P&L vector for the trade.

public double getPnl() --> Returns the current P&L value for the trade.

public static double calculateVaR(List trades, double confidenceLevel) -->

Calculates the Value at Risk (VaR) for a portfolio of trades. trades: The list of trades in the portfolio. confidenceLevel: The confidence level for the VaR calculation (between 0 and 1). Returns the calculated portfolio VaR. Throws IllegalArgumentException if the trades or confidence level are invalid. To run the class, it is in the same package of SingleTradeVaRCalculator, no need to import again

Open the PortfolioVaRCalculator class: In the Package Explorer, expand the project folder and locate the PortfolioVaRCalculator.java file. Double-click on it to open the file in the editor
Run the class directly, provide input values during run time. I took one sample input from the dataset provided.
Here I placed dataset in my localPath and provided the file path, same goes with an other system whoever runs the application must provide their own location where the file exists Handled exceptions wherever it is necessary. 

# Algorithm for portfolioVaRCalculation:

1)Start the PortfolioVaRCalculator application.

2)Print the application title and separator lines.

3)Prompt the user to enter the filename of the CSV file containing the trades.

4)Read the filename from the user's input.

5)Initialize a list of Trade objects to store the trades.

6)Try to read the trades from the CSV file using the readTradesFromCSV() method. If an IOException occurs, print an error message and exit the application.

7)Prompt the user to enter the confidence level (between 0 and 1).

8)Read the confidence level from the user's input.

9)Validate the confidence level to ensure it is within the range of 0 and 1. If it is not, print an error message and exit the application.

10)Calculate the VaR for the portfolio by calling the calculateVaR() method with the list of trades and confidence level as arguments.

11)Print the calculated portfolio VaR.

12)If any IllegalArgumentException occurs during the calculations, catch it and print the error message.

13)End the application.

# Algorithm for calculateVaR() method:

1)Validate the input trades list to ensure it is not null or empty. If it is, throw an IllegalArgumentException with an error message.

2)Validate the confidence level to ensure it is within the range of 0 and 1. If it is not, throw an IllegalArgumentException with an error message.

3)Initialize an empty list, tradeVaRs, to store the VaR values for each trade.

4)Iterate over each trade in the trades list: Calculate the VaR for the trade by calling the calculateSingleTradeVaR() method with the PnLVector, trade's P&L, and confidence level as arguments.

5)Add the calculated VaR to the tradeVaRs list.

6)Calculate the sum of all tradeVaRs by calling the sumList() method with tradeVaRs as the argument.

7)Return the calculated portfolio VaR.

# Algorithm for calculateSingleTradeVaR() method:

1)Validate the input historicalValues list to ensure it is not null or empty. If it is, throw an IllegalArgumentException with an error message.

2)Validate the confidence level to ensure it is within the range of 0 and 1. If it is not, throw an IllegalArgumentException with an error message.

3)Create a sorted copy of the historicalValues list and store it in the sortedValues list.

4)Sort the sortedValues list in ascending order.

5)Check if the pnl value is present in the sortedValues list. If it is not, throw an IllegalArgumentException with an error message.

6)Calculate the percentile index by multiplying the size of sortedValues by (1 - confidenceLevel) and cast it to an integer.

7)Calculate the VaR by subtracting the pnl value from the value at the percentile index in the sortedValues list.

8)Return the calculated VaR.

# Algorithm for readTradesFromCSV() method:

1)Initialize an empty list, trades, to store the Trade objects.

2)Create a BufferedReader to read the CSV file using the provided filename.

3)Read and discard the header row of the CSV file.

4)Repeat the following steps while there are lines to read from the CSV file.

5)Read a line from the CSV file.

6)Split the line using a comma (",") delimiter to obtain the tradeId, pnlValues, and pnl.

7)Split the pnlValues string using a semicolon (";") delimiter to obtain individual P&L values as strings.

8)Create an empty list, pnlVector, to store the parsed P&L values.

9)Iterate over each P&L value string.

10)Parse the value to a double and add it to the pnlVector list.

11)Create a new Trade object using the tradeId, pnlVector, and pnl.

12)Add the Trade object to the trades list.

13)Close the BufferedReader.

14)Return the trades list.

In addition included Junit testing as well inside the same package, PortfolioVaRCalculatorTest

# Dependencies: 
The VaR Calculator application has no external dependencies beyond the core Java libraries. It can be run on any Java-compatible platform or integrated into other Java applications as needed.
