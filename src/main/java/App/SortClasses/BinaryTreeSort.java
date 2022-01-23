package App.SortClasses;

import App.SortingInterface;
import java.util.Vector;

/**
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

    class Node {
        int key;
        Node left, right;

        public Node(int item) {
            key = item;
            left = right = null;
        }
    }

    public void insert(int key) {
        root = insertRec(root, key);
    }

    public Node insertRec(Node root, int key) {
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

    public void inorderRec(Node root) {
        amountOfComparisons++;
        if (root != null) {
            inorderRec(root.left);
            inorderRec(root.right);
        }
    }

    public void treeins(int[] arr) {
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