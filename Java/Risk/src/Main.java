import java.util.*;

public class Main {
    static int total_jugadores;//@modificable @test -> Preparado para tener la mitad de jugadores que de lugares (PROVISIONAL)

    static Scanner sc = new Scanner(System.in);
    static Random r = new Random();

    static ArrayList<Lugar> lugares = new ArrayList<>();
    static ArrayList<Jugador> jugadores = new ArrayList<>();
    static Map<Lugar,Map<Jugador,Integer>> partida = new LinkedHashMap<>();
    static ArrayList<Lugar> listaLugares = new ArrayList<>();

    static int muertes = 0;


    public static void main(String[] args) {
        generaMapa();
        pideJugadores();
        inicializaPartida();
        jugarPartida();
    }


    private static void generaMapa() {
        for (int i=0;i<Grupo.values().length;i++){
            for (int j=0;j<Grupo.values()[i].maxLugares;j++){
                lugares.add(new Lugar());
                total_jugadores++;
            }
        }
        total_jugadores/=2;
    }

    private static void pideJugadores() {
        //
        /*do {
            System.out.println("¿Cuántos jugadores? (Máx:"+maxJugadores+" | Min:"+minJugadores+")");
            while (!sc.hasNextInt())sc.nextLine();
            cantJugadores=sc.nextInt();
            sc.nextLine();

            if(cantJugadores<=maxJugadores && cantJugadores>=minJugadores){
                break;
            }
        }while (true);*/

        String siglasJ;
        for (int i = 0; i< total_jugadores; i++){
            System.out.printf("Introduce las siglas (XX) del jugador "+(i+1)+"/"+ total_jugadores+": ");
            siglasJ=sc.nextLine();
            if(siglasJ.length()<2) siglasJ = "J"+(i+1);
            jugadores.add(new Jugador(siglasJ.substring(0,2).toUpperCase()));
        }
        System.out.println();
    }

    private static void inicializaPartida() {
        int tropas;
        Lugar lugar;
        for (Jugador j : jugadores){
            for (int i=0; i<2;i++){ // *
                lugar = lugares.remove(r.nextInt(lugares.size()));
                partida.put(lugar, new HashMap<>());

                tropas = r.nextInt(Jugador.tropasIniciales-2)+1; // Entre 1 y tropasIniciales-1
                if(j.tropasTot==Jugador.tropasIniciales){
                    partida.get(lugar).put(j,tropas);
                }else{
                    partida.get(lugar).put(j,j.tropasTot);
                }
                j.tropasTot-=tropas;
            }
        }

        for (Jugador j : jugadores) j.tropasTot = Jugador.tropasIniciales;
    }

    private static void jugarPartida() {
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ PARTIDA INICIADA /////////////////////////////////////////////////////////////////");

        int ronda=0;
        int opcion;
        do {//Bucle RONDAS
            System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< RONDA "+(++ronda)+" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            for (Jugador j : jugadores) {//Bucle (CADA JUGADOR)
                if(muertes>total_jugadores-2) break;
                if(j.tropasTot>0){
                    System.out.println("============== Turno de ("+j.id+") "+j.nombre+" ==============");
                    mostrarJugadores();
                    mostrarTablero();
                    System.out.println("========== Selecciona una acción ==========");
                    opcion = pideOpcion();
                    switch (opcion) {
                        case 1 -> {
                            atacar(j);
                        }
                        case 2 -> {
                            moverTropas(j);
                        }
                        case 3 -> {
                            rendirse(j);
                        }
                    }
                }
            }
            actualizaJugadores();
            System.out.println();
            if(jugadores.size()<=1){
                System.out.println("--------------------------------------------------------- FIN DE LA PARTIDA -------------------------------------------------------------");
                if(jugadores.size()==1){
                    System.out.println("¡Enhorabuena ("+jugadores.get(0).id+") "+jugadores.get(0).nombre+"! ¡Has ganado!");
                } else{
                    System.out.println("La guerra no cambia nunca... ¡Nadie gana!");
                }
            }
        }while (jugadores.size()>1);

        System.out.println("\n                                                             MAPA FINAL");
        mostrarTablero();
    }

    private static void mostrarJugadores() {
        System.out.println("--------------JUGADORES--------------");
        for (Jugador j : jugadores){
            System.out.println(j);
        }
    }

    private static void mostrarTablero() {
        System.out.println("----------------------------------------------------------------MAPA-----------------------------------------------------------------------");
        /*for (Lugar l : partida.keySet()){
            System.out.printf(l+" { ");
            for (Jugador j : partida.get(l).keySet()){
                System.out.printf("("+j.id+") "+j.nombre+" - "+partida.get(l).get(j)+" ud");
                if(partida.get(l).get(j)!=1) System.out.printf("s");
            }
            System.out.println(" }\n");
        }*/
        int i=0;
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
        for (Lugar l : partida.keySet()){
            System.out.printf("| "+l);
            for (Jugador j : partida.get(l).keySet()){
                System.out.printf("("+j.id+") "+j.nombre+" - "+partida.get(l).get(j)+" ud");
                if(partida.get(l).get(j)!=1) System.out.print("s");
            }
            System.out.print(" |");
            i++;
            if(i%4==0){
                if(i< partida.size()){
                    System.out.print("\n///////////////////|  |///////////////////////////////|  |///////////////////////////////|  |///////////////////////////////|  |///////////////////\n");
                }else{
                    System.out.println();
                }
            }
        }

        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------");
    }

