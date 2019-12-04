public class EtiqInt extends Instruccion{
    String etiq;            //etiqueta

    EtiqInt(String et){
        this.etiq = et;
    }

    public void print(){
        System.out.print("ETIQ " + etiq);
    }

}