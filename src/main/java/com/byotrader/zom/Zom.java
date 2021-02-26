package com.byotrader.zom;

import javax.swing.UIManager;
import java.awt.Dimension;
import java.awt.Toolkit;

import java.util.Date;
import java.text.DateFormat;
import java.util.Locale;

import java.io.File;

public class Zom {
  boolean packFrame = false;

  /**Construct the application*/
  public Zom(String[] args) {
    // Locate any Jar Resources
    ZomResource.init("bin/zom.jar");

    // Create all the locale specific formatting objects
    ZomFormat.init(args);

    // Build the Main window
    ZomFrame frame = new ZomFrame();

    // Create the master controller
    ZomControl zomControl = new ZomControl(frame);

    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame) {
      frame.pack();
    }
    else {
      frame.validate();
    }
    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    }
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
    frame.setVisible(true);

    // Check the registration
    //zomControl.checkRegistration();
  }
  /**Main method*/
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      // UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
      // UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
      // UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
     }
    catch(Exception e) {
      e.printStackTrace();
    }
    new Zom(args);
  }

  private String byte2Str(byte[] buf, int len) {
    String buf2 = "";
    char ch;
    for(int i = 0 ; i < len ; i++) {
      ch = (char)buf[i];
      buf2 = buf2 + ch;
    }
    return buf2;
  }
}