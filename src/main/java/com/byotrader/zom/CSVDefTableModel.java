package com.byotrader.zom;

import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.util.Vector;

//import java.util.*;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public class CSVDefTableModel extends DefaultTableModel {
  AnalysisCSVdataModel csvData;
  AnalysisControl analysisControl;

  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return false;
  }

  public Class getColumnClass(int columnIndex) {
    return ((Vector)csvData.getRowData().get(0)).get(0).getClass();
  }

  public Color getColumnColor(int columnIndex, Color defaultColor) {
    Color color;
    color = (columnIndex == 0) ?
      new Color((float)224/255, (float)212/255, (float)208/255) :  // Inactive Grey
      defaultColor;
    return color;
  }

  public String getColumnToolTip(int columnIndex) {
    return "CSV Data";
  }

  public void removeRow(int rowIndex) {
    super.removeRow(rowIndex);
  }

  public CSVDefTableModel(AnalysisCSVdataModel csvData) {
    super(csvData.getRowData(), csvData.getHeaderData());

    this.csvData = csvData;
  }
}