public class MathMult{

  final int VALUE = 1;
  final int MATHNODE = 2;

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
    else{
      System.out.println("TIPO NO VALIDO ENTREGADO AL HIJO DER DE  NODO \"MathMult\"!!! ");
      this.typeRigth = 0;
    }

  }

  public String printNode(int depth){
    System.out.println("This MathMult holds: "); 
    for (int i = 0; i <= depth; i++){
      System.out.print("----");
    }
    if(this.typeLeft == VALUE){
      ((Value)this.leftChild).printNode(depth + 1);
    }
    else if(this.typeLeft == MATHNODE){
      ((MathMult)this.leftChild).printNode(depth + 1);
    }
    else{
      System.out.println("ERROR NODO IZQ de \"MathMult\"");
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
    else if(this.typeRight == MATHNODE){
      ((MathMult)this.rightChild).printNode(depth + 1);
    }
    else{
      System.out.println("ERROR NODO DER de \"MathMult\"");
    }
    return "Ola";
  }
}