#include <stdio.h>
#include <stdlib.h>
#include <time.h>

//CONSTANTES GLOBALES (LO SIENTO)
const int N_NAVES = 3;
const int N_FASES = 7;

//REGISTROS
struct Coordenadas{
  int x,y;
};

struct Nave{
  int id,numDisparos,agilidad,velocidad;
  struct Coordenadas posicion;
};

/////////////////////////////////////////
int buscarId(int id, struct Nave *naves){
  for(int i=0;i<N_NAVES;i++){
    if(naves[i].id==id){
      return id;//Devuelve la id de la nave
    }
  }
  return 0;
}

int existeId(int id, struct Nave *naves){
  for(int i=0;i<N_NAVES;i++){
    if(naves[i].id==id){
      return i;//Devuelve la posición en el vector para buscarla más fácilmente
    }
  }
  return -1;
}

void darAltaNaves(struct Nave *naves){
  printf("===INICIALIZAR NAVES===\n");
  struct Nave nueva;
  for(int i=0;i<N_NAVES;i++){
    do{
      //Entrada de información para la nueva nave
      //ID
      printf("\nIntroduce el id de la nave nº%d: ",i+1);
      scanf("%d",&nueva.id);

      //Comprobación de repetición de ID o ID no válido
      if(nueva.id<1 || buscarId(nueva.id,naves)){
        printf("\nERROR: ID no válido\n");
      }else{
        //Disparos
        printf("Introduce el número de disparos de la nave: ");
        scanf("%d",&nueva.numDisparos);

        //Agilidad
        printf("Introduce la agilidad de la nave: ");
        scanf("%d",&nueva.agilidad);

        //Velocidad
        printf("Introduce la velocidad de la nave: ");
        scanf("%d",&nueva.velocidad);

        //Posición
        printf("Introduce la coordenada X: ");
        scanf("%d",&nueva.posicion.x);
        printf("Introduce la coordenada Y: ");
        scanf("%d",&nueva.posicion.y);

        //Añadir la nave
        naves[i]=nueva;
        printf("Nave añadida con éxito\n");
        break;
      }
    }while(1);
  }
}

void generarFases(int fases[N_NAVES][N_FASES]){
  for(int i=0;i<N_NAVES;i++){
    for(int j=0;j<N_FASES;j++){
      fases[i][j]=rand()%2;
    }
  }
}

void inicializarJuego(struct Nave *naves,int fases[N_NAVES][N_FASES]){
  darAltaNaves(naves);
  generarFases(fases);
}

void mostrarNaves(struct Nave *naves){
  printf("\n===Lista de naves===");
  for(int i=0;i<N_NAVES;i++){
    printf("\nNave nº%d:\n\tID:\t\t\t%d\n\tNºDisparos:\t%d\n\tAgilidad:\t%d\n\tVelocidad:\t%d\n\tPos(X,Y):\t%d,%d\n",i+1,naves[i].id,naves[i].numDisparos,naves[i].agilidad,naves[i].velocidad,naves[i].posicion.x,naves[i].posicion.y);
  }
  printf("\n====================\n");
}

void mostrarNave(struct Nave *naves){
  int id=0;
  
  printf("\n===Mostrar nave===\n");
  printf("Introduce el ID de la nave a buscar: ");
  scanf("%d",&id);
  
  int i = existeId(id,naves);
  if(i!=-1){
    printf("\nNave nº%d:\n\tID:\t\t\t%d\n\tNºDisparos:\t%d\n\tAgilidad:\t%d\n\tVelocidad:\t%d\n\tPos(X,Y):\t%d,%d\n",i+1,naves[i].id,naves[i].numDisparos,naves[i].agilidad,naves[i].velocidad,naves[i].posicion.x,naves[i].posicion.y);
  }else{
    printf("\nEse ID no existe");
  }
}

int comprobarColision(struct Nave nave1,struct Nave nave2){
  //0 = Sin conexión | 1 = Total conexión | 2 = Misma X | 3 = Misma Y
  if(nave1.posicion.x==nave2.posicion.x && nave1.posicion.y==nave2.posicion.y) return 1;
  if(nave1.posicion.x==nave2.posicion.x) return 2;
  if(nave1.posicion.y==nave2.posicion.y) return 3;
  return 0;
}

