public class SuperListRunner {
    public static void main(String[] args) {
        SuperList<Integer> list = new SuperList<Integer>();
        for (int i = 0; i < 30; i++) {
            list.add((int) (Math.random() * 1000) + 1);
        }
        System.out.println(list);
        System.out.println("Size: " + list.size());
        SuperList<Integer> stack = new SuperList<Integer>();
        SuperList<Integer> queue = new SuperList<Integer>();
        list.reverse();
        for (int i = 29; i > -1; i--) {
            stack.push(list.remove(i));
        }
        System.out.println("Stack: " + stack);
        for (int i = 0; i < 30; i++) {
            queue.add(stack.pop());
        }
        System.out.println("Queue: " + queue);
        for (int i = 0; i < 30; i++) {
            list.add((int) (Math.random() * list.size()), queue.poll());
        }
        int sum = 0;
        for (int i = 0; i < 30; i++) {
            sum += list.get(i);
        }
        System.out.println("Sum: " + sum);
        sum = 0;
        for (int i = 0; i < 30; i += 2) {
            sum += list.get(i);
        }
        System.out.println("Sum of even: " + sum);
        sum = 0;
        for (int i = 1; i < 30; i += 2) {
            sum += list.get(i);
        }
        System.out.println("Sum of odd: " + sum);
        for (int i = 0; i < 30; i++) {
            if (list.get(i) % 2 == 0) {
                list.add(list.get(i));
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) % 3 == 0) {
                list.remove(i);
                i--;
            }
        }
        list.add(4, 55555);
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i) < list.get(j)) {
                    int temp = list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);
                }
            }
        }
        int median = 0;
        if (list.size() % 2 == 0) {
            median = (list.get(list.size() / 2) + list.get(list.size() / 2 - 1)) / 2;
        } else {
            median = list.get(list.size() / 2);
        }
        System.out.println("Median: " + median);
        System.out.print("Before: ");
        int q = list.size() / 2 - 1;
        list.reverse();
        for (int i = 0; i < q; i++) {
            System.out.print(list.get(i) + " ");
        }
        System.out.print("\nAfter: ");
        for (int i = q; i < list.size(); i++) {
            System.out.print(list.get(i) + " ");
        }
        SuperList<String> list2 = new SuperList<String>();
        String[] t = "The quick brown fox jumped over the lazy dog".split(" ");
        for (int i = 0; i < t.length; i++) {
            list2.add(t[i]);
        }
        System.out.println("\nString List: " + list2);
        for (int i = 0; i < list2.size(); i++) {
            if (list2.get(i).length() < 4) {
                list2.remove(i);
                i--;
            }
        }
        System.out.println("Removed List: " + list2);
        for (int i = 1; i < list2.size(); i++) {
            String temp = list2.get(i);
            int j = i - 1;
            while (j >= 0 && list2.get(j).compareTo(temp) > 0) {
                list2.set(j + 1, list2.get(j));
                j--;
            }
            list2.set(j + 1, temp);
        }
        System.out.println("Sorted List: " + list2);
        double sum2 = 0;
        for (int i = 0; i < list2.size(); i++) {
            sum2 += list2.get(i).length();
        }
        System.out.println("Average word length: " + sum2 / list2.size());
    }
}