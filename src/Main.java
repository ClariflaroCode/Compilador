import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String path ="C:\\Users\\Mateo\\eclipse-workspace\\pruebas\\bin\\codigo.txt";
		Lexer lexLuthor = new Lexer(path);
		int token=0;
		while (token!=-1) { //ESTO HABRIA QUE PASARLO AL PARSER CREO
			token = Lexer.yylex();
			System.out.println(token);
		};
    }
    
}
