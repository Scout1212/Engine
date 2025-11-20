import java.awt.*;
import java.util.ArrayList;

//does collision
//holds the CollisionSegments
public class CollisionLine extends Line{

    private Line parentLine;
    private ArrayList<CollisionSegment> collisionSegments;
    private Rectangle collisionRectangle;
    private double distanceToFurtherPoint;

    private CollisionLine(Point p1, Point p2){

    }

    public CollisionLine(Line ParentLine) {
        this.parentLine = ParentLine;
        collisionSegments = new ArrayList<>();
        collisionSegments = generateCollisionLines(parentLine);
        collisionRectangle = generateBoundingBox();
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
        double thresholdAngle = Math.toRadians(50);

        for(int i = 0; i < lineSegments.size() - 1; i++){
            //the current LineSegment for comparison
            LineSegment currLineSeg = lineSegments.get(i);

            //saving this point to use for the new line later
            Point start = currLineSeg.getStart();


            int j = i + 1;
            LineSegment compareLineSeg = lineSegments.get(j);


            //getting the second angle for comparison
            double compareSlopeAngle = compareLineSeg.getAngle();
            //getting the angle of the current lineSegment for comparison
            double currSlopeAngle = currLineSeg.getAngle();

            //sliding window to compare the two line slopes
            while(Math.abs(currSlopeAngle - compareSlopeAngle) < thresholdAngle && j < lineSegments.size()){
                compareLineSeg = lineSegments.get(j);
                compareSlopeAngle = compareLineSeg.getAngle();

                System.out.println("CurrentSlope angle = " + currSlopeAngle + " CompareSlope angle = " + compareSlopeAngle + " Difference = " + (currSlopeAngle - compareSlopeAngle));

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

    private Rectangle generateBoundingBox(){
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
            if(start.getX() < minX){
                minX = start.getX();
            }

            if(end.getX() > maxX){
                maxX = end.getX();
            }
            if(end.getX() < minX){
                minX = end.getX();
            }

            if(start.getY() > maxY){
                maxY = start.getY();
            }
            if(start.getY() < minY){
                minY = start.getY();
            }

            if(end.getY() > maxY){
                maxY = end.getY();
            }
            if(end.getY() < minY){
                minY = end.getY();
            }
        }

        Point topLeft = new Point(minX, minY);
        Point bottomLeft = new Point(minX, maxY);
        Point topRight = new Point(maxX, minY);
        Point bottomRight = new Point(maxX, maxY);
        return new Rectangle(topLeft, topRight, bottomLeft, bottomRight);
    }

    //at first this method just got if we collided --> it returned boolean
    //but I realize eventually I might want to know what lineSegment I collided into to do some collision stuff with
    /**
     * The first level of collision checking, compares bounding boxes:
     * if touching, it passes down collision check to the next level:
     * CollisionSegment.isCollidingRigidBody(Particle r).
     * @return Collided LineSegments, if no collision then empty ArrayList
     */
    public ArrayList<LineSegment> getCollidedLineSegments(Particle r){
        //on this upper level do is colliding with rectangle
        //if it is then do isCollisionSegment colliding with
        ArrayList<LineSegment> collidedSegments = new ArrayList<>();

        if(collisionRectangle.isOverlapping(r.getBoundingBox())){
            //r is inside box
            for(CollisionSegment collisionSegment : collisionSegments){

                ArrayList<LineSegment> collidedSegment = collisionSegment.getCollidedLineSegment(r);

                if(!collidedSegment.isEmpty()) {
                    collidedSegments.addAll(collidedSegment);
                }
            }
        }

        return collidedSegments;
    }

}
