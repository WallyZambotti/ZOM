package com.byotrader.zom;

import java.util.Vector;
import java.util.Arrays;
import java.util.Comparator;
import java.net.InetAddress;

import org.apache.xpath.XPathException;

import org.w3c.dom.Node;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public class PortfolioDataModel extends XMLDataModel {
  protected static final int STOCK_NAME = 0;
  protected static final int STOCK_CODE = 1;
  protected static final int LAST_UPDATED = 2;
  protected static final int VALUE = 3;
  protected static final int LAST_TRADED = 4;
  protected static final int TRADE_VALUE = 5;

  private String regKey = "";
  private String regCode = "";
  private boolean registered = false;

  public boolean isRegistered() {
    return this.registered;
  }

  public String getRegString() {
    return regKey + "_" + regCode;
  }

  private Comparator keyComparator = new Comparator() {
    public int compare(Object rowData, Object nextNode) {
      return ((ZomInfo)((Vector)rowData).get(STOCK_NAME)).compareTo((ZomInfo)nextNode);
    }
  };

  // Attribute set methods

  public void setRegKey(String newKey) {
    if (newKey == null) {
      this.delRootData("regKey");
      this.regKey = newKey;
      return;
    }

    if (this.regKey == null || this.regKey.compareTo(newKey) != 0) {
      this.addRootData("regKey", newKey);
      this.regKey = newKey;
    }
  }

  public void setRegCode(String newCode) {
    if (newCode == null) {
      this.delRootData("regCode");
      this.regCode = newCode;
      return;
    }

    if (this.regCode == null || this.regCode.compareTo(newCode) != 0) {
      this.addRootData("regCode", newCode);
      this.regCode = newCode;
    }
  }

  public PortfolioDataModel() {
    super();
  }

  public PortfolioDataModel(String xmlFilePath) throws Exception {
    super(xmlFilePath, 6, "Portfolio", "Stocks", "Stock",
      new String[][] {
        { "StockName", "Stock Name", "0", "0", "Your personal descriptive stock name.", "com.byotrader.zom.ZomInfo", "true", "false" },
        { "StockCode", "Stock Code", "1", "1", "The actual exchange stock code.", "com.byotrader.zom.ZomInfo", "true", "false" },
        { "LastUpdated", "Last Analysed", "2", "2", "The last date with which ZOM analysed this stock.", "com.byotrader.zom.ZomDate", "false", "false" },
        { "Value", "Analysis Value", "3", "3", "The estimate of the analysis at the last update time.", "com.byotrader.zom.ZomCurrency", "false", "false" },
        { "LastTraded", "Last Traded", "4", "4", "The last date with which you traded this stock.", "com.byotrader.zom.ZomDate", "false", "false" },
        { "TradeValue", "Trade Value", "5", "5", "The value of the stock at the last update time.", "com.byotrader.zom.ZomCurrency", "false", "false" }
      });
    this.loadRegistrationData();
    //this.maxCount = 3;
    this.loadXML(1);
  }

  private void loadRegistrationData() {
    // get the registration details (XML attributes) for the portfolio
    NamedNodeMap rootAttrs = this.rootNode.getAttributes();

    String attrVal;

    try {
      this.regKey = this.getRootAttr(rootAttrs, "regKey");
    } catch (Exception ex) {
        ex.printStackTrace(System.err);
    }

    try {
      this.regCode = this.getRootAttr(rootAttrs, "regCode");
    } catch (Exception ex) {
        ex.printStackTrace(System.err);
    }

    if (this.regKey == null || this.regCode == null) {
      this.registered = false;
    }
    else {
      try {
        ZomEnigma.setKey(InetAddress.getLocalHost().getHostName());
      }
      catch (Exception ex) {
        ZomEnigma.setKey("localhost");
      }

      String encryptedCode = ZomEnigma.getDecryptedString(this.regCode);

      ZomEnigma.setKey(this.regKey);
      String decrypted = ZomEnigma.getDecryptedString(encryptedCode);

      this.registered = decrypted.regionMatches(0, "Zom 1.0: ", 0, 9);
      ZomString.setAnsiStrings(this.registered);
    }
  }

  private String getRootAttr(NamedNodeMap rootAttrs, String AttrName) {
    Attr rootAttr;
    rootAttr = (Attr)rootAttrs.getNamedItem(AttrName);

    if (rootAttr == null) {
      System.out.println("PortfolioDataModel.getRootAttr: root data " + AttrName + " is missing!");
      return null;
    }

    return rootAttr.getNodeValue();
  }

  private void addRootData(String attr, String value) {
    if (attr == null || attr.trim().compareTo("") == 0) {
      System.out.println("PortfolioDataModel.addRootData: missing attribute name");
      return;
    }

    if (value == null || value.trim().compareTo("") == 0) {
      System.out.println("PortfolioDataModel.addRootData: missing attribute value");
      return;
    }

    // Append root data attribute to root data element
    this.rootNode.setAttribute(attr, value);
  }

  private void delRootData(String attr) {
    if (this.rootNode == null) {
      return; // nothing to remove anyway!
    }

    try {
      this.rootNode.removeAttribute(attr);
    } catch (Exception e) {
      return; // Probably wasn't there so don't care
    }
  }

  public Vector addStock(
    String stockName, String stockCode, ZomDate lastUpdated, ZomCurrency value, ZomDate lastTraded, ZomCurrency tradeValue) {
    Vector rowVector = new Vector(this.getColumnCnt());

    try {
      rowVector.add(new ZomInfo(stockName));
      rowVector.add(new ZomInfo(stockCode));
      rowVector.add(lastUpdated);
      rowVector.add(value);
      rowVector.add(lastTraded);
      rowVector.add(tradeValue);
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    // Let the super handle the generic save
    this.saveNode(stockName, rowVector.toArray());

    return rowVector;
  }

  public void updateStock(
    String stockName,
    String newStockName,
    String newStockCode,
    ZomDate newAnalysisDate,
    ZomCurrency newAnalysisValue,
    ZomDate newTradeDate,
    ZomCurrency newTradeValue) throws XPathException, Exception {
    super.updateNode(
      "/Portfolio/Stocks/Stock[@StockName='" + stockName + "']",
      new Object[] { newStockName, newStockCode, newAnalysisDate, newAnalysisValue, newTradeDate, newTradeValue });
  }

  public void deleteNode(String stockName) throws XPathException, Exception {
      super.deleteNode("/Portfolio/Stocks/Stock[@StockName='" + stockName + "']");
  }

  public boolean existsNode(String stockName) {
    return super.existsNode("/Portfolio/Stocks/Stock[@StockName='" + stockName + "']");
  }

  public Node locateGTnode(String stockName) {
    return super.findNode("/Portfolio/Stocks/Stock[String:compareTo(string(@StockName),'" + stockName + "')>0][1]");
  }

  public String getStockName(int row) {
    return ((ZomInfo)((Vector)this.RowData.get(row)).get(this.STOCK_NAME)).ioString();
  }

  public String getStockCode(int row) {
    return ((ZomInfo)((Vector)this.RowData.get(row)).get(this.STOCK_CODE)).ioString();
  }

  protected void addingDataRow(Vector rowData) {
    return;
  }

  public void addRow(Vector newRow) {
    // binary Search returns negative numbers (+1) when not found.
    // We know it wont be found so re-negate and -1
    int rowFoundIdx = -Arrays.binarySearch(
      this.RowData.toArray(), (ZomInfo)newRow.get(STOCK_NAME), this.keyComparator) - 1;

    if (rowFoundIdx == this.RowData.size()) {
      this.RowData.add(newRow);  // appends row to end of all data
    }
    else {
      this.RowData.add(rowFoundIdx, newRow); // insert row before found row
    }
  }
}