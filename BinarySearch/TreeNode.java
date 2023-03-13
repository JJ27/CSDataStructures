package BinarySearch;

public class TreeNode<E extends Comparable<E>>{
    E data;
    TreeNode<E> left;
    TreeNode<E> right;

    public TreeNode(E data){
        this.data = data;
        left = null;
        right = null;
    }

    public void add(E n){
        if(n.compareTo(data) < 0){
            if(left == null){
                left = new TreeNode<E>(n);
            }else{
                left.add(n);
            }
        }else{
            if(right == null){
                right = new TreeNode<E>(n);
            }else{
                right.add(n);
            }
        }
    }

    public boolean contains(E n){
        if(n.compareTo(data) == 0){
            return true;
        }else if(n.compareTo(data) < 0){
            if(left == null){
                return false;
            }else{
                return left.contains(n);
            }
        }else{
            if(right == null){
                return false;
            }else{
                return right.contains(n);
            }
        }
    }

    public TreeNode<E> remove(E n){
        if(n.compareTo(data) < 0){
            if(left != null){
                left = left.remove(n);
            }
        }else if(n.compareTo(data) > 0){
            if(right != null){
                right = right.remove(n);
            }
        }else{
            if(left == null){
                return right;
            }else if(right == null){
                return left;
            }else{
                data = right.min();
                right = right.remove(data);
            }
        }
        return this;
    }

    public E min(){
        if(left == null){
            return data;
        }else{
            return left.min();
        }
    }

    public E max(){
        if(right == null){
            return data;
        }else{
            return right.max();
        }
    }

    //get method
    public E get(E n){
        if(n.compareTo(data) == 0){
            return data;
        }else if(n.compareTo(data) < 0){
            if(left == null){
                return null;
            }else{
                return left.get(n);
            }
        }else{
            if(right == null){
                return null;
            }else{
                return right.get(n);
            }
        }
    }

    public TreeNode<E> getLeft(){
        return left;
    }

    public TreeNode<E> getRight(){
        return right;
    }

    public void setRight(TreeNode<E> right){
        this.right = right;
    }

    public void setLeft(TreeNode<E> left){
        this.left = left;
    }

    public E getValue(){
        return data;
    }

    @Override
    public String toString(){
        return data.toString();
    }

    public void printInOrder(){
        if(left != null){
            left.printInOrder();
        }
        System.out.println(data);
        if(right != null){
            right.printInOrder();
        }
    }
}
