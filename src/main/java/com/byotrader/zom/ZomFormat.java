package com.byotrader.zom;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.DateFormatSymbols;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public final class ZomFormat {
  private static Locale zomLocale;

  private static SimpleDateFormat ioDateFormat;
  private static DateFormat displayDateFormat;
  //private static SimpleDateFormat displayDateFormat;
  private static NumberFormat displayCurrencyFormat;
  private static NumberFormat displayPercentFormat;
  private static DecimalFormat displayAccuratePriceFormat;
  private static NumberFormat displayCounterFormat;
  private static Date twoDigitYearStart;

  public static SimpleDateFormat getIODateFormat() {
    return ioDateFormat;
  }

  public static void setIODateFormat(String format) {
    // create a date output format fixed to Australia
    ioDateFormat = new SimpleDateFormat(format, new Locale("en", "AU"));
    ioDateFormat.set2DigitYearStart(twoDigitYearStart);
  }

  public static DateFormat getDisplayDateFormat() {
    return displayDateFormat;
  }

  private static void setDisplayDateFormat(int style) {
    displayDateFormat = DateFormat.getDateInstance(style, zomLocale);
//    String[] dateTimePatterns;
//    String pattern=null;
//    ResourceBundle r=null;
//    try {
//    	r = ResourceBundle.getBundle("java.text.resources.LocaleElements", zomLocale);
//    }
//    catch (Exception ex) {
//        try {
//        	r = ResourceBundle.getBundle("sun.text.resources.LocaleElements", zomLocale);
//        }
//        catch (Exception ex1) {
//        	ex1.printStackTrace();
//        }
//    }
//    dateTimePatterns = r.getStringArray("DateTimePatterns");
//    DateFormatSymbols formatData = new DateFormatSymbols(zomLocale);
//    if (style >= 0) {
//      pattern = dateTimePatterns[style + 4];
//    }
//    else {
//      System.out.println("ZomFormat.setDisplayDateFormat: no style");
//      return;
//    }
//    
//    displayDateFormat = new SimpleDateFormat("dd/MM/yy", zomLocale);
//    displayDateFormat.set2DigitYearStart(twoDigitYearStart);
}

  public static NumberFormat getDisplayCurrencyFormat() {
    return displayCurrencyFormat;
  }

  private static void setDisplayCurrencyFormat() {
    displayCurrencyFormat = NumberFormat.getCurrencyInstance(zomLocale);
  }

  public static NumberFormat getDisplayPercentFormat() {
    return displayPercentFormat;
  }

  private static void setDisplayPercentFormat() {
    displayPercentFormat = NumberFormat.getPercentInstance(zomLocale);
    ((DecimalFormat)displayPercentFormat).applyPattern("0.00%");
  }

  public static DecimalFormat getDisplayAccuratePriceFormat() {
    return displayAccuratePriceFormat;
  }

  private static void setDisplayAccuratePriceFormat(String newFormat) {
    //this.displayAccuratePriceFormat = new DecimalFormat(newFormat);
    displayAccuratePriceFormat = (DecimalFormat)NumberFormat.getNumberInstance(zomLocale);
    displayAccuratePriceFormat.applyPattern(newFormat);
  }

  public static NumberFormat getDisplayCounterFormat() {
    return displayCounterFormat;
  }

  private static void setDisplayCounterFormat() {
    displayCounterFormat = NumberFormat.getNumberInstance(zomLocale);
    displayCounterFormat.setParseIntegerOnly(true);
    displayCounterFormat.setMaximumFractionDigits(0);
  }

  public static Date getTwoDigitYearStart() {
    return twoDigitYearStart;
  }

  public static Locale[] getAvailableLocales() {
    return DateFormat.getAvailableLocales();
  }

  private ZomFormat() {
  }

  public static void init(String[] args) {
    switch (args.length) {
      case 0: zomLocale = Locale.getDefault(); break;
      case 2: zomLocale = new Locale(args[0], args[1]); break;
      default:
        System.out.println("ZomFormat.ZomFormat: unexpected arguement count");
        zomLocale = Locale.getDefault();
    }

    System.out.println("Locale: " + zomLocale.getDisplayName());
    String ioDateFormatString = "yyyy.MM.dd"; // yyyy.MM.dd - dd-MMM-yyyy

    try {
      SimpleDateFormat tmpDateFormat = new SimpleDateFormat(ioDateFormatString);
      twoDigitYearStart = tmpDateFormat.parse("1937.01.01");
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    setIODateFormat(ioDateFormatString);
    setDisplayDateFormat(DateFormat.MEDIUM);

    setDisplayCurrencyFormat();

    setDisplayPercentFormat();

    ResourceBundle AccuratePrice = ResourceBundle.getBundle("com.byotrader.zom.resources.AccuratePrice");
    setDisplayAccuratePriceFormat(AccuratePrice.getString("Format"));

    setDisplayCounterFormat();
  }
}