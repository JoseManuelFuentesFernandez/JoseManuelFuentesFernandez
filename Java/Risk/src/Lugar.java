import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class Lugar implements Comparable{
    private static String con="bcdfghjklmnpqrstvwxyz";
    private static String voc="aeiou";
    private static int contadorLugares=0;
    private static int grupoActual=0;
    private static int contador=0;
    private Random r = new Random();

    final int id;
    final String nombre;
    final Grupo grupo;

    public Lugar() {
        this.id = ++contador;
        this.nombre = generaNombre();
        this.grupo = AsignaGrupo();
    }

    private String generaNombre(){
        final StringBuilder sb = new StringBuilder();
        sb.append(con.charAt(r.nextInt(con.length())));
        sb.append(voc.charAt(r.nextInt(voc.length())));
        sb.append(con.charAt(r.nextInt(con.length())));
        sb.append(voc.charAt(r.nextInt(voc.length())));
        sb.append(con.charAt(r.nextInt(con.length())));
        sb.append(voc.charAt(r.nextInt(voc.length())));
        return sb.substring(0, 1).toUpperCase() + sb.substring(1);
    }

    private Grupo AsignaGrupo() {
        if(contadorLugares < Grupo.values()[grupoActual].maxLugares){
            contadorLugares++;
            return Grupo.values()[grupoActual];
        }else{
            contadorLugares=1;
            return Grupo.values()[++grupoActual];
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(nombre);
        sb.append(" (").append(grupo.nombre);
        sb.append(')');
        return sb.toString();
    }

    @Override
    public int compareTo(Object o) {
        Lugar otro = (Lugar) o;
        return this.id-otro.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lugar lugar = (Lugar) o;
        return id == lugar.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
