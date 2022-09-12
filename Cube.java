import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Cube {
    static int[] dice = {1,2,3,4,5,6};
    ArrayList<String> strings = new ArrayList<String>();
    public Cube(){
        File filename = new File("CubeInput.txt");
        try {
            BufferedReader input = new BufferedReader(new FileReader(filename));
            String text;
            while ((text = input.readLine()) != null) {
                strings.add(text);
            }
        } catch (IOException io) {
            System.err.println("File error");
        }
    }

    public static void main(String[] args){
        Cube temp = new Cube();
        for(String s: temp.strings){
            System.out.println(roll(s));
            dice = new int[]{1, 2, 3, 4, 5, 6};
        }
    }
    public static int roll(String roll){
        String[] rolls = roll.split("");
        for(int i = 0; i < rolls.length; i++){
            if(rolls[i].equals("S")){
                int temp = dice[0];
                dice[0] = dice[1];
                dice[1] = dice[5];
                dice[5] = dice[4];
                dice[4] = temp;
            }
            if(rolls[i].equals("N")){
                int temp = dice[0];
                dice[0] = dice[4];
                dice[4] = dice[5];
                dice[5] = dice[1];
                dice[1] = temp;
            }
            if(rolls[i].equals("E")){
                int temp = dice[0];
                dice[0] = dice[3];
                dice[3] = dice[5];
                dice[5] = dice[2];
                dice[2] = temp;
            }
            if(rolls[i].equals("W")){
                int temp = dice[0];
                dice[0] = dice[2];
                dice[2] = dice[5];
                dice[5] = dice[3];
                dice[3] = temp;
            }
        }
        return dice[0];
    }



}
