#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <time.h>

#define N_FICHAS 2

///////////////REGISTROS///////////////
typedef struct{
  int posicion; // posicion en el vector del tablero (inicial 0)
}ficha;

typedef struct{
  char nombre[20];
  ficha fichas[N_FICHAS];
  char color[9];
}jugador;
///////////////////////////////////////

////////DECLARACION FUNCIONES//////////
int leePartida(FILE* F,char const* FICHERO_PARTIDA,jugador *jugadores,int NUM_JUGADORES);
void guardaPartida(FILE* F,char const* FICHERO_PARTIDA,jugador *jugadores,int NUM_JUGADORES);
void desechaPartida(FILE* F,char const* FICHERO_PARTIDA);
void inicializaTablero(int *tablero, int TAMANO_TABLERO, int CANT_CASILLAS_SEGURAS, int CANT_CASILLAS_FINALES);
void pedirNombreJugador(jugador *usuario);
void inicializaJuego(FILE* F,char const* FICHERO_PARTIDA,int *tablero, int TAMANO_TABLERO, int CANT_CASILLAS_SEGURAS, int CANT_CASILLAS_FINALES,jugador *jugadores,int NUM_JUGADORES);
void mostrarTablero(int *tablero,int TAMANO_TABLERO,jugador *jugadores,int NUM_JUGADORES);
void salir(jugador *j,int i);
void volver(jugador *j,int i);
void mover(jugador *j,int i,int dado);
void comer(jugador *j,jugador *otro,int i,int o);
void llegar(jugador *j,int i);
void pedirAccion(int tablero[],int TAMANO_TABLERO,int NUM_JUGADORES,jugador *j,jugador *otro,int dado);
void calcularAccion(int tablero[],int TAMANO_TABLERO,int NUM_JUGADORES,jugador *j,jugador *otro,int dado);
void tirarDado(int turno,int *dado);
void controlTurno(int *turno, int dado, int *seises, jugador *jugadores, int NUM_JUGADORES);
int comprobarFinal(int TAMANO_TABLERO,jugador *j);

void juego(FILE* F,char const* FICHERO_PARTIDA,int *tablero,int TAMANO_TABLERO,jugador *jugadores,int NUM_JUGADORES);
///////////////////////////////////////

//////////////////MAIN/////////////////
int main(void) {
  srand(time(NULL));
  FILE* F;
  const char FICHERO_PARTIDA[] = "partida.txt"; //Archivo donde se guardará la partida en caso de salir sin acabar
  
  const int TAMANO_TABLERO = 40; //RECOMENDADO Min 20  Max 80
  const int CANT_CASILLAS_SEGURAS = 8; //TAMANO_TABLERO % CANT_CASILLAS_SEGURAS debe ser 0 | recomendado TAMANIO_TABLERO/5
  const int CANT_CASILLAS_FINALES = 4; //NO DEBE SER MAYOR QUE TAMANO_TABLERO | recomendado TAMANIO_TABLERO/10
  const int NUM_JUGADORES = 2;

  int tablero[TAMANO_TABLERO]; // 0-Salida | 1-CasillaNormal | 2-CasillaSegura | 3-Finales
  jugador jugadores[NUM_JUGADORES]; // 0-Usuario | 1-Maquina

  inicializaJuego(F,FICHERO_PARTIDA,tablero,TAMANO_TABLERO,CANT_CASILLAS_SEGURAS,CANT_CASILLAS_FINALES,jugadores,NUM_JUGADORES);
  juego(F,FICHERO_PARTIDA,tablero,TAMANO_TABLERO,jugadores,NUM_JUGADORES);

  return 0;
}
///////////////////////////////////////

