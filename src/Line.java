import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

//line holds an arrayList of line segments
public class Line implements Serializable {
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

        for (LineSegment lineSegment : lineSegments) {
            lineSegment.drawSelf(g2d);
        }
    }

    public ArrayList<LineSegment> getLineSegments(){
        return lineSegments;
    }

    public void addLineSegment(LineSegment lineSegment){
        lineSegments.add(lineSegment);
    }



    public CollisionLine getCollisionLine(){
        return new CollisionLine(this);
    }

    //how am I going to handle this with the priority queue
    //where am I going to store each Priority queue for each match of ball and line

    /**
     *Last level of collision checking, checks collision between all of its lineSegments and particle
     * @return Collided LineSegment, if no collision then null
     */
    public ArrayList<LineSegment> getCollidedLineSegments(Particle r1){
        ArrayList<LineSegment> collidedSegments = new ArrayList<>();
        for(LineSegment lineSeg : lineSegments){
            if(lineSeg.isCollidingRigidBody(r1)) {
                //System.out.println("collided");
                collidedSegments.add(lineSeg);
            }
        }

        return collidedSegments;
    }
}
