import java.util.Collection;
import java.util.Comparator;

public enum Grupo {
    GELIDO("Gélido",3,3),//3
    ABRASANTE("Abrasante",3,3),//3
    MAGICO("Mágico",2,2),
    //ACUATICO("Acuático",3,4),
    ;

    final String nombre;
    final int bonusGrupo;
    final int maxLugares;

    Grupo(String nombre, int bonusGrupo, int maxLugares) {
        this.nombre = nombre;
        this.bonusGrupo = bonusGrupo;
        this.maxLugares = maxLugares;
    }
}
