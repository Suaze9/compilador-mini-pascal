package arbol;

import java.util.ArrayList;
import java.io.Serializable;


public class AssigNode implements Serializable{

  final int STR = 1;
  final int CHR = 2;
  final int VALUE = 3;
  final int MATHNODE = 4;
  final int MATHMULT = 5;
  final int MATHSUM = 6;
  final int BOOL = 7;
  final int BOOLMATH = 8;
  final int BOOLAND = 9;
  final int BOOLOR = 10;

  public Value Id;
  public Object expr;
  public int type;

  public AssigNode(Value Id, Object expr){
    this.Id = Id;
    this.expr = expr;

    if(expr instanceof String){
      this.type = STR;
    }
    else if (expr instanceof Character){
      this.type = CHR;
    }
    else if (expr instanceof Value){
      this.type = VALUE;
    }
    else if (expr instanceof MathNode){
      this.type = MATHNODE;
    }
    else if (expr instanceof MathMult){
      this.type = MATHMULT;
    }
    else if (expr instanceof MathSum){
      this.type = MATHSUM;
    }
    else if (expr instanceof BoolNode){
      this.type = BOOL;
    }
    else if (expr instanceof BoolMathNode){
      this.type = BOOLMATH;
    }
    else if (expr instanceof BoolAndNode){
      this.type = BOOLAND;
    }
    else if (expr instanceof BoolOrNode){
      this.type = BOOLOR;
    }
    else{
      //System.out.println("TIPO NO VALIDO ENTREGADO AL HIJO IZQ DE  NODO \"AssigNode\"!!! ");
      this.type = 0;
    }

  }

  public String printNode(int depth){
    String json = "{";

    //System.out.println("This AssigNode holds: "); 

    for (int i = 0; i <= depth; i++){
      //System.out.print("|  ");
    }

    //System.out.print("|-- ");

    json += "\"Id\" : ";
    json += this.Id.printNode(depth + 1) + ",";
    
    for (int i = 0; i <= depth; i++){
      //System.out.print("|  ");
    }

    //System.out.print("|-- ");
    
    switch(this.type){
        case STR:
            json += "\"String\": ";
            json += "\"" + (String)this.expr + "\"";
            break;
        case CHR:
            json += "\"Char\": ";
            json += "\"" + (Character)this.expr + "\"";
            break;
        case VALUE:
            json += "\"Value\": ";
            json += ((Value)this.expr).printNode(depth + 1);
            break;
        case MATHNODE:
            json += "\"MathNode\": ";
            json += ((MathNode)this.expr).printNode(depth + 1);
            break;
        case MATHMULT:
            json += "\"MathMult\": ";
            json += ((MathMult)this.expr).printNode(depth + 1);
            break;
        case MATHSUM:
            json += "\"MathSum\": ";
            json += ((MathSum)this.expr).printNode(depth + 1);
            break;
        case BOOL:
            json += "\"BoolNode\": ";
            json += ((BoolNode)this.expr).printNode(depth + 1);
            break;
        case BOOLMATH:
            json += "\"BoolMathNode\": ";
            json += ((BoolMathNode)this.expr).printNode(depth + 1);
            break;
        case BOOLAND:
            json += "\"BoolAndNode\": ";
            json += ((BoolAndNode)this.expr).printNode(depth + 1);
            break;
        case BOOLOR:
            json += "\"BoolOrNode\": ";
            json += ((BoolOrNode)this.expr).printNode(depth + 1);
            break;
        default:
            json += "\"Error\" : \"0\"";
            //System.out.println("ERROR NODO de \"AssigNode\"");
            break;
    }
    
    json += "}";
    return json;
  }

}