public class Expression{
  boolean isMath;
  boolean booleanValue;
  int intValue;

  public Expression(boolean booleanValue){
    this.booleanValue = booleanValue;
    //intValue = null;
    isMath = false;
  }

  public Expression(int intValue){
    //booleanValue = null;
    this.intValue = intValue;
    isMath = false;
  }

  public boolean booleanValue(){
    return this.booleanValue;
  }

  public int intValue(){
    return this.intValue;
  }
}