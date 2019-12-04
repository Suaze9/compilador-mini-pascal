
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
        boolean rec = false;
        String pref = "";
        String typeRec = "";
        if(id.indexOf(".") != -1 && prof == 0){
            pref = id.substring(0, id.indexOf("."));
            //System.out.println("pref: " + pref);
            Object[] record = buscarTupla(pref, 0);
            if (record != null){
                Tupla t = ((Tupla)record[0]);
                //System.out.println("TIPO: " + t.type);
                if(!(t.type.equals("RECORD") || t.type.equals("INT") || t.type.equals("BOOLEAN") || t.type.equals("CHAR") || t.type.equals("STRING"))){
                    typeRec = t.type;
                    id = id.substring(id.indexOf(".")+1);
                    rec = true;
                    //System.out.println("IIIID: " + id);
                }else{
                    return null;
                }
            }else{
                return null;
            }
        }
        /*
        if(rec){
            System.out.println("typeRec: " + typeRec + " ID: " + id + " pref: " + pref + " typeRec: " + typeRec);
        }
        */
        for(Tupla tup: tuplas){
            Object[] obj = new Object[2];
            obj[0] = tup;
            obj[1] = prof;
            /*
            if(rec){
                ((Tupla)tup).printTupla();
                System.out.println("typeRec: " + typeRec + " ID: " + id + " pref: " + pref + " typeRec: " + typeRec + " REC: " + rec);
                
            }
            */
            if(tup.id.equals(id)){
                if(rec){
                    //System.out.println("\nREC");
                    if(((Tupla)tup).type.indexOf(".") != -1){
                        String pref2 = ((Tupla)tup).type.substring(0, ((Tupla)tup).type.indexOf("."));
                        System.out.println("TUP: " + pref2 + "TYPEREC: " + typeRec);
                        if(typeRec.equals(pref2))
                        return obj;
                    }
                }else{
                    //System.out.println("\nNO-REC");
                    if(!((Tupla)tup).type.contains(".")){
                        return obj;
                    }
                }
            }
        }
        if(papi != null){
            if(rec){
                return papi.buscarTuplaRec( id, prof + 1 , pref, typeRec);
            }else{
                return papi.buscarTupla( id, prof + 1 );
            }
        }else{
            return null;
        }
    }

    public Object[] buscarTuplaRec(String id, int prof, String pref, String typeRec){
        boolean rec = true;
        for(Tupla tup: tuplas){
            Object[] obj = new Object[2];
            obj[0] = tup;
            obj[1] = prof;
            if(tup.id.equals(id)){
                //System.out.println("\nREC");
                if(((Tupla)tup).type.indexOf(".") != -1){
                    String pref2 = ((Tupla)tup).type.substring(0, ((Tupla)tup).type.indexOf("."));
                    //System.out.println("TUP: " + pref2 + "TYPEREC: " + typeRec);
                    if(typeRec.equals(pref2))
                    return obj;
                }
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
        System.out.println("");
        if(papi != null){
            papi.print(depth + 1);
        }else{
            System.out.println("");
        }
    }

}