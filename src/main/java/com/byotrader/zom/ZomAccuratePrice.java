package com.byotrader.zom;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public class ZomAccuratePrice extends ZomType {
  private Double amount;
  private DecimalFormat displayFormat;

  public ZomAccuratePrice() {
    this.displayFormat = ZomFormat.getDisplayAccuratePriceFormat();
  }

  public ZomAccuratePrice(String value) {
    this();
    this.amount = new Double(value);
  }

  public Double getAmount() {
    return this.amount;
  }

  public double getValue() {
    return this.amount.doubleValue();
  }

  public Date getDate() {
    return null;
  }

  public long getTime() {
    return 0;
  }

  public void ioParse(String value) throws Exception {
    this.amount = new Double(value);
  }

  public void formatParse(String value) throws Exception {
    this.amount = new Double(this.displayFormat.parse(value).doubleValue());
  }

  public void exhaustiveParse(String value) throws Exception {
    try {
      this.amount = new Double(value);
    } catch (Exception ex) {
      this.amount = new Double(this.displayFormat.parse(value).doubleValue());
    }
  }

  // Provide formatting method for Table Model
  public String toString() {
    return this.displayFormat.format(this.amount.doubleValue());
  }

  // Provide formatting for persistance interface
  public String ioString() {
    return this.amount.toString();
  }
}