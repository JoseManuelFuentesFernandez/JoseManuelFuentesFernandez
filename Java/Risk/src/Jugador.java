import java.util.Objects;

public class Jugador implements Comparable{
    static final int tropasIniciales = 6;//@modificable @test
    static int contador=0;

    final int id;
    String nombre;
    public int tropasTot;

    public Jugador(String nombre) {
        this.id = ++contador;
        this.nombre = nombre;
        this.tropasTot = tropasIniciales;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("(J");
        sb.append(id).append(") ").append(nombre).append(" -> ").append(tropasTot).append(" tropa");
        if(tropasTot!=1) sb.append("s");
        return sb.toString();
    }

    @Override
    public int compareTo(Object o) {
        Jugador otro = (Jugador) o;
        return this.id-otro.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return id == jugador.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
