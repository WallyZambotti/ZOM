package com.byotrader.zom;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Constructor;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public final class ObjectFactory {
  private static Class theClass;
  private static Object theObject;

  public static Class getTheClass() {
    return theClass;
  }

  private static void setTheObject(Object newObject) {
    theObject = newObject;
  }

  public static Object getTheObject() {
    return theObject;
  }

  private static void setTheClass(String newClassName) {
    try {
      // Create the class for the provide class name (an exception will occur if no such class exists)
      theClass = Class.forName(newClassName);
    }
    catch (Exception e) {
      e.printStackTrace(System.err);
    }
  }

  private ObjectFactory() {
  }

  public static Object instantiateNew(String className) {
    setTheClass(className);

    try {
      setTheObject(theClass.newInstance());
    }
    catch (Exception e) {
      e.printStackTrace(System.err);
    }

    return getTheObject();
  }

  public static Object instantiateNew(String className, Object argValue) {
    return instantiateNew(className, new Object[] { argValue });
  }

  public static Object instantiateNew(String className, Object[] argValues) {
    setTheClass(className);

    try {
      // create an arg list that identifies the constructor
      Class[] theClassMethodArgs = objects2ClassArray(argValues);

      //  Locate the constructor identified by the provided arguement signature
      Constructor theConstructor = null;
      theConstructor = theClass.getConstructor(theClassMethodArgs);

      // Create an object from the qualified class
      setTheObject(theConstructor.newInstance(argValues));
    }
    catch (Exception e) {
      e.printStackTrace(System.err);
    }

    return getTheObject();
  }

  public static Object callMethod(String method, Object argValue) {
    return callMethod(method, new Object[] { argValue });
  }

  public static Object callMethod(String method, Object[] argValues) {
    Object returnObject = null;

    try {
      // create an arg list that identifies the method
      Class[] theClassMethodArgs = objects2ClassArray(argValues);
      //  locate the method identified by the method name & arg type signiture
      //  (an exception will occur if no method that takes such args exists for the class)
      Method theMethod = null;
      boolean isMethodAbstract;

      theMethod = theClass.getMethod(method, theClassMethodArgs);

      isMethodAbstract = Modifier.isAbstract(theMethod.getModifiers());

      if (isMethodAbstract) {
        System.out.println("ObjectFactory.callMethod: method is abstract");
        return null;
      }

      // we now have the object/class the method and the args
      returnObject = theMethod.invoke(theObject, argValues);
    }
    catch (Exception e) {
      e.printStackTrace(System.err);
    }

    return returnObject;
  }

  private static Class[] objects2ClassArray(Object[] objects) {
    int argCnt = objects.length;

    Class[] classArray = new Class[argCnt];

    try {
      for(int i = 0; i < argCnt; i++) {
        classArray[i] = Class.forName(objects[i].getClass().getName());
      }
    }
    catch (Exception e) {
      e.printStackTrace(System.err);
    }

    return classArray;
  }
}