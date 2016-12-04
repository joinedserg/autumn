package dev.autumn;

import java.util.*;

import dev.autumn.parser.AutumnResultParsing;
import dev.autumn.parser.AutumnXmlParser;
import dev.autumn.annotaion.AnnotationHandler;
import dev.autumn.annotaion.Component;
import dev.autumn.annotaion.handlers.AutoInjectHandler;
import dev.autumn.annotaion.handlers.OutputContextHandler;
import dev.autumn.annotaion.handlers.ValueHandler;
import dev.autumn.finder.ClassFinder;
import dev.example.main.*;

public class Autumn {

	AutumnXmlParser xmlParser;
	Map<String, Class<?>> nodes;
	List<AnnotationHandler> handlers;
	
	public Autumn(String xmlPath) throws Exception {
		xmlParser = new AutumnXmlParser();
		xmlParser.parseSettings(xmlPath);
				
		String avPath4Search = xmlParser.getAvailablePath4Scan();
		nodes = new HashMap<String, Class<?>>();
		
		if(avPath4Search != null) {
			nodes = ClassFinder.getMarkedClassesFromPackage(avPath4Search, Component.class);
			
			for(String k : nodes.keySet()) {				
				Class c = nodes.get(k);
				xmlParser.addResult(k, c.getName());
			}
		}
		for(AutumnResultParsing res : xmlParser.getResults()) {			
			Class c = Class.forName(res.getAvailablePath());
			nodes.put(res.getIdNode(), c);
		}
		
		//after apply classfinder		
		handlers = new ArrayList<AnnotationHandler>();
		handlers.add(new ValueHandler());
		handlers.add(new OutputContextHandler(nodes));
		
		
		//handlers.add(new AutoInjectHandler());		
	}
	
	public Object getNode(String idNode) throws InstantiationException, IllegalAccessException, NullPointerException {
		Class c = nodes.get(idNode);		
		Object obj = c.newInstance();
		
		for(AnnotationHandler an : handlers) {
			try {
				an.handler(c, obj, xmlParser.getResultParsing(idNode));
			}
			catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		return obj;
	}
	
	public static void main(String [] args) throws Exception {
		System.out.println("Autumn 0.0.0.1");
		
		Autumn autumn = new Autumn("/home/serg/workspace_spr/AutumnProject/target/classes/example_nodes.xml");
		
		for(String name : autumn.nodes.keySet()) {
			System.out.println(name);
			
			Object o = autumn.getNode(name);
			if(o.getClass() == MainContext.class) {
				((MainContext)o).hey();
			}
		}
		
		
		/*for(Class<?> c : autumn.nodes.values()) {
			
			if(c == MainContext.class) {
				System.out.println("MainContext.class");
				MainContext m = (MainContext)c.newInstance();
				m.hey();
			}
		}*/
		
	}
	
}
