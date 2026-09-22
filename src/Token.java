package src;
public class Token {
    public int token;
    public String lexema;

    public Token(){
        this.token = -1;
        this.lexema = "";
    }

    public void setToken(int nuevoToken){
        this.token = nuevoToken;
    }


    public void setLexema (String nuevoLexema){
        this.lexema=nuevoLexema;
    } 

    public int getToken(){
        return this.token;
    }
    public String getLexema(){
        return this.lexema;
    }
}
