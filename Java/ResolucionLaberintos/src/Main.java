import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static int ALTO;
    static int ANCHO;


    public static void main(String[] args) {
        pideTamanyo();//Solicita al usuario el tamaño del laberinto
        int[][] laberinto = generaLaberinto();//Genera un laberinto válido y lo almacena
        mostrarLaberinto(laberinto);//Muestra al usuario el laberinto
        mostrarCaminos(calcularCaminos(laberinto));//Muestra al usuario los posibles caminos
    }

    private static void pideTamanyo() {
        Scanner sc = new Scanner(System.in);

        System.out.println("DIMENSIONES DEL LABERINTO       Min. 4, Max. recomendado 20");
        System.out.print("Introduce el ALTO del laberinto: ");
        while(!sc.hasNextInt()) sc.next();
        ALTO=sc.nextInt();
        System.out.print("Introduce el ANCHO del laberinto: ");
        while(!sc.hasNextInt()) sc.next();
        ANCHO=sc.nextInt();

        if(ALTO<4) ALTO=4;
        if(ANCHO<4) ANCHO=4;
    }

    private static int[][] generaLaberinto() {
        int[][] laberinto;
        do{
            laberinto = crearLaberinto();
        }while (!comprobarLaberinto(laberinto));
        return laberinto;
    }

    private static int[][] crearLaberinto() {
        Random r = new Random();
        int[][] nuevo = new int[ALTO][ANCHO];

        for(int i = 0 ; i < ALTO ; i++){
            for (int j = 0 ; j < ANCHO ; j++){
                nuevo[i][j]=r.nextInt(2);
                //La entrada y la salida del laberinto marcadas como camino
                if(i==0 && j==0){
                    nuevo[i][j]=0;
                }
                if(i==ALTO-1 && j==ANCHO-1){
                    nuevo[i][j]=0;
                }
            }
        }
        return nuevo;
    }

    private static boolean comprobarLaberinto(int[][] laberinto) {
        return !calcularCaminos(laberinto).isEmpty();
    }

    private static ArrayList<String> calcularCaminos(int[][] laberinto) {
        ArrayList<String> caminos = new ArrayList<>();
        buscarCaminos(laberinto, 0, 0, "", caminos);
        return caminos;
    }

    private static void buscarCaminos(int[][] laberinto, int x, int y, String camino, ArrayList<String> caminos) {
        if (x == ALTO - 1 && y == ANCHO - 1) { //Si llego a la salida, guardo el camino completo
            caminos.add(camino + "(" + x + "," + y + ")");
        }

        //Marco la casilla como visitada
        laberinto[x][y] = 2;

        //Para cada movimiento, compruebo si es válido (No exceda de los límites del array, no haya pared, y no acabe de pasar)
        //Me ayudo de la recursividad para comprobar todos los caminos de una pasada, y no estar recurriendo a bucles
        if (esValido(x + 1, y, laberinto)) {
            buscarCaminos(laberinto, x + 1, y, camino + "(" + x + "," + y + ") ", caminos);
        }
        if (esValido(x, y + 1, laberinto)) {
            buscarCaminos(laberinto, x, y + 1, camino + "(" + x + "," + y + ") ", caminos);
        }
        if (esValido(x - 1, y, laberinto)) {
            buscarCaminos(laberinto, x - 1, y, camino + "(" + x + "," + y + ") ", caminos);
        }
        if (esValido(x, y - 1, laberinto)) {
            buscarCaminos(laberinto, x, y - 1, camino + "(" + x + "," + y + ") ", caminos);
        }

        //Vuelvo a marcarla como camino libre
        laberinto[x][y] = 0;
    }

    private static boolean esValido(int x, int y, int[][] laberinto) {
        return x >= 0 && x < ALTO && y >= 0 && y < ANCHO && laberinto[x][y] == 0;
    }

    private static void mostrarLaberinto(int[][] laberinto) {
        System.out.print("===");
        for (int x = 0; x < ANCHO; x++) System.out.print("=====");
        System.out.println();
        System.out.print("   ");
        for (int x = 0; x < ANCHO; x++){
            if(x<10){
                System.out.print("  "+x+"  ");
            }else {
                System.out.print("  "+x+" ");
            }
        }
        System.out.println();
        for (int i = 0; i < ALTO; i++) {
            System.out.print("   ");
            for (int x = 0; x < ANCHO; x++) System.out.print("-----");
            System.out.println();
            if(i<10){
                System.out.print(i+"  ");
            }else {
                System.out.print(i+" ");
            }
            for (int j = 0; j < ANCHO; j++) {

                if (i == 0 && j == 0) {
                    System.out.print("> ");
                } else {
                    System.out.print("| ");
                }

                if (laberinto[i][j] == 0) {
                    System.out.print(" ");
                } else {
                    System.out.print("X");
                }

                if (i == ALTO - 1 && j == ANCHO - 1) {
                    System.out.print(" >");
                } else {
                    System.out.print(" |");
                }
            }
            System.out.println();
        }
        for (int x = 0; x < ANCHO; x++) System.out.print("=====");
        System.out.print("===");
        System.out.println();
    }

    private static void mostrarCaminos(ArrayList<String> caminos) {
        Scanner sc = new Scanner(System.in);
        int longitudCortos=caminos.get(0).length();
        int pasos=0;
        if(caminos.size()<2000){
            System.out.println("\nPosible(s) camino(s): ");
            for (int i = 0 ; i < caminos.size() ; i++){
                pasos=0;
                for(int j = 0 ; j < caminos.get(i).length() ; j++) if(caminos.get(i).charAt(j)=='(') pasos++;
                System.out.println("Camino nº"+(i+1)+": "+caminos.get(i)+" | "+pasos+" pasos");
                if(caminos.get(i).length()<longitudCortos) longitudCortos=caminos.get(i).length();
            }
        }else {
            System.out.println("\n\n¿Quieres mostrar los "+caminos.size()+" caminos posibles? (S/N)\n *No recomendado\n *Se mostrarán los más cortos de todas formas");
            if(sc.nextLine().charAt(0)=='S'){
                System.out.println("\nPosible(s) camino(s): ");
                for (int i = 0 ; i < caminos.size() ; i++){
                    pasos=0;
                    for(int j = 0 ; j < caminos.get(i).length() ; j++) if(caminos.get(i).charAt(j)=='(') pasos++;
                    System.out.println("Camino nº"+(i+1)+": "+caminos.get(i)+" | "+pasos+" pasos");
                    if(caminos.get(i).length()<longitudCortos) longitudCortos=caminos.get(i).length();
                }
            }else{
                System.out.println("\nOmitidos "+caminos.size()+" caminos");
            }
        }

        System.out.println("\nCamino(s) más corto(s):");
        for (int i = 0 ; i < caminos.size() ; i++){
            if(caminos.get(i).length()==longitudCortos){
                pasos=0;
                for(int j = 0 ; j < caminos.get(i).length() ; j++) if(caminos.get(i).charAt(j)=='(') pasos++;
                System.out.println("Camino nº"+(i+1)+": "+caminos.get(i)+" | "+pasos+" pasos");
            }
        }
    }
}