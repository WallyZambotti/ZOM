package com.byotrader.zom;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public abstract class ZomController {
  public ZomController() {
  }

  public abstract void copyTradeData(TradeCopyBar tradeCopyBar);

  public abstract void cutTradeData(TradeCopyBar tradeCopyBar);

  public abstract void pasteTradeData(TradeCopyBar tradeCopyBar);
}