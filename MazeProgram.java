import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MazeProgram extends JPanel implements KeyListener {
    JFrame frame;
    String[][] maze;
    Hero hero;
    int endR, endC;
    public MazeProgram(){
        frame = new JFrame("Maze");
        frame.add(this);
        frame.setSize(800, 800);

        setMaze();
        frame.addKeyListener(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        hero.move(e.getKeyCode());
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public class Hero{
        public int getR() {
            return r;
        }

        public void setR(int r) {
            this.r = r;
        }

        public int getC() {
            return c;
        }

        public void setC(int c) {
            this.c = c;
        }

        public char getDirection() {
            return direction;
        }

        public void setDirection(char direction) {
            this.direction = direction;
        }

        int r;
        int c;
        char direction;

        public Hero(int r, int c, char direction){
            this.r = r;
            this.c = c;
            this.direction = direction;
        }

        public void move(int key){
            switch(key){
                case 37:
                    //turn left
                    switch(direction){
                        case 'N':
                            direction = 'W';
                            break;
                        case 'E':
                            direction = 'N';
                            break;
                        case 'S':
                            direction = 'E';
                            break;
                        case 'W':
                            direction = 'S';
                            break;
                    }
                    break;
                case 38:
                    //move forward
                    switch(direction){
                        case 'N':
                            try{
                                if(maze[r-1][c].equals(" ")){
                                    r--;
                                }
                            } catch (ArrayIndexOutOfBoundsException e){
                                System.out.println("Out of bounds");
                            }
                            break;
                        case 'E':
                            try{
                                if(maze[r][c+1].equals(" ")){
                                    c++;
                                }
                            } catch (ArrayIndexOutOfBoundsException e){
                                System.out.println("Out of bounds");
                            }
                            break;
                        case 'S':
                            //TODO: Find out why going S twice causes indexoutofbounds
                            try{
                                if(maze[r+1][c].equals(" ")){
                                    r++;
                                }
                            } catch (ArrayIndexOutOfBoundsException e){
                                System.out.println("Out of bounds");
                            }
                            break;
                        case 'W':
                            try{
                                if(maze[r][c-1].equals(" ")){
                                    c--;
                                }
                            } catch (ArrayIndexOutOfBoundsException e){
                                System.out.println("Out of bounds");
                            }
                            break;
                    }
                    break;
                case 39:
                    //turn right
                    switch(direction){
                        case 'N':
                            direction = 'E';
                            break;
                        case 'E':
                            direction = 'S';
                            break;
                        case 'S':
                            direction = 'W';
                            break;
                        case 'W':
                            direction = 'N';
                            break;
                    }
                    break;
            }
            System.out.println(r + " " + c);
        }
    }

    public static void main(String[] args) {
        new MazeProgram();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(100,150,0));
        g.fillRect(0,0,800,800);
        g.setColor(Color.WHITE);
        //iterate through maze
        for(int i = 0; i < maze.length; i++){
            for(int j = 0; j < maze[i].length; j++){
                if(maze[i][j].equals("*")){
                    g.setColor(Color.BLACK);
                    g.fillRect(j*10, i*10, 10, 10);
                }
            }
        }
        g.setColor(Color.WHITE);
        g.fillOval(hero.getC()*10, hero.getR()*10, 10, 10);
    }

    public void setMaze(){
        maze = new String[30][0];
        File filename = new File("MazeDesign.txt");
        try {
            BufferedReader input = new BufferedReader(new FileReader(filename));
            String text;
            boolean first = true;
            int i = -1;
            while ((text = input.readLine()) != null) {
                if(first){
                    String[] pieces = text.split(" ");
                    int r = Integer.parseInt(pieces[0]);
                    int c = Integer.parseInt(pieces[1]);
                    char direction = pieces[2].charAt(0);
                    endR = Integer.parseInt(pieces[3]);
                    endC = Integer.parseInt(pieces[4]);
                    hero = new Hero(r, c, direction);
                    first = false;
                } else
                    maze[i] = text.split("");
                i++;
            }
        } catch (IOException io) {
            System.err.println("File error");
        }
    }
}