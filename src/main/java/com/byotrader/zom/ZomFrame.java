package com.byotrader.zom;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Toolkit;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;

import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;

//import java.util.*;

public class ZomFrame extends JFrame {
  private ZomControl zomControl;
  private JPanel currentPanel;
  JPanel contentPane;
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenuFile = new JMenu();
  JMenuItem jMenuFileExit = new JMenuItem();
  JMenu jMenuHelp = new JMenu();
  JMenuItem jMenuHelpAbout = new JMenuItem();
  JToolBar jToolBar = new JToolBar();
  JButton jButtonCopy = new JButton();
  JButton jButtonCut = new JButton();
  JButton jButtonPaste = new JButton();
  ImageIcon image1;
  ImageIcon image2;
  ImageIcon image3;
  JLabel statusBar = new JLabel();
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanelMenu = new JPanel();
  Border border1;
  TitledBorder titledBorder1;
  Border border2;
  Border border3;
  Border border4;
  TitledBorder titledBorder2;
  Border border5;
  GridBagLayout gridBagLayout1 = new GridBagLayout();
  JButton jButtonPortfolio = new JButton();
  JButton jButtonAnalysis = new JButton();
  JButton jButtonTrade = new JButton();
  Border border6;

  public ZomControl getZomControl() {
    return this.zomControl;
  }

  public void setZomControl(ZomControl newZomControl) {
    this.zomControl = newZomControl;
  }

