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
         this._right = newRoot._left;
         newRoot._left = this;

         // Update heights
         this._height = Math.max(height(this._left), height(this._right)) + 1; //take max of left or right as the new height
         newRoot._height = Math.max(height(newRoot._left), height(newRoot._right)) + 1;

         // Update sizes if necessary
         this._size = size(this._left) + size(this._right) + 1;
         newRoot._size = size(newRoot._left) + size(newRoot._right) + 1;

         return newRoot;

     }
    
    /**
     * Rotates the tree right and returns
     * AVLTree root for rotated result.
     */
     private AVLTree<T> rotateRight() {
         // You should implement right rotation and then use this 
         // method as needed when fixing imbalances.
    	 // TODO

         //same process as rotate left but with a right rotation this time
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

        return null;
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
        // TODO
        AVLTree<T> current = this;
        while (current._left != null) {
            current = current._left;
        }
        return current._value;
    }

    @Override
    public T findMax() {
        if (isEmpty()) {
            throw new RuntimeException("Illegal operation on empty tree");
        }
        // TODO
        AVLTree<T> current = this;
        while (current._right != null) {
            current = current._right;
        }
        return current._value;
    }

    @Override
    public boolean contains(T element) {
    	// TODO

        return false;
    }


    @Override
    public boolean rangeContain(T start, T end) {
        // TODO

        return false;
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

