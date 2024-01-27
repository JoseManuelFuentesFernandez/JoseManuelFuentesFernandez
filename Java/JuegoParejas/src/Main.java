import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    final static int LIMITE_TURNOS=12;
    final static int ANCHO = 4;
    final static int ALTO = 4;

    static Ficha[][] tablero = new Ficha[ANCHO][ALTO];


    public static void main(String[] args) {
        generarTablero();
        jugar();
    }

    private static void generarTablero() {
        Random r = new Random();
        ArrayList<Character> letras = new ArrayList<>();
        letras = inicializaLetras(letras);//Nos inicializa el ArrayList para poder sacar letras en cuanto las asignemos

        for(int i = 0 ; i < ANCHO; i++){
            for(int j = 0 ; j < ALTO ; j++){
                do {//Con este bucle nos aseguramos que la casilla quede asignada sí o sí
                    if(tablero[i][j]==null){//Si la casilla es nula es porque no tiene aún ninguna ficha
                        tablero[i][j] = new Ficha(letras.remove(r.nextInt(letras.size())));//Insertamos una letra al azar y la sacamos del ArrayList
                        break;//Salimos del bucle con la casilla asignada
                    }
                }while (true);
            }
        }
    }

    private static ArrayList<Character> inicializaLetras(ArrayList<Character> letras) {
        String l = "AABBCCDDEEFFGGHH";//Las letras de las fichas
        for(int i = 0; i < l.length() ; i++){
            letras.add(l.charAt(i));//Va insertando en el ArrayList las letras para luego procesar la generación del tablero
        }
        return letras;//Devuelve el ArrayList ya inicializado
    }

    private static void jugar() {
        int turno = 0;

        do{
            System.out.println("\n===TURNO "+(++turno)+"/"+LIMITE_TURNOS+"===");//Incrementa el turno, aparte de mostrarlo

            mostrarTablero();

            //Pide al usuario qué fichas vamos a descubrir y las guarda
            Ficha primero = pedirAccion(1);
            Ficha segundo = pedirAccion(2);

            //Comprueba si sus letras son iguales
            comprobarIgualdad(primero,segundo);

            //Si hemos ganado, sale del juego
            if(comprobarFin()){
                System.out.println("ENHORABUENA: HAS GANADO");
                break;
            }
        }while (turno<LIMITE_TURNOS);
        System.out.println("FIN DE LA PARTIDA");
    }

    private static void mostrarTablero() {
        System.out.printf("   ");
        for(int i = 0 ; i < ALTO ; i++) System.out.print((i+1)+" ");//Pinta una guía del 1-4 para ayudar horizontalmente con la entrada de coordenadas

        System.out.println();
        for(int i = 0 ; i < ANCHO; i++){
            System.out.print((i+1)+"  ");//Pinta una guía del 1-4 para ayudar verticalmente con la entrada de coordenadas
            for(int j = 0 ; j < ALTO ; j++){
                System.out.print(tablero[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static Ficha pedirAccion(Integer movimiento) {
        Scanner sc = new Scanner(System.in);
        String entrada;
        int x = -1;
        int y = -1;

        //Bucle para controlar errores en la interacción con el usuario
        do {
            //Guardo en un string las coordenadas en forma de x,y
            System.out.print(movimiento + "/2 - Indica qué posición quieres revelar (x,y): ");
            entrada = sc.nextLine();

            //Control de errores de entrada (tamaño del string de entrada y si las coordenadas son valores numéricos)
            if(entrada.length()==3 && Character.isDigit(entrada.charAt(0)) && Character.isDigit(entrada.charAt(2))) {
                //Asigno en cada variable su respectivo valor como entero
                x = Integer.parseInt(String.valueOf(entrada.charAt(0))) - 1;
                y = Integer.parseInt(String.valueOf(entrada.charAt(2))) - 1;
            }

            //Control de errores de límites
            if((x>=0 && x<4) && (y>=0 && y<4)) {
                break;
            }else {
                System.out.println("ERROR: introduce unas coordenadas válidas");
            }
        }while (true);

        //La marco como descubierta temporalmente para poder visualizarla
        tablero[x][y].setDescubierta(true);
        mostrarTablero();

        //Devuelvo esa misma Ficha para hacer comprobaciones después
        return tablero[x][y];
    }

    private static void comprobarIgualdad(Ficha primero, Ficha segundo) {
        if(primero.getLetra()== segundo.getLetra()){//Si las letras coinciden, se marcan como acertadas
            primero.setAcertada(true);
            segundo.setAcertada(true);
            System.out.println("Eres un crack :)");
        }else {
            //Si no, se vuelven a esconder
            primero.setDescubierta(false);
            segundo.setDescubierta(false);
            System.out.println("Una lástima... :(");
        }
    }

    private static boolean comprobarFin() {
        //Recorre la matriz y en el caso de que se encuentre una Ficha que no esté descubierta sale devolviendo False
        for(int i = 0 ; i < ANCHO; i++){
            for(int j = 0 ; j < ALTO ; j++){
                if(!tablero[i][j].isAcertada()) return false;
            }
        }
        //Si llega aquí significa que ha recorrido la matriz y todas están descubiertas
        return true;
    }
}