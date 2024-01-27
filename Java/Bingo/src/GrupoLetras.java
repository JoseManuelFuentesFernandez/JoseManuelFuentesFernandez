public enum GrupoLetras {
    B(1,15), //MODIFICABLE
    I(B.numFinal+1, B.numFinal*2),
    N((B.numFinal*2)+1,B.numFinal*3),
    G((B.numFinal*3)+1, B.numFinal*4),
    O((B.numFinal*4)+1,B.numFinal*5);

    public final int numInicio;
    public final int numFinal;

    GrupoLetras(int numComienzo, int numFinal) {
        this.numInicio = numComienzo;
        this.numFinal = numFinal;
    }
}
