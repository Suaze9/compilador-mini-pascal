import java.util.ArrayList;

public class IfNode{
  final int VALUE= 1;
  final int BOOL = 2;
  final int BOOLMATH = 3;
  final int BOOLAND = 4;
  final int BOOLOR = 5;
  
  final int SIMPLEIF = 1;
  final int IFELSE = 2;

  Object condition;
  ArrayList<Object> ifStatements;
  ArrayList<Object> elseStatements;
  int ifType;
  int conditionType;

  public IfNode(Object condition, ArrayList<Object> ifStatements){
    this.condition = condition;
    this.ifStatements = ifStatements;

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
    }

    this.ifType = SIMPLEIF;
}

  public IfNode(Object condition, ArrayList<Object> ifStatements, ArrayList<Object> elseStatements){
    this.condition = condition;
    this.ifStatements = ifStatements;
    this.elseStatements = elseStatements;

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
    }

    this.ifType = IFELSE;
  }

  public String printNode(int depth){
    String json = "{";

    System.out.println("This IfNode holds: "); 

    for (int i = 0; i <= depth; i++){
      System.out.print("|  ");
    }

    System.out.print("|-- ");
    
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
            System.out.println("ERROR NODO CONDITION de \"IfNode\"");
            break;
    }

    json += ", \"If\" : [";

    int index = 0;
    for(Object e : ifStatements ){
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
            System.out.println("ERROR NODO " + index + " de if de \"IfStatement\"");
        }
        index++;

        json += "},";
    }

    json = json.substring(0, json.length()-1);
    json += "]";

    if (this.ifType == IFELSE){
        json += ", \"Else\" : [";

        index = 0;
        for(Object e : elseStatements ){
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
                System.out.println("ERROR NODO " + index + " de else de \"IfStatement\"");
            }
            index++;
            json += "},";
        }

        json = json.substring(0, json.length()-1);
        json += "]";
    }
    
    json += "}";
    return json;
  }
}