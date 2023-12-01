package ite.jp.ak.lab02.logic;

import ite.jp.ak.lab02.data.Discount;
import ite.jp.ak.lab02.data.Preference;
import ite.jp.ak.lab02.data.Ski;
import ite.jp.ak.lab02.data.SkiStock;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Klasa odpowiedzialna za wydawanie nart.
 */
public class SkiDistributor {

    // preferencje
    List<Preference> preferences;

    // wykaz
    List<SkiStock> stocks;

    // zniżki
    List<Discount> discounts;

    // lista wszystkich nart
    public final List<Ski> allSkis = new ArrayList<>();

    private int peopleWithNoSki = 0;

    public int getPeopleWithNoSki() {
        return peopleWithNoSki;
    }

    private int wrongSkiTypeCount = 0;

    public int getWrongSkiTypeCount() {
        return wrongSkiTypeCount;
    }

    /**
     * Konstruktor klasy SkiAssociator.
     * @param preferences lista preferencji
     * @param stocks lista stanu magazynu
     * @param discounts lista zniżek
     */
    public SkiDistributor(List<Preference> preferences, List<SkiStock> stocks, List<Discount> discounts) {
        this.preferences = preferences;
        this.stocks = stocks;
        this.discounts = discounts;

        this.createSkiList();
    }

    /**
     * Tworzy listę wszystkich nart, która zostanie wykorzystana przy generowaniu kombinacji.
     */
    private void createSkiList() {
        for (SkiStock stock : this.stocks) {
            for (int i = 0; i < stock.getQuantity(); i++) {
                this.allSkis.add(stock.getSki());
            }
        }
    }

    /**
     * Przyporządkowuje narty do osób w jak najlepszy sposób.
     * @return lista przyporządkowań
     */
    public List<SkiAllocation> distribute() {
        List<SkiAllocation> skiDistribution = new ArrayList<>();  // lista przyporządkowań

        // sortowanie zniżek od największej do najmniejszej
        this.discounts.sort(new Comparator<Discount>() {
            @Override
            public int compare(Discount a, Discount b) {
                return b.discount() - a.discount();
            }
        });

        // sortowanie preferencji od najmniejszej do największej liczby preferowanych nart
        // ponieważ najpierw chcemy przyporządkować narty osobom, które mają najmniej preferencji
        this.preferences.sort(Comparator.comparingInt(p -> p.listOfSkis.size()));

        // przyporządkowanie nart do osób
        for (Discount discount : this.discounts) {

            // wybieranie wszystkich preferencji dla danej zniżki
            List<Preference> preferencesForKind = this.preferences.stream().filter(p -> p.person.kind() == discount.kind()).toList();

            // wyszukiwanie najlepszego przyporządkowania dla danej osoby
            for (Preference preference : preferencesForKind) {
                SkiAllocation allocation = this.findBestMatch(preference);
                if (allocation != null) {
                    // jeśli znaleziono przyporządkowanie to dodajemy je do listy
                    skiDistribution.add(allocation);
                }
            }
        }

        skiDistribution.sort(Comparator.comparingInt(a -> a.person.id()));

        return skiDistribution;
    }

    /**
     * Oblicza jakość rozwiązania.
     * @param distribution rozwiązanie
     * @return jakość rozwiązania
     */
    public int calculateDistributionScore(List<SkiAllocation> distribution) {
        int price = 0;
        int lengthScore = 0;
        this.wrongSkiTypeCount = 0;
        for (SkiAllocation allocation : distribution) {
            lengthScore += allocation.score;
            int discount = this.discounts.stream().filter(d -> d.kind() == allocation.person.kind()).findFirst().get().discount();
            price += (100 - discount) / 10; // procent ceny
            if (!allocation.gotPreferredSkiType()) {
                this.wrongSkiTypeCount++;
            }
        }

        int notAssigned = this.preferences.size() - distribution.size();
        this.peopleWithNoSki = notAssigned;
        return lengthScore + notAssigned + price;
    }

    /**
     * Zwraca najlepsze przyporządkowanie dla danej osoby
     * z pozostałych nart.
     * @param preference
     * @return
     */
    public SkiAllocation findBestMatch(Preference preference) {
        SkiAllocation bestMatch = null;
        int bestScore = Integer.MAX_VALUE;
        for (Ski ski : this.allSkis) {
            SkiAllocation allocation = new SkiAllocation(preference.person, preference.listOfSkis, ski);
            if (allocation.score < bestScore) {
                bestScore = allocation.score;
                bestMatch = allocation;
            }
        }
        if (bestMatch != null) {
            this.allSkis.remove(bestMatch.actualSki);
        }

        return bestMatch;
    }
}
