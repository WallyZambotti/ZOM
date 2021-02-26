package com.byotrader.zom;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public final class ZomEnigma {
  private static int CODEWHEELS = 17;
  private static int CHARSETSIZE = 95;

  private static byte[][][] cmap = new byte[CODEWHEELS][CHARSETSIZE][2];

  private ZomEnigma() {
  }

  public static void setKey(int key) {
    ZomRandom.setSeed(key);

    initCodeMap();
  }

  public static void setKey(String txtKey) {
    ZomRandom.setSeed(text2Key(txtKey));

    initCodeMap();
  }

  private static long text2Key(String txt) {
    long key = 0;
    String tmpTxt = txt.trim().toUpperCase();
    int len = tmpTxt.length();
    double prec = 0.0;
    long mult, digit;

    for (int i = len-1 ; i >= 0 ; i--) {
      mult = (long)Math.pow(36.0, prec);
      digit = tmpTxt.charAt(i) - 48;
      if (digit > 9) { digit -= 7; }
      key += digit * mult;
      prec += 1.0;
    }

    return key;
  }

  private static void initCodeMap(){
    boolean free[] = new boolean[96];

    int i, j, k;

    for(i = 0 ; i < CODEWHEELS ; i++) {
      for(j = 0 ; j < CHARSETSIZE ; free[j++] = true) ;
      for(j = 0 ; j < CHARSETSIZE ; j++) {
        for (k = (int)(ZomRandom.getRandomLong() % CHARSETSIZE) ; !free[k] ; k = (k + 1) % 96) ;
        cmap[i][j][0] = (byte)k;
        cmap[i][k][1] = (byte)j;
        free[k] = false;
      }
    }
  }

  public static String getEncryptedString(String s) {
    if (s == null) {
      throw new IllegalArgumentException("ZomEnigma.getDecryptedString: Invalid string");
    }

    int len = s.length();

     if (len == 0) {
      return "";
    }

    byte[] rndByte = new byte[len];

    for(int i = 0 ; i < len ; i++) {
      rndByte[i] =(byte)(cmap[(int)(ZomRandom.getRandomLong() % CODEWHEELS)][((int)s.charAt(i)-32)][0] + 32);
    }

    return new String(rndByte);
  }

  public static String getDecryptedString(String s) {
    if (s == null) {
      throw new IllegalArgumentException("ZomEnigma.getDecryptedString: Invalid string");
    }

    int len = s.length();

     if (len == 0) {
      return "";
    }

    byte[] rndByte = new byte[len];

    for(int i = 0 ; i < len ; i++) {
      rndByte[i] = (byte)(cmap[(int)(ZomRandom.getRandomLong() % CODEWHEELS)][((int)s.charAt(i)-32)][1] + 32);
    }

    return new String(rndByte);
  }
}