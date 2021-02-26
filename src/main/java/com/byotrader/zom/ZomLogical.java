package com.byotrader.zom;

import java.util.Date;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public class ZomLogical extends ZomType {
  private boolean logical;

  public ZomLogical() {
  }

  public ZomLogical(boolean newValue) {
    this.logical = newValue;
  }

  public ZomLogical(String newValue) {
    this.logical = Boolean.valueOf(newValue).booleanValue();
  }

  void exhaustiveParse(String value) throws java.lang.Exception {
    this.logical = "ty1".indexOf(value.toLowerCase().charAt(0)) != -1;
  }

  Double getAmount() {
    return new Double(this.logical ? 1.0: 0.0);
  }

  void formatParse(String value) throws java.lang.Exception {
    this.logical = Boolean.valueOf(value).booleanValue();
  }

  double getValue() {
    return this.logical ? 1.0: 0.0;
  }

  long getTime() {
    return 0;
  }

  public String toString() {
    return new Boolean(this.logical).toString();
  }

  String ioString() {
    return new Boolean(this.logical).toString();
  }

  void ioParse(String value) throws java.lang.Exception {
    this.logical = Boolean.valueOf(value).booleanValue();
  }

  Date getDate() {
    return null;
  }

  boolean getBoolean() {
    return this.logical;
  }
}