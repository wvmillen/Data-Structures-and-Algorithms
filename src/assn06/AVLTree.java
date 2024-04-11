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

    /**
     * Rotates the tree left and returns
     * AVLTree root for rotated result.
     */
     private AVLTree<T> rotateLeft() {
         if (this._right == null) {
             return this; // No rotation needed if right child is null.
         }

         AVLTree<T> newRoot = this._right; //new root is the right root
         this._right = newRoot._left; //Child switch operation. If no child it will be null and still work
         newRoot._left = this; //new left node was old root AKA Z node

         // Update heights
         this._height = Math.max(height(this._left), height(this._right)) + 1; //take max of left or right as the new height
         newRoot._height = Math.max(height(newRoot._left), height(newRoot._right)) + 1;

         // Update sizes if necessary
         this._size = size(this._left) + size(this._right) + 1;
         newRoot._size = size(newRoot._left) + size(newRoot._right) + 1;

         return newRoot;
     }

     private AVLTree<T> rotateRight() {
         // You should implement right rotation and then use this
         // method as needed when fixing imbalances.
    	 // TODO

         //Same process as rotate left but with a right rotation this time
         if (this._left == null) {
             return this; // No rotation needed if left child is null.
         }

         AVLTree<T> newRoot = this._left;
         this._left = newRoot._right;
         newRoot._right = this;

         // Update heights
         this._height = Math.max(height(this._left), height(this._right)) + 1;
         newRoot._height = Math.max(height(newRoot._left), height(newRoot._right)) + 1;

         // Update sizes
         this._size = size(this._left) + size(this._right) + 1;
         newRoot._size = size(newRoot._left) + size(newRoot._right) + 1;

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
    	// TODO
        // Base case: insert new node
        if (_value == null) {
            _value = element;
            _height = 0;
            _size = 1;
            return this;
        }

        //insert recursivly
        if (element.compareTo(_value) < 0) {
            if (_left == null) _left = new AVLTree<>();
            _left = (AVLTree<T>) _left.insert(element);

        } else if (element.compareTo(_value) > 0) {
            if (_right == null) _right = new AVLTree<>();
            _right = (AVLTree<T>) _right.insert(element);
        }

        // Update height and size as usual
        _height = 1 + Math.max(height(_left), height(_right));
        _size = 1 + size(_left) + size(_right);

        // Get balance factor to check for imbalance
        int balance = getBalanceFactor();

        // Left-Right Imbalance - Left Rotation then Right Rotation
        if (balance > 1 && element.compareTo(_left._value) > 0) {
            _left = _left.rotateLeft();
            return rotateRight();
        }

        // Right-Left Imbalance - Right Rotation then Left Rotation
        if (balance < -1 && element.compareTo(_right._value) < 0) {
            _right = _right.rotateRight();
            return rotateLeft();
        }

        //Left Left and Right Right) handled in rotations

        return this; // return the node pointer
    }

    private int getBalanceFactor() { //helper method to get the balance factor
        return height(_left) - height(_right);
    }

    @Override
    public SelfBalancingBST<T> remove(T element) {
    	// TODO
        if (_value == null) {
            return this;
        }

        // Step 1: Search for the node to remove
        int compareResult = element.compareTo(_value); //create variable for comparable to mak eit simplier to read
        if (compareResult < 0) { //less than go left
            _left = (AVLTree<T>) _left.remove(element);
        } else if (compareResult > 0) { // greater than go right
            _right = (AVLTree<T>) _right.remove(element);
        } else {
            // Step 2: Remove the node
            if (_left != null && _right != null) {
                _value = _right.findMin();
                _right = (AVLTree<T>) _right.remove(_value);
            } else {
                return (_left != null) ? _left : _right;
            }
        }
        return this; // Step 4: Return the new root
    }



    @Override
    public T findMin() {
        if (isEmpty()) {
            throw new RuntimeException("Illegal operation on empty tree");
        }
       //TODO
        AVLTree<T> current = this; //find left-most node
        while (current._left != null) {
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
        while (current._right != null) {
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
            return this._left != null && this._left.contains(element);  // Search in the left subtree
        else
            return this._right != null && this._right.contains(element);  // Search in the right subtree
        }



    @Override
    public boolean rangeContain(T start, T end) {
        // TODO
        if (this._value == null)
            return start.compareTo(end) > 0;  // Return true if the range is invalid (min > max)

        if (this._value.compareTo(start) < 0)
            return this._right != null && this._right.rangeContain(start, end);  // Check in right subtree if current value is less than min

        if (this._value.compareTo(end) > 0)
            return this._left != null && this._left.rangeContain(start, end);  // Check in left subtree if current value is greater than max

        // If current value is within range then check both subtrees
        boolean leftCheck = this._left == null || this._left.rangeContain(start, this._value);
        boolean rightCheck = this._right == null || this._right.rangeContain(this._value, end);
        return leftCheck && rightCheck;
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

