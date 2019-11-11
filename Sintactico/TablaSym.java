
import java.util.ArrayList;

public class TablaSym{

    public ArrayList<Tupla> tuplas;
    public TablaSym papi;

    public TablaSym(){
        tuplas = new ArrayList<Tupla>();
        papi = null;
    }

    public TablaSym(TablaSym paps){
        tuplas = new ArrayList<Tupla>();
        papi = paps;
    }

    public void add(String id, String tipo, int size){
        Tupla t = new Tupla(id, tipo, size);
        tuplas.add(0, t);
    }

    public Object[] buscarTupla(String id, int prof){
        for(Tupla tup: tuplas){
            if(tup.id.equals(id)){
                Object[] obj = new Object[2];
                obj[0] = tup;
                obj[1] = prof;
                return obj;
            }
        }
        if(papi != null){
            return papi.buscarTupla( id, prof + 1 );
        }else{
            return null;
        }
    }

    public Object[] buscarTuplaFunc(String id, String type, int prof){
        for(Tupla tup: tuplas){
            if(tup.id.equals(id)){
                int index = tup.type.indexOf(" -> ");
                if(index >= 0){
                    String funcAttr = tup.type.substring(0, index);
                    if(funcAttr.equals(type)){
                        Object[] obj = new Object[2];
                        obj[0] = tup;
                        obj[1] = prof;
                        return obj;
                    }
                }
            }
        }
        if(papi != null){
            return papi.buscarTuplaFunc( id, type, prof + 1 );
        }else{
            return null;
        }
    }

    public void print(int depth){
        for(Tupla tup: tuplas){
            for(int i = 0; i < depth; i++){
                System.out.print("-- ");
            }
            tup.printTupla();
            System.out.print("\n");
        }
        if(papi != null){
            papi.print(depth + 1);
        }
    }

}