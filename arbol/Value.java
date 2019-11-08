package arbol;

import java.io.Serializable;

public class Value implements Serializable{
    
    final int NUM = 1;
    final int BOOL = 2;
    final int ID = 3;
    final int FUNCCALL = 4;
    
    Object content;
    int type;
    boolean not;

  public Value(Object content){
    this.content = content;

    if (content instanceof Integer)
        this.type = NUM;
    else if (content instanceof Boolean)
        this.type = BOOL;
    else if (content instanceof String)
        this.type = ID;
    else if (content instanceof FuncCallNode)
        this.type = FUNCCALL;
    else{
        //System.out.println("TIPO NO VALIDO ENTREGADO A NODO \"VALUE\"!!! ");
        this.type = 0;
    }
    this.not = false;
  }

  public Value(Object content, boolean not){
    this.content = content;

    if (content instanceof Integer)
        this.type = NUM;
    else if (content instanceof Boolean)
        this.type = BOOL;
    else if(content instanceof String)
        this.type = ID;
    else if (content instanceof FuncCallNode)
        this.type = FUNCCALL;
    else{
        //System.out.println("TIPO NO VALIDO ENTREGADO A NODO \"VALUE\"!!! ");
        this.type = 0;
    }
    this.not = not;
  }

  public String printNode(int depth){
    String json = "{";
    if(!this.not){
      //System.out.print("This Value holds: ");
    }else{
      //System.out.print("This NOT Value holds: ");
      json += "\"NOT\" :{";
    }

    switch(this.type){
      case NUM:
        json += "\"NUM \" : \"" + ((Integer)this.content).toString() + "\"";
        //System.out.println((Integer)this.content);
        break;
      case BOOL:
        json += "\"BOOL \" : \"" + ((Boolean)this.content).toString()  + "\"";
        //System.out.println((Boolean)this.content);
        break;
      case ID:
        json += "\"ID \" : \"" + (String)this.content  + "\"";
        //System.out.println((String)this.content);
        break;
      case FUNCCALL:
        for (int i = 0; i <= depth; i++){
          //System.out.print("----");
        }
        json += "\"FuncCallNode\": ";
        json += ((FuncCallNode)this.content).printNode(depth + 1);
        break;
      default:
        //System.out.println("ERROR NODO de \"Value\"");
        break;
    }
    if(this.not){
      json += "}";
    }
    json += "}";
    return json;
  }
}