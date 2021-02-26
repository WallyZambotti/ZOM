package com.byotrader.zom;

import java.util.Vector;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public class ZomControl extends ZomController {
  private ZomFrame zomFrame;
  private ZomController currentControl;
  private PortfolioControl portfolioControl;
  private AnalysisControl analysisControl;
  private TradeControl tradeControl;
  private TradeCopyBar tradeCopyBar = new TradeCopyBar();

  public ZomControl(ZomFrame frame) {
    if (frame == null) {
      System.out.println("ZomControl: missing user interface");
      return;
    }

    this.zomFrame = frame;
    this.zomFrame.setZomControl(this);

    // Manually kick off the creation of the default (or opening) Control
    this.portfolioControl = new PortfolioControl(frame);
    // And immediately display it for the first time
    this.portfolioControl.revealUI();
    this.currentControl = this.portfolioControl;
  }

  public void portfolioModeRequested() {
    if (currentControl == portfolioControl) {
      this.zomFrame.statusMsg("Portfolio mode already selected!", true, 5);
      return;
    }

    if (this.analysisControl != null) {
      this.portfolioControl.updateAnalysisSummary(
        this.analysisControl.getStockName(), this.analysisControl.getFinalValue(), this.analysisControl.getFinalDate());
    }

    if (this.tradeControl != null) {
      this.portfolioControl.updateTradeSummary(
        this.tradeControl.getStockName(), this.tradeControl.getFinalValue(), this.tradeControl.getFinalDate());
    }

    this.portfolioControl.revealUI();
    this.currentControl = this.portfolioControl;
  }

  public void analysisModeRequested() {
    if (currentControl == analysisControl) {
      this.zomFrame.statusMsg("Analysis mode already selected!", true, 5);
      return;
    }

    int rowIndex;

    rowIndex = this.portfolioControl.getSelectedRow();

    if (rowIndex == -1) {
      this.zomFrame.statusMsg("No stock selected!", true, 5);
      return;
    }

    Vector stockIdentity = this.portfolioControl.getSelectedStock(rowIndex);

    if (stockIdentity == null) {
      System.out.println("ZomControl.analysisModeRequested: stock row index returned null stock info!");
      return;
    }

    if (this.analysisControl == null) {
      System.out.println("ZomControl.analysisModeRequested: creating new analysis controller.");
      this.analysisControl = new AnalysisControl(this.zomFrame);
    }
    else {
      this.portfolioControl.updateAnalysisSummary(
        this.analysisControl.getStockName(), this.analysisControl.getFinalValue(), this.analysisControl.getFinalDate());
    }

    this.analysisControl.revealUI(stockIdentity);
    this.currentControl = this.analysisControl;
  }

  public void tradeModeRequested() {
    if (currentControl == tradeControl) {
      this.zomFrame.statusMsg("Trade mode already selected!", true, 5);
      return;
    }

    int rowIndex;

    rowIndex = this.portfolioControl.getSelectedRow();

    if (rowIndex == -1) {
      this.zomFrame.statusMsg("No stock selected!", true, 5);
      return;
    }

    Vector stockIdentity = this.portfolioControl.getSelectedStock(rowIndex);

    if (stockIdentity == null) {
      System.out.println("ZomControl.tradeModeRequested: stock row index returned null stock info!");
      return;
    }

    if (this.tradeControl == null) {
      System.out.println("ZomControl.tradeModeRequested: creating new trade controller.");
      this.tradeControl = new TradeControl(this.zomFrame);
    }
    else {
      this.portfolioControl.updateTradeSummary(
        this.tradeControl.getStockName(), this.tradeControl.getFinalValue(), this.tradeControl.getFinalDate());
    }

    this.tradeControl.revealUI(stockIdentity);
    this.currentControl = this.tradeControl;
  }

  public void checkRegistration() {
    this.portfolioControl.checkRegistration();
  }

  // When a stock is deleted the controllers should be destroyed
  // so new controllers can be created
  public void stockDeleted() {
    // Setting the controller reference to null will cause the conrtoller
    // creation methods (tradeModeRequested/analysisModeRequested) to create anew
    this.analysisControl = null;
    this.tradeControl = null;
  }

  public void exit() {
    this.portfolioControl.savePortfolioData();

    if (this.analysisControl != null) {
      this.portfolioControl.updateAnalysisSummary(
        this.analysisControl.getStockName(), this.analysisControl.getFinalValue(), this.analysisControl.getFinalDate());
      this.analysisControl.saveAnalysisData();
    }

    if (this.tradeControl != null) {
      this.portfolioControl.updateTradeSummary(
        this.tradeControl.getStockName(), this.tradeControl.getFinalValue(), this.tradeControl.getFinalDate());
      this.tradeControl.saveTradeData();
    }
  }

  // Access to the Copy, Cut n Paste Object!

  public TradeCopyBar getTradeCopyBar() {
    return this.tradeCopyBar;
  }

  public ZomController getCurrentController() {
    return this.currentControl;
  }

  // Abstract Method Provision for Cut n Paste Logic

  public void copyTradeData(TradeCopyBar tradeCopyBar) {
    zomFrame.statusMsg("Copy function not relevant in zom mode!", true, 5);
  }

  public void cutTradeData(TradeCopyBar tradeCopyBar) {
    zomFrame.statusMsg("Cut function not relevant in zom mode!", true, 5);
  }

  public void pasteTradeData(TradeCopyBar tradeCopyBar) {
    zomFrame.statusMsg("Paste function not relevant in zom mode!", true, 5);
  }

  public String getRegString() {
    return this.portfolioControl.getDataModel().getRegString();
  }
}