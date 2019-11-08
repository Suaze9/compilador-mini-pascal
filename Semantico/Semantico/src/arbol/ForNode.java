package arbol;

import java.util.ArrayList;
import java.io.Serializable;

public class ForNode implements Serializable{
  final int VALUE = 1;
  final int MATH = 2;
  final int MULT = 3;
  final int SUM = 4;
  
  AssigNode assig;
  Object condition;
  ArrayList<Object> statements;

  int conditionType;

  public ForNode(AssigNode assig, Object condition, ArrayList<Object> statements){
    this.assig = assig;
    this.condition = condition;
    this.statements = statements;

    if(condition instanceof Value){
      this.conditionType = VALUE;
    }else if(condition instanceof MathNode){
      this.conditionType = MATH;
    }else if(condition instanceof MathMult){
      this.conditionType = MULT;
    }else if(condition instanceof MathSum){
      this.conditionType = SUM;
    }else{
      System.out.println("TIPO NO ACEPTADO POR FOR NODE");
      this.conditionType = 0;
    }
  }

  public String printNode(int depth){
    String json = "{";

    
    System.out.println("This ForNode holds: "); 

    for (int i = 0; i <= depth; i++){
      System.out.print("|  ");
    }

    System.out.print("|-- ");

    json += "\"AssigNode\" : " + this.assig.printNode(depth + 1) + ",";

    for (int i = 0; i <= depth; i++){
      System.out.print("|  ");
    }

    System.out.print("|-- ");
    
    switch(this.conditionType){
        case VALUE:
            json += "\"Value R\": ";
            json += ((Value)this.condition).printNode(depth + 1);
            break;
        case MATH:
            json += "\"MathNode R\": ";
            json += ((MathNode)this.condition).printNode(depth + 1);
            break;
        case MULT:
            json += "\"MathMult R\": ";
            json += ((MathMult)this.condition).printNode(depth + 1);
            break;
        case SUM:
            json += "\"MathSum R\": ";
            json += ((MathSum)this.condition).printNode(depth + 1);
            break;
        default:
            json += "\"Error\" : \"0\"";
            System.out.println("ERROR NODO DE CONDICION DE \"ForNode\"");
            break;
    }

    json += ", \"Statements\" : [";

    int index = 0;
    for(Object e : statements ){
        for (int i = 0; i <= depth; i++){
            System.out.print("|  ");
        }

        json += "{";

        System.out.print("|-- ");
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
        }else if(e instanceof FuncCallNode){
            json += "\"FuncCallNode\": ";
            json += ((FuncCallNode)e).printNode(depth + 1);
        }else{
            json += "\"Error\" : \"0\"";
            System.out.println("ERROR NODO " + index + " de else de \"IfStatement\"");
        }
        json += "},";
        index++;
    }

    json = json.substring(0, json.length()-1);
    json += "]";

    json += "}";

    return json;
  }
}