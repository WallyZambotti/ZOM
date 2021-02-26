package com.byotrader.zom;

//import javax.swing.table.*;
import javax.swing.table.AbstractTableModel;
import java.util.*;
//import com.ibm.xml.parsers.*;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public class PortfolioAbsTableModel extends AbstractTableModel {
  Object[][] PortfolioData = {
    { "Electronic Resource Group", "ERG", new Date("21-Dec-2000"), Double.valueOf("12345.6") },
    { "Coles Myre", "CML", new Date("20-Jan-2001") , Double.valueOf("555.55") },
    { "Bank West", "BWA", new Date(), Double.valueOf("10000.00")  },
    { "Stock X", "S12", new Date(), Double.valueOf("10000.00")  },
    { "Stock Y", "S23", new Date(), Double.valueOf("10000.00")  },
    { "Stock Z", "S34", new Date(), Double.valueOf("10000.00")  },
    { "Stock Off", "SOFF", new Date(), Double.valueOf("10000.00")  }
  };

  String[] ColumnNames = {
    "Stock Name", "Stock Code", "Last Updated", "Value"
  };

  boolean[] ColumnEditable = {
    true, true, false, false
  };


  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return ColumnEditable[columnIndex];
  }

  public void setValueAt(Object value, int rowIndex, int columnIndex) {
    if (value.getClass() == getColumnClass(columnIndex) && isCellEditable(rowIndex, columnIndex)) {
        PortfolioData[rowIndex][columnIndex] = value;
        fireTableCellUpdated(rowIndex, columnIndex);
    }
  }

  public int getRowCount() {
    return PortfolioData.length;
  }

  public int getColumnCount() {
    return PortfolioData[0].length;
  }

  public Object getValueAt(int row, int col) {
    return PortfolioData[row][col];
  }

  public String getColumnName(int columnIndex) {
    return ColumnNames[columnIndex];
  }

  public Class getColumnClass(int columnIndex) {
    return PortfolioData[0][columnIndex].getClass();
  }
}