int leePartida(FILE* F,char const* FICHERO_PARTIDA,jugador *jugadores,int NUM_JUGADORES){
  F = fopen(FICHERO_PARTIDA,"r");

  for(int j = 0 ; j < NUM_JUGADORES ; j++){
    if(fscanf(F,"%s",jugadores[j].nombre)!=EOF){
      for(int f = 0 ; f < N_FICHAS ; f++){
        fscanf(F,"%d",&jugadores[j].fichas[f].posicion);
      }
      fscanf(F,"%s",jugadores[j].color);
    }else{
      return 0;
    }
  }
  fclose(F);
  return 1;
}

void guardaPartida(FILE* F,char const* FICHERO_PARTIDA,jugador *jugadores,int NUM_JUGADORES){
  F = fopen(FICHERO_PARTIDA,"w");

  for(int j = 0 ; j < NUM_JUGADORES ; j++){
    fprintf(F,"%s\n",jugadores[j].nombre);
    for(int f = 0 ; f < N_FICHAS ; f++){
      fprintf(F,"%d\n",jugadores[j].fichas[f].posicion);
    }
    fprintf(F,"%s\n",jugadores[j].color);
  }
  fclose(F);
}

void desechaPartida(FILE* F,char const* FICHERO_PARTIDA){
  F = fopen(FICHERO_PARTIDA,"w");
  fprintf(F,"");
  fclose(F);
}

void inicializaTablero(int *tablero, int TAMANO_TABLERO, int CANT_CASILLAS_SEGURAS, int CANT_CASILLAS_FINALES){
  int intervalo=TAMANO_TABLERO/CANT_CASILLAS_SEGURAS; //Cada cuantas casillas habra una segura
  int aux=1; //Para llevar el recuento de las casillas seguras

  // 0-Salida | 1-CasillaNormal | 2-CasillaSegura | 3-Finales
  for(int i = 1 ; i < TAMANO_TABLERO ; i++){
    if(TAMANO_TABLERO-i<=CANT_CASILLAS_FINALES){
      tablero[i]=3;
    }else if(i==aux*intervalo){
      aux++;
      tablero[i]=2;
    }else{
      tablero[i]=1;
    }
  }
  tablero[0]=0; //Salida
  tablero[5]=2; //Casilla segura donde salen las fichas
}

void pedirNombreJugador(jugador *usuario){
  char intro;
  
  printf("Introduce tu nombre: ");
  scanf("%s%c",usuario->nombre,&intro);
}

void inicializaJugadores(jugador *jugadores,int NUM_JUGADORES){
  //Asignacion de nombres
  pedirNombreJugador(&jugadores[0]);
  strcpy(jugadores[1].nombre, "Maquina");
  
  //Asignacion de colores
  strcpy(jugadores[0].color, "\x1b[33m");
  strcpy(jugadores[1].color, "\x1b[35m");

  //Inicializacion de fichas
  for(int i = 0 ; i < NUM_JUGADORES ; i++){
    for(int j = 0 ; j < N_FICHAS ; j++){
      jugadores[i].fichas[j].posicion=0;
    }
  }
}

void inicializaJuego(FILE* F,char const* FICHERO_PARTIDA,int *tablero, int TAMANO_TABLERO, int CANT_CASILLAS_SEGURAS, int CANT_CASILLAS_FINALES,jugador *jugadores,int NUM_JUGADORES){
  inicializaTablero(tablero, TAMANO_TABLERO, CANT_CASILLAS_SEGURAS, CANT_CASILLAS_FINALES);//Inicializa el tablero

  if(leePartida(F,FICHERO_PARTIDA,jugadores,NUM_JUGADORES)==1){//Lee el archivo por si hay partidas guardadas
    char intro,opcion;
    printf("Hay una partida guardada de %s%s\x1b[0m, ¿Quieres continuarla? (S/N): ",jugadores[0].color,jugadores[0].nombre);
    scanf("%c%c",&opcion,&intro);
    if(opcion=='s' || opcion=='S') return; //Si elige continuarla, ya la ha leido, asi que lanzamos return para no inicializarla
  }

  inicializaJugadores(jugadores,NUM_JUGADORES);//Inicializa los jugadores, si no habia partida o si se ha elegido no continuarla
}

