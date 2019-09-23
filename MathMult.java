public class MathMult{

  final int VALUE = 1;
  final int MATHNODE = 2;
  final int MATHMULT = 3;
  final int FUNCCALL = 4;

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
    else if (leftChild instanceof MathMult){
      this.typeLeft = MATHMULT;
    }
    else if (leftChild instanceof FuncCallNode){
      this.typeLeft = FUNCCALL;
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
    else if (rightChild instanceof MathMult){
      this.typeRight = MATHMULT;
    }
    else if (rightChild instanceof FuncCallNode){
      this.typeRight = FUNCCALL;
    }
    else{
      System.out.println("TIPO NO VALIDO ENTREGADO AL HIJO DER DE  NODO \"MathMult\"!!! ");
      this.typeRight = 0;
    }

  }

  public String printNode(int depth){
    String json = "{";
    System.out.println("This MathMult holds: "); 
    
    for (int i = 0; i <= depth; i++){
      System.out.print("|  ");
    }

    System.out.print("|-- ");

    switch(this.typeLeft){
      case VALUE:
        json += "\"Value L\": ";
        json += ((Value)this.leftChild).printNode(depth + 1);
        break;
      case MATHNODE:
        json += "\"MathNode L\": ";
        json += ((MathNode)this.leftChild).printNode(depth + 1);
        break;
      case MATHMULT:
        json += "\"MathMult L\": ";
        json += ((MathMult)this.leftChild).printNode(depth + 1);
        break;
      case FUNCCALL:
        json += "\"FuncCallNode L\": ";
        json += ((FuncCallNode)this.leftChild).printNode(depth + 1);
        break;
      default:
        json += "\"Error L\" : \"0\"";
        System.out.println("ERROR NODO IZQ de \"MathMult\"");
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
      case MATHNODE:
        json += "\"MathNode R\": ";
        json += ((MathNode)this.rightChild).printNode(depth + 1);
        break;
      case MATHMULT:
          json += "\"MathMult R\": ";
          json += ((MathMult)this.rightChild).printNode(depth + 1);
        break;
      case FUNCCALL:
        json += "\"FuncCallNode L\": ";
        json += ((FuncCallNode)this.rightChild).printNode(depth + 1);
        break;
      default:
        json += "\"Error R\" : \"0\"";
        System.out.println("ERROR NODO DER de \"MathMult\"");
        break;
    }

    json += "}";
    return json;
  }
}