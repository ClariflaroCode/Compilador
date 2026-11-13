public class Terceto {
    private String operador;
    private String op1;
    private Object op2;
    private String aux;

    public Terceto(String operador, String op1, Object op2){
        this.operador = operador;
        this.op1 = op1;
        this.op2 = op2;
    }

    public String imprimir(){
        return new String("[" + this.operador + "," + this.op1 + "," + this.op2 + "]");
    }

    // @Override
    // public String toString() {
    //     // TODO Auto-generated method stub
    //     // System.out.println;
    // }

}
