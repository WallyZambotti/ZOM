package com.byotrader.zom;

import java.util.Vector;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import  org.apache.xerces.dom.DocumentImpl;
import  org.apache.xerces.dom.DOMImplementationImpl;
import  org.apache.xml.serialize.OutputFormat;
import  org.apache.xml.serialize.Serializer;
import  org.apache.xml.serialize.SerializerFactory;
import  org.apache.xml.serialize.XMLSerializer;
import  org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xpath.XPathAPI;
import org.apache.xpath.XPathException;
import org.xml.sax.SAXParseException;

import java.text.SimpleDateFormat; //temp
import java.util.GregorianCalendar;

/**
 * Title:        ZOM Share Calculator
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      BYO Trader
 * @author Walter Zambotti
 * @version 1.0
 */

public abstract class XMLDataModel {
  protected static final int PREFERENCE_CNT = 8;

  protected static final int HEADING = 0;
  protected static final int DISPLAY_NAME = 1;
  protected static final int COLUMN_ORDER = 2;
  protected static final int MODEL_ORDER = 3;
  protected static final int TOOL_TIP = 4;
  protected static final int TYPE = 5;
  protected static final int EDITABLE = 6;
  protected static final int DERIVED = 7;

  protected int columnCnt = 0;
  private File xmlFile;
  protected Document xmlDocument;
  protected XPathAPI xPath;
  private String rootDataNodeName;
  private String rootNodeName;
  protected String dataNodeName;
  protected Element rootDataNode;
  protected Element rootNode;
  protected double zomVersion;
  protected int maxCount = 0;

  private Element nameSpaceNode;

  protected String[] preferenceTags = {
    "Heading", "DisplayName", "ColumnOrder", "ModelOrder",
    "ToolTip", "Type", "Editable", "Derived"
  };

  protected String[] preferenceType = {
    "java.lang.String", "java.lang.String", "java.lang.Integer", "java.lang.Integer",
    "java.lang.String", "java.lang.String", "java.lang.Boolean", "java.lang.Boolean"
  };

  protected String[][] preferenceDefaults;

  public Vector RowData;
  public Vector ColumnNames;
  public Vector ColumnPrefs;

  public boolean[] ColumnEditable;
  public String[] ColumnToolTip;

  public Document getXMLdocument() {
    return xmlDocument;
  }

  public String getXMLfilePath() {
    return xmlFile.getPath();
  }

  public void setXMLfilePath(String xmlFilePath) {
    String tmpPath = xmlFilePath.trim();

    if (tmpPath.compareTo("") == 0) {
      System.out.println("XMLDataModel.setXMLFilePath: provided path is blank.");
      return;
    }

    xmlFile = new File(tmpPath);
  }

  public int getColumnCnt() {
    return this.columnCnt;
  }

  public void setColumnCnt(int cnt) {
    this.columnCnt = cnt;
  }

  public String getRootDataNodeName() {
    return this.rootDataNodeName;
  }

  public void setRootDataNodeName(String newName) {
    this.rootDataNodeName = newName;
  }

  public String getRootNodeName() {
    return this.rootNodeName;
  }

  public void setRootNodeName(String newName) {
    this.rootNodeName = newName;
  }

  public String getDataNodeName() {
    return this.dataNodeName;
  }

  public void setDataNodeName(String newName) {
    this.dataNodeName = newName;
  }

  public String[][] getPreferenceDefaults() {
    return this.preferenceDefaults;
  }

  public void setPreferenceDefaults(String[][] newDefaults) {
    this.preferenceDefaults = newDefaults;
  }

  public XMLDataModel() {
  }

  public XMLDataModel(
    String xmlFilePath,
    int cnt,
    String rootName,
    String rootDataName,
    String dataName,
    String[][] prefDefaults) throws Exception {

    this.setColumnCnt(cnt);
    this.setXMLfilePath(xmlFilePath);
    this.setRootNodeName(rootName);
    this.setRootDataNodeName(rootDataName);
    this.setDataNodeName(dataName);
    this.setPreferenceDefaults(prefDefaults);

    try {
        // Ensure there is a document to parse
        parseCreateXML();

        // Create a namespace node to define a namespace to be used for locating Zom XPath extension functions
        this.nameSpaceNode = this.xmlDocument.createElement("NameSpace");
        this.nameSpaceNode.setAttribute("xmlns:String", "xalan://java:java.util.String");
     } catch (Exception e) {
        System.out.println("XMLDataModel.XMLDataModel: could not parse XML file");
        throw e;
    }
 }

