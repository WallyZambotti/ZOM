package com.byotrader.zom;

import javax.swing.table.DefaultTableModel;
import java.awt.Color;
//import java.util.*;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public class PortfolioDefTableModel extends DefaultTableModel {
  PortfolioDataModel portfolioData;
  PortfolioControl portfolioControl;

  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return portfolioData.ColumnEditable[columnIndex];
  }

  public Class getColumnClass(int columnIndex) {
    return portfolioData.getRowVector(0).get(columnIndex).getClass();
  }

  public Color getColumnColor(int columnIndex, Color defaultColor) {
    return this.isCellEditable(0, columnIndex) ?
      new Color((float)0.9, (float)1.0, (float)0.9) :  // Light Pastel Green
      defaultColor;
  }

  public String getColumnToolTip(int columnIndex) {
    return portfolioData.ColumnToolTip[columnIndex];
  }

  public void removeRow(int rowIndex) {
    super.removeRow(rowIndex);
  }

  public PortfolioDefTableModel(PortfolioDataModel portfolioData) {
    super(portfolioData.RowData, portfolioData.ColumnNames);

    this.portfolioData = portfolioData;
  }
}