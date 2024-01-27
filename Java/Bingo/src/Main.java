import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static Random r = new Random();
    static Scanner sc = new Scanner(System.in);

    static Jugador jugador;
    static Jugador maquina;
    static ArrayList<Jugador> jugadores = new ArrayList<>();
    static int num; //Variable reutilizable auxiliar
    static ArrayList<Integer> numerosGenerados = new ArrayList<>();


    public static void main(String[] args) {
        preparaJuego();
        jugar();
    }

    private static void preparaJuego() {
        //Preparación de los jugadores
            //Nombre y creación del jugador físico
            System.out.print("Introduce tu nombre: ");
            String nombre;
            do {
                nombre=sc.nextLine();
            }while (nombre.isEmpty());
            jugador=new Jugador(nombre.substring(0,Math.min(nombre.length(),10)));

            //Creación del jugador máquina
            maquina=new Jugador("Anuel");

            //Inclusión en el ArrayList jugadores
            jugadores.add(jugador);
            jugadores.add(maquina);

        //Preparación del tablero de cada jugador
            for (Jugador j : jugadores) preparaTablero(j);
    }

        private static void preparaTablero(Jugador player) {//BIEN
        for (int i = 0; i < 5 ; i++){
            for (int j = 0; j < 5 ; j++){
                do{
                    num=r.nextInt(GrupoLetras.B.numFinal)+GrupoLetras.values()[i].numInicio;
                }while (Numero.numerosNoDisponibles.contains(num));
                player.numeros[j][i]=new Numero(num);
            }
        }
    }


    private static void jugar() {
        do {
            num=generaNumero();

            //Comprobar el número generado con ambos jugadores
            for (Jugador j : jugadores){
                comprobarNumero(j);
            }

            //Comprobar si tienen Bingo
            for (Jugador j : jugadores){
                comprobarBingo(j);
            }

            mostrarRonda();

            System.out.println("Intro para continuar: ");
            sc.nextLine();
        }while (!jugador.bingo && !maquina.bingo);

        if(jugador.bingo){
            System.out.println("\n\n¡¡¡BINGO!!! "+jugador.nombre+" ha ganado");
        }else {
            System.out.println("\n\n¡¡¡BINGO!!!"+maquina.nombre+" ha ganado");
        }


    }

        private static int generaNumero() {
        int x;

        do {
            x = r.nextInt(GrupoLetras.values()[(GrupoLetras.values().length)-1].numFinal);
        }while (numerosGenerados.contains(x));

        numerosGenerados.add(x);
        System.out.print("NUMERO GENERADO: -");
        for(int i = 0 ; i < GrupoLetras.values().length ; i++){
            if(x<GrupoLetras.values()[i].numFinal){
                if(i==0){
                    System.out.print(GrupoLetras.values()[i]);
                    break;
                }else {
                    if(i==GrupoLetras.values().length-1){
                        System.out.print(GrupoLetras.values()[i]);
                    }else if(x<GrupoLetras.values()[i].numFinal){
                        System.out.print(GrupoLetras.values()[i]);
                        break;
                    }

                }
            }
        }
        System.out.println(" "+x+"-   ("+(GrupoLetras.values()[(GrupoLetras.values().length)-1].numFinal-numerosGenerados.size())+" números restantes)");

        return x;
    }

        private static void comprobarNumero(Jugador player) {
        for (int i = 0; i < 5 ; i++){
            for (int j = 0; j < 5 ; j++){
                if(player.numeros[i][j].numero==num){
                    player.numeros[i][j].marcado=true;
                }
            }
        }
    }

        private static void comprobarBingo(Jugador player) {
        boolean racha = false;

        //Comprobación horizontal
        for (int i = 0 ; i < 5 ; i++){
            if(player.numeros[0][i].marcado && player.numeros[1][i].marcado && player.numeros[2][i].marcado && player.numeros[3][i].marcado && player.numeros[4][i].marcado){
                racha=true;
                break;
            }
        }
        if(racha) {
            player.bingo=true;
            return;
        }

        //Comprobación vertical
        for (int i = 0 ; i < 5 ; i++){
            if(player.numeros[i][0].marcado && player.numeros[i][1].marcado && player.numeros[i][2].marcado && player.numeros[i][3].marcado && player.numeros[i][4].marcado){
                racha=true;
                break;
            }
        }
        if(racha) {
            player.bingo=true;
            return;
        }

        //Comprobación diagonal
        if(player.numeros[0][0].marcado && player.numeros[1][1].marcado && player.numeros[2][2].marcado && player.numeros[3][3].marcado && player.numeros[4][4].marcado){
            racha=true;
        }
        if(player.numeros[4][0].marcado && player.numeros[3][1].marcado && player.numeros[2][2].marcado && player.numeros[1][3].marcado && player.numeros[0][4].marcado){
            racha=true;
        }
        if(racha) {
            player.bingo=true;
        }
    }

        private static void mostrarRonda(){
        maquina.mostrarTablero();
        jugador.mostrarTablero();
    }
}