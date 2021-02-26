package com.byotrader.zom;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.Point;
import java.awt.Insets;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public class PortfolioPanel extends JPanel {
  JTable jTablePortfolio = new JTable();
  JScrollPane jScrollPane1 = new JScrollPane();
  JButton jButtonDup = new JButton();
  JButton jButtonAdd = new JButton();
  JPanel jPanelPortfolioButtons = new JPanel();
  JButton jButtonDel = new JButton();
  BorderLayout borderLayout1 = new BorderLayout();
  Border border1;
  TitledBorder titledBorder1;
  PortfolioDefTableModel portfolioTable;
  PortfolioControl portfolioControl;

  /**Construct The Portfolio Column Model */
  DefaultTableColumnModel portfolioColumnModel = new DefaultTableColumnModel();

  public PortfolioPanel() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public PortfolioPanel(PortfolioDefTableModel portfolioTable, PortfolioControl portfolioControl) {
    this.portfolioTable = portfolioTable;
    this.portfolioControl = portfolioControl;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    border1 = BorderFactory.createEtchedBorder(new Color(240, 217, 205),new Color(117, 106, 100));
    titledBorder1 = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,new Color(240, 217, 205),new Color(117, 106, 100)),"Portfolio");
    jScrollPane1.setToolTipText("");
    jScrollPane1.setPreferredSize(new Dimension(128, 128));
    jScrollPane1.getViewport().setBackground(SystemColor.window);
    jScrollPane1.setMaximumSize(new Dimension(128, 128));
    jTablePortfolio.setAutoCreateColumnsFromModel(false);
    jTablePortfolio.setToolTipText("");
    jButtonDup.setPreferredSize(new Dimension(71, 27));
    jButtonDup.setToolTipText("Make a complete duplicate of the selected stock");
    jButtonDup.setMargin(new Insets(2, 1, 2, 1));
    jButtonDup.setText("Duplicate");
    jButtonDup.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonDup_actionPerformed(e);
      }
    });
    jButtonAdd.setPreferredSize(new Dimension(71, 27));
    jButtonAdd.setToolTipText("Display the add new stock form");
    jButtonAdd.setText("Add...");
    jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonAdd_actionPerformed(e);
      }
    });
    jPanelPortfolioButtons.setBackground(new Color(200, 200, 225));
    jPanelPortfolioButtons.setMinimumSize(new Dimension(217, 32));
    jPanelPortfolioButtons.setOpaque(false);
    jPanelPortfolioButtons.setPreferredSize(new Dimension(233, 32));
    jButtonDel.setToolTipText("Delete the selected stock");
    jButtonDel.setText("Delete");
    jButtonDel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonDel_actionPerformed(e);
      }
    });
    this.setLayout(borderLayout1);
    this.setBackground(new Color(188, 201, 181));
    this.setEnabled(true);
    this.setBorder(titledBorder1);
    this.add(jPanelPortfolioButtons, BorderLayout.SOUTH);
    jPanelPortfolioButtons.add(jButtonAdd, null);
    jPanelPortfolioButtons.add(jButtonDel, null);
    jPanelPortfolioButtons.add(jButtonDup, null);
    this.add(jScrollPane1, BorderLayout.CENTER);
    //jScrollPane1.getViewport().add(jTablePortfolio, null);
    jScrollPane1.getViewport().add(jTablePortfolio, null);

    // Set up all the Portfolio Table column Preferences
    for(int col = 0; col < this.portfolioTable.getColumnCount(); col++) {
      portfolioColumnModel.addColumn(setColumnPreferences(col));
    }

    jTablePortfolio.setColumnModel(portfolioColumnModel);
    jTablePortfolio.setModel(portfolioTable);
  }

  TableColumn setColumnPreferences(int columnIndex) {
    // Column 0 Stock
    TableColumn anyColumn = new TableColumn(columnIndex);
    anyColumn.setHeaderValue(portfolioTable.getColumnName(columnIndex));

    // Change the background color to indicate an editable column
    DefaultTableCellRenderer editableCellRenderer = new DefaultTableCellRenderer();
    editableCellRenderer.setBackground(
        portfolioTable.getColumnColor(columnIndex, jTablePortfolio.getBackground()));
    editableCellRenderer.setToolTipText(portfolioTable.getColumnToolTip(columnIndex));
    if (columnIndex > 1) {
      editableCellRenderer.setHorizontalAlignment(JLabel.RIGHT);
    }
    anyColumn.setCellRenderer(editableCellRenderer);

    return anyColumn;
  }

  /**Help | Stock Dialog Requester*/
  public void displayNewStockDialog(String newStockName, String newStockCode) {
    ZomFrame frame = this.portfolioControl.getFrame();
    ZomFrame_NewStockDialog dlg = new ZomFrame_NewStockDialog(frame, this.portfolioControl);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = frame.getSize();
    Point loc = frame.getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.jTextFieldStockName.setText(newStockName);
    dlg.jTextFieldStockCode.setText(newStockCode);
    if (newStockCode.compareTo("") != 0 ) {
      dlg.jTextFieldStockCode.disable();
    }
    else {
      dlg.jTextFieldStockCode.enable();
    }
    dlg.show();
  }

  /**Registration Dialog Requester*/
  public void displayRegistrationDialog(String key, String code) {
    ZomFrame frame = this.portfolioControl.getFrame();
    ZomFrame_RegistrationDialog dlg = new ZomFrame_RegistrationDialog(frame, this.portfolioControl);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = frame.getSize();
    Point loc = frame.getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.jTextFieldKey.setText(key);
    dlg.jTextFieldCode.setText(code);
    dlg.show();
  }

  /**Confirmation Dialog Requester*/
  public void displayConfirmationDialog(String message) {
    String deleteString = "Delete";
    String abortString = "Abort";

    ImageIcon image;
    image = new ImageIcon(ZomResource.getResource("/com/byotrader/zom/resources", "Grim.gif"));

    if (JOptionPane.showOptionDialog(
      this, message, "Confirm Delete", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
      image,
      new String [] { deleteString, abortString }, abortString) == 0) {
      this.portfolioControl.confirmedDeleteStock();
    }
  }

  public void displayRegistrationConfirmation() {
    String okString = "OK";
    String message = "Registration successful.  Please Restart Zom to activate full functionality!";

    ImageIcon image;
    image = new ImageIcon(ZomResource.getResource("/com/byotrader/zom/resources", "LETTER.GIF"));

    JOptionPane.showOptionDialog(
      this, message, "Registration Confirmed", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
      image,
      new String [] { okString }, okString);
  }

  public void displayRegistrationConditions() {
    String okString = "OK";

    String message = "This copy of ZOM will become a full version when a valid \n" +
                     "Key/Code combination is entered.  Until then you are limited to \n" +
                     "loading 3 stocks with 25 entries per stock. \n\n" +
                     "Warning it is possible to experiment with more data but when \n" +
                     "ZOM is restarted the above restrictions will be enforced again! \n\n" +
                     "This restriction is removed when a registration Key/Code is \n" +
                     "purchased!";

    JOptionPane.showOptionDialog(
      this, message, "Registration Conditions", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
      null,
      new String [] { okString }, okString);
  }

  void jButtonAdd_actionPerformed(ActionEvent e) {
    // System.out.println("Portfolio Add Button");
    portfolioControl.addStock();
  }

  void jButtonDel_actionPerformed(ActionEvent e) {
    // System.out.println("Portfolio Del Button");
    portfolioControl.deleteStock();
  }

  void jButtonDup_actionPerformed(ActionEvent e) {
    // System.out.println("Portfolio Dup Button");
    portfolioControl.duplicateStock();
  }
}