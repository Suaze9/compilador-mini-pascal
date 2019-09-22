public class MathNode{
  final int VALUE = 1;
  final int MATH = 2;
  final int MULT = 3;
  final int SUM = 4;
  
  Object content;
  int type;

  public MathNode(Object content){
    this.content = content;
    
    if (content instanceof Value)
        this.type = VALUE;
    else if (content instanceof MathNode)
        this.type = MATH;
    else if (content instanceof MathMult)
        this.type = MULT;
    else if (content instanceof MathSum)
        this.type = SUM;
    else{
        System.out.println("TIPO NO VALIDO ENTREGADO A NODO \"MathNode\"!!! ");
        this.type = 0;
    }
  }

}