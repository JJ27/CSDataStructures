package FirstSix;

import java.io.*;

public class Template{
    public Template(){
        File filename = new File("Text.txt");
        try {
            BufferedReader input = new BufferedReader(new FileReader(filename));
            String text;
            while ((text = input.readLine()) != null) {
                String[] pieces = text.split(" ");
                System.out.println(pieces[0]);
            }
        } catch (IOException io) {
            System.err.println("File error");
        }
    }

    public static void main(String[] args){
        Template temp = new Template();
    }
}