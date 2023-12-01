package ite.jp.ak.lab02.ui;

import ite.jp.ak.lab02.data.Discount;
import ite.jp.ak.lab02.data.Preference;
import ite.jp.ak.lab02.data.SkiStock;
import ite.jp.ak.lab02.utils.DataFilesReader;
import ite.jp.ak.lab02.logic.SkiAllocation;
import ite.jp.ak.lab02.logic.SkiDistributor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SkiRentalGUI {

    List<Preference> preferencesList = null;
    List<SkiStock> skiStockList = null;
    List<Discount> discountsList = null;

    // komponenty
    private JPanel MainPanel;
    private JLabel labelPreferences;
    private JLabel labelChooseFilePreferences;
    private JButton buttonChooseFilePreferences;
    private JLabel labelStock;
    private JLabel labelChooseFileStock;
    private JButton buttonChooseFileStock;
    private JLabel labelDiscounts;
    private JLabel labelChooseFileDiscounts;
    private JButton buttonChooseFileDiscounts;
    private JTable tableOutput;
    private JButton buttonCompute;
    private JScrollPane scrollPaneOutput;

    public SkiRentalGUI() {
        JFrame frame = new JFrame("Ski Rental System");
        frame.setContentPane(MainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID osoby");
        model.addColumn("Grupa wiekowa");
        model.addColumn("Typ nart");
        model.addColumn("Długość nart");

        buttonChooseFilePreferences.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                int returnVal = fileChooser.showOpenDialog(MainPanel);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    String filename = fileChooser.getSelectedFile().getName();
                    try {
                        preferencesList = DataFilesReader.readPreferences(filePath);
                        labelChooseFilePreferences.setText("Wybrano plik: " + filename);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(MainPanel, e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });

        buttonChooseFileStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                int returnVal = fileChooser.showOpenDialog(MainPanel);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    String filename = fileChooser.getSelectedFile().getName();
                    try {
                        skiStockList = DataFilesReader.readSkiStocks(filePath);
                        labelChooseFileStock.setText("Wybrano plik: " + filename);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(MainPanel, e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });

        buttonChooseFileDiscounts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser fileChooser = new JFileChooser();
                int returnVal = fileChooser.showOpenDialog(MainPanel);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    String filename = fileChooser.getSelectedFile().getName();
                    try {
                        discountsList = DataFilesReader.readDiscounts(filePath);
                        labelChooseFileDiscounts.setText("Wybrano plik: " + filename);
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(MainPanel, e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
        });

        buttonCompute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (preferencesList == null || skiStockList == null || discountsList == null) {
                    JOptionPane.showMessageDialog(MainPanel, "Nie wybrano wszystkich plików", "Błąd", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                SkiDistributor skiDistributor = new SkiDistributor(preferencesList, skiStockList, discountsList);
                List<SkiAllocation> solution = skiDistributor.distribute();

                for (SkiAllocation allocation : solution) {
                    model.addRow(new Object[]{allocation.person.id(), allocation.person.kind().toString().charAt(0), allocation.actualSki.type(), allocation.actualSki.length()});
                }


                tableOutput.setModel(model);

                scrollPaneOutput.setViewportView(tableOutput);

                String message = "Wynik dopasowania: " + skiDistributor.calculateDistributionScore(solution) + "\n";
                message += "Osoby, którym nie udało się dopasować typu nart do preferencji: " + skiDistributor.getWrongSkiTypeCount() + "/" + preferencesList.size() + "\n";
                message += "Osoby, dla których zabrakło nart: " + skiDistributor.getPeopleWithNoSki() + "/" + preferencesList.size();

                JOptionPane.showMessageDialog(
                        MainPanel,
                        message,
                        "Przydzielono",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
    }
}