void mostrarTablero(int *tablero,int TAMANO_TABLERO,jugador *jugadores,int NUM_JUGADORES){
  ///////////COLORES/////////
  char color_normal[9] = "\x1b[0m";
  char color_salida[9] = "\x1b[44m";
  char color_casilla[9] = "\x1b[46m";
  char color_segura[9] = "\x1b[42m";
  char color_fin[9] = "\x1b[41m";
  char color_ficha[9] = "\x1b[30m";
  ///////////////////////////
  printf("\n\n");
  for(int j = 0 ; j < NUM_JUGADORES ; j++){
    for(int f = 0 ; f < N_FICHAS ; f++){
      printf("%sF%d%s ",color_ficha,f+1,color_normal);
      for(int i = 0 ; i < TAMANO_TABLERO ; i++){
        switch(tablero[i]){ // Determina el color de la casilla
          case 0:{
            printf("%s",color_salida);
            break;
          }
          case 1:{
            printf("%s",color_casilla);
            break;
          }
          case 2:{
            printf("%s",color_segura);
            break;
          }
          case 3:{
            printf("%s",color_fin);
            break;
          }
        }
        
        if(jugadores[j].fichas[f].posicion==i){
          printf("%s☻%s",jugadores[j].color,color_normal); // Casilla con ficha del jugador con su color
        }else{
          printf(" %s",color_normal); // Casilla normal
        }
      }
      printf("\n");
    }
    printf("%s%s%s\n\n",jugadores[j].color,jugadores[j].nombre,color_normal); // Muestra el nombre del jugador
  }
}

void salir(jugador *j,int i){
  printf("\n%s-F%d salió",j->nombre,i+1);
}

void volver(jugador *j,int i){
  printf("\n%s-F%d volvió a la casilla de salida",j->nombre,i+1);
  j->fichas[i].posicion=0;
}

void mover(jugador *j,int i,int dado){
  printf("\n%s-F%d movió de %d a %d",j->nombre,i+1,j->fichas[i].posicion,(j->fichas[i].posicion+=dado));
}

void comer(jugador *j,jugador *otro,int i,int o){
  printf("\n%s-F%d se ha comido a %s-F%d",j->nombre,i+1,otro->nombre,o+1);
}

void llegar(jugador *j,int i){
  printf("\n%s-F%d ha llegado al final",j->nombre,i+1);
}

