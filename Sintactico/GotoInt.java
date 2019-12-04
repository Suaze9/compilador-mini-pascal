public class GotoInt extends Instruccion{
    String etiqueta;            //etiqueta del goto

    GotoInt(String et){
        this.etiqueta = et;
    }

    public void print(){
        System.out.print("GOTO " + etiqueta);
    }

}