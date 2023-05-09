
public class Activity {
    private int id;
    private double duration;
    private int occurrenceA;
    private int occurrenceB;

    public Activity(int id, double duration, int occurrenceA, int occurrenceB) {
        this.id = id;
        this.duration = duration;
        this.occurrenceA = occurrenceA;
        this.occurrenceB = occurrenceB;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public int getOccurrenceA() {
        return occurrenceA;
    }

    public void setOccurrenceA(int occurrenceA) {
        this.occurrenceA = occurrenceA;
    }

    public int getOccurrenceB() {
        return occurrenceB;
    }

    public void setOccurrenceB(int occurrenceB) {
        this.occurrenceB = occurrenceB;
    }


    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", duration=" + duration +
                ", occurrenceA=" + occurrenceA +
                ", occurrenceB=" + occurrenceB +
                '}';
    }
}