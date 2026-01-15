import java.lang.reflect.Array;
import java.util.ArrayList;

public class Force {
    private Vector force;
    private final String NAME;
    public Force(Vector force, String name){
        this.force = force;
        this.NAME = name;
    }

    public Vector getVector() {
        return force;
    }

    public String getName() {
        return NAME;
    }

    public static Force sumForces(ArrayList<Force> forces, String name){
        Vector sum = new Vector(0, 0);
        for(int i = 0; i < forces.size(); i++){
            sum.add(forces.get(i).getVector());
        }

        System.out.println("Hello");


        return new Force(sum, name);
    }

    public static Vector findAcceleration(Force force, double mass){
        double angle = force.getVector().getAngle();
        return new Vector(force.getVector().getMagnitude()/mass, angle, "");
    }

    public void setForce(Vector v, double mass){
        force = v;
    }
}
