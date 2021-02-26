package com.byotrader.zom;

import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public class ZomBlinkingMessage {
  Timer msgTimer;
  int blinkCount;
  ZomFrame zomFrame;

  public ZomBlinkingMessage(ZomFrame frame, int newCount) {
    if (frame == null) {
      System.out.println("ZomBlinkingMessage.ZomBlinkingMessage: missing ZomFrame");
      return;
    }

    if (newCount < 1) {
      return;
    }

    this.zomFrame = frame;
    this.blinkCount = newCount;

    blink();
  }

  public void blink() {
    msgTimer = new Timer(750,
      new ActionListener() {
        public void actionPerformed(ActionEvent evt) {
          zomFrame.statusBar.setForeground(
            (zomFrame.statusBar.getForeground() == Color.red) ? Color.black : Color.red);

          if (--blinkCount < 1) {
            zomFrame.statusBar.setText(" ");
            msgTimer.stop();
          }
        }
      }
    );

    msgTimer.start();
  }
}