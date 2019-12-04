public class OpTer extends Instruccion{
    String assig;           //variable donde se asignará
    String term1;           //primer termino que se operará
    String opp;             //operador (si hay)
    String term2;           //segundo termino que se operará

    OpTer(String ass, String ter1, String op, String ter2){
        this.assig = ass;
        this.term1 = ter1;
        this.opp = op;
        this.term2 = ter2;
    }

    public void print(){
        System.out.print(assig + " = " + term1 + " " + opp + " " + term2);
    }

}