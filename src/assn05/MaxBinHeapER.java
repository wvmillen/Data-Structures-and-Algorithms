package assn05;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MaxBinHeapER  <V, P extends Comparable<P>> implements BinaryHeap<V, P> {

    private List<Prioritized<V,P>> _heap;

    /**
     * Constructor that creates an empty heap of hospital.Prioritized objects.
     */
    public MaxBinHeapER() {
        _heap = new ArrayList<>();
    }

    @Override
    public int size() {
        return _heap.size();
    }

    static int leftChildIndex(int index) {
        return (2 * index + 1);
    }

    static int rightChildIndex(int index) {
        return (2 * index + 2);
    }

    static int parentIndex(int index) {
        return ((index - 1) / 2);
    }

    boolean validIndex(int index) {
        if ((index >= 0) && (index <= _heap.size() - 1)) {
            return true;
        } else {
            return false;
        }
    }

    boolean hasLeftChild(int index) {
        return validIndex(leftChildIndex(index));
    }

    boolean hasRightChild(int index) {
        return validIndex(rightChildIndex(index));
    }


    int bubbleUp(int index){
        if (index == 0) {
            return(index);
        }
        else {
            Patient child = (Patient) _heap.get(index);    // child is the element that needs to be bubbled-up
            Patient parent = (Patient) _heap.get(parentIndex(index));
            if (child.getPriority().compareTo(parent.getPriority()) > 0){
                _heap.set(parentIndex(index), child);
                _heap.set(index, parent);
                return(bubbleUp(parentIndex(index)));
            }
            else{
                return(index);
            }
        }
    }

    void bubbleDown(int index){
        Patient parent = (Patient) _heap.get(index);
        int leftChildIndex = leftChildIndex(index);
        int rightChildIndex = rightChildIndex(index);

        int largestChildIndex = index;

        if(!hasLeftChild(index) && !hasRightChild(index)){ //has no children
            return;
        }

        if (hasLeftChild(index) && (hasLeftChild(index) && (_heap.get(leftChildIndex).getPriority().compareTo(_heap.get(index).getPriority()) > 0))) {
            largestChildIndex = leftChildIndex;
        }
        if (hasRightChild(index) && _heap.get(rightChildIndex).getPriority().compareTo(_heap.get(largestChildIndex).getPriority()) > 0) {
            largestChildIndex = rightChildIndex;
        }

        if (largestChildIndex != index) {
            swap(index, largestChildIndex);
            bubbleDown(largestChildIndex);
        }

    }

    private void swap(int index1, int index2) {
        Prioritized<V, P> temp = _heap.get(index1);
        _heap.set(index1, _heap.get(index2));
        _heap.set(index2, temp);
    }


    // TODO (Task 2A): enqueue
    public void enqueue(V value) {
        Patient newPatient = new Patient(value);
        if(_heap.isEmpty()){   //add if empty
            _heap.add(newPatient);
            return;
        }
        else{
            enqueue((V) newPatient.getValue(), (P) newPatient.getPriority()); //if not empty get its value and priority and then use next enqueue function to insert
        }

    }

    // TODO (Task 2A): enqueue
    @Override
    public void enqueue(V value, P priority) {
        Patient newPatient = new Patient<>(value, priority);
        if (_heap.isEmpty()) {
            _heap.add(newPatient);
            return;
        }
        _heap.add(newPatient);
        bubbleUp(_heap.size()-1);
    }

    // TODO (Task 2A): dequeue
    @Override
    public V dequeue() {
        if (_heap.isEmpty()) {
            return null;
        }
        else if(_heap.size() == 1){
            return _heap.remove(0).getValue();
        }
        else{
            Prioritized<V, P> retValue = _heap.get(0);
            _heap.set(0, _heap.remove(_heap.size()-1));
            bubbleDown(0);
            return retValue.getValue();
        }
    }

    // TODO (Task 2A): getMax
    @Override
    public V getMax() {
        if(_heap.isEmpty()) {
            return null;
        }
        else{
           return  _heap.get(0).getValue();
        }
    }

    // TODO (part 2B) : updatePriority
    public void updatePriority(V value, P newPriority) {
        int patientIndex = -1;
        for (int i = 0; i < _heap.size(); i++) {
            if (_heap.get(i).getValue().equals(value)) {
                patientIndex = i;
                break;
            }
        }
        if (patientIndex == -1) {return;};

        P oldPriority = _heap.get(patientIndex).getPriority();
        _heap.set(patientIndex, new Patient<V, P>(value, newPriority));

        if (newPriority.compareTo(oldPriority) > 0) {
            bubbleUp(patientIndex);
        } else {
            bubbleDown(patientIndex);
        }

    }

    // In your Patient class or the class implementing Prioritized





    /**
     * Constructor that builds a heap given an initial array of hospital.Prioritized objects.
     */
    // TODO (Task 3): overloaded constructor
    public MaxBinHeapER(Prioritized<V, P>[] initialEntries ) {
    }

    @Override
    public Prioritized<V, P>[] getAsArray() {
        Prioritized<V,P>[] result = (Prioritized<V, P>[]) Array.newInstance(Prioritized.class, size());
        return _heap.toArray(result);
    }

}
