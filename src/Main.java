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
			//String path ="src/codigo.txt";
			Lexer lexLuthor = new Lexer();
			lexLuthor.recibirPath(path);
			int token=0;
			while (token!=-1) { //ESTO HABRIA QUE PASARLO AL PARSER CREO
				token = Lexer.yylex();
				System.out.println(token);
			};
			
		} catch (Exception e) {
			System.out.print(e);
		}
    
    }
    
}
