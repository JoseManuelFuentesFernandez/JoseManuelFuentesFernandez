import java.io.Serializable;

public class Dato implements Comparable<Dato>, Serializable, Encode {
    private int id;
    private String sitioWeb;
    private String usuario;
    private String contrasena;

    public Dato(int id, String sitioWeb, String usuario, String contrasena) {
        this.id = id;
        this.sitioWeb = sitioWeb;
        this.usuario = usuario;
        this.contrasena = Encode.codificar(contrasena);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("(");
        sb.append(id).append(") ").append(sitioWeb).append(": ").append(usuario).append(" - ' ").append(Encode.descodificar(contrasena)).append(" '");
        return sb.toString();
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Dato o) {
        return this.id-o.id;
    }
}
