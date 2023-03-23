import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.DimensionUIResource;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class JuliaSet extends JPanel implements AdjustmentListener
{
    JFrame frame;
    JScrollBar A, B, saturationBar, brightnessBar, zoomBar, hueBar, maxIterationsBar;
    JLabel aLabel, bLabel, saturationLabel, brightnessLabel, zoomLabel, hueLabel, maxIterationsLabel;
    double a, b;
    JPanel scrollPanel, labelPanel, kingPanel, buttonPanel;
    BufferedImage image;
    JFileChooser fc;
    double zoom = 0.5;
    double saturation = 1;
    double brightness = 1;
    double hue = 0.5;
    double shape = 1;
    double maxIterations = 50;
    int eq = 0;
    public JuliaSet()
    {
        //Main JFrame
        frame = new JFrame("Julia Set Program");
        frame.add(this);
        frame.setSize(1000, 1000);

        //panel for scroll bars
        scrollPanel = new JPanel();
        scrollPanel.setLayout(new GridLayout(7, 1)); //3 rows for 3 scrollbars
        //scrollbars for each color
        A = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, -2000, 2000);
        A.addAdjustmentListener(this);
        B = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, -2000, 2000);
        B.addAdjustmentListener(this);
        saturationBar = new JScrollBar(JScrollBar.HORIZONTAL, 1000, 0, 0, 1000);
        saturationBar.addAdjustmentListener(this);
        hueBar = new JScrollBar(JScrollBar.HORIZONTAL, 500, 0, 0, 1000);
        hueBar.addAdjustmentListener(this);
        brightnessBar = new JScrollBar(JScrollBar.HORIZONTAL, 1000, 0, 0, 1000);
        brightnessBar.addAdjustmentListener(this);
        zoomBar = new JScrollBar(JScrollBar.HORIZONTAL, 500, 0, 0, 1000);
        zoomBar.addAdjustmentListener(this);
        maxIterationsBar = new JScrollBar(JScrollBar.HORIZONTAL, 50, 0, 10, 100);
        maxIterationsBar.addAdjustmentListener(this);
        //add reset button
        JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                A.setValue(0);
                B.setValue(0);
                saturationBar.setValue(1000);
                hueBar.setValue(500);
                brightnessBar.setValue(1000);
                zoomBar.setValue(500);
                maxIterationsBar.setValue(50);
            }
        });
        //add save button
        JButton save = new JButton("Save");
        fc = new JFileChooser(System.getProperty("user.dir"));
        save.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                downloadImage();
            }
        });
        //add open button
        JButton open = new JButton("Open");
        open.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                openImage();
            }
        });
        JButton toggleEq = new JButton("Circle EQ");
        toggleEq.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(toggleEq.getText().equals("Circle EQ")) {
                    toggleEq.setText("Cubic EQ");
                    eq = 1;
                } else if(toggleEq.getText().equals("Cubic EQ")) {
                    toggleEq.setText("Quartic EQ");
                    eq = 2;
                } else {
                    toggleEq.setText("Circle EQ");
                    eq = 0;
                }
                repaint();
            }
        });
        //add scrollbars to panel
        scrollPanel.add(A);
        scrollPanel.add(B);
        scrollPanel.add(saturationBar);
        scrollPanel.add(hueBar);
        scrollPanel.add(brightnessBar);
        scrollPanel.add(zoomBar);
        scrollPanel.add(maxIterationsBar);
        //panel for labels
        labelPanel = new JPanel();
        labelPanel.setPreferredSize(new Dimension(300, 200));
        labelPanel.setLayout(new GridLayout(7, 1)); //3 rows for 3 labels
        //labels for each value
        aLabel = new JLabel("A: " + A.getValue()/1000.0);
        bLabel = new JLabel("B: " + B.getValue()/1000.0);
        saturationLabel = new JLabel("Saturation: " + saturationBar.getValue()/1000.0);
        hueLabel = new JLabel("Hue: " + hueBar.getValue()/1000.0);
        brightnessLabel = new JLabel("Brightness: " + brightnessBar.getValue()/1000.0);
        zoomLabel = new JLabel("Zoom: " + zoomBar.getValue()/1000.0);
        maxIterationsLabel = new JLabel("Max Iterations: " + maxIterationsBar.getValue());
        //add labels to panel
        labelPanel.add(aLabel);
        labelPanel.add(bLabel);
        labelPanel.add(saturationLabel);
        labelPanel.add(hueLabel);
        labelPanel.add(brightnessLabel);
        labelPanel.add(zoomLabel);
        labelPanel.add(maxIterationsLabel);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1));
        buttonPanel.add(reset);
        buttonPanel.add(save);
        buttonPanel.add(open);
        buttonPanel.add(toggleEq);

        //big panel
        kingPanel = new JPanel();
        kingPanel.setLayout(new BorderLayout());
        kingPanel.add(labelPanel, BorderLayout.WEST);
        kingPanel.add(scrollPanel, BorderLayout.CENTER);
        kingPanel.add(buttonPanel, BorderLayout.EAST);

        frame.add(this);
        frame.add(kingPanel, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public void paintComponent (Graphics g)
    {
        super.paintComponent(g);
        //g.setColor(color);
        //g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
        if(eq == 0)
            g.drawImage(drawJulia(), 0, 0, null);
        else if(eq == 1)
            g.drawImage(drawJuliaCubic(), 0, 0, null);
        else
            g.drawImage(drawJuliaQuartic(), 0, 0, null);
    }
    /*public BufferedImage drawJulia()
    {
        int w = frame.getWidth();
        int h = frame.getHeight();
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                double zx = 1.5*(x - w/2.0)/(0.5*zoom*w);
                double zy = (y - h/2.0)/(0.5*zoom*h);
                float i = 200;
                while ((zx*zx + zy*zy < 6) && (i > 0)) //shape
                {
                    double tmp = zx*zx - zy*zy + a;
                    zy = 2.0*zx*zy + b;
                    zx = tmp;
                    i--;
                }
                int c;
                if(i>0)
                    c = Color.HSBtoRGB((300 / i) % 1, 1, 1); //outside colors
                else
                    c = Color.HSBtoRGB(0.33f, 1, 1); //eye color
                image.setRGB(x, y, c);
            }
        }
        return image;
    }*/
    //write drawJulia() method with saturation, zoom, brightness, and shape
    /*public BufferedImage drawJulia(){
        int w = frame.getWidth();
        int h = frame.getHeight();
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < w; x++)
        {
            for (int y = 0; y < h; y++)
            {
                double zx = 1.5*(x - w/2.0)/(0.5*zoom*w);
                double zy = (y - h/2.0)/(0.5*zoom*h);
                float i = 200;
                while ((zx*zx + zy*zy < 6) && (i > 0))
                {
                    double tmp = zx*zx - zy*zy + a;
                    zy = 2.0*zx*zy + b;
                    zx = tmp;
                    i--;
                }
                int c;
                if(i>0)
                    c = Color.HSBtoRGB((300 / i) % 1, (float) saturation, (float) brightness); //outside colors
                else
                    c = Color.HSBtoRGB(0.33f, (float) saturation, (float) brightness); //eye color
                image.setRGB(x, y, c);
            }
        }
        return image;
    }*/
    //write drawJulia() method with saturation, zoom, brightness, and hue
    public BufferedImage drawJulia(){
        int w = frame.getWidth();
        int h = frame.getHeight();
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        if(maxIterations != 10) {
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    double zx = 1.5 * (x - w / 2.0) / (0.5 * zoom * w);
                    double zy = (y - h / 2.0) / (0.5 * zoom * h);
                    float i = (float) maxIterations;
                    while ((zx * zx + zy * zy < 6) && (i > 0)) {
                        double tmp = zx * zx - zy * zy + a;
                        zy = 2.0 * zx * zy + b;
                        zx = tmp;
                        i--;
                    }
                    int c;
                    if (i > 0)
                        c = Color.HSBtoRGB((float) hue * i / (float) maxIterations, (float) saturation, (float) brightness); //outside colors
                    else
                        c = Color.HSBtoRGB(0.33f, (float) saturation, (float) brightness); //eye color
                    image.setRGB(x, y, c);
                }
            }
        } else{
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    double zx = 1.5 * (x - w / 2.0) / (0.5 * zoom * w);
                    double zy = (y - h / 2.0) / (0.5 * zoom * h);
                    float i = 200;
                    while ((zx * zx + zy * zy < 6) && (i > 0)) {
                        double tmp = zx * zx - zy * zy + a;
                        zy = 2.0 * zx * zy + b;
                        zx = tmp;
                        i--;
                    }
                    int c;
                    if (i > 0)
                        c = Color.HSBtoRGB((float) hue * i, (float) saturation, (float) brightness); //outside colors
                    else
                        c = Color.HSBtoRGB(0.33f, (float) saturation, (float) brightness); //eye color
                    image.setRGB(x, y, c);
                }
            }
        }
        return image;
    }

    public BufferedImage drawJuliaCubic(){
        int w = frame.getWidth();
        int h = frame.getHeight();
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        if(maxIterations != 10) {
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    double zx = 1.5 * (x - w / 2.0) / (0.5 * zoom * w);
                    double zy = (y - h / 2.0) / (0.5 * zoom * h);
                    float i = (float) maxIterations;
                    while ((zx * zx + zy * zy < 6) && (i > 0)) {
                        double tmp = zx * zx * zx - 3 * zx * zy * zy + a;
                        zy = 3 * zx * zx * zy - zy * zy * zy + b;
                        zx = tmp;
                        i--;
                    }
                    int c;
                    if (i > 0)
                        c = Color.HSBtoRGB((float) hue * i / (float) maxIterations, (float) saturation, (float) brightness); //outside colors
                    else
                        c = Color.HSBtoRGB(0.33f, (float) saturation, (float) brightness); //eye color
                    image.setRGB(x, y, c);
                }
            }
        } else{
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    double zx = 1.5 * (x - w / 2.0) / (0.5 * zoom * w);
                    double zy = (y - h / 2.0) / (0.5 * zoom * h);
                    float i = 200;
                    while ((zx * zx + zy * zy < 6) && (i > 0)) {
                        double tmp = zx * zx * zx - 3 * zx * zy * zy + a;
                        zy = 3 * zx * zx * zy - zy * zy * zy + b;
                        zx = tmp;
                        i--;
                    }
                    int c;
                    if (i > 0)
                        c = Color.HSBtoRGB((float) hue * i, (float) saturation, (float) brightness); //outside colors
                    else
                        c = Color.HSBtoRGB(0.33f, (float) saturation, (float) brightness); //eye color
                    image.setRGB(x, y, c);
                }
            }
        }
        return image;
    }
    //write drawJuliaQuartic() method with saturation, zoom, brightness, hue, and maxIterations
    public BufferedImage drawJuliaQuartic(){
        int w = frame.getWidth();
        int h = frame.getHeight();
        image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        if(maxIterations != 10) {
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    double zx = 1.5 * (x - w / 2.0) / (0.5 * zoom * w);
                    double zy = (y - h / 2.0) / (0.5 * zoom * h);
                    float i = (float) maxIterations;
                    while ((zx * zx + zy * zy < 6) && (i > 0)) {
                        double tmp = zx * zx * zx * zx - 6 * zx * zx * zy * zy + zy * zy * zy * zy + a;
                        zy = 4 * zx * zx * zx * zy - 4 * zx * zy * zy * zy + b;
                        zx = tmp;
                        i--;
                    }
                    int c;
                    if (i > 0)
                        c = Color.HSBtoRGB((float) hue * i / (float) maxIterations, (float) saturation, (float) brightness); //outside colors
                    else
                        c = Color.HSBtoRGB(0.33f, (float) saturation, (float) brightness); //eye color
                    image.setRGB(x, y, c);
                }
            }
        } else{
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    double zx = 1.5 * (x - w / 2.0) / (0.5 * zoom * w);
                    double zy = (y - h / 2.0) / (0.5 * zoom * h);
                    float i = 200;
                    while ((zx * zx + zy * zy < 6) && (i > 0)) {
                        double tmp = zx * zx * zx * zx - 6 * zx * zx * zy * zy + zy * zy * zy * zy + a;
                        zy = 4 * zx * zx * zx * zy - 4 * zx * zy * zy * zy + b;
                        zx = tmp;
                        i--;
                    }
                    int c;
                    if (i > 0)
                        c = Color.HSBtoRGB((float) hue * i, (float) saturation, (float) brightness); //outside colors
                    else
                        c = Color.HSBtoRGB(0.33f, (float) saturation, (float) brightness); //eye color
                    image.setRGB(x, y, c);
                }
            }
        }
        return image;
    }


    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        // TODO Auto-generated method stub
        if (e.getSource() == A)
        {
            a = A.getValue()/1000.0;
            aLabel.setText("A: " + a);
        }
        else if (e.getSource() == B)
        {
            b = B.getValue()/1000.0;
            bLabel.setText("B: " + b);
        }
        else if (e.getSource() == saturationBar)
        {
            saturation = saturationBar.getValue()/1000.0;
            saturationLabel.setText("Saturation: " + saturation);
        }
        else if (e.getSource() == brightnessBar)
        {
            brightness = brightnessBar.getValue()/1000.0;
            brightnessLabel.setText("Brightness: " + brightness);
        }
        else if (e.getSource() == hueBar)
        {
            hue = hueBar.getValue()/1000.0;
            hueLabel.setText("Hue: " + hue);
        }
        else if (e.getSource() == zoomBar)
        {
            zoom = zoomBar.getValue()/1000.0;
            zoomLabel.setText("Zoom: " + zoom);
        }
        else if (e.getSource() == maxIterationsBar)
        {
            maxIterations = maxIterationsBar.getValue();
            if(maxIterations == 10) {
                maxIterationsLabel.setText("Max Iterations: UNBASED");
            } else{
                maxIterationsLabel.setText("Max Iterations: " + maxIterations);
            }
        }
        repaint();
    }

    public void downloadImage() {
        if (image != null) {
            FileFilter filter = new FileNameExtensionFilter("*.png", "png");
            fc.setFileFilter(filter);
            if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                try {
                    String st = file.getAbsolutePath();
                    if (st.indexOf(".png") >= 0)
                        st = st.substring(0, st.length() - 4);
                    ImageIO.write(image, "png", new File(st + ".png"));
                } catch (IOException e) {

                }
                //save Julia values to a txt file
                try {
                    String st = file.getAbsolutePath();
                    if (st.indexOf(".png") >= 0)
                        st = st.substring(0, st.length() - 4);
                    PrintWriter pw = new PrintWriter(new File(st + ".txt"));
                    pw.println("A: " + a);
                    pw.println("B: " + b);
                    pw.println("Saturation: " + saturation);
                    pw.println("Brightness: " + brightness);
                    pw.println("Hue: " + hue);
                    pw.println("Zoom: " + zoom);
                    pw.close();
                } catch (IOException e) {

                }
            }
        }
    }
    //read image and open associated txt file and paint values
    public void openImage(){
        FileFilter filter = new FileNameExtensionFilter("*.png", "png");
        fc.setFileFilter(filter);
        if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            try {
                image = ImageIO.read(file);
                String st = file.getAbsolutePath();
                if (st.indexOf(".png") >= 0)
                    st = st.substring(0, st.length() - 4);
                else if(st.indexOf(".txt") >= 0)
                    st = st.substring(0, st.length() - 4);
                Scanner sc = new Scanner(new File(st + ".txt"));
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    if (line.indexOf("A: ") >= 0) {
                        a = Double.parseDouble(line.substring(3));
                        A.setValue((int) (a * 1000));
                        aLabel.setText("A: " + a);
                    } else if (line.indexOf("B: ") >= 0) {
                        b = Double.parseDouble(line.substring(3));
                        B.setValue((int) (b * 1000));
                        bLabel.setText("B: " + b);
                    } else if (line.indexOf("Saturation: ") >= 0) {
                        saturation = Double.parseDouble(line.substring(12));
                        saturationBar.setValue((int) (saturation * 1000));
                        saturationLabel.setText("Saturation: " + saturation);
                    } else if (line.indexOf("Brightness: ") >= 0) {
                        brightness = Double.parseDouble(line.substring(12));
                        brightnessBar.setValue((int) (brightness * 1000));
                    } else if (line.indexOf("Hue: ") >= 0) {
                        hue = Double.parseDouble(line.substring(5));
                        hueBar.setValue((int) (hue * 1000));
                        hueLabel.setText("Hue: " + hue);
                    } else if (line.indexOf("Zoom: ") >= 0) {
                        zoom = Double.parseDouble(line.substring(6));
                        zoomBar.setValue((int) (zoom * 1000));
                        zoomLabel.setText("Zoom: " + zoom);
                    }
                }
                repaint();
            } catch (IOException e) {
            }
        }
    }
    public static void main(String[]args)
    {
        JuliaSet jsp = new JuliaSet();
    }
}