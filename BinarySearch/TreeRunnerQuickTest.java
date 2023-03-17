package BinarySearch;

public class TreeRunnerQuickTest
{
    public TreeRunnerQuickTest()
    {
        TreeSet<Integer> tree=new TreeSet<Integer>();

        tree.add(10);
        tree.add(6);
        tree.add(12);
        tree.add(3);
        tree.add(7);
        tree.add(15);
        tree.add(4);
        tree.add(5);
        tree.add(10);
        tree.add(11);
        tree.add(19);
        System.out.println("PreOrder: "+tree.preOrder());
        System.out.println("InOrder: "+tree.inOrder());
        System.out.println("PostOrder: "+tree.postOrder());
        System.out.println("Size: "+tree.size());



        System.out.println("\n\nRemoving: ");
        tree.remove(10);
        System.out.println("Removed 10");
        System.out.println(tree.preOrder());
        System.out.println("Size: "+tree.size());
        tree.remove(3);
        System.out.println("\nRemoved 3");
        System.out.println(tree.preOrder());
        System.out.println("Size: "+tree.size());
        tree.remove(7);
        System.out.println("\nRemoved 7");
        System.out.println(tree.preOrder());
        System.out.println("Size: "+tree.size());
        tree.remove(15);
        System.out.println("\nRemoved 15");
        System.out.println(tree.preOrder());
        System.out.println("Size: "+tree.size());
        tree.remove(4);
        System.out.println("\nRemoved 4");
        System.out.println(tree.preOrder());
        System.out.println("Size: "+tree.size());
        tree.remove(5);
        System.out.println("\nRemoved 5");
        System.out.println(tree.preOrder());
        System.out.println("Size: "+tree.size());
        tree.remove(6);
        System.out.println("\nRemoved 6");
        System.out.println(tree.preOrder());
        System.out.println("Size: "+tree.size());
        tree.remove(12);
        System.out.println("\nRemoved 12");
        System.out.println(tree.preOrder());
        System.out.println("Size: "+tree.size());
        tree.remove(10);
        System.out.println("\nRemoved 10");
        System.out.println(tree.preOrder());
        System.out.println("Size: "+tree.size());
        tree.remove(19);
        System.out.println("\nRemoved 19");
        System.out.println(tree.preOrder());
        System.out.println("Size: "+tree.size());
        tree.remove(11);
        System.out.println("\nRemoved 11");
        System.out.println(tree.preOrder());
        System.out.println("Size: "+tree.size());

    }


    public static void main(String[] args)
    {
        TreeRunnerQuickTest app=new TreeRunnerQuickTest();
    }

}