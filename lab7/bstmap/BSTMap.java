package bstmap;

import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private class BSTNode {
        K key;
        V value;
        BSTNode left;
        BSTNode right;

        BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private BSTNode root;
    private int size;

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int size(){
        return size;
    }

    @Override
    public V get(K key) {
        return get(root, key);
    }

    private V get(BSTNode node, K key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return get(node.left, key);
        } else if (cmp > 0) {
            return get(node.right, key);
        } else {
            return node.value;
        }
    }

    @Override
    public boolean containsKey(K key) {
        return containsKey(root, key);
    }

    private boolean containsKey(BSTNode node, K key) {
        if (node == null) {
            return false;
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return containsKey(node.left, key);
        } else if (cmp > 0) {
            return containsKey(node.right, key);
        } else {
            return true;
        }
    }

    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private BSTNode put(BSTNode node, K key, V value) {
        if (node == null) {
            size += 1;
            return new BSTNode(key, value);
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = put(node.left, key, value);
        } else if (cmp > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }

        return node;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();
        traversal(root, set);
        return set;
    }

    private void traversal(BSTNode node, Set<K> set) {
        if (node == null) {
            return;
        }
        traversal(node.left, set);
        set.add(node.key);
        traversal(node.right, set);
    }

    @Override
    public V remove(K key) {
        V oldValue = get(key);

        if (oldValue == null) {
            return null;
        }

        root = remove(root, key);
        size -= 1;
        return oldValue;
    }

    private BSTNode remove(BSTNode node, K key) {
        if (node == null) {
            return null;
        }

        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = remove(node.left, key);
        } else if (cmp > 0) {
            node.right = remove(node.right, key);
        } else {
            if (node.left == null) {
                return node.right;
            }

            if (node.right == null) {
                return node.left;
            }

            BSTNode successor = findMin(node.right);
            node.key = successor.key;
            node.value = successor.value;
            node.right = remove(node.right, successor.key);
        }
        return node;
    }

    private BSTNode findMin(BSTNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    @Override
    public V remove(K key, V value) {
        V oldValue = get(key);
        if (oldValue == null) {
            return null;
        }

        if (!oldValue.equals(value)) {
            return null;
        }

        return remove(key);
    }

    @Override
    public Iterator<K> iterator() {
        List<K> keys = new ArrayList<>();
        inorder(root, keys);
        return keys.iterator();
    }

    private void inorder(BSTNode node, List<K> keys) {
        if (node == null) {
            return;
        }

        inorder(node.left, keys);
        keys.add(node.key);
        inorder(node.right, keys);
    }

    public void printInOrder() {
        printInOrder(root);
    }

    private void printInOrder(BSTNode node) {
        if (node == null) {
            return;
        }
        printInOrder(node.left);
        System.out.println(node.key + " -> " + node.value);
        printInOrder(node.right);
    }
}
