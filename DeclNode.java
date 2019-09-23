import java.util.ArrayList;

public class DeclNode{
  ArrayList<Value> ids; //ArrayList de todos los ids
  String type;

  public DeclNode(String type){
    this.type = type;
    this.ids = new ArrayList<Value>();
  }

  public void push(String id){
    ids.add(new Value(id));
  }

  public String printNode(int depth){
    String json = "{";

    System.out.println("This DeclNode holds: "); 

    for (int i = 0; i <= depth; i++){
      System.out.print("|  ");
    }

    System.out.print("|-- ");

    json += "\"IDs\" : {";

    int index = 0;
    for(Value id : ids){
        for (int i = 0; i <= depth; i++){
            System.out.print("|  ");
        }

        System.out.print("|-- ");
        json += "\"ID " + index + "\" : ";
        json += id.printNode(depth + 1) + ",";
        index ++;
    }

    json = json.substring(0, json.length()-1);
    json += "},";
    
    json += "\"Type\": \"" + type + "\"";

    for (int i = 0; i <= depth; i++){
      System.out.print("|  ");
    }

    System.out.print("|-- ");

    System.out.print("Type: " + type );
    
    json += "}";
    return json;
  }

}