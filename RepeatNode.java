import java.util.ArrayList;

public class RepeatNode{
  final int VALUE = 1;
  final int BOOL = 2;
  final int BOOLMATH = 3;
  final int BOOLAND = 4;
  final int BOOLOR = 5;

  ArrayList<Object> statements;
  Object condition;
  int conditionType;

  public RepeatNode(ArrayList<Object> statements, Object condition){
    this.statements = statements;
    this.condition = condition;

    if(condition instanceof Value){
      this.conditionType = VALUE;
    }else if(condition instanceof BoolNode){
      this.conditionType = BOOL;
    }else if(condition instanceof BoolMathNode){
      this.conditionType = BOOLMATH;
    }else if(condition instanceof BoolAndNode){
      this.conditionType = BOOLAND;
    }else if(condition instanceof BoolOrNode){
      this.conditionType = BOOLOR;
    }else{
      System.out.println("TIPO NO ACEPTADO POR REPEAT NODE");
      this.conditionType = 0;
    }
  }

  public String printNode(int depth){
    String json = "{";

    System.out.println("This RepeatNode holds: "); 

    for (int i = 0; i <= depth; i++){
      System.out.print("|  ");
    }

    System.out.print("|-- ");

    json += " \"Repeat\" : [";

    int index = 0;
    for(Object e : statements ){
        for (int i = 0; i <= depth; i++){
            System.out.print("|  ");
        }

        System.out.print("|-- ");

        json += "{";
        
        if(e instanceof IfNode){
            json += "\"IfNode\": ";
            json += ((IfNode)e).printNode(depth + 1);
        }else if(e instanceof WhileNode){
            json += "\"WhileNode\": ";
            json += ((WhileNode)e).printNode(depth + 1);
        }else if(e instanceof ForNode){
            json += "\"ForNode\": ";
            json += ((ForNode)e).printNode(depth + 1);
        }else if(e instanceof RepeatNode){
            json += "\"RepeatNode\": ";
            json += ((RepeatNode)e).printNode(depth + 1);
        }else if(e instanceof ReadNode){
            json += "\"ReadNode\": ";
            json += ((ReadNode)e).printNode(depth + 1);
        }else if(e instanceof WriteNode){
            json += "\"WriteNode\": ";
            json += ((WriteNode)e).printNode(depth + 1);
        }else if(e instanceof AssigNode){
            json += "\"AssigNode\": ";
            json += ((AssigNode)e).printNode(depth + 1);
        }else{
            json += "\"Error\" : \"0\"";
            System.out.println("ERROR NODO " + index + " de \"RepeatNode\"");
        }
        index++;
        json += "},";
    }

    json = json.substring(0, json.length()-1);
    json += "], ";
    
    switch(this.conditionType){
        case VALUE:
            json += "\"Value\": ";
            json += ((Value)this.condition).printNode(depth + 1);
            break;
        case BOOL:
            json += "\"BoolNode\": ";
            json += ((BoolNode)this.condition).printNode(depth + 1);
            break;
        case BOOLMATH:
            json += "\"BoolMathNode\": ";
            json += ((BoolMathNode)this.condition).printNode(depth + 1);
            break;
        case BOOLAND:
            json += "\"BoolAndNode\": ";
            json += ((BoolAndNode)this.condition).printNode(depth + 1);
            break;
        case BOOLOR:
            json += "\"BoolOrNode\": ";
            json += ((BoolOrNode)this.condition).printNode(depth + 1);
            break;
        default:
            json += "\"Error\" : \"0\"";
            System.out.println("ERROR NODO CONDITION de \"RepeatNode\"");
            break;
    }

    json += "}";

    return json;
  }
}