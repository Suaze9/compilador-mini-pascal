import java.util.ArrayList;

import arbol.*;

public class CodInt {
    
    private ProgramNode root;
    public ArrayList<Instruccion> codigo;
    private int contEtiq;
    private int contTemp;

    CodInt(ProgramNode t){
        this.root = t;
        this.codigo = new ArrayList();
        this.contEtiq = 0;
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
        genEtiq(comienzo);
        genCondFor(fn, state, sig);
        genEtiq(state);
        statements(fn.statements, comienzo);
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
                codigo.add(new OpBin(term, temp));
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
                String temp = "" + (Character)val.content;
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
       
        String e1Verdadero = verdadero;
        String e1Falso = newEtiq();
        
        String e2Verdadero = verdadero;
        String e2Falso = falso;

        //Verifica el tipo de la izquierda del operador
        if(boolNode.typeLeft == 1){
            Value val = (Value)boolNode.leftChild;
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
        
        //propragacion de etiquetas
        String e1Verdadero = newEtiq();
        String e1Falso = falso;
        
        String e2Verdadero = verdadero;
        String e2Falso = falso;

        if(boolNode.typeLeft == 1){
            Value val = (Value)boolNode.leftChild;
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

}