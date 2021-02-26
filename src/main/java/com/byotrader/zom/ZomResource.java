package com.byotrader.zom;

import java.net.URL;
import java.io.File;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public final class ZomResource {
  private static boolean jarResourceExists = false;

  private ZomResource() {
  }

  public static void init(String jarResource) {
    File jarFile = new File(jarResource);
    jarResourceExists = jarFile.exists();
  }

  public static URL getResource(String pkg, String resource) {
    if (jarResourceExists) {
      URL url;
      String jarResource = "jar:file:bin/" + pkg + ".jar!/" + pkg + "/" + resource;

      try {
        url = new URL(jarResource);
      }
      catch (Exception ex) {
        ex.printStackTrace();
        return null;
      }

      return url;
    }
    else {
      URL url = ZomResource.class.getResource(pkg + "/" + resource);
      return url;
    }
  }
}