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

public abstract class ZomType {

  abstract Double getAmount();

  abstract double getValue();

  abstract Date getDate();

  abstract long getTime();

  abstract void ioParse(String value) throws Exception;

  abstract void formatParse(String value) throws Exception;

  abstract void exhaustiveParse(String value) throws Exception;

  abstract String ioString();
}