  public void loadXML(int freq) {
    // Only one XPathAPI is ever needed so allocate it here
    if (xPath == null) {
      xPath = new XPathAPI();
    }
    else {
      System.out.println("XMLDataModel.loadXML: unexpected re-entry");
    }

    // Enforce the contract

    // Columns Need to be defined
    if (columnCnt < 1) {
      System.out.println("XMLDataModel.LoadData: no specified column count.");
      return;
    }

    // Allocate some resources
    RowData = new Vector(10, 10);
    ColumnNames = new Vector(columnCnt);
    ColumnPrefs = new Vector(columnCnt);

    ColumnEditable = new boolean[columnCnt];
    ColumnToolTip = new String[columnCnt];

    try {
        // Ensure there is a document to parse
        loadColumns();
        loadData(freq);
    } catch (Exception e) {
        e.printStackTrace(System.err);
    }
  }

  private void loadColumns() {
    NodeList columnElements = this.xmlDocument.getElementsByTagName("Column");

    // is there anything to do?
    if (columnElements == null) {
        return;
    }

    // There should be COLUMN_CNT sets of column preferences
    int columnCount = columnElements.getLength();

    if (columnCount != this.columnCnt) {
      System.out.println("XMLDataModel.LoadColumns: specified column count does not match XML file column count.");
      return;
    }

    // Fill with dummy items so that later inserts don't right past the end of the vectors
    for (int i = 0; i < columnCount; i++) {
      ColumnPrefs.add(null);
      ColumnNames.add(null);
    }

    // process each column's preferences
    for (int i = 0; i < columnCount; i++) {
      // get the nth column preference (at this point could be out of order)
      Element element = (Element)columnElements.item(i);
      // get the preferences (XML attributes) for that column
      NamedNodeMap attributes = element.getAttributes();

      // there should be PREFERENCE_CNT preferences
      int preferenceCount = attributes.getLength();

      if (preferenceCount != PREFERENCE_CNT) {
        return;
      }

      Attr attrPreference;
      String preferenceValue;
      // Create the vector tocontain
      Vector rowVector = new Vector(PREFERENCE_CNT);

      // Iterate the XML attributes (preferences)
      for (int j = 0; j < preferenceCount; j++) {
        // get the XML preference tag name
        String preference = preferenceTags[j];
        // get the named XML tag from the attributes
        attrPreference = (Attr)attributes.getNamedItem(preference);

        if (attrPreference == null) {
          if (preference.compareTo("Init") != 0 && preference.compareTo("CallOut") != 0) {
            System.out.print("XMLDataModel.LoadColumns: Cant find attribute " + preference);
            // System.out.println(preference);
            return;
          }

          // add one NULL preference to the row of the preferences
          rowVector.add(null);
        }
        else {
          // enumerate the value of the preference
          preferenceValue = attrPreference.getNodeValue();

          // add one preference to the row of the preferences
          rowVector.add(ObjectFactory.instantiateNew(preferenceType[j], preferenceValue));
        }
      }

      // The column index is obtained from the XML file
      Integer colIndex = (Integer)rowVector.get(COLUMN_ORDER);
      int columnIndex = colIndex.intValue();

      // In correct column order save all the preference values in the Column Data vector
      ColumnPrefs.set(columnIndex, rowVector);

      // In correct column order name the column header
      ColumnNames.set(columnIndex, rowVector.get(DISPLAY_NAME));

      // In correct column order set the column tooltip
      ColumnToolTip[columnIndex] = (String)rowVector.get(TOOL_TIP);

      // In correct column order set whether column editable
      Boolean editable = (Boolean)rowVector.get(EDITABLE);
      ColumnEditable[columnIndex] = editable.booleanValue();
    }
  }

