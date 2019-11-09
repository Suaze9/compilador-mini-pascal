
import java.util.ArrayList;

public class TablaSym{

    ArrayList<Tupla> tuplas;
    TablaSym papi;

    public void TablaSym(){
        tuplas = new ArrayList<Tupla>;
        papi = null;
    }

    public void TablaSym(TablaSym paps){
        tuplas = new ArrayList<Tupla>;
        papi = paps;
    }

    public boolean add(String id, String tipo, int size){
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
            return papi.buscarTuplaRec( id, prof + 1 );
        }else{
            return null;
        }
    }

}yList;

public class TablaSym{

    ArrayList<Tupla> tuplas;
    TablaSym papi;

    public void TablaSym(){
        tuplas = new ArrayList<Tupla>;
        papi = null;
    }

    public void TablaSym(TablaSym paps){
        tuplas = new ArrayList<Tupla>;
        papi = paps;
    }

    public boolean add(String id, String tipo, int size){
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
            return papi.buscarTuplaRec( id, prof + 1 );
        }else{
            return null;
        }
    }

}