public class ReadInt extends Instruccion{
    String id;            //id

    ReadInt(String id){
        this.id = id;
    }

    public void print(){
        System.out.print("READ(" + id + ")");
    }

}