package arbol;

import java.io.Serializable;

public class WriteNode extends Node implements Serializable{
  final int STR = 1;
  final int STRID = 2;

  public String conststr;
  public Value id;
  public int type;

  public WriteNode(String conststr, int fila, int columna){
    super(fila,columna);
    this.conststr = conststr;
    this.type = STR;
  }

  public WriteNode(String conststr, Value id, int fila, int columna){
    super(fila,columna);
    this.conststr = conststr;
    this.id = id;
    this.type = STRID;
  }

  public String printNode(int depth){
    String json = "{";

    //System.out.println("This WriteNode holds: "); 

    for (int i = 0; i <= depth; i++){
      //System.out.print("|  ");
    }

    //System.out.print("|-- ");
    
    //System.out.println("String: " + conststr);
    json += "\"String\" : \"" + conststr + "\"";

    if(type ==STRID){
        json += ",";
        for (int i = 0; i <= depth; i++){
        //System.out.print("|  ");
        }

        //System.out.print("|-- ");

        //System.out.print("Id: " + id );

        json += "\"Id\": " + ((Value)this.id).printNode(depth + 1);
        
    }

    json += "}";

    return json;
  }
}