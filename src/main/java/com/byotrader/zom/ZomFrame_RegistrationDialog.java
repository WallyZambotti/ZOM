package com.byotrader.zom;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;



/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

class ZomFrame_RegistrationDialog extends JDialog implements ActionListener {
  PortfolioControl portfolioControl;
  JPanel jPanel1 = new JPanel();
  JTextField jTextFieldKey = new JTextField();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JTextField jTextFieldCode = new JTextField();
  JButton jButtonOK = new JButton();
  JButton jButtonCancel = new JButton();
  JLabel jLabel3 = new JLabel();
  JTextArea jTextArea1 = new JTextArea();
  JTextArea jTextAreaConditions = new JTextArea();

  public ZomFrame_RegistrationDialog(Frame parent, PortfolioControl portfolioControl) {
    super(parent);
    this.portfolioControl = portfolioControl;
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
    this.setTitle("ZOM Registration");
    jPanel1.setLayout(gridBagLayout1);
    jTextFieldKey.setNextFocusableComponent(jTextFieldCode);
    jTextFieldKey.setToolTipText("Enter the provided registration key.");
    jTextFieldKey.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        jTextFieldKey_focusGained(e);
      }
    });
    jLabel1.setText("Key");
    jLabel2.setMaximumSize(new Dimension(67, 17));
    jLabel2.setMinimumSize(new Dimension(67, 17));
    jLabel2.setPreferredSize(new Dimension(67, 17));
    jLabel2.setText("Code");
    jButtonOK.setNextFocusableComponent(jButtonCancel);
    jButtonOK.setText("OK");
    jButtonOK.addActionListener(this);
    jButtonCancel.addActionListener(this);
    jButtonCancel.setNextFocusableComponent(jTextFieldKey);
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
    jPanel1.setMinimumSize(new Dimension(500, 400));
    jPanel1.setPreferredSize(new Dimension(500, 400));
    jTextFieldCode.setNextFocusableComponent(jButtonOK);
    jTextFieldCode.setToolTipText("Enter the provided registration code.");
    jTextFieldCode.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        jTextFieldCode_focusGained(e);
      }
    });
    jTextAreaConditions.setBorder(BorderFactory.createEtchedBorder());
    jTextAreaConditions.setText(
      "Conditions:\n\n" +
      "This copy of ZOM will become a full working version when a valid\n" +
      "Key/Code combination is entered.  Until then you are limited to \n" +
      "loading 3 stocks with 25 entries per stock. \n" +
      "This restriction is removed when a registration Key/Code is \n" +
      "purchased!\n\n" +
      "Warning it is possible to experiment with more data but when \n" +
      "ZOM is restarted the above restrictions will be enforced again!");
    jTextAreaConditions.setBackground(Color.lightGray);
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jTextFieldKey, new GridBagConstraints(2, 3, 2, 1, 1.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 230), 337, 0));
    jPanel1.add(jLabel1, new GridBagConstraints(0, 3, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 7, 0), 0, 0));
    jPanel1.add(jLabel2, new GridBagConstraints(0, 4, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 10, 7, 0), 0, 0));
    jPanel1.add(jTextFieldCode, new GridBagConstraints(2, 4, 2, 1, 1.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 10), 337, 1));
    jPanel1.add(jButtonCancel, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
    jPanel1.add(jLabel3, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jButtonOK, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(12, 0, 0, 0), 0, 0));
    jPanel1.add(jTextArea1, new GridBagConstraints(3, 1, 2, 2, 1.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 10), 1, 0));
    jPanel1.add(jTextAreaConditions, new GridBagConstraints(0, 6, 4, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(10, 10, 10, 10), 0, 0));
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
      if (this.portfolioControl == null ||
        this.portfolioControl.registerProduct(
          jTextFieldKey.getText(),
          jTextFieldCode.getText())) {
        cancel();
      }
    }
  }

  void jTextFieldKey_focusGained(FocusEvent e) {
    jTextArea1.setText(
      "Please enter the Key.  This is the \"ticket\" number supplied when " +
      "ZOM was purchased.  At purchase a copy of the Key is also e-mailed " +
      "to you by byoTrader.  If you have not purchased ZOM you may press " +
      "the Cancel button to use this product as a trial.");
  }

  void jTextFieldCode_focusGained(FocusEvent e) {
    jTextArea1.setText(
      "Please enter the Code that has been provided to you via e-mail by " +
      "byoTrader when ZOM was purchased.  This unique Code identifies you " +
      "to our support team.  If you have not purchased ZOM you may press " +
      "the Cancel button to use this product as a trial.");
  }
}