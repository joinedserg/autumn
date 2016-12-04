package dev.autumn.parser;

import java.util.LinkedHashMap;
import java.util.List;


public class AutumnResultParsing {

	private String idNode;
	private String availablePath;
	
	private LinkedHashMap<String, String> varContext;
	private LinkedHashMap<String, String> properties;
	
	
	public AutumnResultParsing() {
		varContext = new LinkedHashMap<String, String>();
		properties = new LinkedHashMap<String, String>();
	}
	
	public AutumnResultParsing(String idNode, String classPath) {
		this.idNode = idNode;
		this.availablePath = classPath;
		varContext = new LinkedHashMap<String, String>();
		properties = new LinkedHashMap<String, String>();
	}
	
	public String getIdNode() {
		return this.idNode;
	}
	
	public String getAvailablePath() {
		return this.availablePath;
	}
	
	public void setAvailablePath(String availablePath) {
		this.availablePath = availablePath;
	}
	
	public void setIdNode(String idNode) {
		this.idNode = idNode;
	}
	
	public void addVarContext(String name, String value) {
		this.varContext.put(name, value);
	}
	
	public void addProperty(String name, String value) {
		this.properties.put(name, value);
	}
	
	public String getProperty(String name) {
		return this.properties.get(name);
	}
	
	public String getVarContext(String name) {
		return this.varContext.get(name);
	}
	
}
