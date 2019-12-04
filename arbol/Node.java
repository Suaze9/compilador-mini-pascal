package arbol;
import java.io.Serializable;

public abstract class Node implements Serializable{
    public int fila;
    public int columna;

    Node(int fil, int col){
        fila = fil;
        columna = col;
    }

    public abstract String printNode(int depth);
}