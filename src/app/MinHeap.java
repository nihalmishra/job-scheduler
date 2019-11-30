package app;

import java.util.ArrayList;

// Implementation of Min Heap in Java

public class MinHeap {

	// Heap is a list of type Building
	private ArrayList<Building> Heap;
	private static final int FRONT = 1;

	//Constructor for MinHeap class
	public MinHeap() {

		Heap = new ArrayList<Building>();
		Heap.add(null);

	}

	private int parent(int pos) {
		return pos / 2;
	}

	private int leftChild(int pos) {
		return (2 * pos);
	}

	private int rightChild(int pos) {
		return (2 * pos) + 1;
	}

	// @param index a node in Heap
	// method to check if param pos is a leaf node
	private boolean isLeaf(int pos) {
		return (pos >= (Heap.size() / 2) && pos <= Heap.size());
	}

	// @param position1 and position2 in heap
	// method to swap given params in Heap
	private void swap(int pos1, int pos2) {
		Building tempVar;
		tempVar = Heap.get(pos1);
		Heap.set(pos1, Heap.get(pos2));
		Heap.set(pos2, tempVar);
	}

	// @param index of a node in Heap
	// method to ensure min heap property and perform swaps if required
	public void minHeapify(int pos) {
		if (Heap.size() == 3 && Heap.get(1).isGreaterThan(Heap.get(2))) {
			swap(1, 2);
		} else if (!isLeaf(pos)) {
			
			// if left child is the smallest among the three, swap with parent
			if (Heap.get(pos).isGreaterThan(Heap.get(leftChild(pos))) && Heap.get(rightChild(pos)).isGreaterThan(Heap.get(leftChild(pos)))) {
				swap(pos, leftChild(pos));
				minHeapify(leftChild(pos));
			}
			
			// if right child is the smallest among the three, swap with parent
			if (Heap.get(pos).isGreaterThan(Heap.get(rightChild(pos))) && Heap.get(leftChild(pos)).isGreaterThan(Heap.get(rightChild(pos)))) {
				swap(pos, rightChild(pos));
				minHeapify(rightChild(pos));
			}
		}
	}

	//@param Building object
	// Insert a new Building into the heap. Swap with the child pointers to maintain
	// the minheap property
	public void insert(Building building) {
		Heap.add(building);
		int current = Heap.size() - 1;

		while (current != 1 && Heap.get(parent(current)).isGreaterThan(Heap.get(current))) {
			swap(current, parent(current));
			current = parent(current);
		}
		// printHeap();
	}

	// Prints the heap
	public void print() {
		for (int i = 1; i <= Heap.size() - 1; i++) {
			System.out.println(Heap.get(i).buildingNum + " " + Heap.get(i).executedTime + " " + Heap.get(i).totalTime);
		}
		// System.out.println('\n');
	}

	// method to build heap
	public void buildHeap() {
		for (int pos = (Heap.size() - 1 / 2); pos >= 1; pos--) {
			minHeapify(pos);
		}
	}

	// Removes the root value from the heap and return deleted object to caller
	public Building remove() {
		Building bldng = null;
		if (Heap.size() > 1) {
			bldng = Heap.get(FRONT);
			Heap.set(FRONT, Heap.get(Heap.size() - 1));
			minHeapify(FRONT);
			Heap.remove(Heap.size() - 1);
		}
		return bldng;
	}

	// Remove a Building B from the heap
	public Building remove_Building(Building B) {
		Building removed = null;
		if (Heap.size() > 1) {
			int g = Heap.indexOf(B);
			removed = Heap.remove(g);
		}
		return removed;
	}

	// returns root element in Heap
	public Building peek() {
		return Heap.get(FRONT);
	}

	// increases exec time of root by 1
	public void increaseKey() {
		if (Heap.size() > 1) {
			Building root = Heap.get(FRONT);
			root.executedTime += 1;
		}
	}

	// returns the size of the heap
	public int getSize() {
		return Heap.size() - 1;
	}
}