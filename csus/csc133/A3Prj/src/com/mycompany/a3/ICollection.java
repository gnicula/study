package com.mycompany.a3;

// Basic Iterable collection interface.
public interface ICollection {
	
	public void add(GameObject go);
	
	public IIterator getIterator();
}

