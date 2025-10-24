public class Vector {

    private Point resultant;

    private int angle;
    private double magnitude;

    Vector(double x, double y) {
        resultant = new Point(x, y);
        magnitude = findHyp(x, y);
        angle = (int)Math.atan2(y, x);
    }

    /*
    comment this out for now because its getting annoying differentiating between the two constructors
    Vector(double length, int angle){
        resultant = new double[]{length * Math.sin(Math.toRadians(angle)), length * Math.cos(Math.toRadians(angle))};
        magnitude = length;
        this.angle = angle;
    */

    @Override
    public String toString() {
        return "Vector: x = " + resultant.getX() + ", y = " + resultant.getY();
    }

    public void add(Vector v) {
        resultant.translate(v.resultant.getX(), v.resultant.getY());
    }

    public void sub(Vector v) {
       resultant.translate(-v.resultant.getX(), -v.resultant.getY());
    }

    public double getX() {
        return resultant.getX();
    }

    public double getY() {
        return resultant.getY();
    }

    public static double findHyp(double x, double y){
        return Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
    }

    public  VisualVector getVisualVector(){
        return new VisualVector(this);
    }
}
