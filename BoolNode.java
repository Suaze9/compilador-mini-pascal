import java.io.Serializable;

public class BoolNode implements Serializable{
  final int VALUE = 1;
  final int BOOLOR = 2;
  final int BOOLAND = 3;
  final int BOOL = 4;
  final int BOOLMATH = 5;
  final int FUNCCALL = 6;

  final int MATH = 7;
  final int MULT = 8;
  final int SUM = 9;

  Object content;
  int type;

  public BoolNode(Object content){
    this.content = content;

    if(content instanceof Value){
      this.type = VALUE;
    }else if(content instanceof BoolOrNode){
      this.type = BOOLOR;
    }else if(content instanceof BoolAndNode){
      this.type = BOOLAND;
    }else if(content instanceof BoolNode){
      this.type = ((BoolNode)this.content).type;
      this.content = ((BoolNode)this.content).content;
    }else if(content instanceof BoolMathNode){
      this.type = BOOLMATH;
    }else if(content instanceof FuncCallNode){
      this.type = FUNCCALL;
    }else if (content instanceof MathNode){
      this.type = MATH;
    }
    else if (content instanceof MathMult){
      this.type = MULT;
    }
    else if (content instanceof MathSum){
      this.type = SUM;
    }else{
      System.out.println("TIPO NO VALIDO ENTREGADO AL HIJO DE NODO \"BoolNode\"!!! ");
      this.type = 0;
    }
  }

  public String printNode(int depth){
    String json = "{";
    System.out.println("This BoolNode holds: " + depth); 
    
    for (int i = 0; i <= depth; i++){
      System.out.print("|  ");
    }

    System.out.print("|-- ");
    
    switch(this.type){
        case VALUE:
            json += "\"Value\": ";
            json += ((Value)this.content).printNode(depth + 1);
            break;
        case BOOLOR:
            json += "\"BoolOrNode\": ";
            json += ((BoolOrNode)this.content).printNode(depth + 1);
            break;
        case BOOLAND:
            json += "\"BoolAndNode\": ";
            json += ((BoolAndNode)this.content).printNode(depth + 1);
            break;
        case BOOLMATH:
            json += "\"BoolMathNode\": ";
            json += ((BoolMathNode)this.content).printNode(depth + 1);
            break;
        case BOOL:
            json += "\"BoolNode\": ";
            json += ((BoolNode)this.content).printNode(depth + 1);
            break;
        case FUNCCALL:
            json += "\"FuncCallNode\": ";
            json += ((FuncCallNode)this.content).printNode(depth + 1);
            break;
        case MATH:
            json += "\"MathNode\": ";
            json += ((MathNode)this.content).printNode(depth + 1);
            break;
        case MULT:
            json += "\"MultNode\": ";
            json += ((MathMult)this.content).printNode(depth + 1);
            break;
        case SUM:
            json += "\"SumNode\": ";
            json += ((MathSum)this.content).printNode(depth + 1);
            break;
        default:
            json += "\"Error\" : \"0\"";
            System.out.println("ERROR NODO IZQ de \"BoolNode\"");
            break;
    }
    json += "}";
    return json;
  }

}