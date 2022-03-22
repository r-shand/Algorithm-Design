package prj2;
import java.util.*;
import java.io.*;

public class MinHeap {
        //public static final String BORDER = "-------------------------------------------";
 	private int maxSize;
	private int size;
	private Contestant[] heap;
	private int[] handle;
	
	public MinHeap(int maxSize) {
		this.maxSize = maxSize;
		this.size = 0;
		//we aren't using 0th element so we have +1
		heap = new Contestant[this.maxSize+1];
		handle = new int[this.maxSize+1];
		for(int i = 0; i < this.handle.length; i++) {
			this.handle[i] = -1;
		}
	}
	
	//return index of parent node
	public int getParent(int index) {
		if(index > 1 && index <= size) {
			return index/2;
		}
		//return -1 on error
		return -1;
	}
	
	//return index of left child
	public int getLeftChild(int index) {
		int lc = index*2;
		if(lc <= size) {
			return lc;
		}
		//return -1 on error
		return -1;
	}
	
	//return index of right child
	public int getRightChild(int index) {
		int rc = (index*2)+1;
		if(rc <= this.size) {
			return rc;
		}
		//return -1 on error
		return -1;
	}
	
	//check if node index in a heap is a leaf or not
	public boolean isLeaf(int index) {
		if(index > (this.size/2) && index <= this.size) {
			return true;
		}
		return false;
	}

	//swap contestants in heap
	public void swap(int i1, int i2) {
		//swap locations in heap in handle array
		int idi1 = this.heap[i1].getID();
		int idi2 = this.heap[i2].getID();
		this.handle[idi1] = i2;
		this.handle[idi2] = i1;
		//swap contestants in heap
		Contestant temp = this.heap[i1];
		this.heap[i1] = this.heap[i2];
		this.heap[i2] = temp;
	}
	
	//min heapify. we are comparing values of contestants scores here
	public void minHeapify(int index) {
		//check to see if index is a leaf. if not, we continue
		if(!isLeaf(index)) {
			//check if min heap property is broken
			//System.out.println(index);
			//System.out.println(getRightChild(index));
			if(getLeftChild(index) == -1 || getRightChild(index) == -1) {
				//case 1
				//only right child exists
				if(getLeftChild(index) == -1) {
					if(this.heap[index].getScore() > this.heap[getRightChild(index)].getScore()) {
						swap(getRightChild(index), index);
						minHeapify(getRightChild(index));
					}
				}
				//case 2
				//only left child exists
				else if(getRightChild(index) == -1) {
					if(this.heap[index].getScore() > this.heap[getLeftChild(index)].getScore()) {
						swap(getLeftChild(index), index);
						minHeapify(getLeftChild(index));
					}
				}
			}
			else if(this.heap[index].getScore() > this.heap[getRightChild(index)].getScore() || this.heap[index].getScore() > this.heap[getLeftChild(index)].getScore()) {			
				//case 3
				//if min heap property is broken, swap index and right child of index if
				//value at right index is less than value at left index
				if(this.heap[getRightChild(index)].getScore() <= this.heap[getLeftChild(index)].getScore()) {
					swap(getRightChild(index), index);
					minHeapify(getRightChild(index));
				}
				//case 4
				//if min heap property is broken, swap index and left child of index if
				//value at left index is less than value at right index
				else if(this.heap[getRightChild(index)].getScore() > this.heap[getLeftChild(index)].getScore()) {
					swap(getLeftChild(index), index);
					minHeapify(getLeftChild(index));
				}
			}
		}
	}
	public boolean doesIDexist(int k) {
		//if ID already exists we cannot insert. ID's exist for every number less than size.
		if(k <= this.size && k > 0) return true;
		return false;
	}
	
