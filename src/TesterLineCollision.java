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

public class TesterLineCollision extends JComponent implements KeyListener, MouseListener, MouseMotionListener {
    private int WIDTH = 800;
    private int HEIGHT = 800;

    private ArrayList<Line> lines;
    private ArrayList<CollisionLine> collisionLines;

    private RigidBody rigidBody;

    public TesterLineCollision() {
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
        lines = new ArrayList<>();
        collisionLines = new ArrayList<>();
        rigidBody = new RigidBody(30, 10);
    }

    public void keyPressed(KeyEvent e) {

    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        rigidBody.drawSelf(g2d);

        if(lines != null) {
            for (Line l : lines) {
                l.drawSelf(g2d);
            }
        }

        //System.out.println("Collision Lines: " + collisionLines);
        if(collisionLines != null) {
            for (CollisionLine cl : collisionLines) {
                cl.drawSelf(g2d);
            }
        }
    }

    public void loop() {
        rigidBody.update();

        for(CollisionLine cl: collisionLines){
            cl.isCollidingRigidBody(rigidBody);
        }
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
        collisionLines.add(lines.getLast().getCollisionLine());

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
                    TesterLineCollision.this.loop();

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
        TesterLineCollision g = new TesterLineCollision();
        g.start(60);
    }
}
