package MSGame;





import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;
import java.io.*;
public class Minesweeper2 extends JFrame implements MouseListener, ActionListener {
    JToggleButton[][] buttons;
    JPanel buttonPanel;
    int mines = 10;
    boolean firstFlag = true;
    boolean gameOver;
    ImageIcon[] numbers = new ImageIcon[8];
    ImageIcon flag, mine, smile, win, wait, dead;
    int selectedCount = 0;
    boolean[][] selectedLocs;
    Timer timer;
    int timePassed = 0;
    JTextField timeField;
    GraphicsEnvironment ge;
    Font clockFont;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem beginner, intermediate, expert;
    JButton reset;

    public Minesweeper2(){
        for(int i = 1; i <= 8; i++){
            numbers[i-1] = new ImageIcon("MSGame/MineSweeperImages/"+ i + ".png");

            numbers[i-1] = new ImageIcon(numbers[i-1].getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        }
        flag = new ImageIcon("MSGame/MineSweeperImages/flag0.png");

        flag = new ImageIcon(flag.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));

        mine = new ImageIcon("MSGame/MineSweeperImages/mine0.png");

        mine = new ImageIcon(mine.getImage().getScaledInstance(50,50,Image.SCALE_SMOOTH));
        try {
            ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            clockFont=Font.createFont(Font.TRUETYPE_FONT,
                    new File("MSGame/digital-7.ttf"));
            ge.registerFont(clockFont);
        } catch (IOException|FontFormatException e) {}
        timeField = new JTextField("   "+timePassed);
        timeField.setFont(clockFont.deriveFont(18f));
        timeField.setBackground(Color.BLACK);
        timeField.setForeground(Color.GREEN);

        menuBar = new JMenuBar();
        menu = new JMenu("Difficulty");
        beginner = new JMenuItem("Beginner");
        intermediate = new JMenuItem("Intermediate");
        expert = new JMenuItem("Expert");
        menu.add(beginner);
        menu.add(intermediate);
        menu.add(expert);
        beginner.addActionListener(this);
        intermediate.addActionListener(this);
        expert.addActionListener(this);
        menuBar.add(menu);
        reset = new JButton("Reset");
        reset.addActionListener(this);
        menuBar.add(reset);
        menuBar.add(timeField);
        setGrid(9,9);
        this.setJMenuBar(menuBar);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    public void setGrid(int r, int c){
        mine = new ImageIcon("MSGame/MineSweeperImages/mine0.png");
        if(buttonPanel != null)
            this.remove(buttonPanel);
        firstFlag = true;
        gameOver = false;
        buttons = new JToggleButton[r][c];
        selectedLocs = new boolean[r][c];
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(r,c));
        for(int row = 0; row < r; row++){
            for(int col = 0; col < c; col++){
                buttons[row][col] = new JToggleButton();
                buttons[row][col].putClientProperty("row",row);
                buttons[row][col].putClientProperty("col", col);
                buttons[row][col].putClientProperty("state",0);
                buttons[row][col].putClientProperty("flag",false);
                buttons[row][col].addMouseListener(this);
                buttonPanel.add(buttons[row][col]);
                selectedLocs[row][col] = false;
            }
        }
        this.add(buttonPanel, BorderLayout.CENTER);
        this.setSize(c*50,r*50);
        this.revalidate();
    }
    public static void main(String[] args) {
        new Minesweeper2();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int rC = (int)((JToggleButton) e.getComponent()).getClientProperty("row");
        int cC = (int)((JToggleButton) e.getComponent()).getClientProperty("col");
        if(!gameOver){
            if(e.getButton() == MouseEvent.BUTTON1){
                if(firstFlag){
                    dropMines(rC, cC);
                    firstFlag = false;
                    timer = new Timer();
                    timer.schedule(new UpdateTimer(),0,1000);
                }
                int state = (int) buttons[rC][cC].getClientProperty("state");
                if(state == -1) {

                    for (int r = 0; r < buttons.length; r++) {
                        for (int c = 0; c < buttons[0].length; c++) {
                            state = (int) buttons[r][c].getClientProperty("state");
                            if (state == -1)
                                buttons[r][c].setIcon(mine);
                        }
                    }
                    disableButton();
                    gameOver = true;
                    timer.cancel();
                    JOptionPane.showMessageDialog(null, "YOU GOT BOMBED");
                }
                else{
                    if(!selectedLocs[rC][cC]) {
                        selectedCount++;
                        selectedLocs[rC][cC] = true;
                    }
                    System.out.println(selectedCount);
                    expand(rC,cC);
                    if(selectedCount == buttons.length*buttons[0].length-mines){
                        mine = new ImageIcon("MSGame/MineSweeperImages/win0.png");
                        JOptionPane.showMessageDialog(null,"YOU DID NOT GET BOMBED");
                        disableButton();
                        gameOver = true;
                        timer.cancel();
                    }
                }

            }
            else if(!firstFlag && e.getButton() == MouseEvent.BUTTON3) {
                if (!buttons[rC][cC].isSelected()  && buttons[rC][cC].getIcon() == null) {
                    buttons[rC][cC].putClientProperty("flag",true);
                    buttons[rC][cC].setSelected(false);
                    buttons[rC][cC].setIcon(flag);
                    buttons[rC][cC].setDisabledIcon(flag);
                    buttons[rC][cC].setEnabled(false);
                }
                else if(buttons[rC][cC].getIcon() == null || buttons[rC][cC].getIcon().equals(flag)){
                    buttons[rC][cC].putClientProperty("flag",false);
                    buttons[rC][cC].setIcon(null);
                    buttons[rC][cC].setDisabledIcon(null);
                    buttons[rC][cC].setEnabled(true);
                }
            }
        }

    }
    public void disableButton(){
        for(int r = 0; r < buttons.length; r++){
            for(int c = 0; c < buttons[0].length; c++){
                buttons[r][c].setEnabled(false);
                if((int)buttons[r][c].getClientProperty("state") == -1){
                    buttons[r][c].setIcon(mine);
                    buttons[r][c].setDisabledIcon(mine);
                }
            }
        }
    }
    public void expand(int row, int col){
        if(!buttons[row][col].isSelected()){
            buttons[row][col].setSelected(true);
            if(!selectedLocs[row][col])
                selectedCount++;
            selectedLocs[row][col] = true;
        }
        int state = (int) buttons[row][col].getClientProperty("state");
        if(state > 0 && !(boolean)(buttons[row][col].getClientProperty("flag"))){
            buttons[row][col].setIcon(numbers[state-1]);
            buttons[row][col].setDisabledIcon(numbers[state-1]);
        } else{
            for(int r = row-1; r <= row + 1; r++){
                for(int c = col - 1; c <= col + 1; c++){
                    try {
                        if (!buttons[r][c].isSelected() && !flag.equals(buttons[r][c].getIcon())) {
                            expand(r, c);
                        }
                    } catch(ArrayIndexOutOfBoundsException e){}
                }
            }
        }
    }