	public String insert(int k, int score) {
		//if ID exists we can't insert a new element
	        String retMsg = "";
		if(doesIDexist(k)) {
			System.out.println("Contestant <" + Integer.toString(k) + "> is already in the extended heap: cannot insert.");
			retMsg = "Contestant <" + Integer.toString(k) + "> is already in the extended heap: cannot insert.";
			return retMsg;
		}
		Contestant c = new Contestant(k, score);
		//if heap size is max size we can't insert a new element
		if(this.maxSize <= this.size) {
		        //System.out.println("Contestant " + Integer.toString(k) + " could not be inserted because the extended heap is full");
			retMsg = "Contestant <" + Integer.toString(k) + "> could not be inserted because the extended heap is full";
			return retMsg;
		}
		this.size += 1;
		//if other contestants exist but aren't in the heap, move them over
		if(this.heap[this.size] != null) {
			for(int j = this.maxSize; j > this.size; j--) {
				this.heap[j] = this.heap[j-1];
			}
		}
		//insert new element in the right place of the heap
		this.heap[this.size] = c;
		this.handle[k] = this.size;
		int temp = this.size;
		if(hasParent(temp)) {
			//System.out.println(heap[getParent(temp)].getScore());
			//System.out.println(heap[temp].getScore());
			while(hasParent(temp) && this.heap[temp].getScore() < this.heap[getParent(temp)].getScore()) {
				//System.out.println(getParent(temp));
				swap(temp, getParent(temp));
				temp = getParent(temp);
			}
		}
		//System.out.println("Contestant " + Integer.toString(k) + " inserted with initial score " + Integer.toString(score) + ".");
		retMsg = "Contestant <" + Integer.toString(k) + "> inserted with initial score <" + Integer.toString(score) + ">.";
		return retMsg;
	}
	
	public boolean hasParent(int i) {
		if(getParent(i) >= 1) return true;
		return false;
	}
	
    public String eliminateWeakest() {
    	//question: is heap empty at size 1 or 0?
    	//assume empty heap is size 1 for now
	String retMsg = "";
    	if(this.size > 1) {
	    //System.out.println("Contestant " + Integer.toString(this.heap[1].getID()) + " with current lowest score " + Integer.toString(this.heap[1].getScore()) + " eliminated.");
	    retMsg = "Contestant <" + Integer.toString(this.heap[1].getID()) + "> with current lowest score <" + Integer.toString(this.heap[1].getScore()) + "> eliminated.";
    	    swap(1, this.size);
    	    this.size -= 1;
    	    //System.out.println(heap[1].getScore());
    	    showContestants();
    	    minHeapify(1);
    	}
    	else {
	    //System.out.println("No contestant can be eliminated since the extended heap is empty.");
	    retMsg = "No contestant can be eliminated since the extended heap is empty.";
    	}
	return retMsg;
    }
    
  //add points from contestant with id k
    public String earnPoints(int k, int score) {
	String retMsg = "";
    	if(!doesIDexist(k)) {
	    //System.out.println("Contestant " + Integer.toString(k) + " is not in the extended heap.");
	    retMsg = "Contestant <" + Integer.toString(k) + "> is not in the extended heap.";
    	    return retMsg;
    	}
    	this.heap[this.handle[k]].addScore(score);
    	//System.out.println("Contestant " + Integer.toString(k) + "'s score increased by " + Integer.toString(score) + " points to " + Integer.toString(this.heap[this.handle[k]].getScore()) + ".");
	retMsg = "Contestant <" + Integer.toString(k) + ">'s score increased by <" + Integer.toString(score) + "> points to <" + Integer.toString(this.heap[this.handle[k]].getScore()) + ">.";
    	if(hasParent(k)) minHeapify(getParent(k));
    	else minHeapify(k);
	return retMsg;
    }
    
    //remove points from contestant with id k
    public String losePoints(int k, int score) {
	String retMsg = "";
    	if(!doesIDexist(k)) {
	    //System.out.println("Contestant " + Integer.toString(k) + " is not in the extended heap.");
	    retMsg = "Contestant <" + Integer.toString(k) + "> is not in the extended heap.";
    	    return retMsg;
    	}
    	//System.out.println(handle[k]);
    	this.heap[this.handle[k]].removeScore(score);
    	//System.out.println("Contestant " + Integer.toString(k) + "'s score decreased by " + Integer.toString(score) + " points to " + Integer.toString(this.heap[this.handle[k]].getScore()) + ".");
	retMsg = "Contestant <" + Integer.toString(k) + ">'s score decreased by <" + Integer.toString(score) + "> points to <" + Integer.toString(this.heap[this.handle[k]].getScore()) + ">.";
    	if(hasParent(k)) minHeapify(getParent(k));
    	else minHeapify(k);
	return retMsg;
    }
    
