import java.awt.*;
import java.util.ArrayList;

public class Line {
    //really only serves to draw the line --> maybe I can add other things
    //stuff that a rigidbody can interact with
    ArrayList<LineSegment> lineSegments;
    Color color;
    int thickness;


    public Line(ArrayList<LineSegment> lineSegments, int thickness) {
        this.lineSegments = lineSegments;
        this.thickness = thickness;

        color = Color.BLACK;
    }

    public Line(){
        this.lineSegments = new ArrayList<>();
        thickness = 3;
        color = Color.BLACK;
    }

    @Override
    public String toString() {
        return "Line with Segments: "  + lineSegments.size();
    }

    public void addSegment(LineSegment lineSegment){
        lineSegments.add(lineSegment);
    }

    public void drawSelf(Graphics2D g2d){
        g2d.setStroke(new BasicStroke(thickness));
        g2d.setColor(color);

        for(int i = 0; i < lineSegments.size() - 1; i++){
            lineSegments.get(i).drawSelf(g2d);
        }
    }

    /*
    public ArrayList<LineSegment> sortFromDistanceToPoint(ArrayList<LineSegment> lineSegments, Point p){
        ArrayList<LineSegment> output = lineSegments;
        int n = output.size();
        for (int i = 1; i < n; i++) {
            LineSegment currentLineSeg = output.get(i);
            double key = currentLineSeg.distanceToPoint(p);
            int j = i - 1;

            while (j >= 0 && lineSegments.get(i).distanceToPoint(p) > key) {
                output.set(j + 1, lineSegments.get(j));
                j = j - 1;
            }

            output.set(j + 1, currentLineSeg);
        }

        return output;
    }
     */

    public ArrayList<LineSegment> getLineSegments(){
        return lineSegments;
    }
}
