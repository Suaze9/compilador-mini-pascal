import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

import arbol.*;

public class CodInt {
    
    private ProgramNode root;
    public ArrayList<Instruccion> codigo;
    public static TablaSym tabla;

    private int contEtiq;
    private int contTemp;

    public static Map<String, String> strings;

    CodInt(ProgramNode t, TablaSym tab){
        this.root = t;
        this.tabla = tab;
        this.codigo = new ArrayList();
        this.contEtiq = 0;
        this.contTemp = 0;
    }
    
    public void crearCodigoInt(){
        String fin = newEtiq();
        //records(root.records);
        declaraciones((ArrayList<DeclNode>)root.declarations);
        //funciones(root.functions);
        statements(root.statements, fin);

        genEtiq(fin);
        
        for (Instruccion i : codigo) {
            i.print();
            System.out.print("\n");
        }
        System.out.print("\n");
        System.out.print("\n");
        System.out.print("\n");

        codigo = optim(codigo);

        for (Instruccion i : codigo) {
            i.print();
            System.out.print("\n");
        }
        System.out.print("\n");

        String codFin = codigoFinal(codigo);

        System.out.println(codFin);

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
                //genFuncCall((FuncCallNode)obj).equals("ERROR");
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
                        //printError("INT", "BOOLEAN", val.fila, val.columna);
                    }else if(val.type == 3){
                        codigo.add(new Cond(id, "<=", (String)val.content, verdadero, falso));
                    }else if(val.type == 4){
                        //TRUE

                        //String ret = verifyFuncCall((FuncCallNode)val.content);
                        //if(ret.equals("INT")){
                        //    validCondition = true;
                        //}else{
                        //    validCondition = false;
                        //    if(!ret.equals("ERROR"))
                        //        //printError("INT", ret, val.fila, val.columna);
                        //}
                    }else if(val.type == 5){
                        //printError("CHAR", "BOOLEAN", val.fila, val.columna);
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

                    //String ret = verifyFuncCall((FuncCallNode)val.content);
                    //if(ret.equals("BOOELAN")){
                    //    condicion = true;
                    //}else{
                    //    condicion = false;
                    //    if(!ret.equals("ERROR"))
                    //        //printError("BOOLEAN", ret, val.fila, val.columna);
                    //}
                }else if(val.type == 5){
                    //condicion = false;
                }else{
                    //System.out.println("Buscando el tipo, algo salió mal en condicion IF");
                    ////printErrorInesperado(val.fila, val.columna);
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

                //String funcType = verifyFuncCall((FuncCallNode)val.content);
                //if(funcType.equals(tipo)){
                //    return true;
                //}else if(funcType.equals("ERROR")){
                //    return false;
                //}else{
                //    //printError(tipo, funcType, assigNode.fila, assigNode.columna);
                //    return false;
                //}
            }else if(val.type == 5){
                String temp = "\'" + val.content + "\'";
                term = newTemp();
                codigo.add(new OpBin(term, temp));
            }else{
                //printError(tipo, "-", assigNode.fila, assigNode.columna);
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

                    //String ret = verifyFuncCall((FuncCallNode)val.content);
                    //if(ret.equals("INT")){
                    //    checkLeft = true;
                    //}else{
                    //    checkLeft = false;
                    //    if(!ret.equals("ERROR"))
                    //        //printErrorId(ret, val.fila, val.columna);
                    //}
                }else if(val.type == 5){
                    //FALSE
                    //printError("INT", "CHAR", val.fila, val.columna);
                    //return false;
                }else{
                    //FALSE
                    //printErrorInesperado(val.fila, val.columna);
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
                //genFuncCall((FuncCallNode)sumNode.leftChild);
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
                    
                    //String ret = verifyFuncCall((FuncCallNode)val.content);
                    //if(ret.equals("INT")){
                    //    checkRight = true;
                    //}else{
                    //    checkRight = false;
                    //    if(!ret.equals("ERROR"))
                    //        //printErrorId(ret, val.fila, val.columna);
                    //}
                }else if(val.type == 5){
                    //printError("INT", "CHAR", val.fila, val.columna);
                    //return false;
                }else{
                    //printErrorInesperado(val.fila, val.columna);
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

                //genFuncCall();
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

                    //String ret = verifyFuncCall((FuncCallNode)val.content);
                    //if(ret.equals("INT")){
                    //    checkLeft = true;
                    //}else{
                    //    checkLeft = false;
                    //    if(!ret.equals("ERROR"))
                    //        //printErrorId(ret, val.fila, val.columna);
                    //}
                }else if(val.type == 5){
                    //printError("INT", "CHAR", val.fila, val.columna);
                    //return false;
                }else{
                    //printErrorInesperado(val.fila, val.columna);
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

                //genFuncCall()
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

                    //String ret = verifyFuncCall((FuncCallNode)val.content);
                    //if(ret.equals("INT")){
                    //    checkRight = true;
                    //}else{
                    //    checkRight = false;
                    //    if(!ret.equals("ERROR"))
                    //        //printErrorId(ret, val.fila, val.columna);
                    //}
                }else if(val.type == 5){
                    //printError("INT", "CHAR", val.fila, val.columna);
                    //return false;
                }else{
                    //printErrorInesperado(val.fila, val.columna);
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

                //String ret = verifyFuncCall((FuncCallNode)multNode.rightChild);
                //if(ret.equals("INT")){
                //    checkRight = true;
                //}else{
                //    checkRight = false; 
                //    if(!ret.equals("ERROR"))
                //        //printError("INT", ret, ((FuncCallNode)multNode.rightChild).fila, ((FuncCallNode)multNode.rightChild).columna);
                //}
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

                //String tipo = verifyFuncCall((FuncCallNode)val.content);
                //if(tipo.equals("ERROR")){
                //    return false;
                //}else{
                //    leftType = tipo;
                //}
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

            //String tipo = verifyFuncCall((FuncCallNode)boolNode.leftChild);
            //if(tipo.equals("ERROR")){
            //    return false;
            //}else{
            //    leftType = tipo;
            //}
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

                //String tipo = verifyFuncCall((FuncCallNode)val.content);
                //if(tipo.equals("ERROR")){
                //    return false;
                //}else{
                //    rightType = tipo;
                //}                    
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

            //String tipo = verifyFuncCall((FuncCallNode)boolNode.rightChild);
            //if(tipo.equals("ERROR")){
            //    return false;
            //}else{
            //    rightType = tipo;
            //}
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
                //printError("BOOLEAN","INT",boolNode.fila, boolNode.columna);
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

                //String tipo = verifyFuncCall((FuncCallNode)val.content);
                //if(tipo.equals("ERROR")){
                //    checkLeft = false;
                //}else if(tipo.equals("BOOLEAN")){
                //    checkLeft = true;
                //}else{
                //    //printError("BOOLEAN", tipo, boolNode.fila, boolNode.columna);
                //    checkLeft = false;
                //}   
            } else if(val.type == 5){
                //printError("BOOLEAN", "CHAR", boolNode.fila, boolNode.columna);
                //checkLeft = false;
            }else{
                //printError("BOOLEAN", "-", boolNode.fila, boolNode.columna);
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

            //String tipo = verifyFuncCall((FuncCallNode)boolNode.leftChild);
            //    if(tipo.equals("ERROR")){
            //        checkLeft = false;
            //    }else if(tipo.equals("BOOLEAN")){
            //        checkLeft = true;
            //    }else{
            //        //printError("BOOLEAN", tipo, boolNode.fila, boolNode.columna);
            //        checkLeft = false;
            //    }
        }else{
            //printError("BOOLEAN", "-", boolNode.fila, boolNode.columna);
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
                //printError("BOOLEAN", "INT", boolNode.fila, boolNode.columna);
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

                //String tipo = verifyFuncCall((FuncCallNode)val.content);
                //if(tipo.equals("ERROR")){
                //    checkRight = false;
                //}else if(tipo.equals("BOOLEAN")){
                //    checkRight = true;
                //}else{
                //    //printError("BOOLEAN", tipo, boolNode.fila, boolNode.columna);
                //    checkRight = false;
                //}
            } else if(val.type == 5){
                //printError("BOOLEAN", "CHAR", boolNode.fila, boolNode.columna);
                //checkRight = false;
            }else{
                //printError("BOOLEAN", "-", boolNode.fila, boolNode.columna);
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

            //String tipo = verifyFuncCall((FuncCallNode)boolNode.rightChild);
            //    if(tipo.equals("ERROR")){
            //        checkRight = false;
            //    }else if(tipo.equals("BOOLEAN")){
            //        checkRight = true;
            //    }else{
            //        //printError("BOOLEAN", tipo, boolNode.fila, boolNode.columna);
            //        checkRight = false;
            //    }
        }else{
            //printError("BOOLEAN", "-", boolNode.fila, boolNode.columna);
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
                //printError("BOOLEAN", "INT", boolNode.fila, boolNode.columna);
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

                //String tipo = verifyFuncCall((FuncCallNode)val.content);
                //if(tipo.equals("ERROR")){
                //    checkLeft = false;
                //}else if(tipo.equals("BOOLEAN")){
                //    checkLeft = true;
                //}else{
                //    //printError("BOOLEAN", tipo, boolNode.fila, boolNode.columna);
                //    checkLeft = false;
                //}                
            } else if(val.type == 5){
                //printError("BOOLEAN", "CHAR", boolNode.fila, boolNode.columna);
                //return false;
            }else{
                //printError("BOOLEAN", "-", boolNode.fila, boolNode.columna);
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
            
            //String tipo = verifyFuncCall((FuncCallNode)boolNode.leftChild);
            //    if(tipo.equals("ERROR")){
            //        checkLeft = false;
            //    }else if(tipo.equals("BOOLEAN")){
            //        checkLeft = true;
            //    }else{
            //        //printError("BOOLEAN", tipo, boolNode.fila, boolNode.columna);
            //        checkLeft = false;
            //    }
        } else {
            //printError("BOOLEAN", "-", boolNode.fila, boolNode.columna);
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
                //printError("BOOLEAN", "INT", boolNode.fila, boolNode.columna);
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

                //String tipo = verifyFuncCall((FuncCallNode)val.content);
                //if(tipo.equals("ERROR")){
                //    checkRight = false;
                //}else if(tipo.equals("BOOLEAN")){
                //    checkRight = true;
                //}else{
                //    //printError("BOOLEAN", tipo, boolNode.fila, boolNode.columna);
                //    checkRight = false;
                //}
            } else if(val.type == 5){
                //FALSE
                //printError("BOOLEAN", "CHAR", boolNode.fila, boolNode.columna);
                //checkRight = false;
            }else{
                //FALSE
                //printError("BOOLEAN", "-", boolNode.fila, boolNode.columna);
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

            //String tipo = verifyFuncCall((FuncCallNode)boolNode.rightChild);
            //    if(tipo.equals("ERROR")){
            //        checkRight = false;
            //    }else if(tipo.equals("BOOLEAN")){
            //        checkRight = true;
            //    }else{
            //        //printError("BOOLEAN", tipo, boolNode.fila, boolNode.columna);
            //        checkRight = false;
            //    }
        } else {
            //printError("BOOLEAN", "-", boolNode.fila, boolNode.columna);
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
                            String msgString = "__msg" + contMsg;
                            contMsg++;
                            fin += msgString + ":\t.asciiz \"" + content + "\"\n";
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

            fin += "\n\t.text\n\t.globl main\nmain:\n\tmove $fp, $sp\n";

            //relaciona temporales
            Map<String, String> relTemp = new HashMap<>();
            Map<String, Integer> stackPos = new HashMap<>();
            boolean[] activeTemp = new boolean[9];

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
                                term = "_" + term;      
                                opp = "lw";
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
                                assig = "-" + stackTemp + "($sp)";

                                fin += "\t" + opp + " $t9, " + term + "\n"
                                    +  "\tsw $t9 ," + assig + "\n";

                            }

