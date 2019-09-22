public class Value{
    
    final int NUM = 1;
    final int BOOL = 2;
    final int ID = 3;
    final int FUNCCALL = 4;
    
    Object content;
    int type;
    boolean not;

  public Value(Object content){
    this.content = content;

    if (content instanceof Integer)
        this.type = NUM;
    else if (content instanceof Boolean)
        this.type = BOOL;
    else if (content instanceof String)
        this.type = ID;
    else if (content instanceof FuncCallNode)
        this.type = FUNCCALL;
    else{
        System.out.println("TIPO NO VALIDO ENTREGADO A NODO \"VALUE\"!!! ");
        this.type = 0;
    }
    this.not = false;
  }

  public Value(Object content, boolean not){
    this.content = content;

    if (content instanceof Integer)
        this.type = NUM;
    else if (content instanceof Boolean)
        this.type = BOOL;
    else if(content instanceof String)
        this.type = ID;
    else if (content instanceof FuncCallNode)
        this.type = FUNCCALL;
    else{
        System.out.println("TIPO NO VALIDO ENTREGADO A NODO \"VALUE\"!!! ");
        this.type = 0;
    }
    this.not = not;
  }

  public String printNode(int depth){
    if(!this.not){
      System.out.print("This Value holds: ");
    }else{
      System.out.print("This NOT Value holds: ");
    }

    switch(this.type){
      case NUM:
        System.out.println((Integer)this.content);
        break;
      case BOOL:
        System.out.println((Boolean)this.content);
        break;
      case ID:
        System.out.println((String)this.content);
        break;
      case FUNCCALL:
        for (int i = 0; i <= depth; i++){
          System.out.print("----");
        }
        ((FuncCallNode)this.content).printNode(depth + 1);
        break;
      default:
        System.out.println("ERROR NODO de \"Value\"");
        break;
    }
  }
}