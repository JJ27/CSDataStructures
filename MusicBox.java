//MusicBox implementation (plays songs)

import javax.sound.sampled.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.filechooser.FileNameExtensionFilter;
public class MusicBox extends JFrame implements Runnable, ActionListener, AdjustmentListener{
    JToggleButton[][] buttons;
    JToggleButton play;
    JButton clear, reset;
    JPanel buttoncontrol, switchboard;
    JScrollPane scrollpane;
    JScrollBar speedBar;
    JMenu instruments, file, add;
    JMenuBar menu;
    JMenuItem bell, glock, marimba, oboe, vocals, piano, save, load, addCol, add10, remCol, rem10;
    ArrayList<String> notes;
    String[] notesAdj = {"C","C#","D","D#","E","F","F#","G","G#","A","A#","B"};
    ArrayList<String> buttonNames = new ArrayList<String>();
    ArrayList<Clip> clips = new ArrayList<Clip>();
    String[] instrumentsList = {"Bell", "Glockenspiel", "Marimba", "Oboe", "Vocals", "Piano"};
    Thread timer;
    String message;
    int count = 0;
    boolean playing = false;
    int speed = 280;
    String workingDir = System.getProperty("user.dir");
    JFileChooser chooser = new JFileChooser(workingDir);
    Character[][] currSong;

