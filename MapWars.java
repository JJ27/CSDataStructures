import java.util.*;

public class MapWars {
    public static void main(String[] args) {
        long[][] s = new long[][]{{69, 130}, {87, 1310}, {30, 40}};
        System.out.println(convertFrac(s));
    }

    public static double findUniq(double arr[]){
        Map<Double, Integer> map = new HashMap<>();
        for (double d : arr) {
            if (map.containsKey(d)) {
                map.put(d, map.get(d) + 1);
            } else {
                map.put(d, 1);
            }
        }
        for (Map.Entry<Double, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }
        return 0;
    }

    //delete occurences of an element if it occurs more than n times
    public static int[] deleteNth(int[] elements, int maxOccurrences) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < elements.length; i++) {
            if (map.containsKey(elements[i])) {
                map.put(elements[i], map.get(elements[i]) + 1);
            } else {
                map.put(elements[i], 1);
            }
            int f = elements[i];
            while (map.get(f) > maxOccurrences) {
                //remove elements[i] from elements
                for (int j = i; j < elements.length - 1; j++) {
                    elements[j] = elements[j + 1];
                }
                elements = Arrays.copyOf(elements, elements.length - 1);
                //decrease the count of elements[i] in map
                i--;
                map.put(f, map.get(f) - 1);
            }
        }
        return elements;
    }

    public static String decomp(int n){
        int[] primes = new int[n];
        int[] powers = new int[n];
        int count = 0;
        for (int i = 2; i <= n; i++) {
            if (isPrime(i)) {
                primes[count] = i;
                powers[count] = 0;
                int j = i;
                while (j <= n) {
                    powers[count] += n / j;
                    j *= i;
                }
                count++;
            }
        }
        String s = "";
        for (int i = 0; i < count; i++) {
            if (powers[i] > 1) {
                s += primes[i] + "^" + powers[i] + " * ";
            } else if (powers[i] == 1) {
                s += primes[i] + " * ";
            }
        }
        return s.substring(0, s.length() - 3);
    }
    //isPrime
    public static boolean isPrime(int n) {
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }
        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    //find the mode of an array
    public static int mostFrequentItemCount(int[] collection){
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : collection) {
            if (map.containsKey(i)) {
                map.put(i, map.get(i) + 1);
            } else {
                map.put(i, 1);
            }
        }
        int max = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
            }
        }
        return max;
    }

    //find who won an absolute majority given a list of Ballots, return null if tied
    public static String getWinner(final List<String> listOfBallots){
        Map<String, Integer> map = new HashMap<>();
        for (String s : listOfBallots) {
            if (map.containsKey(s)) {
                map.put(s, map.get(s) + 1);
            } else {
                map.put(s, 1);
            }
        }
        int max = 0;
        String winner = null;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                winner = entry.getKey();
            }
        }
        if (max > listOfBallots.size() / 2) {
            return winner;
        }
        return null;
    }

    public static String stockSummary(String[] lstOfArt, String[] lstOf1stLetter) {
        if (lstOfArt.length == 0 || lstOf1stLetter.length == 0) {
            return "";
        }
        Map<String, Integer> map = new HashMap<>();
        for (String s : lstOfArt) {
            String[] split = s.split(" ");
            String key = split[0].substring(0, 1);
            int value = Integer.parseInt(split[1]);
            if (map.containsKey(key)) {
                map.put(key, map.get(key) + value);
            } else {
                map.put(key, value);
            }
        }
        String s = "";
        for (String letter : lstOf1stLetter) {
            if (map.containsKey(letter)) {
                s += "(" + letter + " : " + map.get(letter) + ") - ";
            } else {
                s += "(" + letter + " : " + 0 + ") - ";
            }
        }
        return s.substring(0, s.length() - 3);
    }

    public static HashMap<String, Integer> looseChange(int cent) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Nickels", 0);
        map.put("Pennies", 0);
        map.put("Dimes", 0);
        map.put("Quarters", 0);
        while (cent >= 25) {
            map.put("Quarters", map.get("Quarters") + 1);
            cent -= 25;
        }
        while (cent >= 10) {
            map.put("Dimes", map.get("Dimes") + 1);
            cent -= 10;
        }
        while (cent >= 5) {
            map.put("Nickels", map.get("Nickels") + 1);
            cent -= 5;
        }
        while (cent >= 1) {
            map.put("Pennies", map.get("Pennies") + 1);
            cent -= 1;
        }
        return map;
    }

    /*public static Map<String,List<Integer>> getPeaks(int[] arr) {
        public static Map<String, List<Integer>> getPeaks(int[] arr) {
            Map<String, List<Integer>> map = new HashMap<>();
            List<Integer> pos = new ArrayList<>();
            List<Integer> peak = new ArrayList<>();
            for(int i = 1; i < arr.length - 1; i++) {
                if(arr[i - 1] < arr[i]) {
                    if(arr[i] > arr[i + 1]) {
                        pos.add(i);
                        peak.add(arr[i]);
                    } else if(arr[i] == arr[i + 1]) {
                        for(int j = i; j < arr.length - 1; j++) {
                            if(arr[j] == arr[j + 1]) {
                                continue;
                            }
                            if(arr[j] < arr[j + 1]) {
                                break;
                            }
                            if(arr[j] > arr[j + 1]) {
                                pos.add(i);
                                peak.add(arr[i]);
                                break;
                            }
                        }
                    }
                }
            }
            map.put("pos", pos);
            map.put("peaks", peak);
            return map;
        }
    }*/
    //find the integer in int[] a that appears an odd number of times
    public static int findIt(int[] a) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : a) {
            if (map.containsKey(i)) {
                map.put(i, map.get(i) + 1);
            } else {
                map.put(i, 1);
            }
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() % 2 != 0) {
                return entry.getKey();
            }
        }
        return 0;
    }

    //find the lowest common denominator of all fractions in lst
    public static String convertFrac(long[][] lst){
        if (lst.length == 0) {
            return "";
        }
        long lcm = lst[0][1];
        for (int i = 1; i < lst.length; i++) {
            lcm = lcm(lcm, lst[i][1]);
        }
        long[] n = new long[lst.length+1];
        n[0] = lcm;
        for(int i = 1; i < n.length; i++) {
            n[i] = lst[i-1][0] * (lcm / lst[i-1][1]);
        }
        long g = gcd(n);
        String s = "";
        for (long[] fraction : lst) {
            s += "(" + (fraction[0] * (lcm / fraction[1]) / g) + "," + lcm/g + ")";
        }
        return s;
    }
    public static long gcd(long a, long b) {
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }
    //gcf for long[]
    public static long gcd(long[] c){
        long gcf = c[0];
        for (int i = 1; i < c.length; i++) {
            gcf = gcd(gcf, c[i]);
        }
        return gcf;
    }
    public static long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }
    //Multiply two longs, but take care of overflow. If the result cannot be accurately stored in a long, throw an ArithmeticException.
    public static long multiply(long a, long b) {
        if (a == 0 || b == 0) {
            return 0;
        }
        if (a > 0 && b > 0) {
            if (a > Long.MAX_VALUE / b) {
                throw new ArithmeticException();
            }
        }
        if (a < 0 && b < 0) {
            if (a < Long.MIN_VALUE / b) {
                throw new ArithmeticException();
            }
        }
        if (a < 0 && b > 0) {
            if (b > Long.MAX_VALUE / a) {
                throw new ArithmeticException();
            }
        }
        if (a > 0 && b < 0) {
            if (a > Long.MAX_VALUE / b) {
                throw new ArithmeticException();
            }
        }
        return a * b;
    }

}
