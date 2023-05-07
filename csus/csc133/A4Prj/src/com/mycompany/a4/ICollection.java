package com.mycompany.a4;

// Basic Iterable collection interface.
public interface ICollection {
	
	public void add(GameObject go);
	
	public IIterator getIterator();
}

