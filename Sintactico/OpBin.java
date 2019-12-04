public class OpBin extends Instruccion{
    String assig;           //variable donde se asignará
    String opp;             //operador (si hay)
    String term;            //termino que se operará

    OpBin(String ass, String op, String ter){
        this.assig = ass;
        this.opp = op;
        this.term = ter;
    }

    OpBin(String ass, String ter){
        this.assig = ass;
        this.opp = "no";
        this.term = ter;
    }

    public void print(){
        if(opp.equals("no")){
            System.out.print(assig + " = " + term);
        }else{
            System.out.print(assig + " = " + opp + " " + term);
        }
    }

}