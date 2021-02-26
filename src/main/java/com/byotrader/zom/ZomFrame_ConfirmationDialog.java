package com.byotrader.zom;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Frame;
import java.awt.AWTEvent;
import java.awt.Dimension;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public class ZomFrame_ConfirmationDialog extends JDialog implements ActionListener {
  PortfolioControl portfolioControl;
  String message;
  JPanel jPanel1 = new JPanel();
  JLabel jLabelImage = new JLabel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanelButton = new JPanel();
  JButton jButtonOK = new JButton();
  JButton jButtonCancel = new JButton();
  JTextArea jTextAreaMessage = new JTextArea();

  public ZomFrame_ConfirmationDialog(Frame parent, PortfolioControl portfolioControl, String message) {
    super(parent);
    this.portfolioControl = portfolioControl;
    this.message = message;
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    this.jTextAreaMessage.setText(message);
    pack();
  }
  private void jbInit() throws Exception {
    //imageLabel.setIcon(new ImageIcon(ZomFrame_AboutBox.class.getResource("zomicon.gif")));
    jPanel1.setLayout(borderLayout1);
    jLabelImage.setIcon(new ImageIcon(ZomResource.getResource("/com/byotrader/zom/resources", "Question.gif")));
    jLabelImage.setText("  ");
    jButtonOK.setActionCommand("jButtonOK");
    jButtonOK.setText("OK");
    jButtonCancel.setActionCommand("jButtonCancel");
    jButtonCancel.setText("Cancel");
    jButtonOK.addActionListener(this);
    jButtonCancel.addActionListener(this);
    jTextAreaMessage.setWrapStyleWord(true);
    jTextAreaMessage.setLineWrap(true);
    jTextAreaMessage.setOpaque(false);
    jTextAreaMessage.setText("This text is a place holder for the question that you wish to pose " +
    "to the user!  If you wish to ask a question then do it here!  Inform " +
    "the user of the actions to be taken for each of the available buttons.");
    jTextAreaMessage.setEditable(false);
    jTextAreaMessage.setFont(new java.awt.Font("Dialog", 0, 12));
    this.setResizable(false);
    this.setModal(true);
    this.setTitle("Confirmation Please!");
    jPanel1.setMinimumSize(new Dimension(400, 150));
    jPanel1.setPreferredSize(new Dimension(400, 150));
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jLabelImage, BorderLayout.WEST);
    jPanel1.add(jPanelButton, BorderLayout.SOUTH);
    jPanelButton.add(jButtonOK, null);
    jPanelButton.add(jButtonCancel, null);
    jPanel1.add(jTextAreaMessage, BorderLayout.CENTER);
    this.getRootPane().setDefaultButton(jButtonCancel);
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
      if(this.portfolioControl != null) {
        this.portfolioControl.confirmedDeleteStock();
      }

      cancel();
    }
  }
}