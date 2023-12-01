package ite.jp.ak.lab02.logic;

import ite.jp.ak.lab02.data.Person;
import ite.jp.ak.lab02.data.Ski;

import java.util.List;

public class SkiAllocation {
    public final Person person;
    public final List<Ski> preferredSkis;
    public final Ski actualSki;

    public final int score;

    private boolean gotPreferredSkiType;

    public boolean gotPreferredSkiType() {
        return gotPreferredSkiType;
    }

    public SkiAllocation(Person person, List<Ski> preferredSkis, Ski actualSki) {
        this.person = person;
        this.preferredSkis = preferredSkis;
        this.actualSki = actualSki;

        this.score = calculateAllocationScore();
    }

    public List<String> getPreferredSkisTypes() {
        return this.preferredSkis.stream().map(Ski::type).toList();
    }

    private int getMinDifference() {
        int minDifference = Integer.MAX_VALUE;
        for (Ski ski : this.preferredSkis) {
            int difference = Math.abs(this.actualSki.length() - ski.length());
            if (difference < minDifference) {
                minDifference = difference;
            }
        }
        return minDifference;
    }

    private int calculateAllocationScore() {
        if (getPreferredSkisTypes().contains(this.actualSki.type())) {
            this.gotPreferredSkiType = true;
            Ski preferedSki = this.preferredSkis.stream().filter(ski -> ski.type().equals(this.actualSki.type())).findFirst().get();
            return Math.abs(this.actualSki.length() - preferedSki.length());
        } else {
            this.gotPreferredSkiType = false;
            return getMinDifference() * 10;
        }
    }

    @Override
    public String toString() {
        return "SkiAllocation[" + "person=" + this.person + ", preferedSkis=" + this.preferredSkis + ", actualSki=" + this.actualSki + "]";
    }

}
