//point class is just a point its a basic building block
public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return  "Point: (" + x + ", " + y + ")";
    }

    public double distance(Point p){
        return Math.sqrt(Math.pow(x - p.getX(),2) + Math.pow(y - p.getY(),2));
    }

    public void translate(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Point getPointInDirection(double length, double angle){
        return new Point(x + length * Math.cos(angle),y + length * Math.sin(angle));
    }
}
