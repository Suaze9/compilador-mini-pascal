import java.util.ArrayList;

public class FuncCallNode{


  Value id;
  ArrayList<Object> args;

  //Puede recibir MathNode, BoolNode, conststr, constchar

  public FuncCallNode(Value id, ArrayList<Object> args){
    this.id = id;
    this.args = args;
  }

  public String printNode(int depth){
    String json = "{";

    System.out.println("This FuncCallNode holds: "); 

    for (int i = 0; i <= depth; i++){
      System.out.print("|  ");
    }

    System.out.print("|-- ");

    
    json += "\"Value\" : ";
    json += this.id.printNode(depth + 1) + ",";
    
    for (int i = 0; i <= depth; i++){
      System.out.print("|  ");
    }

    System.out.print("|-- ");
    
    json += "\"Arguments\": {";

    int index = 0;
    for(Object e : args ){
        if(e instanceof MathNode){
            json += "\"MathNode\": ";
            json += ((MathNode)e).printNode(depth + 1) + ",";
        }else if(e instanceof BoolNode){
            json += "\"BoolNode\": ";
            json += ((BoolNode)e).printNode(depth + 1) + ",";
        }else if(e instanceof Character){
            json += "\"Character\": ";
            json += (Character)e + ",";
        }else if(e instanceof String){
            json += "\"String\": ";
            json += (String)e + ",";
        }else{
            json += "\"Error\" : \"0\",";
            System.out.println("ERROR NODO " + index + " de \"FuncCallNode\"");
        }
        index++;
    }

    json = json.substring(0, json.length()-1);
    json += "}";

    json += "}";
    return json;
  }
}