public class Bundesliga {
    private int rank = 0;
    private String name = "";
    private int points = 0, won = 0, draw = 0, lost = 0, shots = 0, scored = 0, gamesplayed = 0;

    public Bundesliga(int rank, String name, int played, int won, int draw, int lost, int shots, int scored, int points) {
        this.rank = rank;
        this.name = name;
        this.points = points;
        this.won = won;
        this.draw = draw;
        this.lost = lost;
        this.shots = shots;
        this.scored = scored;
        this.gamesplayed = played;
    }
    @Override
    public String toString() {
        return (String.format("%2d", rank)  + ". " + String.format("%1$-" + 30 + "s", name) + gamesplayed + "  " + won + "  " + draw + "  " + lost + "  " + shots + ":" + scored + "  " + points);
    }
    
}
