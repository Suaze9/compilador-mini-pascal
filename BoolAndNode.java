public class BoolAndNode{
  final int VALUE = 1;
  final int BOOLMATH = 2;
  final int BOOLAND = 3;
  final int BOOL = 4;
  
  Object leftChild;
  String operator;
  Object rightChild;
  int typeLeft;
  int typeRight;

  public BoolAndNode(Object leftChild, String operator, Object rightChild){
    this.leftChild = leftChild;
    this.operator = operator;
    this.rightChild = rightChild;

    if(leftChild instanceof Value){
      this.typeLeft = VALUE;
    }
    else if (leftChild instanceof BoolMathNode){
      this.typeLeft = BOOLMATH;
    }
    else if (leftChild instanceof BoolAndNode){
      this.typeLeft = BOOLAND;
    }
    else if (leftChild instanceof BoolNode){
      this.typeLeft = BOOL;
    }
    else{
      System.out.println("TIPO NO VALIDO ENTREGADO AL HIJO IZQ DE  NODO \"BoolAndNode\"!!! ");
      this.typeLeft = 0;
    }

    if(rightChild instanceof Value){
      this.typeRight = VALUE;
    }
    else if (rightChild instanceof BoolMathNode){
      this.typeRight = BOOLMATH;
    }
    else if (rightChild instanceof BoolAndNode){
      this.typeRight = BOOLAND;
    }
    else if (rightChild instanceof BoolNode){
      this.typeRight = BOOL;
    }
    else{
      System.out.println("TIPO NO VALIDO ENTREGADO AL HIJO DER DE  NODO \"BoolAndNode\"!!! ");
      this.typeRight = 0;
    }
  }

  public String printNode(int depth){
    System.out.println("This BoolAndNode holds: "); 
    for (int i = 0; i <= depth; i++){
      System.out.print("----");
    }
    if(this.typeLeft == VALUE){
      ((Value)this.leftChild).printNode(depth + 1);
    }
    else if(this.typeLeft == BOOLMATH){
      ((BoolMathNode)this.leftChild).printNode(depth + 1);
    }
    else if(this.typeLeft == BOOLAND){
      ((BoolAndNode)this.leftChild).printNode(depth + 1);
    }
    else if(this.typeLeft == BOOL){
      ((BoolNode)this.leftChild).printNode(depth + 1);
    }
    else{
      System.out.println("ERROR NODO IZQ de \"BoolAndNode\"");
    }

    for (int i = 0; i <= depth; i++){
      System.out.print("----");
    }
    System.out.println(this.operator);

    for (int i = 0; i <= depth; i++){
      System.out.print("----");
    }
    if(this.typeRight == VALUE){
      ((Value)this.rightChild).printNode(depth + 1);
    }
    else if(this.typeRight == BOOLMATH){
      ((BoolMathNode)this.rightChild).printNode(depth + 1);
    }
    else if(this.typeRight == BOOLAND){
      ((BoolAndNode)this.rightChild).printNode(depth + 1);
    }
    else if(this.typeRight == BOOL){
      ((BoolNode)this.rightChild).printNode(depth + 1);
    }
    else{
      System.out.println("ERROR NODO DER de \"MathMult\"");
    }
    return "Ola";
  }

}