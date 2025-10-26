import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class TesterCollisionLineSegment extends JComponent implements KeyListener, MouseListener, MouseMotionListener {

    //todo ask suriel how do I declutter this where should I handle certain things and where/how
    //I want to avoid the million lines of code that was in my last driver for intro (show it)
    //It doesnt make sense to do collision and manage a lot of things in driver --> I think it should just be set up and go
    //like initialize a couple of variables maybe call some methods but thats it


    private int WIDTH = 800;
    private int HEIGHT = 800;

    private RigidBody rigidBody;

    private ArrayList<Line> lines;
    private LineSegment lineSegment;

    public TesterCollisionLineSegment() {

        JFrame gui = new JFrame();
        gui.setDefaultCloseOperation(3);
        gui.setTitle("TesterRigidBodyLines");
        gui.setPreferredSize(new Dimension(this.WIDTH + 5, this.HEIGHT + 30));
        gui.setResizable(false);
        gui.getContentPane().add(this);
        gui.pack();
        gui.setLocationRelativeTo((Component)null);
        gui.setVisible(true);
        gui.addKeyListener(this);
        gui.addMouseListener(this);
        gui.addMouseMotionListener(this);

        rigidBody = new RigidBody(50, 500);
        lines = new ArrayList<>();
        lineSegment = new LineSegment(new Point(50,200), new Point(200,100));
    }

    public void keyPressed(KeyEvent e) {

    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        rigidBody.drawSelf(g2d);

        for(Line l : lines){
            l.drawSelf(g2d);
        }

        if(rigidBody.isCollidingWithLine(lineSegment)){
            System.out.println("hello");
        }

        lineSegment.drawSelf(g2d);
    }

    public void loop() {
        rigidBody.update();

        //fix the collision after

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
        startPoint = null;
        endPoint = null;
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
        if(timer % 15 == 0) {
            //if point is not initialized do it, if it is do the other, if they are both init then make a line segment then reset point values
            //this is like a loop if first initalize first point then loop second if statement
            if(startPoint == null){
                startPoint = new Point(e.getX() - 2, e.getY() - 30);
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
                    TesterCollisionLineSegment.this.loop();

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
        TesterCollisionLineSegment g = new TesterCollisionLineSegment();
        g.start(60);
    }
}
