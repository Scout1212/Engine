//The idea is that a ColllisionSegment is a rough version of the line segment
//it holds a bunch of line segments but tries to generalize them into a couple lines
//only when the ball gets close enough will it reveal the little segments and let the line through
public class CollisionSegment extends LineSegment {
    private Line subLine;
    public CollisionSegment(Point p1, Point p2, Line subline) {
        super(p1, p2);
        this.subLine = subline;
    }

    @Override
    public boolean isCollidingRigidBody(RigidBody r) {
        if(super.isCollidingRigidBody(r)){
            System.out.println("collided with with Collision line");
            return subLine.isCollidingRigidBody(r);
        }

        return false;
    }
}
