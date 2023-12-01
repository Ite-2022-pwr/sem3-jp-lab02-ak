package ite.jp.ak.lab02.data;

public record Person(int id, Kind kind)  {

    @Override
    public String toString() {
        return "Person[id="+this.id+", kind="+this.kind+"]";
    }
}
