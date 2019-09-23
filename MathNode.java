public class MathNode{
  final int VALUE = 1;
  final int MATH = 2;
  final int MULT = 3;
  final int SUM = 4;
  
  Object content;
  int type;

  public MathNode(Object content){
    this.content = content;
    
    if (content instanceof Value)
        this.type = VALUE;
    else if (content instanceof MathNode)
        this.type = MATH;
    else if (content instanceof MathMult)
        this.type = MULT;
    else if (content instanceof MathSum)
        this.type = SUM;
    else{
        System.out.println("TIPO NO VALIDO ENTREGADO A NODO \"MathNode\"!!! ");
        this.type = 0;
    }
  }

  public String printNode(int depth){
    String json = "{ ";
    System.out.println("This MathNode holds: "); 
    
    for (int i = 0; i <= depth; i++){
      System.out.print("|  ");
    }

    System.out.print("|-- ");

    switch(this.type){
        case VALUE:
            json += "\"Value\": ";
            json += ((Value)this.content).printNode(depth + 1);
            break;
        case MATH:
            json += "\"MathNode\": ";
            json += ((MathNode)this.content).printNode(depth + 1);
            break;
        case MULT:
            json += "\"MathNode\": ";
            json += ((MathMult)this.content).printNode(depth + 1);
            break;
        case SUM:
            json += "\"MathSum\": ";
            json += ((MathSum)this.content).printNode(depth + 1);
            break;
        default:
            json += "\"Error\" : 0";
            System.out.println("ERROR NODO IZQ de \"MathNode\"");
            break;
    }

    json += "}";
    return json;
  }

}