import java.awt.*;
import java.util.ArrayList;

public class Line {
    //stuff that a rigidbody can interact with
    ArrayList<Point> points;
    int thickness;


    public Line(ArrayList<Point> points) {
        this.points = points;
        thickness = 2;
    }

    public Line(){
        this.points = new ArrayList<>();
        thickness = 2;
    }

    public void addPoint(Point point){
        this.points.add(point);
    }

    public void drawSelf(Graphics2D graphics2D){
        graphics2D.setStroke(new BasicStroke(thickness));

        for(int i = 0; i < points.size() - 1; i++){
            Point p1 = points.get(i);
            Point p2 = points.get(i + 1);

            graphics2D.drawLine((int)p1.getX(), (int)p1.getY(), (int)p2.getX(), (int)p2.getY());
        }
    }
}
