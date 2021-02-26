package com.byotrader.zom;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public class ZomNumberRenderer extends DefaultTableCellRenderer {
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocused, int row, int column) {
    Component component = super.getTableCellRendererComponent(table, value, isSelected, isFocused, row, column);

    if (value == null) {
      ((JLabel)component).setText("");
    }
    else {
      Color posCol = isSelected ? isFocused ? Color.black : Color.white : Color.black;

      if (isSelected) {
        if (isFocused) {
          if (table.isCellEditable(row, column)) {
            posCol = Color.black;
          }
          else {
            posCol = (Color)SystemColor.window;
          }
        }
        else {
          posCol = (Color)SystemColor.window;
        }
      }
      else {
        posCol = Color.black;
      }

      if (value instanceof ZomCounter) {
        ((JLabel)component).setForeground(((ZomCounter)value).getValue() < 0 ? Color.red : posCol);
      }
      else if (value instanceof ZomCurrency) {
        ((JLabel)component).setForeground(((ZomCurrency)value).getValue() < 0 ? Color.red : posCol);
      }
      else if (value instanceof ZomAccuratePrice) {
        ((JLabel)component).setForeground(((ZomAccuratePrice)value).getValue() < 0 ? Color.red : posCol);
      }
      else if (value instanceof ZomPercent) {
        ((JLabel)component).setForeground(((ZomPercent)value).getValue() < 0 ? Color.red : posCol);
      }

      ((JLabel)component).setText(value.toString());
    }

    return component;
  }
}