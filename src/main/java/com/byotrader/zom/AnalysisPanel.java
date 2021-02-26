package com.byotrader.zom;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ComponentEvent;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

//import java.beans.*;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public class AnalysisPanel extends JPanel {
  Border border1;
  TitledBorder titledBorder1;
  JButton jButtonDelAll = new JButton();
  JButton jButtonAdd = new JButton();
  JPanel jPanelAnalysisButtons = new JPanel();
  JButton jButtonDel = new JButton();
  JScrollPane jScrollPaneTable = new JScrollPane();
  JTable jTableAnalysis = new JTable();
  Border border2;
  TitledBorder titledBorder2;
  ButtonGroup buttonGroupMinTrade = new ButtonGroup();
  JScrollPane jScrollPaneForm = new JScrollPane();
  JComboBox jComboBoxCashReserve = new JComboBox();
  JTextField jTextFieldStartingStock = new JTextField();
  GridBagLayout gridBagLayoutForm = new GridBagLayout();
  JTextField jTextFieldInterestRate = new JTextField();
  JPanel jPanelForm = new JPanel();
  JComboBox jComboBoxMarketIndicator = new JComboBox();
  JTextField jTextFieldStartingPortfolio = new JTextField();
  JTextField jTextFieldBrokerageFee = new JTextField();
  JTextField jTextFieldMinimumTrade = new JTextField();
  JLabel jLabel9 = new JLabel();
  JLabel jLabelMinimumTrade = new JLabel();
  JLabel jLabelBrokerageFee = new JLabel();
  JLabel jLabelInterestRate = new JLabel();
  JLabel jLabelMarketIndicator = new JLabel();
  JLabel jLabelStartingDate = new JLabel();
  JLabel jLabelCashReserve = new JLabel();
  JLabel jLabelStartingStock = new JLabel();
  JTextField jTextFieldStartingDate = new JTextField();
  JLabel jLabelStartingPortfolio = new JLabel();
  JTextField jTextFieldStockName = new JTextField();
  JTextField jTextFieldStockCode = new JTextField();
  GridBagLayout gridBagLayoutMain = new GridBagLayout();
  Border border3;
  AnalysisDefTableModel analysisTable;
  AnalysisControl analysisControl;

  /**Construct The Analysis Column Model */
  DefaultTableColumnModel analysisColumnModel = new DefaultTableColumnModel();
  JButton jButtonImport = new JButton();
  JCheckBox jCheckBoxPercent = new JCheckBox();
  JButton jButtonRecalc = new JButton();
  JLabel jLabelFrequency = new JLabel();
  JTextField jTextFieldFrequency = new JTextField();
  JLabel jLabelComparison = new JLabel();
  JTextField jTextFieldCompZom = new JTextField();
  JTextField jTextFieldCompSingle = new JTextField();

  public AnalysisPanel() {
    return;
  }

  public AnalysisPanel(AnalysisDefTableModel analysisTable, AnalysisControl analysisControl) {
    this.analysisTable = analysisTable;
    this.analysisControl = analysisControl;
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    border1 = new EtchedBorder(EtchedBorder.RAISED,new Color(240, 217, 205),new Color(117, 106, 100));
    titledBorder1 = new TitledBorder(border1,"Analysis");
    border2 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(140, 157, 140));
    titledBorder2 = new TitledBorder(border2,"Analysis");
    border3 = BorderFactory.createEmptyBorder();
    this.setLayout(gridBagLayoutMain);
    this.setBackground(new Color(200, 188, 212));
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
    jButtonDelAll.setNextFocusableComponent(jTextFieldStartingPortfolio);
    jButtonDelAll.setPreferredSize(new Dimension(71, 27));
    jButtonDelAll.setToolTipText("Deletes all values.");
    jButtonDelAll.setMargin(new Insets(2, 1, 2, 1));
    jButtonDelAll.setText("Delete All");
    jButtonDelAll.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonDelAll_actionPerformed(e);
      }
    });
    jButtonAdd.setNextFocusableComponent(jButtonImport);
    jButtonAdd.setPreferredSize(new Dimension(71, 27));
    jButtonAdd.setToolTipText("Envoke the add new value guide.");
    jButtonAdd.setText("Add...");
    jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonAdd_actionPerformed(e);
      }
    });
    jPanelAnalysisButtons.setBackground(new Color(212, 200, 225));
    jPanelAnalysisButtons.setMinimumSize(new Dimension(217, 32));
    jPanelAnalysisButtons.setOpaque(false);
    jPanelAnalysisButtons.setPreferredSize(new Dimension(233, 32));
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
    jTableAnalysis.setAutoCreateColumnsFromModel(false);
    jTableAnalysis.setToolTipText("");
    jScrollPaneForm.getViewport().setBackground(new Color(200, 188, 212));
    jScrollPaneForm.setAlignmentX((float) 0.0);
    jScrollPaneForm.setAlignmentY((float) 0.0);
    jScrollPaneForm.setBorder(border3);
    jScrollPaneForm.setMaximumSize(new Dimension(32767, 162));
    jScrollPaneForm.setMinimumSize(new Dimension(310, 160));
    jScrollPaneForm.setOpaque(false);
    jScrollPaneForm.setPreferredSize(new Dimension(645, 160));
    jComboBoxCashReserve.setMinimumSize(new Dimension(110, 21));
    jComboBoxCashReserve.setNextFocusableComponent(jTextFieldStartingDate);
    jComboBoxCashReserve.setPreferredSize(new Dimension(110, 21));
    jComboBoxCashReserve.setToolTipText("The percentage to allocate to the initial cash reserve.");
    jComboBoxCashReserve.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        jComboBoxCashReserve_focusLost(e);
      }
    });
    jTextFieldStartingStock.setBackground(Color.white);
    jTextFieldStartingStock.setMinimumSize(new Dimension(110, 21));
    jTextFieldStartingStock.setNextFocusableComponent(jComboBoxCashReserve);
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
    jPanelForm.setBackground(new Color(200, 188, 212));
    jPanelForm.setAlignmentX((float) 0.0);
    jPanelForm.setAlignmentY((float) 0.0);
    jPanelForm.setMinimumSize(new Dimension(500, 40));
    jPanelForm.setOpaque(false);
    jPanelForm.setPreferredSize(new Dimension(635, 150));
    jPanelForm.setToolTipText("");
    jPanelForm.setLayout(gridBagLayoutForm);
    jComboBoxMarketIndicator.setMinimumSize(new Dimension(110, 21));
    jComboBoxMarketIndicator.setNextFocusableComponent(jTextFieldInterestRate);
    jComboBoxMarketIndicator.setPreferredSize(new Dimension(110, 21));
    jComboBoxMarketIndicator.setToolTipText("The expected direction for the stock price.");
    jComboBoxMarketIndicator.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        jComboBoxMarketIndicator_focusLost(e);
      }
    });
    jTextFieldStartingPortfolio.setBackground(Color.white);
    jTextFieldStartingPortfolio.setMinimumSize(new Dimension(110, 21));
    jTextFieldStartingPortfolio.setNextFocusableComponent(jTextFieldStartingStock);
    jTextFieldStartingPortfolio.setPreferredSize(new Dimension(110, 21));
    jTextFieldStartingPortfolio.setToolTipText("The starting investment amount for this stock.  To be distributed " +
    "between stock & cash.");
    jTextFieldStartingPortfolio.setHorizontalAlignment(SwingConstants.TRAILING);
    jTextFieldStartingPortfolio.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        jTextFieldStartingPortfolio_focusLost(e);
      }
    });
    jTextFieldBrokerageFee.setBackground(Color.white);
    jTextFieldBrokerageFee.setMinimumSize(new Dimension(110, 21));
    jTextFieldBrokerageFee.setNextFocusableComponent(jTextFieldMinimumTrade);
    jTextFieldBrokerageFee.setPreferredSize(new Dimension(110, 21));
    jTextFieldBrokerageFee.setToolTipText("The fee incurred for a trade");
    jTextFieldBrokerageFee.setHorizontalAlignment(SwingConstants.TRAILING);
    jTextFieldBrokerageFee.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        jTextFieldBrokerageFee_focusLost(e);
      }
    });
    jTextFieldMinimumTrade.setBackground(Color.white);
    jTextFieldMinimumTrade.setMinimumSize(new Dimension(110, 21));
    jTextFieldMinimumTrade.setNextFocusableComponent(jCheckBoxPercent);
    jTextFieldMinimumTrade.setPreferredSize(new Dimension(110, 21));
    jTextFieldMinimumTrade.setToolTipText("The minimum trade value allowed.");
    jTextFieldMinimumTrade.setHorizontalAlignment(SwingConstants.TRAILING);
    jTextFieldMinimumTrade.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        jTextFieldMinimumTrade_focusLost(e);
      }
    });
    jLabel9.setText("Stock Name/Code");
    jLabelMinimumTrade.setText("Minimum Trade $ or %");
    jLabelBrokerageFee.setText("Brokerage Fee");
    jLabelInterestRate.setText("Interest Rate");
    jLabelMarketIndicator.setText("Market Indicator");
    jLabelStartingDate.setText("Starting Date");
    jLabelCashReserve.setText("Starting Cash Reserve %");
    jLabelStartingStock.setToolTipText("");
    jLabelStartingStock.setText("Starting Stock Price");
    jTextFieldStartingDate.setBackground(Color.white);
    jTextFieldStartingDate.setMinimumSize(new Dimension(110, 21));
    jTextFieldStartingDate.setNextFocusableComponent(jComboBoxMarketIndicator);
    jTextFieldStartingDate.setPreferredSize(new Dimension(110, 21));
    jTextFieldStartingDate.setToolTipText("The starting date of the first trade.");
    jTextFieldStartingDate.setHorizontalAlignment(SwingConstants.TRAILING);
    jTextFieldStartingDate.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        jTextFieldStartingDate_focusLost(e);
      }
    });
    jLabelStartingPortfolio.setText("Starting Portfolio Value");
    jTextFieldStockName.setMinimumSize(new Dimension(255, 21));
    jTextFieldStockName.setOpaque(false);
    jTextFieldStockName.setPreferredSize(new Dimension(255, 21));
    jTextFieldStockName.setEditable(false);
    jTextFieldStockCode.setMinimumSize(new Dimension(110, 21));
    jTextFieldStockCode.setOpaque(false);
    jTextFieldStockCode.setPreferredSize(new Dimension(110, 21));
    jTextFieldStockCode.setEditable(false);
    jButtonImport.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonImport_actionPerformed(e);
      }
    });
    jButtonImport.setText("Import...");
    jButtonImport.setToolTipText("Load trade date & price values from an external file.");
    jButtonImport.setMargin(new Insets(2, 1, 2, 1));
    jButtonImport.setNextFocusableComponent(jButtonDel);
    jButtonImport.setPreferredSize(new Dimension(71, 27));
    jCheckBoxPercent.setOpaque(false);
    jCheckBoxPercent.setNextFocusableComponent(jButtonRecalc);
    jCheckBoxPercent.setText("Percent");
    jCheckBoxPercent.setToolTipText("If checked treat value as a percentage of current stock value.");
    jCheckBoxPercent.setBackground(new Color(212, 200, 225));
    jCheckBoxPercent.addItemListener(new java.awt.event.ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        jCheckBoxPercent_itemStateChanged(e);
      }
    });
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
    jLabelFrequency.setText("Trading Frequency");
    jTextFieldFrequency.setBackground(Color.white);
    jTextFieldFrequency.setMinimumSize(new Dimension(110, 21));
    jTextFieldFrequency.setPreferredSize(new Dimension(110, 21));
    jTextFieldFrequency.setHorizontalAlignment(SwingConstants.TRAILING);
    jTextFieldFrequency.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusLost(FocusEvent e) {
        jTextFieldFrequency_focusLost(e);
      }
    });
    jLabelComparison.setText("Zom/Single Comparison");
    jTextFieldCompZom.setMinimumSize(new Dimension(110, 21));
    jTextFieldCompZom.setOpaque(false);
    jTextFieldCompZom.setPreferredSize(new Dimension(110, 21));
    jTextFieldCompZom.setHorizontalAlignment(SwingConstants.TRAILING);
    jTextFieldCompSingle.setMinimumSize(new Dimension(110, 21));
    jTextFieldCompSingle.setOpaque(false);
    jTextFieldCompSingle.setPreferredSize(new Dimension(110, 21));
    jTextFieldCompSingle.setHorizontalAlignment(SwingConstants.TRAILING);
    this.add(jPanelAnalysisButtons, new GridBagConstraints(0, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 432, 0));
    jPanelAnalysisButtons.add(jButtonRecalc, null);
    jPanelAnalysisButtons.add(jButtonAdd, null);
    jPanelAnalysisButtons.add(jButtonImport, null);
    jPanelAnalysisButtons.add(jButtonDel, null);
    jPanelAnalysisButtons.add(jButtonDelAll, null);
    this.add(jScrollPaneTable, new GridBagConstraints(0, 1, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    this.add(jScrollPaneForm, new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 2, 0), 0, 0));
    jScrollPaneForm.getViewport().add(jPanelForm, null);
    jPanelForm.add(jTextFieldMinimumTrade, new GridBagConstraints(3, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
    jPanelForm.add(jTextFieldBrokerageFee, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
    jPanelForm.add(jComboBoxMarketIndicator, new GridBagConstraints(3, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
    jPanelForm.add(jLabelMinimumTrade, new GridBagConstraints(2, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 24, 0, 0), 0, 0));
    jPanelForm.add(jLabelBrokerageFee, new GridBagConstraints(2, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 24, 0, 0), 0, 0));
    jPanelForm.add(jLabelInterestRate, new GridBagConstraints(2, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 24, 0, 0), 0, 0));
    jPanelForm.add(jLabelMarketIndicator, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 24, 0, 0), 0, 0));
    jPanelForm.add(jTextFieldStartingDate, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 8, 0, 0), 0, 0));
    jPanelForm.add(jComboBoxCashReserve, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 8, 0, 0), 0, 0));
    jPanelForm.add(jTextFieldStartingStock, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 8, 0, 0), 0, 0));
    jPanelForm.add(jTextFieldStartingPortfolio, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(2, 8, 0, 0), 0, 0));
    jPanelForm.add(jLabelStartingDate, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanelForm.add(jLabelCashReserve, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanelForm.add(jLabelStartingStock, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanelForm.add(jTextFieldStockName, new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 6, 0));
    jPanelForm.add(jLabelStartingPortfolio, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanelForm.add(jLabel9, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanelForm.add(jTextFieldStockCode, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanelForm.add(jLabelFrequency, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanelForm.add(jTextFieldFrequency, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(2, 8, 0, 0), 0, 0));
    jPanelForm.add(jTextFieldInterestRate, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 8, 0, 0), 0, 0));
    jPanelForm.add(jCheckBoxPercent, new GridBagConstraints(4, 4, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 4, 0, 0), 0, 0));
    jPanelForm.add(jLabelComparison, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 24, 0, 0), 0, 0));
    jPanelForm.add(jTextFieldCompZom, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanelForm.add(jTextFieldCompSingle, new GridBagConstraints(4, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    // jScrollPane1.getViewport().add(jTableAnalysis, null);
    jScrollPaneTable.getViewport().add(jTableAnalysis, null);

    // Set up all the Analysis Table column Preferences
    for(int col = 0; col < this.analysisTable.getColumnCount(); col++) {
      analysisColumnModel.addColumn(setColumnPreferences(col));
    }

    jTableAnalysis.setColumnModel(analysisColumnModel);
    jTableAnalysis.setModel(analysisTable);
  }

  public void populateCashReserve(Object[] items) {
    int cnt = items.length;

    for(int idx = 0 ; idx < cnt ; idx++) {
      this.jComboBoxCashReserve.addItem(items[idx]);
    }
  }

  public void populateMarketIndicator(Object[] items) {
    int cnt = items.length;

    for(int idx = 0 ; idx < cnt ; idx++) {
      this.jComboBoxMarketIndicator.addItem(items[idx]);
    }
  }

  TableColumn setColumnPreferences(int columnIndex) {
    // Column 0 Stock
    TableColumn anyColumn = new TableColumn(columnIndex);
    anyColumn.setHeaderValue(analysisTable.getColumnName(columnIndex));

    // Change the background color to indicate an editable column
    //DefaultTableCellRenderer editableCellRenderer = new DefaultTableCellRenderer();
    DefaultTableCellRenderer editableCellRenderer = new ZomNumberRenderer();
    editableCellRenderer.setBackground(
        analysisTable.getColumnColor(columnIndex, jTableAnalysis.getBackground()));
    editableCellRenderer.setToolTipText(analysisTable.getColumnToolTip(columnIndex));
    editableCellRenderer.setHorizontalAlignment(JLabel.RIGHT);
    anyColumn.setCellRenderer(editableCellRenderer);

    return anyColumn;
  }

  void jButtonAdd_actionPerformed(ActionEvent e) {
    // System.out.println("Analysis Add Button");
    this.analysisControl.addSample();
  }

  void jButtonDel_actionPerformed(ActionEvent e) {
    // System.out.println("Analysis Del Button");
    this.analysisControl.deleteSample();
  }

  void jButtonDelAll_actionPerformed(ActionEvent e) {
    // System.out.println("Analysis Del All Button");
    this.analysisControl.deleteAllSamples();
  }

  void jButtonImport_actionPerformed(ActionEvent e) {
    // System.out.println("Analysis Import Button");
    this.analysisControl.importCSVFile();
  }

  void jButtonRecalc_actionPerformed(ActionEvent e) {
    // System.out.println("Analysis Calculate Button");
    this.analysisControl.zomCalculate();
  }

  void jTextFieldStartingPortfolio_focusLost(FocusEvent e) {
    // System.out.println("Analysis Starting Portfolio Focus Lost");
    if (this.analysisControl.isStartingPortfolioValid(this.getStartingPortfolioText())) {
      this.jLabelStartingPortfolio.setForeground(Color.black);
    }
    else {
      this.jLabelStartingPortfolio.setForeground(Color.red);
    }
  }

  void jTextFieldStartingStock_focusLost(FocusEvent e) {
    // System.out.println("Analysis Starting Stock Focus Lost");
    if (this.analysisControl.isStartingStockValid(this.getStartingStockText())) {
      this.jLabelStartingStock.setForeground(Color.black);
    }
    else {
      this.jLabelStartingStock.setForeground(Color.red);
    }
  }

  void jTextFieldStartingDate_focusLost(FocusEvent e) {
    // System.out.println("Analysis Starting Date Focus Lost");
    if (this.analysisControl.isStartingDateValid(this.getStartingDateText())) {
      this.jLabelStartingDate.setForeground(Color.black);
    }
    else {
      this.jLabelStartingDate.setForeground(Color.red);
    }
  }

  void jTextFieldInterestRate_focusLost(FocusEvent e) {
    // System.out.println("Analysis Interest Rate Focus Lost");
    if (this.analysisControl.isInterestRateValid(this.getInterestRateText())) {
      this.jLabelInterestRate.setForeground(Color.black);
    }
    else {
      this.jLabelInterestRate.setForeground(Color.red);
    }
  }


  void jTextFieldBrokerageFee_focusLost(FocusEvent e) {
    // System.out.println("Analysis Brokerage Fee Focus Lost");
    if (this.analysisControl.isBrokerageFeeValid(this.getBrokerageFeeText())) {
      this.jLabelBrokerageFee.setForeground(Color.black);
    }
    else {
      this.jLabelBrokerageFee.setForeground(Color.red);
    }
  }

  void jTextFieldFrequency_focusLost(FocusEvent e) {
    // System.out.println("Analysis Trading Frequency Focus Lost");
    if (this.analysisControl.isTradeFrequencyValid(this.getTradeFrequencyText())) {
      this.jLabelFrequency.setForeground(Color.black);
    }
    else {
      this.jLabelFrequency.setForeground(Color.red);
    }
  }

  void jComboBoxCashReserve_focusLost(FocusEvent e) {
    // System.out.println("Analysis Cash Reserve Focus Lost");
    if (this.analysisControl.isCashReserveValid(this.getCashReserveText())) {
      this.jLabelCashReserve.setForeground(Color.black);
    }
    else {
      this.jLabelCashReserve.setForeground(Color.red);
    }
  }

  void jComboBoxMarketIndicator_focusLost(FocusEvent e) {
    // System.out.println("Analysis Market Indicator Focus Lost");
    if (this.analysisControl.isMarketIndicatorValid(this.getMarketIndicatorText())) {
      this.jLabelMarketIndicator.setForeground(Color.black);
    }
    else {
      this.jLabelMarketIndicator.setForeground(Color.red);
    }
  }

  void jTextFieldMinimumTrade_focusLost(FocusEvent e) {
    // System.out.println("Analysis Minimum Trade Focus Lost");
    this.minimumTradeCheck();
  }

  void jCheckBoxPercent_itemStateChanged(ItemEvent e) {
    // System.out.println("Analysis Percent State Changed");
    this.minimumTradeCheck();
  }

  private void minimumTradeCheck() {
    if (this.analysisControl.isMinimumTradeValid(this.getMinimumTradeText(), this.getPercentSelected())) {
      this.jLabelMinimumTrade.setForeground(Color.black);
    }
    else {
      this.jLabelMinimumTrade.setForeground(Color.red);
    }
  }

  // Convenience Methods to set Form Fields

  public void setStockNameText(String txtVal) {
    this.jTextFieldStockName.setText(txtVal == null ? "" : txtVal);
  }

  public void setStockCodeText(String txtVal) {
    this.jTextFieldStockCode.setText(txtVal == null ? "" : txtVal);
  }

  public void setStartingPortfolioText(String txtVal) {
    this.jTextFieldStartingPortfolio.setText(txtVal == null ? "" : txtVal);
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

  public void setTradeFrequencyText(String txtVal) {
    this.jTextFieldFrequency.setText(txtVal == null ? "" : txtVal);
  }

  public void setCashReserveIndex(int idxVal) {
    this.jComboBoxCashReserve.setSelectedIndex(idxVal);
  }

  public void setMarketIndicatorIndex(int idxVal) {
    this.jComboBoxMarketIndicator.setSelectedIndex(idxVal);
  }

  public void setMinimumTradeText(String txtVal) {
    this.jTextFieldMinimumTrade.setText(txtVal == null ? "" : txtVal);
  }

  public void setPercentSelected(boolean selVal) {
    this.jCheckBoxPercent.setSelected(selVal);
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

  public String getStartingPortfolioText() {
    return this.jTextFieldStartingPortfolio.getText().trim();
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

  public String getTradeFrequencyText() {
    return this.jTextFieldFrequency.getText().trim();
  }

  public String getCashReserveText() {
    return this.jComboBoxCashReserve.getSelectedItem().toString().trim();
  }

  public String getMarketIndicatorText() {
    return this.jComboBoxMarketIndicator.getSelectedItem().toString().trim();
  }

  public String getMinimumTradeText() {
    return this.jTextFieldMinimumTrade.getText().trim();
  }

  public boolean getPercentSelected() {
    return this.jCheckBoxPercent.isSelected();
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
  public void displayNewSampleDialog(String tradeDate, String tradePrice) {
    ZomFrame frame = this.analysisControl.getFrame();
    ZomFrame_NewSampleDialog dlg = new ZomFrame_NewSampleDialog(frame, this.analysisControl);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = frame.getSize();
    Point loc = frame.getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.jTextFieldSampleDate.setText(tradeDate);
    dlg.jTextFieldSamplePrice.setText(tradePrice);
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
      this.analysisControl.confirmedDeleteSample();
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
      this.analysisControl.confirmedDeleteAllSamples();
    }
  }

  /**Help | Stock Dialog Requester*/
  public void displayImportDialog() {
    ZomFrame frame = this.analysisControl.getFrame();
    ZomFrame_ImportDialog dlg = new ZomFrame_ImportDialog(frame, this.analysisControl);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = frame.getSize();
    Point loc = frame.getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.show();
  }
}