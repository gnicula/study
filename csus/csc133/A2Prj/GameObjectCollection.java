package com.mycompany.a2;

import java.util.ArrayList;

import com.codename1.charts.util.ColorUtil;

public class GameObjectCollection implements ICollection {
	private ArrayList<GameObject> objectList;
	private IIterator it;

	private class GameObjectIterator implements IIterator {

		public int index = 0;

		public Boolean hasNext() {
			return index < objectList.size();
		}

		public GameObject getNext() {
			GameObject toReturn = objectList.get(index);
			++index;

			return toReturn;
		}

		public void Reset() {
			index = 0;
		}

	}

	public GameObjectCollection() {
		objectList = new ArrayList<GameObject>();

		it = new GameObjectIterator();
	}

	public void add(GameObject go) {
		objectList.add(go);
	}

	public IIterator getIterator() {

		it.Reset();
		return it;
	}

	
	/*
	// Code for testing the functionality of the Iterator and Collection.
	public static void main(String args[]) {
		GameObjectCollection goc = new GameObjectCollection();

		goc.add(new Base(8, 50, 50, ColorUtil.BLACK, null, 1));
		goc.add(new Base(8, 100, 100, ColorUtil.BLACK, null, 2));
		goc.add(new Base(8, 150, 150, ColorUtil.BLACK, null, 3));
		goc.add(new Base(8, 200, 200, ColorUtil.BLACK, null, 4));

		IIterator it = goc.getIterator();
		System.out.println(it.hasNext());

		GameObject go = it.getNext();

		System.out.println(go);

		go = it.getNext();

		System.out.println(go);

		it = goc.getIterator();
		go = it.getNext();

		System.out.println(go);

		it.getNext();
		it.getNext();
		it.getNext();

		System.out.println(it.hasNext());

		it = goc.getIterator();

		while (it.hasNext()) {
			go = it.getNext();
		}

		System.out.println("Last element is: " + go);

	}
	*/
}