//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class TesterLine extends JComponent implements KeyListener, MouseListener, MouseMotionListener {
    private int WIDTH = 800;
    private int HEIGHT = 800;

    private ArrayList<Line> lines = new ArrayList<Line>();

    public TesterLine() {
        JFrame gui = new JFrame();
        gui.setDefaultCloseOperation(3);
        gui.setTitle("TesterLine");
        gui.setPreferredSize(new Dimension(this.WIDTH + 5, this.HEIGHT + 30));
        gui.setResizable(false);
        gui.getContentPane().add(this);
        gui.pack();
        gui.setLocationRelativeTo((Component)null);
        gui.setVisible(true);
        gui.addKeyListener(this);
        gui.addMouseListener(this);
        gui.addMouseMotionListener(this);
    }

    public void keyPressed(KeyEvent e) {

    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for(Line l : lines) {
            l.drawSelf(g2d);
        }

    }

    public void loop() {

        this.repaint();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {

    }

    int timer =  0;
    public void mousePressed(MouseEvent e) {
        lines.add(new Line());
        timer = 0;
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

        if(timer % 7 == 0) {
            Line currentLine = lines.getLast();
            Point tempPoint = new Point(e.getX() - 2, e.getY()-30);
            currentLine.addPoint(tempPoint);
        }

        timer++;
    }

    public void start(final int ticks) {
        Thread gameThread = new Thread() {
            public void run() {
                while(true) {
                    TesterLine.this.loop();

                    try {
                        Thread.sleep((long)(1000 / ticks));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        gameThread.start();
    }

    public static void main(String[] args) {
        TesterLine g = new TesterLine();
        g.start(60);
    }
}