  public ZomFrame() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  /**Component initialization*/
  private void jbInit() throws Exception  {
//    image1 = new ImageIcon(ZomResource.getResource("/com/byotrader/zom/resources", "openFile.gif"));
//    image2 = new ImageIcon(ZomResource.getResource("/com/byotrader/zom/resources", "closeFile.gif"));
//    image3 = new ImageIcon(ZomResource.getResource("/com/byotrader/zom/resources", "help.gif"));
    setIconImage(Toolkit.getDefaultToolkit().createImage(ZomResource.getResource("/com/byotrader/zom/resources", "ZomIcon.gif")));
    contentPane = (JPanel) this.getContentPane();
    border1 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(178, 178, 161));
    titledBorder1 = new TitledBorder(border1,"Menu");
    border2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(178, 178, 161)),"Portfolio");
    border3 = BorderFactory.createCompoundBorder(new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(178, 178, 161)),"Portfolio"),BorderFactory.createEmptyBorder(0,5,4,5));
    border4 = new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(156, 145, 140));
    titledBorder2 = new TitledBorder(border4,"Menu");
    border5 = BorderFactory.createCompoundBorder(new TitledBorder(new EtchedBorder(EtchedBorder.RAISED,Color.white,new Color(156, 145, 140)),"Menu"),BorderFactory.createEmptyBorder(0,5,4,5));
    border6 = BorderFactory.createEtchedBorder(Color.white,new Color(151, 145, 140));
    contentPane.setLayout(borderLayout1);
    this.setSize(new Dimension(495, 396));
    this.setTitle("ZOM Calculator");
    jMenuFile.setText("File");
    jMenuFileExit.setText("Exit");
    jMenuFileExit.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        jMenuFileExit_actionPerformed(e);
      }
    });
    jMenuHelp.setText("Help");
    jMenuHelpAbout.setText("About");
    jMenuHelpAbout.addActionListener(new ActionListener()  {
      public void actionPerformed(ActionEvent e) {
        jMenuHelpAbout_actionPerformed(e);
      }
    });
    jButtonCopy.setIcon(new ImageIcon(ZomResource.getResource("/com/byotrader/zom/resources", "copy.gif")));
    jButtonCopy.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonCopy_actionPerformed(e);
      }
    });
    jButtonCopy.setToolTipText("Copy entry");
    jButtonCut.setIcon(new ImageIcon(ZomResource.getResource("/com/byotrader/zom/resources", "cut.gif")));
    jButtonCut.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonCut_actionPerformed(e);
      }
    });
    jButtonCut.setToolTipText("Cut entry");
    jButtonPaste.setIcon(new ImageIcon(ZomResource.getResource("/com/byotrader/zom/resources", "paste.gif")));
    jButtonPaste.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonPaste_actionPerformed(e);
      }
    });
    jButtonPaste.setToolTipText("Paste entry");
    jPanelMenu.setBackground(new Color(223, 208, 200));
    jPanelMenu.setBorder(border5);
    jPanelMenu.setPreferredSize(new Dimension(100, 100));
    jPanelMenu.setLayout(gridBagLayout1);
    jButtonPortfolio.setFont(new java.awt.Font("Dialog", 1, 12));
    jButtonPortfolio.setMaximumSize(new Dimension(81, 81));
    jButtonPortfolio.setMinimumSize(new Dimension(81, 81));
    jButtonPortfolio.setPreferredSize(new Dimension(81, 81));
    jButtonPortfolio.setToolTipText("Display the Portfolio makeup.");
    jButtonPortfolio.setActionCommand("jButtonDoPortfolio");
    jButtonPortfolio.setHorizontalTextPosition(SwingConstants.CENTER);
    jButtonPortfolio.setIcon(new ImageIcon(ZomResource.getResource("/com/byotrader/zom/resources", "FILES.GIF")));
    jButtonPortfolio.setMargin(new Insets(2, 10, 2, 10));
    jButtonPortfolio.setText("Portfolio");
    jButtonPortfolio.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonPortfolio_actionPerformed(e);
      }
    });
    jButtonAnalysis.setFont(new java.awt.Font("Dialog", 1, 12));
    jButtonAnalysis.setMaximumSize(new Dimension(81, 81));
    jButtonAnalysis.setMinimumSize(new Dimension(81, 81));
    jButtonAnalysis.setPreferredSize(new Dimension(81, 81));
    jButtonAnalysis.setToolTipText("Activate the Analysis Tool");
    jButtonAnalysis.setActionCommand("jButtonDoAnalysis");
    jButtonAnalysis.setHorizontalTextPosition(SwingConstants.CENTER);
    jButtonAnalysis.setIcon(new ImageIcon(ZomResource.getResource("/com/byotrader/zom/resources", "CHCKBOOK.GIF")));
    jButtonAnalysis.setMargin(new Insets(2, 10, 2, 10));
    jButtonAnalysis.setText("Analysis");
    jButtonAnalysis.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonAnalysis_actionPerformed(e);
      }
    });
    jButtonTrade.setFont(new java.awt.Font("Dialog", 1, 12));
    jButtonTrade.setMaximumSize(new Dimension(81, 81));
    jButtonTrade.setMinimumSize(new Dimension(81, 81));
    jButtonTrade.setPreferredSize(new Dimension(71, 81));
    jButtonTrade.setToolTipText("Activate Trading History");
    jButtonTrade.setActionCommand("jButtonDoTrade");
    jButtonTrade.setHorizontalTextPosition(SwingConstants.CENTER);
    jButtonTrade.setIcon(new ImageIcon(ZomResource.getResource("/com/byotrader/zom/resources", "LETTER.GIF")));
    jButtonTrade.setMargin(new Insets(2, 10, 2, 10));
    jButtonTrade.setText("Trading");
    jButtonTrade.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jButtonTrade_actionPerformed(e);
      }
    });
    statusMsg("Welcome to ZOM", false, 0);
    jMenuFile.add(jMenuFileExit);
    jMenuHelp.add(jMenuHelpAbout);
    jMenuBar1.add(jMenuFile);
    jMenuBar1.add(jMenuHelp);
    this.setJMenuBar(jMenuBar1);
    contentPane.add(jPanelMenu, BorderLayout.WEST);
    jPanelMenu.add(jButtonPortfolio, new GridBagConstraints(0, GridBagConstraints.RELATIVE, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
    jPanelMenu.add(jButtonAnalysis, new GridBagConstraints(0, GridBagConstraints.RELATIVE, 1, 1, 0.0, 0.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 10, 0), 0, 0));
    jPanelMenu.add(jButtonTrade, new GridBagConstraints(0, GridBagConstraints.RELATIVE, 1, 1, 0.0, 1.0
            ,GridBagConstraints.NORTH, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    contentPane.add(jToolBar, BorderLayout.NORTH);
    jToolBar.add(jButtonCopy);
    jToolBar.add(jButtonCut);
    jToolBar.add(jButtonPaste);
    contentPane.add(statusBar, BorderLayout.SOUTH);
  }
  /**File | Exit action performed*/
  public void jMenuFileExit_actionPerformed(ActionEvent e) {
    System.out.println("Exiting");
    this.zomControl.exit();
    System.exit(0);
  }
  /**Help | About action performed*/
  public void jMenuHelpAbout_actionPerformed(ActionEvent e) {
    String  reg = this.zomControl.getRegString();
    ZomFrame_AboutBox dlg = new ZomFrame_AboutBox(this, reg);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.show();
  }
  /**Overridden so we can exit when window is closed*/
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.out.println("Closing");
      jMenuFileExit_actionPerformed(null);
    }
  }

  public void setZomPanel(JPanel zomPanel) {
    if (zomPanel == null) {
      System.out.println("ZomFrame.setZomPanel: null panel provided.");
      return;
    }

    this.contentPane.setVisible(false);

    if (currentPanel != null) {
      this.contentPane.remove(currentPanel);
    }

    this.contentPane.add(zomPanel, BorderLayout.CENTER);
    this.contentPane.setVisible(true);
    this.currentPanel = zomPanel;
  }

  void jButtonTrade_actionPerformed(ActionEvent e) {
    // System.out.println("Trade Button");
    this.zomControl.tradeModeRequested();
  }

  void jButtonPortfolio_actionPerformed(ActionEvent e) {
    // System.out.println("Portfolio Button");
    this.zomControl.portfolioModeRequested();
  }

  void jButtonAnalysis_actionPerformed(ActionEvent e) {
    // System.out.println("Analysis Button");
    this.zomControl.analysisModeRequested();
  }

  void jButtonCopy_actionPerformed(ActionEvent e) {
    // System.out.println("Copy Button");
    this.zomControl.getCurrentController().copyTradeData(this.zomControl.getTradeCopyBar());
  }

  void jButtonCut_actionPerformed(ActionEvent e) {
    // System.out.println("Cut Button");
    this.zomControl.getCurrentController().cutTradeData(this.zomControl.getTradeCopyBar());
  }

  void jButtonPaste_actionPerformed(ActionEvent e) {
    // System.out.println("Paste Button");
    this.zomControl.getCurrentController().pasteTradeData(this.zomControl.getTradeCopyBar());
  }

  /**Display any status bar messages*/
  public void statusMsg(String msg, boolean isErr, int blinkCount) {
    this.statusBar.setText(msg);

    if (isErr) {
      this.statusBar.setForeground(Color.red);
      Toolkit.getDefaultToolkit().beep();
    }

    if (blinkCount > 0) {
      new ZomBlinkingMessage(this, blinkCount);
    }
  }

  public void displayInfo(String info) {
    JOptionPane.showMessageDialog(this, info, "Zom Import", JOptionPane.INFORMATION_MESSAGE);
  }
}