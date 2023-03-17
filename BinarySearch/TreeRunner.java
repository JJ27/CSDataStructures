package BinarySearch;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class TreeRunner {
    public static void main(String[] args){
        //fill a treeset with 30 random numbers from 1 to 100
        TreeSet<Integer> tree = new TreeSet<Integer>();
        for(int i = 0; i < 30; i++){
            int q = (int)(Math.random()*100)+1;
            System.out.print(q + " ");
            tree.add(q);
        }
        System.out.println();
        System.out.println("Size: "  + tree.size());
        TreeSet<Integer> copyPre = tree.fromArray(tree.toArray(tree.preOrder()));
        System.out.println("CopyPre Pre: " + copyPre.preOrder());
        System.out.println("CopyPre In: " + copyPre.inOrder());
        System.out.println("Copy PrePost: " + copyPre.postOrder());

        TreeSet<Integer> copyIn = tree.fromArray(tree.toArray(tree.inOrder()));
        System.out.println("CopyIn Pre: " + copyIn.preOrder());
        System.out.println("CopyIn In: " + copyIn.inOrder());
        System.out.println("CopyIn Post: " + copyIn.postOrder());

        TreeSet<Integer> copyPost = tree.fromArray(tree.toArray(tree.postOrder()));
        System.out.println("CopyPost Pre: " + copyPost.preOrder());
        System.out.println("CopyPost In: " + copyPost.inOrder());
        System.out.println("CopyPost Post: " + copyPost.postOrder());

        //fill a new TreeSet with 20 random String values
        TreeSet<String> tree2 = new TreeSet<String>();
        for(int i = 0; i < 20; i++){
            String q = "";
            for(int j = 0; j < 5; j++){
                q += (char)((int)(Math.random()*26)+97);
            }
            System.out.print(q + " ");
            tree2.add(q);
        }
        System.out.println();
        System.out.println("Size: "  + tree2.size());
        System.out.println("Tree2 Pre: " + tree2.preOrder());
        System.out.println("Tree2 In: " + tree2.inOrder());
        System.out.println("Tree2 Post: " + tree2.postOrder());
        System.out.println();

        tree2.rotateRight();
        System.out.println("Tree2R1 Pre: " + tree2.preOrder());
        System.out.println("Tree2R1 In: " + tree2.inOrder());
        System.out.println("Tree2R1 Post: " + tree2.postOrder());
        System.out.println();

        tree2.rotateRight();
        System.out.println("Tree2R2 Pre: " + tree2.preOrder());
        System.out.println("Tree2R2 In: " + tree2.inOrder());
        System.out.println("Tree2R2 Post: " + tree2.postOrder());
        System.out.println();

        tree2.rotateRight();
        System.out.println("Tree2R3 Pre: " + tree2.preOrder());
        System.out.println("Tree2R3 In: " + tree2.inOrder());
        System.out.println("Tree2R3 Post: " + tree2.postOrder());
        System.out.println();

        tree2.rotateLeft();
        System.out.println("Tree2L1 Pre: " + tree2.preOrder());
        System.out.println("Tree2L1 In: " + tree2.inOrder());
        System.out.println("Tree2L1 Post: " + tree2.postOrder());
        System.out.println();

        tree2.rotateLeft();
        System.out.println("Tree2L2 Pre: " + tree2.preOrder());
        System.out.println("Tree2L2 In: " + tree2.inOrder());
        System.out.println("Tree2L2 Post: " + tree2.postOrder());
        System.out.println();

        tree2.rotateLeft();
        System.out.println("Tree2L3 Pre: " + tree2.preOrder());
        System.out.println("Tree2L3 In: " + tree2.inOrder());
        System.out.println("Tree2L3 Post: " + tree2.postOrder());
        System.out.println();

        //fill a new TreeSet with 10 random integer values and simultaneously fill an ArrayList with the same values
        TreeSet<Integer> tree3 = new TreeSet<Integer>();
        ArrayList<Integer> list = new ArrayList<Integer>();
        for(int i = 0; i < 10; i++){
            int q = (int)(Math.random()*100)+1;
            System.out.print(q + " ");
            tree3.add(q);
            list.add(q);
        }
        //randomly remove each value from the ArrayList and TreeSet and display the TreeSet in PreOrder, InOrder and PostOrder traversals after each one
        System.out.println();
        System.out.println("Size: "  + tree3.size());
        System.out.println("Tree3 Pre: " + tree3.preOrder());
        System.out.println("Tree3 In: " + tree3.inOrder());
        System.out.println("Tree3 Post: " + tree3.postOrder());
        System.out.println();
        for(int i = 0; i < 10; i++){
            int q = (int)(Math.random()*list.size());
            System.out.println("Removing " + list.get(q));
            System.out.println("Success: " + tree3.remove(list.get(q)));
            list.remove(q);
            System.out.println("Size: "  + tree3.size());
            System.out.println("Tree3 Pre: " + tree3.preOrder());
            System.out.println("Tree3 In: " + tree3.inOrder());
            System.out.println("Tree3 Post: " + tree3.postOrder());
            System.out.println();
        }
    }
}
