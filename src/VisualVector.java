public class VisualVector {
    //this class serves to make a distinction between vectors used in math
    //It's its own class to prevent mistakes where a visual vector with a different magnitude is used in Math and messes
    //up calculations

    Vector vector;
    Vector visualVector;
    final int scale = 10;

    //for turning a upgrading a vector into a visualVector to be shown on screen
    public VisualVector(Vector vector) {
        this.vector = vector;
        visualVector = new Vector(vector.getX() * scale, vector.getY() * scale);
    }

    //for turning a line on screen into a vector to do math with
    public VisualVector(int x, int y){
        this.visualVector = new Vector(x,y);
        this.vector = new Vector(x/10.0,y/10.0);
    }

    public double getX() {
        return visualVector.getX();
    }

    public double getY() {
        return visualVector.getY();
    }

    //convert visual vector back into math vector
    public Vector getVector() {
        return vector;
    }
}
