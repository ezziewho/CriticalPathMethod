import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OccurrenceCalculations {
    public static List<Occurrence> createOccurrences(List<Activity> activities) {
        List<Occurrence> occurrences = new ArrayList<>();

        //Wyszukanie ostatniego zdarzenia
        int maxOccurrenceB = activities.stream()
                .map(Activity::getOccurrenceB)
                .max(Integer::compare)
                .orElse(0);

        //Tworzenie listy zdarzeń
        for (int i = 1; i <= maxOccurrenceB; i++) {
            Occurrence occurrence = new Occurrence(i);
            occurrences.add(occurrence);
        }

        //Przypisanie activity do list prevActivities i nextActivities w occurrences
        for (Activity activity : activities) {
            int occurrenceA = activity.getOccurrenceA();
            int occurrenceB = activity.getOccurrenceB();

            Occurrence prevOccurrence = occurrences.get(occurrenceA - 1);
            prevOccurrence.getNextActivities().add(activity);

            Occurrence nextOccurrence = occurrences.get(occurrenceB - 1);
            nextOccurrence.getPrevActivities().add(activity);
        }

        // Usuwanie zdarzeń bez czynności poprzedzających i następujących
        occurrences = occurrences.stream()
                .filter(o -> !(o.getPrevActivities().isEmpty() && o.getNextActivities().isEmpty()))
                .collect(Collectors.toList());

        //T0
        for (Occurrence occurrence : occurrences) {
            occurrence.setT0(calculateT0(occurrence, occurrences));
        }

        //T1
        for (int i = occurrences.size() - 1; i >= 0; i--) {
            Occurrence occurrence = occurrences.get(i);
            occurrence.setT1(calculateT1(occurrence, occurrences));
        }


        //L
            for (Occurrence occurrence : occurrences) {
                occurrence.setL(calculateL(occurrence));
            }

        return occurrences;
    }

    private static double calculateT0(Occurrence occurrence, List<Occurrence> occurrences) {
        List<Activity> prevActivities = occurrence.getPrevActivities();
        if (prevActivities.isEmpty()) {
            return 0;
        } else {
            double maxT0 = 0;
            for (Activity prevActivity : prevActivities) {
                int prevOccurrenceId = prevActivity.getOccurrenceA();
                Occurrence prevOccurrence = occurrences.get(prevOccurrenceId - 1);
                double currentT0 = prevActivity.getDuration() + prevOccurrence.getT0();
                maxT0 = Math.max(maxT0, currentT0);
            }
            return maxT0;
        }
    }

    private static double calculateT1(Occurrence occurrence, List<Occurrence> occurrences) {
        List<Activity> nextActivities = occurrence.getNextActivities();
        if (nextActivities.isEmpty()) {
            return occurrence.getT0();
        } else {
            double minT1 = Double.MAX_VALUE;
            for (Activity nextActivity : nextActivities) {
                int nextOccurrenceId = nextActivity.getOccurrenceB();
                Occurrence nextOccurrence = occurrences.get(nextOccurrenceId - 1);
                double currentT1 = nextOccurrence.getT1() - nextActivity.getDuration();
                minT1 = Math.min(minT1, currentT1);
            }
            return minT1;
        }
    }

    private static double calculateL(Occurrence occurrence) {
        return occurrence.getT1() - occurrence.getT0();
    }

    public static List<Occurrence> calculateCPM(List<Occurrence> occurrences) {
        List<Occurrence> criticalPath = new ArrayList<>();

        Occurrence currentOccurrence = occurrences.stream()
                .filter(o -> o.getPrevActivities().isEmpty())
                .findFirst()
                .orElse(null);

        if (currentOccurrence == null) {
            return criticalPath;
        }

        criticalPath.add(currentOccurrence);

        while (!currentOccurrence.getNextActivities().isEmpty()) {
            Activity maxDurationActivity = currentOccurrence.getNextActivities().stream()
                    .max((a1, a2) -> Double.compare(a1.getDuration(), a2.getDuration()))
                    .orElse(null);

            if (maxDurationActivity == null) {
                break;
            }
            int nextOccurrenceId = maxDurationActivity.getOccurrenceB();
            Occurrence nextOccurrence = occurrences.get(nextOccurrenceId - 1);
            criticalPath.add(nextOccurrence);

            currentOccurrence = nextOccurrence;
        }

        return criticalPath;
    }


}


