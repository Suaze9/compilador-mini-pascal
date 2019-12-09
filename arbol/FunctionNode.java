package arbol;

import java.util.ArrayList;
import java.io.Serializable;

public class FunctionNode extends Node implements Serializable{
  final int DECLNODE = 1;
  final int DECLARRAY = 2;

  public Value id;
  public ArrayList<ParamsNode> params;
  public Object declarations;
  public ArrayList<Object> statements;
  public String type;

  public int declarationType;

  public String tipoTabla;

  public FunctionNode(Value id, ArrayList<ParamsNode> params, Object declarations, ArrayList<Object> statements, String type, int fila, int columna){
      super(fila,columna);

    this.id = id;
    this.params = params;
    this.declarations = declarations;
    this.statements = statements;
    this.type = type;
    this.tipoTabla = "";
    
    if(declarations instanceof DeclNode){
      this.declarationType = DECLNODE;
    }else if(declarations instanceof ArrayList){
      this.declarationType = DECLARRAY;
    }else{
      //System.out.print("TIPO NO ACEPTADO POR PROCEDURE NODE");
      this.type = "NONE";
    }
  }

  public String printNode(int depth){
    
    String json = "{";

    //System.out.println("This FunctionNode holds: "); 

    for (int i = 0; i <= depth; i++){
      //System.out.print("|  ");
    }

    //System.out.print("|-- ");

    json += "\"Id\" : ";
    json += this.id.printNode(depth + 1) + ",";

    if(this.params.size() > 0){
        
        for (int i = 0; i <= depth; i++){
            //System.out.print("|  ");
        }

        //System.out.print("|-- ");

        //System.out.println("Params: ");
        json += "\"Params\" : {" ;

        int index = 0;
        for(ParamsNode PN : params){
            for (int i = 0; i <= depth; i++){
                //System.out.print("|  ");
            }

            //System.out.print("|-- ");
            json += "\"Param " + index + "\" : "; 
            json += PN.printNode(depth + 1) + ",";
            index ++;
        }
        json = json.substring(0, json.length()-1);
        json += "}";

    }

    if(this.declarationType == DECLNODE){
        for (int i = 0; i <= depth; i++){
            //System.out.print("|  ");
        }

        //System.out.print("|-- ");
        json += ",";
        json += "\"DeclNode\" : " + ((DeclNode)declarations).printNode(depth + 1);

    }else if(this.declarationType == DECLARRAY){
        ArrayList<DeclNode> decl = (ArrayList<DeclNode>) declarations;
        if(decl.size() > 0){
            json += ",";
            json += "\"DeclNodes\": {";

            int index = 0;
            for(DeclNode DN : decl){
                for (int i = 0; i <= depth; i++){
                    //System.out.print("|  ");
                }

                //System.out.print("|-- ");
                json += ",";
                json += "\"DeclNode" + index + "\" : " + DN.printNode(depth + 1) + ",";
                index ++;
            }
            json = json.substring(0, json.length()-1);
            json += "}";

        }
    }else{
      //System.out.println("This shouldn't have happened...FunctionNode");
    }

    json += ", \"Statements\" : [";

    int index = 0;
    for(Object e : statements ){
        for (int i = 0; i <= depth; i++){
            //System.out.print("|  ");
        }

        //System.out.print("|-- ");

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
        }else if(e instanceof FuncCallNode){
            json += "\"FuncCallNode\": ";
            json += ((FuncCallNode)e).printNode(depth + 1);
        }else{
            json += "\"Error\" : \"0\"";
            //System.out.println("ERROR NODO " + index + " de statements de \"FunctionNode\"");
        }
        index++;
        json += "},";
    }

    json = json.substring(0, json.length()-1);
    json += "], ";

    for (int i = 0; i <= depth; i++){
      //System.out.print("|  ");
    }

    //System.out.print("|-- ");


    //System.out.print("Type: " + type);
    json += "\"Type\" : ";
    json += "\"" + this.type + "\"";
    json += "}";

    return json;
  }
}