package com.byotrader.zom;


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;



/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public class ZomFrame_NewTradeDialog extends JDialog implements ActionListener {
  TradeControl tradeControl;
  JPanel jPanel1 = new JPanel();
  JTextField jTextFieldTradeDate = new JTextField();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JLabel jLabel1 = new JLabel();
  JTextField jTextFieldTradePrice = new JTextField();
  JButton jButtonOK = new JButton();
  JButton jButtonCancel = new JButton();
  JLabel jLabel3 = new JLabel();
  JTextArea jTextArea1 = new JTextArea();
  JLabel jLabel4 = new JLabel();
  JLabel jLabel5 = new JLabel();
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JTextField jTextFieldCash = new JTextField();
  JTextField jTextFieldInterest = new JTextField();
  JTextField jTextFieldBrokerage = new JTextField();
  JTextField jTextFieldTraded = new JTextField();
  JLabel jLabel2 = new JLabel();

  public ZomFrame_NewTradeDialog(Frame parent, TradeControl tradeControl) {
    super(parent);
    this.tradeControl = tradeControl;
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    pack();
  }
  private void jbInit() throws Exception {
    this.setTitle("Add new stock price");
    jPanel1.setLayout(gridBagLayout1);
    jTextFieldTradeDate.setNextFocusableComponent(jTextFieldTradePrice);
    jTextFieldTradeDate.setToolTipText("Enter the date of the trade.  Must be unique!");
    jTextFieldTradeDate.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        jTextFieldTradeDate_focusGained(e);
      }
    });
    jLabel1.setText("Trade Date");
    jButtonOK.setNextFocusableComponent(jButtonCancel);
    jButtonOK.setSelected(true);
    jButtonOK.setText("OK");
    jButtonOK.addActionListener(this);
    jButtonCancel.addActionListener(this);
    jButtonCancel.setNextFocusableComponent(jTextFieldTradeDate);
    jButtonCancel.setText("Cancel");
    this.setResizable(false);
    this.setModal(true);
    jLabel3.setIcon(new ImageIcon(ZomResource.getResource("/com/byotrader/zom/resources", "LETTER.GIF")));
    jTextArea1.setWrapStyleWord(true);
    jTextArea1.setLineWrap(true);
    jTextArea1.setText("");
    jTextArea1.setToolTipText("");
    jTextArea1.setBackground(SystemColor.control);
    jTextArea1.setEditable(false);
    jTextArea1.setFont(new java.awt.Font("Serif", 0, 14));
    jPanel1.setMinimumSize(new Dimension(420, 340));
    jPanel1.setPreferredSize(new Dimension(450, 340));
    jTextFieldTradePrice.setNextFocusableComponent(jTextFieldCash);
    jTextFieldTradePrice.setToolTipText("Enter the confirmed trade price of the share.");
    jTextFieldTradePrice.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        jTextFieldTradePrice_focusGained(e);
      }
    });
    jLabel4.setText("Cash");
    jLabel5.setText("Interest");
    jLabel6.setText("Brokerage");
    jLabel7.setText("Shares Traded");
    jLabel2.setText("Share Price");
    jTextFieldCash.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        jTextFieldCash_focusGained(e);
      }
    });
    jTextFieldInterest.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        jTextFieldInterest_focusGained(e);
      }
    });
    jTextFieldBrokerage.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        jTextFieldBrokerage_focusGained(e);
      }
    });
    jTextFieldTraded.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        jTextFieldTraded_focusGained(e);
      }
    });
    jTextFieldCash.setNextFocusableComponent(jTextFieldInterest);
    jTextFieldCash.setToolTipText("Alter the cash reserve.");
    jTextFieldInterest.setNextFocusableComponent(jTextFieldBrokerage);
    jTextFieldInterest.setToolTipText("Reconcile any bank interest descrepencies.");
    jTextFieldBrokerage.setNextFocusableComponent(jTextFieldTraded);
    jTextFieldBrokerage.setToolTipText("Override the brokerage fee if required.");
    jTextFieldTraded.setNextFocusableComponent(jButtonOK);
    jTextFieldTraded.setToolTipText("Buy share (positive) or sell (negative), 0 (zero) for no trade.");
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jTextFieldTradeDate, new GridBagConstraints(1, 1, 2, 1, 1.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 10), 337, 0));
    jPanel1.add(jLabel1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 7, 0), 0, 0));
    jPanel1.add(jTextFieldTradePrice, new GridBagConstraints(1, 2, 2, 1, 1.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 10), 337, 1));
    jPanel1.add(jLabel3, new GridBagConstraints(0, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jTextArea1, new GridBagConstraints(2, 0, 2, 1, 1.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 2), 1, 0));
    jPanel1.add(jLabel4, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 7, 0), 0, 0));
    jPanel1.add(jLabel5, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 7, 0), 0, 0));
    jPanel1.add(jLabel6, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 7, 0), 0, 0));
    jPanel1.add(jLabel7, new GridBagConstraints(0, 6, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 4, 7, 0), 0, 0));
    jPanel1.add(jButtonOK, new GridBagConstraints(1, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(12, 0, 0, 0), 0, 0));
    jPanel1.add(jButtonCancel, new GridBagConstraints(2, 7, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
    jPanel1.add(jTextFieldCash, new GridBagConstraints(1, 3, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 10, 0, 10), 337, 1));
    jPanel1.add(jTextFieldInterest, new GridBagConstraints(1, 4, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 10, 0, 10), 337, 1));
    jPanel1.add(jTextFieldBrokerage, new GridBagConstraints(1, 5, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 10, 0, 10), 337, 1));
    jPanel1.add(jTextFieldTraded, new GridBagConstraints(1, 6, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 10, 0, 10), 337, 1));
    jPanel1.add(jLabel2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 7, 0), 0, 0));
    this.getRootPane().setDefaultButton(jButtonOK);
  }
  /**Close the dialog*/
  void cancel() {
    dispose();
  }
  /**Close the dialog on a button event*/
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == jButtonCancel) {
      cancel();
    }

    if (e.getSource() == jButtonOK) {
      if (this.tradeControl == null ||
        this.tradeControl.verifyNewTrade(
          jTextFieldTradeDate.getText(),
          jTextFieldTradePrice.getText(),
          jTextFieldCash.getText(),
          jTextFieldInterest.getText(),
          jTextFieldBrokerage.getText(),
          jTextFieldTraded.getText())) {
        cancel();
      }
    }
  }

  void jTextFieldTradeDate_focusGained(FocusEvent e) {
    jTextArea1.setText("Supply the date for the new trading entry.  Dates " +
    "are unique so it is not possible to have more than one entry per day.");
  }

  void jTextFieldTradePrice_focusGained(FocusEvent e) {
    jTextArea1.setText("Supply the share price of the stock.  This should " +
    "be the confirmed sell or buy price and not the expected or market value.");
  }

  void jTextFieldCash_focusGained(FocusEvent e) {
    jTextArea1.setText("If you wish to add or take some cash from this stock " +
    "then modify the cash value here.  Consider that any change will affect " +
    "the share buying power. i.e: if you remove all cash then there will be " +
    "none left for trading!");
  }

  void jTextFieldInterest_focusGained(FocusEvent e) {
    jTextArea1.setText("The interest amount is a simple daily calculated " +
    "interest calculation.   If you wish to reconcile the interest to the " +
    "actual interest earnt then you should change the value here.");
  }

  void jTextFieldBrokerage_focusGained(FocusEvent e) {
    jTextArea1.setText("The brokerage fee is taken from the trading form " +
    "in the display.   If this charge is not correct for what ever reason " +
    "then you should change the value here.");
  }

  void jTextFieldTraded_focusGained(FocusEvent e) {
    jTextArea1.setText("Enter the number of shares to either buy (postive figure) " +
    "or sell (negative figure).   If you are only changing the cash value (from " +
    "above) then enter 0 (zero) to indicate no trade.");
  }
}