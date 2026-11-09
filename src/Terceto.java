public class Terceto {
    private String operador; //o podria ser un label
    private String operando1; //o posicion terceto
    private String operando2; //o posicion terceto

    private final String VACIO = "-"; 

    //
    private String variableAuxiliar;

    public Terceto(String operador) {
        this.operador = operador;
        this.operando1 = VACIO;
        this.operando2 = VACIO;
    }
    public void setOp1(String op1) {
        this.operando1 = op1;
    }
    public void setOp2(String op2) {
        this.operando2 = op2;
    }

    public String getOperador(){
        return this.operador;
    }
    public String getOp1(){
        return this.operando1;
    }
    public String getOp2(){
        return this.operando2;
    }
    
}