void pedirAccion(int tablero[],int TAMANO_TABLERO,int NUM_JUGADORES,jugador *j,jugador *otro,int dado){
  char intro;
  int sumaLlegada=5,sumaComida=10;
  int fichaElegida;
  int posibleMovimiento=0,salida=0,comp=0;
  
  // Comprobación de posibles movimientos
  for (int i = 0; i < N_FICHAS; i++) {
    if (j->fichas[i].posicion == 0 && dado == 5) {
      posibleMovimiento = 1;
      salida += i+1;
    } else if (j->fichas[i].posicion > 0 && j->fichas[i].posicion < TAMANO_TABLERO - 1) {
      if (j->fichas[i].posicion + dado < TAMANO_TABLERO) {
        posibleMovimiento = 1;
      }
    }
  }

  if (!posibleMovimiento) {
    printf("\nNo hay movimientos disponibles\n");
    return;
  }

  for(int f=1;f<=N_FICHAS;f++) comp+=f;
  if(salida!=0 && salida!=comp){
    salir(j,salida-1);
    mover(j,salida-1,dado);
    return;
  }

  do {
    printf("\nElige una ficha para mover (1-%d): ", N_FICHAS);
    scanf("%d%c", &fichaElegida,&intro);
    fichaElegida--;

    // Controlar entrada
    if (fichaElegida < 0 || fichaElegida >= N_FICHAS) {
        printf("\nMovimiento no válido: Ficha no válida\n");
        continue;
    }

    if (j->fichas[fichaElegida].posicion == 0) {
        if (dado == 5) {
            salir(j, fichaElegida);
            mover(j, fichaElegida, dado);
            return;
        }else{
          printf("\nMovimiento no válido: No ha salido aún\n");
        }
    } else {
      for (int o = 0; o < N_FICHAS; o++) {
        if (j->fichas[fichaElegida].posicion > 0 && j->fichas[fichaElegida].posicion < TAMANO_TABLERO - 1 &&
          j->fichas[fichaElegida].posicion + dado == otro->fichas[o].posicion &&
          tablero[otro->fichas[o].posicion] == 1) {
          mover(j, fichaElegida, dado);
          comer(j, otro, fichaElegida, o);
          volver(otro,o);
          if (j->fichas[NUM_JUGADORES - fichaElegida - 1].posicion + sumaComida < TAMANO_TABLERO - 1 && j->fichas[NUM_JUGADORES - fichaElegida - 1].posicion>0){
            mover(j, NUM_JUGADORES - fichaElegida - 1, sumaComida);
          }
          return;
        }
      }

      if (j->fichas[fichaElegida].posicion + dado < TAMANO_TABLERO - 1) {
        mover(j, fichaElegida, dado);
        return;
      } else if (j->fichas[fichaElegida].posicion + dado == TAMANO_TABLERO - 1) {
        mover(j, fichaElegida, dado);
        llegar(j, fichaElegida);
        if (j->fichas[NUM_JUGADORES - fichaElegida - 1].posicion + sumaLlegada < TAMANO_TABLERO - 1 && j->fichas[NUM_JUGADORES - fichaElegida - 1].posicion>0){
          mover(j, NUM_JUGADORES - fichaElegida - 1, sumaLlegada);
        }
        return;
      } else {
        printf("\nMovimiento no válido: Fuera de los límites del tablero\n");
      }
    }
  } while (1);  
}

void calcularAccion(int tablero[],int TAMANO_TABLERO,int NUM_JUGADORES,jugador *j,jugador *otro,int dado){
  int sumaLlegada=5,sumaComida=10;
  int salida[N_FICHAS]={0};
  
  // Salida
  for(int i = 0 ; i < N_FICHAS ; i++){
    if(j->fichas[i].posicion==0){
      if(dado==5){ 
        salir(j,i);
        mover(j,i,dado);
        return;
      }
    }else{
      salida[i]=1;
    }
  }

  for(int s = 0 ; s < N_FICHAS ; s++){
    if(salida[s]){
      // Posibilidad de comer alguna ficha
      for(int i = 0 ; i < N_FICHAS ; i++){
        for(int o = 0 ; o < N_FICHAS ; o++){
          if(j->fichas[i].posicion>0&&j->fichas[i].posicion<TAMANO_TABLERO-1){
            if(j->fichas[i].posicion+dado==otro->fichas[o].posicion && tablero[otro->fichas[o].posicion]==1){
              mover(j,i,dado);
              comer(j,otro,i,o);
              volver(otro,o);
              if(j->fichas[NUM_JUGADORES-i-1].posicion+sumaComida<TAMANO_TABLERO-1 && j->fichas[NUM_JUGADORES-i-1].posicion>0) mover(j,NUM_JUGADORES-i-1,sumaComida);
              return;
            }
          }
        }
      }

      for(int i = 0 ; i < N_FICHAS ; i++){
        if(j->fichas[i].posicion>0&&j->fichas[i].posicion<TAMANO_TABLERO-1){
          // Mover a final
          if(j->fichas[i].posicion+dado==TAMANO_TABLERO-1){
            mover(j,i,dado);
            llegar(j,i);
            if(j->fichas[NUM_JUGADORES-i-1].posicion+sumaLlegada<TAMANO_TABLERO-1 && j->fichas[NUM_JUGADORES-i-1].posicion>0) mover(j,NUM_JUGADORES-i-1,sumaLlegada);
            return;
          }

          // Mover a casilla final
          if(tablero[j->fichas[i].posicion+dado]==3){
            mover(j,i,dado);
            return;
          }

          // Mover a casilla segura
          if(tablero[j->fichas[i].posicion+dado]==2){
            mover(j,i,dado);
            return;
          }    
        }

        // Mover a cualquier casilla
        for(int i = 0 ; i < N_FICHAS ; i++){
          if(j->fichas[i].posicion!=TAMANO_TABLERO && j->fichas[i].posicion+dado<TAMANO_TABLERO-1 && j->fichas[i].posicion>0){
            mover(j,i,dado);
            return;
          }
        }
      } 
    }
  }
  
  printf("\nNo hay movimientos disponibles\n");
}

