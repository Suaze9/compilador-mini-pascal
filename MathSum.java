public class MathSum{

  final int VALUE = 1;
  final int MATHNODE = 2;
  final int MATHMULT = 3;

  Object leftChild;
  String operator;
  Object rightChild;
  int typeLeft;
  int typeRight;

  public MathSum(Object leftChild, String operator, Object rightChild){
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
      System.out.println("TIPO NO VALIDO ENTREGADO AL HIJO IZQ DE  NODO \"MathSum\"!!! ");
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
      System.out.println("TIPO NO VALIDO ENTREGADO AL HIJO IZQ DE  NODO \"MathSum\"!!! ");
      this.typeRight = 0;
    }

  }

  public String printNode(int depth){
    System.out.println("This MathSum holds: "); 
    for (int i = 0; i <= depth; i++){
      System.out.print("----");
    }
    if(this.typeLeft == VALUE){
      ((Value)this.leftChild).printNode(depth + 1);
    }
    else if(this.typeLeft == MATHMULT){
      ((MathMult)this.leftChild).printNode(depth + 1);
    }else if(this.typeLeft == MATHNODE){
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
    else if(this.typeRight == MathMult){
      ((MathMult)this.rightChild).printNode(depth + 1);
    }
    else{
      System.out.println("ERROR NODO DER de \"MathMult\"");
    }
    return "Ola";
  }

}