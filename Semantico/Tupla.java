public class Tupla{

    public String id;
    public String type;
    public int offset;

    public Tupla(){
        this.id = "N/A";
        this.type = "N/A";
        this.offset = -1;
    }
    
    public Tupla(String id, String type, int offset){
        this.id = id;
        this.type = type;
        this.offset = offset;
    }

    public void printTupla(){
        System.out.print(this.id + "\t\t|\t\t" + this.type + "\t\t|\t\t" + this.offset);
    }
}