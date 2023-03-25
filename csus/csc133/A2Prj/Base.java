package com.mycompany.a2;

public class Base extends Fixed {
	private int sequenceNumber;
	
	public Base(int size, double x, double y, int color, GameWorld w, int sequenceNumber) {
		super(size, x, y, color, w);
		this.sequenceNumber = sequenceNumber;		
	}
	
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
