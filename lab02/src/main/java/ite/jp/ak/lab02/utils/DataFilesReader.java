package ite.jp.ak.lab02.utils;

import ite.jp.ak.lab02.data.Preference;
import ite.jp.ak.lab02.data.SkiStock;
import ite.jp.ak.lab02.data.Discount;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.Scanner;

public class DataFilesReader {

    public static List<Preference> readPreferences(String filename) throws Exception {
        List<Preference> preferences = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try {
                    preferences.add(Preference.parsePreference(line));
                } catch (Exception ex) {
                    throw new Exception("Błąd parsowania preferencji: " + line, ex);
                }
            }
        } catch (FileNotFoundException ex) {
            throw new Exception("Nie znaleziono pliku: " + filename, ex);
        }

        return preferences;
    }

    public static List<SkiStock> readSkiStocks(String filename) throws Exception {
        List<SkiStock> skiStocks = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try {
                    skiStocks.add(SkiStock.parseSkiStock(line));
                } catch (Exception ex) {
                    throw new Exception("Błąd parsowania stanu magazynowego: " + line, ex);
                }
            }
        } catch (FileNotFoundException ex) {
            throw new Exception("Nie znaleziono pliku: " + filename, ex);
        }

        return skiStocks;
    }

    public static List<Discount> readDiscounts(String filename) throws Exception {
        List<Discount> discounts = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try {
                    discounts.add(Discount.parseDiscount(line));
                } catch (Exception ex) {
                    throw new Exception("Błąd parsowania zniżki: " + line, ex);
                }
            }
        } catch (FileNotFoundException ex) {
            throw new Exception("Nie znaleziono pliku: " + filename, ex);
        }

        return discounts;
    }
}