                            //termino de la derecha tiene que ser una variable o un numero/caractér a fuerza

                        }else{
                            //es una variable
                            assig = "_" + assig;
                            if(term.length() > 1 && term.charAt(1) == '.'){
                                
                                if(relTemp.containsKey(term)){
                                    //está en un temporal
                                    String temp = relTemp.get(term);
                                    relTemp.remove(term);
                                    int tempNum = temp.charAt(temp.length() - 1) - 48;
                                    activeTemp[tempNum] = false;
                                    
                                    fin += "\tsw " + temp + ", " + assig + "\n";
    
                                }else if(stackPos.containsKey(term)){
                                    //está en la pila
                                    
                                    int stackTemp = stackPos.get(term);
                                    stackPos.remove(term);
                                    term = "-" + stackTemp + "($sp)";
    
                                    fin += "\tlw $t9, " + term + "\n"
                                        +  "\tsw $t9 ," + assig + "\n";
    
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

                        first = "-" + stackTemp + "($sp)";

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
                            stackPos.put("$s0", tempS);
                            
                            int stackTemp = stackPos.get(term);
                            stackPos.remove(term);

                            second = "-" + stackTemp + "($sp)";
                            term = "-" + tempS + "($sp)";

                            //Se almacena el valor del registro $s0 en un nuevo espacio de pila
                            //term tiene almacenado
    
                            fin +=  "\tsw $s0, " + term + "\n"
                                +   "\tlw $s0, " + second + "\n";
                            second = "$s0";
                                
                        }else{
                            //...no existe??
                        }

                    }else if(term.charAt(0) == '\'' || (term.charAt(0) - 48 >= 0 && term.charAt(0) - 48 <= 9)){
                        //el segundo termino es un caracter o entero
                        int tempS = getNewStackPos(stackPos);
                        stackPos.put("$s0", tempS);

                        
                        fin +=  "\tsw $s0, " + "-" + tempS + "($sp)\n"
                            +   "\tli $s0, " + term + "\n";
                        second = "$s0";

                        term = "-" + tempS + "($sp)";
                        //guarda el estado de s0 en la pila para recuperarlo despues de usar s0 para almacenar el segundo termino

                    }else{
                        //es una variable
                        
                        int tempS = getNewStackPos(stackPos);
                        stackPos.put("$s0", tempS);

                        
                        fin +=  "\tsw $s0, " + "-" + tempS + "($sp)\n"
                            +   "\tlw $s0, _" + term + "\n";
                        second = "$s0";

                        term = "-" + tempS + "($sp)";
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
                            stackPos.put("$s1", tempS);
                            
                            int stackTemp = stackPos.get(term2);
                            stackPos.remove(term2);

                            third = "-" + stackTemp + "($sp)";
                            term2 = "-" + tempS + "($sp)";

                            //Se almacena el valor del registro $s1 en un nuevo espacio de pila
                            //term2 tiene almacenado
    
                            fin +=  "\tsw $s1, " + term2 + "\n"
                                +   "\tlw $s1, " + third + "\n";
                            third = "$s1";
                                
                        }else{
                            //...no existe??
                        }

                    }else if(term2.charAt(0) == '\'' || (term2.charAt(0) - 48 >= 0 && term2.charAt(0) - 48 <= 9)){
                        //el segundo termino es un caracter o entero
                        
                        third = term2;

                    }else{
                        //es una variable
                        
                        int tempS = getNewStackPos(stackPos);
                        stackPos.put("$s1", tempS);

                        
                        fin +=  "\tsw $s1, " + "-" + tempS + "($sp)\n"
                            +   "\tlw $s1, _" + term2 + "\n";
                        third = "$s1";

                        term2 = "-" + tempS + "($sp)";
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

                    if(second.equals("$s0")){
                        fin +=  "\tlw $s0, " + term + "\n";
                        stackPos.remove("$s0");
                    }

                    if(third.equals("$s1")){
                        fin +=  "\tlw $s1, " + term2 + "\n";
                        stackPos.remove("$s1");
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
                            stackPos.put("$s0", tempS);
                            
                            int stackTemp = stackPos.get(term);
                            stackPos.remove(term);

                            second = "-" + stackTemp + "($sp)";
                            term = "-" + tempS + "($sp)";

                            //Se almacena el valor del registro $s0 en un nuevo espacio de pila
                            //term tiene almacenado
    
                            fin +=  "\tsw $s0, " + term + "\n"
                                +   "\tlw $s0, " + second + "\n";
                            second = "$s0";
                                
                        }else{
                            //...no existe??
                        }

                    }else if(term.charAt(0) == '\'' || (term.charAt(0) - 48 >= 0 && term.charAt(0) - 48 <= 9)){
                        //el segundo termino es un caracter o entero
                        int tempS = getNewStackPos(stackPos);
                        stackPos.put("$s0", tempS);

                        
                        fin +=  "\tsw $s0, " + "-" + tempS + "($sp)\n"
                            +   "\tli $s0, " + term + "\n";
                        second = "$s0";

                        term = "-" + tempS + "($sp)";
                        //guarda el estado de s0 en la pila para recuperarlo despues de usar s0 para almacenar el segundo termino

                    }else{
                        //es una variable
                        
                        int tempS = getNewStackPos(stackPos);
                        stackPos.put("$s0", tempS);

                        
                        fin +=  "\tsw $s0, " + "-" + tempS + "($sp)\n"
                            +   "\tlw $s0, _" + term + "\n";
                        second = "$s0";

                        term = "-" + tempS + "($sp)";
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
                            stackPos.put("$s1", tempS);
                            
                            int stackTemp = stackPos.get(term2);
                            stackPos.remove(term2);

                            third = "-" + stackTemp + "($sp)";
                            term2 = "-" + tempS + "($sp)";

                            //Se almacena el valor del registro $s1 en un nuevo espacio de pila
                            //term2 tiene almacenado
    
                            fin +=  "\tsw $s1, " + term2 + "\n"
                                +   "\tlw $s1, " + third + "\n";
                            third = "$s1";
                                
                        }else{
                            //...no existe??
                        }

                    }else if(term2.charAt(0) == '\'' || (term2.charAt(0) - 48 >= 0 && term2.charAt(0) - 48 <= 9)){
                        //el segundo termino es un caracter o entero
                        
                        third = term2;

                    }else{
                        //es una variable
                        
                        int tempS = getNewStackPos(stackPos);
                        stackPos.put("$s1", tempS);

                        
                        fin +=  "\tsw $s1, " + "-" + tempS + "($sp)\n"
                            +   "\tlw $s1, _" + term2 + "\n";
                        third = "$s1";

                        term2 = "-" + tempS + "($sp)";
                        //guarda el estado de s0 en la pila para recuperarlo despues de usar s0 para almacenar el segundo termino

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

                    if(second.equals("$s0")){
                        fin +=  "\tlw $s0, " + term + "\n";
                        stackPos.remove("$s0");
                    }

                    if(third.equals("$s1")){
                        fin +=  "\tlw $s1, " + term2 + "\n";
                        stackPos.remove("$s1");
                    }

                }else if(ins instanceof EtiqInt){
                    fin += ((EtiqInt)ins).etiq + ":\n";
                }else if(ins instanceof GotoInt){
                    fin += "\tb " + ((GotoInt)ins).etiqueta + "\n";
                }else if(ins instanceof ReadInt){
                    String var = ((ReadInt)ins).id;
                    System.out.println("READINT " + var);
                    Object[] tup = tabla.buscarTuplaDown(var, 0);
                    if(((Tupla)tup[0]).type.equals("INT")){
                        fin += "\tli $v0, 5\n";
                    }else if(((Tupla)tup[0]).type.equals("CHAR")){
                        fin += "\tli $v0, 12\n";
                    }
                    fin += "\tsyscall\n"
                        +  "\tsw $v0, _" + var + "\n";
                }else if(ins instanceof PrintInt){
                    //2 = id, 1 = string
                    String var = ((PrintInt)ins).cont;
                
                    System.out.println("READINT " + var);
                    if(((PrintInt)ins).type == 1){
                        fin += "\tli $v0, 4\n"
                        +  "\tla $a0, " + var + "\n"
                        +  "\tsyscall\n";
                    }else{
                        Object[] tup = tabla.buscarTuplaDown(var, 0);
                        if(tup != null){
                            if(((Tupla)tup[0]).type.equals("INT")){
                                fin += "\tli $v0, 1\n";
                            }else if(((Tupla)tup[0]).type.equals("CHAR")){
                                fin += "\tli $v0, 11\n";
                            }
                            fin += "\tlw $a0, _" + var + "\n"
                                +  "\tsyscall\n";
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