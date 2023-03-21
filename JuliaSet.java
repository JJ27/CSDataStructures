import javax.swing.*;
import javax.swing.plaf.DimensionUIResource;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class JuliaSet extends JPanel implements AdjustmentListener
{
    JFrame frame;
    JScrollBar A, B, saturationBar, brightnessBar, zoomBar;
    JLabel aLabel, bLabel, saturationLabel, brightnessLabel, zoomLabel;
    double a, b;
    JPanel scrollPanel, labelPanel, kingPanel;
    BufferedImage image;
    double zoom = 1;
    double saturation = 1;
    double brightness = 1;
    double shape = 1;
    public JuliaSet()
    {
        //Main JFrame
        frame = new JFrame("Julia Set Program");
        frame.add(this);
        frame.setSize(1000, 1000);

        //panel for scroll bars
        scrollPanel = new JPanel();
        scrollPanel.setLayout(new GridLayout(2, 1)); //3 rows for 3 scrollbars
        //scrollbars for each color
        A = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, -2000, 2000);
        A.addAdjustmentListener(this);
        B = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, -2000, 2000);
        B.addAdjustmentListener(this);
        saturationBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, -2000, 2000);
        saturationBar.addAdjustmentListener(this);
        brightnessBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, -2000, 2000);
        brightnessBar.addAdjustmentListener(this);
        zoomBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, -2000, 2000);
        zoomBar.addAdjustmentListener(this);
        //add scrollbars to panel
        scrollPanel.add(A);
        scrollPanel.add(B);
        scrollPanel.add(saturationBar);
        scrollPanel.add(brightnessBar);
        scrollPanel.add(zoomBar);
        //panel for labels
        labelPanel = new JPanel();
        labelPanel.setPreferredSize(new Dimension(100, 50));
        labelPanel.setLayout(new GridLayout(6, 1)); //3 rows for 3 labels
        //labels for each value
        aLabel = new JLabel("A: " + A.getValue()/1000.0);
        bLabel = new JLabel("B: " + B.getValue()/1000.0);
        saturationLabel = new JLabel("Saturation: " + saturationBar.getValue()/1000.0);
        brightnessLabel = new JLabel("Brightness: " + brightnessBar.getValue()/1000.0);
        zoomLabel = new JLabel("Zoom: " + zoomBar.getValue()/1000.0);
        //add labels to panel
        labelPanel.add(aLabel);
        labelPanel.add(bLabel);
        labelPanel.add(saturationLabel);
        labelPanel.add(brightnessLabel);
        labelPanel.add(zoomLabel);

        //big panel
        kingPanel = new JPanel();
        kingPanel.setLayout(new BorderLayout());
        kingPanel.add(labelPanel, BorderLayout.WEST);
        kingPanel.add(scrollPanel, BorderLayout.CENTER);

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
        g.drawImage(drawJulia(), 0, 0, null);
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
    public BufferedImage drawJulia(){
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
        else if (e.getSource() == zoomBar)
        {
            zoom = zoomBar.getValue()/1000.0;
            zoomLabel.setText("Zoom: " + zoom);
        }
        repaint();
    }
    public static void main(String[]args)
    {
        JuliaSet jsp = new JuliaSet();
    }
}