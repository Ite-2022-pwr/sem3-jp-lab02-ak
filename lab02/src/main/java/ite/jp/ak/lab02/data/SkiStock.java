package ite.jp.ak.lab02.data;

import java.text.ParseException;

/**
 * Klasa reprezentująca stan magazynu nart.
 */
public class SkiStock {
    private final int quantity;
    private int available;
    private final Ski ski;

    public SkiStock(int quantity, Ski ski) {
        this.quantity = quantity;
        this.available = quantity;  // at first every ski is available
        this.ski = ski;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public int getAvailableSkisCount() {
        return this.available;
    }

    public void setAvailableSkisCount(int a) {
        this.available = a;
    }

    public void decreaseAvailableSkisCount() {
        this.available--;
    }

    public Ski getSki() {
        return this.ski;
    }

    /**
     * Parsuje string do obiektu klasy SkiStock.
     * @param stockString string do sparsowania
     * @return obiekt klasy SkiStock
     * @throws ParseException jeśli string nie jest w poprawnym formacie
     */
    public static SkiStock parseSkiStock(String stockString) throws ParseException {
        if (!stockString.matches("^[0-9]+,[a-z]+:[0-9]+$")) throw new ParseException("Błędne dane: " + stockString, 0);
        String[] data = stockString.split(",");
        if (data.length != 2) throw new ParseException("Błędne dane: " + stockString, 0);
        int quantity = Integer.parseInt(data[0]);
        String[] skiData = data[1].split(":");
        if (skiData.length != 2) throw new ParseException("Błędne dane: " + stockString, data[0].length() + 1);
        int skiLength = Integer.parseInt(skiData[1]);
        return new SkiStock(quantity, new Ski(skiLength, skiData[0]));
    }

    @Override
    public String toString() {
        return "SkiStock[" + "quantity=" + this.quantity + ", available=" + this.available + ", " + this.ski + "]";
    }
}
