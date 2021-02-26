package com.byotrader.zom;

import java.lang.Integer;
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

public class ZomCounter extends ZomType {
  private Long counter;
  private NumberFormat displayFormat;

  public ZomCounter() {
    this.displayFormat = ZomFormat.getDisplayCounterFormat();
  }

  public ZomCounter(String value) {
    this();
    this.counter = new Long(value);
  }

  public ZomCounter(long value) {
    this();
    this.counter = new Long(value);
  }

  public Date getDate() {
    return null;
  }

  public long getTime() {
    return 0;
  }

  public Long getCounter() {
    return this.counter;
  }

  public Double getAmount() {
    return new Double((double)this.counter.longValue());
  }

  public double getValue() {
    return (double)this.counter.longValue();
  }

  public long getLong() {
    return this.counter.longValue();
  }

  // Need to supply a toString method for the Table Model
  public String toString() {
    return this.displayFormat.format(this.counter.longValue());
  }

  // Need to supply an ioString method for the ZomPersistence interface
  public String ioString() {
    return this.counter.toString();
  }

  public void ioParse(String value) throws Exception {
    this.counter = new Long(value);
  }

  public void formatParse(String value) throws Exception {
      this.counter = new Long(this.displayFormat.parse(value).longValue());
  }

  public void exhaustiveParse(String value) throws Exception {
    try {
      this.counter = new Long(value);
    } catch (Exception ex) {
      this.counter = new Long(this.displayFormat.parse(value).longValue());
    }
  }
}