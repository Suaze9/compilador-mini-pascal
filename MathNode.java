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
    System.out.println("This MathNode holds: "); 
    for (int i = 0; i <= depth; i++){
      System.out.print("----");
    }

    switch(this.typeLeft){
        case VALUE:
            ((Value)this.leftChild).printNode(depth + 1);
            break;
        case MATH:
            ((MathNode)this.leftChild).printNode(depth + 1);
            break;
        case MULT:
            ((MathMult)this.leftChild).printNode(depth + 1);
            break;
        case SUM:
            ((MathSum)this.leftChild).printNode(depth + 1);
            break;
        default:
            System.out.println("ERROR NODO IZQ de \"MathNode\"");
            break;
    }

    for (int i = 0; i <= depth; i++){
      System.out.print("----");
    }
    System.out.println(this.operator);

    for (int i = 0; i <= depth; i++){
      System.out.print("----");
    }

    switch(this.typeRight){
        case VALUE:
            ((Value)this.rightChild).printNode(depth + 1);
            break;
        case MATH:
            ((MathNode)this.rightChild).printNode(depth + 1);
            break;
        case MULT:
            ((MathMult)this.rightChild).printNode(depth + 1);
            break;
        case SUM:
            ((MathSum)this.rightChild).printNode(depth + 1);
            break;
        default:
            System.out.println("ERROR NODO DER de \"MathNode\"");
            break;
    }
    return "Ola";
  }

}