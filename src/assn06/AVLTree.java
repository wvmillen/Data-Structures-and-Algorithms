package assn06;

public class AVLTree<T extends Comparable<T>> implements SelfBalancingBST<T> {
    // Fields
    private T _value;
    private AVLTree<T> _left;
    private AVLTree<T> _right;
    private int _height;
    private int _size;

    public AVLTree() {
        _value = null;
        _left = null;
        _right = null;
        _height = -1;
        _size = 0;
    }

    //helper method to get the balance factor
    private int balanceFactor(){
        if(isEmpty()){
            return 0;
        }
        return _left.height() - _right.height();
    }

    //helper method to rebalance tree
    private AVLTree<T> AVLbalance() {
        _height = Math.max(_left.height(), _right.height())+1; //height is the maximum of left or right subtree
        int balance = balanceFactor(); //balance factor
        if(balance < -1) { // if negative, go right
            if (_right.balanceFactor() > 0) {
                _right = this._right.rotateRight();
            }
            return this.rotateLeft();
        }

        if(balance > 1) { //if positive go left
            if (_left.balanceFactor() < 0) {
                _left = _left.rotateLeft();
            }
            return this.rotateRight();
        }
        return this;
    }

     private AVLTree<T> rotateLeft() {
         AVLTree<T> newRoot = _right;
         AVLTree<T> z = newRoot._left;

         newRoot._left = this;
         _right = z; //move child to the other side
         _height = Math.max(_left.height(), _right.height())+1; //set hieght with max subtree
         _size = _left._size + _right._size + 1;

         newRoot._height = Math.max(newRoot._left.height(), newRoot._right.height())+1;
         newRoot._size = newRoot._left._size + newRoot._right._size + 1;

         return newRoot;
     }

     private AVLTree<T> rotateRight() {

         AVLTree<T> newRoot = _left; //new root
         AVLTree<T> z = newRoot._right;

         newRoot._right = this; //right = new root
         _left = z; //move child to the other side
         _height = Math.max(_left.height(), _right.height())+1;
         _size = _left._size + _right._size + 1;

         newRoot._height = Math.max(newRoot._left.height(), newRoot._right.height())+1;
         newRoot._size = newRoot._left._size + newRoot._right._size + 1;

         return newRoot;
     }

    private int height(AVLTree<T> node) {
        return node == null ? -1 : node._height;
    }

    // Helper method to get the size of a node
    private int size(AVLTree<T> node) {
        return node == null ? 0 : node._size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int height() {
        return _height;
    }

    @Override
    public int size() {
        return _size;
    }

    @Override
    public SelfBalancingBST<T> insert(T element) {
        //essentialy a bst insert but rebalance at the end
        if(isEmpty()) { // if it is empty, create a new AVL Tree with element as the root
            _value = element;
            _left = new AVLTree<T>();
            _right = new AVLTree<T>();
            _size++;
            _height = Math.max(_left.height(), _right.height()) + 1;
        }
        //BST Insertions
        else if(element.compareTo(getValue()) >= 0) { //if element is larger than the node go right
            _right = (AVLTree<T>) _right.insert(element);
            _height = Math.max(_left.height(), _right.height()) + 1;
            _size++;
        }
        else{
            _left = (AVLTree<T>) _left.insert(element);
            _height = Math.max(_left.height(), _right.height()) + 1;
            _size++;
        }

        //reblance AVL
        return AVLbalance();
    }


    @Override
    public SelfBalancingBST<T> remove(T element) {
    	// TODO
        if(_value == null){ //case for null
            return this;
        }
        //This is my remove function from my bst code for assigment 4 modifyed for AVL Trees. If it works there, it will work here
        if (element.compareTo(_value) == 0) {  //If we find our element

            if (this.getLeft().isEmpty() && this.getRight().isEmpty()) { //If element is a leaf
                return new AVLTree<T>();  //Deletes element

            } else if(this.getLeft().isEmpty() && !this.getRight().isEmpty()){
                return this.getRight(); // Replace current node with right subtree
            } else if(!this.getLeft().isEmpty() && this.getRight().isEmpty()){
                return this.getLeft(); // Replace current node with left subtree
            }
            else{ // If there are two children
                T minInRightSubtree = this.getRight().findMin(); // Find the minimum in the right subtree
                _value = minInRightSubtree; // Replace the current node's value with the min value
                _right = (AVLTree<T>) this._right.remove(minInRightSubtree); // Remove the min value from the right subtree
            }
        } else if (element.compareTo(_value) < 0) { //Go to the left
            _left = (AVLTree<T>) this.getLeft().remove(element); //rerun through thr program moving to the left
        } else if(element.compareTo(_value) > 0){ //go to the right
            _right = (AVLTree<T>) this.getRight().remove(element); //rerun through thr program moving to the right
        }
        //adjust the height and size variables as usual
        _height = Math.max(_left.height(), _right.height()) + 1;
        _size = _left._size + _right._size + 1;

        //rebalance the AVL and return it
        return AVLbalance();
    }



    @Override
    public T findMin() {
        if (isEmpty()) {
            throw new RuntimeException("Illegal operation on empty tree");
        }
       //TODO
        AVLTree<T> current = this; //find left-most node
        while (!current._left.isEmpty()) {
            current = current._left;
        }
        return current._value;
    }

    @Override
    public T findMax() { //find right-most node
        if (isEmpty()) {
            throw new RuntimeException("Illegal operation on empty tree");
        }
        //TODO
        AVLTree<T> current = this;
        while (!current._right.isEmpty()) {
            current = current._right;
        }
        return current._value;
    }

    @Override
    public boolean contains(T element) {
    	// TODO
        if (this._value == null)
            return false;  // Empty tree

        int comparison = element.compareTo(this._value);
        if (comparison == 0)
            return true;  // Found the value
        else if (comparison < 0) //binary search
            return _left.contains(element);  // Search in the left subtree
        else
            return _right.contains(element);  // Search in the right subtree
        }



    @Override
    public boolean rangeContain(T start, T end) {
        // TODO
        if (this._value == null) {
            return false;
        }

        if (start.compareTo(this._value) <= 0 && end.compareTo(this._value) >= 0) {
            return true;
        }

        if (start.compareTo(this._value) > 0) {
            return this._right.rangeContain(start, end);
        }

        return this._left.rangeContain(start, end);
    }

    @Override
    public T getValue() {
        return _value;
    }

    @Override
    public SelfBalancingBST<T> getLeft() {
        if (isEmpty()) {
            return null;
        }
        return _left;
    }

    @Override
    public SelfBalancingBST<T> getRight() {
        if (isEmpty()) {
            return null;
        }
         return _right;
    }

}



