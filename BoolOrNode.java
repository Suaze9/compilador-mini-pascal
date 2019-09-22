public class BoolOrNode{
  final int VALUE = 1;
  final int BOOLOR = 2;
  final int BOOLAND = 3;
  final int BOOLMATH = 4;
  final int BOOL = 5;
  
  Object leftChild;
  String operator;
  Object rightChild;
  int typeLeft;
  int typeRight;

  public BoolOrNode(Object leftChild, String operator, Object rightChild){
    this.leftChild = leftChild;
    this.operator = operator;
    this.rightChild = rightChild;

    if(leftChild instanceof Value){
      this.typeLeft = VALUE;
    }
    else if (leftChild instanceof BoolOrNode){
      this.typeLeft = BOOLOR;
    }
    else if (leftChild instanceof BoolAndNode){
      this.typeLeft = BOOLAND;
    }
    else if (leftChild instanceof BoolMathNode){
      this.typeLeft = BOOLMATH;
    }
    else if (leftChild instanceof BoolNode){
      this.typeLeft = BOOL;
    }
    else{
      System.out.println("TIPO NO VALIDO ENTREGADO AL HIJO IZQ DE  NODO \"BoolOrNode\"!!! ");
      this.typeLeft = 0;
    }

    if(rightChild instanceof Value){
      this.typeRight = VALUE;
    }
    else if (rightChild instanceof BoolOrNode){
      this.typeRight = BOOLOR;
    }
    else if (rightChild instanceof BoolAndNode){
      this.typeRight = BOOLAND;
    }
    else if (rightChild instanceof BoolMathNode){
      this.typeRight = BOOLMATH;
    }
    else if (rightChild instanceof BoolNode){
      this.typeRight = BOOL;
    }
    else{
      System.out.println("TIPO NO VALIDO ENTREGADO AL HIJO DER DE  NODO \"BoolOrNode\"!!! ");
      this.typeRight = 0;
    }
  }

  public String printNode(int depth){
    System.out.println("This BoolOrNode holds: "); 
    for (int i = 0; i <= depth; i++){
      System.out.print("----");
    }
    
    switch(this.typeLeft){
        case VALUE:
            ((Value)this.leftChild).printNode(depth + 1);
            break;
        case BOOLOR:
            ((BoolOrNode)this.leftChild).printNode(depth + 1);
            break;
        case BOOLAND:
            ((BoolAndNode)this.leftChild).printNode(depth + 1);
            break;
        case BOOLMATH:
            ((BoolMathNode)this.leftChild).printNode(depth + 1);
            break;
        case BOOL:
            ((BoolNode)this.leftChild).printNode(depth + 1);
            break;
        default:
            System.out.println("ERROR NODO IZQ de \"BoolOrNode\"");
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
        case BOOLOR:
            ((BoolOrNode)this.rightChild).printNode(depth + 1);
            break;
        case BOOLAND:
            ((BoolAndNode)this.rightChild).printNode(depth + 1);
            break;
        case BOOLMATH:
            ((BoolMathNode)this.rightChild).printNode(depth + 1);
            break;
        case BOOL:
            ((BoolNode)this.rightChild).printNode(depth + 1);
            break;
        default:
            System.out.println("ERROR NODO DER de \"BoolOrNode\"");
            break;
    }
    return "Ola";
  }

}