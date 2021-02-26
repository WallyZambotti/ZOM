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

public class TradeDataModel extends XMLDataModel {
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

  private Comparator keyComparator = new Comparator() {
    public int compare(Object rowData, Object nextNode) {
      return ((ZomDate)((Vector)rowData).get(TRADING_DATE)).compareTo((ZomDate)nextNode);
    }
  };

  // Used to record the formDataNode rather than repeatedly find it.
  private Element formDataNode;

  // Here is the Form Data
  private String stockName;
  private String stockCode;
  private ZomDate startingDate;
  private ZomCounter portfolioStartingValue;
  private ZomCurrency startingCashReserve;
  private ZomAccuratePrice startingStockPrice;
  private ZomPercent interestRate;
  private ZomCurrency brokerageFee;
  // Running Value Data
  private ZomCurrency runningZomValue;
  private ZomCurrency runningSingleValue;
  private long startingSingleUnits;
  // Other state variables
  private boolean tradeFormComplete = false;

  public ZomPercent getInterestRate() {
    return this.interestRate;
  }

  public ZomCounter getPortfolioStartingValue() {
    return this.portfolioStartingValue;
  }

  public ZomCurrency getStartingCashReserve() {
    return this.startingCashReserve;
  }

  public ZomDate getStartingDate() {
    return this.startingDate;
  }

  // Attribute convenience get display value methods

  public String getStockNameString(String defValue) {
    return (stockName == null) ? defValue : stockName.toString();
  }

  public String getStockCodeString(String defValue) {
    return (stockCode == null) ? defValue : stockCode.toString();
  }

  public String getStartingDateString(String defValue) {
    return (startingDate == null) ? defValue : startingDate.toString();
  }

  public String getPortfolioStartingValueString(String defValue) {
    return (portfolioStartingValue == null) ? defValue : portfolioStartingValue.toString();
  }

  public double getStartingCashReserveDouble(double defValue) {
    return (startingCashReserve == null) ? defValue : startingCashReserve.getValue();
  }

  public String getStartingStockPriceString(String defValue) {
    return (startingStockPrice == null) ? defValue : startingStockPrice.toString();
  }

  public String getInterestRateString(String defValue) {
    return (interestRate == null) ? defValue : interestRate.toString();
  }

  public String getBrokerageFeeString(String defValue) {
    return (brokerageFee == null) ? defValue : brokerageFee.toString();
  }

