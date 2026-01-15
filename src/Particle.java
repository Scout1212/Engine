import java.awt.*;
import java.util.ArrayList;

public class Particle {

    //convert "forces" into force class
    private Point position;
    private int diam;
    private Vector velocity;
    private Vector acceleration;
    private ArrayList<Force> forces;
    private double mass;
    private double bounciness;
    private boolean drawForce = true;

    Particle(int diam, int mass) {
        this.diam = diam;
        this.mass = mass;
        position = new Point(200, 100);
        velocity = new Vector(0, 0);
        acceleration = new Vector(0, 0);
        forces = new ArrayList<>();
        //for now gravity is positive but to keep it right in math I want to make it negative to make going towards the earth negative
        //But I will do that later
        //todo resolve
        forces.add(new Force(new Vector(0, mass * PhysConst.GRAVITY*.1), "Gravity"));
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


        for(Force f : forces){
            VisualVector visualVector = f.getVector().getVisualVector();
            g2d.drawLine((int)cX, (int)cY, (int)(cX + visualVector.getX()), (int)(cY + visualVector.getY()));
        }

        g2d.setColor(Color.blue);

        Force fNet = Force.sumForces(forces, "Net");
        VisualVector visualVector = fNet.getVector().getVisualVector();
        g2d.drawLine((int)cX, (int)cY, (int)(cX + visualVector.getX()), (int)(cY + visualVector.getY()));
    }

    public void update(){
        findAcceleration();
        velocity.add(acceleration);

        position.translate(velocity.getX(), velocity.getY());
    }


    public double getCenterX(){
        return position.getX() + diam/2.0;
    }
    public double getCenterY(){
        return position.getY() + diam/2.0;
    }

    private void findAcceleration(){
        Force fNet = Force.sumForces(forces, "Net");

        //a = f/m
        acceleration = Force.findAcceleration(fNet, mass);
    }

    public void resolveCollision(ArrayList<LineSegment> segments){
        //resolveOverlap(segments.getFirst());

        //todo do slide acceleration --> when velocity while touching the ramp is zero or parallel to the ramp
        LineSegment ls = segments.getFirst();
        double lineSegAng1 = ls.getAngle();
        double lineSegAng2 = lineSegAng1 + 180;

        double velAngle = velocity.getAngle();


        //boolean parallel = Math.abs(lineSegAng1 - lineSegAng2) < 5 || Math.abs(lineSegAng1 - velAngle) < 5;
        //if(!parallel) {

            Vector rotatedVelocity = velocity.getRotateVector(-lineSegAng1);
            Vector RotatedReflectedVelocity = new Vector(rotatedVelocity.getX() * bounciness, rotatedVelocity.getY() * bounciness * -1);
            velocity = RotatedReflectedVelocity.getRotateVector(lineSegAng1);
        //}
        //else if(Math.sin(velocity.getAngle()) < 0) {
            //forces.add(new Force(new Vector(0, -mass * PhysConst.GRAVITY * Math.cos(Math.toRadians(lineSegAng1))), "Gravity Perpendicular"));
            //forces.add(new Force(new Vector(0, mass * PhysConst.GRAVITY * Math.sin(Math.toRadians(lineSegAng1))), "Gravity Parallel"));
        //}
    }

    public void resolveOverlap(LineSegment ls){
        double distance = ls.getDistanceToPoint(getCenter());
        double perpendicularAngle = ls.getPerpendicular();

        Vector moveVector = new Vector(distance, perpendicularAngle, "");

        position.translate(moveVector.getX(), moveVector.getY());
    }

    public void setPosition(Point p){
        position = p;
    }

    public void addForce(Force f){
        forces.add(f);
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

    public void printForces(){
        for(Force f : forces){
            System.out.print(f.getName() + " ");
        }
        System.out.println();
    }

}
