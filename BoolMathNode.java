public class BoolMathNode{
  final int VALUE = 1;
  final int MATH = 2;
  final int MULT = 3;
  final int SUM = 4;
  
  Object leftChild;
  String operator;
  Object rightChild;
  int typeLeft;
  int typeRight;

  public BoolMathNode(Object leftChild, String operator, Object rightChild){
    this.leftChild = leftChild;
    this.operator = operator;
    this.rightChild = rightChild;

    if(leftChild instanceof Value){
      this.typeLeft = VALUE;
    }else if(leftChild instanceof MathNode){
      this.typeLeft = MATH;
    }else if(leftChild instanceof MathMult){
      this.typeLeft = MULT;
    }else if(leftChild instanceof MathSum){
      this.typeLeft = SUM;
    }else{
      System.out.println("TIPO NO VALIDO ENTREGADO AL HIJO IZQ DE  NODO \"BoolMathNode\"!!! ");
      this.typeLeft = 0;
    }

    if(rightChild instanceof Value){
      this.typeRight = VALUE;
    }else if(rightChild instanceof MathNode){
      this.typeRight = MATH;
    }else if(rightChild instanceof MathMult){
      this.typeRight = MULT;
    }else if(rightChild instanceof MathSum){
      this.typeRight = SUM;
    }else{
      System.out.println("TIPO NO VALIDO ENTREGADO AL HIJO DER DE  NODO \"BoolMathNode\"!!! ");
      this.typeRight = 0;
    }
  }

  public String printNode(int depth){
    System.out.println("This BoolMathNode holds: "); 
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
            System.out.println("ERROR NODO IZQ de \"BoolMathNode\"");
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
            System.out.println("ERROR NODO IZQ de \"BoolMathNode\"");
            break;
    }
    return "Ola";
  }
}