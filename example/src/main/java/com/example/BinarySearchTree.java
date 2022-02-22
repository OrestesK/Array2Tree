package com.example;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree {

    public BinarySearchTree(int[] arr) {
        Node<Integer> root = arrToBST(arr, 0, arr.length - 1);
        printTree(root);
        System.out.println("");
        System.out.println("");
        printVisualTree(root);
    }

    private Node<Integer> arrToBST(int[] arr, int start, int end) {
        if (start > end) {
            return null; // this returns null if there is nothing to the left (less) than the current
                         // node
        }
        int mid = (start + end) / 2;
        Node<Integer> root = new Node<>(arr[mid]); // makes a node which will serve as the initial point(root)
        root.left = arrToBST(arr, start, mid - 1); // recursevly calls the left array
        root.right = arrToBST(arr, mid + 1, end); // recursevely calls the right array
        return root; // returns the finished node
    }

    private void printTree(Node<Integer> root) {
        if (root == null) {
            return;
        }
        System.out.print(root.data + " ");
        printTree(root.left); // prints left node, null handled at beginning
        printTree(root.right); // prints right node, null handled at beginning
    }

    class Node<T> { // node class, general <T>

        T data; // has a value
        Node<T> left, right; // has a left and right node

        Node(T data) {
            this.data = data; // constructor for Node, takes in data
            // left = right = null;
        }
    }

    public static void printVisualTree(Node<Integer> root) { // based on @MightyPork, GitHub
        List<List<String>> lines = new ArrayList<List<String>>();

        List<Node<Integer>> level = new ArrayList<Node<Integer>>();
        List<Node<Integer>> next = new ArrayList<Node<Integer>>();

        level.add(root);
        int nn = 1;
        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList<String>();
            nn = 0;

            for (Node<Integer> n : level) {
                if (n == null) {
                    line.add(null);
                    next.add(null);
                    next.add(null);
                } else {
                    String aa = "" + n.data;
                    line.add(aa);
                    if (aa.length() > widest)
                        widest = aa.length();

                    next.add(n.left);
                    next.add(n.right);

                    if (n.left != null)
                        nn++;
                    if (n.right != null)
                        nn++;
                }
            }

            if (widest % 2 == 1)
                widest++;

            lines.add(line);

            List<Node<Integer>> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {

                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null)
                                c = '└';
                        }
                    }
                    System.out.print(c);

                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {

                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            // print line of numbers
            for (int j = 0; j < line.size(); j++) {

                String f = line.get(j);
                if (f == null)
                    f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                // a number
                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            perpiece /= 2;
        }
    }
}
