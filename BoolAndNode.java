import java.io.Serializable;

public class BoolAndNode implements Serializable{
  final int VALUE = 1;
  final int BOOLMATH = 2;
  final int BOOLAND = 3;
  final int BOOL = 4;
  final int FUNCCALL = 5;

  final int MATH = 6;
  final int MULT = 7;
  final int SUM = 8;
  
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
    else if (leftChild instanceof FuncCallNode){
      this.typeLeft = FUNCCALL;
    }
    else if (leftChild instanceof MathNode){
      this.typeLeft = MATH;
    }
    else if (leftChild instanceof MathMult){
      this.typeLeft = MULT;
    }
    else if (leftChild instanceof MathSum){
      this.typeLeft = SUM;
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
    else if (rightChild instanceof FuncCallNode){
      this.typeRight = FUNCCALL;
    }
    else if (rightChild instanceof MathNode){
      this.typeRight = MATH;
    }
    else if (rightChild instanceof MathMult){
      this.typeRight = MULT;
    }
    else if (rightChild instanceof MathSum){
      this.typeRight = SUM;
    }
    else{
      System.out.println("TIPO NO VALIDO ENTREGADO AL HIJO DER DE  NODO \"BoolAndNode\"!!! ");
      this.typeRight = 0;
    }
  }

  public String printNode(int depth){
    String json = "{";

    System.out.println("This BoolAndNode holds: "); 
    
    for (int i = 0; i <= depth; i++){
      System.out.print("|  ");
    }

    System.out.print("|-- ");
    
    switch(this.typeLeft){
        case VALUE:
            json += "\"Value L\": ";
            json += ((Value)this.leftChild).printNode(depth + 1);
            break;
        case BOOLAND:
            json += "\"BoolAndNode L\": ";
            json += ((BoolAndNode)this.leftChild).printNode(depth + 1);
            break;
        case BOOLMATH:
            json += "\"BoolMathNode L\": ";
            json += ((BoolMathNode)this.leftChild).printNode(depth + 1);
            break;
        case BOOL:
            json += "\"BoolNode L\": ";
            json += ((BoolNode)this.leftChild).printNode(depth + 1);
            break;
        case FUNCCALL:
            json += "\"FuncCallNode L\": ";
            json += ((FuncCallNode)this.leftChild).printNode(depth + 1);
            break;
        case MATH:
            json += "\"MathNode L\": ";
            json += ((MathNode)this.leftChild).printNode(depth + 1);
            break;
        case MULT:
            json += "\"MultNode L\": ";
            json += ((MathMult)this.leftChild).printNode(depth + 1);
            break;
        case SUM:
            json += "\"SumNode L\": ";
            json += ((MathSum)this.leftChild).printNode(depth + 1);
            break;
        default:
            json += "\"Error L\" : \"0\"";
            System.out.println("ERROR NODO IZQ de \"BoolAndNode\"");
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
        case BOOLAND:
            json += "\"BoolAndNode R\": ";
            json += ((BoolAndNode)this.rightChild).printNode(depth + 1);
            break;
        case BOOLMATH:
            json += "\"BoolMathNode R\": ";
            json += ((BoolMathNode)this.rightChild).printNode(depth + 1);
            break;
        case BOOL:
            json += "\"Bool R\": ";
            json += ((BoolNode)this.rightChild).printNode(depth + 1);
            break;
        case FUNCCALL:
            json += "\"FuncCallNode R\": ";
            json += ((FuncCallNode)this.rightChild).printNode(depth + 1);
            break;
        
        case MATH:
            json += "\"MathNode R\": ";
            json += ((MathNode)this.rightChild).printNode(depth + 1);
            break;
        case MULT:
            json += "\"MultNode R\": ";
            json += ((MathMult)this.rightChild).printNode(depth + 1);
            break;
        case SUM:
            json += "\"SumNode R\": ";
            json += ((MathSum)this.rightChild).printNode(depth + 1);
            break;
        default:
            json += "\"Error R\" : \"0\"";
            System.out.println("ERROR NODO DER de \"BoolAndNode\"");
            break;
    }
    json += "}";
    return json;
  }

}