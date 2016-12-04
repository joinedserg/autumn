package dev;

import dev.autumn.annotaion.Component;



public class Separator {
	private static char sepDir = '/'; /*4 Linux */
	
	
	//private static char sepDir = '\\'; /*4 Windows */
	
	public static char getSep() {
		return sepDir;
	}
}
