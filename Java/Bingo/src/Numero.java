import java.util.ArrayList;

public class Numero {
    public static ArrayList<Integer> numerosNoDisponibles = new ArrayList<>();

    public int numero;
    public boolean marcado;

    public Numero(int numero) {
        this.numero = numero;
        this.marcado = false;
        numerosNoDisponibles.add(numero);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(String.valueOf(numero));
        if(numero>9){
            if(marcado){
                sb.append("*");
            }else {
                sb.append(" ");
            }
        }else{
            if(marcado){
                sb.append("* ");
            }else {
                sb.append("  ");
            }
        }
        sb.append(" ");

        return sb.toString();
    }
}
