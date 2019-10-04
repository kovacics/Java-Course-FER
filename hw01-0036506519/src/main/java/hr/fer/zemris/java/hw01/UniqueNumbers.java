package hr.fer.zemris.java.hw01;

import java.util.Scanner;


/**
 * Program creates and fill binary tree with user input. Program keeps asking for new input until
 * user give 'kraj' command which prints all binary tree nodes sorted.
 * <p>
 * In case of invalid input, user gets error message and is asked for new input.
 */
public class UniqueNumbers {

    /**
     * Method adds new node in the sorted binary tree. If the node is already
     * present, then it just skips that node.
     *
     * @param root  Root node of the binary tree
     * @param value Value of the node we want to add in binary tree
     * @return if root is null, returns new node, otherwise returns root
     */
    public static TreeNode addNode(TreeNode root, int value) {
        if (root == null) {
            TreeNode tn = new TreeNode();
            tn.value = value;
            tn.left = tn.right = null;
            return tn;
        } else if (value < root.value) {
            root.left = addNode(root.left, value);
        } else if (value > root.value) {
            root.right = addNode(root.right, value);
        }

        return root;
    }


    /**
     * Counts and returns number of nodes in binary tree
     *
     * @param root root node of the binary tree
     * @return number of nodes in binary tree
     */
    public static int size(TreeNode root) {
        if (root == null) return 0;
        return 1 + size(root.left) + size(root.right);
    }


    /**
     * Method checks if the binary tree contains node with specific value
     *
     * @param root  root node of the binary tree
     * @param value value of the node we are checking if exist in the tree
     * @return true if tree contains node, false otherwise
     */
    public static boolean containsValue(TreeNode root, int value) {
        if (root == null) {
            return false;
        } else if (root.value == value) {
            return true;
        } else if (value < root.value) {
            return containsValue(root.left, value);
        } else {
            return containsValue(root.right, value);
        }
    }

    /**
     * Prints whole binary tree ascending.
     *
     * @param root binary tree root node
     */
    private static void printTreeAscending(TreeNode root) {
        if (root == null) return;

        printTreeAscending(root.left);
        System.out.printf("%d ", root.value);
        printTreeAscending(root.right);
    }

    /**
     * Prints whole binary tree descending.
     *
     * @param root binary tree root node
     */
    private static void printTreeDescending(TreeNode root) {
        if (root == null) return;

        printTreeDescending(root.right);
        System.out.printf("%d ", root.value);
        printTreeDescending(root.left);
    }

    /**
     * Method that executes first.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        TreeNode root = null;

        while (true) {
            System.out.print("Unesite broj > ");
            String s = sc.next();

            if (!s.equals("kraj")) {
                try {
                    int value = Integer.parseInt(s);
                    if (containsValue(root, value)) {
                        System.out.println("Broj već postoji. Preskačem.");
                        continue;
                    }
                    root = addNode(root, value);
                    System.out.println("Dodano.");
                } catch (NumberFormatException e) {
                    System.out.printf("'%s' nije cijeli broj.%n", s);
                }
            } else {
                System.out.print("Ispis od najmanjeg: ");
                printTreeAscending(root);
                System.out.print("\nIspis od najvećeg: ");
                printTreeDescending(root);
                break;
            }
        }
        sc.close();
    }

    /**
     * Represents node of a binary tree
     */
    public static class TreeNode {

        /**
         * Left child of the parent node
         */
        public TreeNode left;

        /**
         * Right child of the parent node
         */
        public TreeNode right;

        /**
         * Value of the node
         */
        public int value;
    }
}
