import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MazeProgram extends JPanel {
    JFrame frame;
    String[][] maze;
    public MazeProgram(){
        frame = new JFrame("Maze");
        frame.add(this);
        frame.setSize(800, 800);

        setMaze();


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
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
                    g.fillRect(j*10, i*10, 10, 10);
                }
            }
        }
    }

    public void setMaze(){
        maze = new String[30][0];
        File filename = new File("MazeDesign.txt");
        try {
            BufferedReader input = new BufferedReader(new FileReader(filename));
            String text;
            int i = 0;
            while ((text = input.readLine()) != null) {
                maze[i] = text.split("");
                i++;
            }
        } catch (IOException io) {
            System.err.println("File error");
        }
    }
}