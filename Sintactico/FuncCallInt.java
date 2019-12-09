import java.util.ArrayList;

public class FuncCallInt extends Instruccion{
    String func;                //nombre de funcion
    String etiq;                //etiqueta
    ArrayList<String> params;   //parámetros
    String ret;                //variable donde retornar

    FuncCallInt(String func, String etiq, ArrayList<String> params, String ret){ 
        this.func = func;
        this.etiq = etiq;
        this.params = params;
        this.ret = ret;
    }

    public void print(){
        String prin = "";
        
        if(!ret.equals("NULL")){
            prin += ret + " = ";
        }

        prin += func + "(";

        for (String par : params) {
            prin += " " + par + ",";
        }
        prin = prin.substring(0, prin.length() - 1);
        prin += " )";

        System.out.print(prin);
    }
}