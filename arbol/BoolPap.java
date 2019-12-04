package arbol;

import java.io.Serializable;

public abstract class BoolPap extends Node implements Serializable{
  
  public boolean not;

  public BoolPap(boolean not, int fila, int columna){
    super(fila,columna);

    this.not = not;
    
  }
  
  public BoolPap(int fila, int columna){
    super(fila,columna);

    this.not = false;
    
  }

  public abstract String printNode(int depth);

}