import java.util.ArrayList;

public class Vector {

    private Point resultant;
    private double magnitude;
    public double angle;
    //everytime the resultant is changed the magnitude and angle need to be recalculated
    //aka every base operation needs to call recalc vector since it will move the point() --> add subtract
    //the magnitude and angle should never be altered themselves (for the most part)

    //todo fix the things with the angles --> I want to turn them all back to degrees

    Vector(double x, double y) {
        resultant = new Point(x, y);
        magnitude = findHyp(x, y);
        angle = Math.toDegrees(Math.atan2(y, x));
    }

    Vector(double length, double angle, String a) {
        magnitude = length;
        resultant = new Point(magnitude * Math.cos(Math.toRadians(angle)), magnitude * Math.sin(Math.toRadians(angle)));
        this.angle = angle;
    }

    @Override
    public String toString() {
        return "Vector: x = " + resultant.getX() + ", y = " + resultant.getY();
    }

    public void add(Vector v) {
        resultant.translate(v.resultant.getX(), v.resultant.getY());
        recalculateVector();
    }

    public void sub(Vector v) {
       resultant.translate(-v.resultant.getX(), -v.resultant.getY());
       recalculateVector();
    }

    public void recalculateVector(){
        magnitude = findHyp(resultant.getX(), resultant.getY());
        angle = Math.toDegrees(Math.atan2(resultant.getY(), resultant.getX()));
    }

    public double getX() {
        return resultant.getX();
    }

    public double getY() {
        return resultant.getY();
    }

    /***
     * @return the angle of above the horizonal in degrees
     */
    public double getAngle(){
        return Math.toDegrees(Math.atan2(resultant.getY(), resultant.getX()));
    }

    public double getMagnitude(){
        return magnitude;
    }

    public static double findHyp(double x, double y){
        return Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
    }

    public  VisualVector getVisualVector(){
        return new VisualVector(this);
    }

    public Vector getRotateVector(double angle){
        return new Vector(magnitude, getAngle() + angle, "");
    }


    public static Vector sumVectors(ArrayList<Vector> vectors){
        Vector output = new Vector(0,0);
        for(Vector v : vectors){
            output.add(v);
        }

        return output;
    }

    public double getSlope() {
        return resultant.getY() / resultant.getX();
    }
}
