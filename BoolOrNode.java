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
    String json = "{";
    System.out.println("This BoolOrNode holds: " + depth); 
    
    for (int i = 0; i <= depth; i++){
      System.out.print("|  ");
    }

    System.out.print("|-- ");
    
    switch(this.typeLeft){
        case VALUE:
            json += "\"Value L\": ";
            json += ((Value)this.leftChild).printNode(depth + 1);
            break;
        case BOOLOR:
            json += "\"BoolOrNode L\": ";
            json += ((BoolOrNode)this.leftChild).printNode(depth + 1);
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
        default:
            json += "\"Error L\" : \"0\"";
            System.out.println("ERROR NODO IZQ de \"BoolOrNode\"");
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
        case BOOLOR:
            json += "\"BoolOrNode R\": ";
            json += ((BoolOrNode)this.rightChild).printNode(depth + 1);
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
            json += "\"BoolNode R\": ";
            json += ((BoolNode)this.rightChild).printNode(depth + 1);
            break;
        default:
            json += "\"Error R\" : \"0\"";
            System.out.println("ERROR NODO DER de \"BoolOrNode\"");
            break;
    }
    json += "}";
    return json;
  }

}