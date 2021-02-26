package com.byotrader.zom;

import java.lang.String;
import java.util.Date;
import java.lang.Comparable;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public class ZomInfo extends ZomType implements Comparable {
  // At the moment ZomInfo doesn't utilise ZomFormat
  // but it is accepted to satify the ZomPersistence interface
  private String information;
  private ZomFormat zomFormat;

  public ZomInfo() {
  }

  public ZomInfo(String info) {
    this.information = new String(info);
  }

  public String getString() {
    return this.information;
  }

  public Double getAmount() {
    return null;
  }

  public double getValue() {
    return 0.0;
  }

  public Date getDate() {
    return null;
  }

  public long getTime() {
    return 0;
  }

  // The Table models rely on toString for formatting

  public String toString() {
    return this.information;
  }

  // XML Persisteance model relies on ioString for formatting

  public String ioString() {
    return this.information;
  }

  public void ioParse(String value) throws Exception {
    this.information = value;
  }

  public void formatParse(String value) throws Exception {
    this.information = value;
  }

  public void exhaustiveParse(String value) throws Exception {
    this.information = value;
  }

  // Comparable Interface
  public int compareTo(Object compInfo) {
    return this.information.compareTo(((ZomInfo)compInfo).getString());
  }
}