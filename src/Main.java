import java.io.*;
import java.util.HashMap;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        InputStreamReader path = new InputStreamReader(System.in);

        try {
            System.out.print("Ingrese la ruta del archivo que quiera compilar");
            BufferedReader entrada = new BufferedReader(path);
            //compilateLine(entrada)
        } catch (Exception e) {
            System.out.print(e);
        }
    }
    public static void compileLine(FileReader originalCode){
        Lexer lexicalAnalizer = new Lexer();
        lexicalAnalizer.yylex(originalCode, new HashMap<>());
    }
}