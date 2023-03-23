package com.mycompany.a2;

import java.util.ArrayList;

public interface ICollection {
	
	public void add(GameObject go);
	
	public IIterator getIterator();
}

