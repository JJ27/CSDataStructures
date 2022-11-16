import java.util.Stack;

public class Stacks {
    public static void main(String[] args){
        System.out.println("Decimal: 400");
        System.out.println("Binary: " + decimalToBinary(400));
        String q = "We hold these truths to be self-evident, that all men are created equal, that they are endowed by their Creator with certain unalienable Rights, that among these are Life, Liberty and the pursuit of Happiness.";
        System.out.println("String: " + q);
        System.out.println("Reversed: " + reverseString(q));
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

    //Using the StarWarsCharacters.csv file, Create a StarWarsChar object that will store character name, birth year, gender, home world, and species. Create a method that will read the file and return a list of Character objects.
    //Create a method that will return a list of all the characters



}
