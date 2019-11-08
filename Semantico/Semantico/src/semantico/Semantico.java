package semantico;

import arbol.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Semantico {

    public static void main(String[] args) {
        try(ObjectInputStream oi = new ObjectInputStream(new FileInputStream(new File("../arbolito.bebe")))){
            ProgramNode root = (ProgramNode) oi.readObject();
            System.out.println("Olo: \n\n" + root.printNode(1));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}
