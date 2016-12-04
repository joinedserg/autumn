package dev.autumn.annotaion.handlers;

import java.lang.reflect.Field;

import dev.autumn.annotaion.*;
import dev.autumn.parser.AutumnResultParsing;

@MarkedAnnotationHandler
public class AutoInjectHandler implements AnnotationHandler {

	public void handler(Class<?> c, Object obj, AutumnResultParsing res) {
		
		Field[] fields = c.getFields();
		for(Field f : fields) {
			if(f.getAnnotation(AutoInject.class) != null) {
				
				
				
			}	
		}				
	}
	
	
}
