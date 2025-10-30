import java.awt.*;
import java.util.ArrayList;

public class RigidBody {
    public double getDiam;
    //i could turn x and y into a point from point class but eh
    private Point position;

    private int diam;

    private Vector velocity;
    private Vector acceleration;
    private Vector fNet;

    private ArrayList<Vector> forces;
    private double mass;

    private boolean drawForce = true;
    RigidBody(int diam, int mass) {
        this.diam = diam;
        this.mass = mass;
        position = new Point(180, 0);

        velocity = new Vector(0, 0);
        acceleration = new Vector(0, 0);

        fNet = new Vector(0, 0);
        forces = new ArrayList<>();

        //I guess it's a little redundant to have the gravitational constant (an acceleration to turn it into a force then back into an acceleration)
        //its needed for normal and other force

        forces.add(new Vector(0, mass * PhysConst.GRAVITY*.1));
        //forces.add(new Vector(10, 0));

    }

    public String toString() {
        return  "RigidBody: " + position.getX() + ", " + position.getY();
    }

    public void drawSelf(Graphics2D g2d){
        g2d.setColor(Color.black);
        g2d.fillOval((int) position.getX(), (int) position.getY(), diam, diam);

        if(drawForce){
            drawForces(g2d);
        }

        Rectangle bound = getBoundingBox();
        bound.drawSelf(g2d, false);
    }

    private void drawForces(Graphics2D g2d){
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.RED);
        double cX = getCenterX();
        double cY = getCenterY();


        for(Vector f : forces){
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

        position.translate(velocity.getX(), velocity.getY());
    }

    private void findFNet(){
        Vector sumForce = new Vector(0, 0);
        for(Vector v : forces){
            sumForce.add(v);
        }

        fNet = sumForce;
    }

    public double getCenterX(){
        return position.getX() + diam/2.0;
    }
    public double getCenterY(){
        return position.getY() + diam/2.0;
    }

    private void findAcceleration(){
        findFNet();
        //a = f/m
        acceleration = new Vector(fNet.getX()/mass, fNet.getY()/mass);
    }

    public Point getNextPosition(){
        Point nextPosition = position;
        nextPosition.translate(velocity.getX(), velocity.getY());
        return nextPosition;
    }

    public void addForce(Vector v){
        forces.add(v);
    }

    private void removeForce(Vector v){
        forces.remove(v);
    }

    public double getRadius(){
        return diam/2.0;
    }

    public Point getPosition(){
        return position;
    }

    public Point getCenter(){
        return new Point(getCenterX(), getCenterY());
    }

    public int getDiam(){
        return diam;
    }

    public Rectangle getBoundingBox(){
        return new Rectangle(new Point(position.getX() - diam/2.0, position.getY() - diam/2.0), diam * 2, diam *2);
    }

}
