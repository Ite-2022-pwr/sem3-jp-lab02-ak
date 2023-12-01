package ite.jp.ak.lab02.utils;

import ite.jp.ak.lab02.logic.SkiAllocation;

import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.List;

public class SolutionFileWriter {
    public static void writeSkiDistribution(List<SkiAllocation> distribution, String filename) throws  Exception {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (SkiAllocation allocation : distribution) {
                String line = "";
                line += allocation.person.id() + ",";
                line += allocation.person.kind().toString().charAt(0) + ",";
                line += allocation.actualSki.type() + ":" + allocation.actualSki.length();
                writer.println(line);
            }
        } catch (FileNotFoundException ex) {
            throw new Exception("Nie można zapisać do pliku " + filename + ": " + ex.getMessage(), ex);
        }
    }
}
