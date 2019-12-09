import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

import java.io.BufferedWriter;;
import java.io.FileWriter;
import java.io.IOException;

import arbol.*;

public class CodInt {
    
    private ProgramNode root;
    public ArrayList<Instruccion> codigo;
    public static TablaSym tabla;

    private int contEtiq;   
    private int contTemp;

    private static Map<String, String> strings;

    private String ruta = "../compilado.pas";

    CodInt(ProgramNode t, TablaSym tab, String ruta){
        this.root = t;
        this.tabla = tab;

        System.out.println(ruta);
        ruta = ruta.replace(".pas", ".asm");
        System.out.println(ruta);

        this.ruta = ruta;
        this.codigo = new ArrayList();
        this.contEtiq = 0;
        this.contTemp = 0;
    }
    
    public void crearCodigoInt(){
        String fin = newEtiq();
        //records(root.records);
        declaraciones((ArrayList<DeclNode>)root.declarations);
        funciones(root.functions);
        genEtiq("\nmain");
        statements(root.statements, fin);

        genEtiq(fin);
        
        /*
        for (Instruccion i : codigo) {
            i.print();
            System.out.print("\n");
        }
        System.out.print("\n");
        System.out.print("\n");
        System.out.print("\n");
        */

        codigo = optim(codigo);

        for (Instruccion i : codigo) {
            i.print();
            System.out.print("\n");
        }
        System.out.print("\n");
        System.out.print("\n");

        String codFin = codigoFinal(codigo);

        System.out.println(codFin);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ruta))) {
            writer.write(codFin);
            writer.flush();
        }catch(IOException i){
            i.printStackTrace();
        }

    }

    private void declaraciones(ArrayList<DeclNode> decls){
        //generar el código de las declaraciones con su tipo y id
        if(decls != null){
            for(DeclNode decl : decls){
                String type = decl.type;

                for (Value val : decl.ids) {
                    String id = (String)val.content;
                    codigo.add(new Decl(id, type));
                }

            }
        }
    }

    private void statements(ArrayList<Object> states, String sig){
        //por cada statement, llamar su respectivo generador de código
        for(Object obj : states){
            if(obj instanceof IfNode){
                String sSig = newEtiq();
                genIf((IfNode)obj, sSig);
                genEtiq(sSig);
            }else if(obj instanceof WhileNode){
                String sSig = newEtiq();
                genWhile((WhileNode)obj, sSig);
                genEtiq(sSig);
            }else if(obj instanceof ForNode){
                String sSig = newEtiq();
                genFor((ForNode)obj, sSig);
                genEtiq(sSig);
            }else if(obj instanceof RepeatNode){
                String sSig = newEtiq();
                genRepeat((RepeatNode)obj, sSig);
                genEtiq(sSig);
            }else if(obj instanceof ReadNode){
                genRead((ReadNode)obj);
            }else if(obj instanceof WriteNode){
                genWrite((WriteNode)obj);
            }else if(obj instanceof AssigNode){
                genAssig((AssigNode)obj);
            }else if(obj instanceof FuncCallNode){
                genFuncCall((FuncCallNode)obj);
            }else{
                System.out.println("Error generacion código tipos...?");
            }
        }
        genGoto(sig);
    }

    //retorna una nueva etiqueta
    private String newEtiq(){
        String ret = "etiq" + contEtiq;
        contEtiq++;
        return ret;
    }
    
    //retorna un nuevo temporal
    private String newTemp(){
        String ret = "t." + contTemp;
        contTemp++;
        return ret;
    }

    //genera el código del goto con la etiqueta dada
    private void genGoto(String etiq){
        codigo.add(new GotoInt(etiq));
    }

    private void genEtiq(String et){
        codigo.add(new EtiqInt(et));
    }

    /*

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

        ////printErrorId(name, val.fila, val.columna);

        return "ERROR";
    }

    
    public static boolean verifyProcCall(FuncCallNode val){
        
        String name = (String)((Value)val.id).content;
        
        String type = getFuncType(val.args);

        Object[] res = tabla.buscarTuplaFunc(name, type, 0);

        if(res != null){
          return true;
        }else{
          //printErrorId(name, val.fila, val.columna);
          return false;
        }

         
    } 
    */

    private void funciones(ArrayList<Object> funcs){
        if(funcs != null){
            for(Object funcproc : funcs){
                Value id;
                ArrayList<ParamsNode> params;
                ArrayList<DeclNode> declarations;
                ArrayList<Object> statements;
                String funcprocType = "";
    
                if(funcproc instanceof FunctionNode){
                    FunctionNode func = (FunctionNode)funcproc;
                    id = func.id;
                    params = func.params;
                    declarations = (ArrayList<DeclNode>)func.declarations;
                    statements = func.statements;
                    funcprocType = func.type.toUpperCase();

                    ///////////AGREGAR RECORDS AQUI DESPUES!!!
                    if(funcprocType.equals("INT") || funcprocType.equals("BOOLEAN") || funcprocType.equals("CHAR") ){
                        //valido = true;
                    }
                    /*else{
                        //////SI ES RECORD HAY QUE REGRESARLO COMO ESTABA SIN EL UPPERCASE
                        funcprocType = func.type;
                    }
                    */

                }else if (funcproc instanceof ProcedureNode){
                    ProcedureNode proc = (ProcedureNode)funcproc;
                    id = proc.id;
                    params = proc.params;
                    declarations = (ArrayList<DeclNode>)proc.declarations;
                    statements = proc.statements;
                    funcprocType = "NULL";

                    String type =  proc.tipoTabla;
                    type = type.substring(0, type.indexOf(" -> "));

                    ArrayList<String> par = new ArrayList<String>();

                    //String[] tipos = type.substring(1, type.length() - 1).split(" X ");
                    for (ParamsNode v : params) {
                        for (Value va : v.ids) {
                            par.add((String)va.content);
                        }
                    }
                    type = type.replace(" ", "_");
                    type = type.replace("(", "");
                    type = type.replace(")", "");
                    //CUANDO SE ENCUENTRE UNA FUNCINT SE DEBE ALMACENAR LAS PAPADAS
                    String et = "\n_" + ((String)id.content) + "_" + type;
                    codigo.add(new FuncInt(et, declarations, par, funcprocType));
                    String etiqFunc = et + "_END";
                    statements(statements, etiqFunc);
                    genEtiq(etiqFunc);
                    //CUANDO SE ENCUENTRE UNA ETIQUETA QUE EMPIECE CON _ SE DEBE CARGAR LAS PAPADAS

                }else{
                    System.out.println("Error de tipo funcion");
                }
            }
        }
        //return valido;
    }

    private String genFuncCall(FuncCallNode fNode){
        String name = (String)((Value)fNode.id).content;

        ArrayList<String> params = new ArrayList<String>();

        String tip = fNode.tip.replace("(", "");
        tip = tip.replace(")", "");
        tip = tip.replace(" ", "_");
        tip = "_" + name + "_" + tip;

        if(fNode.args != null){

            for(Object attr : fNode.args){
    
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
                            System.out.println("Error, recibió string como parámetro");
                            break;
                        }
                        case 2:{
                            params.add(((Character)o) + "");
                            break;
                        }
                        case 3:{
                            Value val = (Value)o;
                            if(val.type == 1){
                                params.add(((Integer)val.content) + "");
                            } else if(val.type == 2){
                                if((Boolean)val.content){
                                    params.add("1");
                                }else{
                                    params.add("0");
                                }
                            } else if(val.type == 3){
                                params.add((String)val.content);
                            } else if(val.type == 4){
                                params.add(genFuncCall((FuncCallNode)val.content));
                            } else if(val.type == 5){
                                params.add(((Character)val.content) + "");
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
                            params.add(genMult((MathMult)o));
                            break;
                        }
                        case 6:{
                            params.add(genSum((MathSum)o));
                        }
                        case 7:{
                            System.out.println("Se recibió un BoolNode...que ondas");
                            //ya no se usa BOOL
                            break;
                        }
                        case 8:{
                            String verd = newEtiq();
                            String fals = newEtiq();
                            
                            genBoolMath(((BoolMathNode)o), verd, fals);
                            
                            String next = newEtiq();

                            String ret = newTemp();
                            genEtiq(verd);
                            codigo.add(new OpBin(ret, "1"));
                            genGoto(next);
                            genEtiq(fals);
                            codigo.add(new OpBin(ret, "0"));
                            params.add(ret);
                            break;
                        }
                        case 9:{
                            String verd = newEtiq();
                            String fals = newEtiq();
                            
                            genAnd(((BoolAndNode)o), verd, fals);

                            String next = newEtiq();

                            String ret = newTemp();
                            genEtiq(verd);
                            codigo.add(new OpBin(ret, "1"));
                            genGoto(next);
                            genEtiq(fals);
                            codigo.add(new OpBin(ret, "0"));
                            params.add(ret);
                            break;
                        }
                        case 10:{
                            String verd = newEtiq();
                            String fals = newEtiq();
                            
                            genOr(((BoolOrNode)o), verd, fals);
                            
                            String next = newEtiq();

                            String ret = newTemp();
                            genEtiq(verd);
                            codigo.add(new OpBin(ret, "1"));
                            genGoto(next);
                            genEtiq(fals);
                            codigo.add(new OpBin(ret, "0"));
                            params.add(ret);
                            break;
                        }
                    }
                }
            }
        }

        String ret = "NULL";
        if(fNode.ret.equals("NULL")){
            ret = newTemp();
        }
        
        codigo.add(new FuncCallInt(name, tip, params, ret));

        return ret;
    }

    private void genCondFor(ForNode forNode, String verdadero, String falso){
        String id = ((String)forNode.assig.Id.content);
        if(forNode.conditionType == 0){
            //validCondition = false;
            //Imprimir error, el tipo no es valido.
        }else{
            switch(forNode.conditionType){
                case 1:{
                    Value val = (Value)forNode.condition;
                    if(val.type == 1){
                        //validCondition =  true;
                        codigo.add(new Cond(id, "<=", ((Integer)val.content).toString(), verdadero, falso));
                    }else if(val.type == 2){
                        //validCondition = false;
                        ////printError("INT", "BOOLEAN", val.fila, val.columna);
                    }else if(val.type == 3){
                        codigo.add(new Cond(id, "<=", (String)val.content, verdadero, falso));
                    }else if(val.type == 4){
                        //TRUE

                        codigo.add(new Cond(id, "<=", genFuncCall((FuncCallNode)val.content), verdadero, falso));
                        
                    }else if(val.type == 5){
                        ////printError("CHAR", "BOOLEAN", val.fila, val.columna);
                        //validCondition = false;
                    }else{
                        System.out.println("Buscando el tipo, algo salió mal en condicion IF");
                        //validCondition = false;
                    }
                    break;
                }case 6:{
                    //validCondition = 
                    //no hay MathNode
                    break;
                }case 7:{
                    String temp = genMult((MathMult)forNode.condition);
                    codigo.add(new Cond(id, "<=", temp, verdadero, falso));
                    break;
                }case 8:{
                    String temp = genSum((MathSum)forNode.condition);
                    codigo.add(new Cond(id, "<=", temp, verdadero, falso));
                    break;
                }default:{
                    //validCondition = false;
                }
            }
        }
    }

    private void genCond(int condtype, Object condition, String verdadero, String falso){
        
        switch(condtype){
            case 1:{
                Value val = (Value)condition;
                if(val.type == 1){
                    //condicion =  false;
                }else if(val.type == 2){
                    if((Boolean)val.content){
                        genGoto(verdadero);
                    }else{
                        genGoto(falso);
                    }
                }else if(val.type == 3){
                    
                    codigo.add(new Cond((String)val.content, "=", "1", verdadero, falso));
                    
                }else if(val.type == 4){
                    //TRUE
                    
                    codigo.add(new Cond(genFuncCall((FuncCallNode)val.content), "=", "1", verdadero, falso));

                }else if(val.type == 5){
                    //condicion = false;
                }else{
                    //System.out.println("Buscando el tipo, algo salió mal en condicion IF");
                    //////printErrorInesperado(val.fila, val.columna);
                    //condicion = false;
                }
                break;
            }
            case 2:{
                System.out.println("Recibió Bool??");
                //condicion = false;
                break;
            }
            case 3:{
                genBoolMath((BoolMathNode)condition, verdadero, falso);
                break;
            }
            case 4:{
                genAnd((BoolAndNode)condition, verdadero, falso);
                break;
            }
            case 5:{
                genOr((BoolOrNode)condition, verdadero, falso);
                break;
            }
            case 6:{
                System.out.println("Recibió Math??");
                //condicion = false;
                break;
            }
            case 7:{
                //verifyMultTypes((MathMult)condition);
                //condicion = false;
                break;
            }
            case 8:{
                //verifySumTypes((MathSum)condition);
                //condicion = false;
                break;
            }
            default:{
                System.out.println("Error en verifyConditionBool???");
            }
        }
    }

    //generar el código de la comparacion booleana y luego generar el if
    private void genIf(IfNode ifn, String sig){
        
        if(ifn.ifType == 1){
            String eVerdadero = newEtiq();
            String eFalso = sig;
            
            genCond(ifn.conditionType, ifn.condition, eVerdadero, eFalso);
            
            genEtiq(eVerdadero);
    
            statements(ifn.ifStatements, sig);
            
        }else{
            String eVerdadero = newEtiq();
            String eFalso = newEtiq();
            
            genCond(ifn.conditionType, ifn.condition, eVerdadero, eFalso);
            
            genEtiq(eVerdadero);
    
            statements(ifn.ifStatements, sig);

            genGoto(sig);

            genEtiq(eFalso);

            statements(ifn.elseStatements, sig);
            
        }

    }

    //generar el código de la comparacion booleana y luego generar el if
    private void genWhile(WhileNode whn, String sig){

        String comienzo = newEtiq();
        genEtiq(comienzo);
        
        String eVerdadero = newEtiq();
        String eFalso = sig;

        genCond(whn.conditionType, whn.condition, eVerdadero, eFalso);

        genEtiq(eVerdadero);
        statements(whn.statements, comienzo);

    }

    private void genFor(ForNode fn, String sig){
        genAssig(fn.assig);
        String comienzo = newEtiq();
        String state = newEtiq();
        String sum = newEtiq();
        genEtiq(comienzo);
        genCondFor(fn, state, sig);
        genEtiq(state);
        statements(fn.statements, sum);
        genEtiq(sum);
        String temp = newTemp();
        codigo.add(new OpTer(temp, (String)fn.assig.Id.content, "+", "1"));
        codigo.add(new OpBin((String)fn.assig.Id.content, temp));
        genGoto(comienzo);
    
    }

    private void genRepeat(RepeatNode repeatNode, String sig){
        String comienzo = newEtiq();
        genEtiq(comienzo);
        String sSig = newEtiq();
        statements(repeatNode.statements, sSig);
        genEtiq(sSig);

        String eVerdadero = sig;
        String eFalso = comienzo;
        
        genCond(repeatNode.conditionType, repeatNode.condition, eVerdadero, eFalso);
        
    }

    private void genAssig(AssigNode assigNode){
        
        String id = (String)assigNode.Id.content;
        String term = "";
        
        if(assigNode.type == 2){
            String temp = "" + (Character)assigNode.expr;
            term = newTemp();
            codigo.add(new OpBin(term, temp));
        } else if(assigNode.type == 3){
            Value val = (Value)assigNode.expr;
            if(val.type == 1){
                String temp =((Integer)val.content).toString();;
                term = newTemp();
                codigo.add(new OpBin(term, temp));
            }else if(val.type == 2){
                String temp = "" + (boolean)val.content;
                term = newTemp();
                if(temp.equals("true")){
                    codigo.add(new OpBin(term, "1"));
                }else{
                    codigo.add(new OpBin(term, "0"));
                }
            }else if(val.type == 3){
                String temp = (String)val.content;
                term = newTemp();
                codigo.add(new OpBin(term, temp));
            }else if(val.type == 4){
                //TRUE

                term = genFuncCall((FuncCallNode)val.content);

            }else if(val.type == 5){
                String temp = "\'" + val.content + "\'";
                term = newTemp();
                codigo.add(new OpBin(term, temp));
            }else{
                ////printError(tipo, "-", assigNode.fila, assigNode.columna);
                //return "ERROR";
            }
        } else if(assigNode.type == 5){
            term = genMult((MathMult)assigNode.expr);
        } else if(assigNode.type == 6){
            term = genSum((MathSum)assigNode.expr);
        } else if(assigNode.type == 8){
            String verd = newEtiq();
            String fal = newEtiq();
            genBoolMath((BoolMathNode)assigNode.expr, verd, fal);
            
            String fin = newEtiq();
            term = newTemp();

            genEtiq(verd);
            codigo.add(new OpBin(term, "1"));
            genGoto(fin);

            genEtiq(fal);
            codigo.add(new OpBin(term, "0"));

            genEtiq(fin);

        } else if(assigNode.type == 9){
            String verd = newEtiq();
            String fal = newEtiq();
            genAnd((BoolAndNode)assigNode.expr, verd, fal);
            
            String fin = newEtiq();
            term = newTemp();

            genEtiq(verd);
            codigo.add(new OpBin(term, "1"));
            genGoto(fin);

            genEtiq(fal);
            codigo.add(new OpBin(term, "0"));

            genEtiq(fin);
        } else if(assigNode.type == 10){
            String verd = newEtiq();
            String fal = newEtiq();
            genOr((BoolOrNode)assigNode.expr, verd, fal);
            
            String fin = newEtiq();
            term = newTemp();

            genEtiq(verd);
            codigo.add(new OpBin(term, "1"));
            genGoto(fin);

            genEtiq(fal);
            codigo.add(new OpBin(term, "0"));

            genEtiq(fin);
        } else {
            //return "ERROR";
        }
        
        codigo.add(new OpBin(id, term));

    }

    private void genRead(ReadNode readNode){
        codigo.add(new ReadInt((String)readNode.id.content));
    }

    private void genWrite(WriteNode writeNode){
        if(writeNode.type == 1){
            codigo.add(new PrintInt(writeNode.conststr, 1));
        }else{
            codigo.add(new PrintInt(writeNode.conststr, 1));
            codigo.add(new PrintInt((String)writeNode.id.content, 2));
        }
    }

    private String genSum(MathSum sumNode){

        String term1 = "";
        String op = "";
        String term2 = "";
        
        switch(sumNode.typeLeft){
            case 1:{
                Value val = (Value)sumNode.leftChild;
                if(val.type == 1){
                    //TRUE
                    term1 = ((Integer)val.content).toString();
                }else if(val.type == 2){
                    //FALSE
                }else if(val.type == 3){
                    //TRUE

                    term1 = (String)val.content;
                    
                }else if(val.type == 4){
                    //TRUE

                    term1 = genFuncCall((FuncCallNode)val.content);
                    
                }else if(val.type == 5){
                    //FALSE
                    ////printError("INT", "CHAR", val.fila, val.columna);
                    //return false;
                }else{
                    //FALSE
                    ////printErrorInesperado(val.fila, val.columna);
                    //return false;
                }
                break;
            }case 2:{
                System.out.println("Se recibió un MathNode...que ondas");
                //ya no se usa MATHNODE
                break;
            }case 3:{
                term1 = genMult((MathMult)sumNode.leftChild);
                break;
            }case 4:{
                term1 = genSum((MathSum)sumNode.leftChild);
                break;
            }case 5:{
                term1 = genFuncCall((FuncCallNode)sumNode.leftChild);
                break;
            }default:{
                //return false;
            }
        }

        op = sumNode.operator;

        switch(sumNode.typeRight){
            case 1:{
                Value val = (Value)sumNode.rightChild;
                if(val.type == 1){
                    //TRUE
                    term2 = ((Integer)val.content).toString();
                }else if(val.type == 2){
                    //FALSE
                }else if(val.type == 3){
                    //TRUE
                    term2 = (String)val.content;

                }else if(val.type == 4){
                    //TRUE
                    
                    term2 = genFuncCall((FuncCallNode)val.content);
                    
                }else if(val.type == 5){
                    ////printError("INT", "CHAR", val.fila, val.columna);
                    //return false;
                }else{
                    ////printErrorInesperado(val.fila, val.columna);
                    //return false;
                }
                break;
            }case 2:{
                System.out.println("Se recibió un MathNode...que ondas");
                //ya no se usa MATHNODE
                break;
            }case 3:{
                term2 = genMult((MathMult)sumNode.rightChild);
                break;
            }case 4:{
                term2 = genSum((MathSum)sumNode.rightChild);
                break;
            }case 5:{
                //TRUE

                term2 = genFuncCall((FuncCallNode)sumNode.rightChild);
            }default:{
                //return false;
            }
        }

        String res = newTemp();

        codigo.add(new OpTer(res, term1, op, term2));

        return res;
    }

    private String genMult(MathMult multNode){

        String term1 = "";
        String op = "";
        String term2 = "";

        switch(multNode.typeLeft){
            case 1:{
                Value val = (Value)multNode.leftChild;
                if(val.type == 1){
                    //TRUE
                    term1 = ((Integer)val.content).toString();
                }else if(val.type == 2){
                    //FALSE
                }else if(val.type == 3){
                    //TRUE
                    term1 = (String)val.content;
                }else if(val.type == 4){
                    //TRUE

                    term1 = genFuncCall((FuncCallNode)val.content);
                    
                }else if(val.type == 5){
                    ////printError("INT", "CHAR", val.fila, val.columna);
                    //return false;
                }else{
                    ////printErrorInesperado(val.fila, val.columna);
                    //return false;
                }
                break;
            }case 2:{
                System.out.println("Se recibió un MathNode...que ondas");
                //ya no se usa MATHNODE
                break;
            }case 3:{
                term1 = genMult((MathMult)multNode.leftChild);
                break;
            }case 4:{
                //TRUE

                term1 = genFuncCall((FuncCallNode)multNode.leftChild);

                break;
            }case 5:{
                term1 = genSum((MathSum)multNode.leftChild);
                break;
            }default:{
                //return false;
            }
        }

        op = multNode.operator;

        //Verificar el hijo de la derecha
        switch(multNode.typeRight){
            case 1:{
                Value val = (Value)multNode.rightChild;
                if(val.type == 1){
                    //TRUE
                    term2 = ((Integer)val.content).toString();
                }else if(val.type == 2){
                    //return false;
                }else if(val.type == 3){
                    //TRUE
                    term2 = (String)val.content;
                }else if(val.type == 4){
                    //TRUE

                    term2 = genFuncCall((FuncCallNode)val.content);
                    
                }else if(val.type == 5){
                    ////printError("INT", "CHAR", val.fila, val.columna);
                    //return false;
                }else{
                    ////printErrorInesperado(val.fila, val.columna);
                    //return false;
                }
                break;
            }case 2:{
                System.out.println("Se recibió un MathNode...que ondas");
                //ya no se usa MATHNODE
                break;
            }case 3:{
                term2 = genMult((MathMult)multNode.rightChild);
                break;
            }case 4:{
                //TRUE

                term2 = genFuncCall((FuncCallNode)multNode.rightChild);

                break;
            }case 5:{
                term2 = genSum((MathSum)multNode.rightChild);
                break;   
            }default:{
                //return false;
            }
        }

        String res = newTemp();

        codigo.add(new OpTer(res, term1, op, term2));

        return res;

    }

    private void genBoolMath(BoolMathNode boolNode, String verdadero, String falso){
        if(boolNode.not){
            String t = verdadero;
            verdadero = falso;
            falso = t;
        }
        
        String term1 = "";
        String op = "";
        String term2 = "";

        
        //Verificación de la izquierda
        if(boolNode.typeLeft == 1){
            Value val = (Value)boolNode.leftChild;
            if(val.type == 1){
                //TRUE

                term1 = ((Integer)val.content).toString();
            } else if(val.type == 2){
                //TRUE

                term1 = (String)val.content;
            } else if(val.type == 3){
                //TRUE

                term1 = (String)val.content;
            }else if(val.type == 4){
                //TRUE

                term1 = genFuncCall((FuncCallNode)val.content);
                
            } else if(val.type == 5){
                //TRUE

                term1 = (String)val.content;
            }else{
                //return "ERROR";
            }
        }else if(boolNode.typeLeft == 2){
            //return "ERROR";
        }else if(boolNode.typeLeft == 3){
            //TRUE

            term1 = genMult((MathMult)boolNode.leftChild);
        }else if(boolNode.typeLeft == 4){
            //TRUE

            term1 = genSum((MathSum)boolNode.leftChild);
        }else if(boolNode.typeLeft == 5){
            //TRUE

            term1 = genFuncCall((FuncCallNode)boolNode.leftChild);
            
        }else{
            //return "ERROR";
        }

        op = boolNode.operator;

        //Verificación de la der
        if(boolNode.typeRight == 1){
            Value val = (Value)boolNode.rightChild;
            if(val.type == 1){
                //TRUE

                term2 = ((Integer)val.content).toString();
            } else if(val.type == 2){
                //TRUE

                term2 = (String)val.content;
            } else if(val.type == 3){
                //TRUE

                term2 = (String)val.content;
            } else if (val.type == 4){
                //TRUE

                term2 = genFuncCall((FuncCallNode)val.content);
                
            } else if(val.type == 5){
                //TRUE

                term2 = (String)val.content;
            }else{
                //return "ERROR";
            }
        }else if(boolNode.typeRight == 2){
            //return "ERROR";
        }else if(boolNode.typeRight == 3){
            //TRUE

            term2 = genMult((MathMult)boolNode.rightChild);
        }else if(boolNode.typeRight == 4){
            //TRUE

            term2 = genSum((MathSum)boolNode.rightChild);
        }else if(boolNode.typeRight == 5){
            //TRUE

            term2 = genFuncCall((FuncCallNode)boolNode.rightChild);

        }else{
            //return "ERROR";
        }

        codigo.add(new Cond(term1, op, term2, verdadero, falso));

    }

    private void genOr(BoolOrNode boolNode, String verdadero, String falso){
        if(boolNode.not){
            String t = verdadero;
            verdadero = falso;
            falso = t;
        }
       
        String e1Verdadero = verdadero;
        String e1Falso = newEtiq();
        
        String e2Verdadero = verdadero;
        String e2Falso = falso;

        //Verifica el tipo de la izquierda del operador
        if(boolNode.typeLeft == 1){
            Value val = (Value)boolNode.leftChild;
            if(val.not){
                String t = e1Verdadero;
                e1Verdadero = e1Falso;
                e1Falso = t;
            }
            if(val.type == 1){
                //FALSE
                ////printError("BOOLEAN","INT",boolNode.fila, boolNode.columna);
                //return false;
            }else if(val.type == 2){
                //TRUE

                //term1 = (String)val.content;
                if((Boolean)val.content){
                    genGoto(e1Verdadero);
                }else{
                    genGoto(e1Falso);
                }
            }else if(val.type == 3){
                //TRUE

                //term1 = (String)val.content;
                codigo.add(new Cond((String)val.content, "=", "1", e1Verdadero, e1Falso));
                
            } else if(val.type == 4){
                //TRUE

                codigo.add(new Cond(genFuncCall((FuncCallNode)val.content), "=", "1", e1Verdadero, e1Falso));
                
            } else if(val.type == 5){
                ////printError("BOOLEAN", "CHAR", boolNode.fila, boolNode.columna);
                //checkLeft = false;
            }else{
                ////printError("BOOLEAN", "-", boolNode.fila, boolNode.columna);
                //checkLeft = false;
            }
        } else if(boolNode.typeLeft == 2){
            //TRUE
            
            //term1 = genOr((BoolOrNode)boolNode.leftChild, verdadero, falso);
            genOr((BoolOrNode)boolNode.leftChild, e1Verdadero, e1Falso);
        } else if(boolNode.typeLeft == 3){
            //TRUE
            
            //term1 = genAnd((BoolAndNode)boolNode.leftChild, verdadero, falso);
            genAnd((BoolAndNode)boolNode.leftChild, e1Verdadero, e1Falso);
            
        } else if(boolNode.typeLeft == 4){
            //TRUE
            
            //term1 = genBoolMath((BoolMathNode)boolNode.leftChild, verdadero, falso);
            genBoolMath((BoolMathNode)boolNode.leftChild, e1Verdadero, e1Falso);
            
        } else if(boolNode.typeLeft ==6){
            //TRUE
            
            codigo.add(new Cond(genFuncCall((FuncCallNode)boolNode.leftChild), "=", "1", e1Verdadero, e1Falso));
            
        }else{
            ////printError("BOOLEAN", "-", boolNode.fila, boolNode.columna);
            //return false;
        }

        genEtiq(e1Falso);

        //Verifica el tipo de la derecha del operador
        if(boolNode.typeRight == 1){
            Value val = (Value)boolNode.rightChild;
            if(val.not){
                String t = e2Verdadero;
                e2Verdadero = e1Falso;
                e2Falso = t;
            }
            if(val.type == 1){
                //FALSE
                ////printError("BOOLEAN", "INT", boolNode.fila, boolNode.columna);
                //return false;
            }else if(val.type == 2){
                //TRUE

                //term2 = (String)val.content;
                if((Boolean)val.content){
                    genGoto(e2Verdadero);
                }else{
                    genGoto(e2Falso);
                }
            }else if(val.type == 3){
                //TRUE

                //term2 = (String)val.content;
                codigo.add(new Cond((String)val.content, "=", "1", e2Verdadero, e2Falso));
                
            } else if(val.type == 4){
                //TRUE
                
                codigo.add(new Cond(genFuncCall((FuncCallNode)val.content), "=", "1", e2Verdadero, e2Falso));
                
            } else if(val.type == 5){
                ////printError("BOOLEAN", "CHAR", boolNode.fila, boolNode.columna);
                //checkRight = false;
            }else{
                ////printError("BOOLEAN", "-", boolNode.fila, boolNode.columna);
                //checkRight = false;
            }
        } else if(boolNode.typeRight == 2){
            //TRUE
            
            //term2 = genOr((BoolOrNode)boolNode.rightChild, verdadero, falso);
            genOr((BoolOrNode)boolNode.rightChild, e2Verdadero, e2Falso);

        } else if(boolNode.typeRight == 3){
            //TRUE
            
            //term2 = genAnd((BoolAndNode)boolNode.rightChild, verdadero, falso);
            genAnd((BoolAndNode)boolNode.rightChild, e2Verdadero, e2Falso);

        } else if(boolNode.typeRight == 4){
            //TRUE
            
            //term2 = genBoolMath((BoolMathNode)boolNode.rightChild, verdadero, falso);
            genBoolMath((BoolMathNode)boolNode.rightChild, e2Verdadero, e2Falso);

        } else if(boolNode.typeRight == 6){
            //TRUE

            codigo.add(new Cond(genFuncCall((FuncCallNode)boolNode.rightChild), "=", "1", e2Verdadero, e2Falso));

        }else{
            ////printError("BOOLEAN", "-", boolNode.fila, boolNode.columna);
            //return false;
        }
        
    }

    private void genAnd(BoolAndNode boolNode, String verdadero, String falso){
        if(boolNode.not){
            String t = verdadero;
            verdadero = falso;
            falso = t;
            //System.out.println("ENTROOOOOOOOOOOOOOOOOOOO");
        }
        
        //propragacion de etiquetas
        String e1Verdadero = newEtiq();
        String e1Falso = falso;
        
        String e2Verdadero = verdadero;
        String e2Falso = falso;


        if(boolNode.typeLeft == 1){
            Value val = (Value)boolNode.leftChild;
            if(val.not){
                String t = e1Verdadero;
                e1Verdadero = e1Falso;
                e1Falso = t;
            }
            if(val.type == 1){
                //FALSE
                ////printError("BOOLEAN", "INT", boolNode.fila, boolNode.columna);
                //return false;
            }else if(val.type == 2){
                //TRUE
                //term1 = (Boolean)val.content;
                if((Boolean)val.content){
                    genGoto(e1Verdadero);
                }else{
                    genGoto(e1Falso);
                }
            }else if(val.type == 3){                
                //TRUE

                codigo.add(new Cond((String)val.content, "=", "1", e1Verdadero, e1Falso));
            }else if(val.type == 4){
                //TRUE
                
                String temp = genFuncCall((FuncCallNode)val.content);
                codigo.add(new Cond(temp, "=", "1", e1Verdadero, e1Falso));
                                
            } else if(val.type == 5){
                ////printError("BOOLEAN", "CHAR", boolNode.fila, boolNode.columna);
                //return false;
            }else{
                ////printError("BOOLEAN", "-", boolNode.fila, boolNode.columna);
                //return false;
            }
        } else if(boolNode.typeLeft == 2){
            //TRUE

            genBoolMath((BoolMathNode)boolNode.leftChild, e1Verdadero, e1Falso);
        } else if(boolNode.typeLeft == 3){
            //TRUE

            genAnd((BoolAndNode)boolNode.leftChild, e1Verdadero, e1Falso);
        } else if(boolNode.typeLeft == 5){
            //TRUE
            
            String temp = genFuncCall((FuncCallNode)boolNode.leftChild);
            codigo.add(new Cond(temp, "=", "1", e1Verdadero, e1Falso));
        } else {
            ////printError("BOOLEAN", "-", boolNode.fila, boolNode.columna);
            //return false;
        }

        genEtiq(e1Verdadero);

        //Verifica el tipo de la derecha del operador
        if(boolNode.typeRight == 1){
            Value val = (Value)boolNode.rightChild;
            if(val.not){
                String t = e2Verdadero;
                e2Verdadero = e2Falso;
                e2Falso = t;
            }
            if(val.type == 1){
                //FALSE
                ////printError("BOOLEAN", "INT", boolNode.fila, boolNode.columna);
                //return false;
            }else if(val.type == 2){
                //TRUE
                //term2 = (String)val.content;
                if((Boolean)val.content){
                    genGoto(e2Verdadero);
                }else{
                    genGoto(e2Falso);
                }
            }else if(val.type == 3){
                //TRUE
                //term2 = (String)val.content;
                codigo.add(new Cond((String)val.content, "=", "1", e2Verdadero, e2Falso));
            }else if(val.type == 4){
                //TRUE
                
                String temp = genFuncCall((FuncCallNode)val.content);
                codigo.add(new Cond(temp, "=", "1", e2Verdadero, e2Falso));
                
            } else if(val.type == 5){
                //FALSE
                ////printError("BOOLEAN", "CHAR", boolNode.fila, boolNode.columna);
                //checkRight = false;
            }else{
                //FALSE
                ////printError("BOOLEAN", "-", boolNode.fila, boolNode.columna);
                //checkRight = false;
            }
        } else if(boolNode.typeRight == 2){
            //TRUE
            
            genBoolMath((BoolMathNode)boolNode.rightChild, e2Verdadero, e2Falso);
        } else if(boolNode.typeRight == 3){
            //TRUE
            
            genAnd((BoolAndNode)boolNode.rightChild, e2Verdadero, e2Falso);
        } else if(boolNode.typeRight == 5){
            //TRUE
            
            String temp = genFuncCall((FuncCallNode)boolNode.rightChild);
            codigo.add(new Cond(temp, "=", "1", e2Verdadero, e2Falso));

        } else {
            ////printError("BOOLEAN", "-", boolNode.fila, boolNode.columna);
            //return false;
        }

    }

    private ArrayList<Instruccion> optim( ArrayList<Instruccion> codigo){
        if(codigo != null){
            for (int index = 0; index < codigo.size(); index++) {
                if(index != codigo.size() - 1){
                    Instruccion ins = codigo.get(index);
                    if(ins instanceof GotoInt){
                        
                        Instruccion next = codigo.get(index + 1);
                        if(next instanceof GotoInt){
                            codigo.remove(index + 1);
                            index--;
                        }else if(next instanceof EtiqInt && ((GotoInt)ins).etiqueta.equals(((EtiqInt)next).etiq) ){
                            codigo.remove(index);
                            index--;
                        }
                    }
                }
            }
        }
        return codigo;
    }

    private String codigoFinal(ArrayList<Instruccion> codigo){
        tabla.print(0);
        String fin = "";
        if(codigo != null){
            fin += "\t.data\n";
            strings = new HashMap<>();
            int contMsg = 0;
            for (int i = 0; i < codigo.size(); i++) {
                Instruccion ins = codigo.get(i);
                if(ins instanceof Decl){
                    fin += "_" + ((Decl)ins).assig + ":";
                    String type = ((Decl)ins).type;
                    if(type.equals("INT") || type.equals("CHAR") || type.equals("BOOLEAN")){
                        fin += "\t.word 0";
                    }
                    /*
                    else if(condicion de funciones y records){

                        ///////////////////////////////////////////////////////////////////////
                        
                    }
                    */

                    fin += "\n";
                    codigo.remove(i);
                    i--;
                }else if(ins instanceof PrintInt && ((PrintInt)ins).type == 1){
                    String content = ((PrintInt)ins).cont;
                    if(!content.equals("")){
                        if(!strings.containsKey(content)){
                            String msgString = "_msg" + contMsg;
                            contMsg++;
                            fin += "_" + msgString + ":\t.asciiz \"" + content + "\"\n";
                            strings.put(content, msgString);
                            ((PrintInt)ins).cont = msgString;
                        }else{
                            ((PrintInt)ins).cont = strings.get(content);
                        }
                    }else{
                        codigo.remove(i);
                    }
                }
            }

            fin += "\n\t.text\n\t.globl main";

            //relaciona temporales
            Map<String, String> relTemp = new HashMap<>();
            Map<String, Integer> stackPos = new HashMap<>();
            boolean[] activeTemp = new boolean[9];

            //relaciona temporales
            ArrayList<Map<String, String>> relTempOld = new ArrayList<Map<String, String>>();
            ArrayList<Map<String, Integer>> stackPosOld = new ArrayList<Map<String, Integer>>();
            ArrayList<boolean[]> activeTempOld = new ArrayList<boolean[]>();

            //relaciona Ss
            Map<String, String> relS = new HashMap<>();
            boolean[] activeS = new boolean[6];

            //relaciona temporales
            ArrayList<Map<String, String>> relSOld = new ArrayList<Map<String, String>>();
            ArrayList<boolean[]> activeSOld = new ArrayList<boolean[]>();

            for (boolean b : activeTemp)
                b = false;

            for (Instruccion ins : codigo) {

                if(ins instanceof OpBin){
                    OpBin insOp = (OpBin)ins;
                    if(insOp.opp.equals("no")){
                        String assig = insOp.assig;
                        String term = insOp.term;
                        //Termino de asignacion
                        if(assig.length() > 1 && assig.charAt(1) == '.'){
                            //es un temporal
                            String opp = "";
                            if(term.charAt(0) == '\'' || (term.charAt(0) - 48 >= 0 && term.charAt(0) - 48 <= 9)){
                                opp = "li";
                            }else{
                                opp = "lw";
                                if(!stackPos.containsKey(term) && !relS.containsKey(term)){
                                    term = "_" + term;      
                                }else if(relS.containsKey(term)){
                                    opp = "move";
                                    term = relS.get(term);
                                }else{
                                    int varPila = stackPos.get(term);
                                    term = -varPila + "($sp)";
                                }
                            }
                            int actT = getActiveTemp(activeTemp);
                            if(actT != -1){
                                //hay un temporal libre
                                activeTemp[actT] = true;
                                relTemp.put(assig, "$t" + actT);
                                
                                
                                fin += "\t" + opp + " $t" + actT + ", " + term + "\n";

                            }else{
                                //hay que agregarlo a la pila
                                int stackTemp = getNewStackPos(stackPos);
                                stackPos.put(assig, stackTemp);
                                assig = -stackTemp + "($sp)";

                                fin += "\t" + opp + " $t9, " + term + "\n"
                                    +  "\tsw $t9 ," + assig + "\n";

                            }

                            //termino de la derecha tiene que ser una variable o un numero/caractér a fuerza

                        }else{
                            //es una variable
                            String opp = "sw";
                            if(stackPos.containsKey(assig)){
                                assig = -stackPos.get(assig) + "($sp)";
                            }else{
                                if(!stackPos.containsKey(assig) && !relS.containsKey(assig)){
                                    assig = "_" + assig;
                                }else if(relS.containsKey(assig)){
                                    opp = "move";
                                    assig = relS.get(assig);
                                }else{
                                    int varPila = stackPos.get(assig);
                                    assig = -varPila + "($sp)";
                                }
                            }
                            if(term.length() > 1 && term.charAt(1) == '.'){
                                
                                if(relTemp.containsKey(term)){
                                    //está en un temporal
                                    String temp = relTemp.get(term);
                                    relTemp.remove(term);
                                    int tempNum = temp.charAt(temp.length() - 1) - 48;
                                    activeTemp[tempNum] = false;
                                    
                                    if(opp.equals("sw")){
                                        fin += "\tsw " + temp + ", " + assig + "\n";
                                    }else{
                                        fin += "\tmove " + assig + ", " + temp + "\n";
                                    }
                                    
                                }else if(stackPos.containsKey(term)){
                                    //está en la pila
                                    
                                    int stackTemp = stackPos.get(term);
                                    stackPos.remove(term);
                                    term = -stackTemp + "($sp)";
                                    
                                    fin += "\tlw $t9, " + term + "\n";
                                    if(opp.equals("sw")){
                                        fin += "\tsw $t9, " + assig + "\n";
                                    }else{
                                        fin += "\tmove " + assig + ", $t9\n";
                                    }
                                    
                                }else{
                                    fin += "\tERROR\n";
                                }

                            }else{
                                fin += "\tsw " + "ERROR" + ", " + assig + "\n";
                            }


                            //termino de la derecha tiene que ser un temporal a fuerza
                        }
                    }
                }else if(ins instanceof OpTer){
                    OpTer insOp = (OpTer)ins;

                    //estos se usan para saber cual es el valor de código intermedio, y para saber donde se restaurará cualquier valor de registro perdido en el proceso de abajo
                    String assig = insOp.assig;
                    String term = insOp.term1;
                    String term2 = insOp.term2;

                    //Estas son las variables que serán usadas en la opreacion de asignacion final... se modifican dentro de los casos
                    String opp = insOp.opp;     //Operación final
                    String first = "";          //Donde se asignará el valor resultante (primer valor)
                    String second = "";         //primer operando (segundo valor)
                    String third = "";          //segundo operando (tercer valor)

                    //donde se asigna debe ser por fuerza un temporal, y como se está asignando, a fuerza tiene que ser uno nuevo
                    int actT = getActiveTemp(activeTemp);
                    if(actT != -1){
                        //hay un temporal libre
                        activeTemp[actT] = true;
                        first = "$t" + actT;
                        relTemp.put(assig, first);

                    }else{
                        //hay que agregarlo a la pila
                        int stackTemp = getNewStackPos(stackPos);
                        stackPos.put(assig, stackTemp);

                        first = -stackTemp + "($sp)";

                        fin += "\tlw $t9, " + first + "\n";
                        first = "$t9";
                        //temporal 9 lo tengo reservado, por lo tanto no es necesario restaurar su valor original
                    }
                    
                    //El primer operando (segundo valor)
                    if(term.length() > 1 && term.charAt(1) == '.'){
                        //es un temporal

                        if(relTemp.containsKey(term)){
                            //es un registro temporal
                            second = relTemp.get(term);
                            relTemp.remove(term);
                            int tempNum = second.charAt(second.length() - 1) - 48;
                            activeTemp[tempNum] = false;
                            //Como está en el lado derecho de una asignación, ya no se seguirá utilizando y se puede liberar el registro

                        }else if(stackPos.containsKey(term)){
                            //está en la pila

                            int tempS = getNewStackPos(stackPos);
                            stackPos.put("$s6", tempS);
                            
                            int stackTemp = stackPos.get(term);
                            stackPos.remove(term);

                            second = -stackTemp + "($sp)";
                            term = -tempS + "($sp)";

                            //Se almacena el valor del registro $s6 en un nuevo espacio de pila
                            //term tiene almacenado
    
                            fin +=  "\tsw $s6, " + term + "\n"
                                +   "\tlw $s6, " + second + "\n";
                            second = "$s6";
                                
                        }else{
                            //...no existe??
                        }

                    }else if(term.charAt(0) == '\'' || (term.charAt(0) - 48 >= 0 && term.charAt(0) - 48 <= 9)){
                        //el segundo termino es un caracter o entero
                        int tempS = getNewStackPos(stackPos);
                        stackPos.put("$s6", tempS);

                        
                        fin +=  "\tsw $s6, " + -tempS + "($sp)\n"
                            +   "\tli $s6, " + term + "\n";
                        second = "$s6";

                        term = -tempS + "($sp)";
                        //guarda el estado de s0 en la pila para recuperarlo despues de usar s0 para almacenar el segundo termino

                    }else{
                        //es una variable

                        if(relS.containsKey(term)){
                            second = relS.get(term);
                        }else{
                            if(!stackPos.containsKey(term) && !relS.containsKey(term)){
                                term = "_" + term;
                            }else{
                                int varPila = stackPos.get(term);
                                term = -varPila + "($sp)";
                            }
                            
                            int tempS = getNewStackPos(stackPos);
                            stackPos.put("$s6", tempS);
    
                            
                            fin +=  "\tsw $s6, " + -tempS + "($sp)\n"
                                +   "\tlw $s6, " + term + "\n";
                            second = "$s6";
    
                            term = -tempS + "($sp)";
                            //guarda el estado de s0 en la pila para recuperarlo despues de usar s0 para almacenar el segundo termino
                        }


                    }

                    //El segundo operando (tercer valor)
                    if(term2.length() > 1 && term2.charAt(1) == '.'){
                        //es un temporal

                        if(relTemp.containsKey(term2)){
                            //es un registro temporal
                            third = relTemp.get(term2);
                            relTemp.remove(term2);
                            int tempNum = third.charAt(third.length() - 1) - 48;
                            activeTemp[tempNum] = false;
                            //Como está en el lado derecho de una asignación, ya no se seguirá utilizando y se puede liberar el registro

                        }else if(stackPos.containsKey(term2)){
                            //está en la pila

                            int tempS = getNewStackPos(stackPos);
                            stackPos.put("$s7", tempS);
                            
                            int stackTemp = stackPos.get(term2);
                            stackPos.remove(term2);

                            third = -stackTemp + "($sp)";
                            term2 = -tempS + "($sp)";

                            //Se almacena el valor del registro $s7 en un nuevo espacio de pila
                            //term2 tiene almacenado
    
                            fin +=  "\tsw $s7, " + term2 + "\n"
                                +   "\tlw $s7, " + third + "\n";
                            third = "$s7";
                                
                        }else{
                            //...no existe??
                        }

                    }else if(term2.charAt(0) == '\'' || (term2.charAt(0) - 48 >= 0 && term2.charAt(0) - 48 <= 9)){
                        //el segundo termino es un caracter o entero
                        
                        third = term2;

                    }else{
                        //es una variable
                        if(relS.containsKey(term2)){
                            third = relS.get(term2);
                        }else{

                            if(!stackPos.containsKey(term2) && !relS.containsKey(term2)){
                                term2 = "_" + term2;
                            }else{
                                int varPila = stackPos.get(term2);
                                term2 = -varPila + "($sp)";
                            }
                            
                            int tempS = getNewStackPos(stackPos);
                            stackPos.put("$s7", tempS);
    
                            
                            fin +=  "\tsw $s7, " + -tempS + "($sp)\n"
                                +   "\tlw $s7, " + term2 + "\n";
                            third = "$s7";
    
                            term2 = -tempS + "($sp)";
                        }

                        //guarda el estado de s0 en la pila para recuperarlo despues de usar s0 para almacenar el segundo termino

                    }

                    System.out.println("OPP: " + opp);
                    switch(opp){
                        case "+":{
                            fin += "\tadd ";
                            break;
                        }
                        case "-":{
                            fin += "\tsub ";
                            break;
                        }
                        case "*":{
                            fin += "\tmul ";
                            break;
                        }
                        case "/":{
                            fin += "\tdiv ";
                            break;
                        }
                        default:{
                            fin +="\tERROR ";
                        }
                    }

                    fin += first + ", " + second + ", " + third + "\n";

                    if(second.equals("$s6")){
                        fin +=  "\tlw $s6, " + term + "\n";
                        stackPos.remove("$s6");
                    }

                    if(third.equals("$s7")){
                        fin +=  "\tlw $s7, " + term2 + "\n";
                        stackPos.remove("$s7");
                    }


                }else if(ins instanceof Cond){
                    Cond con = (Cond) ins;
                    
                    //estos se usan para saber cual es el valor de código intermedio, y para saber donde se restaurará cualquier valor de registro perdido en el proceso de abajo
                    String term = con.term1;
                    String term2 = con.term2;

                    //Estas son las variables que serán usadas en la opreacion de asignacion final... se modifican dentro de los casos
                    String opp = con.opp;     //Operación final
                    String second = "";         //primer operando (primer valor)
                    String third = "";          //segundo operando (segundo valor)

                    //SE LLAMAN SECOND Y THIRD PORQUE LE DI COPY PASTE A LO DE ARRIBA
                    
                    //El primer operando (segundo valor)
                    if(term.length() > 1 && term.charAt(1) == '.'){
                        //es un temporal

                        if(relTemp.containsKey(term)){
                            //es un registro temporal
                            second = relTemp.get(term);
                            relTemp.remove(term);
                            int tempNum = second.charAt(second.length() - 1) - 48;
                            activeTemp[tempNum] = false;
                            //Como está en el lado derecho de una asignación, ya no se seguirá utilizando y se puede liberar el registro

                        }else if(stackPos.containsKey(term)){
                            //está en la pila

                            int tempS = getNewStackPos(stackPos);
                            stackPos.put("$s6", tempS);
                            
                            int stackTemp = stackPos.get(term);
                            stackPos.remove(term);

                            second = -stackTemp + "($sp)";
                            term = -tempS + "($sp)";

                            //Se almacena el valor del registro $s6 en un nuevo espacio de pila
                            //term tiene almacenado
    
                            fin +=  "\tsw $s6, " + term + "\n"
                                +   "\tlw $s6, " + second + "\n";
                            second = "$s6";
                                
                        }else{
                            //...no existe??
                        }

                    }else if(term.charAt(0) == '\'' || (term.charAt(0) - 48 >= 0 && term.charAt(0) - 48 <= 9)){
                        //el segundo termino es un caracter o entero
                        int tempS = getNewStackPos(stackPos);
                        stackPos.put("$s6", tempS);

                        
                        fin +=  "\tsw $s6, " + -tempS + "($sp)\n"
                            +   "\tli $s6, " + term + "\n";
                        second = "$s6";

                        term = -tempS + "($sp)";
                        //guarda el estado de s0 en la pila para recuperarlo despues de usar s0 para almacenar el segundo termino

                    }else{
                        //es una variable
                        if(relS.containsKey(term)){
                            second = relS.get(term);
                        }else{
                            
                            if(!stackPos.containsKey(term) && !relS.containsKey(term)){
                                term = "_" + term;
                            }else{
                                int varPila = stackPos.get(term);
                                term = -varPila + "($sp)";
                            }
                            
                            int tempS = getNewStackPos(stackPos);
                            stackPos.put("$s6", tempS);
    
                            
                            fin +=  "\tsw $s6, " + -tempS + "($sp)\n"
                                +   "\tlw $s6, " + term + "\n";
                            second = "$s6";
    
                            term = -tempS + "($sp)";
                        }
                        //guarda el estado de s0 en la pila para recuperarlo despues de usar s0 para almacenar el segundo termino

                    }

                    //El segundo operando (tercer valor)
                    if(term2.length() > 1 && term2.charAt(1) == '.'){
                        //es un temporal

                        if(relTemp.containsKey(term2)){
                            //es un registro temporal
                            third = relTemp.get(term2);
                            relTemp.remove(term2);
                            int tempNum = third.charAt(third.length() - 1) - 48;
                            activeTemp[tempNum] = false;
                            //Como está en el lado derecho de una asignación, ya no se seguirá utilizando y se puede liberar el registro

                        }else if(stackPos.containsKey(term2)){
                            //está en la pila

                            int tempS = getNewStackPos(stackPos);
                            stackPos.put("$s7", tempS);
                            
                            int stackTemp = stackPos.get(term2);
                            stackPos.remove(term2);

                            third = -stackTemp + "($sp)";
                            term2 = -tempS + "($sp)";

                            //Se almacena el valor del registro $s7 en un nuevo espacio de pila
                            //term2 tiene almacenado
    
                            fin +=  "\tsw $s7, " + term2 + "\n"
                                +   "\tlw $s7, " + third + "\n";
                            third = "$s7";
                                
                        }else{
                            //...no existe??
                        }

                    }else if(term2.charAt(0) == '\'' || (term2.charAt(0) - 48 >= 0 && term2.charAt(0) - 48 <= 9)){
                        //el segundo termino es un caracter o entero
                        
                        third = term2;

                    }else{
                        //es una variable
                        if(relS.containsKey(term2)){
                            third = relS.get(term2);
                        }else{
                            
                            if(!stackPos.containsKey(term2) && !relS.containsKey(term2)){
                                term2 = "_" + term2;
                            }else{
                                int varPila = stackPos.get(term2);
                                term2 = -varPila + "($sp)";
                            }
                            
                            int tempS = getNewStackPos(stackPos);
                            stackPos.put("$s7", tempS);
    
                            
                            fin +=  "\tsw $s7, " + -tempS + "($sp)\n"
                                +   "\tlw $s7, " + term2 + "\n";
                            third = "$s7";
    
                            term2 = -tempS + "($sp)";
                            //guarda el estado de s0 en la pila para recuperarlo despues de usar s0 para almacenar el segundo termino
                        }

                    }


                    //<>|=|>|<|>=|<=
                    switch(con.opp){
                        case "=":{
                                fin += "\tbeq ";
                                break;
                            }
                            case ">":{
                                fin += "\tbgt ";
                                break;
                            }
                            case "<":{
                                fin += "\tblt ";
                                break;
                            }
                            case ">=":{
                                fin += "\tbge ";
                                break;
                            }
                            case "<=":{
                                fin += "\tble ";
                                break;
                            }
                            default:{
                                fin += "\tERROR ";
                            break;
                        }
                    }

                    fin += second + ", " + third + "," + con.gotoTrue + "\n"
                        +  "\tb " + con.gotoFalse + "\n";

                    if(second.equals("$s6")){
                        fin +=  "\tlw $s6, " + term + "\n";
                        stackPos.remove("$s6");
                    }

                    if(third.equals("$s7")){
                        fin +=  "\tlw $s7, " + term2 + "\n";
                        stackPos.remove("$s7");
                    }

                }else if(ins instanceof EtiqInt){
                    fin += ((EtiqInt)ins).etiq + ":\n";
                    if(((EtiqInt)ins).etiq.charAt(1) == '_'){
                        //AGREGAR LAS PAPADAS
                        fin += "\tmove $sp, $fp\n";
                        
                        for(int i = 7; i >= 0 ; i--){
                            fin += "\tlw $s" + i + ", " + -(((i + 1) * 4) + 8) + "($sp)\n";
                        }
                        
                        fin += "\tlw $ra, -8($sp)\n"
                            +  "\tsw $fp, -4($sp)\n"
                            +  "\tjr $ra\n";

                    }else if(((EtiqInt)ins).etiq.equals("\nmain")){
                        fin += "\tmove $fp, $sp\n";
                    }
                }else if(ins instanceof GotoInt){
                    fin += "\tb " + ((GotoInt)ins).etiqueta + "\n";
                }else if(ins instanceof ReadInt){
                    String var = ((ReadInt)ins).id;
                    String opp = "sw";
                    Object[] tup = tabla.buscarTuplaDown(var, 0);
                    if(!stackPos.containsKey(var) && !relS.containsKey(var)){
                        var = "_" + var;
                    }else if(relS.containsKey(var)){
                        var = relS.get(var);
                        opp = "move";
                    }else{
                        int varPila = stackPos.get(var);
                        var = -varPila + "($sp)";
                    }
                    if(((Tupla)tup[0]).type.equals("INT")){
                        fin += "\tli $v0, 5\n";
                    }else if(((Tupla)tup[0]).type.equals("CHAR")){
                        fin += "\tli $v0, 12\n";
                    }
                    fin += "\tsyscall\n";
                    if(opp.equals("sw")){
                        fin += "\tsw $v0, " + var + "\n";
                    }else{
                        fin += "\tmove " + var + ", $v0\n";
                    }
                }else if(ins instanceof PrintInt){
                    //2 = id, 1 = string
                    String var = ((PrintInt)ins).cont;
                    String org = var;
                    String opp = "lw";
                    if(!stackPos.containsKey(var) && !relS.containsKey(var)){
                        var = "_" + var;
                    }else if(relS.containsKey(var)){
                        opp = "move";
                        var = relS.get(var);
                    }else{
                        int varPila = stackPos.get(var);
                        var = -varPila + "($sp)";
                    }
                
                    if(((PrintInt)ins).type == 1){
                        fin += "\tli $v0, 4\n"
                        +  "\tla $a0, " + var + "\n"
                        +  "\tsyscall\n";
                    }else{
                        Object[] tup = tabla.buscarTuplaDown(org, 0);
                        if(tup != null){
                            if(((Tupla)tup[0]).type.equals("INT")){
                                fin += "\tli $v0, 1\n";
                            }else if(((Tupla)tup[0]).type.equals("CHAR")){
                                fin += "\tli $v0, 11\n";
                            }
                            if(opp == "lw"){
                                fin += "\tlw $a0, " + var + "\n";
                            }else{
                                fin += "\tmove $a0, " + var + "\n";
                            }
                            fin += "\tsyscall\n";
                        }
                    }
                }else if(ins instanceof FuncInt){
                    FuncInt fun = ((FuncInt)ins);

                    stackPosOld.add(stackPos);
                    relTempOld.add(relTemp);
                    activeTempOld.add(activeTemp);

                    relSOld.add(relS);
                    activeSOld.add(activeS);

                    fin += fun.func + ": \n"
                    +  "\tsw $fp, -4($sp)\n"
                    +  "\tsw $ra, -8($sp)\n";

                    for(int i = 0; i < 8 ; i++){
                        if(i < 6 && activeS[i]){
                            fin += "\tsw $s" + i + ", " + (-(((i + 1) * 4) + 8)) + "($sp)\n";
                        }
                    }

                    stackPos = new HashMap<>();
                    relTemp = new HashMap<>();
                    activeTemp = new boolean[9];

                    relS = new HashMap<>();
                    activeS = new boolean[6];
                    
                    ArrayList<DeclNode> decls = fun.decl;
                    int cont = 0;
                    if(decls != null){
                        for(DeclNode decl : decls){
                            String type = decl.type;
            
                            for (Value val : decl.ids) {
                                String id = (String)val.content;
                                stackPos.put(id, -(cont * 4));
                                cont++;
                            }
            
                        }
                    }
                    
                    int contParam = 0;
                    for (int i = 0; i < fun.params.size(); i++) {
                        String funPar = fun.params.get(i);
                        if(i <= 3){
                            int actT = getActiveTemp(activeS);
                            if(actT != -1){
                                //hay un temporal libre
                                activeS[actT] = true;
                                relS.put(funPar, "$s" + actT);
                                
                                fin += "\tmove " + "$s" + actT + ", $a" + i + "\n";
                                continue;
                            }else{
                                //hay que agregarlo a la pila
                                int stackTemp = getNewStackPos(stackPos);
                                stackPos.put(funPar, stackTemp);
                                String ret = -stackTemp + "($sp)";
                                
                                
                                fin += "\tsw $a" + i + ", " + ret + "\n";
                            }
                        }else{
                            int stackTemp = getNewStackPos(stackPos);
                            stackPos.put(funPar, stackTemp);
                            String ret = -stackTemp + "($sp)";
                            
                            fin += "\tlw $s7, " + contParam + "($sp)\n"
                                +  "\tsw $s7, " + ret + "\n";
                            contParam += 4;
                        }
                    }
                    fin += "\tmove $fp, $sp\n"
                    +  "\tsub $sp, $sp, " +  ((cont * 4) + 40) + "\n";
                    
                }else if(ins instanceof FuncCallInt){
                    FuncCallInt fc = (FuncCallInt) ins;

                    int contTemp = 0;
                    for(int i = 0; i < 10 ; i++){
                        if((i < 9 && activeTemp[i]) || i == 9){
                            int stackTemp = getNewStackPos(stackPos);
                            stackPos.put("$t" + i, stackTemp);
                            fin += "\tsw $t" + i + ", " + (-stackTemp) + "($sp)\n";
                        }
                    }

                    int maxPos = getMaxOffset(stackPos);
                    fin += "\tsub $sp, $sp, " + (-maxPos) + "\n";

                    int overflow = (fc.params.size() - 4) * 4;
                    int posParam = maxPos + overflow;
                    for (int i = 0; i < fc.params.size(); i++) {
                        String term = fc.params.get(i);
                        String res = "";
                        if(term.length() > 1 && term.charAt(1) == '.'){
                            //es un temporal
    
                            if(relTemp.containsKey(term)){
                                //es un registro temporal
                                res = relTemp.get(term);
                                relTemp.remove(term);
                                int tempNum = res.charAt(res.length() - 1) - 48;
                                activeTemp[tempNum] = false;
    
                            }else if(stackPos.containsKey(term)){
                                //está en la pila
    
                                int tempS = getNewStackPos(stackPos);
                                stackPos.put("$s7", tempS);
                                
                                int stackTemp = stackPos.get(term);
                                stackPos.remove(term);
    
                                res = -stackTemp + "($sp)";
                                term = -tempS + "($sp)";
    
                                //Se almacena el valor del registro $s7 en un nuevo espacio de pila
                                //term tiene almacenado
        
                                fin +=  "\tsw $s7, " + term + "\n"
                                    +   "\tlw $s7, " + res + "\n";
                                res = "$s7";
                                    
                            }else{
                                //...no existe??
                            }
    
                        }else if(term.charAt(0) == '\'' || (term.charAt(0) - 48 >= 0 && term.charAt(0) - 48 <= 9)){
                            //el segundo termino es un caracter o entero

                            int tempS = getNewStackPos(stackPos);
                            stackPos.put("$s7", tempS);

                            //Se almacena el valor del registro $s7 en un nuevo espacio de pila
                            //term tiene almacenado
    
                            fin +=  "\tsw $s7, " + (-tempS) + "($sp)" + "\n"
                                +   "\tli $s7, " + term + "\n";
                            term = (-tempS) + "($sp)";
                            res = "$s7";
    
                        }else{
                            //es una variable
                            if(relS.containsKey(term)){
                                res = relS.get(term);
                            }else{
                                
                                if(!stackPos.containsKey(term) && !relS.containsKey(term)){
                                    term = "_" + term;
                                }else{
                                    int varPila = stackPos.get(term);
                                    term = -varPila + "($sp)";
                                }
                                
                                int tempS = getNewStackPos(stackPos);
                                stackPos.put("$s7", tempS);
        
                                
                                fin +=  "\tsw $s7, " + -tempS + "($sp)\n"
                                    +   "\tlw $s7, " + term + "\n";
                                res = "$s7";
        
                                term = -tempS + "($sp)";
                                //guarda el estado de s0 en la pila para recuperarlo despues de usar s0 para almacenar el segundo termino
                            }
    
                        }

                        if(i <= 3){
                            fin += "\tmove $a" + i + ", " + res + "\n";
                        }else{
                            fin += "\tsw " + res + ", " + (-posParam) + "($sp)\n";
                            posParam -= 4;
                        }

                        if(res.equals("$s7")){
                            fin +=  "\tlw $s7, " + term + "\n";
                            stackPos.remove("$s7");
                        }

                    }
                    if(overflow > 0){
                        fin += "\tsub $sp, $sp, " + overflow + "\n";
                    }
                    
                    fin += "\tjal " + fc.etiq + "\n";
                    
                    if(overflow > 0){
                        fin += "\tadd $sp, $sp, " + overflow + "\n";
                    }
                    
                    fin += "\tadd $sp, $sp, " + maxPos + "\n";

                    for(int i = 9; i >= 0 ; i--){
                        if((i < 9 && activeTemp[i]) || i == 9){
                            int pos = stackPos.get("$t" + i);
                            stackPos.remove("$t" + i);
                            fin += "\tlw $t" + i + ", " + -pos + "($sp)\n";
                        }
                    }
                    
                    if(!fc.ret.equals("NULL")){
                        int actT = getActiveTemp(activeTemp);
                        if(actT != -1){
                            //hay un temporal libre
                            activeTemp[actT] = true;
                            relTemp.put(fc.ret, "$t" + actT);
                            
                            fin += "\tmove " + "$t" + actT + ", $v0\n";
                            
                        }else{
                            //hay que agregarlo a la pila
                            int stackTemp = getNewStackPos(stackPos);
                            stackPos.put(fc.ret, stackTemp);
                            String ret = -stackTemp + "($sp)";
                            
    
                            fin += "\tsw $v0, " + ret + "\n";

                        }
                    }
                    
                }else{
                    System.out.println("what?!");
                }
            }
            fin += "\tli $v0, 10\n"
	            + "\tsyscall";
            return fin;
        }else{
            return "ERROR";
        }
    }

    private int getNewStackPos(Map<String, Integer> stackPos){
        int offset = 4;
        while(((Map)stackPos).containsValue(offset)){
            offset += 4;
        }
        return offset;
    }
    
    private int getMaxOffset(Map<String, Integer> stackPos){
        return Collections.max(stackPos.values());
    }

    private int getActiveTemp(boolean[] temp){
        for (int i = 0; i < temp.length; i++) {
            if(!temp[i]){
                return i;
            }
        }
        return -1;
    }
    
}