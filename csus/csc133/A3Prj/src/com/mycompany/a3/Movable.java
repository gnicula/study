package com.mycompany.a3;

public class Movable extends GameObject {
    // Class Attributes
    private int speed;
    // heading is the angle in degrees in range 0-359
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
    
    // Move method that all Movable objects derive.
    public void move() {
    	// System.out.println("Movable::move()\n");
        double theta = Math.toRadians(90 - heading);
        double deltaX = Math.cos(theta) * speed;
        double deltaY = Math.sin(theta) * speed;
        setX(getX() + deltaX);
        setY(getY() + deltaY);
        locationBoundAdjust();
    }
    
    public String toString() { 
		String parentDesc = super.toString();
		String myDesc = parentDesc + ", speed=" + speed + ", heading=" + heading;
		return myDesc ; 
	}
    
    // Makes sure that movable objects do not go out of bounds.
    // It accomplishes this by resetting the specific out of bounds coordinate
    // to the edge of the boundary. 
    protected void locationBoundAdjust() {
    	if (getX() < 0) {
    		setX(0);
    	}
    	if (getX() > getWorld().getWidth()) {
    		setX(getWorld().getWidth());
    	}
    	if (getY() < 0) {
    		setY(0);
    	}
    	if (getY() > getWorld().getHeight()) {
    		setY(getWorld().getHeight());
    	}
    }
}

