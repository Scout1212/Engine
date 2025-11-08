import java.util.ArrayList;

//The idea is that a ColllisionSegment is a rough version of the line segment
//it holds a bunch of line segments but tries to generalize them into a couple lines
//only when the ball gets close enough will it reveal the little segments and let the line through
public class CollisionSegment extends LineSegment {
    private Line subLine;
    public CollisionSegment(Point p1, Point p2, Line subline) {
        super(p1, p2);
        this.subLine = subline;
    }

    /**
     * The second level of collision checking, does collision from general lines (this) to particle:
     * if touching then it passes it down to the subline (the line on screen): Line.isCollidingRigidBody();
     * @return Collided LineSegments, if no collision then EmptyArrayList
     */
    public ArrayList<LineSegment> getCollidedLineSegment(Particle r) {
        ArrayList<LineSegment> collidedSegment = new ArrayList<>();

        if(super.isCollidingRigidBody(r)){
            System.out.println("collided with with Collision line");
            collidedSegment.addAll(subLine.getCollidedLineSegments(r));
        }

        return collidedSegment;
    }
}
