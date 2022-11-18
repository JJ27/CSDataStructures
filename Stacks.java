import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Stacks {
    public static void main(String[] args){
        System.out.println("Decimal: 400");
        System.out.println("Binary: " + decimalToBinary(400));
        String q = "We hold these truths to be self-evident, that all men are created equal, that they are endowed by their Creator with certain unalienable Rights, that among these are Life, Liberty and the pursuit of Happiness.";
        System.out.println("String: " + q);
        System.out.println("Reversed: " + reverseString(q));
        ArrayList<StarWarsChar> main = readStarWarsChars("StarWarsCharacters.csv");

        printStack("Male Characters", main.stream().filter(c -> c.getGender().equals("male")).collect(Stack::new, Stack::push, Stack::addAll));
        printStack("Female Characters", main.stream().filter(c -> c.getGender().equals("female")).collect(Stack::new, Stack::push, Stack::addAll));
        printStack("Droids", main.stream().filter(c -> c.getSpecies().equals("Droid")).collect(Stack::new, Stack::push, Stack::addAll));

        Stack<StarWarsChar> ages = main.stream().filter(c -> !c.getBirthYear().equals("NA")).collect(Stack::new, Stack::push, Stack::addAll);
        System.out.println("\nAges");
        System.out.println("Name\t\t\t\t\tHomeworld\t\t\tBirth Year (BBY)");
        while(!ages.isEmpty()){
            StarWarsChar curr = ages.pop();
            System.out.printf("%-23s %-19s %-23s%n", curr.getName(), (!curr.getHomeWorld().equals("NA") ? curr.getHomeWorld() : "Unknown"), curr.getBirthYear());
        }
    }

    public static String decimalToBinary(int decimal){
        Stack<Integer> binary = new Stack<Integer>();
        String s = "";
        while(decimal > 0){
            binary.push(decimal % 2);
            decimal /= 2;
        }
        while(!binary.isEmpty()){
            s += binary.pop();
        }
        return s;
    }

    public static String reverseString(String s){
        Stack<Character> stack = new Stack<Character>();
        String reversed = "";
        for(int i = 0; i < s.length(); i++){
            stack.push(s.charAt(i));
        }
        while(!stack.isEmpty()){
            reversed += stack.pop();
        }
        return reversed;
    }
    public static class StarWarsChar{
        public String getName() {
            return name;
        }

        public String getSpecies() {
            return species;
        }

        public String getGender() {
            return gender;
        }

        public String getHomeWorld() {
            return homeWorld;
        }

        public String getBirthYear() {
            return birthYear;
        }

        private String name;
        private String species;
        private String gender;
        private String homeWorld;
        private String birthYear;

        public StarWarsChar(String name, String species, String gender, String homeWorld, String birthYear){
            this.name = name;
            this.species = species;
            this.gender = gender;
            this.homeWorld = homeWorld;
            this.birthYear = birthYear;
        }
    }

    public static ArrayList<StarWarsChar> readStarWarsChars(String fileName){
        ArrayList<StarWarsChar> characters = new ArrayList<StarWarsChar>();
        try{
            Scanner file = new Scanner(new File(fileName));
            file.nextLine();
            while(file.hasNextLine()){
                String line = file.nextLine();
                String[] data = line.split(",");
                int i = 5;
                while(!data[i].contains("NA") && !data[i].contains("BBY")){
                    i++;
                }
                StarWarsChar character = new StarWarsChar(data[0], data[8], data[6], data[7], data[i]);
                characters.add(character);
            }
        }catch(FileNotFoundException e){
            System.out.println("File not found");
        }
        return characters;
    }

    public static void printStack(String name, Stack<StarWarsChar> stack){
        System.out.println("\n" + name);
        System.out.println("Name\t\t\t\t\tHomeworld");
        while(!stack.isEmpty()){
            StarWarsChar curr = stack.pop();
            System.out.printf("%-23s %s%n", curr.getName(), (!curr.getHomeWorld().equals("NA") ? curr.getHomeWorld() : "Unknown"));
        }
    }


}
