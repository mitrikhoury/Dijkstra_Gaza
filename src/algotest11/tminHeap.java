package algotest11;


import java.util.Arrays;

public class tminHeap {
	private int capacity = 10;
	private int size = 0;
	private Vertix[] items = new Vertix[capacity];

	private int getParentIndex(int childIndex) {
		return (childIndex - 1) / 2;
	}

	private int getLeftChildIndex(int parentIndex) {
		return 2 * parentIndex + 1;
	}

	private int getRightChildIndex(int parentIndex) {
		return 2 * parentIndex + 2;
	}

	private boolean hasParent(int index) {
		return getParentIndex(index) >= 0;
	}

	private boolean hasLeftChild(int index) {
		return getLeftChildIndex(index) < size;
	}

	private boolean hasRightChild(int index) {
		return getRightChildIndex(index) < size;
	}

	private Vertix parent(int index) {
		return items[getParentIndex(index)];
	}

	private Vertix leftChild(int index) {
		return items[getLeftChildIndex(index)];
	}

	private Vertix rightChild(int index) {
		return items[getRightChildIndex(index)];
	}

	private void swap(int index1, int index2) {
		Vertix temp = items[index1];
		items[index1] = items[index2];
		items[index2] = temp;
	}

	private void ensureCapacity() {
		if (size == capacity) {
			items = Arrays.copyOf(items, capacity * 2);
			capacity *= 2;
		}
	}

	public Vertix peek() {
		if (size == 0)
			throw new IllegalStateException();
		return items[0];
	}

	public Vertix poll() {
		if (size == 0)
			return null;

		Vertix item = items[0];
		items[0] = items[size - 1];
		size--;

		heapifyDown();

		return item;
	}

	public void add(Vertix item) {
		ensureCapacity();
		items[size] = item;
		size++;
		heapifyUp();
	}

	private void heapifyUp() {
		int index = size - 1;
		while (hasParent(index) && parent(index).getDistance() > items[index].getDistance()) { // when the distance of parent 
			swap(getParentIndex(index), index);                                              // large from the node will swap
			index = getParentIndex(index);                                                  // to up
		}
	}

	private void heapifyDown() {
		int index = 0;
		while (hasLeftChild(index)) {
			int smallerChildIndex = getLeftChildIndex(index);
			if (hasRightChild(index) && rightChild(index).getDistance() < leftChild(index).getDistance()) { // while dont have the smalest
				smallerChildIndex = getRightChildIndex(index);                                 //will the take the smalest distance
			}                                                                                 // and set it in the top ysing swap

			if (items[index].getDistance() < items[smallerChildIndex].getDistance()) {
				break;
			} else {
				swap(index, smallerChildIndex);
			}

			index = smallerChildIndex;
		}
	}
}