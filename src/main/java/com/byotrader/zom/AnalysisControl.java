package com.byotrader.zom;

import java.io.File;
import java.util.Vector;
import java.util.GregorianCalendar;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public class AnalysisControl extends ZomController {
  private ZomDate zomDate;
  private ZomFrame zomFrame;
  private AnalysisDataModel analysisDataModel;
  private AnalysisCSVdataModel analysisCSVdataModel;
  private AnalysisDefTableModel analysisTableModel;
  private AnalysisPanel analysisPanel;
  private String currentStock;
  private boolean frequencyChanged;

  public void setFrame(ZomFrame frame) {
    this.zomFrame = frame;
  }

  public ZomFrame getFrame() {
    return this.zomFrame;
  }

  public AnalysisControl(ZomFrame frame) {
    this.zomDate = new ZomDate();
    this.zomFrame = frame;
  }

  public void revealUI(Vector stockIdentity) {
    if (stockIdentity == null) {
      System.out.println("AnalysisControl.revealUI: missing stock identity!");
      return;
    }

    String stockName = "AnalysisData/" + ((ZomInfo)stockIdentity.get(0)).getString() + ".xml";

    // If the current stock is different to the indentified stock
    if (this.currentStock != null && this.currentStock.compareTo(stockName) != 0) {
      System.out.println("AnalysisControl.revealUI: saving - " + this.currentStock);
      // Save the current stock ready for the new to be opened
      File stockFile = new File(this.currentStock);
      this.analysisDataModel.saveXML();
      // Cause a change in stock
      this.currentStock = null;
    }

    // Either a new stock or a change in stock
    if (this.currentStock == null) {
      System.out.println("AnalysisControl.revealUI: opening - " + stockName);
      // Prepare the data, table, and panel with the new stock
      try {
        this.analysisDataModel = new AnalysisDataModel(stockName);
      }
      catch (Exception ex) {
        this.zomFrame.statusMsg("Failed to open Analysis Model", true, 10);
        return;
      }

      this.analysisTableModel  = new AnalysisDefTableModel(this.analysisDataModel);
      this.analysisPanel = new AnalysisPanel(this.analysisTableModel, this);
      this.currentStock = stockName;

      // Populate the Form
      this.fillForm(stockIdentity);
    }

    // Instruct the main frame to display the new stock
    this.zomFrame.setZomPanel(this.analysisPanel);
  }

  public void saveAnalysisData() {
    this.analysisDataModel.saveXML();
  }

  private void fillForm(Vector stockIdentity) {
    // The stock Name and Code can come from the Portfolio Table model
    this.analysisPanel.setStockNameText(((ZomInfo)stockIdentity.get(0)).getString());
    this.analysisDataModel.setStockName(((ZomInfo)stockIdentity.get(0)));
    this.analysisPanel.setStockCodeText(((ZomInfo)stockIdentity.get(1)).getString());
    this.analysisDataModel.setStockCode(((ZomInfo)stockIdentity.get(1)));

    // The rest has to come from the Analysis Data model
    this.analysisPanel.setStartingPortfolioText(this.analysisDataModel.getPortfolioStartingValueString(""));
    this.analysisPanel.setStartingStockText(this.analysisDataModel.getStartingStockPriceString(""));
    this.analysisPanel.setStartingDateText(this.analysisDataModel.getStartingDateString(""));
    this.analysisPanel.setInterestRateText(this.analysisDataModel.getInterestRateString(""));
    this.analysisPanel.setBrokerageFeeText(this.analysisDataModel.getBrokerageFeeString(""));
    this.analysisPanel.setTradeFrequencyText(this.analysisDataModel.getTradeFrequencyString("1"));

    // Populate the combo box values
    this.analysisPanel.populateCashReserve(
      new Object[] {
        new ZomPercent("0.0"),
        new ZomPercent("0.1"),
        new ZomPercent("0.2"),
        new ZomPercent("0.3"),
        new ZomPercent("0.4"),
        new ZomPercent("0.5"),
        new ZomPercent("0.6"),
        new ZomPercent("0.7"),
        new ZomPercent("0.8"),
        new ZomPercent("0.9"),
        new ZomPercent("1.0"),
        ""
      }
    );

    // Set the displayed cash percentage value
    int idxCP = (int)(this.analysisDataModel.getStartingCashPercentageDouble(1.1) * 10.0);
    this.analysisPanel.setCashReserveIndex(idxCP);

    this.analysisPanel.populateMarketIndicator(
      new Object[] {
        new ZomInfo("Falling Sharply"),
        new ZomInfo("Falling Slowly"),
        new ZomInfo("Steady or Unsure"),
        new ZomInfo("Rising Slowly"),
        new ZomInfo("Rising Sharply"),
        ""
      }
    );

    int idxMI = (int)(this.analysisDataModel.getMarketIndicatorDouble(2.5) * 100.0);

    switch(idxMI) {
      case 50:  idxMI = 0; break;
      case 75: idxMI = 1; break;
      case 100:  idxMI = 2; break;
      case 150:  idxMI = 3; break;
      case 200:  idxMI = 4; break;
      case 250:  idxMI = 5; break;
    }

    // Set the combo box value to the last market indicator value
    this.analysisPanel.setMarketIndicatorIndex(idxMI);

    // Set the Is Minimum Trade Value and Percentage Toggle
    this.analysisPanel.setMinimumTradeText(this.analysisDataModel.getMinimumTradeValueString(""));
    this.analysisPanel.setPercentSelected(this.analysisDataModel.getMinIsPercentageBoolean(false));

    // Display Running Calcs
    this.displayRunningCalcs();
  }

  private void displayRunningCalcs() {
    this.analysisPanel.setZomComparisonText(this.analysisDataModel.getRunningZomValue(""));
    this.analysisPanel.setSingleComparisonText(this.analysisDataModel.getRunningSingleValue(""));
  }

  public boolean isStartingPortfolioValid(String value) {
    boolean valid = false;

    if (value.trim().compareTo("") == 0) {
      this.analysisDataModel.setPortfolioStartingValue(null);
      return true;
    }

    try {
      ZomCurrency zomValue = new ZomCurrency();
      zomValue.exhaustiveParse(value);
      this.analysisDataModel.setPortfolioStartingValue(zomValue);
      valid = true;
    } catch (Exception ex) {
      this.analysisDataModel.setPortfolioStartingValue(null);
      this.zomFrame.statusMsg("Invalid Starting Portfolio Value", true, 5);
      valid = false;
    }
    return valid;
  }

  public boolean isStartingStockValid(String value) {
    boolean valid = false;

    if (value.trim().compareTo("") == 0) {
      this.analysisDataModel.setStartingStockPrice(null);
      return true;
    }

    try {
      ZomAccuratePrice zomValue = new ZomAccuratePrice();
      zomValue.exhaustiveParse(value);
      this.analysisDataModel.setStartingStockPrice(zomValue);
      valid = true;
    } catch (Exception ex) {
      this.analysisDataModel.setStartingStockPrice(null);
      this.zomFrame.statusMsg("Invalid Starting Stock Value", true ,5);
      valid = false;
    }
    return valid;
  }

  public boolean isStartingDateValid(String value) {
    boolean valid = false;

    if (value.trim().compareTo("") == 0) {
      this.analysisDataModel.setStartingDate(null);
      return true;
    }

    try {
      ZomDate zomDate = new ZomDate();
      zomDate.exhaustiveParse(value);
      this.analysisDataModel.setStartingDate(zomDate);
      valid = true;
    } catch (Exception ex) {
      this.analysisDataModel.setStartingDate(null);
      this.zomFrame.statusMsg("Invalid Starting Date", true, 5);
      valid = false;
    }
    return valid;
  }

  public boolean isInterestRateValid(String value) {
    boolean valid = false;

    if (value.trim().compareTo("") == 0) {
      this.analysisDataModel.setInterestRate(null);
      return true;
    }

    if (value.indexOf("%") == -1) { value += "%"; }

    try {
      ZomPercent zomValue = new ZomPercent();
      zomValue.exhaustiveParse(value);
      this.analysisDataModel.setInterestRate(zomValue);
      valid = true;
    } catch (Exception ex) {
      this.analysisDataModel.setInterestRate(null);
      this.zomFrame.statusMsg("Invalid Interest Rate", true, 5);
      valid = false;
    }
    return valid;
  }

  public boolean isBrokerageFeeValid(String value) {
    boolean valid = false;

    if (value.trim().compareTo("") == 0) {
      this.analysisDataModel.setBrokerageFee(null);
      return true;
    }

    try {
      ZomCurrency zomValue = new ZomCurrency();
      zomValue.exhaustiveParse(value);
      this.analysisDataModel.setBrokerageFee(zomValue);
      valid = true;
    } catch (Exception ex) {
      this.analysisDataModel.setBrokerageFee(null);
      this.zomFrame.statusMsg("Invalid Brokerage Fee", true, 5);
      valid = false;
    }
    return valid;
  }

  public boolean isTradeFrequencyValid(String value) {
    boolean valid = false;

    if (value.trim().compareTo("") == 0) {
      this.analysisDataModel.setTradeFrequency(null);
      return true;
    }

    try {
      ZomCounter zomValue = new ZomCounter();
      zomValue.exhaustiveParse(value);
      if (zomValue.getLong() < 1) {
        throw new Exception("AnalysisControl.isTradeFrequencyValid: negative frequency not allowed");
      }
      this.frequencyChanged = this.analysisDataModel.setTradeFrequency(zomValue);
      valid = true;
    } catch (Exception ex) {
      this.analysisDataModel.setTradeFrequency(null);
      this.zomFrame.statusMsg("Invalid Trading Frequency", true, 5);
      valid = false;
    }
    return valid;
  }

  public boolean isCashReserveValid(String value) {
    boolean valid = false;

    if (value.trim().compareTo("") == 0) {
      this.analysisDataModel.setStartingCashPercentage(null);
      return true;
    }

    try {
      ZomPercent zomValue = new ZomPercent();
      zomValue.exhaustiveParse(value);
      this.analysisDataModel.setStartingCashPercentage(zomValue);
      valid = true;
    } catch (Exception ex) {
      this.analysisDataModel.setStartingCashPercentage(null);
      this.zomFrame.statusMsg("Invalid Starting Cash Percentage", true, 5);
      valid = false;
    }
    return valid;
  }

  public boolean isMarketIndicatorValid(String value) {
    boolean valid = false;
    ZomCurrency marketIndicator;

    try {
      if (value.compareTo("Falling Sharply") == 0)
        marketIndicator = new ZomCurrency("0.5");
      else if (value.compareTo("Falling Slowly") == 0)
        marketIndicator = new ZomCurrency("0.75");
      else if (value.compareTo("Steady or Unsure") == 0)
        marketIndicator = new ZomCurrency("1.0");
      else if (value.compareTo("Rising Slowly") == 0)
        marketIndicator = new ZomCurrency("1.5");
      else if (value.compareTo("Rising Sharply") == 0)
        marketIndicator = new ZomCurrency("2.0");
      else if (value.compareTo("") == 0)
        marketIndicator = null;
      else {
        System.out.println("AnalysisControl.isMarketIndicatorValid: Unknown indicator = " + value);
        throw new Exception("AnalysisControl.isMarketIndicatorValid: Unknown indicator = " + value);
      }
      this.analysisDataModel.setMarketIndicator(marketIndicator);
      valid = true;
    } catch (Exception ex) {
      this.analysisDataModel.setMarketIndicator(null);
      this.zomFrame.statusMsg("Invalid Market Indicator Percentage", true, 5);
      valid = false;
    }
    return valid;
  }

  public boolean isMinimumTradeValid(String value, boolean isPercent) {
    boolean valid = false;

    if (value.trim().compareTo("") == 0) {
      this.analysisDataModel.setMinimumTradeValue(null);
      this.analysisDataModel.setMinIsPercentage(new ZomLogical(isPercent));
      return true;
    }

    if (isPercent) {
      try {
        if (value.indexOf("%") == -1) { value += "%"; }
        ZomPercent zomValue = new ZomPercent();
        zomValue.exhaustiveParse(value);
        this.analysisDataModel.setMinimumTradeValue(zomValue);
        this.analysisDataModel.setMinIsPercentage(new ZomLogical(isPercent));
        valid = true;
      } catch (Exception ex) {
        this.analysisDataModel.setMinimumTradeValue(null);
        this.analysisDataModel.setMinIsPercentage(new ZomLogical(isPercent));
        this.zomFrame.statusMsg("Invalid Minimum Percentage", true, 5);
        valid = false;
      }
    }
    else {
      try {
        ZomCurrency zomValue = new ZomCurrency();
        zomValue.exhaustiveParse(value);
        this.analysisDataModel.setMinimumTradeValue(zomValue);
        this.analysisDataModel.setMinIsPercentage(new ZomLogical(isPercent));
        valid = true;
      } catch (Exception ex) {
        this.analysisDataModel.setMinimumTradeValue(null);
        this.analysisDataModel.setMinIsPercentage(new ZomLogical(isPercent));
        this.zomFrame.statusMsg("Invalid Minimum value", true, 5);
        valid = false;
      }
    }

    return valid;
  }

  public void addSample() {
    if (analysisDataModel == null) {
      System.out.println("AnalysisControl.addSample: Missing Data Model");
      return;
    }

    if (zomFrame == null) {
      System.out.println("AnalysisControl.addSample: Missing Zom User Interface");
      return;
    }

    if (this.analysisDataModel.tradeFormIncomplete()) {
      zomFrame.statusMsg("You must complete the trade form before adding!", true, 5);
      return;
    }

    int[] rowIndex;
    int rowCount;
    String tradeDate;
    String tradePrice;

    rowCount = this.getSelectedRowCount();

    switch (rowCount) {
      case 0:
        int lastRow = this.analysisDataModel.RowData.size() - 1;

        if (lastRow < 0) {
          // Adding a row when no rows exist
          GregorianCalendar nextDate = new GregorianCalendar();
          ZomDate nextDay = new ZomDate();

          nextDate.setTime(this.analysisDataModel.getStartingDate().getDate());
          nextDate.add(GregorianCalendar.DATE, 1);
          nextDay.setValue(nextDate.getTime().getTime());

          tradeDate = nextDay.toString();
          //tradeDate = this.analysisDataModel.getStartingDateString("");
          tradePrice = this.analysisDataModel.getStartingStockPriceString("");

          // Automatically add the first row with the date and price from the form
          Vector newSample = this.analysisDataModel.addStockData(
            this.analysisDataModel.getStartingDate(),
            this.analysisDataModel.getStartingStockPrice());
          this.analysisDataModel.RowData.add(newSample);
        }
        else {
          GregorianCalendar nextDate = new GregorianCalendar();
          ZomDate nextDay = new ZomDate();

          nextDate.setTime(this.analysisDataModel.getTradeDate(lastRow).getDate());
          nextDate.add(GregorianCalendar.DATE, 1);
          nextDay.setValue(nextDate.getTime().getTime());

          tradeDate = nextDay.toString();
          tradePrice = this.analysisDataModel.getTradePrice(lastRow).toString();
        }
        break;

      case 1:
        int rowIdx = this.getSelectedRow();
        GregorianCalendar nextDate = new GregorianCalendar();
        ZomDate nextDay = new ZomDate();

        nextDate.setTime(this.analysisDataModel.getTradeDate(rowIdx).getDate());
        nextDate.add(GregorianCalendar.DATE, 1);
        nextDay.setValue(nextDate.getTime().getTime());

        tradeDate = nextDay.toString();
        tradePrice = this.analysisDataModel.getTradePrice(rowIdx).toString();
        break;

      default:
        System.out.println("AnalysisControl.addSample: cannot add with mulitple rows selected");
        zomFrame.statusMsg("Cannot add with mulitple rows selected!", true, 5);
        return;
    }

    this.analysisPanel.displayNewSampleDialog(tradeDate, tradePrice);
  }

  public boolean verifyNewSharePrice(String shareDate, String sharePrice) {
    ZomDate zomTmpDate;
    ZomAccuratePrice zomValue;

    try {
      // Use the permanent date object to increase parse speed
      this.zomDate.exhaustiveParse(shareDate);
      // But save result in unique date object otherwise every row in the table will display the same value
      zomTmpDate = new ZomDate(this.zomDate.getTime());
    } catch (Exception ex) {
      this.zomFrame.statusMsg("Invalid Trading Date", true, 5);
      return false;
    }

    try {
      zomValue = new ZomAccuratePrice();
      zomValue.exhaustiveParse(sharePrice);
    } catch (Exception ex) {
      this.zomFrame.statusMsg("Invalid Share Price", true, 5);
      return false;
    }

    if (this.analysisDataModel.existsNode(zomDate.ioString())) {
      this.zomFrame.statusMsg("Trading date already exists.", true, 5);
      return false;
    }

    // Ensure that the new date is not less then or the same as the first date
    int lastRow;
    ZomDate firstDate;
    ZomDate lastDate;

    lastRow = this.analysisDataModel.RowData.size() - 1;

    if (lastRow < 0) {
      firstDate = this.analysisDataModel.getStartingDate();
      lastDate = firstDate;
    }
    else {
      firstDate = this.analysisDataModel.getTradeDate(0);
      lastDate = this.analysisDataModel.getTradeDate(lastRow);
    }

    if (this.zomDate.compareTo(firstDate) <= 0) {
      this.zomFrame.statusMsg("Trading date may not preceed Starting Date", true, 5);
      return false;
    }

    Vector newSample = this.analysisDataModel.addStockData(zomTmpDate, zomValue);
    this.analysisDataModel.addRow(newSample);

    // if inserting entry (as apposed to appending) then force recalc
    if (this.zomDate.compareTo(lastDate) < 0) {
      this.analysisDataModel.reCalculateAll();
    }

    this.analysisTableModel.fireTableDataChanged();
    // Display the new running calcs
    this.displayRunningCalcs();

    this.zomFrame.statusMsg("New share price entered!", false, 0);
    return true;
  }

  // Recalculate all trades.  Fortunately the data model can do this all from data
  // stored in memory.
  public void zomCalculate() {
    if (this.analysisDataModel.tradeFormIncomplete()) {
      this.zomFrame.statusMsg("Trading form is incomplete!", true, 5);
    }

    // If there are no rows then then create the first analysis row automatically
    if (this.analysisDataModel.RowData.size() == 0) {
      // Automatically add the first row with the date and price from the form
      Vector newSample = this.analysisDataModel.addStockData(
        this.analysisDataModel.getStartingDate(),
        this.analysisDataModel.getStartingStockPrice());
      this.analysisDataModel.RowData.add(newSample);
    }

    if (this.frequencyChanged) {
      // Need to re-read data from file
      // Dont forget to save any new memory data first
      this.analysisDataModel.saveXML();
      // Reload all data
      this.analysisDataModel.loadData(this.analysisDataModel.getTradeFrequency(1));
    }
    else {
      // Just work from memory data
      this.analysisDataModel.reCalculateAll();
    }
    // Inform the Table UI that the data has changed
    this.analysisTableModel.fireTableDataChanged();
    this.displayRunningCalcs();
  }

  public void deleteSample() {
    if (this.analysisDataModel == null) {
      System.out.println("AnalysisControl.deleteSample: Missing Data Model");
      return;
    }

    if (this.zomFrame == null) {
      System.out.println("AnalysisControl.deleteSample: Missing Zom User Interface");
      return;
    }

    int[] rowIndex;
    int rowCount;

    rowCount = this.getSelectedRowCount();
    rowIndex = this.getSelectedRows();

    if (rowCount == 0) {
      System.out.println("AnalysisControl.deleteSample: no row selected");
      zomFrame.statusMsg("No trade selected!", true, 5);
      return;
    }

    if (rowIndex[0] == 0) {
      System.out.println("AnalysisControl.deleteSample: attempted to delete row zero");
      zomFrame.statusMsg("First row cannot be deleted (use delete all)!", true, 5);
      return;
    }

    if (rowCount == 1) {
      this.analysisPanel.displayConfirmationDialog(
        "You are deleting the trade on date\n" +
        "  " + (ZomDate)((Vector)this.analysisDataModel.RowData.get(rowIndex[0])).get(this.analysisDataModel.TRADING_DATE) + "\n" +
        //"  " + this.analysisDataModel.getTradeDateString(rowIndex[0]) + "\n" +
        "If this is correct then press:\n" +
        "        Delete to confirm.\n" +
        "        Abort to reject.");
    }
    else {
      this.analysisPanel.displayConfirmationDialog(
        "You are deleting a total of (" + rowCount + ") trades.\n" +
        "If this is correct then press:\n" +
        "        Delete to confirm.\n" +
        "        Abort to reject.");
    }
  }

  public int getSelectedRow() {
    return this.analysisPanel.jTableAnalysis.getSelectedRow();
  }

  public int[] getSelectedRows() {
    return this.analysisPanel.jTableAnalysis.getSelectedRows();
  }

  public int getSelectedRowCount() {
    return this.analysisPanel.jTableAnalysis.getSelectedRowCount();
  }

  public void confirmedDeleteSample() {
    if (this.analysisDataModel == null) {
      System.out.println("AnalysisControl.confirmedDeleteSample: Missing Data Model");
      return;
    }

    if (this.zomFrame == null) {
      System.out.println("AnalysisControl.confirmedDeleteSample: Missing Zom User Interface");
      return;
    }

    int[] rowIndex;
    int rowCount;

    rowCount = this.getSelectedRowCount();
    rowIndex = this.getSelectedRows();

    if (rowCount == 0) {
      System.out.println("AnalysisControl.confirmedDeleteSample: no row selected");
      zomFrame.statusMsg("No trade selected!", true, 5);
      return;
    }

    while(rowCount > 0) {
      int idx = rowIndex[--rowCount];
      String tradeDate = this.analysisDataModel.getTradeDateString(idx);
      //System.out.println("Deleting " + rowCount + " " + idx + " " + tradeDate);

      /*
        Need to delete three items when deleting from a table:

        1. The Table row (internal)
        2. The corresponding Data Model entry (internal)
        3. The corresponding source data entry (external)

        Deleting the table row takes care of internal data (1. and 2. above) which leaves
        the data model the task of deleting just external data (3. above)
      */

      // Lets delete the source data entry from the XML source file
      try {
        this.analysisDataModel.deleteNode(tradeDate);
      }
      catch (Exception ex) {
        zomFrame.statusMsg("Failed to delete trade data!", true, 5);
        return;
      }

      // now delete all internal representation of the data
      this.analysisTableModel.removeRow(idx);
    }

    // Calculations for all other rows need to be reapplied and running calcs displayed
    this.analysisDataModel.reCalculateAll();
    this.displayRunningCalcs();
  }

  public void deleteAllSamples() {
    if (this.analysisDataModel == null) {
      System.out.println("AnalysisControl.deleteSample: Missing Data Model");
      return;
    }

    if (this.zomFrame == null) {
      System.out.println("AnalysisControl.deleteSample: Missing Zom User Interface");
      return;
    }

    this.analysisPanel.displayDelAllConfDialog(
      "You have requested the deletion\n" +
      "of all the trade data\n" +
      "If this is correct then press:\n" +
      "        Delete to confirm.\n" +
      "        Abort to reject.");
  }

  public void confirmedDeleteAllSamples() {
    if (this.analysisDataModel == null) {
      System.out.println("AnalysisControl.confirmedDeleteSample: Missing Data Model");
      return;
    }

    if (this.zomFrame == null) {
      System.out.println("AnalysisControl.confirmedDeleteSample: Missing Zom User Interface");
      return;
    }

    this.analysisDataModel.deleteAllNodes();
    this.analysisDataModel.RowData.clear();

    // Inform the Table UI that the data has changed
    this.analysisTableModel.fireTableDataChanged();
    this.displayRunningCalcs();
  }

  public void importCSVFile() {
    if (this.analysisDataModel == null) {
      System.out.println("AnalysisControl.importCSVFile: Missing Data Model");
      return;
    }

    if (this.zomFrame == null) {
      System.out.println("AnalysisControl.importCSVFile: Missing Zom User Interface");
      return;
    }

    if (this.analysisDataModel.RowData.size() != 0) {
      zomFrame.statusMsg("Import cannot append!", true, 5);
      zomFrame.displayInfo("Analysis model already contains data entries.  \n" +
        "You cannot use import to append to existing data.  \n" +
        "Import can only be to an empty Analysis sheet.");
      return;
    }

    try {
      this.provideFormValues();
      this.analysisPanel.displayImportDialog();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void provideFormValues() throws Exception {
    if (this.analysisDataModel.getPortfolioStartingValueString("").compareTo("") == 0) {
      if (this.isStartingPortfolioValid("$10000.00")) {
        this.analysisPanel.setStartingPortfolioText("$10000.00");
      }
      else {
        this.zomFrame.statusMsg("Could not provide Form Starting Portfolio Value", true, 5);
        throw new Exception("AnalysisControl.provideFormValues: bad Starting Protfolio Value");
      }
    }

    if (this.analysisDataModel.getInterestRateString("").compareTo("") == 0) {
      if (this.isInterestRateValid("10.00%")) {
        this.analysisPanel.setInterestRateText("10.00%");
      }
      else {
        this.zomFrame.statusMsg("Could not provide Form Interest Rate", true, 5);
        throw new Exception("AnalysisControl.provideFormValues: bad Interest Rate");
      }
    }

    if (this.analysisDataModel.getBrokerageFeeString("").compareTo("") == 0) {
      if (this.isBrokerageFeeValid("$10.00")) {
        this.analysisPanel.setBrokerageFeeText("$10.00");
      }
      else {
        this.zomFrame.statusMsg("Could not provide Form Brokerage Fee", true, 5);
        throw new Exception("AnalysisControl.provideFormValues: bad Brokerage Fee");
      }
    }

    if (this.analysisDataModel.getMinimumTradeValueString("").compareTo("") == 0) {
      if (this.isMinimumTradeValid("5.00%", true)) {
        this.analysisPanel.setMinimumTradeText("5.00%");
        this.analysisPanel.setPercentSelected(true);
      }
      else {
        this.zomFrame.statusMsg("Could not provide Form Minimum Trade Value", true, 5);
        throw new Exception("AnalysisControl.provideFormValues: bad Minimum Trade Value");
      }
    }

    if (this.analysisDataModel.getStartingCashPercentageString("").compareTo("") == 0) {
      if (this.isCashReserveValid("50%")) {
        this.analysisPanel.setCashReserveIndex(5);
      }
      else {
        this.zomFrame.statusMsg("Could not provide Form Starting Cash Reserve", true, 5);
        throw new Exception("AnalysisControl.provideFormValues: bad Starting Cash Reserve");
      }
    }

    if (this.analysisDataModel.getMarketIndicatorString("").compareTo("") == 0) {
      if (this.isMarketIndicatorValid("Steady or Unsure")) {
        this.analysisPanel.setMarketIndicatorIndex(2);
      }
      else {
        this.zomFrame.statusMsg("Could not provide Form Market Indicator", true, 5);
        throw new Exception("AnalysisControl.provideFormValues: bad Market Indicator");
      }
    }
  }

  public void parseCSVFile(ZomFrame_ImportDialog importDialog, File csvFile) {
    // System.out.println("Parsing CSV File");

    AnalysisCSVdataModel csvDataModel = new AnalysisCSVdataModel();

    if (!csvDataModel.scanCSVfile(csvFile)) {
      this.zomFrame.statusMsg("Unable to process CSV file!", true, 5);
      return;
    }

    // Display the CSV File
    CSVDefTableModel csvTableModel = new CSVDefTableModel(csvDataModel);
    importDialog.setCSVdataModel(csvDataModel, csvTableModel);
  }

  public void loadCSVFile(ZomFrame_ImportDialog importDialog) {
    // System.out.println("Got to the loading module");

    AnalysisCSVdataModel csvDataModel = importDialog.getDataModel();

    if (csvDataModel == null) {
      System.out.println("AnalysisControl.loadCSVFile: no data model");
      return;
    }

    int startRow = importDialog.getStartRow();
    int endRow = importDialog.getEndRow();
    int dateCol = importDialog.getDateColumn();
    int valueCol = importDialog.getValueColumn();

    if (startRow < 0 || startRow > endRow || dateCol < 1 ||  valueCol < 1) {
      System.out.println("AnalysisControl.loadCSVFile: illogical row or column selections");
      return;
    }

    String startingDate = ((Vector)csvDataModel.getRowData().get(startRow)).get(dateCol).toString();

    // Use the first row from the CSV file to complete the form
    if (this.isStartingDateValid(startingDate)) {
      this.analysisPanel.setStartingDateText(startingDate);
    }
    else {
      System.out.println("AnalysisControl.loadCSVFile: First Row Date" + startingDate);
      this.zomFrame.statusMsg("Could not determine first row date!", true, 5);
      return;
    }

    this.analysisPanel.setStartingDateText(startingDate);
    String startingValue = ((Vector)csvDataModel.getRowData().get(startRow)).get(valueCol).toString();

    if (this.isStartingStockValid(startingValue)) {
      this.analysisPanel.setStartingStockText(startingValue);
    }
    else {
      System.out.println("First Row Value" + ((Vector)csvDataModel.getRowData().get(startRow)).get(valueCol).toString());
      this.zomFrame.statusMsg("Could not determine first row value!", true, 5);
      return;
    }

    this.analysisPanel.setStartingStockText(startingValue);

    // The trade form is now complete better set any relevant flags
    this.analysisDataModel.tradeFormIncomplete();

    // Add all the CSV vector rows to data model vector
    this.analysisDataModel.transferData(csvDataModel.getRowData(), startRow, endRow, dateCol, valueCol);
    // Inform the Table UI that the data has changed
    this.analysisTableModel.fireTableDataChanged();
    this.displayRunningCalcs();

    if (this.analysisDataModel.hasCSVLoadedOK()) {
      this.zomFrame.statusMsg("CSV loaded!", false, 0);
    }
    else {
      this.zomFrame.statusMsg("CSV has loaded, but some errors were encountered.  Please check results!", true, 9);
    }
  }

  public void assignStartRow(ZomFrame_ImportDialog importDialog, int row) {
    if (row < 0) {
      this.zomFrame.statusMsg("No row selected!", true, 5);
      return;
    }

    importDialog.setStartRow(row);
  }

  public void assignEndRow(ZomFrame_ImportDialog importDialog, int row) {
    if (row < 0) {
      this.zomFrame.statusMsg("No row selected!", true, 5);
      return;
    }

    importDialog.setEndRow(row);
  }

  public boolean assignValueColumn(ZomFrame_ImportDialog importDialog, AnalysisCSVdataModel dataModel, int col) {
    if (col <= 0) {
      this.zomFrame.statusMsg("No column selected!", true, 5);
      return false;
    }

    boolean valid = false;

    try {
      ZomAccuratePrice zomValue = new ZomAccuratePrice();
      zomValue.exhaustiveParse(((Vector)dataModel.getRowData().get(0)).get(col).toString());
      importDialog.setValueColumn(col);
      valid = true;
    } catch (Exception ex) {
      valid = false;
    }

    return valid;
  }

  public boolean assignDateColumn(ZomFrame_ImportDialog importDialog, AnalysisCSVdataModel dataModel, int col) {
    if (col <= 0) {
      this.zomFrame.statusMsg("No column selected!", true, 5);
      return false;
    }

    boolean valid = false;

    try {
      ZomDate zomDate = new ZomDate();
      zomDate.exhaustiveParse(((Vector)dataModel.getRowData().get(0)).get(col).toString());
      importDialog.setDateColumn(col);
      valid = true;
    } catch (Exception ex) {
      valid = false;
    }

    return valid;
  }

  public String getStockName() {
    return this.analysisDataModel.getStockNameString("");
  }

  public ZomCurrency getFinalValue() {
    return this.analysisDataModel.getFinalValue();
  }

  public ZomDate getFinalDate() {
    return this.analysisDataModel.getFinalDate();
  }

  // Abstract Method Provision for Cut n Paste Logic

  public void copyTradeData(TradeCopyBar tradeCopyBar) {
    int rowIdx;

    try {
      rowIdx = this.rowSelected();
    }
    catch (Exception ex) {
      return;
    }

    Vector row = (Vector)this.analysisDataModel.RowData.get(rowIdx);

    tradeCopyBar.recordTradeData(row);
    tradeCopyBar.recordInterestRate(this.analysisDataModel.getInterestRate());
    zomFrame.statusMsg("Copied trade for date: " + row.get(this.analysisDataModel.TRADING_DATE), false, 0);
  }

  public void cutTradeData(TradeCopyBar tradeCopyBar) {
    zomFrame.statusMsg("Cut function not relevant in analysis mode!", true, 5);
  }

  public void pasteTradeData(TradeCopyBar tradeCopyBar) {
    zomFrame.statusMsg("Cut function not relevant in analysis mode!", true, 5);
  }

  private int rowSelected() throws Exception {
    if (this.analysisDataModel == null) {
      System.out.println("AnalysisControl.rowSelected: Missing Data Model");
      throw new Exception();
    }

    if (this.zomFrame == null) {
      System.out.println("AnalysisControl.rowSelected: Missing Zom User Interface");
      throw new Exception();
    }

    int[] rowIndex;
    int rowCount;

    rowCount = this.getSelectedRowCount();
    rowIndex = this.getSelectedRows();

    if (rowCount == 0) {
      System.out.println("AnalysisControl.rowSelected: no row selected");
      zomFrame.statusMsg("No trade selected!", true, 5);
      throw new Exception();
    }

    if (rowCount != 1) {
      System.out.println("AnalysisControl.rowSelected: not one row selected");
      zomFrame.statusMsg("Only one trade may be copied at a time!", true, 5);
      throw new Exception();
    }

    return rowIndex[0];
  }
}