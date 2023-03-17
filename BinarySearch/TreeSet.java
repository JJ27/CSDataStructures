package BinarySearch;

import BinarySearch.TreeNode;

import java.util.ArrayList;

public class TreeSet<E extends Comparable<E>> {
    private TreeNode<E> root;
    private int size;
    String toString;

    public TreeSet() {
        root = null;
        size = 0;
    }

    public void add(E n) {
        if (root == null) {
            root = new TreeNode<E>(n);
        } else {
            //add if n doesn't already exist
            if (!root.contains(n)) {
                root.add(n);
            }
        }
        size++;
    }

    public boolean contains(E n) {
        if (root == null) {
            return false;
        } else {
            return root.contains(n);
        }
    }

    public boolean remove(E n) {
        if (root == null) {
            return false;
        } else {
            if (root.contains(n)) {
                root = root.remove(n);
                size--;
                return true;
            } else {
                return false;
            }
        }
    }

    public E get(E n) {
        if (root == null) {
            return null;
        } else {
            return root.get(n);
        }
    }

    //preOrder traversal
    public void preOrder(TreeNode<E> node){
        if(node != null){
            toString += node.data + ", ";
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    public String preOrder(){
        toString = "[";
        preOrder(root);
        try {
            toString = toString.substring(0, toString.length() - 2);
        } catch(StringIndexOutOfBoundsException e) {
            //do nothing
        }
        toString += "]";
        return toString;
    }

    public void fill(ArrayList<E> list){
        for(int i = 0; i < list.size(); i++){
            add(list.get(i));
        }
    }

    public int[] toArray(String s){
        s = s.substring(1, s.length() - 1);
        s = s.replaceAll(" ", "");
        String[] temp = s.split(",");
        int[] array = new int[temp.length];
        for(int i = 0; i < temp.length; i++){
            array[i] = Integer.parseInt(temp[i]);
        }
        return array;
    }

    public TreeSet<E> fromArray(int[] arr){
        TreeSet<E> tree = new TreeSet<E>();
        for(int i = 0; i < arr.length; i++){
            tree.add((E)Integer.valueOf(arr[i]));
        }
        return tree;
    }

    //inOrder traversal
    public void inOrder(TreeNode<E> node){
        if(node != null){
            inOrder(node.left);
            toString += node.data + ", ";
            inOrder(node.right);
        }
    }

    public String inOrder(){
        toString = "[";
        inOrder(root);
        try {
            toString = toString.substring(0, toString.length() - 2);
        } catch(StringIndexOutOfBoundsException e) {
            //do nothing
        }
        toString += "]";
        return toString;
    }

    //postOrder traversal
    public void postOrder(TreeNode<E> node){
        if(node != null){
            postOrder(node.left);
            postOrder(node.right);
            toString += node.data + ", ";
        }
    }

    public String postOrder(){
        toString = "[";
        postOrder(root);
        try{
            toString = toString.substring(0, toString.length() - 2);
        } catch(StringIndexOutOfBoundsException e) {
            //do nothing
        }
        toString += "]";
        return toString;
    }

    //rotate right
    public void rotateRight(TreeNode<E> node){
        if (root != null && root.getLeft() != null) {
            TreeNode<E> temp = root.getLeft();
            root.setLeft(temp.getRight());
            temp.setRight(root);
            root = temp;
        }
    }

    public void rotateRight(){
        rotateRight(root);
    }

    public TreeNode<E> rotateLeft(TreeNode<E> node){
        TreeNode<E> temp = node.getRight();
        node.right = temp.getLeft();
        temp.left = node;
        return temp;
    }

    public void rotateLeft(){
        if(root != null && root.left != null)
            root = rotateLeft(root);
        return;
    }

    public int size(TreeNode<E> node){
        if(node == null){
            return 0;
        }else{
            return 1 + size(node.left) + size(node.right);
        }
    }

    public int size(){
        return size(root);
    }

    public TreeNode<E> getRoot(){
        return root;
    }

    public String toString() {
        if (root == null) {
            return "[]";
        } else {
            return root.toString();
        }
    }
}