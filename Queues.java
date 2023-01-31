import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Queues {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File(System.getProperty("user.dir"),"ap.txt");
        Scanner reader = new Scanner(file);
        Queue<Word> q = new LinkedList<Word>();
        PriorityQueue<Word> pq = new PriorityQueue<Word>();
        PriorityQueue<Word> pq2 = new PriorityQueue<Word>(Collections.reverseOrder());
        for(String s: reader.nextLine().split(" ")){
            Word w = new Word(s);
            q.add(w);
            pq.add(w);
            pq2.add(w);
        }
        //print p and pq side by side
        System.out.println("First Task:\n");
        System.out.printf("%-15s %-15s %s%n", "Queue", "Priority Queue", "Reversed");
        while(!q.isEmpty()){
            System.out.printf("%-15s %-15s %s%n", q.remove().getWord(), pq.remove().getWord(), pq2.remove().getWord());
        }

        reader = new Scanner(new File("CarData.txt"));
        reader.nextLine();
        Stack<Car> cars = new Stack<Car>();
        Queue<Car> carq = new LinkedList<Car>();
        PriorityQueue<Car> carpq = new PriorityQueue<Car>();
        while(reader.hasNextLine()){
            int[] data = Arrays.stream(reader.nextLine().split("\t")).peek(s -> s.trim()).mapToInt(Integer::parseInt).toArray();
            cars.add(new Car(data[5], data[1], data[3], data[2], data[4], data[7], data[0], data[6]));
            carq.add(new Car(data[5], data[1], data[3], data[2], data[4], data[7], data[0], data[6]));
            carpq.add(new Car(data[5], data[1], data[3], data[2], data[4], data[7], data[0], data[6]));
        }
        System.out.println("\nSecond Task:\n");
        System.out.printf("%-15s %-15s %s%n", "Stack", "Queue", "Priority Queue");
        while(!cars.isEmpty()){
            System.out.printf("%-15s %-15s %s%n", cars.pop(), carq.remove(), carpq.remove());
        }
    }
    public static class Word implements Comparable<Word>{
        public String getWord() {
            return word;
        }

        private final String word;

        public Word(String word) {
            this.word = word;
        }

        @Override
        public int compareTo(Word o) {
            return this.word.toLowerCase().compareTo(o.getWord().toLowerCase());
        }
    }

    public static class Car implements Comparable<Car>{
        public ArrayList<Integer> getParams() {
            return params;
        }

        private ArrayList<Integer> params;

        public Car(int a, int mpg, int hp, int engine, int weight, int cylinders, int carID, int countryOrigin) {
            params = new ArrayList<Integer>();
            params.add(a);
            params.add(mpg);
            params.add(hp);
            params.add(engine);
            params.add(weight);
            params.add(cylinders);
            params.add(carID);
            params.add(countryOrigin);
        }

        @Override
        public int compareTo(Car o) {
            for(int i = 0; i < params.size(); i++){
                if(params.get(i) > o.getParams().get(i)){
                    return 1;
                }else if(params.get(i) < o.getParams().get(i)){
                    return -1;
                }
            }
            return 0;
        }

        @Override
        public String toString() {
            return "CarID: " + params.get(6);
        }
    }
}