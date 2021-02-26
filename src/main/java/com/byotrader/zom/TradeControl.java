package com.byotrader.zom;

import java.io.File;
import java.util.Vector;
import java.util.GregorianCalendar;
import java.util.Date;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public class TradeControl extends ZomController {
  private ZomFrame zomFrame;
  private TradeDataModel tradeDataModel;
  private TradeDefTableModel tradeTableModel;
  private TradePanel tradePanel;
  private String currentStock;
  private boolean frequencyChanged;
  private String recordedCash;
  private String recordedInterest;

  public void setFrame(ZomFrame frame) {
    this.zomFrame = frame;
  }

  public ZomFrame getFrame() {
    return this.zomFrame;
  }

  public TradeControl(ZomFrame frame) {
    this.zomFrame = frame;
  }

  public void revealUI(Vector stockIdentity) {
    if (stockIdentity == null) {
      System.out.println("TradeControl.revealUI: missing stock identity!");
      return;
    }

    String stockName = "TradeData/" + ((ZomInfo)stockIdentity.get(0)).getString() + ".xml";

    // If the current stock is different to the indentified stock
    if (this.currentStock != null && this.currentStock.compareTo(stockName) != 0) {
      System.out.println("TradeControl.revealUI: saving - " + this.currentStock);
      // Save the current stock ready for the new to be opened
      File stockFile = new File(this.currentStock);
      this.tradeDataModel.saveXML();
      // Cause a change in stock
      this.currentStock = null;
    }

    // Either a new stock or a change in stock
    if (this.currentStock == null) {
      System.out.println("TradeControl.revealUI: opening - " + stockName);
      // Prepare the data, table, and panel with the new stock
      try {
        this.tradeDataModel = new TradeDataModel(stockName);
      }
      catch (Exception ex) {
        this.zomFrame.statusMsg("Failed to open Trade Model", true, 10);
        return;
      }

      this.tradeTableModel  = new TradeDefTableModel(this.tradeDataModel);
      this.tradePanel = new TradePanel(this.tradeTableModel, this);
      this.currentStock = stockName;

      // Populate the Form
      this.fillForm(stockIdentity);
    }

    // Instruct the main frame to display the new stock
    this.zomFrame.setZomPanel(this.tradePanel);
  }

  public void saveTradeData() {
    this.tradeDataModel.saveXML();
  }

  private void fillForm(Vector stockIdentity) {
    // The stock Name and Code can come from the Portfolio Table model
    this.tradePanel.setStockNameText(((ZomInfo)stockIdentity.get(0)).getString());
    this.tradeDataModel.setStockName(((ZomInfo)stockIdentity.get(0)).getString());
    this.tradePanel.setStockCodeText(((ZomInfo)stockIdentity.get(1)).getString());
    this.tradeDataModel.setStockCode(((ZomInfo)stockIdentity.get(1)).getString());

    // The rest has to come from the Trade Data model
    this.tradePanel.setStartingUnitsText(this.tradeDataModel.getPortfolioStartingValueString(""));
    this.tradePanel.setStartingStockText(this.tradeDataModel.getStartingStockPriceString(""));
    this.tradePanel.setStartingDateText(this.tradeDataModel.getStartingDateString(""));
    this.tradePanel.setInterestRateText(this.tradeDataModel.getInterestRateString(""));
    this.tradePanel.setBrokerageFeeText(this.tradeDataModel.getBrokerageFeeString(""));
    this.tradePanel.setCashReserveText(this.tradeDataModel.getStartingCashReserveString(""));

    // Display Running Calcs
    this.displayRunningCalcs();
  }

  private void displayRunningCalcs() {
    this.tradePanel.setZomComparisonText(this.tradeDataModel.getRunningZomValue(""));
    this.tradePanel.setSingleComparisonText(this.tradeDataModel.getRunningSingleValue(""));
  }

  public boolean isStartingPortfolioValid(String value) {
    boolean valid = false;

    if (value.trim().compareTo("") == 0) {
      this.tradeDataModel.setPortfolioStartingValue(null);
      return true;
    }

    try {
      ZomCounter zomValue = new ZomCounter();
      zomValue.exhaustiveParse(value);
      this.tradeDataModel.setPortfolioStartingValue(zomValue);
      valid = true;
    } catch (Exception ex) {
      this.zomFrame.statusMsg("Invalid Starting Portfolio Value", true, 5);
      valid = false;
    }
    return valid;
  }

  public boolean isStartingStockValid(String value) {
    boolean valid = false;

    if (value.trim().compareTo("") == 0) {
      this.tradeDataModel.setStartingStockPrice(null);
      return true;
    }

    try {
      ZomAccuratePrice zomValue = new ZomAccuratePrice();
      zomValue.exhaustiveParse(value);
      this.tradeDataModel.setStartingStockPrice(zomValue);
      valid = true;
    } catch (Exception ex) {
      this.zomFrame.statusMsg("Invalid Starting Stock Value", true ,5);
      valid = false;
    }
    return valid;
  }

  public boolean isStartingDateValid(String value) {
    boolean valid = false;

    if (value.trim().compareTo("") == 0) {
      this.tradeDataModel.setStartingDate(null);
      return true;
    }

    try {
      ZomDate zomDate = new ZomDate();
      zomDate.exhaustiveParse(value);
      this.tradeDataModel.setStartingDate(zomDate);
      valid = true;
    } catch (Exception ex) {
      this.zomFrame.statusMsg("Invalid Starting Date", true, 5);
      valid = false;
    }
    return valid;
  }

  public boolean isInterestRateValid(String value) {
    boolean valid = false;

    if (value.trim().compareTo("") == 0) {
      this.tradeDataModel.setInterestRate(null);
      return true;
    }

    try {
      ZomPercent zomValue = new ZomPercent();
      zomValue.exhaustiveParse(value);
      this.tradeDataModel.setInterestRate(zomValue);
      valid = true;
    } catch (Exception ex) {
      this.zomFrame.statusMsg("Invalid Interest Rate", true, 5);
      valid = false;
    }
    return valid;
  }

  public boolean isBrokerageFeeValid(String value) {
    boolean valid = false;

    if (value.trim().compareTo("") == 0) {
      this.tradeDataModel.setBrokerageFee(null);
      return true;
    }

    try {
      ZomCurrency zomValue = new ZomCurrency();
      zomValue.exhaustiveParse(value);
      this.tradeDataModel.setBrokerageFee(zomValue);
      valid = true;
    } catch (Exception ex) {
      this.zomFrame.statusMsg("Invalid Brokerage Fee", true, 5);
      valid = false;
    }
    return valid;
  }

  public boolean isCashReserveValid(String value) {
    boolean valid = false;

    if (value.trim().compareTo("") == 0) {
      this.tradeDataModel.setStartingCashReserve(null);
      return true;
    }

    try {
      ZomCurrency zomValue = new ZomCurrency();
      zomValue.exhaustiveParse(value);
      this.tradeDataModel.setStartingCashReserve(zomValue);
      valid = true;
    } catch (Exception ex) {
      this.zomFrame.statusMsg("Invalid Starting Cash Reserve", true, 5);
      valid = false;
    }
    return valid;
  }

  public void addSample() {
    if (tradeDataModel == null) {
      System.out.println("TradeControl.addSample: Missing Data Model");
      return;
    }

    if (zomFrame == null) {
      System.out.println("TradeControl.addSample: Missing Zom User Interface");
      return;
    }

    if (this.tradeDataModel.tradeFormIncomplete()) {
      zomFrame.statusMsg("You must complete the trade form before adding!", true, 5);
      return;
    }

    int[] rowIndex;
    int rowCount;
    String tradeDate;
    String tradePrice;
    String cash;
    String interest;
    String brokerage;
    Date lastDate;
    double thisInterest;

    int lastRow = this.tradeDataModel.RowData.size() - 1;

    if (lastRow < 0) {
      lastDate = this.tradeDataModel.getStartingDate().getDate();
      tradePrice = this.tradeDataModel.getStartingStockPriceString("");
      cash = this.tradeDataModel.getStartingCashReserveString("");
      thisInterest = this.tradeDataModel.getStartingCashReserve().getValue() * this.tradeDataModel.getInterestRate().getValue() / 365.0;
    }
    else {
      lastDate = this.tradeDataModel.getTradeDate(lastRow).getDate();
      tradePrice = this.tradeDataModel.getTradePrice(lastRow).toString();
      cash = this.tradeDataModel.getCash(lastRow).toString();
      thisInterest = this.tradeDataModel.getCash(lastRow).getValue() * this.tradeDataModel.getInterestRate().getValue() / 365.0;
    }

    GregorianCalendar nextDate = new GregorianCalendar();
    ZomDate nextDay = new ZomDate();

    nextDate.setTime(lastDate);
    nextDate.add(GregorianCalendar.DATE, 1);
    nextDay.setValue(nextDate.getTime().getTime());
    tradeDate = nextDay.toString();

    try {
      ZomCurrency zomInterest = new ZomCurrency(thisInterest);
      interest = zomInterest.toString();
    }
    catch (Exception ex) {
      interest = "$0.00";
    }
    brokerage = this.tradeDataModel.getBrokerageFeeString("");

    this.recordedCash = cash;
    this.recordedInterest = interest;
    this.tradePanel.displayNewSampleDialog(tradeDate, tradePrice, cash, interest, brokerage, "");
  }

  public boolean verifyNewTrade(String shareDate, String sharePrice, String cash, String interest, String brokerage, String traded) {
    ZomDate zomDate;
    ZomAccuratePrice zomPrice;
    ZomCurrency zomCash, zomTmpCash, zomInterest, zomTmpInterest, zomBrokerage;
    ZomCounter zomTraded;

    try {
      zomDate = new ZomDate();
      zomDate.exhaustiveParse(shareDate);
    } catch (Exception ex) {
      this.zomFrame.statusMsg("Invalid Trading Date", true, 5);
      return false;
    }

    try {
      zomPrice = new ZomAccuratePrice();
      zomPrice.exhaustiveParse(sharePrice);
    } catch (Exception ex) {
      this.zomFrame.statusMsg("Invalid Share Price", true, 5);
      return false;
    }

    try {
      zomCash = null;
      zomTmpCash = new ZomCurrency();
      zomTmpCash.exhaustiveParse(cash);
      if (this.recordedCash.compareTo(cash) != 0) {
        zomCash = zomTmpCash;
      }
    } catch (Exception ex) {
      this.zomFrame.statusMsg("Invalid cash amount", true, 5);
      return false;
    }

    try {
      zomInterest = null;
      zomTmpInterest = new ZomCurrency();
      zomTmpInterest.exhaustiveParse(interest);
      if (this.recordedInterest.compareTo(interest) != 0) {
        zomInterest = zomTmpInterest;
      }
    } catch (Exception ex) {
      this.zomFrame.statusMsg("Invalid interest amount", true, 5);
      return false;
    }

    try {
      zomBrokerage = new ZomCurrency();
      zomBrokerage.exhaustiveParse(brokerage);
    } catch (Exception ex) {
      this.zomFrame.statusMsg("Invalid brokerage amount", true, 5);
      return false;
    }

    try {
      zomTraded = new ZomCounter();
      zomTraded.exhaustiveParse(traded);
    } catch (Exception ex) {
      this.zomFrame.statusMsg("Invalid units traded amount", true, 5);
      return false;
    }

    // Ensure that the new date is not less then or the same as the last entry date
    ZomDate lastDate;
    int lastRow = this.tradeDataModel.RowData.size() - 1;

    if (lastRow < 0) {
      lastDate = this.tradeDataModel.getStartingDate();
    }
    else {
      lastDate = this.tradeDataModel.getTradeDate(lastRow);
    }

    if (zomDate.compareTo(lastDate) <= 0) {
      this.zomFrame.statusMsg("Trading date may not preceed last entry Date", true, 5);
      return false;
    }

    // Ensure no over spending/selling
    if (zomTraded.getLong() > 0) {
      // Buying
      if ((zomTraded.getValue() * zomPrice.getValue()) > (zomTmpCash.getValue() - zomBrokerage.getValue() + zomTmpInterest.getValue())) {
        this.zomFrame.statusMsg("Purchase amount exceeds funds", true, 5);
        return false;
      }
    }
    else if (zomTraded.getLong() < 0) {
      // Selling
      long lastUnits;

      if (lastRow < 0) {
        lastUnits = this.tradeDataModel.getPortfolioStartingValue().getLong();
      }
      else {
        lastUnits = this.tradeDataModel.getUnits(lastRow).getLong();
      }

      if (-zomTraded.getLong() > lastUnits) {
        this.zomFrame.statusMsg("Attempt to sell more shares than owned", true, 5);
        return false;
      }
    }

    Vector newSample = this.tradeDataModel.addStockData(
      zomDate, zomPrice, zomCash, zomInterest, zomBrokerage, zomTraded);
    this.tradeDataModel.addRow(newSample);
    //this.tradeDataModel.RowData.add(newSample);
    this.tradeTableModel.fireTableDataChanged();

    // Need to re-read data from file
    // Dont forget to save any new memory data first
    this.tradeDataModel.saveXML();
    // Reload all data
    this.tradeDataModel.loadData(1);

    //  Display the new running calcs
    this.displayRunningCalcs();

    this.zomFrame.statusMsg("New trade entered!", false, 0);
    return true;
  }

  // Recalculate all trades.  Fortunately the data model can do this all from data
  // stored in memory.
  public void zomCalculate() {
    if (this.tradeDataModel.tradeFormIncomplete()) {
      this.zomFrame.statusMsg("Trading form is incomplete!", true, 5);
    }

    // Dont forget to save any new memory data first
    this.tradeDataModel.saveXML();
    // Reload all data
    this.tradeDataModel.loadData(1);
    // Inform the Table UI that the data has changed
    this.tradeTableModel.fireTableDataChanged();
    this.displayRunningCalcs();
  }

  public void deleteSample() {
    if (this.tradeDataModel == null) {
      System.out.println("TradeControl.deleteSample: Missing Data Model");
      return;
    }

    if (this.zomFrame == null) {
      System.out.println("TradeControl.deleteSample: Missing Zom User Interface");
      return;
    }

    int[] rowIndex;
    int rowCount;

    rowCount = this.getSelectedRowCount();
    rowIndex = this.getSelectedRows();

    if (rowCount == 0) {
      System.out.println("TradeControl.deleteSample: no row selected");
      zomFrame.statusMsg("No trade selected!", true, 5);
      return;
    }

    if (rowCount == 1) {
      this.tradePanel.displayConfirmationDialog(
        "You are deleting the trade on date\n" +
        "  " + (ZomDate)((Vector)this.tradeDataModel.RowData.get(rowIndex[0])).get(this.tradeDataModel.TRADING_DATE) + "\n" +
        //"  " + this.tradeDataModel.getTradeDateString(rowIndex[0]) + "\n" +
        "If this is correct then press:\n" +
        "        Delete to confirm.\n" +
        "        Abort to reject.");
    }
    else {
      this.tradePanel.displayConfirmationDialog(
        "You are deleting a total of (" + rowCount + ") trades.\n" +
        "If this is correct then press:\n" +
        "        Delete to confirm.\n" +
        "        Abort to reject.");
    }
  }

  public int getSelectedRow() {
    return this.tradePanel.jTableTrade.getSelectedRow();
  }

  public int[] getSelectedRows() {
    return this.tradePanel.jTableTrade.getSelectedRows();
  }

  public int getSelectedRowCount() {
    return this.tradePanel.jTableTrade.getSelectedRowCount();
  }

  public void confirmedDeleteSample() {
    if (this.tradeDataModel == null) {
      System.out.println("TradeControl.confirmedDeleteSample: Missing Data Model");
      return;
    }

    if (this.zomFrame == null) {
      System.out.println("TradeControl.confirmedDeleteSample: Missing Zom User Interface");
      return;
    }

    int[] rowIndex;
    int rowCount;

    rowCount = this.getSelectedRowCount();
    rowIndex = this.getSelectedRows();

    if (rowCount == 0) {
      System.out.println("TradeControl.confirmedDeleteSample: no row selected");
      zomFrame.statusMsg("No trade selected!", true, 5);
      return;
    }

    while(rowCount > 0) {
      int idx = rowIndex[--rowCount];
      String tradeDate = this.tradeDataModel.getTradeDateString(idx);
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
        this.tradeDataModel.deleteNode(tradeDate);
      }
      catch (Exception ex) {
        zomFrame.statusMsg("Failed to delete trade data!", true, 5);
        return;
      }

      // now delete all internal representation of the data
      this.tradeTableModel.removeRow(idx);
    }

    // Calculations for all other rows need to be reapplied and running calcs displayed
    // Dont forget to save any new memory data first
    this.tradeDataModel.saveXML();
    // Reload all data
    this.tradeDataModel.loadData(1);
    this.displayRunningCalcs();
  }

  public void deleteAllSamples() {
    if (this.tradeDataModel == null) {
      System.out.println("TradeControl.deleteSample: Missing Data Model");
      return;
    }

    if (this.zomFrame == null) {
      System.out.println("TradeControl.deleteSample: Missing Zom User Interface");
      return;
    }

    this.tradePanel.displayDelAllConfDialog(
      "You have requested the deletion\n" +
      "of all the trade data\n" +
      "If this is correct then press:\n" +
      "        Delete to confirm.\n" +
      "        Abort to reject.");
  }

  public void confirmedDeleteAllSamples() {
    if (this.tradeDataModel == null) {
      System.out.println("TradeControl.confirmedDeleteSample: Missing Data Model");
      return;
    }

    if (this.zomFrame == null) {
      System.out.println("TradeControl.confirmedDeleteSample: Missing Zom User Interface");
      return;
    }

    this.tradeDataModel.deleteAllNodes();
    this.tradeDataModel.RowData.clear();

    // Inform the Table UI that the data has changed
    this.tradeTableModel.fireTableDataChanged();
    this.displayRunningCalcs();
  }

  public String getStockName() {
    return this.tradeDataModel.getStockNameString("");
  }

  public ZomCurrency getFinalValue() {
    return this.tradeDataModel.getFinalValue();
  }

  public ZomDate getFinalDate() {
    return this.tradeDataModel.getFinalDate();
  }

  // Abstract Method Provision for Cut n Paste Logic

  public void copyTradeData(TradeCopyBar tradeCopyBar) {
    zomFrame.statusMsg("Copy function not relevant in trade mode!", true, 5);
  }

  public void cutTradeData(TradeCopyBar tradeCopyBar) {
    zomFrame.statusMsg("Cut function not relevant in trade mode!", true, 5);
  }

  public void pasteTradeData(TradeCopyBar tradeCopyBar) {
    if (tradeCopyBar.recallTradeData() == null) {
      this.zomFrame.statusMsg("Nothing Copied!", true, 5);
      return;
    }

    int choice = 2;

    if (this.tradeDataModel.tradeFormIncomplete()) {
      choice = this.tradePanel.displayCopyPasteDialog(false,
        "Yor are pasting data from the analysis module\n" +
        "dated: " + tradeCopyBar.recallTradeData().get(this.tradeDataModel.TRADING_DATE) + "\n" +
        "Please confirm:\n" +
        "        OK\n" +
        "        Cancel");

      switch (choice) {
        case 1: return; // chose to cancel
        case 0: this.pasteFormData(tradeCopyBar);break;
        default: return;
      }
    }
    else {
      choice = this.tradePanel.displayCopyPasteDialog(true,
        "Yor are pasting data from the analysis module\n" +
        "dated: " + tradeCopyBar.recallTradeData().get(this.tradeDataModel.TRADING_DATE) + "\n" +
        "Please select where to paste:\n" +
        "        Form\n" +
        "        New Entry\n" +
        "        Cancel (abort the paste)");

      switch (choice) {
        case 2: return; // chose to cancel
        case 1: this.pasteNewEntry(tradeCopyBar);break;
        case 0: this.pasteFormData(tradeCopyBar);break;
        default: return;
      }
    }

    this.zomFrame.statusMsg(" ", false, 0); // clear previous copy message
  }

  public void pasteNewEntry(TradeCopyBar copiedData) {
    // The cash from the analysis row may not reflect the cash & interest that is in
    // the trading data.  So it is safer to use the trading data cash amount instead!
    Vector analysisData = copiedData.recallTradeData();
    ZomCurrency previousCash;
    ZomCurrency interest = null;
    ZomDate previousDate;
    ZomDate thisDate;

    thisDate = (ZomDate)analysisData.get(this.tradeDataModel.TRADING_DATE);
//    Vector previousTradeData = this.tradeDataModel.getRowBefore(
//      (ZomDate)analysisData.get(this.tradeDataModel.TRADING_DATE));
//
//    if ( previousTradeData == null) {
//      previousCash = this.tradeDataModel.getStartingCashReserve();
//      previousDate = this.tradeDataModel.getStartingDate();
//    }
//    else {
//      previousCash = (ZomCurrency)previousTradeData.get(this.tradeDataModel.CASH);
//      previousDate = (ZomDate)previousTradeData.get(this.tradeDataModel.TRADING_DATE);
//    }

    int lastRow = this.tradeDataModel.RowData.size() - 1;

    if (lastRow < 0) {
      previousCash = this.tradeDataModel.getStartingCashReserve();
      previousDate = this.tradeDataModel.getStartingDate();
    }
    else {
      previousCash = this.tradeDataModel.getCash(lastRow);
      previousDate = this.tradeDataModel.getTradeDate(lastRow);
    }

    try {
      interest = new ZomCurrency(
        this.tradeDataModel.calcInterest(
          previousDate,
          thisDate,
          previousCash.getValue(),
          this.tradeDataModel.getInterestRate().getValue()));
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    this.recordedCash = previousCash.toString();
    this.recordedInterest = interest.toString();

    // Fortunately the Analysis & Trade vector maintain their data in the same order
    // so it is ok to use the trade data model constants here.
    this.tradePanel.displayNewSampleDialog(
      ((ZomDate)analysisData.get(this.tradeDataModel.TRADING_DATE)).toString(),
      ((ZomAccuratePrice)analysisData.get(this.tradeDataModel.STOCK_PRICE)).toString(),
      this.recordedCash,
      this.recordedInterest,
      ((ZomCurrency)analysisData.get(this.tradeDataModel.BROKERAGE)).toString(),
      ((ZomCounter)analysisData.get(this.tradeDataModel.TRADED)).toString());
  }

  public void pasteFormData(TradeCopyBar copiedData) {
    Vector tradeData = copiedData.recallTradeData();
    ZomPercent intRate = copiedData.recallInterestRate();

    this.tradePanel.setStartingUnitsText(
      ((ZomCounter)tradeData.get(this.tradeDataModel.UNITS)).toString());
    this.tradePanel.setStartingStockText(
      ((ZomAccuratePrice)tradeData.get(this.tradeDataModel.STOCK_PRICE)).toString());
    this.tradePanel.setCashReserveText(
      ((ZomCurrency)tradeData.get(this.tradeDataModel.CASH)).toString());
    this.tradePanel.setStartingDateText(
      ((ZomDate)tradeData.get(this.tradeDataModel.TRADING_DATE)).toString());
    this.tradePanel.setBrokerageFeeText(
      ((ZomCurrency)tradeData.get(this.tradeDataModel.BROKERAGE)).toString());
    this.tradePanel.setInterestRateText(intRate.toString());

    // Need to trigger the verification of form data
    // which will also trigger the save to the data file
    this.tradePanel.jTextFieldStartingUnits_focusLost(null);
    this.tradePanel.jTextFieldStartingStock_focusLost(null);
    this.tradePanel.jTextFieldStartingDate_focusLost(null);
    this.tradePanel.jTextFieldBrokerageFee_focusLost(null);
    this.tradePanel.jTextFieldCashReserve_focusLost(null);
    this.tradePanel.jTextFieldInterestRate_focusLost(null);
  }
}