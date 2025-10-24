import java.awt.*;
import java.util.ArrayList;

public class RigidBody {
    //i could turn x and y into a point from point class but eh
    private double x;
    private double y;

    private int radius;

    private Vector velocity;
    private Vector acceleration;
    private Vector fNet;

    private ArrayList<Vector> forces;
    private double mass;

    private boolean drawForce = true;
    RigidBody(int radius, int mass) {
        this.radius = radius;
        this.mass = mass;
        x = 100;
        y = 0;

        velocity = new Vector(0, 0);
        acceleration = new Vector(0, 0);

        fNet = new Vector(0, 0);
        forces = new ArrayList<>();

        //I guess it's a little redundant to have the gravitational constant (an acceleration to turn it into a force then back into an acceleration)
        //its needed for normal and other force

        forces.add(new Vector(0, mass * PhysConst.GRAVITY*.1));
        //forces.add(new Vector(10, 0));

    }

    public void drawSelf(Graphics2D g2d){
        g2d.setColor(Color.black);
        g2d.fillOval((int) x, (int) y, radius, radius);
        g2d.drawOval((int)x, (int)y, radius, radius);

        if(drawForce){
            drawForces(g2d);
        }
    }

    private void drawForces(Graphics2D g2d){
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.RED);
        double cX = x + radius/2.0;
        double cY = y + radius/2.0;


        for(Vector f : forces){
            System.out.println(f);
            VisualVector visualVector = f.getVisualVector();
            g2d.drawLine((int)cX, (int)cY, (int)(cX + visualVector.getX()), (int)(cY + visualVector.getY()));
        }

        g2d.setColor(Color.blue);

        VisualVector visualVector = fNet.getVisualVector();
        g2d.drawLine((int)cX, (int)cY, (int)(cX + visualVector.getX()), (int)(cY + visualVector.getY()));
    }

    public void update(){
        findAcceleration();
        velocity.add(acceleration);

        x += velocity.getX();
        y += velocity.getY();
    }

    private void findFNet(){
        Vector sumForce = new Vector(0, 0);
        for(Vector v : forces){
            sumForce.add(v);
        }

        fNet = sumForce;
    }

    public double getCenterX(){
        return x + radius/2.0;
    }
    public double getCenterY(){
        return y + radius/2.0;
    }

    private void findAcceleration(){
        findFNet();
        //a = f/m
        acceleration = new Vector(fNet.getX()/mass, fNet.getY()/mass);
    }

    public void addForce(Vector v){
        forces.add(v);
    }

    private void removeForce(Vector v){
        forces.remove(v);
    }

}
