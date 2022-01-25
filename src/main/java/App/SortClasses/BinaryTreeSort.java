package App.SortClasses;

import App.SortingInterface;
import java.util.Vector;

/**
 *
 * <h1>Binary Tree Sort</h1>
 * <h3>Best-Case: O(n * log(n))<br>Average-Case: O(n * log(n))<br>Worst-Case: O(n^2)<br>Stable: Yes</h3>
 * <p>This algorithm builds a binary search tree from the <br>elements (int[] in this case) with Nodes, and traverses<br> the tree in ascending order. The Binary Tree Sort<br> uses a recursive method to solve the problem.</p>
 * <a href="https://www.youtube.com/watch?v=n2MLjGeK7qA">Binary Tree Sort visualization</a>
 *
 * @author Andras Tarlos
 * @since 2022.01.22
 * @version 0.1.1
 */
public class BinaryTreeSort implements SortingInterface {
    private long writeChanges = 0;
    private long timeForSorting = 0;
    private long amountOfComparisons = 0;
    private long storageSpaceRequired = 0;
    private Node root;

    @Override
    public void run(Vector<Integer> array) {
        storageSpaceRequired += mc.getMemorySpace(array);
        int[] arr = ac.copyVectorToArray(array);
        root = null;
        timeForSorting = System.nanoTime();
        treeins(arr);
        inorderRec(root);
        timeForSorting = System.nanoTime() - timeForSorting;
    }

    /**
     * Creates a Node class
     */
    class Node {
        int key;
        Node left, right;

        public Node(int item) {
            key = item;
            left = right = null;
        }
    }

    /**
     * This methods main job is to call the methode insertRec()
     * @param key will be given to insertRec()
     */
    private void insert(int key) {
        root = insertRec(root, key);
    }

    /**
     * This is a recursive function that inserts a new key in the BST
     * @return a Node
     */
    private Node insertRec(Node root, int key) {
        amountOfComparisons++;
        if (root == null) {
            root = new Node(key);
            writeChanges++;
            return root;
        }
        amountOfComparisons++;
        if (key < root.key) {
            root.left = insertRec(root.left, key);
        } else if (key > root.key) {
            amountOfComparisons++;
            root.right = insertRec(root.right, key);
        }
        writeChanges++;
        return root;
    }

    /**
     * Does inorder traversal of Binary Search Tree (BST)
     * @param root
     */
    private void inorderRec(Node root) {
        amountOfComparisons++;
        if (root != null) {
            inorderRec(root.left);
            inorderRec(root.right);
        }
    }

    /**
     *
     * @param arr
     */
    private void treeins(int[] arr) {
        for (int j : arr) {
            storageSpaceRequired += 32;
            insert(j);
        }
    }

    @Override
    public long getWriteChanges() {
        return writeChanges;
    }

    @Override
    public long getTimeForSorting() {
        return timeForSorting;
    }

    @Override
    public long getAmountOfComparisons() {
        return amountOfComparisons;
    }

    @Override
    public long getStorageSpaceRequired() {
        return storageSpaceRequired;
    }

    @Override
    public String getAlgorithmName() {
        return "BinaryTreeSort";
    }
}