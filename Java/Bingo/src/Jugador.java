import java.util.ArrayList;

public class Jugador {
    public final String nombre;
    public Numero[][] numeros;
    public boolean bingo;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.numeros = new Numero[5][5];
        this.bingo = false;
    }

    public void mostrarTablero(){
        System.out.println("Tablero de "+nombre);

        for (int x = 0 ; x < GrupoLetras.values().length ; x++) System.out.print(GrupoLetras.values()[x]+"   ");
        System.out.println();

        for (int i = 0; i < 5 ; i++){
            for (int j = 0; j < 5 ; j++){
                System.out.print(numeros[i][j]);
            }
            System.out.println();
        }

        System.out.println();
    }
}