    public void dropMines(int row, int col){
        int count = mines;
        while(count > 0){
            int r = (int)(Math.random()*buttons.length);
            int c = (int)(Math.random()*buttons[0].length);
            int state = (int) buttons[r][c].getClientProperty("state");
            if(state == 0 && Math.abs(row - r) > 1 && Math.abs(c - col) > 1){
                buttons[r][c].putClientProperty("state",-1);
                count--;
            }
        }
        for(int r = 0; r < buttons.length; r++){
            for(int c= 0; c < buttons[0].length; c++){
                int state = (int) buttons[r][c].getClientProperty("state");
                if(state != -1){
                    count = 0;
                    for(int  a = r-1; a <= r+1; a++){
                        for(int b = c-1; b <= c+1; b++){
                            try{
                                state = (int) buttons[a][b].getClientProperty("state");
                                if(state == -1){
                                    count++;
                                }
                            }
                            catch(ArrayIndexOutOfBoundsException e){}
                        }
                    }
                    buttons[r][c].putClientProperty("state",count);
                }
            }
        }

       /*for(int r = 0; r < buttons.length; r++){
           for(int c= 0; c < buttons[0].length; c++){
               int state = (int) buttons[r][c].getClientProperty("state");
               buttons[r][c].setText(""+state);
           }
       }*/
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(timer != null)
            timer.cancel();
        timePassed = 0;
        timeField.setText("   "+timePassed);
        if (e.getSource() == beginner){
            setGrid(9,9);
            mines = 10;
        } else if (e.getSource() == intermediate){
            setGrid(16,16);
            mines = 40;
        } else if (e.getSource() == expert){
            setGrid(16,40);
            mines = 99;
        } else if(e.getSource() == reset){
            timePassed = 0;
            timeField.setText("   "+timePassed);
            selectedCount = 0;
            mine = new ImageIcon("MSGame/MineSweeperImages/mine0.png");
            setGrid(buttons.length,buttons[0].length);
        }
    }

    public class UpdateTimer extends TimerTask{
        @Override
        public void run() {
            if(!gameOver){
                timePassed++;
                timeField.setText("   "+timePassed);
            }
        }
    }
}
