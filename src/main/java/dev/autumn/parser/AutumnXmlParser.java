package dev.autumn.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

//import org.apache.log4j.Logger;
//import org.apache.log4j.Logger;
//import java.util.logging.*;



import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import dev.autumn.Autumn;





public class AutumnXmlParser extends DefaultHandler {
	
	
	/* put in configure 4 AutumnXmlParser?   */
	private final String availableCompName = "available_components";
		private final String namePropAvailablePackage = "av_package";

	
	private final String nodeName = "node";
		private final String namePropIdNode = "id";
		private final String namePropClassNode = "class";
	
	
	private final String nameVarContext = "var_context";
		private final String namePropNameVarContext = "name";
		private final String namePropIdNodeVarContext = "id_node";
	
	private final String nameProperty = "property";
		private final String namePropNameProp = "name";
		private final String namePropNameValue = "value";
	
	
	/**/
	private List<AutumnResultParsing> results;
	private String availablePath4Scan;
	
	public AutumnXmlParser() {
		super();
		availablePath4Scan = null;
		logger.setLevel(Level.OFF);
	}
	
	public void parseSettings(String xmlPath) throws Exception {
		results = new ArrayList<AutumnResultParsing>();		
		
		try {
			XMLReader xr = XMLReaderFactory.createXMLReader();
			FileReader r = new FileReader(xmlPath);

			xr.setContentHandler(this);
			xr.setErrorHandler(this);
			xr.parse(new InputSource(r));

			logger.log(Level.WARNING, "parseSettingsEnd");
		}
		catch(SAXException ex) {
			ex.printStackTrace();
		}
		catch(FileNotFoundException ex) {
			ex.printStackTrace();
			throw new Exception("error parse xml configuration.");
		}
		catch(IOException ex) {
			ex.printStackTrace();
			throw new Exception("error parse xml configuration.");
		}
		
	}
	
	
	public void startDocument ()
	{
		logger.log(Level.CONFIG, "Start document");
	}
	
	@Override
	public void endDocument ()
	{
		logger.log(Level.WARNING, nodeName + ": " + "");
	}
	
	@Override
	public void startElement(String uri, String name, String qName, Attributes atts) {
		if ("".equals (uri)) {
			logger.log(Level.WARNING, "Start element: " + qName);
			
			if(qName.equals(nodeName)) {
				String id = atts.getValue(namePropIdNode);
				String classPath = atts.getValue(namePropClassNode);
				
				logger.log(Level.WARNING, nodeName + ": " + namePropIdNode + "=" + id 
						+ "  " + namePropClassNode + "=" + classPath);
				
				results.add(new AutumnResultParsing(id, classPath));
			}
			else if(qName.equals(nameVarContext)) {
				String nameVar = atts.getValue(namePropNameVarContext);
				String idNode = atts.getValue(namePropIdNodeVarContext);
				
				results.get(results.size()-1).addVarContext(nameVar, idNode);
				logger.log(Level.WARNING, nameVarContext + ": " + namePropNameVarContext + "=" + nameVar 
						+ namePropIdNodeVarContext + "=" + idNode);
			}
			else if(qName.equals(nameProperty)) {
				String nameProperty = atts.getValue(namePropNameProp);
				String valueProperty = atts.getValue(namePropNameValue);
				
				results.get(results.size() - 1).addProperty(nameProperty, valueProperty);				
				logger.log(Level.WARNING, nameProperty + ": " + namePropNameProp + "=" + nameProperty 
						+ namePropNameValue + "=" + valueProperty);
			}
			else if(qName.equals(availableCompName)) {
				availablePath4Scan = atts.getValue(namePropAvailablePackage);
				
				logger.log(Level.WARNING, namePropAvailablePackage + ": " + availablePath4Scan);
			}
		}
		else {
			logger.log(Level.WARNING, "Start element: {" + uri + "}" + name);
		}
	}
	
	@Override
	public void endElement(String uri, String name, String qName) {
		if ("".equals (uri)) {
			logger.log(Level.WARNING, "End element: " + qName);
		}
		else {
			logger.log(Level.WARNING, "End element:   {" + uri + "}" + name);
		}
		
	}
	
	@Override
	public void characters (char ch[], int start, int length)
	{
		//System.out.print("Characters:    \"");
		/*for (int i = start; i < start + length; i++) {
			switch (ch[i]) {
			case '\\':
				System.out.print("\\\\");
				break;
			case '"':
				System.out.print("\\\"");
				break;
			case '\n':
				System.out.print("\\n");
				break;
			case '\r':
				System.out.print("\\r");
				break;
			case '\t':
				System.out.print("\\t");
				break;
			default:
				System.out.print(ch[i]);
				break;
			}
		}*/
		//System.out.print("\"\n");
	}
	
	public String getAvailablePath4Scan() {
		return availablePath4Scan;
	}
	
	public List<AutumnResultParsing> getResults() {
		return results;
	}
	
	public AutumnResultParsing getResultParsing(String idNodes) throws Exception {
		for(AutumnResultParsing a : results) {
			if(a.getIdNode().equals(idNodes)) {
				return a;
			}
		}
		throw new Exception(idNodes);
	}
	
	public void addResult(String idNodes, String availablePath) {
		this.results.add(new AutumnResultParsing(idNodes, availablePath));
	}
	
	//TODO: very bad, change resutl from list to map, but this need very time and changing architecture:-)
	public String getProperty(String nameNode, String nameProp) throws Exception {
		for(AutumnResultParsing a : this.results) {
			if(a.getIdNode().equals(nameNode))
				return a.getProperty(nameProp);
		}		
		throw new Exception("node not found!");		
	}
	
	
	private Class[] nodes;
	
	private static final Logger logger = Logger.getLogger(AutumnXmlParser.class.getName());
	
	//private static final Logger logger = Logger.getLogger(AutumnXmlParser.class);
	
	public static void main(String [] args) throws Exception {
		System.out.println("Example AutumnXmlParser");
		
		AutumnXmlParser au = new AutumnXmlParser();
		au.parseSettings("/home/serg/workspace_spr/AutumnProject/src/main/resources/example_nodes.xml");
		
	}
	
	
	
	
	
	
}



