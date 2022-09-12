import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GuitarHero {
    static ArrayList<String> pieces = new ArrayList<String>();
    static int measures;
    static String[][] curr = new String[5][6];
    static String[][] guitarnotes = {{"E", "A", "D", "G", "B", "E"}, {"F", "A#", "D#", "G#", "C", "F"}, {"F#", "B", "E", "A", "C#", "F#"}, {"G", "C", "F", "A#", "D", "G"}, {"G#", "C#", "F#", "B", "D#", "G#"}};
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
                    notes += map.get((i/measures)+""+j);
                }
            }
        }
        System.out.println(notes);
    }
    public static void print(String[][] arr){
        for(int i = 0; i < arr.length; i++){
            for(int j = 0; j < arr[i].length; j++){
                System.out.print(arr[i][j]);
            }
            System.out.println();
        }
    }

    //transpose a 2D array
    public static String[][] transpose(String[][] array) {
        String[][] transposed = new String[array[0].length][array.length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                transposed[j][i] = array[i][j];
            }
        }
        return transposed;
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
