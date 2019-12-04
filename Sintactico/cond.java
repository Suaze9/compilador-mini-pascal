public class Cond extends Instruccion{
    String term1;           //primer termino que se comparará
    String opp;             //operador (si hay)
    String term2;           //segundo termino que se comparará
    String gotoTrue;        //caso verdadero
    String gotoFalse;       //caso falso

    Cond(String ter1, String op, String ter2, String goTru, String goFal){
        this.term1 = ter1;
        this.opp = op;
        this.term2 = ter2;
        this.gotoTrue = goTru;
        this.gotoFalse = goFal;
    }

    public void print(){
        System.out.print("IF " + term1 + " " + opp + " " + term2 + " THEN GOTO " + gotoTrue + "\nGOTO " + gotoFalse);
    }

}