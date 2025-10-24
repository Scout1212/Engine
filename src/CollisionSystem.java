import java.lang.reflect.Array;
import java.util.ArrayList;

public class CollisionSystem {
    public CollisionSystem(){

    }

    //im just going to implement the sort based on distance here and copy and paste it wherever its going to go
    public ArrayList<Point> sortOnDistance(ArrayList<Point> points, Point p1){
        int n = points.size();
        for (int i = 1; i < n; i++) {

            double key = p1.distance(points.get(i));
            Point value = points.get(i);
            int j = i - 1;

            while (j >= 0 && p1.distance(points.get(j)) > key) {
                points.set(j + 1, points.get(j));
                j = j - 1;
            }

            points.set(j + 1, value);
        }

        return points;
    }
    //after each time we sort I think we should be able to just grab the first one to check for collison

    //I just realized its not likely for the ball to hit right onto a point --> I need another critera for collision
    public boolean CollisionRigidBodyWithLine(RigidBody b1, Line l1){
        Point b1Center = new Point(b1.getCenterX(), b1.getCenterY());
        if( b1Center.distance())
    }
}
