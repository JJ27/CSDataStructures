import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

public class BroadwayMap {
    public static void main(String[] args) throws IOException {
        ArrayList<Show> shows = new ArrayList<Show>();
        //store Broadway2022.csv in shows
        shows = readShows("Broadway2022.csv");

        System.out.println("\nGross By Month:");
        grossByMonth(shows);
        System.out.println("\nGross By Type: ");
        grossByType(shows);
        System.out.println("\nAttendance by Type: ");
        attendanceByType(shows);
        System.out.println("\nGross by Show Per Week:");
        grossByShowPerWeek(shows);
        System.out.println("\nAttendance by Show Per Week:");
        attendanceByShowPerWeek(shows);
        System.out.println("\nGross by Theatre:");
        grossByTheatre(shows);
        System.out.println("\nPercent Capacity by Month:");
        percentCapacityByShow(shows);
        System.out.println("\nAttendance by Type at Theatre:");
        typeByTheatre(shows);
    }

    public static void grossByMonth(ArrayList<Show> shows){
        //create a TreeMap to store the total gross income by each month
        TreeMap<Integer, Double> grossIncome = new TreeMap<Integer, Double>();
        //loop through shows
        for(int i = 0; i < shows.size(); i++){
            //get the month of the show
            int month = Integer.parseInt(shows.get(i).getDate().substring(0, shows.get(i).getDate().indexOf("/")));
            //get the gross income of the show
            double income = shows.get(i).getGross();
            //if the month is not in the TreeMap, add it
            if(!grossIncome.containsKey(month)){
                grossIncome.put(month, income);
            }
            //if the month is in the TreeMap, add the income to the existing income
            else{
                grossIncome.put(month, grossIncome.get(month) + income);
            }
        }
        //print out the TreeMap
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        for(int month : grossIncome.keySet()){
            System.out.println(month + ": " + formatter.format(grossIncome.get(month)));
        }
    }

    public static void percentCapacityByShow(ArrayList<Show> shows){
        //create a TreeMap to store the total attendance by each show
        TreeMap<String, ArrayList<Double>> attendance = new TreeMap<String, ArrayList<Double>>();
        //loop through shows
        for(int i = 0; i < shows.size(); i++){
            //get the name of the show
            String name = shows.get(i).getName();
            //get the attendance of the show
            double pc = shows.get(i).getPercentCapacity();
            //if the name is not in the TreeMap, add it
            if(!attendance.containsKey(name)){
                attendance.put(name, new ArrayList<Double>(Arrays.asList(pc)));
            }
            //if the name is in the TreeMap, add the attendance to the Arraylist<Integer>
            else{
                attendance.get(name).add(pc);
            }
        }
        //print out the TreeMap
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        for(String name : attendance.keySet()){
            System.out.println(name + ": " + df.format(average(attendance.get(name))));
        }
    }
    public static double average(ArrayList<Double> list){
        double sum = 0;
        for(double d: list){
            sum += d;
        }
        return sum / list.size();
    }

    public static void attendanceByType(ArrayList<Show> shows){
        //create a TreeMap to store the total attendance by each type
        TreeMap<String, Integer> attendance = new TreeMap<String, Integer>();
        //loop through shows
        for(int i = 0; i < shows.size(); i++){
            //get the type of the show
            String type = shows.get(i).getType();
            //get the attendance of the show
            int attend = shows.get(i).getAttendance();
            //if the type is not in the TreeMap, add it
            if(!attendance.containsKey(type)){
                attendance.put(type, attend);
            }
            //if the type is in the TreeMap, add the attendance to the existing attendance
            else{
                attendance.put(type, attendance.get(type) + attend);
            }
        }
        //print out the TreeMap
        NumberFormat f = NumberFormat.getInstance();
        f.setGroupingUsed(true);
        for(String type : attendance.keySet()){
            System.out.println(type + ": " + f.format(attendance.get(type)));
        }
    }

    public static void grossByTheatre(ArrayList<Show> shows){
        //create a TreeMap to store the total gross income by each theatre
        TreeMap<String, Double> grossIncome = new TreeMap<String, Double>();
        //loop through shows
        for(int i = 0; i < shows.size(); i++){
            //get the theatre of the show
            String theatre = shows.get(i).getTheatre();
            //get the gross income of the show
            double income = shows.get(i).getGross();
            //if the theatre is not in the TreeMap, add it
            if(!grossIncome.containsKey(theatre)){
                grossIncome.put(theatre, income);
            }
            //if the theatre is in the TreeMap, add the income to the existing income
            else{
                grossIncome.put(theatre, grossIncome.get(theatre) + income);
            }
        }
        //print out the TreeMap
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        for(String theatre : grossIncome.keySet()){
            System.out.println(theatre + ": " + formatter.format(grossIncome.get(theatre)));
        }
    }

    public static void grossByShowPerWeek(ArrayList<Show> shows){
        //create a TreeMap to store the total gross income by each show
        TreeMap<String, ArrayList<Double>> grossIncome = new TreeMap<String, ArrayList<Double>>();
        //loop through shows
        for(int i = 0; i < shows.size(); i++){
            //get the name of the show
            String name = shows.get(i).getName();
            //get the gross income of the show
            double income = shows.get(i).getGross();
            //if the name is not in the TreeMap, add it
            if(!grossIncome.containsKey(name)){
                grossIncome.put(name, new ArrayList<Double>(Arrays.asList(income)));
            }
            //if the name is in the TreeMap, add the income to the Arraylist<Double>
            else{
                grossIncome.get(name).add(income);
            }
        }
        //print out the TreeMap
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        for(String name : grossIncome.keySet()){
            System.out.println(name + ": ");
            //print out the gross income of each week
            for(int i = 0; i < grossIncome.get(name).size(); i++){
                System.out.println("\tWeek " + (i + 1) + ": " + formatter.format(grossIncome.get(name).get(i)));
            }
        }
    }