void choqueNaves(struct Nave *naves){
  struct Nave nave1,nave2;
  int id1,id2;

  printf("\n==Comprobar colisión entre naves==");
  printf("\nIntroduce el ID de la primera nave: ");
  scanf("%d",&id1);
  printf("Introduce el ID de la segunda nave: ");
  scanf("%d",&id2);

  id1 = existeId(id1,naves);
  id2 = existeId(id2,naves);

  if(id1==-1){
    printf("ERROR: Primer ID no válido");
    return;
  }
  if(id2==-1){
    printf("ERROR: Segundo ID no válido");
    return;
  }

  nave1=naves[id1];
  nave2=naves[id2];

  printf("\n");
  switch(comprobarColision(nave1,nave2)){
    case 0: printf("No están colisionando");
            break;
    case 1: printf("Están colisionando");
            break;
    case 2: printf("No están colisionando, pero coinciden verticalmente");
            break;
    case 3: printf("No están colisionando, pero coinciden horizontalmente");
            break;    
  }
  printf("\n");
}

void buscarNaveRapida(struct Nave *naves){
  struct Nave naveRapida=naves[0];
  
  for(int i=0;i<N_NAVES;i++){
    if(naves[i].velocidad>naveRapida.velocidad){
      naveRapida=naves[i];
    }
  }

  printf("\nNave más rápida:\n\tID:\t\t\t%d\n\tNºDisparos:\t%d\n\tAgilidad:\t%d\n\tVelocidad:\t%d\n\tPos(X,Y):\t%d,%d\n",naveRapida.id,naveRapida.numDisparos,naveRapida.agilidad,naveRapida.velocidad,naveRapida.posicion.x,naveRapida.posicion.y);
}

void ordenarPorDisparos(struct Nave *naves){
  struct Nave aux;
  for (int i = 0 ; i < N_NAVES - 1; i++){
    for (int j = 0 ; j < N_NAVES - i - 1; j++){
      if (naves[j].numDisparos < naves[j+1].numDisparos){
        aux=naves[j];
        naves[j]=naves[j+1];
        naves[j+1]=aux;
      }
    }
  }
}

void buscarAparicionEnFase(struct Nave *naves,int fases[N_NAVES][N_FASES]){
  int id,vacio=0;
  
  printf("\n==Buscar apariciones==");
  printf("\nIntroduce el ID de la nave: ");
  scanf("%d",&id);

  int i = existeId(id,naves);
  
  if(i!=-1){
    printf("Buscando...");
    for(int j=0;j<N_FASES;j++){
      if(fases[i][j]){
        printf("\nAparece en la fase %d",j+1);
        vacio++;
      }
    }
    if(vacio==0){
      printf("\nNo aparece en ninguna fase");
    }
  }else{
    printf("ERROR: ID no válido");
  }
  printf("\n");
}

void menu(struct Nave *naves, int fases[N_NAVES][N_FASES]){
  int opcion;

  do{
    printf("\n=======MENÚ=======\n");
    printf("Indica una opción:\n\t0) Salir\n\t1) Mostrar lista de naves\n\t2) Buscar nave por ID\n\t3) Comprobar colisión de dos naves\n\t4) Mostrar nave más rápida\n\t5) Ordenar lista por mayor número de disparos\n\t6) Mostrar las fases en las que aparece una nave\n\t> ");
    scanf("%d",&opcion);

    switch(opcion){
      default:printf("\nERROR: %d no es una opción válida",opcion);
              break;
      case 0: printf("\nSaliendo...");
              return;
      case 1: mostrarNaves(naves);
              break;
      case 2: mostrarNave(naves);
              break;
      case 3: choqueNaves(naves);
              break;
      case 4: buscarNaveRapida(naves);
              break;
      case 5: ordenarPorDisparos(naves);
              break;
      case 6: buscarAparicionEnFase(naves,fases);
              break;
    }
  }while(1);
}

/////////////////////////////////////////////////
int main(void) {
  srand(time(NULL));
  struct Nave naves[N_NAVES];
  int fases[N_NAVES][N_FASES];
  
  inicializarJuego(naves,fases);
  menu(naves,fases);
  return 0;
}