package dev.example.main;

import dev.autumn.annotaion.*;
import dev.autumn.*;

import dev.example.common.maintenance.*;


@Component("mainContextV2")
public class MainContextV2 {

	@OutputContext("OutputDescriptorV2")
	OutputDescriptor outputHandler;
	
	public MainContextV2() {
		System.out.println("Hey, I'm MainContextV2");
		
	}
	
	
	
}
