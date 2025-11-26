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
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;


public class TesterSaveLoadLine extends JComponent implements KeyListener, MouseListener, MouseMotionListener {
    private int WIDTH = 800;
    private int HEIGHT = 800;

    private ArrayList<Line> lines =  new ArrayList<>();

    String fileName = null;
    public TesterSaveLoadLine() {

        //IF we have a save file we want to load it
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("." +"/LineSaves/"));
        int returnVal = fileChooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            fileName = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                InputStream inputStream = new FileInputStream(fileName);
                ObjectInputStream objectStream = new ObjectInputStream(inputStream);
                lines = (ArrayList<Line>) objectStream.readObject();
                objectStream.close();
            } catch (Exception a) {
                a.printStackTrace();
            }
        }
        //if we dont have a save file then we will just start with everything fresh: lines will stay as new ArrayList<Line>()

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

        JOptionPane.showMessageDialog(null, "Press ESC to save your current lines");
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if(fileName == null) {
                fileName = JOptionPane.showInputDialog("What would you like to name your save file?") + ".obj";
            }

            try {
                FileOutputStream fileOutputStream = new FileOutputStream("LineSaves/"+fileName);
                ObjectOutputStream objOutputStream = new ObjectOutputStream(fileOutputStream);
                objOutputStream.writeObject(lines);
                objOutputStream.close();
            }
            catch (Exception a) {
                a.printStackTrace();
            }

            System.exit(0);
        }
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


    int timer = 0;
    public void mousePressed(MouseEvent e) {
        timer =  0;
        lines.add(new Line());
    }

    public void mouseReleased(MouseEvent e) {
        endPoint = null;
        startPoint = null;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {

    }

    Point startPoint;
    Point endPoint;
    public void mouseDragged(MouseEvent e) {
        if(timer % 4 == 0) {
            //if point is not initialized do it, if it is do the other, if they are both init then make a line segment then reset point values
            if(startPoint == null){
                startPoint = new Point(e.getX()-2, e.getY() - 30);
            }
            else if(endPoint == null){
                endPoint = new Point(e.getX() - 2, e.getY() - 30);
                LineSegment currentLineSeg = new LineSegment(startPoint, endPoint);
                lines.getLast().addSegment(currentLineSeg);

                startPoint = endPoint;
                endPoint = null;
            }
        }

        timer++;
    }

    public void start(final int ticks) {
        Thread gameThread = new Thread() {
            public void run() {
                while(true) {
                    TesterSaveLoadLine.this.loop();

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
        TesterSaveLoadLine g = new TesterSaveLoadLine();
        g.start(60);
    }
}
