package com.byotrader.zom;

import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.FileInputStream;
import java.util.TreeMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Vector;
import java.lang.Character;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public class AnalysisCSVdataModel {
  // A class with a counter for use as a Value to the TreeMap class
  private class ColTally {
    public int count;

    public ColTally(int newCount) {
      this.count = newCount;
    }
  }

  // A class that serves only to provide the exhaustiveParse method for
  // for Label/Identifier data/strings/tokens
  private class LabelParser {
    public LabelParser() {
    }

    public void exhaustiveParse(String token) throws Exception {
      if (!Character.isLetter(token.trim().charAt(0))) {
        throw new Exception("Label not Found");
      }
    }
  }

  // Parsers Resources
  private ZomDate dateParser;
  private ZomAccuratePrice valueParser;
  private LabelParser labelParser;

  // Deliverables
  private File csvFile;
  private int noCols;
  private int dateCol;
  private int valueCol;
  private int startRow;
  private int endRow;
  private int headerRow;
  private Vector rowData;
  private Vector headerData;

  // Deliverable Access
  public int getColumnCount() { return this.noCols; }

  public int getDateColumn() { return this.dateCol; }

  public int getValueColumn() { return this.valueCol; }

  public int getStartRow() { return this.startRow; }

  public int getEndRow() { return this.endRow; }

  public int getHeaderRow() { return this.headerRow; }

  public Vector getRowData() { return this.rowData; }

  public Vector getHeaderData() { return this.headerData; }

  public File getCSVfile() { return this.csvFile; }

  public void setDateColumn(int newCol) { this.dateCol = newCol; }

  public void setValueColumn(int newCol) { this.valueCol = newCol; }

  public void setStartRow(int newRow) { this.startRow = newRow; }

  public void setEndRow(int newRow) { this.endRow = newRow; }

  // Construct-or object
  public AnalysisCSVdataModel() {
    this.dateParser = new ZomDate();
    this.valueParser = new ZomAccuratePrice();
    this.labelParser = new LabelParser();
  }

  // Perform a three span scan of the CSV file.
  // First determine the number of columns in the file.
  // Next determine the which columns are the Date & Value columns
  // Last load the file into the data model using the forgiven knowledge
  public boolean scanCSVfile(File CSVFile) {
    BufferedReader brCSV;

    this.csvFile = CSVFile;

    if (CSVFile == null) {
      return false;
    }

    if((brCSV = openCSVfile(CSVFile)) == null) {
      return false;
    }

    // Pass 1 - Lets determine how many columns in this csv file.
    this.noCols = this.countCSVcols(brCSV);
    System.out.println("No of cols = " + this.noCols);

    this.closeCSVfile(brCSV);

    if((brCSV = openCSVfile(CSVFile)) == null) {
      return false;
    }

    // Pass 2 - Lets see if the columns and rows can be determined
    this.determineColTypes(brCSV);

    this.closeCSVfile(brCSV);

    if((brCSV = openCSVfile(CSVFile)) == null) {
      return false;
    }

    // Pass 3 - Lets load the rows into a data model
    this.loadCSVfile(brCSV);

    this.closeCSVfile(brCSV);

    // In case the CSV file had no header give it one!
    this.setMissingHeader();

    return (
      this.noCols > 1 &&
      this.startRow > this.headerRow &&
      this.dateCol > 0 &&
      this.valueCol > 0);
  }

  // Pass 1 - methods here for Column Count Determination

  private int countCSVcols(BufferedReader brCSV) {
    TreeMap cntFreq = new TreeMap();
    int colCnt = 0;
    String CSVLine;

    while((CSVLine = getCSVline(brCSV)) != null) {
      if((colCnt = getColCnt(CSVLine)) != 0) {
        Integer colKeyCnt = new Integer(colCnt);
        ColTally existingValue = (ColTally)cntFreq.get(colKeyCnt);
        if (existingValue == null) {
          cntFreq.put(colKeyCnt, new ColTally(1));
        }
        else {
          existingValue.count++;
        }
      }
    }

    return this.maxColCnt(cntFreq);
  }

  private int getColCnt(String CSVLine) {
    int cidx = 0;
    int ccnt = 0;

    while ((cidx = CSVLine.indexOf(",", cidx)) != -1) {
      ccnt++;
      cidx++;
    }

    return ccnt+1; // Number of comma's indicate number of columns + 1
  }

  private int maxColCnt(TreeMap cntFreq) {
    int cnt;
    int col;
    int max = 0;
    int maxCol = 0;
    Set keySet = cntFreq.keySet();
    Iterator keys = keySet.iterator();

    while(keys.hasNext()) {
      col = ((Integer)keys.next()).intValue();
      cnt = ((ColTally)cntFreq.get(new Integer(col))).count;
      System.out.println(Integer.toString(cnt) + " lines with " + Integer.toString(col) + " columns");
      if (cnt > max) {
        maxCol = col;
        max = cnt;
      }
    }

    return maxCol;
  }

  // Pass 2 - methods here for Column Type And Row Start End Determination

  public void determineColTypes(BufferedReader brCSV) {
    String CSVLine;
    int lineCnt = 0;

    while((CSVLine = getCSVline(brCSV)) != null) {
      lineCnt++;

      if(getColCnt(CSVLine) == this.noCols) {
        this.columniseLine(CSVLine, lineCnt);
        this.endRow = lineCnt;
      }
    }

    System.out.println("End row @ " + this.endRow);
  }

  private void columniseLine(String CSVLine, int lineCnt) {
    int cidx = 0;
    int prevcidx = 0;
    int ccnt = 0;

    while ((cidx = CSVLine.indexOf(",", cidx)) != -1) {
      this.parseToken(
        this.cleanToken(CSVLine.substring(prevcidx, cidx)), ++ccnt, lineCnt);
      prevcidx = ++cidx;
    }

    this.parseToken(
      this.cleanToken(CSVLine.substring(prevcidx)), ++ccnt, lineCnt);
  }

  private String cleanToken(String token) {
    token = token.trim();
    int len = token.length();

    if (token.charAt(0) == '"' && token.charAt(len-1) == '"') {
      token = token.substring(1, len-1);
    }

    return token;
  }

  private void parseToken(String token, int colNo, int lineCnt) {
    // Lets see if its a date!
    if (this.dateCol == 0) {
      try {
        this.dateParser.exhaustiveParse(token);
        this.dateCol = colNo;
        System.out.println("Date Col = " + colNo);

        if (this.startRow == 0) {
          this.startRow = lineCnt;
          System.out.println("Start row @ " + lineCnt);
        }
      } catch (Exception ex) {
      }
    }
    // Lets see if its a value!
    if (this.valueCol == 0) {
      try {
        if (token.indexOf(".") >= 0) {
          this.valueParser.exhaustiveParse(token);
          this.valueCol = colNo;
          System.out.println("Value Col = " + colNo);

          if (this.startRow == 0) {
            this.startRow = lineCnt;
            System.out.println("Start row @ " + lineCnt);
          }
        }
      } catch (Exception ex) {
      }
    }
    // Lets see if its a header label
    if (this.headerRow == 0) {
      try {
        this.labelParser.exhaustiveParse(token);
        this.headerRow = lineCnt;
        System.out.println("Header Line @ " + lineCnt);
      }
      catch (Exception ex) {
        System.out.println(
          "AnaylsisCSVdataModel.parseToken: could not determine column type "
           + Integer.toString(lineCnt) + ":" + Integer.toString(colNo));
      }
    }
  }

  // Pass 3 - methods for loading the CSV file into a data model

  private void loadCSVfile(BufferedReader brCSV) {
    this.rowData = new Vector(100, 100);
    String CSVLine;
    int lineCnt = 0;
    int rowCnt = 0;

    while((CSVLine = getCSVline(brCSV)) != null) {
      lineCnt++;

      if(getColCnt(CSVLine) == this.noCols) {
        if (lineCnt == this.headerRow) {
          this.loadHeaderLine(CSVLine);
        }
        else {
          this.loadLine(CSVLine, ++rowCnt);
        }
      }
    }
  }

  private void loadHeaderLine(String CSVLine) {
    this.headerData = new Vector(this.noCols + 1);
    int cidx = 0;
    int prevcidx = 0;

    headerData.add(" ");

    while ((cidx = CSVLine.indexOf(",", cidx)) != -1) {
      headerData.add(this.cleanToken(CSVLine.substring(prevcidx, cidx)));
      prevcidx = ++cidx;
    }

    headerData.add(this.cleanToken(CSVLine.substring(prevcidx)));
  }

  private void setMissingHeader() {
    if (this.headerData != null) { return; }

    this.headerData = new Vector(this.noCols + 1);

    headerData.add(" ");

    for (int cidx = 1 ; cidx <= this.noCols ; cidx++) {
      headerData.add(Integer.toString(cidx));
    }
  }

  private void loadLine(String CSVLine, int lineCnt) {
    Vector aRowData = new Vector(this.noCols + 1);
    int cidx = 0;
    int prevcidx = 0;

    aRowData.add(Integer.toString(lineCnt));

    while ((cidx = CSVLine.indexOf(",", cidx)) != -1) {
      aRowData.add(this.cleanToken(CSVLine.substring(prevcidx, cidx)));
      prevcidx = ++cidx;
    }

    aRowData.add(this.cleanToken(CSVLine.substring(prevcidx)));
    this.rowData.add(aRowData); // Add each row vector to the rows vector
  }

  // Supporting IO methods

  private BufferedReader openCSVfile(File CSVFile) {
    InputStream inCSV;

    try {
      inCSV = new FileInputStream(CSVFile);
    }
    catch (Exception ex) {
      return null;
    }

    return new BufferedReader(new InputStreamReader(inCSV));
  }

  private void closeCSVfile(BufferedReader brCSV) {
    try {
      brCSV.close();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private String getCSVline(BufferedReader brCSV) {
    try {
      return brCSV.readLine();
    }
    catch (Exception ex) {
      return null;
    }
  }
}