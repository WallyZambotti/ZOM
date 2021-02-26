package com.byotrader.zom;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Vector;
import java.net.InetAddress;
import java.text.SimpleDateFormat;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public class PortfolioControl extends ZomController {
  private PortfolioPanel portfolioPanel;
  private PortfolioDataModel portfolioDataModel;
  private PortfolioDefTableModel portfolioTableModel;
  private ZomFrame zomFrame;
  private String recordedFileName;

  public void setDataModel(PortfolioDataModel dataModel) {
    this.portfolioDataModel = dataModel;
  }

  public PortfolioDataModel getDataModel() {
    return this.portfolioDataModel;
  }

  public void setTableModel(PortfolioDefTableModel tableModel) {
    this.portfolioTableModel = tableModel;
  }

  public PortfolioDefTableModel getTableModel() {
    return this.portfolioTableModel;
  }

  public void setFrame(ZomFrame frame) {
    this.zomFrame = frame;
  }

  public ZomFrame getFrame() {
    return this.zomFrame;
  }

  public void addStock() {
    if (portfolioDataModel == null) {
      System.out.println("PorfolioControl.addstock: Missing Data Model");
      return;
    }

    if (zomFrame == null) {
      System.out.println("PorfolioControl.addstock: Missing Zom User Interface");
      return;
    }

    this.portfolioPanel.displayNewStockDialog("", "");
  }

  public void deleteStock() {
    if (portfolioDataModel == null) {
      System.out.println("PorfolioControl.deleteStock: Missing Data Model");
      return;
    }

    if (zomFrame == null) {
      System.out.println("PorfolioControl.deleteStock: Missing Zom User Interface");
      return;
    }

    int rowIndex;

    rowIndex = this.getSelectedRow();

    if (rowIndex == -1) {
      System.out.println("PorfolioControl.deleteStock: no row selected");
      zomFrame.statusMsg("No stock selected!", true, 5);
      return;
    }

    this.portfolioPanel.displayConfirmationDialog(
      "Deleting a stock removes the stock\n" +
      "from the Portfolio list as well as\n" +
      "deleting the associated stock file\n" +
      "and its contents.    Are you sure?\n" +
      "        Delete to confirm.\n" +
      "        Abort to reject.");
  }

  public void confirmedDeleteStock() {
    if (portfolioDataModel == null) {
      System.out.println("PorfolioControl.deleteStock: Missing Data Model");
      return;

    }

    if (zomFrame == null) {
      System.out.println("PorfolioControl.deleteStock: Missing Zom User Interface");
      return;
    }

    int rowIndex;

    rowIndex = this.getSelectedRow();

    if (rowIndex == -1) {
      System.out.println("PorfolioControl.deleteStock: no row selected");
      zomFrame.statusMsg("No stock selected!", true, 5);
      return;
    }

    String stockName = this.portfolioDataModel.getStockName(rowIndex);

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
      this.portfolioDataModel.deleteNode(stockName);
    }
    catch (Exception ex) {
      zomFrame.statusMsg("Failed to delete stock!", true, 5);
      return;
    }

    // now delete all internal representation of the data
    this.portfolioTableModel.removeRow(rowIndex);

    // Inform Zom Control that the stock has been deleted
    this.zomFrame.getZomControl().stockDeleted();

    // Delete the associated Analysis File
    String stockFilename = "AnalysisData/" + stockName + ".xml";

    File stockFile = new File(stockFilename);

    try {
      if (stockFile.exists()) {
        stockFile.delete();
      }
      else {
        System.out.println("PortfolioControl.deleteStock: missing analysis file: " + stockFilename);
      }
    }
    catch (Exception ex) {
      System.out.println("PortfolioControl.deleteStock: could not delete analysis file: " + stockFilename);
      this.zomFrame.statusMsg("Could not delete analysis file.", true, 5);
      return;
    }

    // Delete the associated Trade File
    stockFilename = "TradeData/" + stockName + ".xml";

    stockFile = new File(stockFilename);

    try {
      if (stockFile.exists()) {
        stockFile.delete();
      }
      else {
        System.out.println("PortfolioControl.deleteStock: missing trade file: " + stockFilename);
      }
    }
    catch (Exception ex) {
      System.out.println("PortfolioControl.deleteStock: could not delete trade file: " + stockFilename);
      this.zomFrame.statusMsg("Could not delete trade file.", true, 5);
      return;
    }
  }

  public void duplicateStock() {
    if (portfolioDataModel == null) {
      System.out.println("PorfolioControl.duplicateStock: Missing Data Model");
      return;
    }

    if (zomFrame == null) {
      System.out.println("PorfolioControl.duplicateStock: Missing Zom User Interface");
      return;
    }

    int rowIndex;

    rowIndex = this.getSelectedRow();

    if (rowIndex == -1) {
      System.out.println("PorfolioControl.duplicateStock: no row selected");
      zomFrame.statusMsg("No stock selected!", true, 5);
      return;
    }

    String stockName = this.portfolioDataModel.getStockName(rowIndex);
    String stockCode = this.portfolioDataModel.getStockCode(rowIndex);

    int uniqueID = 0;
    String uniqueStockName;

    do {
      uniqueStockName = stockName + " Copy " + String.valueOf(++uniqueID);
    }
    while (this.portfolioDataModel.existsNode(uniqueStockName));

    this.recordedFileName = ""; //reset
    this.portfolioPanel.displayNewStockDialog(uniqueStockName, stockCode);

    if (this.recordedFileName.compareTo("") == 0) {
      return;
    }

    // Now copy the files
    // Copy the associated Analysis File
    String stockFilename = "AnalysisData/" + stockName + ".xml";
    String uniqueStockFilename = "AnalysisData/" + this.recordedFileName + ".xml";

    File stockFile = new File(stockFilename);

    try {
      if (stockFile.exists()) {
        this.portfolioDataModel.copyFile(stockFile, uniqueStockFilename);
      }
      else {
        System.out.println("PortfolioControl.duplicateStock: missing analysis file: " + stockFilename);
      }
    }
    catch (Exception ex) {
      System.out.println("PortfolioControl.duplicateStock: could not duplicate analysis file: " + stockFilename);
      this.zomFrame.statusMsg("Could not duplicate analysis file.", true, 5);
      return;
    }

    // Copy the associated Trade File
    String tradeFilename = "TradeData/" + stockName + ".xml";
    String uniqueTradeFilename = "TradeData/" + this.recordedFileName + ".xml";

    File tradeFile = new File(tradeFilename);

    try {
      if (tradeFile.exists()) {
        this.portfolioDataModel.copyFile(tradeFile, uniqueTradeFilename);
      }
      else {
        System.out.println("PortfolioControl.duplicateStock: missing trade file: " + stockFilename);
      }
    }
    catch (Exception ex) {
      System.out.println("PortfolioControl.duplicateStock: could not duplicate trade file: " + stockFilename);
      this.zomFrame.statusMsg("Could not duplicate trade file.", true, 5);
      return;
    }
  }

  public boolean verifyNewStock(String stockName, String stockCode) {
    if (stockName.trim().compareTo("") == 0) {
      this.zomFrame.statusMsg("No stock name provided.  Use cancel if aborting!", true, 5);
      return false;
    }

    if (stockCode.trim().compareTo("") == 0) {
      this.zomFrame.statusMsg("No stock code provided.", true, 5);
      return false;
    }

    if (this.portfolioDataModel.existsNode(stockName)) {
      this.zomFrame.statusMsg("Stock already exists.", true, 5);
      return false;
    }

    boolean stockFileCreated;
    String stockFilename = "AnalysisData/" + stockName + ".xml";

    File stockFile = new File(stockFilename);

    try {
      stockFileCreated = stockFile.createNewFile();
    }
    catch (IOException ex) {
      this.zomFrame.statusMsg("Stock name does not also represent a valid file name.", true, 5);
      return false;
    }

    if (stockFileCreated) {
      stockFile.delete();
    }
    else {
      this.zomFrame.statusMsg("Stock name is unexpectedly not unique.", true, 5);
      return false;
    }

    // SimpleDateFormat dateFormat = new SimpleDateFormat("d/MM/yyyy");

    Vector newStock;

    try {
      newStock = this.portfolioDataModel.addStock(
        stockName,
        stockCode,
        new ZomDate(),
        new ZomCurrency("0.0"),
        new ZomDate(),
        new ZomCurrency("0.0"));
    } catch (Exception e) {
      e.printStackTrace(System.err);
      return false;
    }

    this.portfolioDataModel.addRow(newStock);
    //this.portfolioDataModel.RowData.add(newStock);
    this.portfolioTableModel.fireTableDataChanged();

    this.zomFrame.statusMsg("New stock created!", true, 5);
    this.recordedFileName = stockName; //required by duplicateStock
    return true;
  }

  public int getSelectedRow() {
    return this.portfolioPanel.jTablePortfolio.getSelectedRow();
  }

  public Vector getSelectedStock(int row) {
    if (row < 0 || row >= this.portfolioPanel.jTablePortfolio.getRowCount()) {
      return null;
    }

    return this.portfolioDataModel.getRowVector(row);
  }

  public void revealUI() {
    this.zomFrame.setZomPanel(portfolioPanel);
  }

  public void savePortfolioData() {
    this.portfolioDataModel.saveXML();
  }

  public PortfolioControl(ZomFrame frame) {
    if (frame == null) {
      System.out.println("PortfolioControl: missing user interface");
      return;
    }

    this.zomFrame = frame;

    try {
      portfolioDataModel = new PortfolioDataModel("portfolio.xml");
    }
    catch (Exception ex) {
      this.zomFrame.statusMsg("Failed to open Portfolio", true, 10);
      return;
    }

    portfolioTableModel  = new PortfolioDefTableModel(portfolioDataModel);
    portfolioPanel = new PortfolioPanel(portfolioTableModel, this);
  }

  public void updateAnalysisSummary(String stockName, ZomCurrency newValue, ZomDate newDate) {
    if (stockName.trim().compareTo("") == 0) {
      System.out.println("PortfolioControl.updateAnalysisSummary:No stock name provided.");
      return;
    }

    if (!this.portfolioDataModel.existsNode(stockName)) {
      System.out.println("PortfolioControl.updateAnalysisSummary:Stock name not found.");
      return;
    }

    try {
      this.portfolioDataModel.updateStock(stockName, null, null, newDate, newValue, null, null);
    }
    catch (Exception ex) {
      zomFrame.statusMsg("Failed to update stock!", true, 5);
      return;
    }

    // Need to re-read data from file
    // Dont forget to save any new memory data first
    this.portfolioDataModel.saveXML();
    // Reload all data
    this.portfolioDataModel.loadData(1);

    // System.out.println(stockName + " " + newValue + " " + newDate);
  }

  public void updateTradeSummary(String stockName, ZomCurrency newValue, ZomDate newDate) {
    if (stockName.trim().compareTo("") == 0) {
      System.out.println("PortfolioControl.updateTradeSummary:No stock name provided.");
      return;
    }

    if (!this.portfolioDataModel.existsNode(stockName)) {
      System.out.println("PortfolioControl.updateTradeSummary:Stock name not found.");
      return;
    }

    try {
      this.portfolioDataModel.updateStock(stockName, null, null, null, null, newDate, newValue);
    }
    catch (Exception ex) {
      zomFrame.statusMsg("Failed to update stock!", true, 5);
      return;
    }

    // Need to re-read data from file
    // Dont forget to save any new memory data first
    this.portfolioDataModel.saveXML();
    // Reload all data
    this.portfolioDataModel.loadData(1);

    // System.out.println(stockName + " " + newValue + " " + newDate);
  }

  // Abstract Method Provision for Cut n Paste Logic

  public void copyTradeData(TradeCopyBar tradeCopyBar) {
    zomFrame.statusMsg("Copy function not relevant in portfolio mode!", true, 5);
  }

  public void cutTradeData(TradeCopyBar tradeCopyBar) {
    zomFrame.statusMsg("Cut function not relevant in portfolio mode!", true, 5);
  }

  public void pasteTradeData(TradeCopyBar tradeCopyBar) {
    zomFrame.statusMsg("Paste function not relevant in portfolio mode!", true, 5);
  }

  public void checkRegistration() {
    if (!this.portfolioDataModel.isRegistered()) {
      //this.portfolioPanel.displayRegistrationConditions();
      this.portfolioPanel.displayRegistrationDialog("", "");
    }
  }

  public boolean registerProduct(String key, String code) {
    try {
      ZomEnigma.setKey(key);
    }
    catch (Exception ex) {
      this.zomFrame.statusMsg("Incorrect Key or Code, try again or cancel!", true, 10);
      return false;
    }

    System.out.println("Code: " + code);
    String decrypted = ZomEnigma.getDecryptedString(code);

    boolean registered = decrypted.regionMatches(0, "Zom 1.0: ", 0, 9);
    ZomString.setAnsiStrings(registered);

    if (registered) {
      try {
        ZomEnigma.setKey(InetAddress.getLocalHost().getHostName());
      }
      catch (Exception ex) {
        ZomEnigma.setKey("localhost");
      }
      String encryptedCode = ZomEnigma.getEncryptedString(code);
      this.portfolioDataModel.setRegKey(key);
      this.portfolioDataModel.setRegCode(encryptedCode);
      this.portfolioPanel.displayRegistrationConfirmation();
    }
    else {
      this.zomFrame.statusMsg("Incorrect Key or Code, try again or cancel!", true, 10);
    }

    return registered;
  }
}