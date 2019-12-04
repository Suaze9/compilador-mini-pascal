public class PrintInt extends Instruccion{
    String cont;            //cont
    int type;               //2 = id, 1 = constchar

    PrintInt(String cont, int type){
        this.cont = cont;
        this.type = type;
    }

    public void print(){
        if(type == 2){
            System.out.print("PRINT(" + cont + ")");
        }else{
            System.out.print("PRINT(\"" + cont + "\")");
        }
    }

}