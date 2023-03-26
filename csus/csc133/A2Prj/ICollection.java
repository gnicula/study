package com.mycompany.a2;

// Basic Iterable collection interface.
public interface ICollection {
	
	public void add(GameObject go);
	
	public IIterator getIterator();
}

