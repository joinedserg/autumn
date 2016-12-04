package dev.autumn.annotaion;

import dev.autumn.parser.AutumnResultParsing;

public interface AnnotationHandler {
	
	public void handler(Class<?> c, Object obj, AutumnResultParsing res) throws IllegalArgumentException, IllegalAccessException, InstantiationException;
}
