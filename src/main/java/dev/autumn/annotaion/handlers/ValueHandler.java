package dev.autumn.annotaion.handlers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import dev.autumn.annotaion.*;
import dev.autumn.parser.AutumnResultParsing;


public class ValueHandler implements AnnotationHandler {

	
	public void handler(Class<?> c, Object obj, AutumnResultParsing res)throws IllegalArgumentException, IllegalAccessException  {

		Field[] fields = c.getDeclaredFields();
		
		for(Field f : fields) {
			Value a = (Value)f.getAnnotation(Value.class);
			if(a != null) {
				String nameProp = a.value().substring(2, a.value().length()-1);
				String prop = res.getProperty(nameProp);
				Class type = f.getType();
				if(prop == null) {
					if(type == Integer.class) {
						prop = "0";
					}
					else {
						prop = "not found";
					}
					
				}
				
				f.setAccessible(true);
				
				
				
				if(type == Integer.class) {
					f.set(obj, Integer.parseInt(prop));
				}
				else {
					f.set(obj, prop);
				}
				
				f.setAccessible(false);
			}	
		}				
	}
	
}