    public MusicBox(){
        message = "";
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");

        } catch (Exception e){
            System.out.println("Error setting native LAF: " + e);
        }
        setTitle("Music Box");
        notes = new ArrayList<String>(Arrays.asList("C4", "B3", "A#3", "A3", "G#3", "G3", "F#3", "F3", "E3", "D#3", "D3", "C#3", "C3", "B2", "A#2", "A2", "G#2", "G2", "F#2", "F2", "E2", "D#2", "D2", "C#2", "C2", "B1", "A#1", "A1", "G#1", "G1", "F#1", "F1", "E1", "D#1", "D1", "C#1", "C0"));
        int rows = 37;
        int cols = 50;
        if(buttoncontrol != null){
            scrollpane.remove(buttoncontrol);
            this.remove(scrollpane);
        }
        if (cols < 0)
            cols = 0;
        buttoncontrol = new JPanel();
        buttons = new JToggleButton[rows][cols];
        buttoncontrol.setLayout(new GridLayout(rows, cols));
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                buttons[i][j] = new JToggleButton();
                buttons[i][j].setBackground(Color.WHITE);
                buttons[i][j].setPreferredSize(new Dimension(40,40));
                buttons[i][j].setMinimumSize(new Dimension(40,40));
                buttons[i][j].setMaximumSize(new Dimension(40,40));
                buttons[i][j].setText(notes.get(i));
                buttons[i][j].setMargin(new Insets(0,0,0,0));
                buttons[i][j].setActionCommand(i + "," + j);
                buttons[i][j].addActionListener(this);
                buttoncontrol.add(buttons[i][j]);
            }
        }
        scrollpane = new JScrollPane(buttoncontrol, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //this.add(scrollpane);

        buttoncontrol = new JPanel();
        buttoncontrol.setLayout(new GridLayout(1, 2));
        setSize(3400,4500);
        play = new JToggleButton("Play");
        play.addActionListener(this);
        clear = new JButton("Clear");
        clear.addActionListener(this);
        reset = new JButton("Reset");
        reset.addActionListener(this);

        instruments = new JMenu("Instruments");
        instruments.setMnemonic(KeyEvent.VK_I);
        menu = new JMenuBar();
        bell = new JMenuItem("Bell");
        bell.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, ActionEvent.CTRL_MASK));
        bell.addActionListener(this);
        glock = new JMenuItem("Glockenspiel");
        glock.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.CTRL_MASK));
        glock.addActionListener(this);
        marimba = new JMenuItem("Marimba");
        marimba.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, ActionEvent.CTRL_MASK));
        marimba.addActionListener(this);
        oboe = new JMenuItem("Oboe");
        oboe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
        oboe.addActionListener(this);
        vocals = new JMenuItem("Vocals");
        vocals.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
        vocals.addActionListener(this);
        piano = new JMenuItem("Piano");
        piano.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        piano.addActionListener(this);
        instruments.add(bell);
        instruments.add(glock);
        instruments.add(marimba);
        instruments.add(oboe);
        instruments.add(vocals);
        instruments.add(piano);

        file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        save = new JMenuItem("Save");
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        save.addActionListener(this);
        load = new JMenuItem("Load");
        load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK));
        load.addActionListener(this);
        file.add(save);
        file.add(load);
        add = new JMenu("Add/Remove");
        addCol = new JMenuItem("Add Column");
        addCol.addActionListener(this);
        add10 = new JMenuItem("Add 10 Columns");
        add10.addActionListener(this);
        remCol = new JMenuItem("Remove Column");
        remCol.addActionListener(this);
        rem10 = new JMenuItem("Remove 10 Columns");
        rem10.addActionListener(this);
        add.add(addCol);
        add.add(add10);
        add.add(remCol);
        add.add(rem10);

        setInstrument("Bell");
        speedBar = new JScrollBar(JScrollBar.HORIZONTAL, 280, 0, 50, 350);
        speedBar.addAdjustmentListener(this);
        buttoncontrol.add(play);
        buttoncontrol.add(clear);
        buttoncontrol.add(reset);
        menu.add(file, BorderLayout.CENTER);
        menu.add(instruments, BorderLayout.CENTER);
        menu.add(buttoncontrol, BorderLayout.CENTER);
        menu.add(add, BorderLayout.CENTER);
        this.add(speedBar, BorderLayout.SOUTH);
        this.setJMenuBar(menu);
        this.add(scrollpane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        timer = new Thread(this);
        timer.start();
    }
    public void setInstrument(String instrument){
        clips.clear();
        if(instrument.equals("Vocals"))
            instrument = "oh_ah";
        try {
            for (int i = 0; i < notes.size(); i++) {
                String n = notes.get(i).replaceAll("#", "Sharp");
                System.out.println(workingDir + "/MBT/" + instrument + "/" + instrument + " - " + n + ".wav");
                File f = new File(workingDir + "/MBT/" + instrument + "/" + instrument + " - " + n + ".wav");
                AudioInputStream aI = AudioSystem.getAudioInputStream(f);
                clips.add(AudioSystem.getClip());
                clips.get(i).open(aI);
            }
        } catch (Exception e){
            System.out.println("Error setting instrument: " + e);
        }
    }
    public static void main(String[] args) {
        new MusicBox();
    }
    public void adjustmentValueChanged(AdjustmentEvent e) {
        speed = speedBar.getValue();
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == play){
            if(playing){
                count = 0;
                play.setText("Play");
                playing = false;
                play.setSelected(false);
            } else {
                play.setText("Pause");
                playing = true;
                count = 0;
                play.setSelected(true);
            }
        } else if(e.getSource() == clear){
            for(int i = 0; i < buttons.length; i++){
                for(int j = 0; j < buttons[i].length; j++){
                    buttons[i][j].setSelected(false);
                }
            }
            count = 0;
            playing = false;
            play.setText("Play");
        } else if(e.getSource() == reset){
            count = 0;
            playing = false;
            play.setText("Play");
            play.setSelected(false);
        } else if(e.getSource() == bell){
            setInstrument("Bell");
        } else if(e.getSource() == glock){
            setInstrument("Glockenspiel");
        } else if(e.getSource() == marimba){
            setInstrument("Marimba");
            message = "Marimba";
        } else if(e.getSource() == oboe){
            setInstrument("Oboe");
        } else if(e.getSource() == vocals){
            setInstrument("Vocals");
        } else if(e.getSource() == piano){
            setInstrument("Piano");
        } else if(e.getSource() == save){
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
            chooser.setFileFilter(filter);
            int val = chooser.showSaveDialog(this);
            if(val == JFileChooser.APPROVE_OPTION){
                File f = chooser.getSelectedFile();
                try{
                    String loc = f.getAbsolutePath();
                    if(!loc.endsWith(".txt")){
                        loc += ".txt";
                    }
                    String song = "";
                    String[] notenames = {" ","c ", "b ", "a-", "a ", "g-", "g ", "f-", "f ", "e ", "d-", "d ", "c-", "c ", "b ", "a-", "a ","g-", "g ", "f-", "f ", "e ", "d-","d ","c-","c ", "b ", "a-", "a ", "g-", "g ", "f-","f ", "e ", "d-","d ", "c-", "c "};
                    for(int i = -1; i < buttons.length; i++){
                        if(i == -1) {
                            song += speed + " " + buttons[0].length + "\n";
                            continue;
                        }
                        song += notenames[i+1] + " ";
                        for(int j = 0; j < buttons[i].length; j++){
                            if(buttons[i][j].isSelected()){
                                song += "x";
                            } else {
                                song += "-";
                            }
                        }
                        song += "\n";
                    }
                    BufferedWriter out = new BufferedWriter(new FileWriter(loc));
                    out.write(song);
                    out.close();
                } catch(IOException ex){
                    System.out.println("Error saving file: " + ex);
                }
            }
        } else if(e.getSource() == load) {
            int val = chooser.showOpenDialog(this);
            if(val == JFileChooser.APPROVE_OPTION){
                try{
                    File f = chooser.getSelectedFile();
                    BufferedReader in = new BufferedReader(new FileReader(f));
                    String line = in.readLine();
                    String[] data = line.split(" ");
                    speed = Integer.parseInt(data[0]);
                    speedBar.setValue(speed);
                    int cols = Integer.parseInt(data[1]);
                    int rows = 37;
                    if(buttoncontrol != null){
                        scrollpane.remove(buttoncontrol);
                        this.remove(scrollpane);
                    }
                    if (cols < 0)
                        cols = 0;
                    buttoncontrol = new JPanel();
                    buttons = new JToggleButton[rows][cols];
                    buttoncontrol.setLayout(new GridLayout(rows, cols));
                    for(int i = 0; i < rows; i++){
                        for(int j = 0; j < cols; j++){
                            buttons[i][j] = new JToggleButton();
                            buttons[i][j].setBackground(Color.WHITE);
                            buttons[i][j].setPreferredSize(new Dimension(40,40));
                            buttons[i][j].setMinimumSize(new Dimension(40,40));
                            buttons[i][j].setMaximumSize(new Dimension(40,40));
                            buttons[i][j].setText(notes.get(i));
                            buttons[i][j].setMargin(new Insets(0,0,0,0));
                            buttons[i][j].setActionCommand(i + "," + j);
                            buttons[i][j].addActionListener(this);
                            buttoncontrol.add(buttons[i][j]);
                        }
                    }
                    scrollpane = new JScrollPane(buttoncontrol, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
                    this.add(scrollpane);
                    currSong = new Character[rows][cols];
                    int iterator = 0;
                    while((line = in.readLine()) != null){
                        for(int i = 0; i < rows; i++){
                            currSong[i][iterator] = line.charAt(i+3);
                        }
                        iterator++;
                    }
                    resetNotes(currSong);
                } catch(IOException ex){
                    System.out.println("Error loading file: " + ex);
                }
            }
        }
        //add column
        else if(e.getSource() == addCol){
            addCol(1);
        } else if(e.getSource() == add10){
            addCol(10);
        } else if(e.getSource() == remCol){
            addCol(-1);
        } else if(e.getSource() == rem10){
            addCol(-10);
        }
    }
    public void addCol(int colsToAdd){
        int rows = 37;
        if(colsToAdd < 0) {
            if (buttons[0].length + colsToAdd < 20) {
                colsToAdd = 20 - buttons[0].length;
            }
        }
        int cols = buttons[0].length + colsToAdd;
        if(buttoncontrol != null){
            scrollpane.remove(buttoncontrol);
            this.remove(scrollpane);
        }
        if (cols < 0)
            cols = 0;
        buttoncontrol = new JPanel();
        JToggleButton[][] buttons2 = new JToggleButton[rows][cols];
        buttoncontrol.setLayout(new GridLayout(rows, cols));
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                buttons2[i][j] = new JToggleButton();
                buttons2[i][j].setBackground(Color.WHITE);
                buttons2[i][j].setPreferredSize(new Dimension(40,40));
                buttons2[i][j].setMinimumSize(new Dimension(40,40));
                buttons2[i][j].setMaximumSize(new Dimension(40,40));
                buttons2[i][j].setText(notes.get(i));
                buttons2[i][j].setMargin(new Insets(0,0,0,0));
                buttons2[i][j].setActionCommand(i + "," + j);
                buttons2[i][j].addActionListener(this);
                if(j < buttons[i].length)
                    buttons2[i][j].setSelected(buttons[i][j].isSelected());
                buttoncontrol.add(buttons2[i][j]);
            }
        }
        buttons = buttons2;
        scrollpane = new JScrollPane(buttoncontrol, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.add(scrollpane);
        revalidate();
    }
    public void resetNotes(Character[][] notes){
        message = "Reset Notes!";
        scrollpane.remove(buttoncontrol);
        this.remove(scrollpane);
        scrollpane = new JScrollPane(buttoncontrol, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        for(int i = 0; i < buttons.length; i++){
            for(int j = 0; j < buttons[i].length && j < notes[i].length; j++){
                System.out.println(i + " " + j + " N: " + notes[i][j]);
                if(notes[i][j] != null && notes[i][j] == 'x'){
                    buttons[i][j].setSelected(true);
                    //System.out.println("Selected: " + i + "," + j);
                }
            }
        }
        this.add(scrollpane, BorderLayout.CENTER);
        revalidate();
    }
    public void run(){
        while(true){
            System.out.println(message);
            try{
                if(playing){
                    for(int i = 0; i < buttons.length; i++){
                        if(buttons[i][count].isSelected()){
                            clips.get(i).start();
                            //buttons[i][count].setBackground(Color.GREEN);
                        }
                    }
                    if(560 - speed <= 0)
                        speed = 550;
                    timer.sleep(560 - speed);
                    for(int i = 0; i < buttons.length; i++){
                        if(buttons[i][count].isSelected()){
                            clips.get(i).stop();
                            clips.get(i).setFramePosition(0);
                            buttons[i][count].setBackground(Color.WHITE);
                        }
                    }
                    count++;
                    if(count == buttons[0].length - 1){
                        count = 0;
                    }
                }
            } catch (InterruptedException ex){
                System.out.println("Error: " + ex);
            }
        }
    }
}
