public class MathSumNode{
  String operator;
  MathSumNode leftChild;
  MathMultNode rightChild;
  MathMultNode value;
  int type; //1 for operation, 2 for a direct value

  public MathSumNode(MathSumNode leftChild, String operator, MathMultNode rightChild, int type){
    this.operator = operator;
    this.leftChild = leftChild;
    this.rightChild = rightChild;
    this.type = type;
  }

  public MathSumNode(MathMultNode value, int type){
    this.value = value;
    this.type = type;
  }

  public String printNode(){
    //Print logic goes here
  }
}