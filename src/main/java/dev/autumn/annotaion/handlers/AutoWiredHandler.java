package dev.autumn.annotaion.handlers;

import java.lang.reflect.Field;
import java.util.Map;

import dev.autumn.annotaion.AnnotationHandler;
import dev.autumn.annotaion.Autowired;
import dev.autumn.annotaion.MarkedAnnotationHandler;
import dev.autumn.annotaion.OutputContext;
import dev.autumn.parser.AutumnResultParsing;
import dev.example.common.maintenance.OutputDescriptor;

@MarkedAnnotationHandler
public class AutoWiredHandler implements AnnotationHandler {

	Map<String, Class<?>> nodes; 
	public AutoWiredHandler(Map<String, Class<?>> nodes) {
		this.nodes = nodes;
	}


	public void handler(Class<?> c, Object obj, AutumnResultParsing res) throws IllegalArgumentException, IllegalAccessException, InstantiationException {

		for(Field f : c.getDeclaredFields()) {
			Autowired a = (Autowired)f.getAnnotation(Autowired.class);
			if(a != null) {
				Class autowired = null;
				String cont = res.getVarContext(OutputContext.class.getSimpleName());
				if(cont != null) {
					autowired = nodes.get(cont);
				}
				else if(a.value().equals("auto")) {
					autowired = null;

					for(Class cand : nodes.values()) {
						if(cand == f.getType() || f.getType().isAssignableFrom(cand)) {
							autowired = cand;
							break;
						}
					}

				}
				else {
					autowired = nodes.get(a.value());
				}

				f.setAccessible(true);
				
				f.set(obj, autowired.newInstance());
				f.setAccessible(false);
			}
		}			
	}
}
