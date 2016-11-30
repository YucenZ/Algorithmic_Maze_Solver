/**
 * A linked priority Queue that sorted element based on its priority
 * @author Yucen Zhang
 */

public class LinkedPriorityQueue<T> implements PriorityQueueADT<T>
{
	private int count;  //count the element in the queue
	private PriorityNode<T> front; //front node

	/**
	 * Default Constructor: creates an empty queue.
	 */
	public LinkedPriorityQueue()
	{
		count = 0;
		front = new PriorityNode<T>();

	}

	@Override
	/**
	 * Adds the specified element to the rear of this queue. 
	 * @param element  the element to be added to the rear of linked queue
	 */
	public void enqueue (T element)
	{
		PriorityNode<T> node = new PriorityNode<T>();
		node.setElement(element);
		PriorityNode<T> current = front;
		count++;
		while(!isEmpty()){
			if(current.getNext() == null){	//checks if it is at the end
				current.setNext(node);
				break;
			}else{								//moves onto the next 
				current = current.getNext();
			}
		}

	}

	@Override 
	/**
	 * Adds the element into linked queue based on its priority. The higher the priority the closer to the front
	 * @param element  the element to be added to the rear of this queue
	 * @param priority the priority of the element 
	 */
	public void enqueue (T element, double priority)
	{
		PriorityNode<T> node = new PriorityNode<T>(element,priority);  //node to add
		PriorityNode<T> current = front;   // current always start from the front
		count++;
		while(!isEmpty()){
			if(current.getNext() == null){	//checks if it is at the end
				current.setNext(node);
				break;
			}else if((current.getNext().getPriority() > priority)){		//compares node's priority with current 
				node.setNext(current.getNext());
				current.setNext(node);
				break;
			}else{								//moves onto the next if node's priority is lower than current 
				current = current.getNext();
			}
		}
	}

	/**
	 * Removes the element at the front of this queue and returns a
	 * reference to it. Throws an EmptyCollectionException if the
	 * queue is empty.
	 *
	 * @return                           the element at the front of this queue
	 * @throws EmptyCollectionException  if an empty collection exception occurs
	 */
	public T dequeue() throws EmptyCollectionException
	{
		if (isEmpty())
			throw new EmptyCollectionException ("queue");
		front = front.getNext();
		T result = front.getElement();
		//front = front.getNext();
		count--;

		return result;
	}

	/**
	 * Returns a reference to the element at the front of this queue.
	 * The element is not removed from the queue.  Throws an
	 * EmptyCollectionException if the queue is empty.  
	 *
	 * @return                            a reference to the first element in
	 *                                    this queue
	 * @throws EmptyCollectionsException  if an empty collection exception occurs
	 */
	public T first() throws EmptyCollectionException
	{
		if (isEmpty())
			throw new EmptyCollectionException ("queue");
		return front.getNext().getElement();

	}

	/**
	 * Returns true if this queue is empty and false otherwise. 
	 *
	 * @return  true if this queue is empty and false if otherwise
	 */
	public boolean isEmpty()
	{
		return count == 0;
	}

	/**
	 * Returns the number of elements currently in this queue.
	 *
	 * @return  the integer representation of the size of this queue
	 */
	public int size()
	{
		return count;
	}

	/**
	 * Returns a string representation of this queue. 
	 *
	 * @return  the string representation of this queue
	 */
	public String toString()
	{
		String s = "";
		PriorityNode<T> current = front ;

		while(current!=null){
			s = s + current.getElement() + "\n";
			current=current.getNext();
		}
		return(s);
	}   

}
