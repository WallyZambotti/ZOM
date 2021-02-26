package com.byotrader.zom;

import java.util.Vector;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public class TradeCopyBar {
  private Vector recordedData;
  private ZomPercent interestRate;

  public TradeCopyBar() {
  }

  public void recordTradeData(Vector row) {
    this.recordedData = row;
  }

  public void recordInterestRate(ZomPercent intRate) {
    this.interestRate = intRate;
  }

  public Vector recallTradeData() {
    return this.recordedData;
  }

  public ZomPercent recallInterestRate() {
    return this.interestRate;
  }
}