import java.util.*;
import java.util.stream.Collectors;

public class Bundesliga {
    public static void main(String[] args){
        Bundesliga b = new Bundesliga();
    }

    public Bundesliga(){
        String[] results = new String[]
                {
                        "6:0 FC Bayern Muenchen - Werder Bremen",
                        "-:- Eintracht Frankfurt - Schalke 04",
                        "-:- FC Augsburg - VfL Wolfsburg",
                        "-:- Hamburger SV - FC Ingolstadt",
                        "-:- 1. FC Koeln - SV Darmstadt",
                        "-:- Borussia Dortmund - FSV Mainz 05",
                        "-:- Borussia Moenchengladbach - Bayer Leverkusen",
                        "-:- Hertha BSC Berlin - SC Freiburg",
                        "-:- TSG 1899 Hoffenheim - RasenBall Leipzig"
                };
        System.out.println(table(results));
        String expectedTable =
                " 1. FC Bayern Muenchen            1  1  0  0  6:0  3\n" +
                        " 2. 1. FC Koeln                   0  0  0  0  0:0  0\n" +
                        " 2. Bayer Leverkusen              0  0  0  0  0:0  0\n" +
                        " 2. Borussia Dortmund             0  0  0  0  0:0  0\n" +
                        " 2. Borussia Moenchengladbach     0  0  0  0  0:0  0\n" +
                        " 2. Eintracht Frankfurt           0  0  0  0  0:0  0\n" +
                        " 2. FC Augsburg                   0  0  0  0  0:0  0\n" +
                        " 2. FC Ingolstadt                 0  0  0  0  0:0  0\n" +
                        " 2. FSV Mainz 05                  0  0  0  0  0:0  0\n" +
                        " 2. Hamburger SV                  0  0  0  0  0:0  0\n" +
                        " 2. Hertha BSC Berlin             0  0  0  0  0:0  0\n" +
                        " 2. RasenBall Leipzig             0  0  0  0  0:0  0\n" +
                        " 2. SC Freiburg                   0  0  0  0  0:0  0\n" +
                        " 2. Schalke 04                    0  0  0  0  0:0  0\n" +
                        " 2. SV Darmstadt                  0  0  0  0  0:0  0\n" +
                        " 2. TSG 1899 Hoffenheim           0  0  0  0  0:0  0\n" +
                        " 2. VfL Wolfsburg                 0  0  0  0  0:0  0\n" +
                        "18. Werder Bremen                 1  0  0  1  0:6  0";
        System.out.println(expectedTable);
    }
    public static String table(String[] results){
        TreeMap<String, Team> map = new TreeMap<String, Team>();
        for(String x: results) {
            try {
                int a = Integer.parseInt(x.charAt(0) + "");
                int b = Integer.parseInt(x.charAt(2) + "");
                String teamA = x.substring(x.indexOf(" ") + 1, x.indexOf("-") - 1);
                String teamB = x.substring(x.indexOf("-") + 2);
                if (!map.containsKey(teamA)) {
                    map.put(teamA, new Team(teamA,1,(a > b) ? 1 : 0, (a == b) ? 1 : 0, (a < b) ? 1 : 0,a,b,(a > b) ? 3 : (a == b) ? 1 : 0));
                } else{
                    Team t = map.get(teamA);
                    t.played++;
                    t.won += (a > b) ? 1 : 0;
                    t.tied += (a == b) ? 1 : 0;
                    t.lost += (a < b) ? 1 : 0;
                    t.scored += a;
                    t.conceded += b;
                    t.points += (a > b) ? 3 : (a == b) ? 1 : 0;
                }
                if (!map.containsKey(teamB)) {
                    map.put(teamB, new Team(teamB,1,(b > a) ? 1 : 0, (a == b) ? 1 : 0, (b < a) ? 1 : 0,b,a,(b > a) ? 3 : (a == b) ? 1 : 0));
                } else{
                    Team t = map.get(teamB);
                    t.played++;
                    t.won += (b > a) ? 1 : 0;
                    t.tied += (a == b) ? 1 : 0;
                    t.lost += (b < a) ? 1 : 0;
                    t.scored += b;
                    t.conceded += a;
                    t.points += (b > a) ? 3 : (a == b) ? 1 : 0;
                }
            } catch (NumberFormatException e) {
                int a = 0;
                int b = 0;
                x = x.substring(3);
                String teamA = x.substring(x.indexOf(" ") + 1, x.indexOf("-") - 1);
                String teamB = x.substring(x.indexOf("-") + 2);
                if (!map.containsKey(teamA)) {
                    map.put(teamA, new Team(teamA,0,0, 0, 0,a,b,0));
                }
                if (!map.containsKey(teamB)) {
                    map.put(teamB, new Team(teamB,0,0, 0, 0,b,a,0));
                }
            }
        }
        PriorityQueue<Team> pq = new PriorityQueue<Team>(new Comparator<Team>() {
            @Override
            public int compare(Team o1, Team o2) {
                if(o1.points == o2.points){
                    if(o1.scored - o1.conceded == o2.scored - o2.conceded){
                        if(o1.scored == o2.scored){
                            return o1.name.compareTo(o2.name);
                        }
                        return o2.scored - o1.scored;
                    }
                    return o2.scored - o1.scored - (o2.conceded - o1.conceded);
                }
                return o2.points - o1.points;
            }
        });
        for(Map.Entry<String, Team> entry: map.entrySet()){
            pq.add(entry.getValue());
        }
        //print pq
        int i = 0;
        ArrayList<Team> ts = new ArrayList<Team>();
        String s = "";
        while(!pq.isEmpty()) {
            s += "\n";
            ts.add(pq.poll());
            try {
                if (compareToVerify(ts.get(ts.size() - 1), ts.get(ts.size() - 2)) != 0) {
                    i = ts.size();
                }
            } catch(IndexOutOfBoundsException e){
                i = ts.size();
            }
            if(i < 10)
                s += " " + i + ". " + ts.get(ts.size() - 1);
            else
                s += i + ". " + ts.get(ts.size() - 1);
        }
        s = s.substring(2);
        return s;
    }

