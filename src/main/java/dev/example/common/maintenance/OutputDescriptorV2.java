package dev.example.common.maintenance;



public class OutputDescriptorV2 extends OutputDescriptor {

	public OutputDescriptorV2() {
		System.out.println("Hey, I'm OuputDescriptorV2");
		
	}
	
	public void writeline(String out) {
		//System.out.println("OutputDescriptorV1__: " + out);
		System.out.println("OutputDescriptorV2: " + out);
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
