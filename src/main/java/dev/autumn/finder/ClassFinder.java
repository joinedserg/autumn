package dev.autumn.finder;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.*;

import dev.Separator;
import dev.autumn.annotaion.*;


@MarkedAnnotationHandler
public class ClassFinder {
	
	
	public static Map<String, Class<?>> getMarkedClassesFromPackage(String packageName, Class<? extends Component> annotation) {
		Map<String, Class<?>> mapClasses = new HashMap();
		
		String path = packageName.replace('.', Separator.getSep());
		path = ClassLoader.getSystemClassLoader().getResource(path).getPath();
		mapClasses = find(new File(path), mapClasses, annotation, packageName);
		return mapClasses;
	}
	

	private static Map<String, Class<?>> find(File dir, Map<String, Class<?>> map, Class<? extends Component> markedAnnotation, String workPackage) {
		
		for(String f : dir.list()) {
			if(f.endsWith(".class")) {
				String className = f.substring(0, f.length() - ".class".length());
				String fullClassName = workPackage + "." + className;
				
				try {
					Class candidate = Class.forName(fullClassName);						
					Component a = (Component)candidate.getAnnotation(markedAnnotation);
					if(a != null && !Modifier.isAbstract(candidate.getModifiers())) {
						if(a.value().equals("auto")) {
							map.put(className, candidate);
							//f.substring(0, f.length() - ".class".length())
						}
						else {
							map.put(a.value(), candidate);
						}
						
					}					
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			else {
				String path = dir.getAbsolutePath() + Separator.getSep() + f;
				
				File subdirCand = new File(path);
				if(subdirCand.isDirectory()) {
					map = find(subdirCand, map, markedAnnotation, workPackage + "." + f);
				}
			}
		}
		
		return map;
	}
	
	
	
	public static void main(String[] args) {	
		
		Map<String, Class<?>> map = getMarkedClassesFromPackage("dev", Component.class);
		
		System.out.println("Finded classes:");
		for(String n : map.keySet()) {
			System.out.println(n);
		}
	}
	
	
}
