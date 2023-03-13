package BinarySearch;

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
        toString = toString.substring(0, toString.length() - 2);
        toString += "]";
        return toString;
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
        toString = toString.substring(0, toString.length() - 2);
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
        toString = toString.substring(0, toString.length() - 2);
        toString += "]";
        return toString;
    }

    //rotate right
    public TreeNode<E> rotateRight(TreeNode<E> node){
        TreeNode<E> temp = node.left;
        node.left = temp.right;
        temp.right = node;
        return temp;
    }

    public void rotateRight(){
        root = rotateRight(root);
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
