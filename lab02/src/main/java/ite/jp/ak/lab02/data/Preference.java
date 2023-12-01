package ite.jp.ak.lab02.data;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa reprezentująca preferencje narciarza.
 */
public class Preference {

    // lista preferowanych nart
    public List<Ski> listOfSkis = new ArrayList<>();

    // osoba, której dotyczą preferencje
    public final Person person;

    public Preference(Person person) {
        this.person = person;
    }

    /**
     * Parsuje string do obiektu klasy Preference.
     * @param preferenceString string do sparsowania
     * @return obiekt klasy Preference
     * @throws ParseException jeśli string nie jest w poprawnym formacie
     */
    public static Preference parsePreference(String preferenceString) throws ParseException {
        if (!preferenceString.matches("^[0-9]+,[A-Z],([a-z]+:[0-9]+;?)+$")) throw new ParseException("Błędne dane: " + preferenceString, 0);
        String[] data = preferenceString.split(",");
        if (data.length != 3) throw new ParseException("Błędne dane: " + preferenceString, 0);
        int id = Integer.parseInt(data[0]);
        Kind kind;
        switch (data[1]) {
            case "C":
                kind = Kind.Child;
                break;
            case "J":
                kind = Kind.Junior;
                break;
            case "A":
                kind = Kind.Adult;
                break;
            default:
                throw new ParseException("Błędne dane: " + preferenceString, data[0].length() + 1);
        }

        var person = new Person(id, kind);
        var preference = new Preference(person);

        String[] skis = data[2].split(";");
        for (String ski : skis) {
            String[] skiInfo = ski.split(":");
            if (skiInfo.length != 2) throw new ParseException("Błędne dane: " + preferenceString, data[0].length() + data[1].length() + 2);
            int skiLength = Integer.parseInt(skiInfo[1]);
            preference.addSki(new Ski(skiLength, skiInfo[0]));
        }

        return preference;
    }

    /**
     * Dodaje nartę do listy preferowanych nart.
     * @param newSki narta do dodania
     */
    public void addSki(Ski newSki) {
        this.listOfSkis.add(newSki);
    }

    @Override
    public String toString() {
        return "Preference[" + this.person.toString() + ", Skis" + this.listOfSkis.toString() + "]";
    }

}
