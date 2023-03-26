package com.mycompany.a2;

// The 'Base' class implements basic methods and it has a toString() 
// that builds the Base string representation on top of its 
// inherited toString method.
public class Base extends Fixed {
	// Base objects are Fixed objects with a sequence number.
	private int sequenceNumber;
	
	public Base(int size, double x, double y, int color, GameWorld w, int sequenceNumber) {
		super(size, x, y, color, w);
		this.sequenceNumber = sequenceNumber;		
	}
	
	@Override
	public void setColor(int color) {
		System.out.println("Color cannot be changed once set");
	}
	
	public int getSequenceNumber() {
		return this.sequenceNumber;
	}
	
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	
	@Override
	public String toString() {   
		String parentDesc = super.toString();    
		String myDesc = "Base: " + parentDesc + ", sequenceNumber=" + sequenceNumber;
		return myDesc;
	}
}
