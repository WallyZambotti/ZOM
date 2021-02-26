package com.byotrader.zom;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.lang.Comparable;
import java.util.Locale;
import java.text.ParseException;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public class ZomDate extends ZomType implements Comparable {
  final int POS = 0;
  final int LEN = 1;
  final int TYPE = 2;
  final int ALLTYPES = 7;

  private Date date;
  private SimpleDateFormat ioDateFormat;
  private DateFormat displayDateFormat;
  private Locale locales[];
  private SimpleDateFormat recordedFormat;
  private Date twoDigitYearStart;

  private int predfinedFormats[] = new int[] {
    DateFormat.DEFAULT, DateFormat.FULL, DateFormat.LONG, DateFormat.MEDIUM, DateFormat.SHORT
  };

  private String[][] dateFieldFormats = new String[][] {
    { "d", "dd", null, null },
    { "M", "MM", "MMM", "MMMM" },
    { "yy", "yyyy", null, null }
  };

//  private String dateFormats[][] = new String[][] {
//    { "d", "M", "yy" }, { "dd", "M", "yy" }, { "d", "MM", "yy" }, { "dd", "MM", "yy" },
//    { "d", "MMM", "yy" }, { "dd", "MMM", "yy" }, { "d", "MMMM", "yy" }, { "dd", "MMMM", "yy" },
//    { "M", "d", "yy" }, { "M", "dd", "yy" }, { "MM", "d", "yy" }, { "MM", "dd", "yy" },
//    { "MMM", "d", "yy" }, { "MMM", "dd", "yy" }, { "MMMM", "d", "yy" }, { "MMMM", "dd", "yy" },
//    { "d", "M", "yyyy" }, { "dd", "M", "yyyy" }, { "d", "MM", "yyyy" }, { "dd", "MM", "yyyy" },
//    { "d", "MMM", "yyyy" }, { "dd", "MMM", "yyyy" }, { "d", "MMMM", "yyyy" }, { "dd", "MMMM", "yyyy" },
//    { "M", "d", "yyyy" }, { "M", "d", "yyyy" }, { "MM", "d", "yyyy" }, { "MM", "dd", "yyyy" },
//    { "MMM", "d", "yyyy" }, { "MMM", "dd", "yyyy" }, { "MMMM", "d", "yyyy" }, { "MMMM", "dd", "yyyy" },
//    { "yy", "M", "d" }, { "yy", "M", "dd" }, { "yy", "MM", "d" }, { "yy", "MM", "dd" },
//    { "yy", "MMM", "d" }, { "yy", "MMM", "dd" }, { "yy", "MMMM", "d" }, { "yy", "MMMM", "dd" },
//    { "yyyy", "M", "d" }, { "yyyy", "M", "dd" }, { "yyyy", "MM", "d" }, { "yyyy", "MM", "dd" },
//    { "yyyy", "MMM", "d" }, { "yyyy", "MMM", "dd" }, { "yyyy", "MMMM", "d" }, { "yyyy", "MMMM", "dd" }
//  };

  private String dateSep[] = new String[] { " ", "/", "-", "." };

  public ZomDate() {
    this.ioDateFormat = ZomFormat.getIODateFormat();
    this.displayDateFormat = ZomFormat.getDisplayDateFormat();
    this.locales = DateFormat.getAvailableLocales();
    this.twoDigitYearStart = ZomFormat.getTwoDigitYearStart();

    this.date = new Date();
  }

  public ZomDate(String newDate) {
    this.ioDateFormat = ZomFormat.getIODateFormat();
    this.displayDateFormat = ZomFormat.getDisplayDateFormat();
    this.locales = DateFormat.getAvailableLocales();
    this.twoDigitYearStart = ZomFormat.getTwoDigitYearStart();

    this.date = new Date(makeDate(newDate));
  }

  public ZomDate(long newDate) {
    this.ioDateFormat = ZomFormat.getIODateFormat();
    this.displayDateFormat = ZomFormat.getDisplayDateFormat();
    this.locales = DateFormat.getAvailableLocales();
    this.twoDigitYearStart = ZomFormat.getTwoDigitYearStart();

    this.date = new Date(newDate);
  }

  public Double getAmount() {
    return null;
  }

  public double getValue() {
    return 0.0;
  }

  public Date getDate() {
    return this.date;
  }

  public void setDate(String newDate) {
    this.date.setTime(this.makeDate(newDate));
  }

  public long getTime() {
    return this.date.getTime();
  }

  public void setValue(long value) {
    this.date.setTime(value);
  }

  // The Table models rely on toString for formatting so override with display format

  public String toString() {
    return this.displayDateFormat.format(this.date);
  }

  // XML model relies on  ioString for formatting

  public String ioString() {
    return this.ioDateFormat.format(this.date);
  }

  private long makeDate(String newDate) {
    long theDate = 0;

    try {
      theDate = this.ioDateFormat.parse(newDate).getTime();
    } catch (Exception ex) {
      try {
        SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        theDate = newDateFormat.parse(newDate).getTime();
      } catch (Exception ex2) {
        ex.printStackTrace();
      }
    }

    return theDate;
  }

  public void ioParse(String newDate) throws Exception {
    this.date.setTime(this.ioDateFormat.parse(newDate).getTime());
  }

  public void formatParse(String newDate) throws Exception {
    this.date.setTime(this.displayDateFormat.parse(newDate).getTime());
  }

  public void exhaustiveParse(String newDate) throws ParseException {
    final int DAY = 1;
    final int MONTH = 2;
    final int YEAR = 4;
    boolean parsed;
//
//    // Check the last used Locale/Format combination to speed things up.
//    if (this.recordedFormat != null) {
//      try {
//        this.date.setTime(this.recordedFormat.parse(newDate).getTime());
//        parsed = true;
//        return;
//      } catch (Exception ex) {
//        parsed = false;
//      }
//    }
//
    // Did not succeed with previous format continue with parse
    int[] fieldHints = new int[] { ALLTYPES, ALLTYPES, ALLTYPES };
    String[] field = new String[3];
    String sep = null;

//    // Determine what separator is been used and the first field sub string
//    int seppos = 0;
//
//    for (int idx = 0 ; idx < dateSep.length ; idx++) {
//      if ((seppos = newDate.indexOf(dateSep[idx])) != -1)  {
//        sep = dateSep[idx];
//        field[0] = newDate.substring(0, seppos);
//        break;
//      }
//    }
//
//    if (sep == null) {
//      throw new ParseException("ZomDate.exhaustiveParse: No valid separator", 0);
//    }
//
//    // Using the known separator determine the other two field lengths and positions
//    int lastseppos = seppos;
//    seppos = newDate.indexOf(sep, seppos+1); // second and last separator position
//    field[1] = newDate.substring(lastseppos+1, seppos);
//    field[2] = newDate.substring(seppos+1);

    // Rebuild the supplied date into a temp date using single spaces as
    // the date separator.

    // Walk the date string and delimit the date fields on punctuation boundaries

    int fieldCnt = -1, fieldStart = 0;
    final int PUNCTUATION = 1;
    final int LETTERDIGIT = 2;
    int state = PUNCTUATION;
    String tempDate = newDate + " "; // Create a trailing punctuation;

    for (int idx = 0 ; idx < tempDate.length() ; idx++) {
      boolean isCharDigit = Character.isLetterOrDigit(tempDate.charAt(idx));

      switch (state) {
        // When ever a Letter or Digit is encountered while scanning punctuation
        // change state to Letter Digit and record the location as the field start
        case PUNCTUATION:
          if (isCharDigit) {
            fieldStart = idx;
            state = LETTERDIGIT;
          }
        break;
        // When ever punctuation is encountered while scanning Letters & Digits
        // change the state to punctuation and record the new field
        case LETTERDIGIT:
          if (!isCharDigit) {
            field[++fieldCnt] = tempDate.substring(fieldStart, idx);
            state = PUNCTUATION;
          }
        break;
      }
    }

    // check to see we found three fields (0,1,2)
    if (fieldCnt != 2) {
      throw new ParseException("ZomDate.exhaustiveParse: Not enough valid field separators found", 0);
    }

    // glue the temp date from the individual fields and the known separator
    sep = " ";
    tempDate = field[0] + sep + field[1] + sep + field[2];

    // Check the last used Locale/Format combination to speed things up.
    if (this.recordedFormat != null) {
      try {
        this.date.setTime(this.recordedFormat.parse(tempDate).getTime());
        parsed = true;
        return;
      } catch (Exception ex) {
        parsed = false;
      }
    }

    // Try and limit each field to either day month or year
    boolean firstFieldIsYear = false;

    for (int idx = 0 ; idx <= 2 ; idx++) {
      if (Character.isLetter(field[idx].charAt(0))) {
        fieldHints[idx] &= MONTH;  // this field is month and ...
        fieldHints[(idx + 1) % 3] &= ~MONTH;  // the other fields cannot ..
        fieldHints[(idx + 2) % 3] &= ~MONTH;  // be month
      }
      else { // Must be Numeric
        int value = Integer.parseInt(field[idx]);

        if (field[idx].length() == 4 || value > 31) {
          fieldHints[idx] &= YEAR;  // this field is year and ...
          fieldHints[(idx + 1) % 3] &= ~YEAR;  // the other fields cannot ..
          fieldHints[(idx + 2) % 3] &= ~YEAR;  // be year
          // Record the year field as first if first
          firstFieldIsYear = idx == 0;
        }
        else if (value > 12) {// almost certainly a day value
          fieldHints[idx] &= DAY;  // this field is day and ...
          fieldHints[(idx + 1) % 3] &= ~DAY;  // the other fields cannot ..
          fieldHints[(idx + 2) % 3] &= ~DAY;  // be day
        }
      }
    }

    // If the first field is the year but the other two fields are ambiguous then
    // promote the second field to be the month and the third field to be the day.
    if (firstFieldIsYear && (fieldHints[1] & fieldHints[2]) == (MONTH + DAY)) {
      fieldHints[1] = MONTH;
      fieldHints[2] = DAY;
    }

    // Now use the TYPE information to parse the fields with relevant formats
    parseFields(tempDate, field, sep, fieldHints, 0, 0, "");

//    // Otherwise start a long search for a workable date format
//    // For each locale
//    for (int ilx = 0 ; ilx < locales.length ; ilx++) {
//      Locale locale = this.locales[ilx];
//      SimpleDateFormat newDateFormat = new SimpleDateFormat("y M d", locale);
//      newDateFormat.set2DigitYearStart(this.twoDigitYearStart);
//      // For each date seperator
//      for (int idx = 0 ; idx < dateSep.length ; idx++) {
//        String sep = dateSep[idx];
//        // For each date format
//        for (int ifx = 0 ; ifx < dateFormats.length ; ifx++) {
//          String newFormat = dateFormats[ifx][0] + sep + dateFormats[ifx][1] + sep + dateFormats[ifx][2];
//          newDateFormat.applyLocalizedPattern(newFormat);
//          try {
//            this.date.setTime(newDateFormat.parse(newDate).getTime());
//            this.recordedFormat = newDateFormat;
//            parsed = true;
//            return;
//          } catch (Exception ex) {
//            parsed = false;
//          }
//        }
//      }
//    }
  }

  private boolean parseFields(String newDate, String[] field, String sep, int[] fieldHints, int typeInUse, int first, String format) {
    String tmpFormat;

    // Rip through field
    for (int i = first; i <= 2; i++) {
      // If field can be of certain type and type not already in use
      int type = (fieldHints[i] & ~typeInUse);
      // Rip through each type
      for (int j = 0; j <= 2; j++) {
        int thisType = (1 << j);
        if ((type & thisType) != 0) {
          // Rip through all type formats
          for (int k = 0; k < dateFieldFormats[j].length; k++){
            if (dateFieldFormats[j][k] != null) {
              tmpFormat = format.toString();
              if (format.length() != 0) { tmpFormat += sep; }
              tmpFormat += dateFieldFormats[j][k];
              typeInUse |= thisType;
              // If all the fields have had a format associated to them then parse the date
              if (typeInUse == ALLTYPES && allLocaleParse(tmpFormat, newDate)) {
                return true;
              }
              // If this is not the last field then process the next field
              if (first < 2 && parseFields(newDate, field, sep, fieldHints, typeInUse, first+1, tmpFormat)) {
                return true;
              }
            }
          }
        }
      }
    }

    return false;
  }

  private boolean allLocaleParse(String newFormat, String newDate) {
    boolean parsedOK = false;

    for (int ilx = 0 ; ilx < locales.length ; ilx++) {
      Locale locale = this.locales[ilx];
      SimpleDateFormat newDateFormat = new SimpleDateFormat(newFormat, locale);
      newDateFormat.set2DigitYearStart(this.twoDigitYearStart);
      try {
        this.date.setTime(newDateFormat.parse(newDate).getTime());
        this.recordedFormat = newDateFormat;
        parsedOK = true;
        break;
      } catch (Exception ex) {
        parsedOK = false;
      }
    }

    return parsedOK;
  }

  // Comparable Interface
  public int compareTo(Object compDate) {
    return this.date.compareTo(((ZomDate)compDate).getDate());
  }
}