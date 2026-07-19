import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    int size;
    private class BSTNode {
        K item;
        V value;
        BSTNode left;
        BSTNode right;
        BSTNode(K item, V value, BSTNode left, BSTNode right) {
            this.item = item;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    private BSTNode root;
    private V tempValue;
    private V tempValue1;
    private K tempKey1;

    BSTMap() {
        size = 0;
        root = null;
        tempValue = null;
        tempValue1 = null;
        tempKey1 = null;
    }

   private BSTNode BSTPut(BSTNode root, K key, V value) {
        if (root == null) {
            size += 1;
            return new BSTNode(key, value, null, null);
        }
        if (key.compareTo(root.item) < 0) {
            root.left = BSTPut(root.left, key, value);
        } else if (key.compareTo(root.item) > 0) {
            root.right = BSTPut(root.right, key, value);
        } else {
            root.value = value;
        }
        return root;
   }

    private V BSTGet(BSTNode root, K key) {
        if (root == null) {
            return null;
        }
        if (root.item.equals(key)) {
            return root.value;
        } else if (key.compareTo(root.item) < 0) {
            return BSTGet(root.left, key);
        } else {
            return BSTGet(root.right, key);
        }
    }

    private boolean BSTContainsKey(BSTNode root, K key) {
        if (root == null) {
            return false;
        }
        if (root.item.equals(key)) {
            return true;
        }
        return BSTContainsKey(root.left, key) || BSTContainsKey(root.right, key);
    }

    private List<K> getList(BSTNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<K> leftList = getList(root.left);
        List<K> middleList = new ArrayList<>(Collections.singletonList(root.item));
        List<K> rightList = getList(root.right);
        leftList.addAll(middleList);
        leftList.addAll(rightList);
        return leftList;
    }

    private BSTNode BSTChange(BSTNode node, K key) {
        if (key.compareTo(node.item) < 0) {
            node.left = BSTChange(node.left, key);
        } else if (key.compareTo(node.item) > 0) {
            node.right = BSTChange(node.right, key);
        } else {
            tempKey1 = node.item;
            tempValue1 = node.value;
            if (node.left != null) {
                node = node.left;
            } else if (node.right != null) {
                node = node.right;
            } else {
                node = null;
            }
        }
        return node;
    }

    private BSTNode BSTRemove(BSTNode root, K key) {
       if (key.compareTo(root.item) < 0) {
           root.left = BSTRemove(root.left, key);
       } else if (key.compareTo(root.item) > 0) {
           root.right = BSTRemove(root.right, key);
       } else {
           if (root.left == null && root.right == null) {
               tempValue = root.value;
               root = null;
               size -= 1;
           } else if (root.left != null && root.right == null) {
               tempValue = root.value;
               root = root.left;
               size -= 1;
           } else if (root.left == null) {
               tempValue = root.value;
               root = root.right;
               size -= 1;
           } else {
               tempValue = root.value;
               List<K> list = getList(root);
               int index = list.indexOf(root.item);
               K target;
               if (index > 0) {
                   target = list.get(index - 1);
               } else {
                   target = list.get(1);
               }
               root = BSTChange(root, target);
               root.item = tempKey1;
               root.value = tempValue1;
               size -= 1;
           }
       }
       return root;
    }

    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
       root = BSTPut(root, key, value);
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        return BSTGet(root, key);
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        return BSTContainsKey(root, key);
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        List<K> list = getList(root);
        return new TreeSet<>(list);
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        }
        root = BSTRemove(root, key);
        return tempValue;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
       return new Map61BIterator();
    }

    private class Map61BIterator implements Iterator<K> {
        int wizPos;
        List<K> list;

        Map61BIterator() {wizPos = 0;}

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return wizPos < size;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public K next() {
            List<K> list = getList(root);
            K thingToReturn = list.get(wizPos);
            wizPos += 1;
            return thingToReturn;
        }
    }
}
