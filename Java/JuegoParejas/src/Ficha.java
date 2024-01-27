public class Ficha {
    private char letra;
    private boolean descubierta;
    private boolean acertada;

    public Ficha(char letra) {
        this.letra = letra;
        this.descubierta = false;
    }

    public char getLetra() {
        return letra;
    }

    public boolean isDescubierta() {
        return descubierta;
    }

    public void setDescubierta(boolean descubierta) {
        this.descubierta = descubierta;
    }

    public boolean isAcertada() {
        return acertada;
    }

    public void setAcertada(boolean acertada) {
        this.acertada = acertada;
    }

    @Override
    public String toString() {
        //Controlo la salida por pantalla en el tablero con el propio toString
        if(acertada || descubierta) return Character.toString(letra);
        return ".";
    }
}
