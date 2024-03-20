package assn04;
import java.util.LinkedList;
import java.util.Queue;

public class NonEmptyBST<T extends Comparable<T>> implements BST<T> {
	private T _element;
	private BST<T> _left;
	private BST<T> _right;

	public NonEmptyBST(T element) {
		_left = new EmptyBST<T>();
		_right = new EmptyBST<T>();
		_element = element;
	}

	// TODO: insert
	@Override
	public BST<T> insert(T element){

		if( element.compareTo(this._element) == 0 ){  //If the elements are the same don't insert the element
			return this;
		}
		else if(element.compareTo(this._element) > 0){ //If element is greater than the current one, check the right
			if(this._right.isEmpty()){            //if the right is empty, insert the element in the right spot
				this._right = new NonEmptyBST<>(element);
			}
			else{                                    //Go through process again
				this._right.insert(element);
			}
		}
		else if(element.compareTo(this._element) < 0){ //If element is greater than the current one, check the right
			if(this._left.isEmpty()){            //if the left is empty, insert the element in the right spot
				this._left = new NonEmptyBST<>(element);
			}
			else{                                    //Go through process again
				this._left.insert(element);
			}
		}
		return this;
	}

	// TODO: remove
	@Override
	public BST<T> remove(T element) {

			if (element.compareTo(this._element) == 0) {  //If we find our element

				if (this.getLeft().isEmpty() && this.getRight().isEmpty()) { //If element is a leaf
					return new EmptyBST<T>();  //Deletes element

				} else if(this.getLeft().isEmpty() && !this.getRight().isEmpty()){
					return this.getRight(); // Replace current node with right subtree
				} else if(!this.getLeft().isEmpty() && this.getRight().isEmpty()){
					return this.getLeft(); // Replace current node with left subtree
				}
				else{ // If there are two children
					T minInRightSubtree = this.getRight().findMin(); // Find the minimum in the right subtree
					this._element = minInRightSubtree; // Replace the current node's value with the min value
					this._right = this._right.remove(minInRightSubtree); // Remove the min value from the right subtree
				}
			} else if (element.compareTo(this._element) < 0) { //Go to the left
				_left = this.getLeft().remove(element); //rerun through thr program moving to the left
			} else { //go to the right
				_right = this.getRight().remove(element); //rerun through thr program moving to the right
			}
			return this; // Return the current tree with the node removed
		}


		// TODO: remove all in range (inclusive)
	@Override
	public BST<T> remove_range(T start, T end) {
		if(this.isEmpty()){ //Base case
			return this;
		}
		if(this._element.compareTo(start) >= 0 ){
			this._left = this._left.remove_range(start, end);
		}
		if(this._element.compareTo(end) <= 0){
			this._right = this._right.remove_range(start, end);
		}
		if(this._element.compareTo(start) >= 0 && this._element.compareTo(end) <= 0){
			return this.remove(this._element);
		}

		return this;
	}

	// TODO: printPreOrderTraversal
	@Override
	public void printPreOrderTraversal() {
		if(!this.isEmpty()){
			System.out.print(this._element + " ");
			if(!_left.isEmpty()){
				this._left.printPreOrderTraversal();
			}
			if(!_right.isEmpty()) {
				this._right.printPreOrderTraversal();
			}

		}
	}

	// TODO: printPostOrderTraversal
	@Override
	public void printPostOrderTraversal() {
		if(!this.isEmpty()){
		if(!_left.isEmpty()){
			this._left.printPostOrderTraversal();
		}
		if(!_right.isEmpty()) {
			this._right.printPostOrderTraversal();
		}
			System.out.print(this._element + " ");

		}
	}

	// The findMin method returns the minimum value in the tree.
	@Override
	public T findMin() {
		if(_left.isEmpty()) {
			return _element;
		}
		return _left.findMin();
	}

	@Override
	public int getHeight() {
		   return Math.max(_left.getHeight(), _right.getHeight())+1;
	}

	@Override
	public BST<T> getLeft() {
		return _left;
	}

	@Override
	public BST<T> getRight() {
		return _right;
	}

	@Override
	public T getElement() {
		return _element;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

}
