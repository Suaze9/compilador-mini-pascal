public class MathMult{

  final int VALUE = 1;
  final int MATHNODE = 2;
  final int MATHMULT = 3;

  Object leftChild;
  String operator;
  Object rightChild;
  int typeLeft;
  int typeRight;
  
  //1 for operation, 2 for a direct value

  public MathMult(Object leftChild, String operator, Object rightChild){
    this.leftChild = leftChild;
    this.operator = operator;
    this.rightChild = rightChild;
    
    if(leftChild instanceof Value){
      this.typeLeft = VALUE;
    }
    else if (leftChild instanceof MathNode){
      this.typeLeft = MATHNODE;
    }
    else if (leftChild instanceof MathMult){
      this.typeLeft = MATHMULT;
    }
    else{
      System.out.println("TIPO NO VALIDO ENTREGADO AL HIJO IZQ DE  NODO \"MathMult\"!!! ");
      this.typeLeft = 0;
    }

    if(rightChild instanceof Value){
      this.typeRight = VALUE;
    }
    else if (rightChild instanceof MathNode){
      this.typeRight = MATHNODE;
    }
    else if (rightChild instanceof MathMult){
      this.typeRight = MATHMULT;
    }
    else{
      System.out.println("TIPO NO VALIDO ENTREGADO AL HIJO DER DE  NODO \"MathMult\"!!! ");
      this.typeRight = 0;
    }

  }

  public String printNode(int depth){
    System.out.println("This MathMult holds: "); 
    for (int i = 0; i <= depth; i++){
      System.out.print("----");
    }

    switch(this.typeLeft){
      case VALUE:
          ((Value)this.leftChild).printNode(depth + 1);
        break;
      case MATHNODE:
          ((MathNode)this.leftChild).printNode(depth + 1);
        break;
      case MATHMULT:
          ((MathMult)this.leftChild).printNode(depth + 1);
        break;
      default:
        System.out.println("ERROR NODO IZQ de \"MathMult\"");
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
      case MATHNODE:
        ((MathNode)this.rightChild).printNode(depth + 1);
        break;
      case MATHMULT:
        ((MathMult)this.rightChild).printNode(depth + 1);
        break;
      default:
        System.out.println("ERROR NODO DER de \"MathMult\"");
        break;
    }
    return "Ola";
  }
}