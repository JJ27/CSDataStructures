package BinarySearch;

public class TreeRunnerQuickTest
{
    public TreeRunnerQuickTest()
    {
        TreeSet<Integer> tree= new TreeSet<Integer>();
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
        System.out.println(tree.preOrder());
    }
    public static void main(String[] args)
    {
        TreeRunnerQuickTest app=new TreeRunnerQuickTest();
    }
}
