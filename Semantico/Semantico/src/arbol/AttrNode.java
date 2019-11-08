package arbol;

import java.io.Serializable;

public class AttrNode implements Serializable{
  final int STR = 1;
  final int CHAR = 2;
  final int VALUE = 3;
  final int MATHNODE = 4;
  final int MATHMULT = 5;
  final int MATHSUM = 6;
  final int BOOL = 7;
  final int BOOLMATH = 8;
  final int BOOLAND = 9;
  final int BOOLOR = 10;
  
  Object attr;
  int type;

  public AttrNode(Object attr){
    this.attr = attr;
    
    if(attr instanceof String){
      this.type = STR;
    }else if(attr instanceof Character){
      this.type = CHAR;
    }else if(attr instanceof Value){
      this.type = VALUE;
    }else if(attr instanceof MathNode){
      this.type = MATHNODE;
    }else if(attr instanceof MathMult){
      this.type = MATHMULT;
    }else if(attr instanceof MathSum){
      this.type = MATHSUM;
    }else if(attr instanceof BoolNode){
      this.type = BOOL;
    }else if(attr instanceof BoolMathNode){
      this.type = BOOLMATH;
    }else if(attr instanceof BoolAndNode){
      this.type = BOOLAND;
    }else if(attr instanceof BoolOrNode){
      this.type = BOOLOR;
    }else{
      System.out.println("TIPO NO ACEPTADO POR ATTR NODE");
      this.type = 0;
    }
  }

  public String printNode(int depth){
    String json = "{";

    System.out.println("This AttrNode holds: "); 

    for (int i = 0; i <= depth; i++){
      System.out.print("|  ");
    }

    System.out.print("|-- ");

    switch(this.type){
        case STR:
            json += "\"String\": ";
            json += (String)this.attr;
            break;
        case CHAR:
            json += "\"Char\": ";
            json += (Character)this.attr;
            break;
        case VALUE:
            json += "\"Value\": ";
            json += ((Value)this.attr).printNode(depth + 1);
            break;
        case MATHNODE:
            json += "\"MathNode\": ";
            json += ((MathNode)this.attr).printNode(depth + 1);
            break;
        case MATHMULT:
            json += "\"MathMult\": ";
            json += ((MathMult)this.attr).printNode(depth + 1);
            break;
        case MATHSUM:
            json += "\"MathSum\": ";
            json += ((MathSum)this.attr).printNode(depth + 1);
            break;
        case BOOL:
            json += "\"BoolNode\": ";
            json += ((BoolNode)this.attr).printNode(depth + 1);
            break;
        case BOOLMATH:
            json += "\"BoolMathNode\": ";
            json += ((BoolMathNode)this.attr).printNode(depth + 1);
            break;
        case BOOLAND:
            json += "\"BoolAndNode\": ";
            json += ((BoolAndNode)this.attr).printNode(depth + 1);
            break;
        case BOOLOR:
            json += "\"BoolOrNode\": ";
            json += ((BoolOrNode)this.attr).printNode(depth + 1);
            break;
        default:
            json += "\"Error\" : \"0\"";
            System.out.println("ERROR NODO de \"AttrNode\"");
            break;
    }
    
    json += "}";
    return json;
  }
}