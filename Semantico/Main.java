import arbol.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Map;
import java.util.HashMap;

public class Main {

    public static ProgramNode root;
    public static TablaSym tabla;

    public static Map<String, Integer> tipos;
    public static Map<Integer, String> tiposInv;

    public static int offset;
    
    
    public static void main(String[] args) {
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
    }

    public static void leerArbol(){
        try(ObjectInputStream oi = new ObjectInputStream(new FileInputStream(new File("./arbolito.bebe")))){
            root = (ProgramNode) oi.readObject();
            System.out.println("Olo: \n\n" + root.printNode(1));
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
        //records
        declaraciones((ArrayList<DeclNode>)root.declarations);
        funciones(root.functions);
        comprobacionTipos(root.statements);
    }

    public static void comprobacionTipos(){
        
    }

    public static void records(ArrayList<RecordNode> recs){

    }
    
    public static void funciones(ArrayList<Object> funcs){
        for(Object funcproc : funcs){
            Value id;
            ArrayList<ParamsNode> params;
            Object declarations;
            ArrayList<Object> statements;
            String funcprocType = "";

            if(func instanceof FunctionNode){
                FunctionNode func = (FunctionNode)funcproc;
                id = func.id;
                params = func.params;
                declarations = func.declarations;
                statements = func.statements;
                funcprocType = func.type;
            }else if (func instanceof ProcedureNode){
                ProcedureNode proc = (ProcedureNode)funcproc;
                id = proc.id;
                params = proc.params;
                declarations = proc.declarations;
                statements = proc.statements;
                funcprocType = "NULL";
            }
            String type = "(";
            for(ParamsNode params : params){
                /*
                    final int NUM = 1;
                    final int BOOL = 2;
                    final int ID = 3;
                    final int FUNCCALL = 4;
                */

                String tipo = params.type;
                int size = tipos.get(tipo);

                for(Value val : params.ids){
                    String idval = (String)val.content;
                    Tupla tuplita = new Tupla(idval, tipo, offset);
                    offset += size;

                    if(func.indexOf(params) != func.size() - 1 && params.ids.idenxOf(val) != params.ids.size() - 1){
                        type += tipo + " x ";
                    }else{
                        type += tipo;
                    }

                }

            }
            type += ") -> " + funcprocType;
            
            int tempOffset = offset;

            tabla.add(id, type, -1);

        }
        for(Object funcproc : funcs){
            Object declarations;
            ArrayList<Object> statements;
            
            offset = tempOffset;
            offset = 0;

            tabla = new TablaSym(tabla);

            declaraciones((ArrayList<DeclNode>)declarations);

            comprobacionTipos(statements);

            if(stepBack())
                offset = tempOffset;
        }
    }

    public static void declaraciones(ArrayList<DeclNode> decls){

        for(DeclNode decl : decls){

            String type = decl.type;
            int size = tipos.get(type);

            for(Value val : decl.ids){

                String id = (String)val.content;
                Tupla tuplita = new Tupla(id, type, offset);
                offset += size;

            }
        }
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

                Object o = attr.attr;
                int tipo = attr.type;

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
                            /////////buscar el tipo en la tabla de simbolos
                        } else if(val.type == 4){
                            verifyFuncCall((FuncCallNode)val.content);
                        } else {
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
                        if(verifyBoolAndTypes((BoolOrNode)o)){
                            type += "BOOLEAN";
                        }else{
                            type += "ERROR";
                        }
                        break;
                    }
                    case 10:{
                        if(verifyBoolOrTypes((BoolAndNode)o)){
                            type += "BOOLEAN";
                        }else{
                            type += "ERROR";
                        }
                        break;
                    }
                }

            }

            if(val.args.indexOf(obj) != val.args.size() - 1){
                type += " x ";
            }

            /*
            String tipo = params.type;
            int size = tipos.get(tipo);

            for(Value val : params.ids){
                String idval = (String)val.content;
                Tupla tuplita = new Tupla(idval, tipo, offset);
                offset += size;

                if(func.indexOf(params) != func.size() - 1 && params.ids.idenxOf(val) != params.ids.size() - 1){
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

        Object[] res = tabla.buscarTuplaFunc(name, type, 0);



        if(res != null){
            int index = (((Tupla)res[0]).type).indexOf(" -> ");
            String tipoRetorno = (((Tupla)res[0]).type).substring( index + 4 );
            return tipoRetorno;
        }
        return "ERROR";
    }

    public static boolean verifyProcCall(FuncCallNode val){
        
        String name = (String)((Value)val.id).content;
        
        String type = getFuncType(val.args);

        Object[] res = tabla.buscarTuplaFunc(name, type, 0);

        return (res != null);
         
    }

    public static boolean verifyConditionBool(int condtype, Object condition){

        switch(condtype){
            case 1:{
                Value val = (Value)condition;
                if(val.type == 1){
                    condicion =  false;
                }else if(val.type == 2){
                    condicion = true;
                }else if(val.type == 3){
                    //buscar en la tabla de simbolos
                }else if(val.type == 4){
                    //buscar en la tabla de simbolos
                }else{
                    System.out.println("Buscando el tipo, algo salió mal en condicion IF");
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
        condicion = verifyConditionBool(ifn.conditionType, ifn.condition);
        tiposState = comprobacionTipos(ifn.statements);
        return condicion && tiposState;
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
                case 1:
                    Value val = (Value)forNode.condition;
                    if(val.type == 1){
                        validCondition =  true;
                    }else if(val.type == 2){
                        validCondition = false;
                    }else if(val.type == 3){
                        //buscar en la tabla de simbolos
                    }else if(val.type == 4){
                        //buscar en la tabla de simbolos
                    }else{
                        System.out.println("Buscando el tipo, algo salió mal en condicion IF");
                        validCondition = false;
                    }
                    break;
                case 6:
                    //validCondition = 
                    //no hay MathNode
                    break;
                case 7:
                    validCondition = verifyMultTypes((MathMult)forNode.condition);
                    break;
                case 8:
                    validCondition = verifySumTypes((MathSum)forNode.condition);
                    break;
                default:
                    validCondition = false;
            }
            return validAssignation && validCondition;
        }

        
    }

    public static boolean verifyRepeat(RepeatNode repeatNode){
        boolean validCondition = verifyConditionBool(repeatNode.conditionType, repeatNode.condition);
        return validCondition;
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
            case 1:
                Value val = (Value)sumNode.leftChild;
                if(val.type == 1){
                    checkLeft = true;
                }else if(val.type == 2){
                    return false;
                }else if(val.type == 3 || val.type == 4){
                    //buscar en la tabla de simbolos
                }else{
                    return false;
                }
                break;
            case 2:
                System.out.println("Se recibió un MathNode...que ondas");
                //ya no se usa MATHNODE
                break;
            case 3:
                checkLeft = verifyMultTypes((MathMult)sumNode.leftChild);
                break;
            case 4:
                checkLeft = verifySumTypes((MathSum)sumNode.leftChild);
                break;
            case 5:
                //busacrlo en la tabla de simbolos
                break;
            default:
                return false;
        }

        switch(sumNode.rightChild){
            case 1:
                Value val = (Value)sumNode.rightChild;
                if(val.type == 1){
                    checkRight = true;
                }else if(val.type == 2){
                    return false;
                }else if(val.type == 3 || val.type == 4){
                    //buscar en la tabla de simbolos
                }else{
                    return false;
                }
                break;
            case 2:
                System.out.println("Se recibió un MathNode...que ondas");
                //ya no se usa MATHNODE
                break;
            case 3:
                checkRight = verifyMultTypes((MathMult)sumNode.rightChild);
                break;
            case 4:
                checkRight = verifySumTypes((MathSum)sumNode.rightChild);
                break;
            case 5:
                //busacrlo en la tabla de simbolos
                break;
            default:
                return false;
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
            case 1:
                Value val = (Value)multNode.leftChild;
                if(val.type == 1){
                    checkLeft = true;
                }else if(val.type == 2){
                    //Type mismatch, bool no se puede multiplicar con int
                    return false;
                }else if(val.type == 3 || val.type == 4){
                    //buscar en la tabla de simbolos
                }else{
                    return false;
                }
                break;
            case 2:
                System.out.println("Se recibió un MathNode...que ondas");
                //ya no se usa MATHNODE
                break;
            case 3:
                checkLeft = verifyMultTypes((MathMult)sumNode.leftChild);
                break;
            case 4:
                //function call
                //busacrlo en la tabla de simbolos
                break;
            default:
                return false;
        }

        //Verificar el hijo de la derecha
        switch(multNode.typeRight){
            case 1:
                Value val = (Value)multNode.rightChild;
                if(val.type == 1){
                    checkRight = true;
                }else if(val.type == 2){
                    return false;
                }else if(val.type == 3 || val.type == 4){
                    //buscar en la tabla de simbolos
                }else{
                    return false;
                }
                break;
            case 2:
                System.out.println("Se recibió un MathNode...que ondas");
                //ya no se usa MATHNODE
                break;
            case 3:
                checkRight = verifyMultTypes((MathMult)sumNode.rightChild);
                break;
            case 4:
                //function call
                //busacrlo en la tabla de simbolos
                break;
            default:
                return false;
        }
        return checkLeft && checkRight;
    }

    public static boolean verifyAsignFor(AssigNode assigNode){
        /*
            La asignación de un for solo debería ser int con int
            for a := 0 to 10; donde a es INT
        */
        String tipo = "";
        //tipo = buscarID((String) assigNode.Id.content);
        if(!tipo.equals("INT")){
            return false;
        }
        if(assigNode.type == 2 && tipo.equals("CHAR")){
            return false;
        } else if(assignNode.type == 3){
            Value val = (Value)assigNode.expr;
            if(val.type == 1 && tipo.equals("INT")){
                return true;
            }else if(val.type == 2 && tipo.equals("BOOLEAN")){
                return false;
            }else if(val.type == 3 || val.type == 4){
                //Buscar el id de value en la tabla de simbolos
                //Si los tipos son INT, devolver verdadero
            }else{
                return false;
            }
        } else if(assigNode.type == 5){
            if(tipo.equals("INT") && verifyMultTypes((MathMult)assigNode.expr)){
                return true;
            }else{
                return false;
            }
        } else if(assigNode.type == 6){
            if(tipo.equals("INT") && verifySumTypes((MathSum)assigNode.expr)){
                return true;
            }else{
                return false;
            }
        } else if(assigNode.type == 8){
            if(tipo.equals("BOOLEAN") && verifyBoolMathTypes((BoolMathNode)assigNode.expr)){
                return false;
            }
        } else if(assigNode.type == 9){
            if(tipo.equals("BOOLEAN") && verifyBoolAndTypes((BoolAndNode)assigNode.expr)){
                return false;
            }
        } else if(assigNode.type == 10){
            if(tipo.equals("BOOLEAN") && verifyBoolOrTypes((BoolOrNode)assigNode.expr)){
                return false;
            }
        } else {
            return false;
        }
        return false;
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
        String tipo;
        //tipo = buscarID((String) assigNode.Id.content);
        if(assigNode.type == 2 && tipo.equals("CHAR")){
            return true;
        } else if(assignNode.type == 3){
            Value val = (Value)assigNode.expr;
            if(val.type == 1 && tipo.equals("INT")){
                return true;
            }else if(val.type == 2 && tipo.equals("BOOLEAN")){
                return true;
            }else if(val.type == 3 || val.type == 4){
                //Buscar el id de value en la tabla de simbolos
                //Si los tipos son iguales, devolver verdadero
            }else{
                return false;
            }
        } else if(assigNode.type == 5){
            if(tipo.equals("INT") && verifyMultTypes((MathMult)assigNode.expr)){
                return true;
            }else{
                return false;
            }
        } else if(assigNode.type == 6){
            if(tipo.equals("INT") && verifySumTypes((MathSum)assigNode.expr)){
                return true;
            }else{
                return false;
            }
        } else if(assigNode.type == 8){
            if(tipo.equals("BOOLEAN") && verifyBoolMathTypes((BoolMathNode)assigNode.expr)){
                return true;
            } else {
                return false;
            }
        } else if(assigNode.type == 9){
            if(tipo.equals("BOOLEAN") && verifyBoolAndTypes((BoolAndNode)assigNode.expr)){
                return true;
            } else {
                return false;
            }
        } else if(assigNode.type == 10){
            if(tipo.equals("BOOLEAN") && verifyBoolOrTypes((BoolOrNode)assigNode.expr)){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
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
        String leftType;
        String rightType;
        //Si es diferente o igual, a fuerza deben ser el mismo tipo
        if(boolNode.operator.equals("<>") || boolNode.operator.equals("=")){
            //Verificación de la izquierda
            if(boolNode.typeLeft == 1){
                Value val = (Value)boolNode.leftChild;
                if(val.type == 1){
                    leftType = "INT";
                } else if(val.type == 2){
                    leftType = "BOOLEAN";
                } else if(val.type == 3 || val.type == 4){
                    //buscar el tipo el la tabla de simbolos y asignarlo a leftType
                } else {
                    return false;
                }
            }else if(boolNode.typeLeft == 2 &&){
                return false;
            }else if(boolNode.typeLeft == 3 && verifyMultTypes((MathMult)boolNode.leftChild)){
                leftType = "INT";
            }else if(boolNode.typeLeft == 4 && verifySumTypes((MathSum)boolNode.leftChild)){
                leftType = "INT";
            }else if(boolNode.typeLeft == 5){
                //Buscar el tipo en la tabla de simbolos y asignarlo a leftType
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
                } else if(val.type == 3 || val.type == 4){
                    //buscar el tipo el la tabla de simbolos y asignarlo a rightType
                } else {
                    return false;
                }
            }else if(boolNode.typeRight == 2){
                return false;
            }else if(boolNode.typeRight == 3 && verifyMultTypes((MathMult)boolNode.rightChild)){
                rightType = "INT";
            }else if(boolNode.typeRight == 4 && verifySumTypes((MathSum)boolNode.rightChild)){
                rightType = "INT";
            }else if(boolNode.typeRight == 5){
                //Buscar el tipo en la tabla de simbolos y asignarlo a rightType
            }else{
                return false;
            }

            if(leftType.equals(rightType)){
                retVal = true;
            }
        }else{
            //Si no, deben ser solamente int. No deberia decir true < 10
            //Verificación de la izquierda
            if(boolNode.typeLeft == 1){
                Value val = (Value)boolNode.leftChild;
                if(val.type == 1){
                    leftType = "INT";
                } else if(val.type == 2){
                    return false;
                } else if(val.type == 3 || val.type == 4){
                    //buscar el tipo el la tabla de simbolos y asignarlo a leftType
                } else {
                    return false;
                }
            }else if(boolNode.typeLeft == 2){
                return false;
            }else if(boolNode.typeLeft == 3 && verifyMultTypes((MathMult)boolNode.leftChild)){
                leftType = "INT";
            }else if(boolNode.typeLeft == 4 && verifySumTypes((MathSum)boolNode.leftChild)){
                leftType = "INT";
            }else if(boolNode.typeLeft == 5){
                //Buscar el tipo en la tabla de simbolos y asignarlo a leftType
            }else{
                return false;
            }

            //Verificación de la der
            if(boolNode.typeRight == 1){
                Value val = (Value)boolNode.rightChild;
                if(val.type == 1){
                    rightType = "INT";
                } else if(val.type == 2){
                    return false;
                } else if(val.type == 3 || val.type == 4){
                    //buscar el tipo el la tabla de simbolos y asignarlo a rightType
                } else {
                    return false;
                }
            }else if(boolNode.typeRight == 2){
                return false;
            }else if(boolNode.typeRight == 3 && verifyMultTypes((MathMult)boolNode.rightChild)){
                rightType = "INT";
            }else if(boolNode.typeRight == 4 && verifySumTypes((MathSum)boolNode.rightChild)){
                rightType = "INT";
            }else if(boolNode.typeRight == 5){
                //Buscar el tipo en la tabla de simbolos y asignarlo a rightType
            }else{
                return false;
            }

            if(leftType.equals(rightType)){
                retVal = true;
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
                return false;
            }else if(val.type == 2){
                checkLeft = true;
            }else if(val.type == 3 || val.type == 4){
                //buscar en la tabla el ID y compara los tipos
            } else {
                return false;
            }
        } else if(boolNode.typeLeft == 2 && verifyBoolMathTypes((BoolMathNode)boolNode.leftChild)){
            checkLeft = true;
        } else if(boolNode.typeLeft == 3 && verifyBoolAndTypes((BoolAndNode)boolNode.leftChild)){
            checkLeft = true;
        } else if(boolNode.typeLeft == 5){
            //buscar en la tabla de simbolos el id de la función
        } else {
            return false;
        }

        //Verifica el tipo de la derecha del operador
        if(boolNode.typeRight == 1){
            Value val = (Value)boolNode.rightChild;
            if(val.type == 1){
                return false;
            }else if(val.type == 2){
                checkRight = true;
            }else if(val.type == 3 || val.type == 4){
                //buscar en la tabla el ID y compara los tipos
            } else {
                checkRight = false;
            }
        } else if(boolNode.typeRight == 2 && verifyBoolMathTypes((BoolMathNode)boolNode.rightChild)){
            checkRight = true;
        } else if(boolNode.typeRight == 3 && verifyBoolAndTypes((BoolAndNode)boolNode.rightChild)){
            checkRight = true;
        } else if(boolNode.typeRight == 5){
            //buscar en la tabla de simbolos el id de la función
        } else {
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
                return false;
            }else if(val.type == 2){
                checkLeft = true;
            }else if(val.type == 3 || val.type == 4){
                //buscar en la tabla el ID y compara los tipos
            } else {
                checkLeft = false;
            }
        } else if(boolNode.typeLeft == 2 && verifyBoolOrTypes((BoolOrNode)boolNode.leftChild)){
            checkLeft = true;
        } else if(boolNode.typeLeft == 3 && verifyBoolAndTypes((BoolAndNode)boolNode.leftChild)){
            checkLeft = true;
        } else if(boolNode.typeLeft == 4 && verifyBoolMathTypes((BoolMathNode)boolNode.leftChild)){
            checkLeft = true;
        } else if(boolNode.typeLeft ==6){
            //buscar en la tabla de simbolos el id de la función
        }else{
            return false;
        }

        //Verifica el tipo de la derecha del operador
        if(boolNode.typeRight == 1){
            Value val = (Value)boolNode.rightChild;
            if(val.type == 1){
                return false;
            }else if(val.type == 2){
                checkRight = true;
            }else if(val.type == 3 || val.type == 4){
                //buscar en la tabla el ID y compara los tipos
            } else {
                checkRight = false;
            }
        } else if(boolNode.typeRight == 2 && verifyBoolOrTypes((BoolOrNode)boolNode.rightChild)){
            checkRight = true;
        } else if(boolNode.typeRight == 3 && verifyBoolAndTypes((BoolAndNode)boolNode.rightChild)){
            checkRight = true;
        } else if(boolNode.typeRight == 4 && verifyBoolMathTypes((BoolMathNode)boolNode.rightChild)){
            checkRight = true;
        } else if(boolNode.typeRight == 6){
            //buscar en la tabla de simbolos el id de la función
        }else{
            return false;
        }

        return checkLeft && checkRight;     
    }
}