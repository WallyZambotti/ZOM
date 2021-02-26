package com.byotrader.zom;

import java.util.Date;
import java.text.*;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public class MyDateFormat extends DateFormat {

  public MyDateFormat() {
  }
  public Date parse(String text, ParsePosition pos) {
    //super.parse(text, pos);
    return null;
  }
  public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
    //super.format(date, toAppendTo, fieldPosition);
    return null;
  }
}