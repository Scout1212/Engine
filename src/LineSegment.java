import java.awt.*;
import java.io.Serializable;

public class LineSegment implements Serializable{
    private Point start;
    private Point end;
    private double slope;
    private double distance;

    public LineSegment(Point start, Point end) {
        this.start = start;
        this.end = end;
        slope = findSlope(start, end);
        distance = start.distance(end);
    }

    public static double findSlope(Point p1, Point p2) {
        return ((p1.getX()-p2.getX())*10)/((p1.getY() - p2.getY())*10);
    }

    //I decided to make this double since I need it to be that way for my get priority queue method to work
    //It also maybe make sense since this is a just a formula only doing calculations
    public double distanceToPoint(Point p1) {
        //we want to make a triangle to check if the angles are acute

        //the line segment
        double cSquare = Math.pow(getDistance(), 2);

        //the two legs I want the thetas of (leg and segment)

        double a = p1.distance(getEnd());
        double b = p1.distance(getStart());
        double c = getDistance();

        double aSquare = Math.pow(a, 2);
        double bSquare  = Math.pow(b, 2);
        //I know the length of all the sides so I can use law of cosines

        //this is my law of cosine formula solved for a theta
        double aTheta = Math.acos((aSquare - cSquare - bSquare)/(-2*c*b));
        double bTheta = Math.acos((bSquare - cSquare - aSquare)/(-2*c*a));

        //if either angle is obtuse that means p1 is not between the points of the line segment and so the distance
        //to the line segment is just a simple p1 to the side thats obtuse or right --> just draw it out
        if(aTheta >= Math.PI/2){
            return b;
        }
        else if(bTheta >= Math.PI/2){
            return a;
        }
        else{
            //at this point we know that p1 is between start and end point so we can just drop an altitude
            //then solve for the height which is the distance to the segment
            return Math.sin(aTheta) * b;
        }
    }

    public boolean isCollidingRigidBody(Particle r) {
       return distanceToPoint(r.getCenter()) < r.getDiam()/2.0;
    }

    /**
     * @return the acute angle between the two lines, quadrant ignored
     */
    public double getAcuteAngleBetweenLines(LineSegment l1){
        double m1 = l1.getSlope();
        double m2 = this.getSlope();

        //should I turn this into math.atan2 probably not because I think this will probably only be used relatively and I wont need an absolute value
        //for the quadrant
        return Math.atan(Math.abs((m1-m2)/(1+m1*m2)));
    }


    public void drawSelf(Graphics2D g2d) {
        g2d.drawLine((int)start.getX(), (int)start.getY(), (int)end.getX(), (int)end.getY());
    }

    public double getAngle(){
        return Math.atan2(end.getY() - start.getY(), end.getX() - start.getX());
    }

    public double getNormalAngle(){
        return (getAngle() + Math.PI/2) % (2*Math.PI);
    }

    public double getPerpendicular(){
        return Math.pow(slope, -1) * -1;
    }

    public double getSlope(){
        return slope;
    }

    public double getDistance(){
        return distance;
    }

    public Point getStart(){
        return start;
    }
    public Point getEnd(){
        return end;
    }
    public Point[] getPoints(){
        return new Point[]{start, end};
    }

}
