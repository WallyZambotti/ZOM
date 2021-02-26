package com.byotrader.zom;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import java.util.Vector;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public class ZomFrame_ImportDialog extends JDialog implements ActionListener {
  JPanel jPanel1 = new JPanel();
  JTextField jTextFieldImportFile = new JTextField();
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel2 = new JLabel();
  JTextField jTextFieldStartDate = new JTextField();
  JButton jButtonImport = new JButton();
  JButton jButtonCancel = new JButton();
  Component component1;
  JButton jButtonBrowse = new JButton();
  JLabel jLabel3 = new JLabel();
  JTextField jTextFieldStartRow = new JTextField();
  JLabel jLabel4 = new JLabel();
  JTextField jTextFieldEndDate = new JTextField();
  JLabel jLabel5 = new JLabel();
  Component component2;
  JLabel jLabel6 = new JLabel();
  JLabel jLabel7 = new JLabel();
  JTextField jTextFieldDateColumn = new JTextField();
  JTextField jTextFieldEndRow = new JTextField();
  JTextField jTextFieldValueColumn = new JTextField();
  Component component3;
  JButton jButtonAssignEndRow = new JButton();
  JButton jButtonAssignDateColumn = new JButton();
  JButton jButtonAssignValueColumn = new JButton();
  JButton jButtonAssignStartRow = new JButton();
  // Additions
  AnalysisControl analysisControl;
  CSVDefTableModel csvTableModel;
  AnalysisCSVdataModel csvDataModel;

  /**Construct The Portfolio Column Model */
  DefaultTableColumnModel csvColumnModel = new DefaultTableColumnModel();
  JScrollPane jScrollPaneCSV = new JScrollPane();
  JTable jTableCSV = new JTable();

  private int startRow;
  private int endRow;
  private int maxRow;
  private int dateCol;
  private int valueCol;
  private int maxCol;

  public int getStartRow() { return this.startRow; }

  public int getEndRow() { return this.endRow; }

  public int getDateColumn() { return this.dateCol; }

  public int getValueColumn() { return this.valueCol; }

  public AnalysisCSVdataModel getDataModel() { return this.csvDataModel; }

  public ZomFrame_ImportDialog(Frame parent, AnalysisControl analysisControl) {
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
    component1 = Box.createHorizontalStrut(8);
    component2 = Box.createHorizontalStrut(8);
    component3 = Box.createHorizontalStrut(8);
    this.setTitle("Import CSV File");
    jPanel1.setLayout(gridBagLayout1);
    jTextFieldImportFile.setBackground(UIManager.getColor("inactiveCaptionText"));
    jTextFieldImportFile.setEnabled(false);
    jTextFieldImportFile.setNextFocusableComponent(jTextFieldStartRow);
    jTextFieldImportFile.setToolTipText("Enter the path and file name of the csv file.");
    jTextFieldImportFile.setDisabledTextColor(Color.black);
    jTextFieldImportFile.setEditable(false);
    jTextFieldImportFile.setText("To begin press the GREEN button (or Cancel to Quit)");
    jLabel1.setText("Import File");
    jLabel2.setMaximumSize(new Dimension(67, 17));
    jLabel2.setMinimumSize(new Dimension(67, 17));
    jLabel2.setPreferredSize(new Dimension(67, 17));
    jLabel2.setText("Start Date");
    jButtonImport.setNextFocusableComponent(jButtonCancel);
    jButtonImport.setSelected(true);
    jButtonImport.setText("Import");
    jButtonImport.addActionListener(this);
    jButtonCancel.addActionListener(this);
    jButtonCancel.setNextFocusableComponent(jTextFieldImportFile);
    jButtonCancel.setText("Cancel");
    this.setResizable(false);
    this.setModal(true);
    jPanel1.setMinimumSize(new Dimension(420, 280));
    jPanel1.setPreferredSize(new Dimension(420, 280));
    jTextFieldStartDate.setBackground(UIManager.getColor("inactiveCaptionText"));
    jTextFieldStartDate.setEnabled(false);
    jTextFieldStartDate.setNextFocusableComponent(jButtonImport);
    jTextFieldStartDate.setToolTipText("The Start Date corresponding to the Start Row.");
    jTextFieldStartDate.setEditable(false);
    jButtonBrowse.setBackground(new Color(0, 171, 0));
    jButtonBrowse.setMaximumSize(new Dimension(16, 20));
    jButtonBrowse.setMinimumSize(new Dimension(16, 20));
    jButtonBrowse.setPreferredSize(new Dimension(16, 20));
    jButtonBrowse.setToolTipText("Browse for a CSV file.");
    jButtonBrowse.setMargin(new Insets(0, 0, 0, 0));
    jButtonBrowse.setText("...");
    jButtonBrowse.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonBrowse_actionPerformed(e);
      }
    });
    component1.setBackground(Color.black);
    jLabel3.setText("Start Row");
    jLabel4.setText("End Date");
    jLabel5.setText("End Row");
    component2.setBackground(Color.black);
    jLabel6.setText("Date Column");
    jLabel7.setText("Value Column");
    component3.setBackground(Color.black);
    jButtonAssignEndRow.setMaximumSize(new Dimension(16, 20));
    jButtonAssignEndRow.setMinimumSize(new Dimension(16, 20));
    jButtonAssignEndRow.setPreferredSize(new Dimension(16, 20));
    jButtonAssignEndRow.setToolTipText("Assign the end row from that currently selected.");
    jButtonAssignEndRow.setMargin(new Insets(0, 0, 0, 0));
    jButtonAssignEndRow.setText("<");
    jButtonAssignEndRow.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonAssignEndRow_actionPerformed(e);
      }
    });
    jButtonAssignDateColumn.setText("<");
    jButtonAssignDateColumn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonAssignDateColumn_actionPerformed(e);
      }
    });
    jButtonAssignDateColumn.setMargin(new Insets(0, 0, 0, 0));
    jButtonAssignDateColumn.setPreferredSize(new Dimension(16, 20));
    jButtonAssignDateColumn.setToolTipText("Assign the date column from that currently selected.");
    jButtonAssignDateColumn.setMinimumSize(new Dimension(16, 20));
    jButtonAssignDateColumn.setMaximumSize(new Dimension(16, 20));
    jButtonAssignValueColumn.setText("<");
    jButtonAssignValueColumn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonAssignValueColumn_actionPerformed(e);
      }
    });
    jButtonAssignValueColumn.setMargin(new Insets(0, 0, 0, 0));
    jButtonAssignValueColumn.setPreferredSize(new Dimension(16, 20));
    jButtonAssignValueColumn.setToolTipText("Assign the value column from that currently selected.");
    jButtonAssignValueColumn.setMinimumSize(new Dimension(16, 20));
    jButtonAssignValueColumn.setMaximumSize(new Dimension(16, 20));
    jButtonAssignStartRow.setMaximumSize(new Dimension(16, 20));
    jButtonAssignStartRow.setMinimumSize(new Dimension(16, 20));
    jButtonAssignStartRow.setPreferredSize(new Dimension(16, 20));
    jButtonAssignStartRow.setToolTipText("Assign the start row from that currently selected.");
    jButtonAssignStartRow.setMargin(new Insets(0, 0, 0, 0));
    jButtonAssignStartRow.setText("<");
    jButtonAssignStartRow.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonAssignStartRow_actionPerformed(e);
      }
    });
    jTextFieldEndDate.setBackground(UIManager.getColor("inactiveCaptionText"));
    jTextFieldEndDate.setEnabled(false);
    jTextFieldEndDate.setToolTipText("The End Date corresponding to the End Row.");
    jTextFieldEndDate.setEditable(false);
    jTextFieldStartRow.setNextFocusableComponent(jTextFieldEndRow);
    jTextFieldStartRow.setToolTipText("Changes the row for which the Start Date is chosen.");
    jTextFieldStartRow.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        jTextFieldStartRow_focusGained(e);
      }
      public void focusLost(FocusEvent e) {
        jTextFieldStartRow_focusLost(e);
      }
    });
    jTextFieldEndRow.setNextFocusableComponent(jTextFieldDateColumn);
    jTextFieldEndRow.setToolTipText("Changes the row for which the End Date is chosen.");
    jTextFieldEndRow.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        jTextFieldEndRow_focusGained(e);
      }
      public void focusLost(FocusEvent e) {
        jTextFieldEndRow_focusLost(e);
      }
    });
    jTextFieldDateColumn.setNextFocusableComponent(jTextFieldValueColumn);
    jTextFieldDateColumn.setToolTipText("Specify the date column from the CSV file. ");
    jTextFieldDateColumn.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        jTextFieldDateColumn_focusGained(e);
      }
      public void focusLost(FocusEvent e) {
        jTextFieldDateColumn_focusLost(e);
      }
    });
    jTextFieldValueColumn.setNextFocusableComponent(jButtonImport);
    jTextFieldValueColumn.setToolTipText("Specify the value column from the CSV file. ");
    jTextFieldValueColumn.addFocusListener(new java.awt.event.FocusAdapter() {
      public void focusGained(FocusEvent e) {
        jTextFieldValueColumn_focusGained(e);
      }
      public void focusLost(FocusEvent e) {
        jTextFieldValueColumn_focusLost(e);
      }
    });
    this.getContentPane().add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jTextFieldImportFile, new GridBagConstraints(1, 0, 4, 1, 1.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 2, 2), 230, 0));
    jPanel1.add(jLabel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 4, 7, 0), 0, 0));
    jPanel1.add(jTextFieldStartDate, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 1));
    jPanel1.add(jButtonImport, new GridBagConstraints(1, 8, 1, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(8, 0, 0, 0), 0, 0));
    jPanel1.add(jLabel2, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 4, 7, 0), 0, 0));
    jPanel1.add(component1, new GridBagConstraints(0, 1, 4, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 3));
    jPanel1.add(jTextFieldStartRow, new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 2), 60, 0));
    jPanel1.add(jLabel4, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 4, 7, 0), 0, 0));
    jPanel1.add(jTextFieldEndDate, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(component2, new GridBagConstraints(0, 4, 4, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 3));
    jPanel1.add(jLabel6, new GridBagConstraints(0, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 4, 7, 4), 0, 0));
    jPanel1.add(jLabel7, new GridBagConstraints(3, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 7, 4), 0, 0));
    jPanel1.add(jTextFieldDateColumn, new GridBagConstraints(1, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.BOTH, new Insets(0, 0, 2, 0), 0, 0));
    jPanel1.add(jTextFieldEndRow, new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 2), 0, 0));
    jPanel1.add(jTextFieldValueColumn, new GridBagConstraints(4, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL, new Insets(0, 0, 0, 2), 0, 0));
    jPanel1.add(component3, new GridBagConstraints(0, 6, 4, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 3));
    jPanel1.add(jButtonAssignValueColumn, new GridBagConstraints(5, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jButtonAssignEndRow, new GridBagConstraints(5, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 4), 0, 0));
    jPanel1.add(jButtonAssignDateColumn, new GridBagConstraints(2, 5, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 2, 0, 4), 0, 0));
    jPanel1.add(jLabel5, new GridBagConstraints(3, 3, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jLabel3, new GridBagConstraints(3, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jButtonBrowse, new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jButtonAssignStartRow, new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    jPanel1.add(jButtonCancel, new GridBagConstraints(3, 8, 1, 1, 0.0, 0.0
            ,GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(8, 0, 0, 0), 0, 0));
    jPanel1.add(jScrollPaneCSV, new GridBagConstraints(0, 7, 6, 1, 0.0, 0.0
            ,GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 100));
    jScrollPaneCSV.getViewport().add(jTableCSV, null);
    this.getRootPane().setDefaultButton(jButtonImport);

    this.jButtonImport.setEnabled(false);
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

    if (e.getSource() == jButtonImport) {
      if (this.analysisControl != null) {
        this.analysisControl.loadCSVFile(this) ;
      }
    }

    cancel();
  }

  void jButtonBrowse_actionPerformed(ActionEvent e) {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setMultiSelectionEnabled(false);

    int result = fileChooser.showOpenDialog(this);

    if (result == JFileChooser.APPROVE_OPTION) {
      this.analysisControl.parseCSVFile(this, fileChooser.getSelectedFile());
    }
  }

  // Populate the dialog from the csvDataModel

  public void setCSVdataModel(AnalysisCSVdataModel csvDataModel, CSVDefTableModel csvTableModel) {
    this.csvTableModel = csvTableModel;
    this.csvDataModel = csvDataModel;

    this.startRow = 0;
    this.maxRow = csvDataModel.getRowData().size();
    this.endRow = maxRow-1;
    this.maxCol = ((Vector)csvDataModel.getRowData().get(0)).size();
    this.dateCol = csvDataModel.getDateColumn();
    this.valueCol = csvDataModel.getValueColumn();

    this.jTextFieldImportFile.setText(csvDataModel.getCSVfile().getPath());
    this.jTextFieldStartDate.setText(
      (String)((Vector)csvDataModel.getRowData().get(0)).get(dateCol));
    this.jTextFieldStartRow.setText(Integer.toString(1));
    this.jTextFieldEndDate.setText(
      (String)((Vector)csvDataModel.getRowData().get(endRow)).get(dateCol));
    this.jTextFieldEndRow.setText(Integer.toString(maxRow));
    this.jTextFieldDateColumn.setText(Integer.toString(dateCol));
    this.jTextFieldValueColumn.setText(Integer.toString(csvDataModel.getValueColumn()));

    // Set up all the Portfolio Table column Preferences
    for(int col = 0; col < this.csvTableModel.getColumnCount(); col++) {
      csvColumnModel.addColumn(setColumnPreferences(col));
    }

    jTableCSV.setColumnModel(csvColumnModel);
    jTableCSV.setModel(csvTableModel);
    jTableCSV.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    jTableCSV.setColumnSelectionAllowed(true);

    this.jButtonImport.setEnabled(true);
  }

  TableColumn setColumnPreferences(int columnIndex) {
    // Column 0 Stock
    TableColumn anyColumn = new TableColumn(columnIndex);
    anyColumn.setHeaderValue(this.csvTableModel.getColumnName(columnIndex));

    // Change the background color to indicate non selectable column
    DefaultTableCellRenderer editableCellRenderer = new DefaultTableCellRenderer();
    editableCellRenderer.setBackground(
        this.csvTableModel.getColumnColor(columnIndex, jTableCSV.getBackground()));
    editableCellRenderer.setToolTipText(this.csvTableModel.getColumnToolTip(columnIndex));
    anyColumn.setCellRenderer(editableCellRenderer);

    return anyColumn;
  }

  void jButtonAssignStartRow_actionPerformed(ActionEvent e) {
    this.analysisControl.assignStartRow(this, jTableCSV.getSelectedRow());
  }

  void setStartRow(int row) {
    this.jTextFieldStartDate.setText(
      (String)((Vector)csvDataModel.getRowData().get(row)).get(csvDataModel.getDateColumn()));
    this.jTextFieldStartRow.setText(Integer.toString(row+1));
    this.startRow = row;
  }

  void jButtonAssignEndRow_actionPerformed(ActionEvent e) {
    this.analysisControl.assignEndRow(this, jTableCSV.getSelectedRow());
  }

  void setEndRow(int row) {
    this.jTextFieldEndDate.setText(
      (String)((Vector)csvDataModel.getRowData().get(row)).get(csvDataModel.getDateColumn()));
    this.jTextFieldEndRow.setText(Integer.toString(row+1));
    this.endRow = row;
  }

  void jButtonAssignValueColumn_actionPerformed(ActionEvent e) {
    this.analysisControl.assignValueColumn(this, this.csvDataModel, jTableCSV.getSelectedColumn());
  }

  void setValueColumn(int col) {
    this.jTextFieldValueColumn.setText(Integer.toString(col));
    this.valueCol = col;
  }

  void jButtonAssignDateColumn_actionPerformed(ActionEvent e) {
    this.analysisControl.assignDateColumn(this, this.csvDataModel, jTableCSV.getSelectedColumn());
  }

  void setDateColumn(int col) {
    this.jTextFieldDateColumn.setText(Integer.toString(col));
    this.jTextFieldStartDate.setText(
      (String)((Vector)csvDataModel.getRowData().get(
        Integer.parseInt(this.jTextFieldStartRow.getText())-1)).get(col));
    this.jTextFieldEndDate.setText(
      (String)((Vector)csvDataModel.getRowData().get(
        Integer.parseInt(this.jTextFieldEndRow.getText())-1)).get(col));
    this.dateCol = col;
  }

  void jTextFieldStartRow_focusGained(FocusEvent e) {
    try {
      this.startRow = Integer.parseInt(this.jTextFieldStartRow.getText())-1;
    }
    catch (Exception ex) {
      this.startRow = 0;
    }
  }

  void jTextFieldEndRow_focusGained(FocusEvent e) {
    try {
      this.endRow = Integer.parseInt(this.jTextFieldEndRow.getText())-1;
    }
    catch (Exception ex) {
      this.endRow = 0;
    }
  }

  void jTextFieldDateColumn_focusGained(FocusEvent e) {
    try {
      this.dateCol = Integer.parseInt(this.jTextFieldDateColumn.getText());
    }
    catch (Exception ex) {
      this.dateCol = 0;
    }
  }

  void jTextFieldValueColumn_focusGained(FocusEvent e) {
    try {
      this.valueCol = Integer.parseInt(this.jTextFieldValueColumn.getText());
    }
    catch (Exception ex) {
      this.valueCol = 0;
    }
  }

  void jTextFieldStartRow_focusLost(FocusEvent e) {
    int row;
    try {
      row = Integer.parseInt(this.jTextFieldStartRow.getText())-1;
    }
    catch (Exception ex) {
      row = 0;
    }

    if (row == this.startRow) { return; }
    if (row > this.endRow) { row = this.endRow; }
    if (row < 0) { row = 0; }

    this.setStartRow(row);
  }

  void jTextFieldEndRow_focusLost(FocusEvent e) {
    int row;
    try {
      row = Integer.parseInt(this.jTextFieldEndRow.getText())-1;
    }
    catch (Exception ex) {
      row = 0;
    }

    if (row == this.endRow) { return; }
    if (row > this.maxRow) { row = this.maxRow; }
    if (row < this.startRow) { row = this.startRow; }

    this.setEndRow(row);
  }

  void jTextFieldDateColumn_focusLost(FocusEvent e) {
    int col;
    try {
      col = Integer.parseInt(this.jTextFieldDateColumn.getText());
    }
    catch (Exception ex) {
      col = 0;
    }

    if (col == this.dateCol) { return; }
    if (col >= this.maxCol) { col = this.maxCol-1; }
    if (col < 1) { col = 1; }
    if (col == this.valueCol) { col = this.dateCol; }

    if (!this.analysisControl.assignDateColumn(this, this.csvDataModel, col)) {
      this.setDateColumn(this.dateCol);
    }
  }

  void jTextFieldValueColumn_focusLost(FocusEvent e) {
    int col;
    try {
      col = Integer.parseInt(this.jTextFieldValueColumn.getText());
    }
    catch (Exception ex) {
      col = 0;
    }

    if (col == this.valueCol) { return; }
    if (col >= this.maxCol) { col = this.maxCol-1; }
    if (col < 1) { col = 1; }
    if (col == this.dateCol) { col = this.valueCol; }

    if (!this.analysisControl.assignValueColumn(this, this.csvDataModel, col)) {
      this.setValueColumn(this.valueCol);
    }
  }
}