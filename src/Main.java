import java.io.BufferedReader;
import java.io.InputStreamReader;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
		System.out.println("Ingrese el path de donde se encuentra el archivo que quiera compilar");
		
		try {
			BufferedReader entrada =  new BufferedReader(new InputStreamReader(System.in));
			String path = String.valueOf(entrada.readLine());
			Lexer lexLuthor = new Lexer(path);
			//System.out.println("hola"+Lexer.filaSimbolos.get(13)+"juli");
			lexLuthor.recibirPath(path);
			
			Parser p  = new Parser();
			int e = p.yyparse();
			//System.out.println("imprimi esto " + e);
			System.out.println(Lexer.line);
			
		} catch (Exception e) {
			System.out.print(e);
		}
    
    }
    
}