    private static int pideOpcion() {
        int opcion;

        System.out.print("  1) Atacar\n  2) Mover tropas\n  3) Rendirse\n   > ");
        do {
            while (!sc.hasNextInt()) sc.next();
            opcion = sc.nextInt();
            sc.nextLine();
        }while (opcion>3||opcion<1);

        return opcion;
    }

    private static void atacar(Jugador j) {
        Lugar[] lugares;
        lugares=partida.keySet().toArray(new Lugar[0]);
        Lugar lugarOrigen,lugarDestino;
        int seleccion;
        int i=0;


        System.out.println("--------------ATAQUE----------------\nSelecciona el lugar desde el que quieres atacar:");
        for(Lugar l : partida.keySet()){
            if(partida.get(l).containsKey(j)){
                System.out.println(" "+(++i)+") "+l);//Comprobación -> el origen debe ser del territorio del jugador
                listaLugares.add(l);
            }
        }
        System.out.print("  > ");
        do {
            while (!sc.hasNextInt()) sc.next();
            seleccion = sc.nextInt()-1;
            sc.nextLine();
        }while (seleccion>=listaLugares.size()||seleccion<0);//
        lugarOrigen=listaLugares.get(seleccion);
        listaLugares.clear();

        i=0;
        seleccion=0;
        System.out.println("Selecciona el lugar que quieres atacar:");
        for (int c = 0; c < lugares.length; c++){
            if(lugares[c].equals(lugarOrigen)){
                if(c-1>=0){
                    listaLugares.add(lugares[c-1]);
                }
                if(c+1<lugares.length) {
                    listaLugares.add(lugares[c+1]);
                }
                if(c-4>=0) {
                    listaLugares.add(lugares[c-4]);
                }
                if(c+4<lugares.length){
                    listaLugares.add(lugares[c+4]);
                }
                break;
            }
        }
        listaLugares.removeIf(l -> partida.get(l).containsKey(j));
        for(Lugar l : listaLugares){
            System.out.printf(" "+(++i)+") "+l+" ");
            for(Jugador infoJ : partida.get(l).keySet()){
                System.out.printf(" [("+infoJ.id+") "+infoJ.nombre+" | "+partida.get(l).get(infoJ)+" ud");
                if(partida.get(l).get(infoJ)!=1)System.out.print("s");
                System.out.println("]");
            }
        }
        System.out.printf("  > ");
        do {
            while (!sc.hasNextInt()) sc.next();
            seleccion = sc.nextInt()-1;
            sc.nextLine();
        }while (seleccion>=listaLugares.size()||seleccion<0);
        lugarDestino=listaLugares.get(seleccion);
        listaLugares.clear();

        //TODO la acción de atacar (calcular si el ataque ha sido exitoso o fallido, calcular pérdida de tropas y cambiar o no el propietario del lugar)


            //TODO AÑADIR COMPROBACIÓN POR SI EL JUGADOR DEL DESTINO HA MUERTO

    }

    private static void moverTropas(Jugador j) {

    }

    private static void rendirse(Jugador j) {
        for(Lugar l : partida.keySet()) {
            if (partida.get(l).containsKey(j)){
                partida.get(l).clear();
                partida.get(l).put(j, 0);
            }
        }
        morir(j);
        System.out.println("El jugador ("+j.id+") "+j.nombre+" se ha rendido, ahora sus territorios no opondrán resistencia.");
    }

    private static void morir(Jugador j) {
        j.tropasTot=0;
        j.nombre=j.nombre+" x.x";
        muertes++;
    }

    private static void actualizaJugadores() {
        ArrayList<Jugador> aux=new ArrayList<>();
        for(Jugador j:jugadores) {
            if(j.tropasTot>0){
                aux.add(j);
            }
        }
        jugadores.clear();
        jugadores=aux;


        //Suma de tropas al final de ronda
        boolean bonus = false;

        for (Jugador jugador : jugadores) {
            for (Lugar lugar : partida.keySet()) {
                if (partida.get(lugar).containsKey(jugador)) {
                    // Comprobación de cuántos territorios del mismo grupo tiene cada jugador
                    int i = 0;
                    ArrayList<Lugar> checked = new ArrayList<>();

                    for (Lugar l : partida.keySet()) {
                        if (partida.get(l).containsKey(jugador) && l.grupo.equals(lugar.grupo) && !checked.contains(l)) {
                            i++;
                            checked.add(l);

                            if (i >= lugar.grupo.maxLugares) {
                                bonus = true;
                                break;
                            }
                        }
                    }

                    // Si un jugador tiene un grupo entero se le suma el bonus a cada lugar, si no 1 a cada lugar
                    if (bonus) {
                        partida.get(lugar).put(jugador, partida.get(lugar).get(jugador) + lugar.grupo.bonusGrupo);
                        jugador.tropasTot += lugar.grupo.bonusGrupo;
                    } else {
                        partida.get(lugar).put(jugador, partida.get(lugar).get(jugador) + 1);
                        jugador.tropasTot++;
                    }

                    // Reiniciar bonus a false para el siguiente jugador
                    bonus = false;
                }
            }
        }
    }
}

