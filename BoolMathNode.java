public class BoolMathNode{
  final int VALUE = 1;
  final int MATH = 2;
  final int MULT = 3:
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
    }else if(leftChild instanceof MathMul){
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
    }else if(rightChild instanceof MathMul){
      this.typeRight = MULT;
    }else if(rightChild instanceof MathSum){
      this.typeRight = SUM;
    }else{
      System.out.println("TIPO NO VALIDO ENTREGADO AL HIJO DER DE  NODO \"BoolMathNode\"!!! ");
      this.typeRight = 0;
    }
  }
}