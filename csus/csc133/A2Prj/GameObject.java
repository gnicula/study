package com.mycompany.a2;
import com.codename1.charts.util.ColorUtil;

public class GameObject {
	private int size;
	private double xPos;
	private double yPos;
	private int color;
	private GameWorld world;
	
//	public GameObject(){
//		
//	}
	
	public GameObject(int size, double x, double y, int color, GameWorld w) {
		this.size = size;
		this.xPos = x;
		this.yPos = y;
		this.color = color;
		this.world = w;
	}
	
	public int getSize() {
		return size;
	}
	
	public double getX() {
		return xPos;
	}
	
	public void setX(int x) {
		xPos = x;
	}
	
	public double getY() {
		return yPos;
	}
	
	public void setY(int y) {
		yPos = y;
	}
	
	public void setColor(int c) {
		color = c;
	}
	
	public int color() {
		return color;
	}
	
	public GameWorld getWorld() {
		return world;
	}
	
	public String toString() {      
		String myDesc = "location=" + xPos + ", " + yPos + ", color=" + "["  + ColorUtil.red(color) + 
				","  + ColorUtil.green(color) + ","  + ColorUtil.blue(color) + "]" + ", size=" + size;
	;    return myDesc ; 
	}
	
}
