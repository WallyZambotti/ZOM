package com.byotrader.zom;

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

public class ZomPercent extends ZomType {
  private Double percent;
  private NumberFormat displayFormat;

  public ZomPercent() {
    displayFormat = ZomFormat.getDisplayPercentFormat();
  }

  public ZomPercent(String value) {
    this();
    this.percent = new Double(value);
  }

  public ZomPercent(double value) {
    this();
    this.percent = new Double(value);
  }

  public Double getPercent() {
    return this.percent;
  }

  public Double getAmount() {
    return this.percent;
  }

  public double getValue() {
    return this.percent.doubleValue();
  }

  public Date getDate() {
    return null;
  }

  public long getTime() {
    return 0;
  }

  public void ioParse(String value) throws Exception {
    this.percent = new Double(value);
  }

  public void formatParse(String value) throws Exception {
    this.percent = new Double(this.displayFormat.parse(value).doubleValue());
  }

  public void exhaustiveParse(String value) throws Exception {
    try {
      this.percent = new Double(value);
    } catch (Exception ex) {
      this.percent = new Double(this.displayFormat.parse(value).doubleValue());
    }
  }

  // Provide formatted output
  public String toString() {
    return this.displayFormat.format(this.percent);
  }

  // Provide unformated output
  public String ioString() {
    return this.percent.toString();
  }
}