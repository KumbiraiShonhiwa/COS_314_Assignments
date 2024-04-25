public class Campus // define the campus i.e hatfield, groenkloof, etc
{
    public String name;
    private int[] distances;

    public Campus(String name, int[] distances) {
        this.name = name;
        this.distances = distances;
    }

    public int[] getDistances() {
        return distances;
    }

    public String getName() {
        return name;
    }
}
