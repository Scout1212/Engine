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

public class TesterCollisionResolution extends JComponent implements KeyListener, MouseListener, MouseMotionListener {
    private int WIDTH = 800;
    private int HEIGHT = 800;

    private Particle particle;
    private ArrayList<Line> lines;
    private ArrayList<CollisionLine> collisionLines;
    public TesterCollisionResolution() {
        JFrame gui = new JFrame();
        gui.setDefaultCloseOperation(3);
        gui.setTitle("TesterCollisionResolution");
        gui.setPreferredSize(new Dimension(this.WIDTH + 5, this.HEIGHT + 30));
        gui.setResizable(false);
        gui.getContentPane().add(this);
        gui.pack();
        gui.setLocationRelativeTo((Component)null);
        gui.setVisible(true);
        gui.addKeyListener(this);
        gui.addMouseListener(this);
        gui.addMouseMotionListener(this);
        particle = new Particle(30, 10);

        //I should be able to make sure that the user cant load a file that is not an ArrayList<Line>
        collisionLines = new ArrayList<>();
        lines = (ArrayList<Line>)SaveManager.load();

        if(lines == null) {
            lines = new ArrayList<>();
        }

        for(int i = 0; i < lines.size(); i++){
            collisionLines.add(lines.get(i).getCollisionLine());
        }



    }

    public void keyPressed(KeyEvent e) {

    }

    //next problem find out the angle that it hit the thing at

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        particle.drawSelf(g2d);
        for(Line line: lines){
            line.drawSelf(g2d);
        }

        for(CollisionLine cl : collisionLines){
            cl.drawSelf(g2d);
        }

        g2d.setColor(Color.RED);


    }

    public void loop() {
        particle.update();

        for(CollisionLine cl: collisionLines){
            ArrayList<LineSegment> collidedLineSegments = cl.getCollidedLineSegments(particle);
            if(!collidedLineSegments.isEmpty()) {
                particle.resolveCollision(collidedLineSegments);
            }
        }


        this.repaint();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {

    }

    public void mousePressed(MouseEvent e) {
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
    }

    public void start(final int ticks) {
        Thread gameThread = new Thread() {
            public void run() {
                while(true) {
                    TesterCollisionResolution.this.loop();

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
        TesterCollisionResolution g = new TesterCollisionResolution();
        g.start(60);
    }
}
