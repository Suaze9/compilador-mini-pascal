public class Value{
    
    final int NUM = 1;
    final int BOOL = 2;
    final int FUNCCALL = 4;
    
    Object content;
    int type;

  public Value(Object content){
    this.content = content;

    if (content instanceof Integer)
        this.type = NUM;
    else if (content instanceof Boolean)
        this.type = BOOL;
    else if (content instanceof FuncCallNode)
        this.type = FUNCCAlL;
    else{
        System.out.println("TIPO NO VALIDO ENTREGADO A NODO \"VALUE\"!!! ");
        this.type = 0;
    }
  }

  public String printNode(int depth){
    System.out.print("This Value holds: ");
    if(this.type == NUM){
      System.out.println((Integer)this.content);
    }
    else if(this.type == BOOL){
      System.out.println((Boolean)this.content);
    }else if(this.type == ID){
      System.out.println((String)this.content);
    }else if(this.type == FUNCCALL){
      for (int i = 0; i <= depth; i++){
        System.out.print("----");
      }
      ((FuncCallNode)this.content).printNode(depth + 1);
    }
    else{
      System.out.println("ERROR NODO de \"Value\"");
    }
  }
}