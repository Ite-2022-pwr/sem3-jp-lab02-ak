package ite.jp.ak.lab02.data;

import java.text.ParseException;

/**
 * Klasa reprezentująca zniżkę.
 */
public record Discount(Kind kind, int discount) {

    @Override
    public String toString() {
        return "Discount[kind="+this.kind+", discount="+this.discount+"]";
    }

    /**
     * Parsuje string do obiektu klasy Discount.
     * @param discountString string do sparsowania
     * @return obiekt klasy Discount
     * @throws ParseException jeśli string nie jest w poprawnym formacie
     */
    public static Discount parseDiscount(String discountString) throws ParseException {
        if (!discountString.matches("^[A-Z],[0-9]+$")) throw new ParseException("Błędne dane: " + discountString, 0);
        String[] data = discountString.split(",");
        if (data.length != 2) throw new ParseException("Błędne dane: " + discountString, 0);
        Kind kind;
        switch (data[0]) {
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
                throw new ParseException("Błędne dane: " + discountString, 0);
        }
        int discount = Integer.parseInt(data[1]);
        return new Discount(kind, discount);
    }
}
