import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JFrame;

public class TesterRigidBodyLines extends JComponent implements KeyListener, MouseListener, MouseMotionListener {

    //todo ask suriel how do I declutter this where should I handle certain things and where/how
    //I want to avoid the million lines of code that was in my last driver for intro (show it)
    //It doesnt make sense to do collision and manage a lot of things in driver --> I think it should just be set up and go
    //like initialize a couple of variables maybe call some methods but thats it


    private int WIDTH = 800;
    private int HEIGHT = 800;

    private RigidBody rigidBody;

    private ArrayList<Line> lines = new ArrayList<Line>();

    public TesterRigidBodyLines() {

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

        rigidBody = new RigidBody(20, 500);

        //figure out collision
    }

    public void keyPressed(KeyEvent e) {

    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        rigidBody.drawSelf(g2d);

        g2d.setColor(Color.BLACK);
        for(Line l : lines) {
            l.drawSelf(g2d);
        }
    }

    public void loop() {
        rigidBody.update();

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
                    TesterRigidBodyLines.this.loop();

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
        TesterRigidBodyLines g = new TesterRigidBodyLines();
        g.start(60);
    }
}
