package FirstSix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LiftTask {
    private Level[] floors;
    private Lift lift;
    static ArrayList<String> pieces;

    public LiftTask(Level[] floors, int capacity) {
        this.floors = floors;
        this.lift = new Lift(floors[0], capacity);
    }

    public static void main(String[] args){
        File filename = new File("FirstSix/TheLiftFile.txt");
        try {
            BufferedReader input = new BufferedReader(new FileReader(filename));
            String text;
            String e = "";
            while ((text = input.readLine()) != null) {
                e += text + "q";
            }
            pieces = new ArrayList<String>(Arrays.asList(e.split("Floors: ")));
        } catch (IOException io) {
            System.err.println("File error");
        }
        pieces.remove(0);
        for(String s: pieces){
            String prematrix = "{";
            String[] split = s.split("q");
            int floors = Integer.parseInt(split[0]);
            for(int i = 1; i <= floors; i++){
                if(split[i].isEmpty() && i == floors){
                    prematrix += "{" + (i-1) + "},";
                } else {
                    prematrix += "{" + split[i] + "},";
                }
            }
            int capacity = Integer.parseInt(split[split.length - 1].substring(10));
            prematrix = prematrix.substring(0, prematrix.length() - 1);
            prematrix += "}";
            makeLift(LiftTask.Lift.intto2D(prematrix), capacity);
        }
    }

    public static void makeLift(final int[][] queues, final int personCapacity) {
        Level[] floors = generateFloors(queues);
        LiftTask lt = new LiftTask(floors, personCapacity);
        lt.lift.liftEngine();
    }

    private static Level[] generateFloors(int[][] elevator) {
        Level[] levels = new Level[elevator.length];
        for (int floorLevel = 0; floorLevel < elevator.length; floorLevel++) {
            Deque<Passenger> nonEmptyPassengers = Arrays.stream(elevator[floorLevel]).filter(c -> c != -1)
                    .mapToObj(Passenger::new)
                    .collect(Collectors.toCollection(LinkedList::new));
            levels[floorLevel] = new Level(floorLevel, nonEmptyPassengers);
        }
        return levels;
    }


    public class Lift {
        private Level floor;
        private List<Level> floorList;
        private List<Passenger> passengers;
        private int liftCapacity;
        private boolean up = true;

        public Lift(Level initial, int capacity) {
            this.floor = initial;
            this.liftCapacity = capacity;
            this.floorList = new LinkedList<>(Arrays.asList(initial));
            this.passengers = new LinkedList<>();
        }

        public static int[][] intto2D(String str){
            String[][] s = Arrays.stream(str.substring(2, str.length() - 2).split("\\},\\{")).map(e -> Arrays.stream(e.split("\\s*,\\s*")).toArray(String[]::new)).toArray(String[][]::new);
            int max = 0;
            for(String[] q: s){
                if(q.length > max)
                    max = q.length;
            }
            int[][] arr = new int[s.length][max];
            //ArrayList<ArrayList<Integer>> arr = new ArrayList<ArrayList<Integer>>();
            for(int i = 0; i < s.length; i++){
                for(int j = 0; j < max; j++){
                    try {
                        arr[i][j] = Integer.parseInt(s[i][j]);
                    } catch (Exception e) {
                        arr[i][j] = -1;
                    }
                }
            }
            return arr;
        }

        public void liftEngine() {
            boolean moved = false;
            while (true) {
                deliverPeople();
                getPeople();

                Optional<Level> floorNext = nextAvailableFloor();
                if (floorNext.isPresent() == false) {
                    floorNext = floorsInDirection()
                            .filter(floor -> floor.people.stream().filter(person -> floor.number != person.destLevel).findAny().isPresent())
                            .max(Comparator.comparingInt(this::getFloorDistance));
                    up = !up;
                }

                if (floorNext.isPresent()) {
                    changePosition(floorNext.get());
                    moved = true;
                } else if (!moved) {
                    break;
                } else {
                    moved = false;
                }
            }
            changePosition(floors[0]);
            System.out.println("Floors Visited: " + floorList.toString().replaceAll("\\[","").replaceAll("\\]",""));
        }

        private void deliverPeople() {
            for (int i = 0; i < passengers.size(); i++) {
                Passenger a = passengers.get(i);
                if (a.destLevel == floor.number) {
                    //Note to Self: Decrease i to account for the removal
                    passengers.remove(i);
                    i--;
                    floor.people.add(a);
                }
            }
        }

        private void getPeople() {
            List<Passenger> peopleComing;
            if (up) {
                peopleComing = floor.people.stream().filter(person -> floor.number < person.destLevel)
                        .limit(liftCapacity - passengers.size())
                        .collect(Collectors.toList());
            } else {
                peopleComing = floor.people.stream().filter(person -> floor.number > person.destLevel)
                        .limit(liftCapacity - passengers.size())
                        .collect(Collectors.toList());
            }
            peopleComing.forEach(person -> {
                floor.people.remove(person);
                passengers.add(person);
            });
        }

        private Optional<Level> nextAvailableFloor() {
            Optional<Level> firstFloorEntrance = floorsInDirection().filter(floor -> up ? floor.people.stream()
                    .filter(person -> floor.number < person.destLevel).findAny().isPresent():
                    floor.people.stream().filter(person -> floor.number > person.destLevel).findAny().isPresent()).findFirst();
            Optional<Level> firstFloorExit = floorsInDirection().filter(floor -> passengers.stream()
                    .map(Passenger::getDestLevel)
                    .anyMatch(personDestinationFloorLevel -> floor.getFloor() == personDestinationFloorLevel)).findFirst();
            return Stream.of(firstFloorEntrance, firstFloorExit)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .min(Comparator.comparingInt(this::getFloorDistance));
        }

        private void changePosition(Level destinationFloor) {
            if (!floor.equals(destinationFloor)) {
                floorList.add(destinationFloor);
                floor = destinationFloor;
            }
        }

        private Stream<Level> floorsInDirection() {
            return up ? IntStream.range(floor.number + 1, floors.length).mapToObj(i -> floors[i]) : IntStream.rangeClosed(1, floor.number).mapToObj(i -> floors[floor.number - i]);
        }

        private int getFloorDistance(Level floor) {
            return Math.abs(this.floor.number - floor.number);
        }
    }

    public static class Level {
        private Deque<Passenger> people;
        private int number;

        public Level(int number, Deque<Passenger> people) {
            this.number = number;
            this.people = people;
        }

        @Override
        public String toString() {
            return "" + number;
        }

        public int getFloor() {
            return number;
        }
    }

    public static class Passenger {

        int destLevel;

        public Passenger(int destLevel) {
            this.destLevel = destLevel;
        }

        public int getDestLevel() {
            return destLevel;
        }

        @Override
        public String toString() {
            return "" + destLevel;
        }
    }
}