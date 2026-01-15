import java.util.ArrayList;

// A CollisionSegment is a rough approximation a part of the line.
// It groups many small segments of line and generalizes them into a segment.
// When the object gets close, it reveals the small segments to do stuff with them
public class CollisionSegment extends LineSegment {
    private Line subLine;
    private double distanceToFurthestPoint;

    public CollisionSegment(Point p1, Point p2, Line subline) {
        super(p1, p2);
        this.subLine = subline;
        this.distanceToFurthestPoint = findDistanceToFurthestPoint(subLine);
    }

    @Override
    public boolean isCollidingRigidBody(Particle r) {
        //What this does it makes a circle around the point on the line which extends to the furthest point on the sub line so
        //it will account for any collisions where the ball collides with the subline but not directly with the collision line segment
        return getDistanceToPoint(r.getCenter()) < (r.getDiam()/2.0) + distanceToFurthestPoint;
    }

    /**
     * The second level of collision checking, does collision from general lines (this) to particle:
     * if touching then it passes it down to the subline (the line on screen): Line.isCollidingRigidBody();
     * @return Collided LineSegments, if no collision then EmptyArrayList
     */
    public ArrayList<LineSegment> getCollidedLineSegment(Particle r) {
        ArrayList<LineSegment> collidedSegment = new ArrayList<>();

        if(isCollidingRigidBody(r)){
            collidedSegment.addAll(subLine.getCollidedLineSegments(r));
        }

        return collidedSegment;
    }

    public double findDistanceToFurthestPoint(Line subLine){
        double distanceToFurthestPoint = 0;
        for(LineSegment ls: subLine.getLineSegments()){
            for(Point point: ls.getPoints()){
                //the end and start points
                double distance = getDistanceToPoint(point);
                if(distance > distanceToFurthestPoint){
                    distanceToFurthestPoint = distance;
                }
            }
        }

        return distanceToFurthestPoint + 5;
    }
}
