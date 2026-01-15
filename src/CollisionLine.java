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
        for(CollisionSegment collisionSegment : collisionSegments){
            collisionSegment.drawSelf(g2d);
        }

        //todo put back

        //g2d.setColor(Color.blue);
        //collisionRectangle.drawSelf(g2d, false);
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

        //properly find the thresholdAngle later --> maybe if there are more line segments then threshold angle should increase to cut out more lines
        double thresholdAngle = Math.toRadians(20);

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

            //when the difference between the two angles is less than a certain number (the angles are close in magnitude --> the slopes are similar)
            //Then we want to keep going until we find the angle that is different from the current lineSegment
            while(Math.abs(currSlopeAngle - compareSlopeAngle) < thresholdAngle && j < lineSegments.size()){
                compareLineSeg = lineSegments.get(j);
                compareSlopeAngle = compareLineSeg.getAngle();
                j++;
            }

            //when we exit this while loop we will use the lineSegment with the different angle(slope) so we can end the collision segment at the end of that lineSegment
            Point end = compareLineSeg.getEnd();

            int startIndex = i;
            int endIndex = j;

            //If J was incremented (or j is not the next element)
            if(j != i + 1){
                //If we incremented j then we passed by a bunch of lineSegments with slopes similar to current line
                //so we want to skip over them since they will already be included in a segment
                i = j - 1;
            }
            else {
                //If we did not increment j then compareLineSeg has a different slope so we do not want the end of it
                end = currLineSeg.getEnd();

                //going further --> keep in mind we do not want the end of compareLineSeg
                //If we are at the last iteration of the outer loop and we are here (comment above) then we will skip the compareLineSeg(the last segment) and add currline segment instead
                //but the outerloop wont make it to the last index of the lineSegments ArrayList and we will miss adding the last segment in this edge case
                //this feels like band aid code but we will add it manually here
                if(i == lineSegments.size() - 2){
                    LineSegment lastLineSeg = lineSegments.getLast();
                    ArrayList<LineSegment> sublineLast = new ArrayList<>();
                    sublineLast.add(lastLineSeg);
                    Line sublineLastLine = new Line(sublineLast, 3);
                    collisionSegments.add(new CollisionSegment(lastLineSeg.getStart(), lastLineSeg.getEnd(), sublineLastLine));
                }
            }

            //generating the subline (part of the line) to give to the collisionSegment
            ArrayList<LineSegment> subLineSegments = new ArrayList<>(lineSegments.subList(startIndex, endIndex));
            Line subLine = new Line(subLineSegments, 3);

            //adding everything to the Collision segment then to the list to give to the collision line
            collisionSegments.add(new CollisionSegment(start, end, subLine));
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

    /**
     * The first level of collision checking, compares bounding boxes:
     * if touching, it passes down collision check to the next level:
     * CollisionSegment.isCollidingRigidBody(Particle r).
     * @return Collided LineSegments, if no collision then empty ArrayList
     */
    public ArrayList<LineSegment> getCollidedLineSegments(Particle r){
        //on this upper level do is colliding with rectangle --> refer to green comment above
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
