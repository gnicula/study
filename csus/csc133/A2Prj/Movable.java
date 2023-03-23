package com.mycompany.a2;

public class Movable extends GameObject {
    // Class Attributes
    private int speed;
    private int heading;
    

    // constructors
    public Movable(int size, double x, double y, int color, int speed, int heading, GameWorld world) {
    	super(size, x, y, color, world);
    	this.speed = speed;
        this.heading = heading;        
    }

    // getters and setters
    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getHeading() {
        return this.heading;
    }

    public void setHeading(int heading) {
        this.heading = heading;
    }
    
    // move method
    public void move() {
        //TODO update location, heading and speed.
    	System.out.println("Moving placeholder method");
    }
	
    public String toString() { 
		String parentDesc = super.toString();
		String myDesc = parentDesc + ", speed=" + speed + " ,heading=" + heading;
		return myDesc ; 
	}
}

