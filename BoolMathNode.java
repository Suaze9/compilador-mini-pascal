import java.io.Serializable;

public class BoolMathNode implements Serializable{
  final int VALUE = 1;
  final int MATH = 2;
  final int MULT = 3;
  final int SUM = 4;
  final int FUNCCALL = 5;
  
  
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
    }else if(leftChild instanceof FuncCallNode){
      this.typeLeft = FUNCCALL;
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
    }else if(rightChild instanceof FuncCallNode){
      this.typeRight = FUNCCALL;
    }else{
      System.out.println("TIPO NO VALIDO ENTREGADO AL HIJO DER DE  NODO \"BoolMathNode\"!!! ");
      this.typeRight = 0;
    }
  }

  public String printNode(int depth){
    String json = "{";
    System.out.println("This BoolMathNode holds: "); 
    
    for (int i = 0; i <= depth; i++){
      System.out.print("|  ");
    }

    System.out.print("|-- ");
    
    switch(this.typeLeft){
        case VALUE:
            json += "\"Value L\": ";
            json += ((Value)this.leftChild).printNode(depth + 1);
            break;
        case MATH:
            json += "\"MathNode L\": ";
            json += ((MathNode)this.leftChild).printNode(depth + 1);
            break;
        case MULT:
            json += "\"MathMult L\": ";
            json += ((MathMult)this.leftChild).printNode(depth + 1);
            break;
        case SUM:
            json += "\"MathSum L\": ";
            json += ((MathSum)this.leftChild).printNode(depth + 1);
            break;
        case FUNCCALL:
            json += "\"FuncCallNode L\": ";
            json += ((FuncCallNode)this.leftChild).printNode(depth + 1);
            break;
        default:
            json += "\"Error L\" : \"0\"";
            System.out.println("ERROR NODO IZQ de \"BoolMathNode\"");
            break;
    }

    for (int i = 0; i <= depth; i++){
      System.out.print("|  ");
    }

    System.out.print("|-- ");

    System.out.println(this.operator);
    json += ", \"Operator\" : \"" + this.operator + "\",";


    for (int i = 0; i <= depth; i++){
      System.out.print("|  ");
    }

    System.out.print("|-- ");
    
    switch(this.typeRight){
        case VALUE:
            json += "\"Value R\": ";
            json += ((Value)this.rightChild).printNode(depth + 1);
            break;
        case MATH:
            json += "\"MathNode R\": ";
            json += ((MathNode)this.rightChild).printNode(depth + 1);
            break;
        case MULT:
            json += "\"MathMult R\": ";
            json += ((MathMult)this.rightChild).printNode(depth + 1);
            break;
        case SUM:
            json += "\"MathSum R\": ";
            json += ((MathSum)this.rightChild).printNode(depth + 1);
            break;
        case FUNCCALL:
            json += "\"FuncCallNode R\": ";
            json += ((FuncCallNode)this.rightChild).printNode(depth + 1);
            break;
        default:
            json += "\"Error R\" : \"0\"";
            System.out.println("ERROR NODO DER de \"BoolMathNode\"");
            break;
    }
    json += "}";
    return json;
  }
}