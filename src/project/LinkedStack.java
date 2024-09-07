/* 
Joshua Ozomaro
10/17/21
Team: Byte the Bullet
Matthew Say, Victor Mendoza, Phat Ho, Joshua Ozomaro
Stacks Exercise 1
*/

package project;


public final class LinkedStack<T> implements StackInterface<T>
{

	private Node topNode; // References the first node in the chain

	//default constructor
	public LinkedStack() {
		topNode = null;
	}
 
	// Implement the unimplemented methods 
	public void push(T newEntry) {
		Node newNode = new Node(newEntry, topNode);
		topNode = newNode;
	}
	
	public T pop() {
		T stackTop = peek();
		assert topNode != null;
		topNode = topNode.getNextNode();
		return stackTop;
	}
 
	public T peek() {
		if(isEmpty()) {
			throw new EmptyStackException("The stack is empty");
		}
		return topNode.getData();
	}
	
	public boolean isEmpty() {
		return topNode == null;
	}
	
	public void clear() {
		topNode = null;
	}

	private class Node
	{
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
	}// end Node

 

}// end LinkedStack