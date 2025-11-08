import java.util.ArrayList;
//todo I want to make this a child of CollisionLine and make a physics object
public class RigidBody {
    ArrayList<Particle> particles;

    public RigidBody() {
        particles = new ArrayList<>();
    }

    public RigidBody(final ArrayList<Particle> particles) {
        this.particles = particles;
    }
}
