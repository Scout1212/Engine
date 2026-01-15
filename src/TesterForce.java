import java.awt.*;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class TesterForce extends JComponent implements KeyListener, MouseListener, MouseMotionListener {
    private int WIDTH = 800;
    private int HEIGHT = 800;

    private Particle particle;

   private Point[] line;

    public TesterForce() {

        JFrame gui = new JFrame();
        gui.setDefaultCloseOperation(3);
        gui.setTitle("TesterForce");
        gui.setPreferredSize(new Dimension(this.WIDTH + 5, this.HEIGHT + 30));
        gui.setResizable(false);
        gui.getContentPane().add(this);
        gui.pack();
        gui.setLocationRelativeTo((Component)null);
        gui.setVisible(true);
        gui.addKeyListener(this);
        gui.addMouseListener(this);
        gui.addMouseMotionListener(this);

        particle = new Particle(20, 500);

        //figure out collision
    }

    public void keyPressed(KeyEvent e) {

    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        particle.drawSelf(g2d);

        if(line != null) {
            g2d.drawLine((int)line[0].getX(), (int)line[0].getY(), (int)line[1].getX(), (int)line[1].getY());
        }
    }

    public void loop() {
        particle.update();

        particle.printForces();

        this.repaint();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        line = new Point[]{new Point(e.getX() - 2, e.getY() - 30), new Point(e.getX() - 2, e.getY() - 30)};
    }

    int counter = 1;
    public void mouseReleased(MouseEvent e) {
        //todo figure out where to inverse to account for inverted y axis in screen class --> why is it already accounted for and working?
        Point p1 = line[0];
        Point p2 = new Point(e.getX() - 2, e.getY() - 30);
        //calculating a line to apply force to the object


        VisualVector mouseForceVector = new VisualVector((int)(p2.getX() - p1.getX()), (int)(p2.getY() - p1.getY()));
        particle.addForce(new Force(mouseForceVector.getVector(), "Mouse Force " + counter));
        counter++;
        line = null;
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
        line [1] = new Point(e.getX() - 2, e.getY() - 30);
    }

    public void start(final int ticks) {
        Thread gameThread = new Thread() {
            public void run() {
                while(true) {
                    TesterForce.this.loop();

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
        TesterForce g = new TesterForce();
        g.start(60);
    }
}
