package com.byotrader.zom;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public final class ZomRandom {
  private static long seed = 1;

  private static long a = 16807;
  private static long m = 9223372036854775807L;
  private static long q = 127773;
  private static long r = 2836;

  private ZomRandom() {
  }

  public static void setSeed(long s) {
    if (s < 1 || s >= m) {
      throw new IllegalArgumentException("ZomRandom.setSeed: Invalid Seed");
    }

    seed = s;
  }

  public static double getRandomDouble() {
    generateNextSeed();

    return (double)seed / (double)m;
  }

  public static long getRandomLong() {
    generateNextSeed();

    return seed;
  }

  public static char getRandomChar() {
    generateNextSeed();

    return (char)((seed % 96) + 32);
  }

  public static byte getRandomByte() {
    generateNextSeed();

    return (byte)((seed % 96) + 32);
  }

  public static String getRandomString(int len) {
    if (len < 0) {
      throw new IllegalArgumentException("ZomRandom.getRandomString: Invalid len");
    }

    if (len == 0) {
      return "";
    }

    byte[] rndByte = new byte[len];

    for(int i = 0 ; i < len ; i++) {
      rndByte[i] = getRandomByte();
    }

    return new String(rndByte);
  }

  private static void generateNextSeed() {
    seed = a * (seed % q) - r * (seed / q);
    if (seed < 0) {
      seed += m;
    }
  }
}