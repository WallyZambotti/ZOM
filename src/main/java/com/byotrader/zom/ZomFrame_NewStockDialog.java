package com.byotrader.zom;


import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.*;
import java.awt.event.*;



/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

class ZomFrame_NewStockDialog extends JDialog implements ActionListener {
  PortfolioControl portfolioControl;
  JPanel jPanel1 = new JPanel();
  JTextField jTextFieldStockName = new JTextField();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JTextField jTextFieldStockCode = new JTextField();
  JButton jButtonOK = new JButton();
  JButton jButtonCancel = new JButton();
  JLabel jLabel3 = new JLabel();
  JTextArea jTextArea1 = new JTextArea();

  public ZomFrame_NewStockDialog(Frame parent, PortfolioControl portfolioControl) {
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
    this.setTitle("Add new stock");
    jPanel1.setLayout(gridBagLayout1);
    jTextFieldStockName.setNextFocusableComponent(jTextFieldStockCode);
    jTextFieldStockName.setToolTipText("Enter a unique stock name here.");
    jTextFieldStockName.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        jTextFieldStockName_focusGained(e);
      }
    });
    jLabel1.setText("Stock Name");
    jLabel2.setMaximumSize(new Dimension(67, 17));
    jLabel2.setMinimumSize(new Dimension(67, 17));
    jLabel2.setPreferredSize(new Dimension(67, 17));
    jLabel2.setText("Stock Code");
    jButtonOK.setNextFocusableComponent(jButtonCancel);
    jButtonOK.setText("OK");
    jButtonOK.addActionListener(this);
    jButtonCancel.addActionListener(this);
    jButtonCancel.setNextFocusableComponent(jTextFieldStockName);
    jButtonCancel.setText("Cancel");
    this.setResizable(false);
    this.setModal(true);
    jLabel3.setIcon(new ImageIcon(ZomResource.getResource("/com/byotrader/zom/resources", "FILES.GIF")));
    jTextArea1.setWrapStyleWord(true);
    jTextArea1.setLineWrap(true);
    jTextArea1.setText("");
    jTextArea1.setToolTipText("");
    jTextArea1.setBackground(SystemColor.control);
    jTextArea1.setEditable(false);
    jTextArea1.setFont(new java.awt.Font("Serif", 0, 14));
    jPanel1.setMinimumSize(new Dimension(420, 240));
    jPanel1.setPreferredSize(new Dimension(450, 240));
    jTextFieldStockCode.setNextFocusableComponent(jButtonOK);
    jTextFieldStockCode.setToolTipText("The stock code as refered to by your exchange.");
    jTextFieldStockCode.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        jTextFieldStockCode_focusGained(e);
      }
    });
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jTextFieldStockName, new GridBagConstraints(1, 2, 2, 1, 1.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 10), 337, 0));
    jPanel1.add(jLabel1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 10, 7, 0), 0, 0));
    jPanel1.add(jLabel2, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 10, 7, 0), 0, 0));
    jPanel1.add(jTextFieldStockCode, new GridBagConstraints(1, 3, 2, 1, 1.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 10), 337, 1));
    jPanel1.add(jButtonCancel, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 0, 0, 0), 0, 0));
    jPanel1.add(jLabel3, new GridBagConstraints(0, 1, 2, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jButtonOK, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(12, 0, 0, 0), 0, 0));
    jPanel1.add(jTextArea1, new GridBagConstraints(2, 1, 2, 1, 1.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 1, 0));
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
        this.portfolioControl.verifyNewStock(
          jTextFieldStockName.getText(),
          jTextFieldStockCode.getText())) {
        cancel();
      }
    }
  }

  void jTextFieldStockName_focusGained(FocusEvent e) {
    jTextArea1.setText("Supply a Stock Name for your personal identification.  Stock names " +
    "are unique so if you intend to have several for the same stock code " +
    "then make each name slightly different.");
  }

  void jTextFieldStockCode_focusGained(FocusEvent e) {
    jTextArea1.setText("Supply the Stock Code.  The Stock Code should be " +
    "that assigned by the exchange.  It need not be unique so it is possible " +
    "to trade with the same stock code repeatedly.");
  }
}