  protected void loadData(int freq) {
    if (freq < 1) {
      System.out.println("XMLDataModel.loadData: Frequency is invalid");
      return;
    }

    // Clear Any Previous Rows
    RowData.clear();

    // get the root Data element
    NodeList rootDataElements = this.xmlDocument.getElementsByTagName(this.rootDataNodeName);

    // Better not be missing
    if (rootDataElements == null) {
      System.out.println("XMLDataModel.loadData: missing root data node");
      return;
    }

    if (rootDataElements.getLength() != 1) {
      System.out.println("XMLDataModel.loadData: more than one root data nodes");
      return;
    }

    this.rootDataNode = (Element)rootDataElements.item(0);

    // get the Data elements that match
    NodeList elements = this.xmlDocument.getElementsByTagName(this.dataNodeName);

    // is there anything to do?
    if (elements == null) {
        return;
    }

    // how many Data elements are we dealing with
    int elementCount = elements.getLength();
    if (!ZomString.isAnsiStrings()) {
      if (this.maxCount != 0) {
        if (elementCount > this.maxCount) {
          elementCount = this.maxCount;
        }
      }
    }

    // iterate through all the Data elements
    for (int i = 0; i < elementCount; i+=freq) {
      // get the nth Data element
      Element element = (Element)elements.item(i);
      // get all the Data column values (attributes)
      NamedNodeMap attributes = element.getAttributes();

      Attr tmpAttribute;
      String dataAttr, colClass;

      // Create the data model row vector
      Vector rowVector = new Vector(columnCnt);

      // iterate through all the Data attributes
      for(int j = 0; j < columnCnt; j++) {
        // If the column is derived then no data exists in the xml file so skip processing
        Boolean derivedColumn = (Boolean)getColValueVector(j, DERIVED);

        if (!derivedColumn.booleanValue()) {
          // get the attribute identified by the heading preference of the column data
          tmpAttribute = (Attr)attributes.getNamedItem((String)getColValueVector(j, HEADING));

          if(tmpAttribute == null) {
            System.out.println("XMLDataModel.LoadData: Cant find attribute " + (String)getColValueVector(j, HEADING));
            // System.out.println((String)getColValueVector(j, HEADING));
          }

          // enumerate the data attribute
          try {
            dataAttr = tmpAttribute.getNodeValue();
            colClass = (String)getColValueVector(j, TYPE);

            // add one attribute to the row of the data attributes (column values) to the portfolio table
            rowVector.add(ObjectFactory.instantiateNew(colClass, dataAttr));
          }
          catch (Exception ex) {
            // Column is not derived but contains no value, instead add a null value
            rowVector.add(null);
          }
        }
        else {
          // Column is derived add a null value
          rowVector.add(null);
        }
      }

      // Provide an opertunity for sub classes to manipulate the data prior to addition to the table
      this.addingDataRow(rowVector);
      // add one row of the data attributes (column values) to the data table
      RowData.add(rowVector);
    }
  }
  abstract void addingDataRow(Vector rowData) ;

  public void parseCreateXML() {
    // Have to have an XML file
    if (xmlFile == null) {
      System.out.println("XMLDataModel.parseCreateXML: no specified XML file.");
      return;
    }

    if (xmlFile.exists()) {
      // Set up a DOM tree to query.
      if (!this.cleanParseXMLfile()) {
        System.out.println("XMLDataModel.parseCreateXML: could not load XML file - " + xmlFile.getAbsolutePath());
        return;
      }

      this.rootNode = this.xmlDocument.getDocumentElement();
      this.zomVersion = new Double(this.rootNode.getAttribute("Version")).doubleValue();
      return;
    }

    try {
      // XML File doesn't exist so build it
      this.xmlDocument = new DocumentImpl();

      // Create Root Element
      Element root = this.xmlDocument.createElement(this.rootNodeName);

      // Append Version attribute to root element
      root.setAttribute("Version", "1.1");

      // Append Columns element to root
      Element colsItem = this.xmlDocument.createElement("Columns");
      root.appendChild(colsItem);

      // Append Column Count attribute to Columns elelemnt
      colsItem.setAttribute("Count", String.valueOf(this.columnCnt));

      // Create all the Columns
      for(int col = 0; col < this.columnCnt; col++) {
        Element colItem = this.xmlDocument.createElement("Column");
        colsItem.appendChild(colItem);

        // Create all the Preferences for each column
        for(int pref = 0; pref < this.PREFERENCE_CNT; pref++) {
          String prefValue = this.preferenceDefaults[col][pref];

          if (prefValue != null) {
            colItem.setAttribute(this.preferenceTags[pref], prefValue);
          }
          else {
            colItem.setAttribute("No_" + this.preferenceTags[pref], "");
          }
        }
      }

      // Append Root Data element to root
      this.rootDataNode = this.xmlDocument.createElement(this.rootDataNodeName);
      root.appendChild(this.rootDataNode);

      // Add Root to Document
      this.xmlDocument.appendChild(root);
      this.rootNode = root;

      // Save the XML structure to a file
      this.saveXML();

    } catch (Exception ex) {
        ex.printStackTrace();
    }
  }

  // this function attempts to parse the xml file.  It it fails it assumes the xml file is badly formed
  // and attempts to truncate the file at the end of the root node element.

