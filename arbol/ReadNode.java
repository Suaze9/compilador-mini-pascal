package arbol;

import java.io.Serializable;

public class ReadNode extends Node implements Serializable{
  public Value id;

  public ReadNode(Value id, int fila, int columna){
    super(fila,columna);
    this.id = id;
  }

  public String printNode(int depth){
    String json = "{";

    //System.out.println("This ReadNode holds: "); 

    for (int i = 0; i <= depth; i++){
      //System.out.print("|  ");
    }

    //System.out.print("|-- ");
    
    //System.out.print("Id: " + id );

    json += "\"Id\": " + ((Value)this.id).printNode(depth	+ 1);
    
    json += "}";
    return json;
  }
}