package com.byotrader.zom;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public final class  ZomString {
  private static boolean ansiStrings = false;

  public static void setAnsiStrings(boolean b) {
    ansiStrings = b;
  }

  public static boolean isAnsiStrings() {
    return ansiStrings;
  }

  private ZomString() {
  }
}