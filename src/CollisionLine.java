import java.awt.*;
import java.util.ArrayList;
import java.util.List;

//holds the CollisionSegments
public class CollisionLine extends Line{

    private Line parentLine;
    private ArrayList<CollisionSegment> collisionSegments;
    private Rectangle collisionRectangle;

    private CollisionLine(Point p1, Point p2){

    }

    public CollisionLine(Line ParentLine) {
        this.parentLine = ParentLine;
        collisionSegments = new ArrayList<>();
        collisionSegments = generateCollisionLines(parentLine);
        collisionRectangle = generateBoundingBox();
        System.out.println(collisionRectangle);
    }

    @Override
    public void drawSelf(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        for(CollisionSegment collisionSegment : collisionSegments){
            collisionSegment.drawSelf(g2d);
        }

        g2d.setColor(Color.blue);
        collisionRectangle.drawSelf(g2d, false);
    }

    @Override
    public String toString() {
        return "CollisionLine with Segments: "  + collisionSegments.size() + " Holds " + parentLine.toString();
    }

    /**
     * I want to break free
     * @param line
     * @return ArrayList
     */
    public static ArrayList<CollisionSegment> generateCollisionLines(Line line) {
        ArrayList<LineSegment> lineSegments = line.getLineSegments();
        ArrayList<CollisionSegment> collisionSegments = new ArrayList<>();

        //my goal is to check the slopes of each segment and whichever segment is different

        //find threshold later
        int thresholdAngle = 25;

        for(int i = 0; i < lineSegments.size() - 1; i++){
            //the current LineSegment for comparison
            LineSegment currLineSeg = lineSegments.get(i);
            Point start = currLineSeg.getStart();

            //if you take slope and do atan you can get theta I think
            double currSlopeAngle = Math.toDegrees(Math.atan(currLineSeg.getSlope()));

            //second LineSegment for comparison
            int j = i + 1;
            LineSegment compareLineSeg = lineSegments.get(j);
            double compareSlopeAngle = Math.toDegrees(Math.atan(currLineSeg.getSlope()));


            //sliding window to compare the two line slopes
            while(Math.abs(currSlopeAngle - compareSlopeAngle) < thresholdAngle && j < lineSegments.size()){
                compareLineSeg = lineSegments.get(j);
                compareSlopeAngle = Math.toDegrees(Math.atan(compareLineSeg.getSlope()));
                j++;
            }
            //when we exit this while loop we will get the lineSegment with the different slope compared to the current line

            Point end = compareLineSeg.getEnd();

            //generating the subline (part of the line) to give to the collisionSegment
            ArrayList<LineSegment> subLineSegments = new ArrayList<>(lineSegments.subList(i, j));
            Line subLine = new Line(subLineSegments, 3);


            collisionSegments.add(new CollisionSegment(start, end, subLine));

            if(j != i + 1){
                //if we entered the while loop we want to skip right to where ever it left off
                i = j - 1;
            }
        }

        return collisionSegments;
    }

    public Rectangle generateBoundingBox(){
        //hopefully this works
        double maxX = Integer.MIN_VALUE;
        double maxY = Integer.MIN_VALUE;
        double minX = Integer.MAX_VALUE;
        double minY = Integer.MAX_VALUE;

        for(LineSegment ls : parentLine.getLineSegments()){
            Point start = ls.getStart();
            Point end = ls.getEnd();
            if(start.getX() > maxX){
                maxX = start.getX();
            }
            else if(start.getX() < minX){
                minX = start.getX();
            }

            if(end.getX() > maxX){
                maxX = end.getX();
            }
            else if(end.getX() < minX){
                minX = end.getX();
            }

            if(start.getY() > maxY){
                maxY = start.getY();
            }
            else if(start.getY() < minY){
                minY = start.getY();
            }

            if(end.getY() > maxY){
                maxY = end.getY();
            }
            else if(end.getY() < minY){
                minY = end.getY();
            }
        }

        Point topLeft = new Point(minX, minY);
        Point bottomLeft = new Point(minX, maxY);
        Point topRight = new Point(maxX, minY);
        Point bottomRight = new Point(maxX, maxY);
        return new Rectangle(topLeft, topRight, bottomLeft, bottomRight);
    }

    public boolean isCollidingRigidBody(RigidBody r){
        //on this upper level do is colliding with rectangle
        //if it is then do isCollisionSegment colliding with

        if(collisionRectangle.isOverlapping(r.getBoundingBox())){
            System.out.println("Inside box");
            //r is inside box
            //if we are in box then we want now check all the collision segments to see if they're colliding
            for(CollisionSegment collisionSegment : collisionSegments){
                if(collisionSegment.isCollidingRigidBody(r))
                    return true;
            }
        }

        return false;
    }

}
