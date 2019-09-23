import java.util.ArrayList;

public class ProgramNode{
  final int DECLNODE = 1;
  final int DECLARRAY = 2;

  final int FUNCNODE = 3;
  final int FUNCARRAY = 4;
    
  Value id;
  Object declarations;
  ArrayList<Object> functions;
  ArrayList<Object> statements;

  int declarationType;

  public ProgramNode(Value id, Object declarations, ArrayList<Object> functions, ArrayList<Object> statements){
    this.id = id;
    this.declarations = declarations;
    this.functions = functions;
    this.statements = statements;

    if(declarations instanceof DeclNode){
      this.declarationType = DECLNODE;    
    }else if(declarations instanceof ArrayList){
      this.declarationType = DECLARRAY;
    }else{
      System.out.println("TIPO \"DECLARATIONS\" NO ACEPTADO POR PROGRAM NODE");
      this.declarationType = 0;
    }



  }

  public String printNode(int depth){
        String json = "{";

        System.out.println("This ProgramNode holds: "); 

        for (int i = 0; i <= depth; i++){
            System.out.print("|  ");
        }

        System.out.print("|-- ");

        json += "\"Id\" : ";
        json += this.id.printNode(depth + 1);
        
        for (int i = 0; i <= depth; i++){
            System.out.print("|  ");
        }

        System.out.print("|-- ");

        
        if(this.declarationType == DECLNODE){
            for (int i = 0; i <= depth; i++){
                System.out.print("|  ");
            }

            System.out.print("|-- ");
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
                        System.out.print("|  ");
                    }

                    System.out.print("|-- ");
                    json += "\"DeclNode" + index + "\" : " + DN.printNode(depth + 1) + ",";
                    index ++;
                }
                json = json.substring(0, json.length()-1);
                json += "}";

            }
        }else{
        System.out.println("This shouldn't have happened...ProgramNode");
        }

        if( functions.size() > 0 ){
            for (int i = 0; i <= depth; i++){
                System.out.print("|  ");
            }
            
            System.out.print("|-- ");
            
            System.out.println("Functions: ");
            
            json += ", \"Functions/Procedures\" :  {";

            int index = 0;
            for( Object O : functions){
                for (int i = 0; i <= depth; i++){
                    System.out.print("|  ");
                }
                
                System.out.print("|-- ");
                if(O instanceof FunctionNode){
                    json += "\"FunctionNode\" : ";
                    json += ((FunctionNode)O).printNode(depth + 1) + ",";
                }else if (O instanceof ProcedureNode){
                    json += "\"ProcedureNode\" : ";
                    json += ((ProcedureNode)O).printNode(depth + 1) + ",";
                }else{
                    System.out.println("ERROR EN FUNCION/PROCEDIMIENTO " + index + " EN \"ProgramNode\"");
                }
                index ++;
            }
            json = json.substring(0, json.length()-1);
            json += "}";
        }

        json += ", \"Statements\" : [";

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
            }else if(e instanceof FuncCallNode){
                json += "\"FuncCallNode\": ";
                json += ((FuncCallNode)e).printNode(depth + 1);
            }else{
                json += "\"Error\" : \"0\"";
                System.out.println("ERROR NODO " + index + " de statements de \"ProgrammNode\"");
            }
            index++;
            json += "},";
        }

        json = json.substring(0, json.length()-1);
        json += "]";

        json += "}";
    
        return json;
    }
}