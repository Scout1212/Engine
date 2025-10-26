import java.awt.*;

public class LineSegment {
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

    public double findSlope(Point p1, Point p2) {
        double dx;
        double dy;

        if(p2.getX() > p1.getX()) {
            dx = p2.getX()-p1.getX();
            dy = p2.getY()-p1.getY();
        }
        else{
            dx = p1.getX()-p2.getX();
            dy = p1.getY()-p2.getY();
        }

        return dy/dx;
    }

    public double distanceToPoint(Point p1) {
        //we want to make a triangle to check if the angles are acute

        //the line segment
        double cSquare = Math.pow(distance, 2);

        //the two legs I want the thetas of (leg and segment)

        double a = p1.distance(end);
        double b = p1.distance(start);
        double c = distance;

        double aSquare = Math.pow(a, 2);
        double bSquare  = Math.pow(b, 2);
        //I know the length of all the sides so I can use law of cosines

        //this is my law of cosine formula solved for a theta
        double aTheta = Math.acos((aSquare - cSquare - bSquare)/(-2*c*b));
        double bTheta = Math.acos((bSquare - cSquare - aSquare)/(-2*c*a));

        //if either angle is obtuse that means p1 is not between the points of the line segment and so the distance
        //to the line segment is just a simple p1 to the side thats obtuse or right --> just draw it out
        if(Math.toDegrees(aTheta) >= 90){
            return b;
        }
        else if(Math.toDegrees(bTheta) >= 90){
            return a;
        }
        else{
            //at this point we know that p1 is between start and end point so we can just drop an altitude
            //then solve for the height which is the distance to the segment
            return Math.sin(aTheta) * b;
        }
    }

    public void drawSelf(Graphics2D g2d) {
        g2d.drawLine((int)start.getX(), (int)start.getY(), (int)end.getX(), (int)end.getY());
    }

    public double getPerpendicular(){
        return Math.pow(slope, -1) * -1;
    }

    public double getSlope(){
        return slope;
    }

}
