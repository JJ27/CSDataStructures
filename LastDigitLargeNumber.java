import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

public class LastDigitLargeNumber {
    ArrayList<String> numbers = new ArrayList<String>();
    public LastDigitLargeNumber(){
        File filename = new File("LargeNumberPracticeSet.txt");
        try {
            BufferedReader input = new BufferedReader(new FileReader(filename));
            String text;
            while ((text = input.readLine()) != null) {
                numbers.add(text.split(" ")[0]);
                numbers.add(text.split(" ")[1]);
            }
        } catch (IOException io) {
            System.err.println("File error");
        }
    }

    public static void main(String[] args){
        LastDigitLargeNumber temp = new LastDigitLargeNumber();
        for(int i = 0; i < temp.numbers.size(); i+=2){
            System.out.println(temp.lastDigit(new BigInteger(temp.numbers.get(i)), new BigInteger(temp.numbers.get(i+1))));
        }
    }
    public int lastDigit(BigInteger a, BigInteger b){
        if(b.equals(BigInteger.ZERO)){
            return 1;
        }
        if(b.equals(BigInteger.ONE)){
            return a.mod(BigInteger.TEN).intValue();
        }
        if(b.mod(BigInteger.TWO).equals(BigInteger.ZERO)){
            return lastDigit(a.multiply(a).mod(BigInteger.TEN), b.divide(BigInteger.TWO));
        }
        return BigInteger.valueOf(lastDigit(a.multiply(a).mod(BigInteger.TEN), b.divide(BigInteger.TWO))).multiply(a.mod(BigInteger.TEN)).mod(BigInteger.TEN).intValue();
    }
}
