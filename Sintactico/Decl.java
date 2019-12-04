public class Decl extends Instruccion{
    String assig;           //variable que se declarará
    String type;            //tipo de la variable

    Decl(String ass, String ty){
        this.assig = ass;
        this.type = ty;
    }

    public void print(){
        System.out.print(type + " " + assig);
    }

}