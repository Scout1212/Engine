import java.awt.*;
import java.security.PublicKey;

public class Rectangle {
    private Point centerPoint;
    private Point topRight, topLeft, bottomLeft, bottomRight;
    private double length;
    private int width;
    private int height;

    public Rectangle(Point p1, int width, int height){
        this.topLeft = p1;
        this.topRight = new Point(p1.getX() + width, p1.getY());
        this.bottomLeft = new Point(p1.getX(), p1.getY() + height);
        this.bottomRight = new Point(p1.getX() + width, p1.getY() + height);
        this.centerPoint = new Point(p1.getX() + width/2.0, p1.getY() + height/2.0);
        this.width = width;
        this.height = height;
    }

    public Rectangle(Point topLeft, Point topRight, Point bottomLeft, Point bottomRight){
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;


        this.width = (int)(topRight.getX() - topLeft.getX());
        this.height = (int)(bottomLeft.getY() - topLeft.getY());
        centerPoint = getCenterPoint();
    }

    @Override
    public String toString() {
        return "Rectangle with Points: " + topLeft + " " + topRight + " " + bottomLeft + " " + bottomRight;
    }

    public void drawSelf(Graphics2D g2d, boolean fill){
        if(fill) {
            g2d.fillRect((int) topLeft.getX(), (int) topLeft.getY(), width, height);
        }
        else {
            LineSegment top =  new LineSegment(topLeft, topRight);
            LineSegment bottom = new LineSegment(bottomLeft, bottomRight);
            LineSegment left = new LineSegment(topLeft, bottomLeft);
            LineSegment right = new LineSegment(topRight, bottomRight);

            top.drawSelf(g2d);
            bottom.drawSelf(g2d);
            left.drawSelf(g2d);
            right.drawSelf(g2d);
        }
    }

    public boolean isOverlapping(Rectangle r){
        Point rc = r.getCenterPoint();

        double dx = Math.abs(centerPoint.getX() - rc.getX());
        double dy = Math.abs(centerPoint.getY() - rc.getY());

        return dx < (width + r.getWidth()) / 2 &&
                dy < (height + r.getHeight()) / 2;
    }
    public Rectangle getBiggerRectangleFromCenter(int inc){
        Point newTopLeft = new Point(topLeft.getX() - inc, topLeft.getY() - inc);
        Point newTopRight = new Point(topRight.getX() + inc, topRight.getY() - inc);
        Point newBottomLeft = new Point(bottomLeft.getX() - inc, bottomLeft.getY() + inc);
        Point newBottomRight = new Point(bottomRight.getX() + inc, bottomRight.getY() + inc);

        return new Rectangle(newTopLeft, newTopRight, newBottomLeft, newBottomRight);
    }


    public Point getCenterPoint(){
        return new Point(topLeft.getX() + width/2.0, topLeft.getY() + height/2.0);
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }

    public Point getTopLeft(){
        return topLeft;
    }
    public Point getTopRight(){
        return topRight;
    }
    public Point getBottomLeft(){
        return bottomLeft;
    }
    public Point getBottomRight(){
        return bottomRight;
    }


}
