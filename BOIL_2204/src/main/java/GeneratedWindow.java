import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GeneratedWindow {
    private JFrame frame;

    public GeneratedWindow(List<Activity> activities, List<Occurrence> occurrences) {
        frame = new JFrame("Wyniki");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        initialize(activities, occurrences);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    private void initialize(List<Activity> activities, List<Occurrence> occurrences) {

        //Tworzenie tabeli z Acticity
        DefaultTableModel activityModel = new DefaultTableModel();
        activityModel.addColumn("Czynność");
        activityModel.addColumn("Czas trwania");
        activityModel.addColumn("Zdarzenie rozpoczynające");
        activityModel.addColumn("Zdarzenie kończące");

        //Wypełnianie tabeli z Activity danymi z listy
        for (Activity activity : activities) {
            Object[] row = {
                    activity.getId(),
                    activity.getDuration(),
                    activity.getOccurrenceA(),
                    activity.getOccurrenceB()
            };
            activityModel.addRow(row);
        }

        JTable activityTable = new JTable(activityModel);
        JScrollPane activityScrollPane = new JScrollPane(activityTable);


        frame.getContentPane().add(activityScrollPane);

        //Tworzenie tabeli z Occurrence
        DefaultTableModel occurrenceModel = new DefaultTableModel();
        occurrenceModel.addColumn("ID");
        occurrenceModel.addColumn("t0");
        occurrenceModel.addColumn("t1");
        occurrenceModel.addColumn("L");
        occurrenceModel.addColumn("Previous Activities");
        occurrenceModel.addColumn("Next Activities");

        //Wypełnianie tabeli
        for (Occurrence occurrence : occurrences) {
            String prevActivities = formatActivities(occurrence.getPrevActivities());
            String nextActivities = formatActivities(occurrence.getNextActivities());

            Object[] row = {
                    occurrence.getId(),
                    occurrence.getT0(),
                    occurrence.getT1(),
                    occurrence.getL(),
                    prevActivities,
                    nextActivities
            };
            occurrenceModel.addRow(row);
        }

        JTable occurrenceTable = new JTable(occurrenceModel);
        JScrollPane occurrenceScrollPane = new JScrollPane(occurrenceTable);

        //Dodawanie tabel do okna
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, activityScrollPane, occurrenceScrollPane);
        frame.getContentPane().add(splitPane, BorderLayout.CENTER);


        //CPM
                List<Occurrence> criticalPath = OccurrenceCalculations.calculateCPM(occurrences);
                JLabel criticalPathLabel = new JLabel("Ścieżka krytyczna: " + formatOccurrences(criticalPath));

        //TR
                double trValue = criticalPath.get(criticalPath.size() - 1).getT1();
                JLabel trLabel = new JLabel("Tr = " + trValue);

        //Tworzenie miejsca w oknie do wyświetlania CPM i TR
                JPanel labelPanel = new JPanel(new FlowLayout());
                labelPanel.add(criticalPathLabel);
                labelPanel.add(trLabel);

                frame.getContentPane().add(labelPanel, BorderLayout.SOUTH);
    }

    //Formatowanie aktywności aby były zdatne do ukazywania ich w tabeli
    private String formatActivities(List<Activity> activities) {
        if (activities == null || activities.isEmpty()) {
            return "-";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < activities.size(); i++) {
            sb.append(activities.get(i).getId());
            if (i < activities.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    //Formatowanie zdarzeń aby były zdatne do ukazywania ich w tabeli
    private String formatOccurrences(List<Occurrence> occurrences) {
        if (occurrences == null || occurrences.isEmpty()) {
            return "-";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < occurrences.size(); i++) {
            sb.append(occurrences.get(i).getId());
            if (i < occurrences.size() - 1) {
                sb.append(" >> ");
            }
        }
        return sb.toString();
    }


    public void show() {
        frame.setVisible(true);
    }
}
