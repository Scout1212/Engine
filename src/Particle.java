import java.awt.*;
import java.util.ArrayList;

public class Particle {
    public double getDiam;
    private Point position;
    private int diam;
    private Vector velocity;
    private Vector acceleration;
    private Vector fNet;
    private ArrayList<Vector> forces;
    private double mass;
    private double bounciness;
    private boolean drawForce = true;

    Particle(int diam, int mass) {
        this.diam = diam;
        this.mass = mass;
        position = new Point(200, 100);
        velocity = new Vector(0, 0);
        acceleration = new Vector(0, 0);
        fNet = new Vector(0, 0);
        forces = new ArrayList<>();
        //for now gravity is positive but to keep it right in math I want to make it negative to make going towards the earth negative
        //But I will do that later
        //todo resolve
        forces.add(new Vector(0, mass * PhysConst.GRAVITY*.1));
        bounciness = .9;
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
        fNet = Vector.sumVectors(forces);
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

    public void resolveCollision(ArrayList<LineSegment> segments){
        LineSegment ls = segments.getFirst();
        double angle = ls.getAngle();
        System.out.println(velocity.getMagnitude());
        Vector rotatedVelocity = velocity.getRotateVector(-angle);
        Vector RotatedReflectedVelocity = new Vector(rotatedVelocity.getX() * bounciness, rotatedVelocity.getY() * bounciness * -1);
        velocity = RotatedReflectedVelocity.getRotateVector(angle);
    }

    public void setPosition(Point p){
        position = p;
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
        Rectangle boundingBox = new Rectangle(position, diam, diam);
        return boundingBox.getBiggerRectangleFromCenter(diam/2);
    }

}
