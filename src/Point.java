public class Point {
    double x;
    double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
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
}
