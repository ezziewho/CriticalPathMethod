import java.util.ArrayList;
import java.util.List;

public class Occurrence {
    private int id;
    private Double t0;
    private Double t1;
    private Double L;
    private List<Activity> prevActivity;
    private List<Activity> nextActivity;

    public Occurrence(int id, Double t0, Double t1, Double L, List<Activity> prevActivity, List<Activity> nextActivity) {
        this.id = id;
        this.t0 = t0;
        this.t1 = t1;
        this.L = L;
        this.prevActivity = prevActivity;
        this.nextActivity = nextActivity;
    }

    public Occurrence(int id) {
        this(id, null, null, null, new ArrayList<>(), new ArrayList<>());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getT0() {
        return t0;
    }

    public void setT0(Double t0) {
        this.t0 = t0;
    }

    public Double getT1() {
        return t1;
    }

    public void setT1(Double t1) {
        this.t1 = t1;
    }

    public Double getL() {
        return L;
    }

    public void setL(Double L) {
        this.L = L;
    }

    public List<Activity> getPrevActivities() {
        return prevActivity;
    }

    public void setPrevActivity(List<Activity> prevActivity) {
        this.prevActivity = prevActivity;
    }

    public List<Activity> getNextActivities() {
        return nextActivity;
    }

    public void setNextActivity(List<Activity> nextActivity) {
        this.nextActivity = nextActivity;
    }
}
