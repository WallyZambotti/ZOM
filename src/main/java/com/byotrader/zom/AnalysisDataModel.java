package com.byotrader.zom;

import java.util.Vector;
import java.util.Iterator;
import java.util.Arrays;
import java.util.Comparator;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.apache.xpath.XPathException;

import java.text.SimpleDateFormat; // temp

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public class AnalysisDataModel extends XMLDataModel {
  Attr tmpAttr;
  // Constants
  protected static final int TRADING_DATE = 0;
  protected static final int STOCK_PRICE = 1;
  protected static final int STOCK_VALUE = 2;
  protected static final int CASH = 3;
  protected static final int INTEREST = 4;
  protected static final int BROKERAGE = 5;
  protected static final int TRADED = 6;
  protected static final int UNITS = 7;
  protected static final int PORTFOLIO_VALUE = 8;
  protected static final int PERFORMANCE = 9;
  protected static final int ORDER = 10;
  protected static final int PORTFOLIO_CONTROL = 11;

  private boolean dataHasChanged = false;
  private boolean csvLoadedOK;

  private Comparator keyComparator = new Comparator() {
    public int compare(Object rowData, Object nextNode) {
      return ((ZomDate)((Vector)rowData).get(TRADING_DATE)).compareTo((ZomDate)nextNode);
    }
  };

  // Used to record the formDataNode rather than repeatedly find it.
  private Element formDataNode;

  // Here is the Form Data
  private ZomInfo stockName;
  private ZomInfo stockCode;
  private ZomDate startingDate;
  private ZomCurrency portfolioStartingValue;
  private ZomPercent startingCashPercentage;
  private ZomAccuratePrice startingStockPrice;
  private ZomCurrency marketIndicator;
  private ZomType minimumTradeValue;
  private ZomPercent interestRate;
  private ZomCurrency brokerageFee;
  private ZomLogical minIsPercentage;
  private ZomCounter tradeFrequency;
  // Running Value Data
  private ZomCurrency runningZomValue;
  private ZomCurrency runningSingleValue;
  private long startingSingleUnits;
  // Other state variables
  private boolean tradeFormComplete = false;

  // Attribute convenience get display value methods

  public boolean hasCSVLoadedOK() { return this.csvLoadedOK; }

  public String getStockNameString(String defValue) {
    return (stockName == null) ? defValue : stockName.toString();
  }

  public String getStockCodeString(String defValue) {
    return (stockCode == null) ? defValue : stockCode.toString();
  }

  public ZomDate getStartingDate() {
    return this.startingDate;
  }

  public String getStartingDateString(String defValue) {
    return (startingDate == null) ? defValue : startingDate.toString();
  }

  public String getPortfolioStartingValueString(String defValue) {
    return (portfolioStartingValue == null) ? defValue : portfolioStartingValue.toString();
  }

  public double getStartingCashPercentageDouble(double defValue) {
    return (startingCashPercentage == null) ? defValue : startingCashPercentage.getValue();
  }

  public String getStartingCashPercentageString(String defValue) {
    return (startingCashPercentage == null) ? defValue : startingCashPercentage.toString();
  }

  public String getStartingStockPriceString(String defValue) {
    return (startingStockPrice == null) ? defValue : startingStockPrice.toString();
  }

  public ZomAccuratePrice getStartingStockPrice() {
    return startingStockPrice;
  }

  public double getMarketIndicatorDouble(double defValue) {
    return (marketIndicator == null) ? defValue : marketIndicator.getAmount().doubleValue();
  }

  public String getMarketIndicatorString(String defValue) {
    return (marketIndicator == null) ? defValue : marketIndicator.toString();
  }

  public String getMinimumTradeValueString(String defValue) {
    return (minimumTradeValue == null) ? defValue : minimumTradeValue.toString();
  }

  public ZomPercent getInterestRate() {
    return interestRate;
  }

  public String getInterestRateString(String defValue) {
    return (interestRate == null) ? defValue : interestRate.toString();
  }

  public String getBrokerageFeeString(String defValue) {
    return (brokerageFee == null) ? defValue : brokerageFee.toString();
  }

  public int getTradeFrequency(int defValue) {
    return (tradeFrequency == null) ? defValue : (int)tradeFrequency.getLong();
  }

  public String getTradeFrequencyString(String defValue) {
    return (tradeFrequency == null) ? defValue : tradeFrequency.toString();
  }

  public boolean getMinIsPercentageBoolean(boolean defValue) {
    return (minIsPercentage == null) ? defValue : minIsPercentage.getBoolean();
  }

  public String getRunningZomValue(String defValue) {
    int lastRow = this.RowData.size()-1;

    if (lastRow >= 0) {
      try {
        this.runningZomValue.setValue(
          ((ZomCurrency)((Vector)this.RowData.get(lastRow)).get(PORTFOLIO_VALUE)).getValue());
      } catch (Exception e) {
        this.runningZomValue = new ZomCurrency();
      }
    }
    else {
      this.runningZomValue = new ZomCurrency();
    }

    return (this.runningZomValue.getAmount() == null) ? defValue : this.runningZomValue.toString();
  }

  public String getRunningSingleValue(String defValue) {
    int lastRow = this.RowData.size()-1;

    if (lastRow >= 0 && this.startingSingleUnits > 0) {
      try {
        this.runningSingleValue.setValue((double)(this.startingSingleUnits *
          ((ZomAccuratePrice)((Vector)this.RowData.get(lastRow)).get(STOCK_PRICE)).getValue()));
      } catch (Exception e) {
        this.runningSingleValue = new ZomCurrency();
      }
    }
    else {
      this.runningSingleValue = new ZomCurrency();
    }

    return (this.runningSingleValue.getAmount() == null) ? defValue : this.runningSingleValue.toString();
  }

  // Attribute set methods

  private boolean setField(ZomType newValue, ZomType field, String fieldName) {
    if (newValue == null) {
      this.delFormData(fieldName);
      field = newValue;
      return true;
    }

    if (field == null || field.toString().compareTo(newValue.toString()) != 0) {
      this.addFormData(fieldName, newValue.ioString());
      field = newValue;
      return true;
    }

    return false;
  }

//  public void setStockName(ZomInfo newName) {
//    this.setField(newName, this.stockName, "stockName");
//  }
//
  public void setStockName(ZomInfo newName) {
    if (newName == null) {
      this.delFormData("stockName");
      this.stockName = newName;
      return;
    }

    if (this.stockName == null || this.stockName.compareTo(newName) != 0) {
      this.addFormData("stockName", newName.ioString());
      this.stockName = newName;
    }
  }

//  public void setStockCode(ZomInfo newCode) {
//    this.setField(newCode, this.stockCode, "stockCode");
//  }
//
  public void setStockCode(ZomInfo newCode) {
    if (newCode == null) {
      this.delFormData("stockCode");
      this.stockCode = newCode;
      return;
    }

    if (this.stockCode == null || this.stockCode.toString().compareTo(newCode.toString()) != 0) {
      this.addFormData("stockCode", newCode.ioString());
      this.stockCode = newCode;
    }
  }

//  public void setStartingDate(ZomDate newDate) {
//    this.setField(newDate, this.startingDate, "startingDate");
//  }
//
  public void setStartingDate(ZomDate newDate) {
    if (newDate == null) {
      this.delFormData("startingDate");
      this.startingDate = newDate;
      return;
    }

    if (this.startingDate == null || this.startingDate.getDate().compareTo(newDate.getDate()) != 0) {
      this.addFormData("startingDate", newDate.ioString());
      this.startingDate = newDate;
    }
  }

//  public void setPortfolioStartingValue(ZomCurrency newValue) {
//    this.setField(newValue, this.portfolioStartingValue, "portfolioStartingValue");
//  }
//
  public void setPortfolioStartingValue(ZomCurrency newValue) {
    if (newValue == null) {
      this.delFormData("portfolioStartingValue");
      this.portfolioStartingValue = newValue;
      return;
    }

    if (this.portfolioStartingValue == null || this.portfolioStartingValue.getAmount().compareTo(newValue.getAmount()) != 0) {
      this.addFormData("portfolioStartingValue", newValue.ioString());
      this.portfolioStartingValue = newValue;
    }
  }

//  public void setStartingCashPercentage(ZomPercent newValue) {
//    this.setField(newValue, this.startingCashPercentage, "startingCashPercentage");
//  }
//
  public void setStartingCashPercentage(ZomPercent newValue) {
    if (newValue == null) {
      this.delFormData("startingCashPercentage");
      this.startingCashPercentage = newValue;
      return;
    }

    if (this.startingCashPercentage == null || this.startingCashPercentage.getAmount().compareTo(newValue.getAmount()) != 0) {
      this.addFormData("startingCashPercentage", newValue.ioString());
      this.startingCashPercentage = newValue;
    }
  }

//  public void setStartingStockPrice(ZomAccuratePrice newValue) {
//    this.setField(newValue, this.startingStockPrice, "startingStockPrice");
//  }
//
  public void setStartingStockPrice(ZomAccuratePrice newValue) {
    if (newValue == null) {
      this.delFormData("startingStockPrice");
      this.startingStockPrice = newValue;
      return;
    }

    if (this.startingStockPrice == null || this.startingStockPrice.getAmount().compareTo(newValue.getAmount()) != 0) {
      this.addFormData("startingStockPrice", newValue.ioString());
      this.startingStockPrice = newValue;
    }
  }

//  public void setMarketIndicator(ZomCurrency newValue) {
//    this.setField(newValue, this.marketIndicator, "marketIndicator");
//  }
//
  public void setMarketIndicator(ZomCurrency newValue) {
    if (newValue == null) {
      this.delFormData("marketIndicator");
      this.marketIndicator = newValue;
      return;
    }

    if (this.marketIndicator == null || this.marketIndicator.getAmount().compareTo(newValue.getAmount()) != 0) {
      this.addFormData("marketIndicator", newValue.ioString());
      this.marketIndicator = newValue;
    }
  }

//  public void setMinimumTradeValue(ZomType newValue) {
//    this.setField(newValue, this.minimumTradeValue, "minimumTradeValue");
//  }
//
  public void setMinimumTradeValue(ZomType newValue) {
    if (newValue == null) {
      this.delFormData("minimumTradeValue");
      this.minimumTradeValue = newValue;
      return;
    }

    if (this.minimumTradeValue == null || this.minimumTradeValue.getAmount().compareTo(newValue.getAmount()) != 0) {
      this.addFormData("minimumTradeValue", newValue.ioString());
      this.minimumTradeValue = newValue;
    }
  }

//  public void setInterestRate(ZomPercent newValue) {
//    this.setField(newValue, this.interestRate, "interestRate");
//  }
//
  public void setInterestRate(ZomPercent newValue) {
    if (newValue == null) {
      this.delFormData("interestRate");
      this.interestRate = newValue;
      return;
    }

    if (this.interestRate == null || this.interestRate.getPercent().compareTo(newValue.getPercent()) != 0) {
      this.addFormData("interestRate", newValue.ioString());
      this.interestRate = newValue;
    }
  }

//  public void setBrokerageFee(ZomCurrency newValue) {
//    this.setField(newValue, this.brokerageFee, "brokerageFee");
//  }
//
  public void setBrokerageFee(ZomCurrency newValue) {
    if (newValue == null) {
      this.delFormData("brokerageFee");
      this.brokerageFee = newValue;
      return;
    }

    if (this.brokerageFee == null || this.brokerageFee.getAmount().compareTo(newValue.getAmount()) != 0) {
      this.addFormData("brokerageFee", newValue.ioString());
      this.brokerageFee = newValue;
    }
  }

//  public boolean setTradeFrequency(ZomCounter newValue) {
//    return this.setField(newValue, this.tradeFrequency, "tradeFrequency");
//  }
//
  public boolean setTradeFrequency(ZomCounter newValue) {
    if (newValue == null) {
      this.delFormData("tradeFrequency");
      this.tradeFrequency = newValue;
      return true;
    }

    if (this.tradeFrequency == null || this.tradeFrequency.getAmount().compareTo(newValue.getAmount()) != 0) {
      this.addFormData("tradeFrequency", newValue.ioString());
      this.tradeFrequency = newValue;
      return true;
    }

    return false;
  }

//  public void setMinIsPercentage(ZomLogical newValue) {
//    this.setField(newValue, this.minIsPercentage, "minIsPercentage");
//  }
//
  public void setMinIsPercentage(ZomLogical newValue) {
    if (newValue == null) {
      this.delFormData("minIsPercentage");
      this.minIsPercentage = newValue;
      return;
    }

    if (this.minIsPercentage == null || this.minIsPercentage.getBoolean() != newValue.getBoolean()) {
      this.addFormData("minIsPercentage", newValue.ioString());
      this.minIsPercentage = newValue;
    }
  }

  public AnalysisDataModel() {
    super();
  }

  public AnalysisDataModel(String xmlFilePath) throws Exception {
    super(xmlFilePath, 10, "Analysis", "Samples", "Sample",
      new String[][] {
        { "TradingDate", "Trading Date", "0", "0", "The date of the entered share value.", "com.byotrader.zom.ZomDate", "true", "false" },
        { "StockPrice", "Stock Price", "1", "1", "The value of a single share.", "com.byotrader.zom.ZomAccuratePrice", "true", "false" },
        { "StockValue", "Stock Value", "2", "2", "The value of all shares.", "com.byotrader.zom.ZomCurrency", "false", "true" },
        { "Cash", "Cash", "3", "3", "The current cash trading reserve.", "com.byotrader.zom.ZomCurrency", "false", "true" },
        { "Interest", "Interest", "4", "4", "Interest earnt on cash reserve.", "com.byotrader.zom.ZomCurrency", "false", "true" },
        { "Brokerage", "Buy/Sell Brokerage", "5", "5", "The fee charged if a trade occurs.", "com.byotrader.zom.ZomCurrency", "false", "true" },
        { "Traded", "Shares Acq/Exc", "6", "6", "The number of shares acquired or exchanged if a trade occurs.", "com.byotrader.zom.ZomCounter", "false", "true" },
        { "Units", "Stock Units", "7", "7", "Total number of held shares.", "com.byotrader.zom.ZomCounter", "false", "true" },
        { "PortfolioValue", "Portfolio Value", "8", "8", "The running value for this stock.", "com.byotrader.zom.ZomCurrency", "false", "true" },
        { "Performance", "Performance", "9", "9", "The gain/loss percentage compared to starting amount.", "com.byotrader.zom.ZomPercent", "false", "true" }
      });
    this.runningZomValue = new ZomCurrency();
    this.runningSingleValue = new ZomCurrency();
    this.loadFormData();
    //this.maxCount = 25;
    this.loadXML(this.getTradeFrequency(1));
  }

  private void delFormData(String attr) {
    if (this.formDataNode == null) {
      return; // nothing to remove anyway!
    }

    try {
      this.formDataNode.removeAttribute(attr);
      this.dataHasChanged = true;
    } catch (Exception e) {
      return; // Probably wasn't there so don't care
    }
  }

  private void addFormData(String attr, String value) {
    if (attr == null || attr.trim().compareTo("") == 0) {
      System.out.println("AnalysisDataModel.addFormData: missing attribute name");
      return;
    }

    if (value == null || value.trim().compareTo("") == 0) {
      System.out.println("AnalysisDataModel.addFormData: missing attribute value");
      return;
    }

    if (this.formDataNode == null) {
      // Create Data Element and append it to the root Data Node
      this.formDataNode = this.xmlDocument.createElement("FormData");
      this.rootNode.appendChild(this.formDataNode);
    }

    // Append form data attribute to form data element
    this.formDataNode.setAttribute(attr, value);
    this.dataHasChanged = true;
  }


  // Transfer data from the CSV vector to the data model vector

  public void transferData(Vector csvRowData, int start, int end, int dateCol, int valueCol) {
    // Clear the CSV loaded without error flag
    this.csvLoadedOK = true;
    // Create a date parsing object
    ZomDate zomDate = new ZomDate();

    for (int idx = start ; idx <= end ; idx++) {
      // Create the data model row vector
      Vector rowVector = new Vector(this.columnCnt);

      // Parse the date from the provided vector
      try {
        zomDate.exhaustiveParse(((Vector)csvRowData.get(idx)).get(dateCol).toString());
        // Add the date & value to the new row
        Vector newSample = this.addStockData(
          new ZomDate(zomDate.getTime()),
          new ZomAccuratePrice(((Vector)csvRowData.get(idx)).get(valueCol).toString())
        );
        // Add the new row to the data model
        this.RowData.add(newSample);
      }
      catch (Exception ex) {
        this.csvLoadedOK = false;
        try {
          System.out.println(
            "AnalysisDataModel.transferData: bad data @ line " + Integer.toString(idx) + " " +
            ((Vector)csvRowData.get(idx)).get(dateCol).toString() + ":" +
            ((Vector)csvRowData.get(idx)).get(valueCol).toString()
          );
        }
        catch (Exception ex2) {
          System.out.println(
            "AnalysisDataModel.transferData: very bad data @ line " + Integer.toString(idx));
        }
      }
    }
  }

  public Vector addStockData(ZomDate tradingDate, ZomAccuratePrice stockPrice) {
    Vector rowVector = new Vector(this.getColumnCnt() + 2);

    rowVector.add(tradingDate);
    rowVector.add(stockPrice);
    rowVector.add(null); // Stock Value
    rowVector.add(null); // Cash
    rowVector.add(null); // Interest
    rowVector.add(null); // Brokerage
    rowVector.add(null); // Traded
    rowVector.add(null); // Units
    rowVector.add(null); // Portfolio Value
    rowVector.add(null); // Performance

    // Need to calculate the null/derived values
    this.performCalculations(rowVector, false, 0);

    // Let the super handle the generic save
    this.saveNode(tradingDate.ioString(), rowVector.toArray());

    return rowVector;
  }

  protected void addingDataRow(Vector rowData) {
    this.performCalculations(rowData, false, 0);
    return;
  }

  private void loadFormData() {
    // get the form Data element
    NodeList formDataElements = this.xmlDocument.getElementsByTagName("FormData");

    // Better not be missing
    if (formDataElements == null || formDataElements.getLength() == 0) {
      System.out.println("AnalysisDataModel.loadFormData: missing form data node");
      return;
    }
    // or too many
    if (formDataElements.getLength() != 1) {
      System.out.println("AnalysisDataModel.loadFormData: more than one form data nodes");
      return;
    }
    // this one is just right!
    this.formDataNode = (Element)formDataElements.item(0);

    // get the preferences (XML attributes) for the form
    NamedNodeMap formAttrs = formDataNode.getAttributes();

    this.stockName = new ZomInfo(this.getFormAttr(formAttrs, "stockName"));
    this.stockCode = new ZomInfo(this.getFormAttr(formAttrs, "stockCode"));
    this.tradeFormComplete = true;

    String attrVal;

    try {
      this.portfolioStartingValue =
        (attrVal = this.getFormAttr(formAttrs, "portfolioStartingValue")) == null ?
          null : new ZomCurrency(attrVal);
    } catch (Exception ex) {
        ex.printStackTrace(System.err);
    }

    try {
      this.startingCashPercentage =
        (attrVal = this.getFormAttr(formAttrs, "startingCashPercentage")) == null ?
          null : new ZomPercent(attrVal);
    } catch (Exception ex) {
        ex.printStackTrace(System.err);
    }

    try {
      this.startingStockPrice =
        (attrVal = this.getFormAttr(formAttrs, "startingStockPrice")) == null ?
          null : new ZomAccuratePrice(attrVal);
    } catch (Exception ex) {
        ex.printStackTrace(System.err);
    }

    try {
      this.marketIndicator =
        (attrVal = this.getFormAttr(formAttrs, "marketIndicator")) == null ?
          null : new ZomCurrency(attrVal);
    } catch (Exception ex) {
        ex.printStackTrace(System.err);
    }

    try {
      this.startingDate =
        (attrVal = this.getFormAttr(formAttrs, "startingDate")) == null ?
          null : new ZomDate(attrVal);
//
//        if (this.zomVersion == 1.0) {
//          String newDateString;
//
//          SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy.MM.dd");
//          newDateString = newDateFormat.format(this.startingDate.getDate());
//
//          this.tmpAttr.setValue(newDateString);
//        }
    } catch (Exception ex) {
        ex.printStackTrace(System.err);
    }

    try {
      this.interestRate =
        (attrVal = this.getFormAttr(formAttrs, "interestRate")) == null ?
          null : new ZomPercent(attrVal);
    } catch (Exception ex) {
        ex.printStackTrace(System.err);
    }

    try {
      this.brokerageFee =
        (attrVal = this.getFormAttr(formAttrs, "brokerageFee")) == null ?
          null : new ZomCurrency(attrVal);
    } catch (Exception ex) {
        ex.printStackTrace(System.err);
    }

    try {
      // Since trade frequency can legally be null setting the tradeFormComplete
      // flag is an error.  So we record it and then reset it afterwards.
      boolean recordComplete = this.tradeFormComplete;
      this.tradeFrequency =
        (attrVal = this.getFormAttr(formAttrs, "tradeFrequency")) == null ?
          null : new ZomCounter(attrVal);
      this.tradeFormComplete = recordComplete;
    } catch (Exception ex) {
        ex.printStackTrace(System.err);
    }

    try {
      this.minIsPercentage =
        (attrVal = this.getFormAttr(formAttrs, "minIsPercentage")) == null ?
          null :  new ZomLogical(attrVal);
    } catch (Exception ex) {
        ex.printStackTrace(System.err);
    }

    try {
      if (this.minIsPercentage.getBoolean()) {
        this.minimumTradeValue =
          (attrVal = this.getFormAttr(formAttrs, "minimumTradeValue")) == null ?
            null :  new ZomPercent(attrVal);
      }
      else {
        this.minimumTradeValue =
          (attrVal = this.getFormAttr(formAttrs, "minimumTradeValue")) == null ?
            null :  new ZomCurrency(attrVal);
      }
    } catch (Exception ex) {
        ex.printStackTrace(System.err);
    }
  }

  private String getFormAttr(NamedNodeMap formAttrs, String AttrName) {
    Attr formAttr;
    formAttr = (Attr)formAttrs.getNamedItem(AttrName);
    this.tmpAttr = formAttr; // temp

    if (formAttr == null) {
      System.out.println("AnalysisDataModel.loadFormData: form data " + AttrName + " is missing!");
      this.tradeFormComplete = false;
      return null;
    }

    return formAttr.getNodeValue();
  }

  public void reCalculateAll() {
    int rowIdx = 0;

    this.tradeFormIncomplete();

    Iterator rowIterator = this.RowData.iterator();

    while(rowIterator.hasNext()) {
      this.performCalculations((Vector)rowIterator.next(), true, rowIdx++);
    }
  }

  protected void performCalculations(Vector tradeData, boolean isRecalc, int rowIdx) {
    if (!this.tradeFormComplete) { return; }

    // Need to reference last row if not recalculating
    if (!isRecalc) { rowIdx = this.RowData.size(); }

    // System.out.println((ZomDate)tradeData.get(TRADING_DATE) + " " + (ZomAccuratePrice)tradeData.get(STOCK_PRICE));

    // Always get the new stock price and date from the newly provided row data
    double thisStockPrice = ((ZomAccuratePrice)tradeData.get(STOCK_PRICE)).getValue();
    ZomDate thisDate = ((ZomDate)tradeData.get(TRADING_DATE));

    double prevStockValue;
    double prevCash;
    double thisInterest;
    double prevBrokerage = 0;
    long prevTraded;
    long prevUnits;
    double prevOrder;
    double thisPerformance;
    double thisBuySellAdvice;
    double prevPortfolioControl;
    ZomDate prevDate;

    // Need good form data
    if (interestRate == null || brokerageFee == null || portfolioStartingValue == null) { return; }

    // Perpare all the calculation values
    if (rowIdx == 0) {
      // Obtain values from initial template

      // Need extra good form data
      if (startingCashPercentage == null || startingStockPrice == null || marketIndicator == null) { return; }

      double tradingAmount = portfolioStartingValue.getValue() * (1.0 - startingCashPercentage.getValue());
      prevBrokerage = tradingAmount != 0.0 ? brokerageFee.getValue() : 0.0;
      prevTraded = (long)((tradingAmount - prevBrokerage) / startingStockPrice.getValue());
      prevUnits = prevTraded;
      prevStockValue = (double)prevUnits * startingStockPrice.getValue();
      prevCash = portfolioStartingValue.getValue() - prevStockValue - prevBrokerage;
      prevOrder = 0.0;
      prevPortfolioControl = prevStockValue;
      prevDate = startingDate;
      // Running Value Calcs
      this.startingSingleUnits = (long)((portfolioStartingValue.getValue() - brokerageFee.getValue()) / startingStockPrice.getValue());
    } else {
      // Obtain values from previous row
      rowIdx--;;
      prevTraded = ((ZomCounter)((Vector)this.RowData.get(rowIdx)).get(TRADED)).getLong();
      prevCash = ((ZomCurrency)((Vector)this.RowData.get(rowIdx)).get(CASH)).getValue();
      prevUnits = ((ZomCounter)((Vector)this.RowData.get(rowIdx)).get(UNITS)).getLong();
      prevOrder = ((Double)((Vector)this.RowData.get(rowIdx)).get(ORDER)).doubleValue();
      prevPortfolioControl = ((Double)((Vector)this.RowData.get(rowIdx)).get(PORTFOLIO_CONTROL)).doubleValue();
      if (rowIdx == 0) {
        prevPortfolioControl *= marketIndicator.getValue();
      }
      prevDate = (ZomDate)((Vector)this.RowData.get(rowIdx)).get(TRADING_DATE);
      rowIdx++;
    }

    // Now ready to calculate derived data

    // This is interesting
    // thisInterest = (double)((long)((prevCash * interestRate.getValue() / 365.0)*100+0.5)*.01);
    thisInterest = (rowIdx == 0) ? 0.0 : this.calcInterest(prevDate, thisDate, prevCash, interestRate.getValue());

    // Stock Value and Cash are straight forward
    double thisStockValue = (double)prevUnits * thisStockPrice;
    //double thisCash = prevCash - prevOrder + thisInterest;
    double thisCash = prevCash + thisInterest;

    // Portfolio Control
    double thisPortfolioControl = prevPortfolioControl + ((prevOrder > 0) ? prevOrder * 0.5 : 0);

    // Buy Sell Advice
    thisBuySellAdvice = thisPortfolioControl - thisStockValue;

    // Do we have an order
    double thisOrder = (rowIdx != 0) ?
      determineOrder(thisStockValue, thisBuySellAdvice, thisStockPrice, thisCash - brokerageFee.getValue()) :
      0.0;
    double thisBrokerage = thisOrder != 0.0 ? brokerageFee.getValue() : 0.0;
    long thisTraded = (long)(thisOrder / thisStockPrice);
    long thisUnits = prevUnits + thisTraded;

    // So what are we worth now
    // thisCash -= thisBrokerage;
    thisCash -= thisOrder + thisBrokerage;
    thisStockValue = (double)thisUnits * thisStockPrice;
    double thisPortfolioValue = thisStockValue + thisCash;

    // How is our performance
    thisPerformance = (thisPortfolioValue / this.portfolioStartingValue.getValue()) - 1.0;

    // Populate the table row
    try {
      tradeData.set(STOCK_VALUE, new ZomCurrency(thisStockValue));
      tradeData.set(CASH, new ZomCurrency(thisCash));
      tradeData.set(INTEREST, new ZomCurrency(thisInterest));
      tradeData.set(BROKERAGE, new ZomCurrency((rowIdx == 0) ? prevBrokerage : thisBrokerage));
      tradeData.set(TRADED, new ZomCounter((rowIdx == 0) ? prevTraded : thisTraded));
      tradeData.set(UNITS, new ZomCounter(thisUnits));
      tradeData.set(PORTFOLIO_VALUE, new ZomCurrency(thisPortfolioValue));
      tradeData.set(PERFORMANCE, new ZomPercent(thisPerformance));
    } catch (Exception ex) {
        ex.printStackTrace(System.err);
    }
    // Invisible data
    if (tradeData.size() > ORDER) {
      tradeData.set(ORDER, new Double(thisOrder));
    }
    else {
      tradeData.add(new Double(thisOrder));
    }

    if (tradeData.size() > PORTFOLIO_CONTROL) {
      tradeData.set(PORTFOLIO_CONTROL, new Double(thisPortfolioControl));
    }
    else {
      tradeData.add(new Double(thisPortfolioControl));
    }
  }

  private double determineOrder(double thisStockValue, double thisBuySellAdvice, double thisStockPrice, double thisCash) {
    // Market Order, assume none
    double thisOrder = 0;
    // Calc Buying or Selling Flag (buying is positive)
    double sign = (thisBuySellAdvice > 0 ) ? 1.0 : -1.0;
    // Calc the necessary substantially safe trading value
    double resist = thisStockValue * 0.1;
    // Calcs are absolute
    double absAdvice = Math.abs(thisBuySellAdvice) ;
    // Is it safe to trade
    if ((absAdvice - resist) < 0) {
      return thisOrder;
    }
    
    // If its a buy then do we have the cash to cover the order
    if ((sign > 0) && (absAdvice > thisCash)) {
      absAdvice = thisCash;
    }
    // recalc the trade value to the closest share amount
    double tmpOrder = Math.rint(absAdvice / thisStockPrice - 0.5) * thisStockPrice;
    // So far so good but is the order to small ie dont let fees erode our transaction
    if (this.minIsPercentage.getBoolean()) {
      // Calc min trade based as a percentage
      if (tmpOrder < (thisStockValue * this.minimumTradeValue.getValue())) {
        return thisOrder;
      }
    }
    else {
      // Calc min trade as absolute provide value
      if (tmpOrder < this.minimumTradeValue.getValue()) {
        return thisOrder;
      }
    }
    // Show me the money with rounding
    thisOrder = ((double)Math.rint(tmpOrder * 100)) / 100 * sign;

    return thisOrder;
  }

  public boolean tradeFormIncomplete() {
    this.tradeFormComplete = false;

    if (this.getPortfolioStartingValueString("").compareTo("") == 0) {
      System.out.println("AnalysisDataModel.tradeFormIncomplete: no Starting Value");
      return true;
    }

    if (this.getStartingStockPriceString("").compareTo("") == 0) {
      System.out.println("AnalysisDataModel.tradeFormIncomplete: no Stock Price");
      return true;
    }

    if (this.getStartingDateString("").compareTo("") == 0) {
      System.out.println("AnalysisDataModel.tradeFormIncomplete: no Date");
      return true;
    }

    if (this.getInterestRateString("").compareTo("") == 0) {
      System.out.println("AnalysisDataModel.tradeFormIncomplete: no Interest Rate");
      return true;
    }

    if (this.getBrokerageFeeString("").compareTo("") == 0) {
      System.out.println("AnalysisDataModel.tradeFormIncomplete: no Brokerage Fee");
      return true;
    }

    if (this.getMinimumTradeValueString("").compareTo("") == 0) {
      System.out.println("AnalysisDataModel.tradeFormIncomplete: no Minimum Trade");
      return true;
    }

    if (this.getStartingCashPercentageString("").compareTo("") == 0) {
      System.out.println("AnalysisDataModel.tradeFormIncomplete: no cash percentage");
      return true;
    }

    if (this.getMarketIndicatorString("").compareTo("") == 0) {
      System.out.println("AnalysisDataModel.tradeFormIncomplete: no market indicator");
      return true;
    }

    this.tradeFormComplete = true;
    return false;
  }

  public void deleteNode(String tradingDate) throws XPathException, Exception {
      super.deleteNode("/Analysis/Samples/Sample[@TradingDate='" + tradingDate + "']");
  }

  public boolean existsNode(String tradingDate) {
    return super.existsNode("/Analysis/Samples/Sample[@TradingDate='" + tradingDate + "']");
  }

  public Node locateGTnode(String tradingDate) {
    return super.findNode("/Analysis/Samples/Sample[@TradingDate>'" + tradingDate + "'][1]");
  }

  public String getTradeDateString(int row) {
    return ((ZomDate)((Vector)this.RowData.get(row)).get(this.TRADING_DATE)).ioString();
  }

  public ZomDate getTradeDate(int row) {
    return ((ZomDate)((Vector)this.RowData.get(row)).get(this.TRADING_DATE));
  }

  public ZomAccuratePrice getTradePrice(int row) {
    return ((ZomAccuratePrice)((Vector)this.RowData.get(row)).get(this.STOCK_PRICE));
  }

  public ZomCurrency getFinalValue() {
    int lastRow = this.RowData.size()-1;

    if (lastRow >= 0) {
      return (ZomCurrency)((Vector)this.RowData.get(lastRow)).get(PORTFOLIO_VALUE);
    }
    else {
      try {
        return new ZomCurrency(0.0);
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    return null;
  }

  public ZomDate getFinalDate() {
    int lastRow = this.RowData.size()-1;

    if (lastRow >= 0) {
      return (ZomDate)((Vector)this.RowData.get(lastRow)).get(TRADING_DATE);
    }
    else {
      return new ZomDate();
    }
  }

  public void addRow(Vector newRow) {
    // binary Search returns negative numbers (+1) when not found.
    // We know it wont be found so re-negate and -1
    int rowFoundIdx = -Arrays.binarySearch(
      this.RowData.toArray(), (ZomDate)newRow.get(TRADING_DATE), this.keyComparator) - 1;

    if (rowFoundIdx == this.RowData.size()) {
      this.RowData.add(newRow);  // appends row to end of all data
    }
    else {
      this.RowData.add(rowFoundIdx, newRow); // insert row before found row
    }
  }
}