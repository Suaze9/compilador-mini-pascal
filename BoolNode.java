public class BoolNode{
  final int VALUE = 1;
  final int BOOLOR = 2;
  final int BOOLAND = 3;
  final int BOOL = 4;
  final int BOOLMATH = 5;

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
    }else{
      System.out.println("TIPO NO VALIDO ENTREGADO AL HIJO DE NODO \"BoolNode\"!!! ");
      this.type = 0;
    }
  }

  public String printNode(int depth){
    System.out.println("This BoolNode holds: "); 
    for (int i = 0; i <= depth; i++){
      System.out.print("----");
    }
    
    switch(this.type){
        case VALUE:
            ((Value)this.content).printNode(depth + 1);
            break;
        case BOOLOR:
            ((BoolOrNode)this.content).printNode(depth + 1);
            break;
        case BOOLAND:
            ((BoolAndNode)this.content).printNode(depth + 1);
            break;
        case BOOLMATH:
            ((BoolMathNode)this.content).printNode(depth + 1);
            break;
        case BOOL:
            ((BoolNode)this.content).printNode(depth + 1);
            break;
        default:
            System.out.println("ERROR NODO IZQ de \"BoolNode\"");
            break;
    }
    return "Ola";
  }

}