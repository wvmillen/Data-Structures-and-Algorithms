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
     private AVLTree<T> rotateLeft(AVLTree<T> node) {
         if (node == null || node._right == null){
             return node;  // No rotation needed
         }

         //preform rotation
         AVLTree<T> rightChild = node._right;
         node._right = rightChild._left; //switch right child's left child to the other side
         rightChild._left = node;

         // Update heights and sizes after rotation
         node._height = Math.max(height(node._left), height(node._right)) + 1;
         node._size = size(node._left) + size(node._right) + 1;
         rightChild._height = Math.max(height(rightChild._left), height(rightChild._right)) + 1;
         rightChild._size = size(rightChild._left) + size(rightChild._right) + 1;

         return rightChild;  // The new root after rotation
     }

     private AVLTree<T> rotateRight(AVLTree<T> node) {

         //preform same operation as rotateLeft except on the right
         if (node == null || node._left == null) return node;  // No rotation needed

         AVLTree<T> leftChild = node._left;
         node._left = leftChild._right;
         leftChild._right = node;

         // Update heights and sizes after rotation
         node._height = Math.max(height(node._left), height(node._right)) + 1;
         node._size = size(node._left) + size(node._right) + 1;
         leftChild._height = Math.max(height(leftChild._left), height(leftChild._right)) + 1;
         leftChild._size = size(leftChild._left) + size(leftChild._right) + 1;

         return leftChild;  // The new root after rotation
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
    public SelfBalancingBST<T> insert(T value) {
        if (value == null) return this; // Ignore null values

        // Standard BST insert
        if (_value == null) {
            _value = value;
            _height = 0;
            _size = 1;
        } else if (value.compareTo(_value) < 0) {
            if (_left == null) _left = new AVLTree<>();
            _left = (AVLTree<T>) _left.insert(value);
        } else if (value.compareTo(_value) > 0) {
            if (_right == null) _right = new AVLTree<>();
            _right = (AVLTree<T>) _right.insert(value);
        }

        // Update height and size of this node
        _height = Math.max(height(_left), height(_right)) + 1;
        _size = size(_left) + size(_right) + 1;

        // Check balance and apply rotations
        int balance = balanceFactor();

        // Left Left Case
        if (balance > 1 && value.compareTo(_left._value) < 0) {
            return rotateRight(this);
        }

        // Right Right Case
        if (balance < -1 && value.compareTo(_right._value) > 0) {
            return rotateLeft(this);
        }

        // Left Right Case
        if (balance > 1 && value.compareTo(_left._value) > 0) {
            if (_left != null) {
                _left = _left.rotateLeft(_left);
            } else {
                _left = null;
            }
            return rotateRight(this);
        }

        // Right Left Case
        if (balance < -1 && value.compareTo(_right._value) < 0) {
            if (_right != null) {
                _right = _right.rotateRight(_right);
            } else {
                _right = null;
            }
            return rotateLeft(this);
        }

        return this; // return the node itself if no rotations are needed
    }

    private int balanceFactor() { //helper method to get the balance factor
        return height(_left) - height(_right);
    }

    @Override
    public SelfBalancingBST<T> remove(T element) {
    	// TODO

        return null;
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

