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
        int spaces = 30;
        System.out.print(this.id);
        for (int i = spaces; i > this.id.length(); i--) {
            System.out.print(" ");
        }
        System.out.print("|");
        for (int i = spaces; i > this.type.length()/2; i--) {
            System.out.print(" ");
        }
        System.out.print(this.type);
        int lengthP = this.type.length() / 2;
        if(this.type.length() % 2 == 0){
            lengthP--;
        }
        for (int i = spaces; i > lengthP; i--) {
            System.out.print(" ");
        }
        System.out.print("|");
        for (int i = spaces; i > 0; i--) {
            System.out.print(" ");
        }
        System.out.print(this.offset);
    }
}