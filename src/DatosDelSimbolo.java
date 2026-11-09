import java.util.ArrayList;

public class DatosDelSimbolo {
    private int token;
    private String tipo; //admite el tipo de dato desconocido para poder ser descubierto desp :D
    private int uso; //para los identificadores, 
    // nombre funcion 0, nombre clase 1, nombre variable 2[], nombre programa 3, nombre parametro formal 4, nombre parametro real 5, nombre metodo 6, nombre atributo 7 ......

    private ArrayList<Parametro> parametros; 
    private ArrayList<String> clasesAmigas;

    public DatosDelSimbolo (int token, String tipo) {
        this.token = token;
        this.tipo = tipo;
        this.parametros = new ArrayList<>();
        this.clasesAmigas = new ArrayList<>();
    }

    //getters
    public int getToken () {
        return this.token;
    }
    public String getTipo () {
        return this.tipo;
    }
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
    public int getUso (){
        return this.uso;
    }
    public void setUso(int uso){
        this.uso = uso;
    }
    //añadiendo a listas. 
    public void addParametro(Parametro p){
        this.parametros.add(p);
    }
    public void addClaseAmiga(String c){
        this.clasesAmigas.add(c);
    }
    //copy
    public ArrayList<Parametro> getParametros(){
        ArrayList<Parametro> copia = new ArrayList<>(this.parametros);
        return copia;
    }
    public ArrayList<String> getClasesAmigas(){
        ArrayList<String> copia = new ArrayList<>(this.clasesAmigas);
        return copia;
    }


}