void tirarDado(int turno,int *dado){
  char color_dado[9] = "\x1b[47m";
  char color_num[9] = "\x1b[31m";
  char color_normal[9] = "\x1b[0m";
  char intro;

  if(!turno){
    printf("Intro para tirar el dado... ");
    scanf("%c",&intro);
  }
  
  *dado = rand() % 6+1; // Aleatorio entre 1 y 6
  printf("¡Has sacado un %s%s%d%s!\n",color_dado,color_num,*dado,color_normal);
}

void controlTurno(int *turno, int dado, int *seises, jugador *jugadores, int NUM_JUGADORES) {
  if(dado==6){
    (*seises)++;
      if (*seises == 3) {
        volver(&jugadores[*turno],rand()%N_FICHAS);
        if (*turno) {  // Cambio de turno por triple seis
          *turno = 0;
        }else{
          *turno = 1;
        }
      }
  }else{
    *seises=0;
    if(*turno){  // Cambio de turno normal
      *turno = 0;
    }else{
      *turno = 1;
    }
  }
}

int comprobarFinal(int TAMANO_TABLERO,jugador *j){
  int llegadas = 0;
  for(int i = 0 ; i < N_FICHAS ; i++){
    if(j->fichas[i].posicion==TAMANO_TABLERO-1) llegadas++;
  }

  if(llegadas==N_FICHAS) return 1;
  return 0;
}

void juego(FILE* F,char const* FICHERO_PARTIDA,int *tablero,int TAMANO_TABLERO,jugador *jugadores,int NUM_JUGADORES){
  char color_normal[9] = "\x1b[0m";
  int dado,seises=0,turno=0;
  
  do{
    mostrarTablero(tablero,TAMANO_TABLERO,jugadores,NUM_JUGADORES);
    printf("--Turno de %s%s%s--\n",jugadores[turno].color,jugadores[turno].nombre,color_normal);

    tirarDado(turno,&dado);

    if(turno){
      calcularAccion(tablero,TAMANO_TABLERO,NUM_JUGADORES,&jugadores[turno],&jugadores[NUM_JUGADORES-turno-1],dado); //Accion para maquina
    }else{
      pedirAccion(tablero,TAMANO_TABLERO,NUM_JUGADORES,&jugadores[turno],&jugadores[NUM_JUGADORES-turno-1],dado); //Accion para usuario
    }

    if(comprobarFinal(TAMANO_TABLERO,&jugadores[turno])){ // Comprueba si han llegado las dos casillas de alguno de los jugadores
      mostrarTablero(tablero,TAMANO_TABLERO,jugadores,NUM_JUGADORES);
      printf("\n---FIN DE LA PARTIDA---\nGanador: %s%s%s",jugadores[turno].color,jugadores[turno].nombre,color_normal);
      desechaPartida(F,FICHERO_PARTIDA);
      break;
    } 
    
    controlTurno(&turno,dado,&seises,jugadores,NUM_JUGADORES); //Control de cambio de turno
    guardaPartida(F,FICHERO_PARTIDA,jugadores,NUM_JUGADORES); //Guardado de partida
  }while(1);
}