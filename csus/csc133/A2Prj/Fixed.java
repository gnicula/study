package com.mycompany.a2;

// Fixed is a type of GameObject that is used to define a
// common base type for the immovable game objects.
public class Fixed extends GameObject{
	
	public Fixed(int size, double x, double y, int color, GameWorld w) {
		super(size, x, y, color, w);
	}

}