    //display all contestants in the extended heap
    public String showContestants() {
    	//System.out.println(BORDER);
	String retMsg = "";
	if(size == 0) {
	    //System.out.println("There are no contestants in the extended heap to show");
	    //this is so the program won't terminate if showContestants is called on an empty heap.
	    retMsg = "There are no contestants in the extended heap to show";
	    return retMsg;
	}
    	for(int i = 1; i <= size; i++) {
    	    //System.out.println("Contestant " + Integer.toString(heap[i].getID()) + " in extended heap location " + Integer.toString(i) + " with score " + Integer.toString(heap[i].getScore()) + ".");
	    retMsg += "Contestant <" + Integer.toString(heap[i].getID()) + "> in extended heap location <" + Integer.toString(i) + "> with score <" + Integer.toString(heap[i].getScore()) + ">.";
	    if(i < size) retMsg += "\n";
    	}
    	//System.out.println(BORDER);
	return retMsg;
    }
    
    public String findContestant(int id) {
	String retMsg = "";
    	if(handle[id] >= 1) {
    	    //System.out.println("Contestant " + Integer.toString(id) + " is in the extended heap with score " + Integer.toString(this.heap[this.handle[id]].getScore()) + ".");
	    retMsg = "Contestant <" + Integer.toString(id) + "> is in the extended heap with score <" + Integer.toString(this.heap[this.handle[id]].getScore()) + ">.";
    	}
    	else {
    	    //System.out.println("Contestant " + Integer.toString(id) + " is not in the extended heap.");
	    retMsg = "Contestant <" + Integer.toString(id) + "> is not in the extended heap.";
    	}
	return retMsg;
    }
    
    public String showHandles() {
	String retMsg = "";
    	for(int i = 1; i < this.handle.length; i++) {
    	    if(this.handle[i] >= 1 && this.handle[i] <= this.size) {
    		//System.out.println("Contestant " + Integer.toString(i) + " stored in extended heap location " + Integer.toString(this.handle[i]) + ".");
		retMsg += "Contestant <" + Integer.toString(i) + "> stored in extended heap location <" + Integer.toString(this.handle[i]) + ">.";
    	    }
    	    else {
    		//System.out.println("There is no Contestant " + Integer.toString(i) + " in the extended heap: handle[" + Integer.toString(i) + "] = -1.");
		retMsg += "There is no Contestant <" + Integer.toString(i) + "> in the extended heap: handle[<" + Integer.toString(i) + ">] = -1.";
    	    }
	    if(i < this.handle.length-1) retMsg += "\n";
    	}
	return retMsg;
    }
    
    public String showLocation(int id) {
	String retMsg = "";
    	if(this.handle[id] >= 1) {
	    //System.out.println("Contestant " + Integer.toString(id) + " stored in extended heap location " + Integer.toString(this.handle[id]) + ".");
	    retMsg = "Contestant <" + Integer.toString(id) + "> stored in extended heap location <" + Integer.toString(this.handle[id]) + ">.";
	}
	else {
	    //System.out.println("There is no Contestant " + Integer.toString(id) + " in the extended heap: handle[" + Integer.toString(id) + "] = -1.");
	    retMsg = "There is no Contestant <" + Integer.toString(id) + "> in the extended heap: handle[<" + Integer.toString(id) + ">] = -1.";
	}
	return retMsg;
    }
    
    public String crownWinner( ) {
	String retMsg = "";
    	while(this.size > 1) {
    		eliminateWeakest();
    		//showContestants();
    	}
    	//System.out.println("Contestant " + Integer.toString(heap[1].getID()) + " wins with score " + Integer.toString(heap[1].getScore()) + "!");
	retMsg = "Contestant <" + Integer.toString(heap[1].getID()) + "> wins with score <" + Integer.toString(heap[1].getScore()) + ">!";
	return retMsg;
    }
}
