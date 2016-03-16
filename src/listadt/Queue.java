
public class Queue <T> extends List <T> {

	public void enqueue(T data){
	
		insert(data);
	}
	
	public T dequeue(){
		
		T tempData = null;
		
		if(count > 0){
			tempData = head.data;
		}
		
		ListItem <T> newHead = new ListItem<T>();
		
		if(count == 0){
			tempData = null;
			count++;//keep count the same
		}
		
		if(count == 1){
			head = null;
		}
		
		if(count == 2){
			head = tail;
			tail = null;
		}
		
		if(count > 2){
			newHead = head.next;
			newHead.next = head.next.next;
			head = newHead;
		}

		count--;
		return tempData;
	}
}
