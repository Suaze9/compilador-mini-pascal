import java.util.ArrayList;
import arbol.*;

public class FuncInt extends Instruccion{
    String func;                //nombre de funcion
    ArrayList<DeclNode> decl;   //declaraciones
    ArrayList<String> params;   //parámetros
    String ret;                 //valor de retorno (NULL si es procedimiento)

    FuncInt(String func, ArrayList<DeclNode> decl, ArrayList<String> params, String ret){ 
        this.func = func;
        this.decl = decl;
        this.params = params;
        this.ret = ret;
    }

    FuncInt(String func, ArrayList<DeclNode> decl, ArrayList<String> params){ 
        this.func = func;
        this.decl = decl;
        this.params = params;
        this.ret = "NULL";
    }

    public void print(){
        String prin = "";
        if(ret.equals("NULL")){
            prin += "PROCEDURE " + func + " (";
        }else{
            prin += "FUNCTION " + func + " (";
        }
        for (String par : params) {
            prin += " " + par + ",";
        }
        prin = prin.substring(0, prin.length() - 1);
        prin += " )";

        if(!ret.equals("NULL")){
            prin += ": " + ret;
        }
        System.out.print(prin);
    }
}