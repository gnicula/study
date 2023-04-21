package com.mycompany.a3;

// New in A3, every object that collide must implement this
// interface.
public interface ICollider {
	// Returns true if it currently collides with object.
	public boolean collidesWith(GameObject otherObject);
	// Handles all side effects of the collision.
	public void handleCollision(GameObject otherObject);
}