    public static void attendanceByShowPerWeek(ArrayList<Show> shows){
        //create a TreeMap to store the total attendance by each show
        TreeMap<String, ArrayList<Integer>> attendance = new TreeMap<String, ArrayList<Integer>>();
        //loop through shows
        for(int i = 0; i < shows.size(); i++){
            //get the name of the show
            String name = shows.get(i).getName();
            //get the attendance of the show
            int attend = shows.get(i).getAttendance();
            //if the name is not in the TreeMap, add it
            if(!attendance.containsKey(name)){
                attendance.put(name, new ArrayList<Integer>(Arrays.asList(attend)));
            }
            //if the name is in the TreeMap, add the attendance to the Arraylist<Integer>
            else{
                attendance.get(name).add(attend);
            }
        }
        //print out the TreeMap
        NumberFormat f = NumberFormat.getInstance();
        f.setGroupingUsed(true);
        for(String name : attendance.keySet()){
            System.out.println(name + ": ");
            //print out the attendance of each week
            for(int i = 0; i < attendance.get(name).size(); i++){
                System.out.println("\tWeek " + (i + 1) + ": " + f.format(attendance.get(name).get(i)));
            }
        }
    }

    public static void grossByType(ArrayList<Show> shows){
        //create a TreeMap to store the total gross income by each type
        TreeMap<String, Double> grossIncome = new TreeMap<String, Double>();
        //loop through shows
        for(int i = 0; i < shows.size(); i++){
            //get the type of the show
            String type = shows.get(i).getType();
            //get the gross income of the show
            double income = shows.get(i).getGross();
            //if the type is not in the TreeMap, add it
            if(!grossIncome.containsKey(type)){
                grossIncome.put(type, income);
            }
            //if the type is in the TreeMap, add the income to the existing income
            else{
                grossIncome.put(type, grossIncome.get(type) + income);
            }
        }
        //print out the TreeMap
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        for(String type : grossIncome.keySet()){
            System.out.println(type + ": " + formatter.format(grossIncome.get(type)));
        }
    }

    public static void typeByTheatre(ArrayList<Show> shows){
        //create a TreeMap to store the total attendance by each theatre
        TreeMap<String, TreeMap<String, Integer>> attendance = new TreeMap<String, TreeMap<String, Integer>>();
        //loop through shows
        for(int i = 0; i < shows.size(); i++){
            //get the theatre of the show
            String theatre = shows.get(i).getTheatre();
            //get the type of the show
            String type = shows.get(i).getType();
            //get the attendance of the show
            int attend = shows.get(i).getAttendance();
            //if the theatre is not in the TreeMap, add it
            if(!attendance.containsKey(theatre)){
                attendance.put(theatre, new TreeMap<String, Integer>());
                attendance.get(theatre).put(type, attend);
            }
            //if the theatre is in the TreeMap, add the type and attendance to the existing TreeMap
            else{
                if(!attendance.get(theatre).containsKey(type)){
                    attendance.get(theatre).put(type, attend);
                }
                else{
                    attendance.get(theatre).put(type, attendance.get(theatre).get(type) + attend);
                }
            }
        }
        //print out the TreeMap
        NumberFormat f = NumberFormat.getInstance();
        f.setGroupingUsed(true);
        for(String theatre : attendance.keySet()){
            System.out.println(theatre + ": ");
            for(String type : attendance.get(theatre).keySet()){
                System.out.println("\t" + type + ": " + f.format(attendance.get(theatre).get(type)));
            }
        }
    }

    public static ArrayList<Show> readShows(String file) throws IOException {
        ArrayList<String> content = new ArrayList<>();
        ArrayList<Show> shows = new ArrayList<Show>();
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = "";
            br.readLine();
            while ((line = br.readLine()) != null) {
                content.addAll(new ArrayList<String>(Arrays.asList(line.split(","))));
                shows.add(new Show(content.get(0),content.get(1),content.get(2), content.get(3), Long.parseLong(content.get(4)),Integer.parseInt(content.get(5)),Double.parseDouble(content.get(6))));
                content.clear();
            }
        } catch (FileNotFoundException e) {}
        return shows;
    }

    public static class Show{
        private String date;
        private String name;
        private String type;
        private String theatre;
        private long gross;
        private int attendance;
        private double percentCapacity;
        public Show(String date, String name, String type, String theatre, long gross, int attendance, double percentCapacity){
            this.date = date;
            this.name = name;
            this.type = type;
            this.theatre = theatre;
            this.gross = gross;
            this.attendance = attendance;
            this.percentCapacity = percentCapacity;
        }
        public String getDate(){
            return date;
        }
        public String getName(){
            return name;
        }
        public String getType(){
            return type;
        }
        public String getTheatre(){
            return theatre;
        }
        public long getGross(){
            return gross;
        }
        public int getAttendance(){
            return attendance;
        }
        public double getPercentCapacity(){
            return percentCapacity;
        }
        //toString method
        public String toString(){
            return "Date: " + date + ", Name: " + name + ", Type: " + type + ", Theatre: " + theatre + ", Gross: " + gross + ", Attendance: " + attendance + ", Percent Capacity: " + percentCapacity;
        }
    }
}