  private boolean cleanParseXMLfile() {
    int parseAttemptCnt = 0;

    // The clean parse may require more than one attempt (so loop)
    do {
      parseAttemptCnt++;

      //  attempt to parse the xml file if this work we shall return
      try {
        this.xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(
          new InputSource(new FileInputStream(xmlFile)));

        // (Can only get to this coed if parse has wortked) if this has worked on attempt 2 indicate recovery
        if (parseAttemptCnt == 2) {
          System.out.println("XMLDataModel.cleanParseXMLfile: Parse recovered!");
        }

        return true;
      }
      catch (SAXParseException ex) {
        // Can only get to this code if a parse failure has occured
        System.out.println("XMLDataModel.cleanParseXMLfile: Parse exception");

        // after the second attempt indicate a failure and return
        if (parseAttemptCnt == 2) {
          System.out.println("XMLDataModel.cleanParseXMLfile: Parse failure");
          return false;
        }

        // Before munjing the xml file make a backup copy of it
        try {
          this.copyFile(this.xmlFile, this.xmlFile.getAbsolutePath() + ".bak");
        }
        catch (Exception ex2) {
          // Cant even make a backup copy, better abort
          System.out.println("XMLDataModel.cleanParseXMLfile: failed to backup.  Aborting cleanup!");
          return false;
        }

        // Open the XML file as a plain random file
        RandomAccessFile xmlRAF;
        long xmlLen;

        try {
          xmlRAF = new RandomAccessFile(this.xmlFile, "rw");
          xmlLen = xmlRAF.length(); // Length of file
        }
        catch (Exception ex2) {
          // cant even plainly open the XML file
          return false;
        }

        // define the target as the root node closing element
        String searchTarget = "</"  + this.rootNodeName + ">";
        String fileTarget;
        boolean matched = false;
        int targetLen = searchTarget.length();
        byte[] buffer = new byte[targetLen];
        long offset;

        // seek from the end of file - one towards the beginning of
        for (offset = xmlLen - targetLen - 1; offset > targetLen ; offset--) {
          try {
            xmlRAF.seek(offset);
            xmlRAF.read(buffer, 0, targetLen);
          }
          catch (Exception ex2) {
            return false;
          }

          // grab a string from the file equal in length to the
          fileTarget = new String(buffer, 0, targetLen);

          if (fileTarget.compareTo(searchTarget) == 0) {
            // Found the target so truncate the file
            offset += targetLen;
            try {
              xmlRAF.setLength((long)offset);
              xmlRAF.close();
            }
            catch (Exception ex2) {
              // failed to truncate the file just abort
              return false;
            }

            matched = true;
            break;
          }
        }

        if (!matched) {
          System.out.println("XMLDataModel.cleanParseXMLfile: failed to recover XML file.");
          return false;
        }
      }
      catch (Exception ex) {
        ex.printStackTrace();
        return false;
      }
    }
    while (parseAttemptCnt < 2);

    return false;
  }

  public void copyFile(File inFile, String outFileName) throws IOException {
    File outFile = new File(outFileName);

    InputStream in = new FileInputStream(inFile);
    OutputStream out = new FileOutputStream(outFile);

    int ch;
    while((ch = in.read()) != -1) {
      out.write(ch);
    }

    in.close();
    out.close();
  }

  public void saveXML() {
    if (this.xmlFile == null) {
      System.out.println("XMLDataModel.saveXML: missing xmlFile");
      return;
    }

    try {
        // Spit out DOM
        FileOutputStream xmlStream = new FileOutputStream(this.xmlFile);
        OutputFormat    format  = new OutputFormat(this.xmlDocument);     //Serialize DOM
        XMLSerializer    serial = new XMLSerializer(xmlStream, format);
        serial.asDOMSerializer();                            // As a DOM Serializer
        serial.serialize(this.xmlDocument.getDocumentElement());
        xmlStream.close();
    } catch (Exception ex) {
        ex.printStackTrace();
    }
  }

  public void saveNode(Object[] nodeAttrs) {
    //  null means append to the insertNoed method
    this.insertNode(null, nodeAttrs);
  }

  public void saveNode(String key, Object[] nodeAttrs) {
    // Locate the node and pass it to insertNode
    // if the find return null it will insert will treat this as an append
    this.insertNode(this.locateGTnode(key), nodeAttrs);
  }

  public void appendNode(Object[] nodeAttrs) {
    // Create Data Element and append it to the root Data Node
    Element dataElement = this.xmlDocument.createElement(this.dataNodeName);
    this.rootDataNode.appendChild(dataElement);

    // Append data attributes to data element
    for(int iAttr = 0; iAttr < this.columnCnt; iAttr++) {
      if (!((Boolean)getColValueVector(iAttr, DERIVED)).booleanValue()) {
        dataElement.setAttribute(
          (String)getColValueVector(iAttr, HEADING),
          ((ZomType)nodeAttrs[iAttr]).ioString());
      }
    }
  }

