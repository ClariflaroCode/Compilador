import java.io.BufferedReader;
import java.io.InputStreamReader;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
		System.out.println("Ingrese el path de donde se encuentra el archivo que quiera compilar");
		Token t = new Token();
		t.setToken(10);
		t.setLexema("hola");
		try {
			BufferedReader entrada =  new BufferedReader(new InputStreamReader(System.in));
			String path = String.valueOf(entrada.readLine());
			//String path ="src/codigo.txt";
			Lexer lexLuthor = new Lexer(path);
			lexLuthor.recibirPath(path);
			/* 
			int token=0;
			while (token!=-1) { //ESTO HABRIA QUE PASARLO AL PARSER CREO
				token = Lexer.getToken().token;
			
				System.out.println(token);
				System.out.println("-------------------------------");
				t.setToken(t.getToken()-1);
				token = t.getToken();

			}*/
			
			
			Parser p  = new Parser();
			int e = p.yyparse();
			System.out.println("imprimi esto " + e);
			
			
		} catch (Exception e) {
			System.out.print(e);
		}
    
    }
    
}
