package dev.autumn.annotaion.handlers;

import java.lang.reflect.Field;
import java.util.Map;

import dev.autumn.annotaion.*;
import dev.autumn.parser.AutumnResultParsing;
import dev.example.common.maintenance.OutputDescriptor;

@MarkedAnnotationHandler
public class OutputContextHandler implements AnnotationHandler {

	Map<String, Class<?>> nodes; 
	public OutputContextHandler(Map<String, Class<?>> nodes) {
		this.nodes = nodes;
	}
	
	public void handler(Class<?> c, Object obj, AutumnResultParsing res) throws IllegalArgumentException, IllegalAccessException, InstantiationException {

		Field[] fields = c.getDeclaredFields();
		for(Field f : fields) {
			OutputContext a = (OutputContext)f.getAnnotation(OutputContext.class);
			if(a != null) {
				Class outputContext = null;
				
				String cont = res.getVarContext(OutputContext.class.getSimpleName());
				if(cont != null) {
					outputContext = nodes.get(cont);
				}
				else if(a.value().equals("auto")) {
					outputContext = null;
					
					for(Class cand : nodes.values()) {
						if(cand == f.getType() || f.getType().isAssignableFrom(cand)) {
							outputContext = cand;
							break;
						}
					}
					
				}
				else {
					outputContext = nodes.get(a.value());
				}
				
				f.setAccessible(true);
				f.set(obj, (OutputDescriptor)outputContext.newInstance());
				f.setAccessible(false);
			}	
		}				
	}
}
