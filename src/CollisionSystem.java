import java.awt.*;
import java.util.ArrayList;

public class CollisionSystem {
    public CollisionSystem(){

    }

    public boolean isRigidBodyCollideWithLine(RigidBody b1, LineSegment l1, Graphics2D g2d){
        return l1.distanceToPoint(b1.getPosition()) < b1.getCenterX();
    }
}
