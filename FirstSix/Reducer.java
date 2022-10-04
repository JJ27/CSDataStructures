package FirstSix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Reducer {
    ArrayList<String> numbers = new ArrayList<String>();
    public Reducer(){
        File filename = new File("FirstSix/Reducer.txt");
        try {
            BufferedReader input = new BufferedReader(new FileReader(filename));
            String text;
            while ((text = input.readLine()) != null) {
                numbers.add(text);
            }
        } catch (IOException io) {
            System.err.println("File error");
        }
    }

    public static void main(String[] args){
        Reducer temp = new Reducer();
        for(int i = 0; i < temp.numbers.size(); i++){
            System.out.println(temp.reduceFraction(temp.numbers.get(i)));
        }
    }

    public static String reduceFraction(String fraction){
        String[] pieces = fraction.split("/");
        int numerator = Integer.parseInt(pieces[0]);
        int denominator = Integer.parseInt(pieces[1]);
        int whole = numerator/denominator;
        int newNumerator = numerator%denominator;
        int gcd = gcf(newNumerator, denominator);
        if(whole == 0){
            return newNumerator/gcd + "/" + denominator/gcd;
        }
        return whole + " " + newNumerator/gcd + "/" + denominator/gcd;
    }

    public static int gcf(int a, int b) { return b==0 ? a : gcf(b, a%b); }
}
