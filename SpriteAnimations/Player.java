package SpriteAnimations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

public class Player extends JPanel implements MouseListener {
    private BufferedImage[] walkingLeft = {Sprite.getSprite(0, 1), Sprite.getSprite(2, 1)}; // Gets the upper left images of my sprite sheet
    private BufferedImage[] walkingRight = {Sprite.getSprite(0, 2), Sprite.getSprite(2, 2)};
    private BufferedImage[] standing = {Sprite.getSprite(1, 0)};

    private Animation walkLeft = new Animation(walkingLeft, 10);
    private Animation walkRight = new Animation(walkingRight, 10);
    private Animation stand = new Animation(standing, 10);
    private Animation last = null;

    // This is the actual animation
    private Animation animation = stand;

    JFrame frame;

    public static void main(String[] args) {
        Player player = new Player();
    }

    public Player() {
        addMouseListener(this);

        frame = new JFrame();
        frame.setSize(100, 100);
        frame.add(this);
        frame.setVisible(true);

        start();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (walkLeft.equals(last)) {
            animation = walkRight;
        } else if (walkRight.equals(last)) {
            animation = walkLeft;
        } else {
            animation = walkLeft;
        }
        animation.start();
        System.out.println("Pressed");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        animation.stop();
        animation.reset();
        last = animation;
        animation = stand;
        animation.start();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    //run an update method for animation every 1 sec in a separate thread
    public void start() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    animation.update();
                    repaint();
                    try {
                        Thread.sleep(1000 / 60);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(animation.getSprite(), 0, 0, null);
    }
}
