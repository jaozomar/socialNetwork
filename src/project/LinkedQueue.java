/* 
Joshua Ozomaro
10/23/21
Team: Byte the Bullet
Matthew Say, Victor Mendoza, Phat Ho, Joshua Ozomaro
Queue Exercise 1
*/

package project;

public final class LinkedQueue<T> implements QueueInterface<T>
{
	private Node firstNode;
	private Node lastNode;
	
	public LinkedQueue() {
		firstNode = null;
		lastNode = null;
	}
	
	public void enqueue(T newEntry) {
		Node n = new Node(newEntry, null);
		if(isEmpty())
			firstNode = n;
		else
			lastNode.setNextNode(n);
		lastNode = n;
	}
	
	public T dequeue() {
		T front = getFront();
		assert firstNode != null;
		firstNode.setData(null);
		firstNode = firstNode.getNextNode();
		if(firstNode == null)
			lastNode = null;
		return front;
	}
	
	public T getFront() {
		if(isEmpty())
			throw new EmptyQueueException("The queue is empty");
		else
			return firstNode.getData();
	}
	
	public boolean isEmpty() {
		return ((firstNode == null) && (lastNode == null));
	}
	
	public void clear() {
		firstNode = null;
		lastNode = null;
	}

	class Node{
		private T data;
		private Node next;
	
		private Node(T dataPortion) {
			this(dataPortion, null);
		}
	
		private Node(T dataPortion, Node nextNode) {
			data = dataPortion;
			next = nextNode;
		}
	
		private T getData() {
			return data;
		}
	
		private void setData(T newData) {
			data = newData;
		}
	
		private Node getNextNode() {
			return next;
		}
		
		private void setNextNode(Node nextNode) {
			next = nextNode;
		}
	} // end Node
} // end Linkedqueue