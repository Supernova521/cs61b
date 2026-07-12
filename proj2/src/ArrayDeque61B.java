import java.util.*;

public class ArrayDeque61B<T> implements Deque61B<T>{
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    private int getFirstIndex() {
        int index = nextFirst;
        if (index < 0) {
            index = items.length - 1;
            nextFirst = items.length - 1;
        }
        else if (index >= items.length) {
            index = 0;
            nextFirst = 0;
        }
        return index;
    }

    private int getLastIndex() {
        int index = nextLast;
        if (index < 0) {
            index = items.length - 1;
            nextLast = items.length - 1;
        }
        else if (index >= items.length) {
            index = 0;
            nextLast = 0;
        }
        return index;
    }

    private int getIndex(int index) {
        if (index < 0) {
            index = items.length - 1 + index;
        } else if (index >= items.length){
            index = index - items.length;
        }
        return index;
    }

    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        int index = getFirstIndex();
        items[index] = x;
        nextFirst -= 1;
        size += 1;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        int index = getLastIndex();
        items[index] = x;
        nextLast += 1;
        size += 1;
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        if (size == 0) {
            return returnList;
        }
        else {
            for (int i = 0; i < size; i++) {
               returnList.add(this.get(i));
            }
        }
        return returnList;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Return the element at the front of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public T getFirst() {
        int index = getIndex(nextFirst + 1);
        return items[index];
    }

    /**
     * Return the element at the back of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public T getLast() {
        int index = getIndex(nextLast - 1);
        return items[index];
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        if (items.length >= 16 && items.length >= size * 4) {
            resize(size / 2);
        }
        int index = getIndex(nextFirst + 1);
        T result = items[index];
        items[index] = null;
        nextFirst = getIndex(nextFirst + 1);
        size -= 1;
        return result;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if (items.length >= 16 && items.length >= size * 4) {
            resize(size / 2);
        }
        int index = getIndex(nextLast - 1);
        T result = items[index];
        items[index] = null;
        nextLast = getIndex(nextLast - 1);
        size -= 1;
        return result;
    }

    /**
     * The Deque61B abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively. Returns
     * null if index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        } else {
            int realIndex = getIndex(nextFirst + 1 + index);
            return items[realIndex];
        }
    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively. Returns null if
     * index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for ArrayDeque61B.");
    }

    public void resize(int capacity) {
        T[] resized = (T[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            resized[i] = this.get(i);
        }
        items = resized;
        nextFirst = -1;
        nextLast = size;
    }

    class ArrayDequeIterator implements Iterator<T> {
        int wizPos;
        int remaining;

        ArrayDequeIterator() {
            wizPos = nextFirst + 1;
            remaining = size;
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return remaining > 0;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
            T thingToReturn = items[getIndex(wizPos)];
            wizPos += 1;
            remaining -= 1;
            return thingToReturn;
        }
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {return new ArrayDequeIterator();}

    @Override
    public boolean equals(Object o) {
        if (o instanceof ArrayDeque61B<?> Furina) {
            if (size == Furina.size()) {
                for (int i = 0; i < size; i++) {
                    if (this.get(i) != Furina.get(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder returnString = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i != size - 1) {
                returnString.append(this.get(i)).append(", ");
            } else {
                returnString.append(this.get(i));
            }
        }
        returnString.append("]");
        return returnString.toString();
    }
}