  public void insertNode(Node nextNode, Object[] nodeAttrs) {
    // Create Data Element and insert it before the provided node
    Element dataElement = this.xmlDocument.createElement(this.dataNodeName);
    this.rootDataNode.insertBefore((Node)dataElement, nextNode);

    // Append data attributes to data element
    for(int iAttr = 0; iAttr < this.columnCnt; iAttr++) {
      if (!((Boolean)getColValueVector(iAttr, DERIVED)).booleanValue()) {
        dataElement.setAttribute(
          (String)getColValueVector(iAttr, HEADING),
          ((ZomType)nodeAttrs[iAttr]).ioString());
      }
    }
  }

  public void updateNode(String xPathExp, Object[] nodeAttrs) throws XPathException, Exception {
    NodeList matchingNodes;

    try {
      matchingNodes = xPath.selectNodeList(this.rootDataNode, xPathExp);
    }
    catch (Exception ex) {
      System.out.println("XMLDataModel.updateNode: invalid XPath specification.");
      throw new XPathException("XMLDataModel.updateNode: invalid xpath: " + xPathExp);
    }

    if (matchingNodes.getLength() != 1) {
      System.out.println("XMLDataModel.updateNode: data node not unique.");
      throw new Exception("XMLDataModel.updateNode: data node not unique.");
    }

    Element dataElement = (Element)matchingNodes.item(0);

    // Append data attributes to data element
    for(int iAttr = 0; iAttr < this.columnCnt; iAttr++) {
      if (!((Boolean)getColValueVector(iAttr, DERIVED)).booleanValue() && nodeAttrs[iAttr] != null) {
        dataElement.setAttribute(
          (String)getColValueVector(iAttr, HEADING),
          ((ZomType)nodeAttrs[iAttr]).ioString());
      }
    }
  }

  public void deleteNode(String xPathExp) throws XPathException, Exception {
    NodeList matchingNodes;

    try {
      matchingNodes = xPath.selectNodeList(this.rootDataNode, xPathExp);
    }
    catch (Exception ex) {
      System.out.println("XMLDataModel.deleteNode: invalid XPath specification.");
      throw new XPathException("XMLDataModel.deleteNode: invalid xpath: " + xPathExp);
    }

    if (matchingNodes.getLength() != 1) {
      System.out.println("XMLDataModel.deleteNode: data node not unique.");
      throw new Exception("XMLDataModel.deleteNode: data node not unique.");
    }

    Node targetNode = matchingNodes.item(0);

    this.rootDataNode.removeChild(targetNode);

    return;
  }

  public void deleteAllNodes() {
    if (this.rootNode == null) {
      return;
    }

    if (this.rootDataNode == null) {
      return;
    }

    // Delete all nodes by taking out the root data node
    this.rootNode.removeChild(this.rootDataNode);

    // Now put the root data node back
    this.rootDataNode = this.xmlDocument.createElement(this.rootDataNodeName);
    this.rootNode.appendChild(this.rootDataNode);
  }

  public boolean existsNode(String xPathExp) {
    NodeList matchingNodes;

    try {
      matchingNodes = xPath.selectNodeList(this.rootDataNode, xPathExp);
    }
    catch (Exception ex) {
      return false;
    }

    return (matchingNodes.getLength() > 0);
  }

  public Node findNode(String xPathExp) {
    NodeList matchingNodes;
    try {
      matchingNodes = xPath.selectNodeList(this.rootDataNode, xPathExp, this.nameSpaceNode);
    }
    catch (Exception ex) {
      return null;
    }

    return (matchingNodes.getLength() > 0) ? matchingNodes.item(0) : null;
  }

  public abstract Node locateGTnode(String xPathExp);

  public Object getColValueVector(int rowIndex, int preference) {
    return ((Vector)ColumnPrefs.get(rowIndex)).get(preference);
  }

  public Vector getRowVector(int rowIndex) {
    return (Vector)RowData.get(rowIndex);
  }

  public double calcInterest(ZomDate primoDate, ZomDate finalDate, double primoPrice, double intRate) {
    double appreciatedVal = primoPrice;
    GregorianCalendar incDate = new GregorianCalendar();
    GregorianCalendar endDate = new GregorianCalendar();

    incDate.setTime(primoDate.getDate());
    endDate.setTime(finalDate.getDate());

    while (incDate.before(endDate)) {
      int yearLen = incDate.getActualMaximum(GregorianCalendar.DAY_OF_YEAR);
      appreciatedVal += (double)((long)((appreciatedVal * intRate / yearLen)*100+0.5)*.01);
      incDate.add(GregorianCalendar.DATE, 1);
    }

    return appreciatedVal - primoPrice;
  }
}