    public static int compareToVerify(Team o1, Team o2) {
        if(o1.points == o2.points){
            if(o1.scored - o1.conceded == o2.scored - o2.conceded){
                if(o1.scored == o2.scored){
                    return 0;
                }
                return o2.scored - o1.scored;
            }
            return o2.scored - o1.scored - (o2.conceded - o1.conceded);
        }
        return o2.points - o1.points;
    }

    public static class Team implements Comparable<Team>{
        String name;
        int played, won, tied, lost, conceded, scored, points;

        public Team(String name){
            this.name = name;
            played = 0;
            won = 0;
            tied = 0;
            lost = 0;
            conceded = 0;
            scored = 0;
            points = 0;
        }

        public Team(String name, int played, int won, int tied, int lost, int scored, int conceded, int points){
            this.name = name;
            this.played = played;
            this.won = won;
            this.tied = tied;
            this.lost = lost;
            this.conceded = conceded;
            this.points = points;
            this.scored = scored;
        }

        @Override
        public String toString() {
            return String.format("%-29s %d  %d  %d  %d  %d:%d  %d", name, played, won, tied, lost, scored, conceded, points);
        }

        @Override
        public int compareTo(Team o) {
            if(this.points > o.points){
                return -1;
            } else if(this.points < o.points){
                return 1;
            } else {
                if (this.scored - this.conceded > o.scored - o.conceded) {
                    return -1;
                } else if (this.scored - this.conceded < o.scored - o.conceded) {
                    return 1;
                } else {
                    if (this.scored > o.scored) {
                        return -1;
                    } else if (this.scored < o.scored) {
                        return 1;
                    } else {
                        return this.name.toLowerCase().compareTo(o.name.toLowerCase());
                    }
                }
            }
        }
    }

    public static <K, V extends Comparable<V> > Map<K, V>
    valueSort(final Map<K, V> map)
    {
        // Static Method with return type Map and
        // extending comparator class which compares values
        // associated with two keys
        Comparator<K> valueComparator = new Comparator<K>() {

            // return comparison results of values of
            // two keys
            public int compare(K k1, K k2)
            {
                int comp = map.get(k1).compareTo(
                        map.get(k2));
                if (comp == 0)
                    return 1;
                else
                    return comp;
            }

        };

        // SortedMap created using the comparator
        Map<K, V> sorted = new TreeMap<K, V>(valueComparator);

        sorted.putAll(map);

        return sorted;
    }
}
