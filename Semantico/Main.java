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

    public static void crearTabla(){
        tabla = new TablaSym();
        //declarations
        
    }

    public static void records(ArrayList<RecordNode> recs){

    }
    
    public static void functions(ArrayList<Object> funcs){
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

                /////////////////   AÑADIR A LA TABLA DE SIMBOLOS


                if(func.indexOf(params) != func.size() - 1)
                    type += params.type + " x ";
            }
            type += ") -> " + funcprocType;
            
            /////////////////   AÑADIR A LA TABLA DE SIMBOLOS


        }
    }

    public static void declaraciones(ArrayList<DeclNode> decls){

        for(DeclNode decl : decls){

            String type = decl.type;
            int size = map.get(type);

            for(Value val : decl.ids){

                String id = (String)val.content;
                Tupla tuplita = new Tupla(id, type, offset);
                offset += size;

            }
        }
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
                    System.out.println("Buscando el tipo, algo salió mal en verifyMathType, tipo izquierdo");
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
                    System.out.println("Buscando el tipo, algo salió mal en verifyMathType, tipo izquierdo");
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
        switch(multNode.typeLeft){
            case 1:
                Value val = (Value)multNode.leftChild;
                if(val.type == 1){
                    checkLeft = true;
                }else if(val.type == 2){
                    return false;
                }else if(val.type == 3 || val.type == 4){
                    //buscar en la tabla de simbolos
                }else{
                    System.out.println("Buscando el tipo, algo salió mal en verifyMathType, tipo izquierdo");
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
        }

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
                    System.out.println("Buscando el tipo, algo salió mal en verifyMathType, tipo izquierdo");
                }
                break;
            case 2:
                System.out.println("Se recibió un MathNode...que ondas");
                //ya no se usa MATHNODE
                break;
            case 3:
                checkRight = verifyMultTypes((MathMult)sumNode.leftChild);
                break;
            case 4:
                //function call
                //busacrlo en la tabla de simbolos
                break;
        }
        return checkLeft && checkRight;
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
            if(val.type == 1 && tipo.equals("INTEGER")){
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
            if(tipo.equals("INTEGER") && verifyMultTypes((MathMult)assigNode.expr)){
                return true;
            }else{
                return false;
            }
        } else if(assigNode.type == 6){
            if(tipo.equals("INTEGER") && verifySumTypes((MathSum)assigNode.expr)){
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
        } else if(assigNode.type == 8){
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

    }

    public static boolean verifyBoolAndTypes(BoolAndNode boolNode){

    }

    public static boolean verifyBoolOrTypes(BoolOrNode boolNode){
        
    }



    

}