package ite.jp.ak.lab02;

import ite.jp.ak.lab02.data.Discount;
import ite.jp.ak.lab02.data.Preference;
import ite.jp.ak.lab02.data.SkiStock;
import ite.jp.ak.lab02.ui.SkiRentalGUI;
import ite.jp.ak.lab02.utils.DataFilesReader;
import ite.jp.ak.lab02.utils.SolutionFileWriter;
import ite.jp.ak.lab02.logic.SkiAllocation;
import ite.jp.ak.lab02.logic.SkiDistributor;

import java.util.*;

public class SkiRental {

    static List<SkiStock> skiStockList = null;
    static List<Preference> preferencesList = null;
    static List<Discount> discountsList = null;

    static final String outputFileName = "output.txt";

    public static void main(String[] args) {

        if (args.length == 0) {
            new SkiRentalGUI();
            return;
        } else if (args.length < 3) {
            System.out.println("Sposób użycia: java -jar lab02.jar preferences.txt wykaz.txt znizki.txt");
            return;
        }

        String preferencesFile = args[0];
        String skiStockFile = args[1];
        String discountsFile = args[2];

        System.out.print("Wczytywanie danych... ");

        try {
            preferencesList = DataFilesReader.readPreferences(preferencesFile);
            skiStockList = DataFilesReader.readSkiStocks(skiStockFile);
            discountsList = DataFilesReader.readDiscounts(discountsFile);
        } catch (Exception e) {
            System.out.println("BŁĄD");
            System.out.println(e.getMessage());
            System.exit(-1);
        }

        System.out.println("OK");

        System.out.print("Przydzielanie nart... ");

        SkiDistributor skiDistributor = new SkiDistributor(preferencesList, skiStockList, discountsList);
        List<SkiAllocation> solution = skiDistributor.distribute();

        System.out.println("OK");


        System.out.println("Wynik dopasowania: " + skiDistributor.calculateDistributionScore(solution));
        System.out.print("Osoby, którym nie udało się dopasować typu nart do preferencji: " + skiDistributor.getWrongSkiTypeCount());
        System.out.println("/" + preferencesList.size());
        System.out.print("Osoby, dla których zabrakło nart: " + skiDistributor.getPeopleWithNoSki());
        System.out.println("/" + preferencesList.size());

        try {
            SolutionFileWriter.writeSkiDistribution(solution, outputFileName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

        System.out.println("Zapisano do pliku: " + outputFileName);
    }
}