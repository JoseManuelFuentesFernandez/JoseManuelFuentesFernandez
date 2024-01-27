import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    final static int N_ESTACIONES = 5;
    static String[] estaciones = new String[N_ESTACIONES];
    static boolean[][] mapaEstaciones = new boolean[N_ESTACIONES][N_ESTACIONES];
    static int[][] tiempoEstaciones = new int[N_ESTACIONES][N_ESTACIONES];

    static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        inicializarEstaciones();
        pedirRuta();
    }

    private static void inicializarEstaciones() {
        pedirNombreEstaciones();
        pedirMapaEstaciones();
    }

    private static void pedirNombreEstaciones() {
        for (int i = 0 ; i < N_ESTACIONES ; i++){
            System.out.print("Introduce el nombre de la estación nº"+(i+1)+": ");
            estaciones[i]=scanner.nextLine();
            if(estaciones[i].isEmpty() || estaciones[i].isBlank() || buscarIdPorNombre(estaciones[i])!=i){
                System.out.println("ERROR: Nombre no válido");
                i--;
            }
        }
    }

    private static void pedirMapaEstaciones() {
        String e1,e2;

        System.out.println("================================================");
        while (true){
            mostrarNombreEstaciones();
            mostrarConexionEstaciones();
            System.out.println("\nAñade una conexión UNIDIRECCIONAL entre dos estaciones: (0 para salir)");
            System.out.print("Nombre estación 1 (Salida): ");
            e1= scanner.nextLine();
            System.out.print("Nombre estación 2 (Destino): ");
            e2= scanner.nextLine();

            if(e1.equals("0") || e2.equals("0")) break;

            if(buscarIdPorNombre(e1)!=-1 && buscarIdPorNombre(e2)!=-1){
                if(!mapaEstaciones[buscarIdPorNombre(e1)][buscarIdPorNombre(e2)]){
                    mapaEstaciones[buscarIdPorNombre(e1)][buscarIdPorNombre(e2)]=true;
                    pedirTiempoEstaciones(e1,e2);
                }else{
                    System.out.println("ERROR: Esa ruta ya existe");
                }
            }else{
                if(buscarIdPorNombre(e1)==-1){
                    System.out.println("ERROR: "+e1+" no es una estación");
                }
                if(buscarIdPorNombre(e2)==-1){
                    System.out.println("ERROR: "+e2+" no es una estación");
                }
                System.out.println("No se ha podido establecer una conexión");
            }
        }
    }

    private static void pedirTiempoEstaciones(String e1, String e2) {
        System.out.print("Indica la duración del viaje de "+e1+" a "+e2+" (minutos): ");
        while(!scanner.hasNextInt()) scanner.next();
        tiempoEstaciones[buscarIdPorNombre(e1)][buscarIdPorNombre(e2)]=Integer.parseInt(scanner.nextLine());
    }

    private static void mostrarNombreEstaciones() {
        System.out.println("===========");
        System.out.println("ESTACIONES:");
        for (int i = 0 ; i < N_ESTACIONES ; i++){
            System.out.println(" Nº"+(i+1)+": "+estaciones[i]);
        }
    }

    private static void mostrarConexionEstaciones() {
        System.out.println("==========================");
        System.out.println("CONEXIÓN ENTRE ESTACIONES:");
        for (int i = 0 ; i < N_ESTACIONES ; i++){
            for (int j = 0 ; j < N_ESTACIONES ; j++){
                if(mapaEstaciones[i][j]) System.out.println(" "+estaciones[i]+" --> "+estaciones[j]+" ("+tiempoEstaciones[i][j]+" mins)");
            }
        }
        System.out.println("==========================");
    }

    private static void pedirRuta() {
        String salida,destino;

        System.out.println("\n========================================");
        while(true){
            mostrarNombreEstaciones();
            mostrarConexionEstaciones();
            System.out.println();

            System.out.print("Introduce el nombre de la estación de SALIDA (0 para salir): ");
            salida = scanner.nextLine();
            System.out.print("Introduce el nombre de la estación de DESTINO (0 para salir): ");
            destino = scanner.nextLine();

            if(salida.equals("0") || destino.equals("0")) break;

            if(buscarIdPorNombre(salida)!=-1 && buscarIdPorNombre(destino)!=-1){
                calcularRuta(salida,destino);
                System.out.println("\nEnter para continuar");
                scanner.nextLine();
            }else{
                if(buscarIdPorNombre(salida)==-1){
                    System.out.println("ERROR: "+salida+" no es una estación");
                }
                if(buscarIdPorNombre(destino)==-1){
                    System.out.println("ERROR: "+destino+" no es una estación");
                }
            }
        }
        System.out.println("Saliendo...");
    }

    private static void calcularRuta(String salida, String destino) {
        System.out.println("Calculando posibles caminos entre "+salida+" y "+destino);
        ArrayList<String> caminos = new ArrayList<>();
        String caminoMasCorto = "";
        String caminoMasRapido = "";
        int menorTiempo=100000;
        int menorParadas=100000;
        int tiempo=0;

        comprobarCamino(destino,salida,caminos,salida,tiempo);

        if(caminos.isEmpty()){
            System.out.println("No hay rutas entre "+salida+" y "+destino);
        }else{
            for (int i = 0 ; i < caminos.size() ; i++){
                int paradas=0;
                tiempo=0;
                int underscore=0;
                for (int j = 0 ; j < caminos.get(i).length() ; j++){
                    if(caminos.get(i).charAt(j)=='>') paradas++;
                    if(caminos.get(i).charAt(j)=='_'){
                        underscore=j;
                        tiempo = Integer.parseInt(caminos.get(i).substring(underscore+1));
                    }
                }
                System.out.println("Camino nº"+(i+1)+": "+caminos.get(i).substring(0,underscore)+" ("+paradas+" paradas, "+tiempo+" minutos)");
                if(paradas<menorParadas){
                    menorParadas=paradas;
                    caminoMasCorto=caminos.get(i).substring(0,underscore)+" ("+paradas+" paradas, "+tiempo+" minutos)";
                }
                if(tiempo<menorTiempo){
                    menorTiempo=tiempo;
                    caminoMasRapido=caminos.get(i).substring(0,underscore)+" ("+paradas+" paradas, "+tiempo+" minutos)";
                }
            }
            System.out.println();
            System.out.println("Camino más CORTO: "+caminoMasCorto);
            System.out.println("Camino más RAPIDO: "+caminoMasRapido);
        }
    }

    private static void comprobarCamino(String destino, String posicionActual, ArrayList<String> caminos,String camino,int tiempo) {
        if(posicionActual.equals(destino)){
            caminos.add(camino+"_"+tiempo);
        }

        for(int i = 0 ; i < N_ESTACIONES ; i++){
            if(mapaEstaciones[buscarIdPorNombre(posicionActual)][i]){
                comprobarCamino(destino,estaciones[i],caminos,camino+" --> "+estaciones[i],tiempo+tiempoEstaciones[buscarIdPorNombre(posicionActual)][i]);
            }
        }

    }

    private static int buscarIdPorNombre(String nombreEstacion){
        for (int i = 0 ; i < N_ESTACIONES ; i++){
            if(estaciones[i].equals(nombreEstacion)){
                return i;
            }
        }
        return -1;
    }
}