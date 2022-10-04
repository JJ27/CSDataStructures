package FirstSix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LiftProblem {
    private static ArrayList<ArrayList<Integer>> queues;
    static ArrayList<String> pieces;

    public static int[] theLift(final ArrayList<ArrayList<Integer>> queues, final int capacity, final int floors) {
        LiftProblem.queues = queues;

        Lift lift = new Lift(capacity, floors);

        while (!allArrived(lift)) {
            lift.move();
        }
        return lift.floorHistory.stream().mapToInt(i -> i).toArray();
    }

    public static void main(String[] args){
        LiftProblem p = new LiftProblem();
        pieces.remove(0);
        for(String s: pieces){
            System.out.println(s);
            //queues = new int[][];
            String prematrix = "{";
            String[] split = s.split("q");
            int floors = Integer.parseInt(split[0]);
            for(int i = 1; i <= floors; i++){
                prematrix += "{" + split[i] + "},";
            }
            int capacity = Integer.parseInt(split[split.length - 1].substring(10));
            prematrix = prematrix.substring(0, prematrix.length() - 1);
            prematrix += "}";
            System.out.println(theLift(Lift.intto2D(prematrix), capacity, floors));
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

    //method to print array
    public static void printarr(ArrayList<Integer> arr){
        for(int i = 0; i < arr.size(); i++){
            System.out.print(arr.get(i) + "\t");
        }
        System.out.println();
    }

    public static void print(ArrayList<ArrayList<Integer>> arr){
        System.out.println("Start:");
        for(int i = 0; i < arr.size(); i++){
            for(int j = 0; j < arr.get(i).size(); j++){
                System.out.print(arr.get(i).get(j) + "\t");
            }
            System.out.println();
        }
    }

    public static boolean check(){
        for(int i = 0; i < queues.size(); i++){
            for(Integer j: queues.get(i)){
                if(j != -1 && i != 0){
                    return false;
                }
            }
        }
        return true;
    }

    public LiftProblem(){
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
    }

    private static boolean allArrived(Lift lift) {
        for (int i = 0; i < queues.size(); i++) {
            for (int j = 0; j < queues.get(i).size(); j++) {
                if (queues.get(i).get(j) != i) {
                    return false;
                }
            }
        }
        return lift.passengers.size() == 0 && lift.floor == 0;
    }

    private static void push(int person, int floor) {
        List<Integer> people = queues.get(floor);
        people.add(person);
        queues.set(floor, new ArrayList<Integer>(people));
    }

    private static int pop(int floor, int index) {
        List<Integer> people = queues.get(floor);
        int person;
        if (people.size() == 0) {
            person = -1;
        } else {
            person = people.get(index);
            people.set(index, -1);
        }
        queues.set(floor,new ArrayList<Integer>(people));
        return person;
    }

    private static class Lift {
        private final int capacity;
        private List<Integer> passengers;
        private List<Integer> floorHistory;
        private int floor = -1;
        private boolean travelingUp = true;
        private int floors;

        Lift(int capacity, int floors) {
            this.capacity = capacity;
            passengers = new ArrayList<>();
            floorHistory = new ArrayList<>();
            this.floors = floors;
            changeFloor(0);
        }

        void move() {
            if(!(deliverPassengers() | pickupPassengers()) && floor != floors-1 && floors != 0) {
                try {
                    floorHistory.remove(floorHistory.size() - 1);
                } catch (IndexOutOfBoundsException e) {}
            }
            goToNextFloor();
        }

        private boolean deliverPassengers() {
            List<Integer> arrivedPassengers = passengers.stream().filter(destination -> floor == destination).collect(Collectors.toList());
            arrivedPassengers.forEach(passenger -> {
                passengers.remove(passenger);
                push(passenger, floor);
            });
            if(arrivedPassengers.size() > 0){
                return true;
            }
            return false;
        }

        private boolean pickupPassengers() {
            boolean flag = false;
            for (int i = 0; i < queues.get(floor).size() && capacity > passengers.size(); i++) {
                int destination = queues.get(floor).get(i);
                if (isGoingSameWay(destination, floor)) {
                    passengers.add(pop(floor, i));
                    flag = true;
                    i--;//decrease because of in use change of array
                }
            }
            return flag;
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
            if(floor != 0){
                changeFloor(0);
            } else{
                floorHistory.add(0);
            }
        }

        public static ArrayList<ArrayList<Integer>> intto2D(String str){
            String[][] s = Arrays.stream(str.substring(2, str.length() - 2).split("\\},\\{")).map(e -> Arrays.stream(e.split("\\s*,\\s*")).toArray(String[]::new)).toArray(String[][]::new);
            //int[][] arr = new int[s.length][s[0].length];
            print(s);
            ArrayList<ArrayList<Integer>> arr = new ArrayList<ArrayList<Integer>>();
            for(int i = 0; i < s.length; i++){
                for(int j = 0; j < s[i].length; j++){
                    if(j == 0){
                        arr.add(new ArrayList<Integer>());
                    }
                    try {
                        System.out.println(i + " " + j);
                        arr.get(i).add(Integer.parseInt(s[i][j]));
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
            System.out.println("Finished");
            print(arr);
            return arr;
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
            for (int i = queues.size() - 1; i >= floor; i--) {
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
            for (int i = floor + 1; i < queues.size(); i++) {
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
            for (int i = 0; i < queues.get(floor).size(); i++) {
                if (isGoingSameWay(queues.get(floor).get(i), floor)) {
                    changeFloor(floor);
                    return true;
                }
            }
            return false;
        }

        private boolean moveIfAnyGoingDifferentWay(int floor) {
            for (int i = 0; i < queues.get(floor).size(); i++) {
                if (queues.get(floor).get(i) != floor && !isGoingSameWay(queues.get(floor).get(i), floor)) {
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
            printarr(new ArrayList<Integer>(floorHistory));
            //print(queues);
            if(check()){
                System.out.println("Done!");
                System.exit(0);
            }
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
            if (floor == queues.size() - 1) {
                travelingUp = false;
            }
            if (floor == 0) {
                travelingUp = true;
            }
        }
    }
}