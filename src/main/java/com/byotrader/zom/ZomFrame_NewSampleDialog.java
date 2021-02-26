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

public class ZomFrame_NewSampleDialog extends JDialog implements ActionListener {
  AnalysisControl analysisControl;
  JPanel jPanel1 = new JPanel();
  JTextField jTextFieldSampleDate = new JTextField();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JTextField jTextFieldSamplePrice = new JTextField();
  JButton jButtonOK = new JButton();
  JButton jButtonCancel = new JButton();
  JLabel jLabel3 = new JLabel();
  JTextArea jTextArea1 = new JTextArea();

  public ZomFrame_NewSampleDialog(Frame parent, AnalysisControl analysisControl) {
    super(parent);
    this.analysisControl = analysisControl;
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
    jTextFieldSampleDate.setNextFocusableComponent(jTextFieldSamplePrice);
    jTextFieldSampleDate.setToolTipText("Enter the date of the trade.  Must be unique!");
    jTextFieldSampleDate.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        jTextFieldSampleDate_focusGained(e);
      }
    });
    jLabel1.setText("Share Date");
    jLabel2.setMaximumSize(new Dimension(67, 17));
    jLabel2.setMinimumSize(new Dimension(67, 17));
    jLabel2.setPreferredSize(new Dimension(67, 17));
    jLabel2.setText("Share Price");
    jButtonOK.setNextFocusableComponent(jButtonCancel);
    jButtonOK.setSelected(true);
    jButtonOK.setText("OK");
    jButtonOK.addActionListener(this);
    jButtonCancel.addActionListener(this);
    /*jButtonOK.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonOK_actionPerformed(e);
      }
    });*/
    jButtonCancel.setNextFocusableComponent(jTextFieldSampleDate);
    jButtonCancel.setText("Cancel");
    this.setResizable(false);
    this.setModal(true);
    jLabel3.setIcon(new ImageIcon(ZomResource.getResource("/com/byotrader/zom/resources", "CHCKBOOK.GIF")));
    jTextArea1.setWrapStyleWord(true);
    jTextArea1.setLineWrap(true);
    jTextArea1.setText("");
    jTextArea1.setToolTipText("");
    jTextArea1.setBackground(SystemColor.control);
    jTextArea1.setEditable(false);
    jTextArea1.setFont(new java.awt.Font("Serif", 0, 14));
    jPanel1.setMinimumSize(new Dimension(420, 240));
    jPanel1.setPreferredSize(new Dimension(450, 240));
    jTextFieldSamplePrice.setNextFocusableComponent(jButtonOK);
    jTextFieldSamplePrice.setToolTipText("Enter the trade price of the share.");
    jTextFieldSamplePrice.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        jTextFieldSamplePrice_focusGained(e);
      }
    });
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jTextFieldSampleDate, new GridBagConstraints(1, 2, 2, 1, 1.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 10, 0, 10), 337, 0));
    jPanel1.add(jLabel1, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 10, 7, 0), 0, 0));
    jPanel1.add(jLabel2, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 10, 7, 0), 0, 0));
    jPanel1.add(jTextFieldSamplePrice, new GridBagConstraints(1, 3, 2, 1, 1.0, 0.0
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
      if (this.analysisControl == null ||
        this.analysisControl.verifyNewSharePrice(
          jTextFieldSampleDate.getText(),
          jTextFieldSamplePrice.getText())) {
        cancel();
      }
    }
  }

  void jTextFieldSampleDate_focusGained(FocusEvent e) {
    jTextArea1.setText("Supply the date of the new trading entry.  Dates " +
    "are unique so it is not possible to have more than one entry per day. " +
    "Entries are automatically sorted by date.");
  }

  void jTextFieldSamplePrice_focusGained(FocusEvent e) {
    jTextArea1.setText("Supply the price of the new trading entry.  The " +
    "Share Price should be the price obtained from the exchange for " +
    "that date.  You can use opening, closing, highest or lowest prices," +
    "but which ever you choose be consistant.");
  }
}