public class BoolNode{
  final int VALUE = 1;
  final int BOOLOR = 2;
  final int BOOLAND = 3;
  final int BOOL = 4;
  final int BOOLMATH = 5;
  final int FUNCCALL = 6;

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
      this.type = BOOL;
    }else if(content instanceof BoolMathNode){
      this.type = BOOLMATH;
    }else if(content instanceof FuncCallNode){
      this.type = FUNCCALL;
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
        default:
            json += "\"Error\" : \"0\"";
            System.out.println("ERROR NODO IZQ de \"BoolNode\"");
            break;
    }
    json += "}";
    return json;
  }

}