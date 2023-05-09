import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class AppCPM {
    private JFrame frame;
    private DefaultTableModel model;
    private JTable tabela;

    public AppCPM() {
        frame = new JFrame("CMP");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initialize();
    }

    private void initialize() {

        //Tworzenie pól tekstowych i przycisków
        JTextField czasField = new JTextField(10);
        JTextField rozpoczynajaceField = new JTextField(10);
        JTextField konczaceField = new JTextField(10);
        JButton dodajButton = new JButton("Dodaj");
        JButton usunButton = new JButton("Usuń");
        JButton generujButton = new JButton("Generuj");

        //Tworzenie tabeli
        DefaultTableModel model = new DefaultTableModel();
        JTable tabela = new JTable(model);
        model.addColumn("Czynność");
        model.addColumn("Czas trwania");
        model.addColumn("Zdarzenie rozpoczynające");
        model.addColumn("Zdarzenie kończące");

        //Dodawanie tabeli do okna
        JScrollPane scrollPane = new JScrollPane(tabela);

        //Dodawanie pól tekstowych i przycisków do okna
        JPanel panel = new JPanel();
        panel.add(new JLabel("Czas trwania: "));
        panel.add(czasField);
        panel.add(new JLabel("Zdarzenie rozpoczynające: "));
        panel.add(rozpoczynajaceField);
        panel.add(new JLabel("Zdarzenie kończące: "));
        panel.add(konczaceField);
        panel.add(dodajButton);
        panel.add(usunButton);
        panel.add(generujButton);
        panel.add(scrollPane);

        int[] rowCount = {0};

        //DODAJ
        dodajButton.addActionListener(e -> {
            try {
                float czas = Float.parseFloat(czasField.getText());
                int rozpoczynajace = Integer.parseInt(rozpoczynajaceField.getText());
                int konczace = Integer.parseInt(konczaceField.getText());

                if (rozpoczynajace >= konczace) {
                    JOptionPane.showMessageDialog(frame, "Zdarzenia nie mogą być takie same/Zdarzenie kończące nie może być mniejsze od początkowego", "Błąd", JOptionPane.ERROR_MESSAGE);
                } else {
                    boolean istnieje = false;
                    for (int i = 0; i < model.getRowCount(); i++) {
                        if (Integer.parseInt(model.getValueAt(i, 2).toString()) == rozpoczynajace && Integer.parseInt(model.getValueAt(i, 3).toString()) == konczace) {
                            istnieje = true;
                            break;
                        }
                    }

                    if (istnieje) {
                        JOptionPane.showMessageDialog(frame, "Zdarzenie już istnieje.", "Błąd", JOptionPane.ERROR_MESSAGE);
                    } else {
                        rowCount[0]++;
                        String[] row = {
                                Integer.toString(rowCount[0]),
                                Float.toString(czas),
                                Integer.toString(rozpoczynajace),
                                Integer.toString(konczace)
                        };
                        model.addRow(row);
                        czasField.setText("");
                        rozpoczynajaceField.setText("");
                        konczaceField.setText("");
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Błędne dane. Wprowadź poprawne wartości.", "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        });

        //USUŃ
        usunButton.addActionListener(e -> {
            int selectedRow = tabela.getSelectedRow();
            if (selectedRow != -1) {
                model.removeRow(selectedRow);
                rowCount[0]--;
                for (int i = 0; i < model.getRowCount(); i++) {
                    model.setValueAt(i + 1, i, 0);
                }
            }
        });

        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tabela.rowAtPoint(e.getPoint());
                tabela.getSelectionModel().setSelectionInterval(selectedRow, selectedRow);
            }
        });

        //Wyświetlanie okna
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);

        //GENERUJ
        generujButton.addActionListener(e -> {
            List<Activity> activities = new ArrayList<>();

            for (int i = 0; i < model.getRowCount(); i++) {
                int id = Integer.parseInt((String) model.getValueAt(i, 0));
                double duration = Double.parseDouble((String) model.getValueAt(i, 1));
                int occurrenceA = Integer.parseInt((String) model.getValueAt(i, 2));
                int occurrenceB = Integer.parseInt((String) model.getValueAt(i, 3));
                activities.add(new Activity(id, duration, occurrenceA, occurrenceB));
            }

            List<Occurrence> occurrences = OccurrenceCalculations.createOccurrences(activities);
            GeneratedWindow generatedWindow = new GeneratedWindow(activities, occurrences);
            generatedWindow.show();
        });
    }

    public void show() {
        frame.pack();
        frame.setVisible(true);
    }
}


