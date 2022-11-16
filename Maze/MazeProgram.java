package Maze;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class MazeProgram extends JPanel implements KeyListener {
    JFrame frame;
    String[][] maze;
    Hero hero;
    int endR, endC;
    int s = 40;
    int moves = 0;
    int[] scolor = new int[]{180,180,180};

    ArrayList<Wall> walls = new ArrayList<Wall>();
    boolean in2D = true;
    public MazeProgram(){
        frame = new JFrame("Maze");
        frame.add(this);
        frame.setSize(1000, 800);

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
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            in2D = !in2D;
        } else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
            System.exit(0);
        }
        if(!in2D)
            set3D();
        hero.move(e.getKeyCode());
        set3D();
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
                                    moves++;
                                }
                            } catch (ArrayIndexOutOfBoundsException e){
                                System.out.println("Out of bounds");
                            }
                            break;
                        case 'E':
                            try{
                                if(maze[r][c+1].equals(" ")){
                                    c++;
                                    moves++;
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
                                    moves++;
                                }
                            } catch (ArrayIndexOutOfBoundsException e){
                                System.out.println("Out of bounds");
                            }
                            break;
                        case 'W':
                            try{
                                if(maze[r][c-1].equals(" ")){
                                    c--;
                                    moves++;
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
        }
    }

    public static void main(String[] args) {
        new MazeProgram();
    }
    int scale = 50;
    public void leftWall(int i){
        int[] x = {100 + 50*i, 150 + 50*i, 150 + 50*i, 100 + 50*i};
        int[] y = {100 + scale*i, 100 + scale*i, 550-scale*i, 550-scale*i};
        walls.add(new Wall(x, y, scolor[0]-s*i, scolor[1]-s*i, scolor[2]-s*i, "left"));
    }
    //build right wall
    public void rightWall(int i){
        int[] x = {700 - 50*i, 650 - 50*i, 650 - 50*i, 700 - 50*i};
        int[] y = {100 + scale*i, 100 + scale*i, 550-scale*i, 550-scale*i};
        walls.add(new Wall(x, y, scolor[0]-s*i, scolor[1]-s*i, scolor[2]-s*i, "right"));
    }
    //build top wall
    public void topWall(int i){
        int[] y = new int[]{100 + 50 * i, 100 + 50 * i, 150 + 50 * i, 150 + 50 * i};
        int[] x;
        if(i != -1)
            x = new int[]{100 + 50 * i, 700 - 50 * i, 700 - 50 * i, 100 + 50 * i};
        else
            x = new int[]{100, 700, 700, 100};
        walls.add(new Wall(x, y, scolor[0]-s*i, scolor[1]-s*i, scolor[2]-s*i,"top"));
    }
    //build bottom wall
    public void bottomWall(int i){
        int[] x;
        int[] y = {550 - 50*i, 550 - 50*i, 500 - 50*i, 500 - 50*i};
        if(i != -1)
            x = new int[]{100 + 50 * i, 700 - 50 * i, 700 - 50 * i, 100 + 50 * i};
        else
            x = new int[]{100, 700, 700, 100};
        walls.add(new Wall(x, y, scolor[0]-s*i, scolor[1]-s*i, scolor[2]-s*i,"bottom"));
    }


    public class Wall{
        int[] x;
        int[] y;
        int r,g,b,rf,gf,bf;

        public String getType() {
            return type;
        }

        String type;
        public Wall(int[] x, int[] y){
            this.x = x;
            this.y = y;
        }

        public Wall(int[] x, int[] y, int r, int g, int b, String type){
            this.x = x;
            this.y = y;
            this.r = r;
            this.g = g;
            this.b = b;
            this.type = type;
            this.gf = g - 50;
            this.rf = r - 50;
            this.bf = b - 50;
            if(gf < 0)
                gf = 0;
            if(rf < 0)
                rf = 0;
            if(bf < 0)
                bf = 0;
            if(r < 0)
                this.r = 0;
            if(g < 0)
                this.g = 0;
            if(b < 0)
                this.b = 0;
        }

        public Polygon getPoly(){
            return new Polygon(x, y, 4);
        }

        //getPaint() returns GradientPaint object
        public GradientPaint getPaint(){
            switch(type){
                case "right":
                case "left":
                    return new GradientPaint(x[0], y[0], new Color(r, g, b), x[1], y[0], new Color(rf, gf, bf));
            }
            return new GradientPaint(x[0], y[0], new Color(r, g, b), x[0], y[2], new Color(rf, gf, bf));
        }

        @Override
        public String toString(){
            return Arrays.toString(x) + " " + Arrays.toString(y);
        }
    }

    public void set3D(){
        //walls.clear();
        walls = new ArrayList<Wall>();
        for(int i = 0; i < 5; i++){
            leftWall(i);
            rightWall(i);
            topWall(i);
            if(i == 0){
                bottomWall(i-1);
                topWall(i-1);
            }
            bottomWall(i);
        }
        int r = hero.getR();
        int c = hero.getC();
        final int[] WALLF = {500, 450, 400, 350, 300};
        int curri = 5;
        Wall fwall = null;
        for(int i = 0; i < 5; i++){
            try {
                switch (hero.getDirection()) {
                    case 'E':
                        //leftwalll
                        if (maze[hero.getR() - 1][hero.getC() + i].equals("*")) {
                            int[] x = {100 + 50 * i, 150 + 50 * i, 150 + 50 * i, 100 + 50 * i};
                            int[] y = {50 + 50 * i, 100 + 50 * i, 550 - 50 * i, 600 - 50 * i};
                            walls.add(new Wall(x, y, scolor[0]-s*i, scolor[1]-s*i, scolor[2]-s*i, "left"));
                        } else{
                            int[] x = {100 + 50 * i, 150 + 50 * i, 150 + 50 * i, 100 + 50 * i};
                            int[] y = {100 + 50 * i, 100 + 50 * i, 550 - 50 * i, 550 - 50 * i};
                            walls.add(new Wall(x, y, scolor[0]-s*i, scolor[1]-s*i, scolor[2]-s*i, "right"));
                        }
                        //right wall
                        if (maze[hero.getR() + 1][hero.getC() + i].equals("*")) {
                            int[] x = {700 - 50 * i, 650 - 50 * i, 650 - 50 * i, 700 - 50 * i};
                            int[] y = {50 + 50 * i, 100 + 50 * i, 550 - 50 * i, 600 - 50 * i};
                            walls.add(new Wall(x, y, scolor[0]-s*i, scolor[1]-s*i, scolor[2]-s*i, "right"));
                        } else{
                            int[] x = {700 - 50 * i, 650 - 50 * i, 650 - 50 * i, 700 - 50 * i};
                            int[] y = {100 + 50 * i, 100 + 50 * i, 550 - 50 * i, 550 - 50 * i};
                            walls.add(new Wall(x, y, scolor[0]-s*i, scolor[1]-s*i, scolor[2]-s*i, "right"));
                        }
                        //front wall
                        if (maze[hero.getR()][hero.getC() + i].equals("*")) {
                            int[] x = {100 + 50 * i, 700 - 50 * i, 700 - 50 * i, 100 + 50 * i};
                            int[] y = {50 + 50 * i, 50 + 50 * i, 100 + WALLF[i], 100 + WALLF[i]};
                            if(i < curri){
                                fwall = new Wall(x, y, scolor[0]-s*i, scolor[1]-s*i, scolor[2]-s*i, "front");
                                curri = i;
                            }
                        }
                        break;
                    case 'N':
                        //leftwall
                        if (maze[hero.getR() - i][hero.getC() - 1].equals("*")) {
                            int[] x = {100 + 50 * i, 150 + 50 * i, 150 + 50 * i, 100 + 50 * i};
                            int[] y = {50 + 50 * i, 100 + 50 * i, 550 - 50 * i, 600 - 50 * i};
                            walls.add(new Wall(x, y, scolor[0]-s*i, scolor[1]-s*i, scolor[2]-s*i, "left"));
                        } else{
                            int[] x = {100 + 50 * i, 150 + 50 * i, 150 + 50 * i, 100 + 50 * i};
                            int[] y = {100 + 50 * i, 100 + 50 * i, 550 - 50 * i, 550 - 50 * i};
                            walls.add(new Wall(x, y, scolor[0]-s*i, scolor[1]-s*i, scolor[2]-s*i, "right"));
                        }
                        //right wall
                        if (maze[hero.getR() - i][hero.getC() + 1].equals("*")) {
                            int[] x = {700 - 50 * i, 650 - 50 * i, 650 - 50 * i, 700 - 50 * i};
                            int[] y = {50 + 50 * i, 100 + 50 * i, 550 - 50 * i, 600 - 50 * i};
                            walls.add(new Wall(x, y, scolor[0]-s*i, scolor[1]-s*i, scolor[2]-s*i, "right"));
                        } else{
                            int[] x = {700 - 50 * i, 650 - 50 * i, 650 - 50 * i, 700 - 50 * i};
                            int[] y = {100 + 50 * i, 100 + 50 * i, 550 - 50 * i, 550 - 50 * i};
                            walls.add(new Wall(x, y, scolor[0]-s*i, scolor[1]-s*i, scolor[2]-s*i, "right"));
                        }
                        //front wall
                        if (maze[hero.getR()-i][hero.getC()].equals("*")) {
                            int[] x = {100 + 50 * i, 700 - 50 * i, 700 - 50 * i, 100 + 50 * i};
                            int[] y = {50 + 50 * i, 50 + 50 * i, 100 + WALLF[i], 100 + WALLF[i]};
                            if(i <= curri){
                                fwall = new Wall(x, y, scolor[0]-s*i, scolor[1]-s*i, scolor[2]-s*i, "front");
                                curri = i;
                            }
                        }
                        break;
                    case 'S':
                        //left wall
                        if (maze[hero.getR() + i][hero.getC() + 1].equals("*")) {
                            int[] x = {100 + 50 * i, 150 + 50 * i, 150 + 50 * i, 100 + 50 * i};
                            int[] y = {50 + 50 * i, 100 + 50 * i, 550 - 50 * i, 600 - 50 * i};
                            walls.add(new Wall(x, y, scolor[0]-s*i, scolor[1]-s*i, scolor[2]-s*i, "left"));
                        } else{
                            int[] x = {100 + 50 * i, 150 + 50 * i, 150 + 50 * i, 100 + 50 * i};
                            int[] y = {100 + 50 * i, 100 + 50 * i, 550 - 50 * i, 550 - 50 * i};
                            walls.add(new Wall(x, y, scolor[0]-s*i, scolor[1]-s*i, scolor[2]-s*i, "right"));
                        }
                        //right wall
                        if (maze[hero.getR() + i][hero.getC() - 1].equals("*")) {
                            int[] x = {700 - 50 * i, 650 - 50 * i, 650 - 50 * i, 700 - 50 * i};
                            int[] y = {50 + 50 * i, 100 + 50 * i, 550 - 50 * i, 600 - 50 * i};
                            walls.add(new Wall(x, y, scolor[0]-s*i, scolor[1]-s*i, scolor[2]-s*i, "right"));
                        } else{
                            int[] x = {700 - 50 * i, 650 - 50 * i, 650 - 50 * i, 700 - 50 * i};
                            int[] y = {100 + 50 * i, 100 + 50 * i, 550 - 50 * i, 550 - 50 * i};
                            walls.add(new Wall(x, y, scolor[0]-s*i, scolor[1]-s*i, scolor[2]-s*i, "right"));
                        }
                        //front wall
                        if (maze[hero.getR()+i][hero.getC()].equals("*")) {
                            int[] x = {100 + 50 * i, 700 - 50 * i, 700 - 50 * i, 100 + 50 * i};
                            int[] y = {50 + 50 * i, 50 + 50 * i, 100 + WALLF[i], 100 + WALLF[i]};
                            if(i <= curri){
                                fwall = new Wall(x, y, scolor[0]-s*i, scolor[1]-s*i, scolor[2]-s*i, "front");
                                curri = i;
                            }
                        }
                        break;
                    case 'W':
                        //left wall
                        if (maze[hero.getR() + 1][hero.getC() - i].equals("*")) {
                            int[] x = {100 + 50 * i, 150 + 50 * i, 150 + 50 * i, 100 + 50 * i};
                            int[] y = {50 + 50 * i, 100 + 50 * i, 550 - 50 * i, 600 - 50 * i};
                            walls.add(new Wall(x, y, scolor[0]-s*i, scolor[1]-s*i, scolor[2]-s*i, "left"));
                        } else{
                            int[] x = {100 + 50 * i, 150 + 50 * i, 150 + 50 * i, 100 + 50 * i};
                            int[] y = {100 + 50 * i, 100 + 50 * i, 550 - 50 * i, 550 - 50 * i};
                            walls.add(new Wall(x, y, scolor[0]-s*i, scolor[1]-s*i, scolor[2]-s*i, "right"));
                        }
                        //right wall
                        if (maze[hero.getR() - 1][hero.getC() - i].equals("*")) {
                            int[] x = {700 - 50 * i, 650 - 50 * i, 650 - 50 * i, 700 - 50 * i};
                            int[] y = {50 + 50 * i, 100 + 50 * i, 550 - 50 * i, 600 - 50 * i};
                            walls.add(new Wall(x, y, scolor[0]-s*i, scolor[1]-s*i, scolor[2]-s*i, "right"));
                        } else{
                            int[] x = {700 - 50 * i, 650 - 50 * i, 650 - 50 * i, 700 - 50 * i};
                            int[] y = {100 + 50 * i, 100 + 50 * i, 550 - 50 * i, 550 - 50 * i};
                            walls.add(new Wall(x, y, scolor[0]-s*i, scolor[1]-s*i, scolor[2]-s*i, "right"));
                        }
                        //front wall
                        if (maze[hero.getR()][hero.getC() - i].equals("*")) {
                            int[] x = {100 + 50 * i, 700 - 50 * i, 700 - 50 * i, 100 + 50 * i};
                            int[] y = {50 + 50 * i, 50 + 50 * i, 100 + WALLF[i], 100 + WALLF[i]};
                            if(i <= curri){
                                fwall = new Wall(x, y, scolor[0]-s*i, scolor[1]-s*i, scolor[2]-s*i, "front");
                                curri = i;
                            }
                        }
                        break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
            }
        }
        if(fwall != null){
            walls.add(fwall);
        }
    }
    @Override
    public void paintComponent(Graphics gi) {
        super.paintComponent(gi);
        Graphics2D g = (Graphics2D) gi;
        g.setColor(new Color(100,150,0));
        g.fillRect(0,0,1000,800);
        g.setColor(Color.WHITE);
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getSize() * 8F);
        g.setFont(newFont);
        BufferedImage trophy = null;
        try {
            trophy = ImageIO.read(new File("Maze/trophy.png"));
        } catch (IOException e) {
        }
        if(hero.getC() == 29 && hero.getR() == 29){
            g.drawImage(trophy, 790, 450, 200,200,this);
            g.drawString("Congrats! " + moves, 155, 720);
        } else{
            g.drawString("" + hero.getDirection() + " " + moves, 600, 100);
        }
        if(in2D) {
            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[i].length; j++) {
                    if (maze[i][j].equals("*")) {
                        g.setColor(Color.BLACK);
                        g.fillRect(j * 10, i * 10, 10, 10);
                    }
                    if(i == 29 && j == 29){
                        g.setColor(Color.YELLOW);
                        g.fillRect(j * 10, i * 10, 10, 10);
                    }
                }
            }
            g.setColor(Color.WHITE);
            g.fillOval(hero.getC() * 10, hero.getR() * 10, 10, 10);
        } else{
            int i0 = hero.getR()-10;
            int j0 = hero.getC()-10;
            for (int i = i0; i < i0+20; i++) {
                for (int j = j0; j < j0+20; j++) {
                    try{
                        if (maze[i][j].equals("*")) {
                            g.setColor(Color.BLACK);
                            g.fillRect(750 + (j-j0) * 10, 200 + (i-i0) * 10, 10, 10);
                        }
                        if(i == 29 && j == 29){
                            g.setColor(Color.YELLOW);
                            g.fillRect(750 + (j-j0) * 10, 200 + (i-i0) * 10, 10, 10);
                        }
                    } catch (ArrayIndexOutOfBoundsException e){}
                }
            }
            g.setColor(Color.WHITE);
            g.fillOval(850, 300, 10, 10);
            for(Wall w: walls){
                g.setPaint(w.getPaint());
                g.fillPolygon(w.getPoly());
                g.setColor(Color.BLACK);
                g.drawPolygon(w.getPoly());
            }
        }
    }

    public void setMaze(){
        maze = new String[31][31];
        hero = new Hero(1,1,'E');
        endR = 29;
        endC = 29;
        String s = (new MazeMaker(15,15)).send();
        s = s.replaceAll("\n","");
        StringTokenizer st = new StringTokenizer(s, "+,-,|, ,\n", true);
        for(int i = 0; i < 31; i++){
            for(int j = 0; j < 31; j++){
                String sp = st.nextToken();
                switch(sp) {
                    case "+":
                        maze[i][j] = "*";
                        break;
                    case "-":
                        maze[i][j] = "*";
                        break;
                    case "|":
                        maze[i][j] = "*";
                        break;
                    case " ":
                        maze[i][j] = " ";
                        break;
                }
            }
        }
    }
}