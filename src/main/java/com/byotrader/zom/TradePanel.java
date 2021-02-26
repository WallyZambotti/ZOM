package com.byotrader.zom;



import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

//import java.beans.*;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public class TradePanel extends JPanel {
  Border border1;
  TitledBorder titledBorder1;
  JButton jButtonDelAll = new JButton();
  JButton jButtonAdd = new JButton();
  JPanel jPanelTradeButtons = new JPanel();
  JButton jButtonDel = new JButton();
  JScrollPane jScrollPaneTable = new JScrollPane();
  JTable jTableTrade = new JTable();
  Border border2;
  TitledBorder titledBorder2;
  JScrollPane jScrollPaneForm = new JScrollPane();
  JTextField jTextFieldStartingStock = new JTextField();
  GridBagLayout gridBagLayoutForm = new GridBagLayout();
  JTextField jTextFieldInterestRate = new JTextField();
  JPanel jPanelForm = new JPanel();
  JTextField jTextFieldStartingUnits = new JTextField();
  JTextField jTextFieldBrokerageFee = new JTextField();
  JLabel jLabel9 = new JLabel();
  JLabel jLabelBrokerageFee = new JLabel();
  JLabel jLabelInterestRate = new JLabel();
  JLabel jLabelStartingDate = new JLabel();
  JLabel jLabelCashReserve = new JLabel();
  JLabel jLabelStartingStock = new JLabel();
  JTextField jTextFieldStartingDate = new JTextField();
  JLabel jLabelStartingUnits = new JLabel();
  JTextField jTextFieldStockName = new JTextField();
  JTextField jTextFieldStockCode = new JTextField();
  GridBagLayout gridBagLayoutMain = new GridBagLayout();
  Border border3;
  TradeDefTableModel tradeTable;
  TradeControl tradeControl;

  /**Construct The Trade Column Model */
  DefaultTableColumnModel tradeColumnModel = new DefaultTableColumnModel();
  JCheckBox jCheckBoxPercent = new JCheckBox();
  JButton jButtonRecalc = new JButton();
  JLabel jLabelComparison = new JLabel();
  JTextField jTextFieldCompZom = new JTextField();
  JTextField jTextFieldCompSingle = new JTextField();
  JTextField jTextFieldCashReserve = new JTextField();

  public TradePanel() {
    return;
  }

  public TradePanel(TradeDefTableModel tradeTable, TradeControl tradeControl) {
    this.tradeTable = tradeTable;
    this.tradeControl = tradeControl;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    border1 = new EtchedBorder(EtchedBorder.RAISED,new Color(240, 217, 205),new Color(117, 106, 100));
    titledBorder1 = new TitledBorder(border1,"Trade");
    border2 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(140, 157, 140));
    titledBorder2 = new TitledBorder(border2,"Trade");
    border3 = BorderFactory.createEmptyBorder();
    this.setLayout(gridBagLayoutMain);
    this.setBackground(new Color(217, 164, 164));
    this.setEnabled(true);
    this.setAlignmentX((float) 0.0);
    this.setAlignmentY((float) 0.0);
    this.setBorder(titledBorder2);
    this.setMinimumSize(new Dimension(500, 290));
    this.setPreferredSize(new Dimension(639, 460));
    this.addComponentListener(new java.awt.event.ComponentAdapter() {
      public void componentResized(ComponentEvent e) {
        this_componentResized(e);
      }
    });
    jButtonDelAll.setNextFocusableComponent(jTextFieldStartingUnits);
    jButtonDelAll.setPreferredSize(new Dimension(71, 27));
    jButtonDelAll.setToolTipText("Deletes all values.");
    jButtonDelAll.setMargin(new Insets(2, 1, 2, 1));
    jButtonDelAll.setText("Delete All");
    jButtonDelAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonDelAll_actionPerformed(e);
      }
    });
    jButtonAdd.setPreferredSize(new Dimension(71, 27));
    jButtonAdd.setToolTipText("Envoke the add new value guide.");
    jButtonAdd.setText("Add...");
    jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonAdd_actionPerformed(e);
      }
    });
    jPanelTradeButtons.setBackground(new Color(212, 200, 225));
    jPanelTradeButtons.setMinimumSize(new Dimension(217, 32));
    jPanelTradeButtons.setOpaque(false);
    jPanelTradeButtons.setPreferredSize(new Dimension(233, 32));
    jButtonDel.setNextFocusableComponent(jButtonDelAll);
    jButtonDel.setToolTipText("Delete the selected value");
    jButtonDel.setText("Delete");
    jButtonDel.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonDel_actionPerformed(e);
      }
    });
    jScrollPaneTable.setToolTipText("");
    jScrollPaneTable.setPreferredSize(new Dimension(150, 250));
    jScrollPaneTable.getViewport().setBackground(UIManager.getColor("window"));
    jScrollPaneTable.setBorder(BorderFactory.createLoweredBevelBorder());
    jScrollPaneTable.setMinimumSize(new Dimension(150, 210));
    jTableTrade.setAutoCreateColumnsFromModel(false);
    jTableTrade.setToolTipText("");
    jScrollPaneForm.getViewport().setBackground(new Color(217, 164, 164));
    jScrollPaneForm.setAlignmentX((float) 0.0);
    jScrollPaneForm.setAlignmentY((float) 0.0);
    jScrollPaneForm.setBorder(border3);
    jScrollPaneForm.setMaximumSize(new Dimension(32767, 162));
    jScrollPaneForm.setMinimumSize(new Dimension(310, 130));
    jScrollPaneForm.setPreferredSize(new Dimension(635, 110));
    jTextFieldStartingStock.setBackground(Color.white);
    jTextFieldStartingStock.setMinimumSize(new Dimension(110, 21));
    jTextFieldStartingStock.setNextFocusableComponent(jTextFieldCashReserve);
    jTextFieldStartingStock.setPreferredSize(new Dimension(110, 21));
    jTextFieldStartingStock.setToolTipText("The price of the stock on the \"Starting Date\"");
    jTextFieldStartingStock.setHorizontalAlignment(SwingConstants.TRAILING);
    jTextFieldStartingStock.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        jTextFieldStartingStock_focusLost(e);
      }
    });
    jTextFieldInterestRate.setBackground(Color.white);
    jTextFieldInterestRate.setMinimumSize(new Dimension(110, 21));
    jTextFieldInterestRate.setNextFocusableComponent(jTextFieldBrokerageFee);
    jTextFieldInterestRate.setPreferredSize(new Dimension(110, 21));
    jTextFieldInterestRate.setToolTipText("The interest rate applied to the current cash value.");
    jTextFieldInterestRate.setHorizontalAlignment(SwingConstants.TRAILING);
    jTextFieldInterestRate.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        jTextFieldInterestRate_focusLost(e);
      }
    });
    jPanelForm.setBackground(new Color(245, 164, 164));
    jPanelForm.setAlignmentX((float) 0.0);
    jPanelForm.setAlignmentY((float) 0.0);
    jPanelForm.setMinimumSize(new Dimension(500, 40));
    jPanelForm.setOpaque(false);
    jPanelForm.setPreferredSize(new Dimension(635, 110));
    jPanelForm.setToolTipText("");
    jPanelForm.setLayout(gridBagLayoutForm);
    jTextFieldStartingUnits.setBackground(Color.white);
    jTextFieldStartingUnits.setMinimumSize(new Dimension(110, 21));
    jTextFieldStartingUnits.setNextFocusableComponent(jTextFieldStartingStock);
    jTextFieldStartingUnits.setPreferredSize(new Dimension(110, 21));
    jTextFieldStartingUnits.setToolTipText("The starting investment amount for this stock.  To be distributed " +
    "between stock & cash.");
    jTextFieldStartingUnits.setHorizontalAlignment(SwingConstants.TRAILING);
    jTextFieldStartingUnits.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        jTextFieldStartingUnits_focusLost(e);
      }
    });
    jTextFieldBrokerageFee.setBackground(Color.white);
    jTextFieldBrokerageFee.setMinimumSize(new Dimension(110, 21));
    jTextFieldBrokerageFee.setPreferredSize(new Dimension(110, 21));
    jTextFieldBrokerageFee.setToolTipText("The fee incurred for a trade");
    jTextFieldBrokerageFee.setHorizontalAlignment(SwingConstants.TRAILING);
    jTextFieldBrokerageFee.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        jTextFieldBrokerageFee_focusLost(e);
      }
    });
    jLabel9.setText("Stock Name/Code");
    jLabelBrokerageFee.setText("Brokerage Fee");
    jLabelInterestRate.setText("Interest Rate");
    jLabelStartingDate.setText("Starting Date");
    jLabelCashReserve.setText("Starting Cash Reserve");
    jLabelStartingStock.setToolTipText("");
    jLabelStartingStock.setText("Starting Stock Price");
    jTextFieldStartingDate.setBackground(Color.white);
    jTextFieldStartingDate.setMinimumSize(new Dimension(110, 21));
    jTextFieldStartingDate.setPreferredSize(new Dimension(110, 21));
    jTextFieldStartingDate.setToolTipText("The starting date of the first trade.");
    jTextFieldStartingDate.setHorizontalAlignment(SwingConstants.TRAILING);
    jTextFieldStartingDate.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        jTextFieldStartingDate_focusLost(e);
      }
    });
    jLabelStartingUnits.setText("Starting Portfolio Units");
    jTextFieldStockName.setMinimumSize(new Dimension(255, 21));
    jTextFieldStockName.setOpaque(false);
    jTextFieldStockName.setPreferredSize(new Dimension(255, 21));
    jTextFieldStockName.setEditable(false);
    jTextFieldStockCode.setMinimumSize(new Dimension(110, 21));
    jTextFieldStockCode.setOpaque(false);
    jTextFieldStockCode.setPreferredSize(new Dimension(110, 21));
    jTextFieldStockCode.setEditable(false);
    jButtonRecalc.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonRecalc_actionPerformed(e);
      }
    });
    jButtonRecalc.setText("Calculate");
    jButtonRecalc.setToolTipText("Forces the entire portfolio to recalculate.");
    jButtonRecalc.setMargin(new Insets(2, 1, 2, 1));
    jButtonRecalc.setNextFocusableComponent(jButtonAdd);
    jButtonRecalc.setPreferredSize(new Dimension(71, 27));
    jLabelComparison.setText("Zom/Single Comparison");
    jTextFieldCompZom.setMinimumSize(new Dimension(110, 21));
    jTextFieldCompZom.setOpaque(false);
    jTextFieldCompZom.setPreferredSize(new Dimension(110, 21));
    jTextFieldCompZom.setHorizontalAlignment(SwingConstants.TRAILING);
    jTextFieldCompSingle.setMinimumSize(new Dimension(110, 21));
    jTextFieldCompSingle.setOpaque(false);
    jTextFieldCompSingle.setPreferredSize(new Dimension(110, 21));
    jTextFieldCompSingle.setHorizontalAlignment(SwingConstants.TRAILING);
    jTextFieldCashReserve.setBackground(Color.white);
    jTextFieldCashReserve.setMinimumSize(new Dimension(110, 21));
    jTextFieldCashReserve.setPreferredSize(new Dimension(110, 21));
    jTextFieldCashReserve.setToolTipText("The amount of cash reserve to use for subsequent trading.");
    jTextFieldCashReserve.setHorizontalAlignment(SwingConstants.TRAILING);
    jTextFieldCashReserve.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        jTextFieldCashReserve_focusLost(e);
      }
    });
    this.add(jPanelTradeButtons, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 432, 0));
    jPanelTradeButtons.add(jButtonRecalc, null);
    jPanelTradeButtons.add(jButtonAdd, null);
    jPanelTradeButtons.add(jButtonDel, null);
    jPanelTradeButtons.add(jButtonDelAll, null);
    this.add(jScrollPaneTable, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 0, 0, 0), 0, 0));
    this.add(jScrollPaneForm, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.NORTHEAST, GridBagConstraints.BOTH, new Insets(0, 0, 2, 0), 0, 0));
    jScrollPaneForm.getViewport().add(jPanelForm, null);
    jPanelForm.add(jTextFieldStartingDate, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 8, 0, 0), 0, 0));
    jPanelForm.add(jTextFieldStartingStock, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 8, 0, 0), 0, 0));
    jPanelForm.add(jTextFieldStartingUnits, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 8, 0, 0), 0, 0));
    jPanelForm.add(jLabelStartingDate, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 2, 0, 0), 0, 0));
    jPanelForm.add(jLabelCashReserve, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 2, 0, 0), 0, 0));
    jPanelForm.add(jLabelStartingStock, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 2, 0, 0), 0, 0));
    jPanelForm.add(jTextFieldStockName, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 6, 0));
    jPanelForm.add(jLabelStartingUnits, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 2, 0, 0), 0, 0));
    jPanelForm.add(jLabel9, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 2, 0, 0), 0, 0));
    jPanelForm.add(jTextFieldStockCode, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanelForm.add(jLabelInterestRate, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 24, 0, 0), 0, 0));
    jPanelForm.add(jTextFieldInterestRate, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
    jPanelForm.add(jTextFieldBrokerageFee, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
    jPanelForm.add(jLabelBrokerageFee, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 24, 0, 0), 0, 0));
    jPanelForm.add(jLabelComparison, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 24, 0, 0), 0, 0));
    jPanelForm.add(jTextFieldCompZom, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanelForm.add(jTextFieldCompSingle, new GridBagConstraints(4, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 2), 0, 0));
    jPanelForm.add(jTextFieldCashReserve, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 8, 0, 0), 0, 0));
    // jScrollPane1.getViewport().add(jTableTrade, null);
    jScrollPaneTable.getViewport().add(jTableTrade, null);

    // Set up all the Trade Table column Preferences
    for(int col = 0; col < this.tradeTable.getColumnCount(); col++) {
      tradeColumnModel.addColumn(setColumnPreferences(col));
    }

    jTableTrade.setColumnModel(tradeColumnModel);
    jTableTrade.setModel(tradeTable);
  }

  TableColumn setColumnPreferences(int columnIndex) {
    // Column 0 Stock
    TableColumn anyColumn = new TableColumn(columnIndex);
    anyColumn.setHeaderValue(tradeTable.getColumnName(columnIndex));

    // Change the background color to indicate an editable column
    //DefaultTableCellRenderer editableCellRenderer = new DefaultTableCellRenderer();
    DefaultTableCellRenderer editableCellRenderer = new ZomNumberRenderer();
    editableCellRenderer.setBackground(
        tradeTable.getColumnColor(columnIndex, jTableTrade.getBackground()));
    editableCellRenderer.setToolTipText(tradeTable.getColumnToolTip(columnIndex));
    editableCellRenderer.setHorizontalAlignment(JLabel.RIGHT);
    anyColumn.setCellRenderer(editableCellRenderer);

    return anyColumn;
  }

  void jButtonAdd_actionPerformed(ActionEvent e) {
    // System.out.println("Trade Add Button");
    this.tradeControl.addSample();
  }

  void jButtonDel_actionPerformed(ActionEvent e) {
    // System.out.println("Trade Del Button");
    this.tradeControl.deleteSample();
  }

  void jButtonDelAll_actionPerformed(ActionEvent e) {
    // System.out.println("Trade Del All Button");
    this.tradeControl.deleteAllSamples();
  }

  void jButtonImport_actionPerformed(ActionEvent e) {
    // System.out.println("Trade Import Button");
  }

  void jButtonRecalc_actionPerformed(ActionEvent e) {
    // System.out.println("Trade Calculate Button");
    this.tradeControl.zomCalculate();
  }

  void jTextFieldStartingUnits_focusLost(FocusEvent e) {
    // System.out.println("Trade Starting Units Focus Lost");
    if (this.tradeControl.isStartingPortfolioValid(this.getStartingUnitsText())) {
      this.jLabelStartingUnits.setForeground(Color.black);
    }
    else {
      this.jLabelStartingUnits.setForeground(Color.red);
    }
  }

  void jTextFieldStartingStock_focusLost(FocusEvent e) {
    // System.out.println("Trade Starting Stock Focus Lost");
    if (this.tradeControl.isStartingStockValid(this.getStartingStockText())) {
      this.jLabelStartingStock.setForeground(Color.black);
    }
    else {
      this.jLabelStartingStock.setForeground(Color.red);
    }
  }

  void jTextFieldStartingDate_focusLost(FocusEvent e) {
    // System.out.println("Trade Starting Date Focus Lost");
    if (this.tradeControl.isStartingDateValid(this.getStartingDateText())) {
      this.jLabelStartingDate.setForeground(Color.black);
    }
    else {
      this.jLabelStartingDate.setForeground(Color.red);
    }
  }

  void jTextFieldInterestRate_focusLost(FocusEvent e) {
    // System.out.println("Trade Interest Rate Focus Lost");
    if (this.tradeControl.isInterestRateValid(this.getInterestRateText())) {
      this.jLabelInterestRate.setForeground(Color.black);
    }
    else {
      this.jLabelInterestRate.setForeground(Color.red);
    }
  }


  void jTextFieldBrokerageFee_focusLost(FocusEvent e) {
    // System.out.println("Trade Brokerage Fee Focus Lost");
    if (this.tradeControl.isBrokerageFeeValid(this.getBrokerageFeeText())) {
      this.jLabelBrokerageFee.setForeground(Color.black);
    }
    else {
      this.jLabelBrokerageFee.setForeground(Color.red);
    }
  }

  void jTextFieldCashReserve_focusLost(FocusEvent e) {
    // System.out.println("Trade Cash Reserve Focus Lost");
    if (this.tradeControl.isCashReserveValid(this.getCashReserveText())) {
      this.jLabelCashReserve.setForeground(Color.black);
    }
    else {
      this.jLabelCashReserve.setForeground(Color.red);
    }
  }

  // Convenience Methods to set Form Fields

  public void setStockNameText(String txtVal) {
    this.jTextFieldStockName.setText(txtVal == null ? "" : txtVal);
  }

  public void setStockCodeText(String txtVal) {
    this.jTextFieldStockCode.setText(txtVal == null ? "" : txtVal);
  }

  public void setStartingUnitsText(String txtVal) {
    this.jTextFieldStartingUnits.setText(txtVal == null ? "" : txtVal);
  }

  public void setStartingStockText(String txtVal) {
    this.jTextFieldStartingStock.setText(txtVal == null ? "" : txtVal);
  }

  public void setStartingDateText(String txtVal) {
    this.jTextFieldStartingDate.setText(txtVal == null ? "" : txtVal);
  }

  public void setInterestRateText(String txtVal) {
    this.jTextFieldInterestRate.setText(txtVal == null ? "" : txtVal);
  }

  public void setBrokerageFeeText(String txtVal) {
    this.jTextFieldBrokerageFee.setText(txtVal == null ? "" : txtVal);
  }

  public void setCashReserveText(String txtVal) {
    this.jTextFieldCashReserve.setText(txtVal == null ? "" : txtVal);
  }

  public void setZomComparisonText(String txtVal) {
    this.jTextFieldCompZom.setText(txtVal == null ? "" : txtVal);
  }

  public void setSingleComparisonText(String txtVal) {
    this.jTextFieldCompSingle.setText(txtVal == null ? "" : txtVal);
  }

  // Convenience methods to get field values

  public String getStockNameText() {
    return this.jTextFieldStockName.getText().trim();
  }

  public String getStockCodeText() {
    return this.jTextFieldStockCode.getText().trim();
  }

  public String getStartingUnitsText() {
    return this.jTextFieldStartingUnits.getText().trim();
  }

  public String getStartingStockText() {
    return this.jTextFieldStartingStock.getText().trim();
  }

  public String getStartingDateText() {
    return this.jTextFieldStartingDate.getText().trim();
  }

  public String getInterestRateText() {
    return this.jTextFieldInterestRate.getText().trim();
  }

  public String getBrokerageFeeText() {
    return this.jTextFieldBrokerageFee.getText().trim();
  }

  public String getCashReserveText() {
    return this.jTextFieldCashReserve.getText().trim();
  }

  /*
    Keep track of resize events and set a policy such that most of the space is given
    to the table area (ie stolen from the form area).  When the form area no longer has
    a vertical scroll bar then stop it from growing any further giving all avaliable
    space to the table area.  If the table height ever becomes less than the form height
    then revert back to the original policy.
  */

  void this_componentResized(ComponentEvent e) {
    int height = e.getComponent().getSize().height;
    int width = e.getComponent().getSize().width;
    int formHeight = this.jScrollPaneForm.getSize().height;
    int tableHeight = this.jScrollPaneTable.getSize().height;

    //System.out.println("Frame Size (H,W):" + height + ", " + width);
    //System.out.println("Pane  Size:" + this.jScrollPaneForm.getSize().width + "," + this.jScrollPaneForm.getSize().height);
    //System.out.println("Panel Size:" + this.jPanelForm.getSize().width + "," + this.jPanelForm.getSize().height);

    GridBagConstraints formConstraints = this.gridBagLayoutMain.getConstraints(this.jScrollPaneForm);
    boolean haveVScrollBar = this.jScrollPaneForm.getVerticalScrollBar().isShowing();

    this.setVisible(false);

    if (haveVScrollBar) {
      //System.out.println("Have V scroll bar");
      formConstraints.weighty = 1.0;
    }
    else {
      //System.out.println("No V scroll bar");
      if (formHeight > tableHeight) {
        //System.out.println("Form height greater");
        formConstraints.weighty = 1.0;
        this.jScrollPaneForm.setVerticalScrollBarPolicy(this.jScrollPaneForm.VERTICAL_SCROLLBAR_AS_NEEDED);
      }
      else {
        //System.out.println("Table height greater");
        formConstraints.weighty = 0.0;
        this.jScrollPaneForm.setVerticalScrollBarPolicy(this.jScrollPaneForm.VERTICAL_SCROLLBAR_NEVER);
      }
    }

    this.gridBagLayoutMain.setConstraints(this.jScrollPaneForm, formConstraints);
    this.setVisible(true);
  }

  /**Help | Stock Dialog Requester*/
  public void displayNewSampleDialog(
    String tradeDate, String tradePrice, String cash, String interest, String brokerage, String traded) {
    ZomFrame frame = this.tradeControl.getFrame();
    ZomFrame_NewTradeDialog dlg = new ZomFrame_NewTradeDialog(frame, this.tradeControl);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = frame.getSize();
    Point loc = frame.getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.jTextFieldTradeDate.setText(tradeDate);
    dlg.jTextFieldTradePrice.setText(tradePrice);
    dlg.jTextFieldCash.setText(cash);
    dlg.jTextFieldInterest.setText(interest);
    dlg.jTextFieldBrokerage.setText(brokerage);
    dlg.jTextFieldTraded.setText(traded);
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
      this.tradeControl.confirmedDeleteSample();
    }
  }

  /**Confirmation Dialog Requester*/
  public void displayDelAllConfDialog(String message) {
    String deleteString = "Delete";
    String abortString = "Abort";

    ImageIcon image;
    image = new ImageIcon(ZomResource.getResource("/com/byotrader/zom/resources", "Grim.gif"));

    if (JOptionPane.showOptionDialog(
      this, message, "Confirm Delete", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
      image,
      new String [] { deleteString, abortString }, abortString) == 0) {
      this.tradeControl.confirmedDeleteAllSamples();
    }
  }

  /**Confirmation Dialog Requester*/
  public int displayCopyPasteDialog(boolean newEntry, String message) {
    String formString = "Form";
    String entryString = "New Entry";
    String cancelString = "Cancel";

    ImageIcon image;
    image = new ImageIcon(ZomResource.getResource("/com/byotrader/zom/resources", "Letter.gif"));

    if (newEntry) {
      return JOptionPane.showOptionDialog(
        this, message, "Confirm Paste", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
        image,
        new String [] { formString, entryString, cancelString }, cancelString);
    }
    else {
      return JOptionPane.showOptionDialog(
        this, message, "Confirm Paste", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
        image,
        new String [] { formString, cancelString }, cancelString);
    }
  }
}