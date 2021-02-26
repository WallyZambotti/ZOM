package com.byotrader.zom;

import java.lang.Number;
import java.text.NumberFormat;
import java.util.Date;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public class ZomCurrency extends ZomType {
  private Double amount;
  private NumberFormat displayFormat;

  public ZomCurrency() {
    this.displayFormat = ZomFormat.getDisplayCurrencyFormat();
  }

  public ZomCurrency(String value) throws Exception {
    this();
    this.amount = new Double(value);
  }

  public ZomCurrency(double value) throws Exception {
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

  // Format output suitable for Table Models and display
  public String toString() {
    return this.displayFormat.format(this.amount.doubleValue());
  }

  // Format output suitable for independant persistance
  public String ioString() {
    return this.amount.toString();
  }

  public void setValue(double newValue) {
    this.amount = new Double(newValue);
  }

  public void setValue(Object newValue) {
    this.amount = null;
  }
}