import java.io.*;
import arbol.*;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
   
public class Main {

  public static ProgramNode root;
  public static TablaSym tabla;

  public static Map<String, Integer> tipos;
  public static Map<Integer, String> tiposInv;

  public static int offset;


  static public void main(String argv[]) {    
    /* Start the parser */
    try {
      parser p = new parser(new lexer(new FileReader(argv[0])));
      p.parse();

      tipos = new HashMap<>();
      tipos.put("INT", 4);
      tipos.put("CHAR", 1);
      tipos.put("BOOLEAN", 4);
      tiposInv = new HashMap<>();
      tiposInv.put(1, "INT");
      tiposInv.put(2, "BOOL");
      tiposInv.put(3, "ID");
      offset = 0;
      leerArbol();
      crearTabla();

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public static void printError(String esperado, String recibido, int fila, int columna){
      System.out.println("\nError de tipos:\n\nSe esperaba el tipo: " + esperado + "\n\tPero se recibio el tipo: " + recibido + "\n\tLinea: " + fila + "\tColumna: " + columna);
  }

  public static void printErrorId(String id, int fila, int columna){
      System.out.println("\nError de tipos:\n\nNo se declaro la variable/funcion: " + id + "\n\tLinea: " + fila + "\tColumna: " + columna);
  }

  public static void printErrorInesperado(int fila, int columna){
    System.out.println("\nError de tipos inesperado:\n\tLinea: " + fila + "\tColumna: " + columna);    
  }

    public static void leerArbol(){
        try(ObjectInputStream oi = new ObjectInputStream(new FileInputStream(new File("../Semantico/arbolito.bebe")))){
            root = (ProgramNode) oi.readObject();
            //System.out.println("Olo: \n\n" + root.printNode(1));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static boolean stepBack(){
        if(tabla.papi != null){
            tabla = tabla.papi;
            return true;
        }else{
            System.out.println("Error!! La tabla actual es la raiz!!!");
            return false;
        }
    }

    public static void crearTabla(){
        tabla = new TablaSym();
        records(root.records);
        declaraciones((ArrayList<DeclNode>)root.declarations);
        funciones(root.functions);
        comprobacionTipos(root.statements);
    }

    public static boolean comprobacionTipos(ArrayList<Object> states){
        boolean valid = true;
        for(Object obj : states){
            if(obj instanceof IfNode){
                valid = valid && verifyIf((IfNode)obj);
            }else if(obj instanceof WhileNode){
                valid = valid && verifyWhile((WhileNode)obj);
            }else if(obj instanceof ForNode){
                valid = valid && verifyFor((ForNode)obj);
            }else if(obj instanceof RepeatNode){
                valid = valid && verifyRepeat((RepeatNode)obj);
            }else if(obj instanceof ReadNode){
                valid = valid && verifyRead((ReadNode)obj);
            }else if(obj instanceof WriteNode){
                valid = valid && verifyWrite((WriteNode)obj);
            }else if(obj instanceof AssigNode){
                valid = valid && verifyAsignType((AssigNode)obj);
            }else if(obj instanceof FuncCallNode){
                valid = valid && !verifyFuncCall((FuncCallNode)obj).equals("ERROR");
            }else{
                System.out.println("Error comprobacion Tipos");
                valid = false;
            }
        }
        return valid;
    }

    public static void records(ArrayList<RecordNode> recs){
        for(RecordNode record : recs){

            String nom = record.name;
            ArrayList<DeclNode> decls = record.decls;

            tabla.add(nom, "RECORD", -1);

            declaracionesRecord(decls, nom);
            
            tabla.print(0);

        }
    }
    
    public static void funciones(ArrayList<Object> funcs){
        for(Object funcproc : funcs){
            Value id;
            ArrayList<ParamsNode> params;
            Object declarations;
            ArrayList<Object> statements;
            String funcprocType = "";

            if(funcproc instanceof FunctionNode){
                FunctionNode func = (FunctionNode)funcproc;
                id = func.id;
                params = func.params;
                declarations = func.declarations;
                statements = func.statements;
                funcprocType = func.type;
            }else if (funcproc instanceof ProcedureNode){
                ProcedureNode proc = (ProcedureNode)funcproc;
                id = proc.id;
                params = proc.params;
                declarations = proc.declarations;
                statements = proc.statements;
                funcprocType = "NULL";
            }else{
                System.out.println("Error de tipo funcion");
                return;
            }
            String type = "(";
            for(ParamsNode param : params){
                /*
                    final int NUM = 1;
                    final int BOOL = 2;
                    final int ID = 3;
                    final int FUNCCALL = 4;
                */

                String tipo = param.type;
                int size = tipos.get(tipo);

                for(Value val : param.ids){
                    String idval = (String)val.content;
                    Tupla tuplita = new Tupla(idval, tipo, offset);
                    offset += size;

                    if(!(params.indexOf(param) == params.size() - 1 && param.ids.indexOf(val) == param.ids.size() - 1)){
                        type += tipo + " x ";
                    }else{
                        type += tipo;
                    }

                }

            }
            type += ") -> " + funcprocType;
            

            tabla.add((String)id.content, type, -1);

        }
        for(Object funcproc : funcs){
            Object declarations;
            ArrayList<ParamsNode> params;
            ArrayList<Object> statements;


            if(funcproc instanceof FunctionNode){
                FunctionNode func = (FunctionNode)funcproc;
                declarations = func.declarations;
                params = func.params;
                statements = func.statements;
            }else if (funcproc instanceof ProcedureNode){
                ProcedureNode proc = (ProcedureNode)funcproc;
                declarations = proc.declarations;
                params = proc.params;
                statements = proc.statements;
            }else{
                return;
            }
            
            int tempOffset = offset;
            offset = 0;
            
            TablaSym ts = new TablaSym(tabla);
            tabla = ts;

            for(ParamsNode param : params){
                /*
                    final int NUM = 1;
                    final int BOOL = 2;
                    final int ID = 3;
                    final int FUNCCALL = 4;
                */

                String tipo = param.type;
                int size = tipos.get(tipo);

                for(Value val : param.ids){
                    String idval = (String)val.content;
                    tabla.add(idval, tipo, offset);
                    offset += size;
                }

            }

            declaraciones((ArrayList<DeclNode>)declarations);

            comprobacionTipos(statements);

            if(stepBack())
                offset = tempOffset;
        }
    }

    public static void declaraciones(ArrayList<DeclNode> decls){

        for(DeclNode decl : decls){

            String type = decl.type;
            
            int size = 0;

            if(type.equals("INT")){
              size = 4;
            }else if(type.equals("BOOLEAN")){
              size = 1;
            }else if(type.equals("CHAR")){
              size = 4;
            }else{
              size = -2;
            }


            for(Value val : decl.ids){

                String id = (String)val.content;
                System.out.println("Id: " + id);
                tabla.add(id, type, offset);
                offset += size;

            }
        }
        tabla.print(0);
    }

    public static void declaracionesRecord(ArrayList<DeclNode> decls, String nom){

        for(DeclNode decl : decls){

            String type = nom + "." + decl.type;
            
            int size = 0;

            if(type.equals("INT")){
              size = 4;
            }else if(type.equals("BOOLEAN")){
              size = 1;
            }else if(type.equals("CHAR")){
              size = 4;
            }else{
              size = -2;
            }

            type = nom + "." + decl.type;

            for(Value val : decl.ids){

                String id = (String)val.content;
                tabla.add(id, type, offset);
                offset += size;

            }
        }
        //tabla.print(0);
    }

    public static String getFuncType(ArrayList<Object> args){

        String type = "(";
        for(Object attr : args){

            if(attr instanceof AttrNode){
                /*
                final int STR = 1;
                final int CHAR = 2;
                final int VALUE = 3;
                final int MATHNODE = 4;
                final int MATHMULT = 5;
                final int MATHSUM = 6;
                final int BOOL = 7;
                final int BOOLMATH = 8;
                final int BOOLAND = 9;
                final int BOOLOR = 10;
                */

                Object o = ((AttrNode)attr).attr;
                int tipo = ((AttrNode)attr).type;

                switch(tipo){
                    case 1:{
                        type += "STR";
                        break;
                    }
                    case 2:{
                        type += "CHAR";
                        break;
                    }
                    case 3:{
                        Value val = (Value)o;
                        if(val.type == 1){
                            type += "INT";
                        } else if(val.type == 2){
                            type += "BOOLEAN";
                        } else if(val.type == 3){
                            Object[] tup = tabla.buscarTupla((String)val.content, 0);
                            if(tup != null){
                                type += ((Tupla)tup[0]).type;
                            }else{
                                printErrorId((String)val.content, val.fila, val.columna);
                                type += "ERROR";
                            }
                        } else if(val.type == 4){
                            type += verifyFuncCall((FuncCallNode)val.content);
                        } else if(val.type == 5){
                            type += "CHAR";
                        } else {
                            printErrorInesperado(val.fila, val.columna);
                            return "ERROR";
                        }
                        break;
                    }
                    case 4:{
                        System.out.println("Se recibió un MathNode...que ondas");
                        //ya no se usa MATHNODE
                        break;
                    }
                    case 5:{
                        if(verifyMultTypes((MathMult)o)){
                            type += "INT";
                        }else{
                            type += "ERROR";
                        }
                        break;
                    }
                    case 6:{
                        if(verifySumTypes((MathSum)o)){
                            type += "INT";
                        }else{
                            type += "ERROR";
                        }
                        break;
                    }
                    case 7:{
                        System.out.println("Se recibió un BoolNode...que ondas");
                        //ya no se usa BOOL
                        break;
                    }
                    case 8:{
                        if(verifyBoolMathTypes((BoolMathNode)o)){
                            type += "BOOLEAN";
                        }else{
                            type += "ERROR";
                        }
                        break;
                    }
                    case 9:{
                        if(verifyBoolAndTypes((BoolAndNode)o)){
                            type += "BOOLEAN";
                        }else{
                            type += "ERROR";
                        }
                        break;
                    }
                    case 10:{
                        if(verifyBoolOrTypes((BoolOrNode)o)){
                            type += "BOOLEAN";
                        }else{
                            type += "ERROR";
                        }
                        break;
                    }
                }

            }

            if(args.indexOf(attr) != args.size() - 1){
                type += " x ";
            }

            /*
            String tipo = params.type;
            int size = tipos.get(tipo);

            for(Value val : params.ids){
                String idval = (String)val.content;
                Tupla tuplita = new Tupla(idval, tipo, offset);
                offset += size;

                if(func.indexOf(params) != func.size() - 1 && params.ids.indexOf(val) != params.ids.size() - 1){
                    type += tipo + " x ";
                }else{
                    type += tipo;
                }

            }
            */
        }
        type += ")";

        return type;

    }

    public static String verifyFuncCall(FuncCallNode val){
        
        String name = (String)((Value)val.id).content;
        
        String type = getFuncType(val.args);

        //System.out.println("TIPO: " + type);

        Object[] res = tabla.buscarTuplaFunc(name, type, 0);

        if(res != null){
            int index = (((Tupla)res[0]).type).indexOf(" -> ");
            String tipoRetorno = (((Tupla)res[0]).type).substring( index + 4 );
            return tipoRetorno;
        }

        printErrorId(name, val.fila, val.columna);

        return "ERROR";
    }

    public static boolean verifyProcCall(FuncCallNode val){
        
        String name = (String)((Value)val.id).content;
        
        String type = getFuncType(val.args);

        Object[] res = tabla.buscarTuplaFunc(name, type, 0);

        if(res != null){
          return true;
        }else{
          printErrorId(name, val.fila, val.columna);
          return false;
        }

         
    }

    public static boolean verifyConditionBool(int condtype, Object condition){
        boolean  condicion = false;
        switch(condtype){
            case 1:{
                Value val = (Value)condition;
                if(val.type == 1){
                    condicion =  false;
                }else if(val.type == 2){
                    condicion = true;
                }else if(val.type == 3){
                    Object[] tup = tabla.buscarTupla((String)val.content, 0);
                    if(tup != null){
                        if(((Tupla)tup[0]).type.equals("BOOLEAN")){
                            condicion = true;
                        }else{
                            condicion = false;
                            printError("BOOELAN", ((Tupla)tup[0]).type, val.fila, val.columna);
                        }
                    }else{
                        printErrorId((String)val.content, val.fila, val.columna);
                        condicion = false;
                    }
                }else if(val.type == 4){
                    String ret = verifyFuncCall((FuncCallNode)val.content);
                    if(ret.equals("BOOELAN")){
                        condicion = true;
                    }else{
                        condicion = false;
                        if(!ret.equals("ERROR"))
                            printError("BOOLEAN", ret, val.fila, val.columna);
                    }
                }else if(val.type == 5){
                    condicion = false;
                }else{
                    //System.out.println("Buscando el tipo, algo salió mal en condicion IF");
                    printErrorInesperado(val.fila, val.columna);
                    condicion = false;
                }
                break;
            }
            case 2:{
                System.out.println("Recibió Bool??");
                condicion = false;
                break;
            }
            case 3:{
                condicion = verifyBoolMathTypes((BoolMathNode)condition);
                break;
            }
            case 4:{
                condicion = verifyBoolAndTypes((BoolAndNode)condition);
                break;
            }
            case 5:{
                condicion = verifyBoolOrTypes((BoolOrNode)condition);
                break;
            }
            case 6:{
                System.out.println("Recibió Math??");
                condicion = false;
                break;
            }
            case 7:{
                verifyMultTypes((MathMult)condition);
                condicion = false;
                break;
            }
            case 8:{
                verifySumTypes((MathSum)condition);
                condicion = false;
                break;
            }
            default:{
                System.out.println("Error en verifyConditionBool???");
            }
        }
        return condicion;
    }

    public static boolean verifyIf(IfNode ifn){
        boolean condicion = false;
        boolean tiposState = false;
        boolean tiposStateElse = true;
        //tabla.print(0);
        condicion = verifyConditionBool(ifn.conditionType, ifn.condition);
        tiposState = comprobacionTipos(ifn.ifStatements);
        if(ifn.ifType == 2)
            tiposStateElse = comprobacionTipos(ifn.elseStatements);
        return condicion && tiposState && tiposStateElse;
    }

    public static boolean verifyWhile(WhileNode whileNode){
        boolean condicion = false;
        boolean tiposState = false;
        condicion = verifyConditionBool(whileNode.conditionType, whileNode.condition);
        tiposState = comprobacionTipos(whileNode.statements);
        return condicion && tiposState;
    }

    public static boolean verifyFor(ForNode forNode){
        boolean validAssignation = verifyAsignFor(forNode.assig);
        boolean validCondition = false;
        if(forNode.conditionType == 0){
            validCondition = false;
            //Imprimir error, el tipo no es valido.
        }else{
            switch(forNode.conditionType){
                case 1:{
                    Value val = (Value)forNode.condition;
                    if(val.type == 1){
                        validCondition =  true;
                    }else if(val.type == 2){
                        validCondition = false;
                        printError("INT", "BOOLEAN", val.fila, val.columna);
                    }else if(val.type == 3){
                        Object[] tup = tabla.buscarTupla((String)val.content, 0);
                        if(tup != null){
                            if(((Tupla)tup[0]).type.equals("INT")){
                              validCondition = true;
                            }else{
                                printError("BOOLEAN", ((Tupla)tup[0]).type, val.fila, val.columna);
                                validCondition = false;
                            }
                        }else{
                            printErrorId((String)val.content, val.fila, val.columna);
                            validCondition = false;
                        }
                    }else if(val.type == 4){
                        String ret = verifyFuncCall((FuncCallNode)val.content);
                        if(ret.equals("INT")){
                            validCondition = true;
                        }else{
                            validCondition = false;
                            if(!ret.equals("ERROR"))
                                printError("INT", ret, val.fila, val.columna);
                        }
                    }else if(val.type == 5){
                        printError("CHAR", "BOOLEAN", val.fila, val.columna);
                        validCondition = false;
                    }else{
                        System.out.println("Buscando el tipo, algo salió mal en condicion IF");
                        validCondition = false;
                    }
                    break;
                }case 6:{
                    //validCondition = 
                    //no hay MathNode
                    break;
                }case 7:{
                    validCondition = verifyMultTypes((MathMult)forNode.condition);
                    break;
                }case 8:{
                    validCondition = verifySumTypes((MathSum)forNode.condition);
                    break;
                }default:{
                    validCondition = false;
                }
            }
        }

        return validAssignation && validCondition;
        
    }

    public static boolean verifyRepeat(RepeatNode repeatNode){
        boolean validCondition = verifyConditionBool(repeatNode.conditionType, repeatNode.condition);
        return validCondition;
    }

    public static boolean verifyRead(ReadNode readNode){
        boolean validVariable = false;
        
        Object[] tup = tabla.buscarTupla((String)readNode.id.content, 0);
        if(tup != null){
            validVariable = ((Tupla)tup[0]).type.equals("INT") || ((Tupla)tup[0]).type.equals("CHAR");
        }else{
            validVariable = false;
        }
        return validVariable;
    }

    public static boolean verifyWrite(WriteNode writeNode){
        boolean validWrite = false;
        if(writeNode.type == 1){
            validWrite = true;
        }else{
            Value val = (Value)writeNode.id;
            if(val.type == 1){
                validWrite = true;
            }else if(val.type == 3){
                Object[] tup = tabla.buscarTupla((String)val.content, 0);
                if(tup != null){
                    if(((Tupla)tup[0]).type.equals("INT") || ((Tupla)tup[0]).type.equals("CHAR")){
                        validWrite = true;
                    }else{
                        validWrite = false;
                        printError("INT/CHAR", ((Tupla)tup[0]).type, val.fila, val.columna);
                    }
                }else{
                    validWrite = false;
                    printErrorId((String)val.content, val.fila, val.columna);
                }
            }else if(val.type == 5){
                validWrite = true;
            }else{
                printErrorInesperado(val.fila, val.columna);
                validWrite = false;
            }
        }
        return validWrite;
    }



    public static boolean verifySumTypes(MathSum sumNode){
        /*
            MATHSUM CONSTANTS
            final int VALUE = 1;
            final int MATHNODE = 2; <- ya no se usa
            final int MATHMULT = 3;
            final int MATHSUM = 4;
            final int FUNCCALL = 5;

            VALUE CONSTANTS
            final int NUM = 1;
            final int BOOL = 2;
            final int ID = 3;
            final int FUNCCALL = 4;
        */
        
        boolean checkLeft = false;
        boolean checkRight = false;
        switch(sumNode.typeLeft){
            case 1:{
                Value val = (Value)sumNode.leftChild;
                if(val.type == 1){
                    checkLeft = true;
                }else if(val.type == 2){
                    return false;
                }else if(val.type == 3){
                    Object[] tup = tabla.buscarTupla((String)val.content, 0);
                    if(tup != null){
                        if(((Tupla)tup[0]).type.equals("INT")){
                            checkLeft = true;
                        }else{
                            checkLeft = false;
                            printError("INT", ((Tupla)tup[0]).type, val.fila, val.columna);
                        }
                    }else{
                        checkLeft = false;
                        printErrorId((String)val.content, val.fila, val.columna);
                    }
                }else if(val.type == 4){
                    String ret = verifyFuncCall((FuncCallNode)val.content);
                    if(ret.equals("INT")){
                        checkLeft = true;
                    }else{
                        checkLeft = false;
                        if(!ret.equals("ERROR"))
                            printErrorId(ret, val.fila, val.columna);
                    }
                }else if(val.type == 5){
                    printError("INT", "CHAR", val.fila, val.columna);
                    return false;
                }else{
                    printErrorInesperado(val.fila, val.columna);
                    return false;
                }
                break;
            }case 2:{
                System.out.println("Se recibió un MathNode...que ondas");
                //ya no se usa MATHNODE
                break;
            }case 3:{
                checkLeft = verifyMultTypes((MathMult)sumNode.leftChild);
                break;
            }case 4:{
                checkLeft = verifySumTypes((MathSum)sumNode.leftChild);
                break;
            }case 5:{
                String ret = verifyFuncCall((FuncCallNode)sumNode.leftChild);
                if(ret.equals("INT")){
                    checkLeft = true;
                }else{
                    checkLeft = false;
                    if(!ret.equals("ERROR"))
                        printError("INT", ret, ((FuncCallNode)sumNode.leftChild).fila, ((FuncCallNode)sumNode.leftChild).columna);
                }
                break;
            }default:{
                return false;
            }
        }

        switch(sumNode.typeRight){
            case 1:{
                Value val = (Value)sumNode.rightChild;
                if(val.type == 1){
                    checkRight = true;
                }else if(val.type == 2){
                    return false;
                }else if(val.type == 3){
                    Object[] tup = tabla.buscarTupla((String)val.content, 0);
                    if(tup != null){
                        if(((Tupla)tup[0]).type.equals("INT")){
                            checkRight = true;
                        }else{
                            checkRight = false;
                            printError("INT", ((Tupla)tup[0]).type, val.fila, val.columna);
                        }
                    }else{
                        checkRight = false;
                        printErrorId((String)val.content, val.fila, val.columna);
                    }
                }else if(val.type == 4){
                    String ret = verifyFuncCall((FuncCallNode)val.content);
                    if(ret.equals("INT")){
                        checkRight = true;
                    }else{
                        checkRight = false;
                        if(!ret.equals("ERROR"))
                            printErrorId(ret, val.fila, val.columna);
                    }
                }else if(val.type == 5){
                    printError("INT", "CHAR", val.fila, val.columna);
                    return false;
                }else{
                    printErrorInesperado(val.fila, val.columna);
                    return false;
                }
                break;
            }case 2:{
                System.out.println("Se recibió un MathNode...que ondas");
                //ya no se usa MATHNODE
                break;
            }case 3:{
                checkRight = verifyMultTypes((MathMult)sumNode.rightChild);
                break;
            }case 4:{
                checkRight = verifySumTypes((MathSum)sumNode.rightChild);
                break;
            }case 5:{
                String ret = verifyFuncCall((FuncCallNode)sumNode.rightChild);
                if(ret.equals("INT")){
                    checkRight = true;
                }else{
                    checkRight = false; 
                    if(!ret.equals("ERROR"))
                        printError("INT", ret, ((FuncCallNode)sumNode.rightChild).fila, ((FuncCallNode)sumNode.rightChild).columna);
                }
                break;
            }default:{
                return false;
            }
        }
        return checkLeft && checkRight;
    }

    public static boolean verifyMultTypes(MathMult multNode){
        /*
            MATHMULT CONSTANTS
            final int VALUE = 1;
            final int MATHNODE = 2
            final int MATHMULT = 3;
            final int FUNCCALL = 4;

            VALUE CONSTANTS
            final int NUM = 1;
            final int BOOL = 2;
            final int ID = 3;
            final int FUNCCALL = 4;
        */

        boolean checkLeft = false;
        boolean checkRight = false;
        //Verificar el hijo de la izquierda
        switch(multNode.typeLeft){
            case 1:{
                Value val = (Value)multNode.leftChild;
                if(val.type == 1){
                    checkLeft = true;
                }else if(val.type == 2){
                    //Type mismatch, bool no se puede multiplicar con int
                    return false;
                }else if(val.type == 3){
                    Object[] tup = tabla.buscarTupla((String)val.content, 0);
                    if(tup != null){
                        if(((Tupla)tup[0]).type.equals("INT")){
                            checkLeft = true;
                        }else{
                            checkLeft = false;
                            printError("INT", ((Tupla)tup[0]).type, val.fila, val.columna);
                        }
                    }else{
                        checkLeft = false;
                        printErrorId((String)val.content, val.fila, val.columna);
                    }
                }else if(val.type == 4){
                    String ret = verifyFuncCall((FuncCallNode)val.content);
                    if(ret.equals("INT")){
                        checkLeft = true;
                    }else{
                        checkLeft = false;
                        if(!ret.equals("ERROR"))
                            printErrorId(ret, val.fila, val.columna);
                    }
                }else if(val.type == 5){
                    printError("INT", "CHAR", val.fila, val.columna);
                    return false;
                }else{
                    printErrorInesperado(val.fila, val.columna);
                    return false;
                }
                break;
            }case 2:{
                System.out.println("Se recibió un MathNode...que ondas");
                //ya no se usa MATHNODE
                break;
            }case 3:{
                checkLeft = verifyMultTypes((MathMult)multNode.leftChild);
                break;
            }case 4:{
                String ret = verifyFuncCall((FuncCallNode)multNode.leftChild);
                if(ret.equals("INT")){
                    checkLeft = true;
                }else{
                    checkLeft = false;
                    if(!ret.equals("ERROR"))
                        printError("INT", ret, ((FuncCallNode)multNode.leftChild).fila, ((FuncCallNode)multNode.leftChild).columna);
                }
            }default:{
                return false;
            }
        }

        //Verificar el hijo de la derecha
        switch(multNode.typeRight){
            case 1:{
                Value val = (Value)multNode.rightChild;
                if(val.type == 1){
                    checkRight = true;
                }else if(val.type == 2){
                    return false;
                }else if(val.type == 3){
                    Object[] tup = tabla.buscarTupla((String)val.content, 0);
                    if(tup != null){
                        if(((Tupla)tup[0]).type.equals("INT")){
                            checkRight = true;
                        }else{
                            checkRight = false;
                            printError("INT", ((Tupla)tup[0]).type, val.fila, val.columna);
                        }
                    }else{
                        checkRight = false;
                        printErrorId((String)val.content, val.fila, val.columna);
                    }
                }else if(val.type == 4){
                    String ret = verifyFuncCall((FuncCallNode)val.content);
                    if(ret.equals("INT")){
                        checkRight = true;
                    }else{
                        checkRight = false;
                        if(!ret.equals("ERROR"))
                            printErrorId(ret, val.fila, val.columna);
                    }
                }else if(val.type == 5){
                    printError("INT", "CHAR", val.fila, val.columna);
                    return false;
                }else{
                    printErrorInesperado(val.fila, val.columna);
                    return false;
                }
                break;
            }case 2:{
                System.out.println("Se recibió un MathNode...que ondas");
                //ya no se usa MATHNODE
                break;
            }case 3:{
                checkRight = verifyMultTypes((MathMult)multNode.rightChild);
                break;
            }case 4:{
                String ret = verifyFuncCall((FuncCallNode)multNode.rightChild);
                if(ret.equals("INT")){
                    checkRight = true;
                }else{
                    checkRight = false; 
                    if(!ret.equals("ERROR"))
                        printError("INT", ret, ((FuncCallNode)multNode.rightChild).fila, ((FuncCallNode)multNode.rightChild).columna);
                }
                break;
            }default:{
                return false;
            }
        }
        return checkLeft && checkRight;
    }

    public static boolean verifyAsignFor(AssigNode assigNode){
        /*
            La asignación de un for solo debería ser int con int
            for a := 0 to 10; donde a es INT
        */
        Object[] obj = tabla.buscarTupla((String)assigNode.Id.content, 0);
        String tipo = ".";
        if(obj != null){
            tipo = ((Tupla)obj[0]).type;
            if(!tipo.equals("INT")){
                if(!tipo.equals("ERROR")){
                    printError("INT", tipo, assigNode.fila, assigNode.columna);
                }
                return false;
            }
        }else{
            printErrorId((String)assigNode.Id.content, assigNode.fila, assigNode.columna);
        }
        if(assigNode.type == 2){
            printError(tipo, "CHAR", assigNode.fila, assigNode.columna);
            return false;
        } else if(assigNode.type == 3){
            Value val = (Value)assigNode.expr;
            if(val.type == 1 && tipo.equals("INT")){
                return true;
            }else if(val.type == 2){
                printError(tipo, "BOOLEAN", assigNode.fila, assigNode.columna);
                return false;
            }else if(val.type == 3){
                Object[] tup = tabla.buscarTupla((String)val.content, 0);
                if(tup != null){
                    if(((Tupla)tup[0]).type.equals("INT")){
                        return true;
                    }else{
                        printError("INT", ((Tupla)tup[0]).type, val.fila, val.columna);
                        return false;
                    }
                }else{
                    printErrorId((String)val.content, val.fila, val.columna);
                    return false;
                }
            }else if(val.type == 4){
                String ret = verifyFuncCall((FuncCallNode)val.content); 
                if(ret.equals("INT")){
                    return true;
                }else{
                    printError("INT", ret, val.fila, val.columna);
                    return false;
                }
            }else if(val.type == 5){
                printError("INT", "CHAR", val.fila, val.columna);
                return false;
            }else{
                return false;
            }
        } else if(assigNode.type == 5){
            if(tipo.equals("INT")){
                return verifyMultTypes((MathMult)assigNode.expr);
            }else{
                printError("INT", tipo, ((MathMult)assigNode.expr).fila, ((MathMult)assigNode.expr).columna);
                return false;
            }
        } else if(assigNode.type == 6){
            if(tipo.equals("INT")){
                return verifySumTypes((MathSum)assigNode.expr);
            }else{
                printError("INT", tipo, ((MathSum)assigNode.expr).fila, ((MathSum)assigNode.expr).columna);
                return false;
            }
        } else if(assigNode.type == 8){
            verifyBoolMathTypes((BoolMathNode)assigNode.expr);
            printError("INT", "BOOLEAN", ((BoolMathNode)assigNode.expr).fila, ((BoolMathNode)assigNode.expr).columna);
            return false;
        } else if(assigNode.type == 9){
            verifyBoolAndTypes((BoolAndNode)assigNode.expr);
            printError("INT", "BOOLEAN", ((BoolAndNode)assigNode.expr).fila, ((BoolAndNode)assigNode.expr).columna);
            return false;
        } else if(assigNode.type == 10){
            verifyBoolOrTypes((BoolOrNode)assigNode.expr);
            printError("INT", "BOOLEAN", ((BoolOrNode)assigNode.expr).fila, ((BoolOrNode)assigNode.expr).columna);
            return false;
        } else {
            return false;
        }
    }



    public static boolean verifyAsignType(AssigNode assigNode){
        /*
            final int STR = 1;
            final int CHR = 2;
            final int VALUE = 3;
            final int MATHNODE = 4; <- no se usa
            final int MATHMULT = 5;
            final int MATHSUM = 6;
            final int BOOL = 7;
            final int BOOLMATH = 8;
            final int BOOLAND = 9;
            final int BOOLOR = 10;
        */
        String tipo = "";
        Object[] tuplita = tabla.buscarTupla((String)assigNode.Id.content, 0);
        if(tuplita != null){
          tipo = ((Tupla)tuplita[0]).type;
        }else{
            printErrorId((String)assigNode.Id.content, assigNode.fila, assigNode.columna);
          return false;
        }
        
        if(assigNode.type == 2){
            if(tipo.equals("CHAR")){
                return true;
            }else{
                printError(tipo, "CHAR", assigNode.fila, assigNode.columna);
                return false;
            }
        } else if(assigNode.type == 3){
            Value val = (Value)assigNode.expr;
            if(val.type == 1){
                if(tipo.equals("INT")){
                    return true;
                }else{
                    printError(tipo,"INT", assigNode.fila, assigNode.columna);
                    return false;
                }
            }else if(val.type == 2){
                if(tipo.equals("BOOLEAN")){
                    return true;
                }else{
                    printError(tipo, "BOOLEAN", assigNode.fila, assigNode.columna);
                }
            }else if(val.type == 3){
                Object[] tup = tabla.buscarTupla((String)val.content, 0);
                if(tup != null){
                    if(((Tupla)tup[0]).type.equals(tipo)){
                        return true;
                    }else{
                        printError(tipo, ((Tupla)tup[0]).type, assigNode.fila, assigNode.columna );
                        return false;
                    }
                }else{
                    printErrorId((String)val.content, assigNode.fila, assigNode.columna);
                    return false;
                }
            }else if(val.type == 4){
                String funcType = verifyFuncCall((FuncCallNode)val.content);
                if(funcType.equals(tipo)){
                    return true;
                }else if(funcType.equals("ERROR")){
                    return false;
                }else{
                    printError(tipo, funcType, assigNode.fila, assigNode.columna);
                    return false;
                }
            }else if(val.type == 5){
                if(tipo.equals("CHAR")){
                    return true;
                }else{
                    printError(tipo, "CHAR", assigNode.fila, assigNode.columna);
                    return false;
                }
            }else{
                printError(tipo, "-", assigNode.fila, assigNode.columna);
                return false;
            }
        } else if(assigNode.type == 5){
            if(tipo.equals("INT")){
                if(verifyMultTypes((MathMult)assigNode.expr)){
                    return true;
                }else{
                    printError(tipo, "INT", assigNode.fila, assigNode.columna);
                    return false;
                }
            }else{
                printError("INT", tipo, assigNode.fila, assigNode.columna);
                return false;
            }
        } else if(assigNode.type == 6){
            if(tipo.equals("INT")){
                if(verifySumTypes((MathSum)assigNode.expr)){
                    return true;
                }else{
                    printError(tipo, "INT", assigNode.fila, assigNode.columna);
                    return false;
                }
            }else{
                printError("INT", tipo, assigNode.fila, assigNode.columna);
                return false;
            }
        } else if(assigNode.type == 8){
            if(tipo.equals("BOOLEAN")){
                if(verifyBoolMathTypes((BoolMathNode)assigNode.expr)){
                    return true;
                }else{
                    printError(tipo, "BOOLEAN", assigNode.fila, assigNode.columna);
                    return false;
                }
            }else{
                printError("BOOLEAN", tipo, assigNode.fila, assigNode.columna);
                return false;
            }
        } else if(assigNode.type == 9){
            if(tipo.equals("BOOLEAN")){
                if(verifyBoolAndTypes((BoolAndNode)assigNode.expr)){
                    return true;
                }else{
                    printError(tipo, "BOOLEAN", assigNode.fila, assigNode.columna);
                    return false;
                }
            }else{
                printError("BOOLEAN", tipo, assigNode.fila, assigNode.columna);
                return false;
            }
        } else if(assigNode.type == 10){
            if(tipo.equals("BOOLEAN")){
                if(verifyBoolOrTypes((BoolOrNode)assigNode.expr)){
                    return true;
                }else{
                    printError(tipo, "BOOLEAN", assigNode.fila, assigNode.columna);
                    return false;
                }
            }else{
                printError("BOOLEAN", tipo, assigNode.fila, assigNode.columna);
                return false;
            }
        } else {
            return false;
        }
        return false;
    }

    public static boolean verifyBoolMathTypes(BoolMathNode boolNode){
        /*  
            Constantes de BoolMathNode
            final int VALUE = 1;
            final int MATH = 2;
            final int MULT = 3;
            final int SUM = 4;
            final int FUNCCALL = 5;

            Constantes de Value
            final int NUM = 1;
            final int BOOL = 2;
            final int ID = 3;
            final int FUNCCALL = 4;
        */
        boolean retVal = false;
        String leftType = "?";
        String rightType = "¿";
        //Si es diferente o igual, a fuerza deben ser el mismo tipo
        if(boolNode.operator.equals("<>") || boolNode.operator.equals("=")){
            //Verificación de la izquierda
            if(boolNode.typeLeft == 1){
                Value val = (Value)boolNode.leftChild;
                if(val.type == 1){
                    leftType = "INT";
                } else if(val.type == 2){
                    leftType = "BOOLEAN";
                } else if(val.type == 3){
                    Object[] tuplita = tabla.buscarTupla((String)val.content, 0);
                    if(tuplita != null){
                        leftType = ((Tupla)tuplita[0]).type;
                    }else{
                        printErrorId((String)val.content, boolNode.fila, boolNode.columna);
                        return false;
                    }
                }else if(val.type == 4){
                    String tipo = verifyFuncCall((FuncCallNode)val.content);
                    if(tipo.equals("ERROR")){
                        return false;
                    }else{
                        leftType = tipo;
                    }
                } else if(val.type == 5){
                    leftType = "CHAR";
                }else{
                    return false;
                }
            }else if(boolNode.typeLeft == 2){
                return false;
            }else if(boolNode.typeLeft == 3 && verifyMultTypes((MathMult)boolNode.leftChild)){
                leftType = "INT";
            }else if(boolNode.typeLeft == 4 && verifySumTypes((MathSum)boolNode.leftChild)){
                leftType = "INT";
            }else if(boolNode.typeLeft == 5){
                String tipo = verifyFuncCall((FuncCallNode)boolNode.leftChild);
                if(tipo.equals("ERROR")){
                    return false;
                }else{
                    leftType = tipo;
                }
            }else{
                return false;
            }

            //Verificación de la der
            if(boolNode.typeRight == 1){
                Value val = (Value)boolNode.rightChild;
                if(val.type == 1){
                    rightType = "INT";
                } else if(val.type == 2){
                    rightType = "BOOLEAN";
                } else if(val.type == 3){
                    Object[] tuplita = tabla.buscarTupla((String)val.content, 0);
                    if(tuplita != null){
                        rightType = ((Tupla)tuplita[0]).type;
                    }else{
                        printErrorId((String)val.content, boolNode.fila, boolNode.columna);
                        return false;
                    }
                } else if (val.type == 4){
                    String tipo = verifyFuncCall((FuncCallNode)val.content);
                    if(tipo.equals("ERROR")){
                        return false;
                    }else{
                        rightType = tipo;
                    }                    
                } else if(val.type == 5){
                    rightType = "CHAR";
                }else{
                    return false;
                }
            }else if(boolNode.typeRight == 2){
                return false;
            }else if(boolNode.typeRight == 3 && verifyMultTypes((MathMult)boolNode.rightChild)){
                rightType = "INT";
            }else if(boolNode.typeRight == 4 && verifySumTypes((MathSum)boolNode.rightChild)){
                rightType = "INT";
            }else if(boolNode.typeRight == 5){
                String tipo = verifyFuncCall((FuncCallNode)boolNode.rightChild);
                if(tipo.equals("ERROR")){
                    return false;
                }else{
                    rightType = tipo;
                }
            }else{
                return false;
            }

            if(leftType.equals(rightType)){
                retVal = true;
            }else{
                printError(leftType, rightType, boolNode.fila, boolNode.columna);
                retVal = false;
            }
        }else{
            //Si no, deben ser solamente int. No deberia decir true < 10
            //Verificación de la izquierda
            if(boolNode.typeLeft == 1){
                Value val = (Value)boolNode.leftChild;
                if(val.type == 1){
                    leftType = "INT";
                } else if(val.type == 2){
                    printError("INT", "BOOLEAN", boolNode.fila, boolNode.columna);
                    return false;
                } else if(val.type == 3){
                    Object[] tuplita = tabla.buscarTupla((String)val.content, 0);
                    if(tuplita != null){
                        leftType = ((Tupla)tuplita[0]).type;
                        if(!leftType.equals("INT")){
                            printError("INT", leftType, boolNode.fila, boolNode.columna);
                            return false;
                        }
                    }else{
                        printErrorId((String)val.content, boolNode.fila, boolNode.columna);
                        return false;
                    }
                }else if(val.type == 4){
                    String tipo = verifyFuncCall((FuncCallNode)val.content);
                    if(!tipo.equals("INT")){
                        printError("INT", tipo, boolNode.fila, boolNode.columna);
                        return false;
                    }else{
                        leftType = tipo;
                    }
                } else if(val.type == 5){
                    printError("INT", "CHAR", boolNode.fila, boolNode.columna);
                    return false;
                }else{
                    printError("INT", "-", boolNode.fila, boolNode.columna);
                    return false;
                }
            }else if(boolNode.typeLeft == 2){
                return false;
            }else if(boolNode.typeLeft == 3 && verifyMultTypes((MathMult)boolNode.leftChild)){
                leftType = "INT";
            }else if(boolNode.typeLeft == 4 && verifySumTypes((MathSum)boolNode.leftChild)){
                leftType = "INT";
            }else if(boolNode.typeLeft == 5){
                String tipo = verifyFuncCall((FuncCallNode)boolNode.leftChild);
                if(tipo.equals("ERROR")){
                    return false;
                }else if(!tipo.equals("INT")){
                    printError("INT", tipo, boolNode.fila, boolNode.columna);
                }
            }else{
                return false;
            }

            //Verificación de la der
            if(boolNode.typeRight == 1){
                Value val = (Value)boolNode.rightChild;
                if(val.type == 1){
                    rightType = "INT";
                } else if(val.type == 2){
                    printError("INT", "BOOLEAN", boolNode.fila, boolNode.columna);
                    return false;
                } else if(val.type == 3){
                    Object[] tuplita = tabla.buscarTupla((String)val.content, 0);
                    if(tuplita != null){
                        rightType = ((Tupla)tuplita[0]).type;
                        if(!rightType.equals("INT")){
                            printError("INT", rightType, boolNode.fila, boolNode.columna);
                            return false;
                        }
                    }else{
                        printErrorId((String)val.content, boolNode.fila, boolNode.columna);
                        return false;
                    }
                }else if(val.type == 4){
                    String tipo = verifyFuncCall((FuncCallNode)val.content);
                    if(!tipo.equals("INT")){
                        printError("INT", tipo, boolNode.fila, boolNode.columna);
                        return false;
                    }else{
                        rightType = tipo;
                    }                    
                } else if(val.type == 5){
                    printError("INT", "CHAR", boolNode.fila, boolNode.columna);
                    return false;
                }else{
                    printError("INT", "-", boolNode.fila, boolNode.columna);
                    return false;
                }
            }else if(boolNode.typeRight == 2){
                return false;
            }else if(boolNode.typeRight == 3 && verifyMultTypes((MathMult)boolNode.rightChild)){
                rightType = "INT";
            }else if(boolNode.typeRight == 4 && verifySumTypes((MathSum)boolNode.rightChild)){
                rightType = "INT";
            }else if(boolNode.typeRight == 5){
                String tipo = verifyFuncCall((FuncCallNode)boolNode.rightChild);
                if(tipo.equals("ERROR")){
                    return false;
                }else if(!tipo.equals("INT")){
                    printError("INT", tipo, boolNode.fila, boolNode.columna);
                }
            }else{
                return false;
            }

            if(leftType.equals(rightType)){
                retVal = true;
            }else{
                printError(leftType, rightType, boolNode.fila, boolNode.columna);
            }
        }
        return retVal;

    }

    public static boolean verifyBoolAndTypes(BoolAndNode boolNode){
        /*
            final int VALUE = 1;
            final int BOOLMATH = 2;
            final int BOOLAND = 3;
            final int BOOL = 4;
            final int FUNCCALL = 5;

            final int MATH = 6;
            final int MULT = 7;
            final int SUM = 8;
        */
        
        boolean checkLeft = false;
        boolean checkRight = false;

        //Verifica el tipo de la izquierda del operador
        if(boolNode.typeLeft == 1){
            Value val = (Value)boolNode.leftChild;
            if(val.type == 1){
                printError("BOOLEAN", "INT", boolNode.fila, boolNode.columna);
                return false;
            }else if(val.type == 2){
                checkLeft = true;
            }else if(val.type == 3){                
                Object[] tuplita = tabla.buscarTupla((String)val.content, 0);
                if(tuplita != null){
                    if(((Tupla)tuplita[0]).type.equals("BOOLEAN")){
                        checkLeft = true;
                    }else{
                        printError("BOOLEAN", ((Tupla)tuplita[0]).type, boolNode.fila, boolNode.columna);
                        checkLeft = false; 
                    }
                }else{
                    printErrorId((String)val.content, boolNode.fila, boolNode.columna);
                    checkLeft = false;
                }
            }else if(val.type == 4){
                String tipo = verifyFuncCall((FuncCallNode)val.content);
                if(tipo.equals("ERROR")){
                    checkLeft = false;
                }else if(tipo.equals("BOOLEAN")){
                    checkLeft = true;
                }else{
                    printError("BOOLEAN", tipo, boolNode.fila, boolNode.columna);
                    checkLeft = false;
                }                
            } else if(val.type == 5){
                printError("BOOLEAN", "CHAR", boolNode.fila, boolNode.columna);
                return false;
            }else{
                printError("BOOLEAN", "-", boolNode.fila, boolNode.columna);
                return false;
            }
        } else if(boolNode.typeLeft == 2 && verifyBoolMathTypes((BoolMathNode)boolNode.leftChild)){
            checkLeft = true;
        } else if(boolNode.typeLeft == 3 && verifyBoolAndTypes((BoolAndNode)boolNode.leftChild)){
            checkLeft = true;
        } else if(boolNode.typeLeft == 5){
            String tipo = verifyFuncCall((FuncCallNode)boolNode.leftChild);
                if(tipo.equals("ERROR")){
                    checkLeft = false;
                }else if(tipo.equals("BOOLEAN")){
                    checkLeft = true;
                }else{
                    printError("BOOLEAN", tipo, boolNode.fila, boolNode.columna);
                    checkLeft = false;
                }
        } else {
            printError("BOOLEAN", "-", boolNode.fila, boolNode.columna);
            return false;
        }

        //Verifica el tipo de la derecha del operador
        if(boolNode.typeRight == 1){
            Value val = (Value)boolNode.rightChild;
            if(val.type == 1){
                printError("BOOLEAN", "INT", boolNode.fila, boolNode.columna);
                return false;
            }else if(val.type == 2){
                checkRight = true;
            }else if(val.type == 3){
                Object[] tuplita = tabla.buscarTupla((String)val.content, 0);
                if(tuplita != null){
                    if(((Tupla)tuplita[0]).type.equals("BOOLEAN")){
                        checkRight = true;
                    }else{
                        printError("BOOLEAN", ((Tupla)tuplita[0]).type, boolNode.fila, boolNode.columna);
                        checkRight = false; 
                    }
                }else{
                    printErrorId((String)val.content, boolNode.fila, boolNode.columna);
                    checkRight = false;
                }
            }else if(val.type == 4){
                String tipo = verifyFuncCall((FuncCallNode)val.content);
                if(tipo.equals("ERROR")){
                    checkRight = false;
                }else if(tipo.equals("BOOLEAN")){
                    checkRight = true;
                }else{
                    printError("BOOLEAN", tipo, boolNode.fila, boolNode.columna);
                    checkRight = false;
                }
            } else if(val.type == 5){
                printError("BOOLEAN", "CHAR", boolNode.fila, boolNode.columna);
                checkRight = false;
            }else{
                printError("BOOLEAN", "-", boolNode.fila, boolNode.columna);
                checkRight = false;
            }
        } else if(boolNode.typeRight == 2 && verifyBoolMathTypes((BoolMathNode)boolNode.rightChild)){
            checkRight = true;
        } else if(boolNode.typeRight == 3 && verifyBoolAndTypes((BoolAndNode)boolNode.rightChild)){
            checkRight = true;
        } else if(boolNode.typeRight == 5){
            String tipo = verifyFuncCall((FuncCallNode)boolNode.rightChild);
                if(tipo.equals("ERROR")){
                    checkRight = false;
                }else if(tipo.equals("BOOLEAN")){
                    checkRight = true;
                }else{
                    printError("BOOLEAN", tipo, boolNode.fila, boolNode.columna);
                    checkRight = false;
                }
        } else {
            printError("BOOLEAN", "-", boolNode.fila, boolNode.columna);
            return false;
        }
        return checkLeft && checkRight;

    }

    public static boolean verifyBoolOrTypes(BoolOrNode boolNode){
        /*
            final int VALUE = 1;
            final int BOOLOR = 2;
            final int BOOLAND = 3;
            final int BOOLMATH = 4;
            final int BOOL = 5;
            final int FUNCCALL = 6;

            final int MATH = 7;
            final int MULT = 8;
            final int SUM = 9;
        */
        boolean checkLeft = false;
        boolean checkRight = false;

        //Verifica el tipo de la izquierda del operador
        if(boolNode.typeLeft == 1){
            Value val = (Value)boolNode.leftChild;
            if(val.type == 1){
                printError("BOOLEAN","INT",boolNode.fila, boolNode.columna);
                return false;
            }else if(val.type == 2){
                checkLeft = true;
            }else if(val.type == 3){
                Object[] tuplita = tabla.buscarTupla((String)val.content, 0);
                if(tuplita != null){
                    if(((Tupla)tuplita[0]).type.equals("BOOLEAN")){
                        checkLeft = true;
                    }else{
                        printError("BOOLEAN", ((Tupla)tuplita[0]).type, boolNode.fila, boolNode.columna);
                        checkLeft = false; 
                    }
                }else{
                    printErrorId((String)val.content, boolNode.fila, boolNode.columna);
                    checkLeft = false;
                }
            } else if(val.type == 4){
                String tipo = verifyFuncCall((FuncCallNode)val.content);
                if(tipo.equals("ERROR")){
                    checkLeft = false;
                }else if(tipo.equals("BOOLEAN")){
                    checkLeft = true;
                }else{
                    printError("BOOLEAN", tipo, boolNode.fila, boolNode.columna);
                    checkLeft = false;
                }   
            } else if(val.type == 5){
                printError("BOOLEAN", "CHAR", boolNode.fila, boolNode.columna);
                checkLeft = false;
            }else{
                printError("BOOLEAN", "-", boolNode.fila, boolNode.columna);
                checkLeft = false;
            }
        } else if(boolNode.typeLeft == 2 && verifyBoolOrTypes((BoolOrNode)boolNode.leftChild)){
            checkLeft = true;
        } else if(boolNode.typeLeft == 3 && verifyBoolAndTypes((BoolAndNode)boolNode.leftChild)){
            checkLeft = true;
        } else if(boolNode.typeLeft == 4 && verifyBoolMathTypes((BoolMathNode)boolNode.leftChild)){
            checkLeft = true;
        } else if(boolNode.typeLeft ==6){
            String tipo = verifyFuncCall((FuncCallNode)boolNode.leftChild);
                if(tipo.equals("ERROR")){
                    checkLeft = false;
                }else if(tipo.equals("BOOLEAN")){
                    checkLeft = true;
                }else{
                    printError("BOOLEAN", tipo, boolNode.fila, boolNode.columna);
                    checkLeft = false;
                }
        }else{
            printError("BOOLEAN", "-", boolNode.fila, boolNode.columna);
            return false;
        }

        //Verifica el tipo de la derecha del operador
        if(boolNode.typeRight == 1){
            Value val = (Value)boolNode.rightChild;
            if(val.type == 1){
                printError("BOOLEAN", "INT", boolNode.fila, boolNode.columna);
                return false;
            }else if(val.type == 2){
                checkRight = true;
            }else if(val.type == 3){
                Object[] tuplita = tabla.buscarTupla((String)val.content, 0);
                if(tuplita != null){
                    if(((Tupla)tuplita[0]).type.equals("BOOLEAN")){
                        checkRight = true;
                    }else{
                        printError("BOOLEAN", ((Tupla)tuplita[0]).type, boolNode.fila, boolNode.columna);
                        checkRight = false; 
                    }
                }else{
                    printErrorId((String)val.content, boolNode.fila, boolNode.columna);
                    checkRight = false;
                }
            } else if(val.type == 4){
                String tipo = verifyFuncCall((FuncCallNode)val.content);
                if(tipo.equals("ERROR")){
                    checkRight = false;
                }else if(tipo.equals("BOOLEAN")){
                    checkRight = true;
                }else{
                    printError("BOOLEAN", tipo, boolNode.fila, boolNode.columna);
                    checkRight = false;
                }
            } else if(val.type == 5){
                printError("BOOLEAN", "CHAR", boolNode.fila, boolNode.columna);
                checkRight = false;
            }else{
                printError("BOOLEAN", "-", boolNode.fila, boolNode.columna);
                checkRight = false;
            }
        } else if(boolNode.typeRight == 2 && verifyBoolOrTypes((BoolOrNode)boolNode.rightChild)){
            checkRight = true;
        } else if(boolNode.typeRight == 3 && verifyBoolAndTypes((BoolAndNode)boolNode.rightChild)){
            checkRight = true;
        } else if(boolNode.typeRight == 4 && verifyBoolMathTypes((BoolMathNode)boolNode.rightChild)){
            checkRight = true;
        } else if(boolNode.typeRight == 6){
            String tipo = verifyFuncCall((FuncCallNode)boolNode.rightChild);
                if(tipo.equals("ERROR")){
                    checkRight = false;
                }else if(tipo.equals("BOOLEAN")){
                    checkRight = true;
                }else{
                    printError("BOOLEAN", tipo, boolNode.fila, boolNode.columna);
                    checkRight = false;
                }
        }else{
            printError("BOOLEAN", "-", boolNode.fila, boolNode.columna);
            return false;
        }

        return checkLeft && checkRight;     
    }
}