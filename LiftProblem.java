import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LiftProblem {
    private static int[][] queues;
    static ArrayList<String> pieces;

    public static int[] theLift(final int[][] queues, final int capacity) {
        LiftProblem.queues = queues;

        Lift lift = new Lift(capacity);

        while (!allArrived(lift)) {
            lift.move();
        }
        return lift.floorHistory.stream().mapToInt(i -> i).toArray();
    }

    public static void main(String[] args){
        LiftProblem p = new LiftProblem();
        pieces.remove(0);
        for(String s: pieces){
            //queues = new int[][];
            String prematrix = "{";
            String[] split = s.split("q");
            int floors = Integer.parseInt(split[0]);
            for(int i = 1; i <= floors; i++){
               prematrix += "{" + split[i] + "},";
            }
            prematrix = prematrix.substring(0, prematrix.length() - 1);
            prematrix += "}";
            System.out.println(prematrix);
            //queues = Lift.stringTo2D(prematrix);
            print(primate(Lift.intto2D(prematrix)));
        }
    }

    public static void print(int[][] arr){
        for(int i = 0; i < arr.length; i++){
            for(int j = 0; j < arr[i].length; j++){
                System.out.print(arr[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static int[][] primate(Integer[][] arr){
        int[][] arr2 = new int[arr.length][arr[0].length];
        for(int i = 0; i < arr.length; i++){
            for(int j = 0; j < arr[i].length; j++){
                arr2[i][j] = arr[i][j].intValue();
            }
        }
        return arr2;
    }

    public static void print(String[][] arr){
        for(int i = 0; i < arr.length; i++){
            for(int j = 0; j < arr[i].length; j++){
                System.out.print(arr[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public LiftProblem(){
        File filename = new File("TheLiftFile.txt");
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
    }

    private static boolean allArrived(Lift lift) {
        for (int i = 0; i < queues.length; i++) {
            for (int j = 0; j < queues[i].length; j++) {
                if (queues[i][j] != i) {
                    return false;
                }
            }
        }
        return lift.passengers.size() == 0 && lift.floor == 0;
    }

    private static void push(int person, int floor) {
        List<Integer> people = Arrays.stream(queues[floor]).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        people.add(person);
        queues[floor] = people.stream().mapToInt(i -> i).toArray();
    }

    private static int pop(int floor, int index) {
        List<Integer> people = Arrays.stream(queues[floor]).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        int person;
        if (people.size() == 0) {
            person = -1;
        } else {
            person = people.get(index);
            people.set(index, -1);
        }
        queues[floor] = people.stream().filter(i -> i >= 0).mapToInt(i -> i).toArray();
        return person;
    }

    private static class Lift {
        private final int capacity;
        private List<Integer> passengers;
        private List<Integer> floorHistory;
        private int floor = -1;
        private boolean travelingUp = true;

        Lift(int capacity) {
            this.capacity = capacity;
            passengers = new ArrayList<>();
            floorHistory = new ArrayList<>();
            changeFloor(0);
        }

        void move() {
            deliverPassengers();
            pickupPassengers();
            goToNextFloor();
        }

        private void deliverPassengers() {
            List<Integer> arrivedPassengers = passengers.stream().filter(destination -> floor == destination).collect(Collectors.toList());
            passengers.removeAll(arrivedPassengers);
            for (Integer passenger : arrivedPassengers) {
                push(passenger, floor);
            }
        }

        private void pickupPassengers() {
            for (int i = 0; i < queues[floor].length && capacity > passengers.size(); i++) {
                int destination = queues[floor][i];
                if (isGoingSameWay(destination, floor)) {
                    passengers.add(pop(floor, i));
                    i--;//decrease because of in use change of array
                }
            }
        }

        private void goToNextFloor() {
            if (travelingUp) {
                if (moveUpIfUseful()) {
                    return;
                }
                if (passengers.size() == 0) {
                    if (smartUp()) {
                        return;
                    }
                    travelingUp = false;
                    if (moveDownIfUseful()) {
                        return;
                    }
                    if (smartDown()) {
                        return;
                    }
                }
            } else {
                if (moveDownIfUseful()) {
                    return;
                }
                if (passengers.size() == 0) {
                    if (smartDown()) {
                        return;
                    }
                    travelingUp = true;
                    if (moveUpIfUseful()) {
                        return;
                    }
                    if (smartUp()) {
                        return;
                    }
                }
            }
            changeFloor(0);
        }

        public static Integer[][] intto2D(String str){
            String[][] s = Arrays.stream(str.substring(2, str.length() - 2).split("\\},\\{")).map(e -> Arrays.stream(e.split("\\s*,\\s*")).toArray(String[]::new)).toArray(String[][]::new);
            //int[][] arr = new int[s.length][s[0].length];
            ArrayList<ArrayList<Integer>> arr = new ArrayList<ArrayList<Integer>>();
            for(int i = 0; i < s.length; i++){
                for(int j = 0; j < s[i].length; j++){
                    if(j == 0){
                        arr.add(new ArrayList<Integer>(Arrays.asList(new Integer[s[i].length])));
                    }
                    try {
                        System.out.println(i + " " + j);
                        arr.get(i).set(j, Integer.parseInt(s[i][j]));
                    } catch (NumberFormatException e) {
                        arr.get(i).set(j, -1);
                    }
                }
            }
            Integer[][] stringArray = arr.stream().map(u -> u.toArray(new Integer[0])).toArray(Integer[][]::new);
            return stringArray;
        }

        private boolean smartDown() {
            for (int i = 0; i <= floor; i++) {
                if (moveIfAnyGoingDifferentWay(i)) {
                    return true;
                }
            }
            return false;
        }

        private boolean smartUp() {
            for (int i = queues.length - 1; i >= floor; i--) {
                if (moveIfAnyGoingDifferentWay(i)) {
                    return true;
                }
            }
            return false;
        }

        private boolean moveDownIfUseful() {
            for (int i = floor - 1; i >= 0; i--) {
                if (moveIfPassengerDestination(i)) {
                    return true;
                }
                if (moveIfAnyGoingSameWay(i)) {
                    return true;
                }
            }
            return false;
        }

        private boolean moveUpIfUseful() {
            for (int i = floor + 1; i < queues.length; i++) {
                if (moveIfPassengerDestination(i)) {
                    return true;
                }
                if (moveIfAnyGoingSameWay(i)) {
                    return true;
                }
            }
            return false;
        }

        private boolean moveIfPassengerDestination(int floor) {
            if (passengers.stream().anyMatch(dest -> dest == floor)) {
                changeFloor(floor);
                return true;
            }
            return false;
        }

        private boolean moveIfAnyGoingSameWay(int floor) {
            for (int i = 0; i < queues[floor].length; i++) {
                if (isGoingSameWay(queues[floor][i], floor)) {
                    changeFloor(floor);
                    return true;
                }
            }
            return false;
        }

        private boolean moveIfAnyGoingDifferentWay(int floor) {
            for (int i = 0; i < queues[floor].length; i++) {
                if (queues[floor][i] != floor && !isGoingSameWay(queues[floor][i], floor)) {
                    changeFloor(floor);
                    travelingUp = !travelingUp;
                    return true;
                }
            }
            return false;
        }

        private boolean isGoingSameWay(int destination, int currentFloor) {
            if (travelingUp) {
                return destination > currentFloor;
            } else {
                return destination < currentFloor;
            }
        }

        private void changeFloor(int floor) {
            if (this.floor == floor) {
                return;
            }
            if (travelingUp && floor < this.floor) {
                travelingUp = false;
            } else if (!travelingUp && floor > this.floor) {
                travelingUp = true;
            }
            this.floor = floor;
            floorHistory.add(floor);
            if (floor == queues.length - 1) {
                travelingUp = false;
            }
            if (floor == 0) {
                travelingUp = true;
            }
        }
    }
}