  public String getStartingCashReserveString(String defValue) {
    return (startingCashReserve == null) ? defValue : startingCashReserve.toString();
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

  public void setStockName(String newName) {
    if (newName == null) {
      this.delFormData("stockName");
      this.stockName = newName;
      return;
    }

    if (this.stockName == null || this.stockName.compareTo(newName) != 0) {
      this.addFormData("stockName", newName);
      this.stockName = newName;
    }
  }

  public void setStockCode(String newCode) {
    if (newCode == null) {
      this.delFormData("stockCode");
      this.stockCode = newCode;
      return;
    }

    if (this.stockCode == null || this.stockCode.compareTo(newCode) != 0) {
      this.addFormData("stockCode", newCode);
      this.stockCode = newCode;
    }
  }

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

  public void setPortfolioStartingValue(ZomCounter newValue) {
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

  public void setStartingCashReserve(ZomCurrency newValue) {
    if (newValue == null) {
      this.delFormData("startingCashReserve");
      this.startingCashReserve = newValue;
      return;
    }

    if (this.startingCashReserve == null || this.startingCashReserve.getAmount().compareTo(newValue.getAmount()) != 0) {
      this.addFormData("startingCashReserve", newValue.ioString());
      this.startingCashReserve = newValue;
    }
  }

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

  public TradeDataModel() {
    super();
  }

  public TradeDataModel(String xmlFilePath) throws Exception {
    super(xmlFilePath, 10, "Trade", "Samples", "Sample",
      new String[][] {
        { "TradingDate", "Trading Date", "0", "0", "The date of the entered share value.", "com.byotrader.zom.ZomDate", "true", "false" },
        { "StockPrice", "Stock Price", "1", "1", "The value of a single share.", "com.byotrader.zom.ZomAccuratePrice", "true", "false" },
        { "StockValue", "Stock Value", "2", "2", "The value of all shares.", "com.byotrader.zom.ZomCurrency", "false", "false" },
        { "Cash", "Cash", "3", "3", "The current cash trading reserve.", "com.byotrader.zom.ZomCurrency", "true", "false" },
        { "Interest", "Interest", "4", "4", "Interest earnt on cash reserve.", "com.byotrader.zom.ZomCurrency", "true", "false" },
        { "Brokerage", "Buy/Sell Brokerage", "5", "5", "The fee charged if a trade occurs.", "com.byotrader.zom.ZomCurrency", "true", "false" },
        { "Traded", "Shares Acq/Exc", "6", "6", "The number of shares acquired or exchanged if a trade occurs.", "com.byotrader.zom.ZomCounter", "true", "false" },
        { "Units", "Stock Units", "7", "7", "Total number of held shares.", "com.byotrader.zom.ZomCounter", "false", "true" },
        { "PortfolioValue", "Portfolio Value", "8", "8", "The running value for this stock.", "com.byotrader.zom.ZomCurrency", "false", "true" },
        { "Performance", "Performance", "9", "9", "The gain/loss percentage compared to starting amount.", "com.byotrader.zom.ZomPercent", "false", "true" }
      });
    this.runningZomValue = new ZomCurrency();
    this.runningSingleValue = new ZomCurrency();
    this.loadFormData();
    //this.maxCount = 25;
    this.loadXML(1);
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
      System.out.println("TradeDataModel.addFormData: missing attribute name");
      return;
    }

    if (value == null || value.trim().compareTo("") == 0) {
      System.out.println("TradeDataModel.addFormData: missing attribute value");
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

  public Vector addStockData(
    ZomDate tradingDate,
    ZomAccuratePrice stockPrice,
    ZomCurrency cash,
    ZomCurrency interest,
    ZomCurrency brokerage,
    ZomCounter traded) {
    Vector rowVector = new Vector(this.getColumnCnt() + 2);

    rowVector.add(tradingDate);
    rowVector.add(stockPrice);
    rowVector.add(null); // Stock Value
    rowVector.add(cash); // Cash
    rowVector.add(interest); // Interest
    rowVector.add(brokerage); // Brokerage
    rowVector.add(traded); // Traded
    rowVector.add(null); // Units
    rowVector.add(null); // Portfolio Value
    rowVector.add(null); // Performance

    // Need to calculate the null/derived values
    this.performCalculations(rowVector, false, 0, true);

    // With Trade data dont wan't to save derived values for cash, interest
    // so save a copy of the vector
    Vector tmpVector = (Vector)rowVector.clone();
    tmpVector.set(CASH, cash);
    tmpVector.set(INTEREST, interest);

    // Let the super handle the generic save
    this.saveNode(tradingDate.ioString(), tmpVector.toArray());

    return rowVector;
  }

  public void appendNode(Object[] nodeAttrs) {
    // Create Data Element and append it to the root Data Node
    Element dataElement = this.xmlDocument.createElement(this.dataNodeName);
    this.rootDataNode.appendChild(dataElement);

    // Append data attributes to data element
    for(int iAttr = 0; iAttr < this.columnCnt; iAttr++) {
      if (!((Boolean)getColValueVector(iAttr, DERIVED)).booleanValue() && nodeAttrs[iAttr] != null) {
        dataElement.setAttribute(
          (String)getColValueVector(iAttr, HEADING),
          ((ZomType)nodeAttrs[iAttr]).ioString());
      }
    }
  }

  public void insertNode(Node nextNode, Object[] nodeAttrs) {
    // Create Data Element and insert it before the provided node
    Element dataElement = this.xmlDocument.createElement(this.dataNodeName);
    this.rootDataNode.insertBefore((Node)dataElement, (Node)nextNode);

    // Append data attributes to data element
    for(int iAttr = 0; iAttr < this.columnCnt; iAttr++) {
      if (!((Boolean)getColValueVector(iAttr, DERIVED)).booleanValue() && nodeAttrs[iAttr] != null) {
        dataElement.setAttribute(
          (String)getColValueVector(iAttr, HEADING),
          ((ZomType)nodeAttrs[iAttr]).ioString());
      }
    }
  }

  protected void addingDataRow(Vector rowData) {
    this.performCalculations(rowData, false, 0, false);
    return;
  }

  private void loadFormData() {
    // get the form Data element
    NodeList formDataElements = this.xmlDocument.getElementsByTagName("FormData");

    // Better not be missing
    if (formDataElements == null || formDataElements.getLength() == 0) {
      System.out.println("TradeDataModel.loadFormData: missing form data node");
      return;
    }
    // or too many
    if (formDataElements.getLength() != 1) {
      System.out.println("TradeDataModel.loadFormData: more than one form data nodes");
      return;
    }
    // this one is just right!
    this.formDataNode = (Element)formDataElements.item(0);

    // get the preferences (XML attributes) for the form
    NamedNodeMap formAttrs = formDataNode.getAttributes();

    this.stockName = this.getFormAttr(formAttrs, "stockName");
    this.stockCode = this.getFormAttr(formAttrs, "stockCode");
    this.tradeFormComplete = true;

    String attrVal;

    try {
      this.portfolioStartingValue =
        (attrVal = this.getFormAttr(formAttrs, "portfolioStartingValue")) == null ?
          null : new ZomCounter(attrVal);
    } catch (Exception ex) {
        ex.printStackTrace(System.err);
    }

    try {
      this.startingCashReserve =
        (attrVal = this.getFormAttr(formAttrs, "startingCashReserve")) == null ?
          null : new ZomCurrency(attrVal);
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
      this.startingDate =
        (attrVal = this.getFormAttr(formAttrs, "startingDate")) == null ?
          null : new ZomDate(attrVal);
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
  }

  private String getFormAttr(NamedNodeMap formAttrs, String AttrName) {
    Attr formAttr;
    formAttr = (Attr)formAttrs.getNamedItem(AttrName);
    this.tmpAttr = formAttr; // temp

    if (formAttr == null) {
      System.out.println("TradeDataModel.loadFormData: form data " + AttrName + " is missing!");
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
      this.performCalculations((Vector)rowIterator.next(), true, rowIdx++, false);
    }
  }

  private void performCalculations(Vector tradeData, boolean isRecalc, int rowIdx, boolean isNewData) {
    if (!this.tradeFormComplete) {
      return;
    }

    // Need to reference last row if not recalculating
    if (!isRecalc) {
      rowIdx = this.RowData.size();
    }

    // System.out.println((ZomDate)tradeData.get(TRADING_DATE) + " " + (ZomAccuratePrice)tradeData.get(STOCK_PRICE));

    // Always get the new stock price & date from the newly provided row data
    double thisStockPrice = ((ZomAccuratePrice)tradeData.get(STOCK_PRICE)).getValue();
    ZomDate thisDate = (ZomDate)tradeData.get(TRADING_DATE);

    double prevStockValue;
    double prevCash;
    double thisInterest;
    double prevBrokerage;
    long prevTraded;
    long prevUnits;
    double thisPerformance;
    ZomDate prevDate;

    // Need good form data
    if (interestRate == null || brokerageFee == null || portfolioStartingValue == null) {
      return;
    }

    // Perpare all the calculation values
    if (rowIdx == 0) {
      // Obtain values from initial template

      // Need extra good form data
      if (startingCashReserve == null || startingStockPrice == null) {
        return;
      }

      prevUnits = portfolioStartingValue.getLong();
      prevTraded = prevUnits;
      prevStockValue = prevTraded;
      prevCash = startingCashReserve.getValue();
      prevDate = startingDate;
      // Running Value Calcs
      this.startingSingleUnits = prevUnits + (long)(this.startingCashReserve.getValue() / this.startingStockPrice.getValue());
    } else {
      prevTraded = ((ZomCounter)((Vector)this.RowData.get(rowIdx-1)).get(TRADED)).getLong();
      prevUnits = ((ZomCounter)((Vector)this.RowData.get(rowIdx-1)).get(UNITS)).getLong();
      prevDate = (ZomDate)((Vector)this.RowData.get(rowIdx-1)).get(TRADING_DATE);
      if (tradeData.get(CASH) != null) {
        prevCash = ((ZomCurrency)tradeData.get(CASH)).getValue();
      }
      else {
        prevCash = ((ZomCurrency)((Vector)this.RowData.get(rowIdx-1)).get(CASH)).getValue();
      }
    }

    // So how many units were Traded and was the cash changed ?
    long thisTraded = ((ZomCounter)tradeData.get(TRADED)).getLong();

    // Now ready to calculate derived data

    // This is interesting
    if (tradeData.get(INTEREST) != null) {
      thisInterest = ((ZomCurrency)tradeData.get(INTEREST)).getValue();
    }
    else {
    //  thisInterest = prevCash * interestRate.getValue() / 365.0;
      thisInterest = this.calcInterest(prevDate, thisDate, prevCash, interestRate.getValue());
    }

    // Do we have an order
    double thisOrder = (double)thisTraded * thisStockPrice;
    double thisBrokerageFee = ((ZomCurrency)tradeData.get(BROKERAGE)).getValue();
    double thisBrokerage = thisOrder != 0.0 ? thisBrokerageFee : 0.0;
    long thisUnits = prevUnits + thisTraded;

    // So what are we worth now
    double thisStockValue = (double)thisUnits * thisStockPrice;
    double thisCash = prevCash - thisOrder + thisInterest - thisBrokerage;
    double thisPortfolioValue = thisStockValue + thisCash;

    // How is our performance
    // this currently emulates the Analysis Performance
    thisPerformance = (thisPortfolioValue /
      (this.portfolioStartingValue.getValue() * this.startingStockPrice.getValue() +
      this.startingCashReserve.getValue() + this.brokerageFee.getValue())) - 1.0;

    // Populate the table row
    try {
      tradeData.set(STOCK_VALUE, new ZomCurrency(thisStockValue));
      tradeData.set(CASH, new ZomCurrency(thisCash));
      tradeData.set(INTEREST, new ZomCurrency(thisInterest));
      tradeData.set(BROKERAGE, new ZomCurrency(thisBrokerage));
      tradeData.set(TRADED, new ZomCounter(thisTraded));
      tradeData.set(UNITS, new ZomCounter(thisUnits));
      tradeData.set(PORTFOLIO_VALUE, new ZomCurrency(thisPortfolioValue));
      tradeData.set(PERFORMANCE, new ZomPercent(thisPerformance));
    } catch (Exception ex) {
        ex.printStackTrace(System.err);
    }
  }

  public boolean tradeFormIncomplete() {
    this.tradeFormComplete = false;

    if (this.getPortfolioStartingValueString("").compareTo("") == 0) {
      System.out.println("TradeDataModel.tradeFormIncomplete: no Starting Value");
      return true;
    }

    if (this.getStartingStockPriceString("").compareTo("") == 0) {
      System.out.println("TradeDataModel.tradeFormIncomplete: no Stock Price");
      return true;
    }

    if (this.getStartingDateString("").compareTo("") == 0) {
      System.out.println("TradeDataModel.tradeFormIncomplete: no Date");
      return true;
    }

    if (this.getInterestRateString("").compareTo("") == 0) {
      System.out.println("TradeDataModel.tradeFormIncomplete: no Interest Rate");
      return true;
    }

    if (this.getBrokerageFeeString("").compareTo("") == 0) {
      System.out.println("TradeDataModel.tradeFormIncomplete: no Brokerage Fee");
      return true;
    }

    this.tradeFormComplete = true;
    return false;
  }

  public void deleteNode(String tradingDate) throws XPathException, Exception {
      super.deleteNode("/Trade/Samples/Sample[@TradingDate='" + tradingDate + "']");
  }

  public boolean existsNode(String tradingDate) {
    return super.existsNode("/Trade/Samples/Sample[@TradingDate='" + tradingDate + "']");
  }

  public Node locateGTnode(String tradingDate) {
    return super.findNode("/Trade/Samples/Sample[@TradingDate>'" + tradingDate + "'][1]");
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

  public ZomCurrency getCash(int row) {
    return ((ZomCurrency)((Vector)this.RowData.get(row)).get(this.CASH));
  }

  public ZomCounter getUnits(int row) {
    return ((ZomCounter)((Vector)this.RowData.get(row)).get(this.UNITS));
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

  // Return the data model row that falls immediately before the provided date
  public Vector getRowBefore(ZomDate newDate) {
    // binary Search returns negative numbers (+1) when not found.
    // We know it wont be found so re-negate and -1
    int rowFoundIdx = -Arrays.binarySearch(
      this.RowData.toArray(), newDate, this.keyComparator) - 1;

    if (rowFoundIdx == 0) {
      return null; // date preceeds all rows so none can come before
    }
    else {
      // binary search returns the row after this date, but
      // we need the row before so return then previous row
      return (Vector)this.RowData.get(rowFoundIdx-1);
    }
  }
}