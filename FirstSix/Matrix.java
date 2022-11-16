package FirstSix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Matrix {
    static ArrayList<String> pieces = new ArrayList<String>();
    public Matrix(){
        File filename = new File("FirstSix/MatrixFile.txt");
        try {
            BufferedReader input = new BufferedReader(new FileReader(filename));
            String text;
            String e = "";
            while ((text = input.readLine()) != null) {
                for(String s: text.split(" ")){
                    e += s;
                }
            }
            e.replaceAll(" ", "");
            for(String s: e.split("}}")){
                pieces.add(s);
            }
            for(int i = 0; i < pieces.size(); i++){
                String j = pieces.get(i) + "}}";
                j = j.trim();
                pieces.set(i, j);
                if(!pieces.get(i).contains("{{")){
                    pieces.remove(i);
                }
            }
        } catch (IOException io) {
            System.err.println("File error");
        }
    }
    public static void main(String[] args){
        Matrix temp = new Matrix();
        for(int i = 0; i < pieces.size(); i+=2) {
            int[][] m1 = new int[0][], m2 = new int[0][];
            try {
                m1 = stringTo2D(pieces.get(i));
                m2 = stringTo2D(pieces.get(i + 1));
            } catch (ArrayIndexOutOfBoundsException e) {}

            System.out.println("Matrix 1:");
            try {
                print(m1);
            } catch (Exception e) {
                System.out.println("Operation Not Possible!");
            }
            System.out.println("Matrix 2:");
            try {
                print(m2);
            } catch (Exception e) {
                System.out.println("Operation Not Possible!");
            }
            System.out.println("Matrix 1 + Matrix 2:");
            try {
                print(sum(m1, m2));
            } catch (Exception e) {
                System.out.println("Operation Not Possible!");
            }
            System.out.println("Matrix 1 - Matrix 2:");
            try {
                print(subtract(m1, m2));
            } catch (Exception e) {
                System.out.println("Operation Not Possible!");
            }
            System.out.println("Matrix 1 * Matrix 2:");
            try {
                print(multiply(m1, m2));
            } catch (Exception e) {
                System.out.println("Operation Not Possible!");
            }
            System.out.println("--------------------");
        }
    }

    //String to 2D Array
    public static int[][] stringTo2D(String str){
        String[][] s = Arrays.stream(str.substring(2, str.length() - 2).split("\\},\\{")).map(e -> Arrays.stream(e.split("\\s*,\\s*")).toArray(String[]::new)).toArray(String[][]::new);
        int[][] arr = new int[s.length][s[0].length];
        for(int i = 0; i < s.length; i++){
            for(int j = 0; j < s[i].length; j++){
                arr[i][j] = Integer.parseInt(s[i][j]);
            }
        }
        return arr;
    }

    public static void print(int[][] arr){
        for(int i = 0; i < arr.length; i++){
            for(int j = 0; j < arr[i].length; j++){
                System.out.print(arr[i][j] + "\t");
            }
            System.out.println();
        }
    }

    //sum matrices
    public static int[][] sum(int[][] a, int[][] b){
        int[][] arr = new int[a.length][a[0].length];
        for(int i = 0; i < a.length; i++){
            for(int j = 0; j < a[i].length; j++){
                arr[i][j] = a[i][j] + b[i][j];
            }
        }
        return arr;
    }

    //multiply matrices
    public static int[][] multiply(int[][] a, int[][] b){
        int[][] arr = new int[a.length][b[0].length];
        for(int i = 0; i < a.length; i++){
            for(int j = 0; j < b[0].length; j++){
                for(int k = 0; k < a[0].length; k++){
                    arr[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return arr;
    }

    //subtract matrices
    public static int[][] subtract(int[][] a, int[][] b){
        int[][] arr = new int[a.length][a[0].length];
        for(int i = 0; i < a.length; i++){
            for(int j = 0; j < a[i].length; j++){
                arr[i][j] = a[i][j] - b[i][j];
            }
        }
        return arr;
    }
}
