
//----------------------------------------------------
// The following code was generated by CUP v0.11b 20160615 (GIT 4ac7450)
//----------------------------------------------------

import java_cup.runtime.*;
import java_cup.runtime.XMLElement;

/** CUP v0.11b 20160615 (GIT 4ac7450) generated parser.
  */
@SuppressWarnings({"rawtypes"})
public class parser extends java_cup.runtime.lr_parser {

 public final Class getSymbolContainer() {
    return sym.class;
}

  /** Default constructor. */
  @Deprecated
  public parser() {super();}

  /** Constructor which sets the default scanner. */
  @Deprecated
  public parser(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public parser(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\023\000\002\002\003\000\002\002\004\000\002\002" +
    "\003\000\002\003\003\000\002\004\005\000\002\004\003" +
    "\000\002\005\005\000\002\005\003\000\002\006\003\000" +
    "\002\006\005\000\002\011\005\000\002\011\003\000\002" +
    "\007\003\000\002\007\003\000\002\007\003\000\002\007" +
    "\004\000\002\007\005\000\002\007\003\000\002\010\005" +
    "" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\040\000\016\005\014\017\005\020\012\021\011\036" +
    "\013\040\017\001\002\000\010\002\ufff6\024\ufff6\027\ufff6" +
    "\001\002\000\010\002\ufff5\024\ufff5\027\ufff5\001\002\000" +
    "\016\002\ufffc\024\ufffc\025\ufffc\026\041\027\ufffc\030\ufffc" +
    "\001\002\000\016\002\ufffa\024\ufffa\025\ufffa\026\ufffa\027" +
    "\ufffa\030\ufffa\001\002\000\014\002\ufffe\024\ufffe\025\037" +
    "\027\ufffe\030\ufffe\001\002\000\016\005\014\017\005\020" +
    "\012\021\011\036\013\040\017\001\002\000\010\002\ufff4" +
    "\024\ufff4\027\ufff4\001\002\000\010\002\ufff3\024\ufff3\027" +
    "\ufff3\001\002\000\016\002\ufff9\024\ufff9\025\ufff9\026\ufff9" +
    "\027\ufff9\030\ufff9\001\002\000\006\002\001\030\025\001" +
    "\002\000\004\002\033\001\002\000\016\005\014\017\005" +
    "\020\012\021\011\036\013\040\017\001\002\000\010\002" +
    "\ufff0\024\ufff0\027\ufff0\001\002\000\006\002\uffff\027\022" +
    "\001\002\000\016\005\014\017\005\020\012\021\011\036" +
    "\013\040\017\001\002\000\010\002\ufff7\024\ufff7\027\ufff7" +
    "\001\002\000\004\030\025\001\002\000\006\005\014\021" +
    "\026\001\002\000\006\005\014\021\026\001\002\000\010" +
    "\002\uffef\024\uffef\027\uffef\001\002\000\004\024\031\001" +
    "\002\000\016\002\ufff8\024\ufff8\025\ufff8\026\ufff8\027\ufff8" +
    "\030\ufff8\001\002\000\010\002\ufff2\024\ufff2\027\ufff2\001" +
    "\002\000\004\002\000\001\002\000\006\024\031\030\025" +
    "\001\002\000\006\024\036\027\022\001\002\000\010\002" +
    "\ufff1\024\ufff1\027\ufff1\001\002\000\006\005\014\021\026" +
    "\001\002\000\016\002\ufffd\024\ufffd\025\ufffd\026\041\027" +
    "\ufffd\030\ufffd\001\002\000\006\005\014\021\026\001\002" +
    "\000\016\002\ufffb\024\ufffb\025\ufffb\026\ufffb\027\ufffb\030" +
    "\ufffb\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\040\000\022\002\015\003\014\004\007\005\005\006" +
    "\006\007\003\010\017\011\020\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\020\003\033\004\007\005\005\006\006" +
    "\007\003\010\017\011\034\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\016\003\023\004\007\005\005\006\006\007" +
    "\031\010\017\001\001\000\002\001\001\000\002\001\001" +
    "\000\016\003\023\004\007\005\005\006\006\007\022\010" +
    "\017\001\001\000\002\001\001\000\002\001\001\000\012" +
    "\003\026\004\007\005\005\006\006\001\001\000\012\003" +
    "\027\004\007\005\005\006\006\001\001\000\002\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\006\005\037\006\006\001\001\000\002\001" +
    "\001\000\004\006\041\001\001\000\002\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$parser$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$parser$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$parser$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 1;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}


/** Cup generated class to encapsulate user supplied action code.*/
@SuppressWarnings({"rawtypes", "unchecked", "unused"})
class CUP$parser$actions {
  private final parser parser;

  /** Constructor */
  CUP$parser$actions(parser parser) {
    this.parser = parser;
  }

  /** Method 0 with the actual generated action code for actions 0 to 300. */
  public final java_cup.runtime.Symbol CUP$parser$do_action_part00000000(
    int                        CUP$parser$act_num,
    java_cup.runtime.lr_parser CUP$parser$parser,
    java.util.Stack            CUP$parser$stack,
    int                        CUP$parser$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$parser$result;

      /* select the action based on the action number */
      switch (CUP$parser$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // expr ::= math 
            {
              Expression RESULT =null;
		int mleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int mright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Integer m = (Integer)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		
    System.out.println("Math is int: " + m.intValue());
    RESULT = new Expression(m.intValue());

              CUP$parser$result = parser.getSymbolFactory().newSymbol("expr",0, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // $START ::= expr EOF 
            {
              Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		Expression start_val = (Expression)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		RESULT = start_val;
              CUP$parser$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$parser$parser.done_parsing();
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // expr ::= rel 
            {
              Expression RESULT =null;
		int rleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int rright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Boolean r = (Boolean)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		
    System.out.println("Rel is boolean: " + r.booleanValue());
    RESULT = new Expression(r.booleanValue());


              CUP$parser$result = parser.getSymbolFactory().newSymbol("expr",0, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // math ::= sum 
            {
              Integer RESULT =null;
		int sleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int sright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Integer s = (Integer)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		
    System.out.print("puto el que lo lea :v: " + s.intValue());
    RESULT = s.intValue();

              CUP$parser$result = parser.getSymbolFactory().newSymbol("math",1, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // sum ::= sum OPSUM mult 
            {
              Integer RESULT =null;
		int sleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int sright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		Integer s = (Integer)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		int oleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int oright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		Object o = (Object)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		int mleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int mright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Integer m = (Integer)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		
    switch((String)o){
        case "+":{
            RESULT = s.intValue() + m.intValue();
            break;
        }
        case "-":{
            RESULT = s.intValue() - m.intValue();
            break;
        }
        default:{
            ///////////////////////////////////
            break;
        }
    }

              CUP$parser$result = parser.getSymbolFactory().newSymbol("sum",2, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // sum ::= mult 
            {
              Integer RESULT =null;
		int mleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int mright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Integer m = (Integer)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		
    RESULT = m.intValue();

              CUP$parser$result = parser.getSymbolFactory().newSymbol("sum",2, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // mult ::= mult OPMULT num 
            {
              Integer RESULT =null;
		int mleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int mright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		Integer m = (Integer)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		int oleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int oright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		Object o = (Object)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		int nleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int nright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Integer n = (Integer)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		
    switch((String)o){
        case "*":{
            RESULT = m.intValue() * n.intValue();
            break;
        }
        case "/":
        case "div":{
            RESULT = new Integer((int)(m.intValue() / n.intValue()));
            break;
        }
        case "mod":{
            RESULT = m.intValue() % n.intValue();
            break;
        }
        default:{
            ///////////////////////////////////////////////////////
            break;
        }
    }

              CUP$parser$result = parser.getSymbolFactory().newSymbol("mult",3, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // mult ::= num 
            {
              Integer RESULT =null;
		int nleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int nright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Integer n = (Integer)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		
    RESULT = n.intValue();

              CUP$parser$result = parser.getSymbolFactory().newSymbol("mult",3, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // num ::= NUMS 
            {
              Integer RESULT =null;
		int nleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int nright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		String n = (String)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		
    System.out.println("N: " + (String)n);
    RESULT = new Integer((String)n);

              CUP$parser$result = parser.getSymbolFactory().newSymbol("num",4, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // num ::= PARIZQ math PARDER 
            {
              Integer RESULT =null;
		int mleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int mright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		Integer m = (Integer)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		
    RESULT = m.intValue();

              CUP$parser$result = parser.getSymbolFactory().newSymbol("num",4, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // rel ::= rel OPAO bool 
            {
              Boolean RESULT =null;
		int rleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int rright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		Boolean r = (Boolean)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		int oleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int oright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		Object o = (Object)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		int bleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int bright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Boolean b = (Boolean)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		
    //<>|=|>|<|>=|<=|[aA][nN][dD]|[oO][rR]|[nN][oO][tT]
    switch((String)o){
        case "and":{
            RESULT = new Boolean(r.booleanValue() && b.booleanValue());
            break;
        }
        case "or":{
            RESULT = new Boolean(r.booleanValue() || b.booleanValue());
            break;
        }
    }

              CUP$parser$result = parser.getSymbolFactory().newSymbol("rel",7, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // rel ::= bool 
            {
              Boolean RESULT =null;
		int bleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int bright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Boolean b = (Boolean)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		
    RESULT = b.booleanValue();

              CUP$parser$result = parser.getSymbolFactory().newSymbol("rel",7, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // bool ::= TRUE 
            {
              Boolean RESULT =null;
		
    RESULT = new Boolean(true);

              CUP$parser$result = parser.getSymbolFactory().newSymbol("bool",5, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // bool ::= FALSE 
            {
              Boolean RESULT =null;
		
    RESULT = new Boolean(false);

              CUP$parser$result = parser.getSymbolFactory().newSymbol("bool",5, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // bool ::= ID 
            {
              Boolean RESULT =null;
		int ileft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int iright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Object i = (Object)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		
    ///////////////////////////////////////////////////////////////////////

              CUP$parser$result = parser.getSymbolFactory().newSymbol("bool",5, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // bool ::= NOT bool 
            {
              Boolean RESULT =null;
		int bleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int bright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Boolean b = (Boolean)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		
    RESULT = !b.booleanValue();

              CUP$parser$result = parser.getSymbolFactory().newSymbol("bool",5, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // bool ::= PARIZQ rel PARDER 
            {
              Boolean RESULT =null;
		int rleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int rright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		Boolean r = (Boolean)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		
    RESULT = r.booleanValue();

              CUP$parser$result = parser.getSymbolFactory().newSymbol("bool",5, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 17: // bool ::= boolmath 
            {
              Boolean RESULT =null;
		int bleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int bright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Boolean b = (Boolean)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		
    RESULT = b.booleanValue();

              CUP$parser$result = parser.getSymbolFactory().newSymbol("bool",5, ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 18: // boolmath ::= math OPREL math 
            {
              Boolean RESULT =null;
		int bleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).left;
		int bright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)).right;
		Integer b = (Integer)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-2)).value;
		int oleft = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).left;
		int oright = ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-1)).right;
		Object o = (Object)((java_cup.runtime.Symbol) CUP$parser$stack.elementAt(CUP$parser$top-1)).value;
		int mleft = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).left;
		int mright = ((java_cup.runtime.Symbol)CUP$parser$stack.peek()).right;
		Integer m = (Integer)((java_cup.runtime.Symbol) CUP$parser$stack.peek()).value;
		
    switch((String)o){
        case "<>":{
            RESULT = new Boolean(b.intValue() != m.intValue());
            break;
        }
        case "=":{
            RESULT = new Boolean(b.intValue() == m.intValue());
            break;
        }
        case "<":{
            RESULT = new Boolean(b.intValue() < m.intValue());
            break;
        }
        case ">":{
            RESULT = new Boolean(b.intValue() > m.intValue());
            break;
        }
        case ">=":{
            RESULT = new Boolean(b.intValue() >= m.intValue());
            break;
        }
        case "<=":{
            RESULT = new Boolean(b.intValue() <= m.intValue());
            break;
        }
        default:{
            //////////////////////////////////////////////////////////////////
            break;
        }
    }

              CUP$parser$result = parser.getSymbolFactory().newSymbol("boolmath",6, ((java_cup.runtime.Symbol)CUP$parser$stack.elementAt(CUP$parser$top-2)), ((java_cup.runtime.Symbol)CUP$parser$stack.peek()), RESULT);
            }
          return CUP$parser$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number "+CUP$parser$act_num+"found in internal parse table");

        }
    } /* end of method */

  /** Method splitting the generated action code into several parts. */
  public final java_cup.runtime.Symbol CUP$parser$do_action(
    int                        CUP$parser$act_num,
    java_cup.runtime.lr_parser CUP$parser$parser,
    java.util.Stack            CUP$parser$stack,
    int                        CUP$parser$top)
    throws java.lang.Exception
    {
              return CUP$parser$do_action_part00000000(
                               CUP$parser$act_num,
                               CUP$parser$parser,
                               CUP$parser$stack,
                               CUP$parser$top);
    }
}

}