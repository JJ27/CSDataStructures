import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GuitarHero {
    static ArrayList<String> pieces = new ArrayList<String>();
    static int measures;
    static String[][] curr = new String[5][6];
    static String[][] guitarnotes = {{"E", "F", "D", "G", "B", "E"}, {"F", "A#", "D#", "G#", "C", "F"}, {"F#", "B", "E", "A", "C#", "F#"}, {"G", "C", "F", "A#", "D", "G"}, {"G#", "C#", "F#", "B", "D#", "G#"}};
    public GuitarHero(){
        File filename = new File("GuitarSong.txt");
        try {
            BufferedReader input = new BufferedReader(new FileReader(filename));
            String text;
            boolean flag = true;
            while ((text = input.readLine()) != null) {
                for(String s: text.split(",")){
                    pieces.add(s);
                }
                if(flag) {
                    measures = pieces.size();
                    flag = false;
                }
            }
        } catch (IOException io) {
            System.err.println("File error");
        }
    }

    public static void main(String[] args){
        GuitarHero temp = new GuitarHero();
        String notes = "";
        HashMap<String, String> map = arrayLink(guitarnotes);
        for(int i = 0; i < pieces.size(); i+=measures){
            String[] current = pieces.get(i).split("");
            for(int j = 0; j < current.length; j++){
                if(current[j].equals("o") || current[j].equals("*")){
                    notes += (i/measures)+""+j;
                }
            }
        }
        System.out.println(notes);
        String[] array = Stream.of(guitarnotes)
                .flatMap(Stream::of)
                .toArray(String[]::new);
        ArrayList<String> noteslist = new ArrayList<String>(Arrays.asList(array));
        noteslist.add("Measure");
        ArrayList<ArrayList<String>> grid = new ArrayList<ArrayList<String>>();
        ArrayList<String> curradd = new ArrayList<String>(Collections.nCopies(60, " "));
        for(int i = 0; i < notes.length(); i+=2){
            String curr = notes.substring(i, i+2);
            int note = (6 * Integer.parseInt(curr.substring(0,1))) + Integer.parseInt(curr.substring(1));
            System.out.println(note);
            curradd.set(note, "O");
        }
        grid.add(noteslist);
        grid.add(curradd);
        grid = transpose(grid);
        print(grid);
    }
    public static void print(String[][] arr){
        for(int i = 0; i < arr.length; i++){
            for(int j = 0; j < arr[i].length; j++){
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
    }
    public static void print(ArrayList<ArrayList<String>> arr){
        for(int i = arr.size()-1; i > -1; i--){
            for(int j = 0; j < arr.get(i).size(); j++){
                System.out.print(arr.get(i).get(j) + "\t");
                if(j == 0)
                    System.out.print("\t");
            }
            System.out.println();
        }
    }

    //transpose a 2D array
    public static String[][] transpose(String[][] array) {
        String[][] transposed = new String[array[0].length][array.length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                transposed[j][i] = array[i][j];
            }
        }
        return transposed;
    }

    //transpose the rows and columns of a 2D ArrayList
    public static ArrayList<ArrayList<String>> transpose(ArrayList<ArrayList<String>> orig) {
        String[][] array = orig.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
        return arrayToArrayList(transpose(array));
    }

    //2D Array to 2D ArrayList
    public static ArrayList<ArrayList<String>> arrayToArrayList(String[][] array) {
        ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
        for (int i = 0; i < array.length; i++) {
            list.add(new ArrayList<String>(Arrays.asList(array[i])));
        }
        return list;
    }

    //2D Array to Hashmap
    public static HashMap<String, String> arrayLink(String[][] array) {
        HashMap<String, String> map = new HashMap<String, String>();
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                map.put(i+""+j, array[i][j]);
            }
        }
        return map;
    }
}
