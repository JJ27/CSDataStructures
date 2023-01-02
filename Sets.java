import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Sets {
    public static void main(String[] args){
        ArrayList<HashSet<Integer>> list = new ArrayList<HashSet<Integer>>();
        for (int i = 0; i < (int)(Math.random() * 11 + 2); i++){
            HashSet<Integer> set = new HashSet<Integer>();
            while (set.size() < 10){
                set.add((int)(Math.random() * 20 + 1));
            }
            list.add(set);
        }
        System.out.println("List: " + list);
        Set<Integer> intersection = new HashSet<Integer>(list.get(0));
        for (int i = 1; i < list.size(); i++){
            intersection.retainAll(list.get(i));
        }
        System.out.println("Intersection: " + intersection);

        Set<Integer> union = new HashSet<Integer>();
        for (int i = 0; i < list.size(); i++){
            union.addAll(list.get(i));
        }
        System.out.println("Union: " + union);

        Set<Integer> evenIntersection = new HashSet<Integer>();
        for (int i = 0; i < list.size(); i++){
            evenIntersection = evenIntersection(evenIntersection, list.get(i));
        }
        System.out.println("Even Intersection: " + evenIntersection);

        Set<Integer> evenUnion = new HashSet<Integer>();
        for (int i = 0; i < list.size(); i++){
            evenUnion = evenUnion(evenUnion, list.get(i));
        }
        System.out.println("Even Union: " + evenUnion);
    }
    public static <E> Set<E> union(Set<E> s1, Set<E> s2){
        Set<E> result = new java.util.HashSet<E>(s1);
        result.addAll(s2);
        return result;
    }
    public static <E> Set<E> intersection(Set<E> s1, Set<E> s2){
        Set<E> result = new java.util.HashSet<E>(s1);
        result.retainAll(s2);
        return result;
    }
    public static <E> boolean isSubset(Set<E> s1, Set<E> s2){
        return s1.containsAll(s2);
    }
    public static <E> Set<E> evenIntersection(Set<E> s1, Set<E> s2){
        Set<E> result = new java.util.HashSet<E>(s1);
        result.retainAll(s2);
        Set<E> even = new java.util.HashSet<E>();
        for (E e : result){
            if ((Integer)e % 2 == 0){
                even.add(e);
            }
        }
        return even;
    }
    public static <E> Set<E> evenUnion(Set<E> s1, Set<E> s2){
        Set<E> result = new java.util.HashSet<E>(s1);
        result.addAll(s2);
        Set<E> even = new java.util.HashSet<E>();
        for (E e : result){
            if ((Integer)e % 2 == 0){
                even.add(e);
            }
        }
        return even;
    }
}
