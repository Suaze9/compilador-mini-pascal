package arbol;

import java.util.ArrayList;
import java.io.Serializable;

public class RecordNode implements Serializable{
  ArrayList<DeclNode> decls; //ArrayList de todos los decls
  String name;

  public RecordNode(String name, ArrayList<DeclNode> nodes){
    this.name = name;
    this.decls = nodes;
  }

  public String printNode(int depth){
    String json = "{";

    //System.out.println("This RecordNode holds: "); 

    for (int i = 0; i <= depth; i++){
      //System.out.print("|  ");
    }

    //System.out.print("|-- ");

    json += "\"Decls\" : {";

    int index = 0;
    for(DeclNode decl : decls){
        for (int i = 0; i <= depth; i++){
            //System.out.print("|  ");
        }

        //System.out.print("|-- ");
        json += "\"DeclNode " + index + "\" : ";
        json += decl.printNode(depth + 1) + ",";
        index ++;
    }

    json = json.substring(0, json.length()-1);
    json += "},";
    
    json += "\"Name\": \"" + name + "\"";

    for (int i = 0; i <= depth; i++){
      //System.out.print("|  ");
    }

    //System.out.print("|-- ");

    //System.out.print("Name: " + name );
    
    json += "}";
    return json;
  }

}