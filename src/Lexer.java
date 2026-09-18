//package pruebas;
/*Asumimos que el estado final es el ESTADO_FINAL
 Una celda invalida esta representada con un TRANSICION_INVALIDA
*/
//import Parser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

interface AccionSemantica {
	String aplicarAccion(String lexema,char entrada);	
	//si necesitamos 3, hacemos una lista de objetos
}

public class Lexer {
    private static final int ESTADO_FINAL = 17;
    private static final int TRANSICION_INVALIDA = -1;
    private static int indexFile = 0;
    private static int line = 0;
    private static int tokenOutput;

    private static final int[][] matriz_transiciones = {
            {13, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, 13, 15, 15, 15},
            {14, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 16, 0},
            {9, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, 6, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {11, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, 13, TRANSICION_INVALIDA, 15, 15},
            {12, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {11, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {9, 3, 3, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {9, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {TRANSICION_INVALIDA, 2, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, 7, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {4, 4, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {0, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {0, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, TRANSICION_INVALIDA, 15, 15},
            {TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, 9, 10, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {9, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, 9, 10, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {10, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, 10, 10, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {1, 1, ESTADO_FINAL, TRANSICION_INVALIDA, 5, 5, 8, 8, 8, 9, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
    };
    private static Map<String, Integer> tokens;
    private static Map<String, Integer> filaSimbolos;
    private static Map<String, AccionSemantica> accionesSemanticas; 
   // private static Map<String, Integer> tablaSimbolos;
    private static String sourceCode;


    public Lexer(String path) {
            this.recibirPath(path);
        
			//String path ="src/codigo.txt";
			
			//lexLuthor.recibirPath(path);
        tokens = Map.ofEntries(
                Map.entry("if", 257),
                Map.entry("else", 258),
                Map.entry("end_if", 259),
                Map.entry("begin", 260),
                Map.entry("end", 261),
                Map.entry("pout", 262),
                Map.entry("rec", 263),
                Map.entry("class", 264),
                Map.entry("function", 265),
                Map.entry("id", 266),
                Map.entry("cte_entera", 267),
                Map.entry("cte_double", 268),
                Map.entry(">=", 269),
                Map.entry("<=", 270),
                Map.entry("!=", 271),
                Map.entry("==", 272),
                Map.entry(":=", 273),
                Map.entry("string", 274),
                Map.entry("while", 275),
                Map.entry("repeat", 276),
                Map.entry("auto", 277),
                Map.entry("typedef", 278),
                Map.entry("friend", 279),
                Map.entry("private", 280),
                Map.entry("extends", 281),
                Map.entry("uninteger", 282),
                Map.entry("doublef", 283)
        );
        construyeFilaSimbolos();
    //    tablaSimbolos = Map.ofEntries(null);
        construyeAccionesSemanticas();

    }

    public void recibirPath(String path){ 
        try {
    		sourceCode = Files.readString(Path.of(path));
    	} catch (Exception e) {
    		System.out.println("No valid path for source code");
    	}
    }
    public void construyeAccionesSemanticas(){
    	//por ahora faltan todos los warnings
    			//cambiar los tokens directamente por el numero
    			//hay algunas que ahora ya puedo sacar o cambiar cosas, hice las primeras 10 y despues meti los tokens aca y se me rompio todo xd
    			AccionSemantica as1 = (lexema,entrada)->{return ""+entrada;};
    			AccionSemantica as2 = (lexema,entrada)->{return lexema+entrada;};
    			AccionSemantica as3 = (lexema,entrada)->{return "0"+entrada;};
    			AccionSemantica as4 = (lexema,entrada)->{indexFile--;
    			return lexema;};
    			AccionSemantica as5 = (lexema,entrada)->{indexFile--;
    			return lexema+"d+1";};
    			AccionSemantica as6 = (lexema,entrada)->{return lexema+"+"+entrada;};
    			AccionSemantica as7 = (lexema,entrada)->{if (entrada=='=') {
    				tokenOutput = tokens.get(lexema+entrada);
    				return lexema+entrada;
    			}else {
    				indexFile--;
    				tokenOutput = lexema.charAt(0);//si pongo el numero en vez de string se puede poner aca y listo
    				return lexema;
    			}
    			};
    			AccionSemantica as8 = (lexema,entrada)->{
    				tokenOutput = tokens.get("string");
    				return "";};
    			AccionSemantica as9 = (lexema,entrada)->{if (entrada=='\n')
    				line ++;
    			return "";};
    			AccionSemantica as10=(lexema,entrada)->{return "";};
    			AccionSemantica as11=(lexema,entrada)->{
    				tokenOutput = tokens.get("cte_entera");
                    //yylval = new ParserVal();
    				return as1.aplicarAccion(lexema, entrada);};
    			AccionSemantica as12=(lexema,entrada)->{
    				tokenOutput=tokens.get("cte_double");
    				return as3.aplicarAccion(lexema, entrada);
    			};
    			AccionSemantica as13=(lexema,entrada)->{
    				tokenOutput=tokens.get("cte_double");
    				return as4.aplicarAccion(lexema, entrada);
    			};
                AccionSemantica as14=(lexema,entrada)->{
                    lexema = lexema+entrada;
                    indexFile--;
                    //Parser.yylval = new ParserVal(lexema);
                    tokenOutput = tokens.get("cte_entera");
                    return lexema;
                };

                AccionSemantica as15=(lexema,entrada)->{
                    //Parser.yylval = new ParserVal(lexema);
                    tokenOutput = tokens.get("string");
                    return lexema;
                };

    			AccionSemantica as17=(lexema,entrada)->{
    				Integer esteToken = tokens.get(lexema.toLowerCase());
    				if (esteToken!=null)
    					tokenOutput=esteToken;
    				else {
    					//Agregar a tabla de simbolos y
    					tokenOutput = tokens.get("id");
    				}
    				return as4.aplicarAccion(lexema, entrada);
    			};
    			AccionSemantica as18=(lexema,entrada)->{
    				Integer esteToken = tokens.get(lexema.toLowerCase());
    				if (esteToken!=null)
    					tokenOutput=esteToken;
    				else {
    					tokenOutput = 300;//ERROR pero con un -1 se corta el parser //mayuscula y no es palabra reservada
    				}
    				return as4.aplicarAccion(lexema, entrada);
    			};
    			AccionSemantica as19=(lexema,entrada)->{
    				tokenOutput=tokens.get(lexema+entrada);
    				return lexema+entrada;
    			};
    			AccionSemantica as20=(lexema,entrada)->{
    				tokenOutput = entrada;
    				return ""+entrada;
    			};
    			accionesSemanticas = new HashMap<String,AccionSemantica>();
    			accionesSemanticas.put("e0e1", as11);
    			accionesSemanticas.put("e1e1", as2);
    			accionesSemanticas.put("e1e2", as2);
    			accionesSemanticas.put("e2e3",as2);
    			accionesSemanticas.put("e3e17", as14);
    			//accionesSemanticas.put("e3e17",as2);
    			accionesSemanticas.put("e0e4",as12);
    			accionesSemanticas.put("e1e4",as13);//4
    			accionesSemanticas.put("e4e5", as2);
    			accionesSemanticas.put("e5e5", as2);
    			accionesSemanticas.put("e5e17", as5);
    			accionesSemanticas.put("e5e6",as2);
    			accionesSemanticas.put("e6e7", as2);
    			accionesSemanticas.put("e6e8", as6);
    			accionesSemanticas.put("e7e8", as2);
    			accionesSemanticas.put("e8e8", as2);
    			accionesSemanticas.put("e8e17", as4);
    			accionesSemanticas.put("e0e9", as1);
    			accionesSemanticas.put("e0e10",as1);
    			accionesSemanticas.put("e9e9",as2);
    			accionesSemanticas.put("e10e10", as2);
    			accionesSemanticas.put("e9e10", as2);
    			accionesSemanticas.put("e9e17",as17);
    			accionesSemanticas.put("e10e17", as18);
    			accionesSemanticas.put("e0e11",as1);
    			accionesSemanticas.put("e11e17", as7);
    			accionesSemanticas.put("e0e12",as1);
    			accionesSemanticas.put("e12e17",as19);
    			accionesSemanticas.put("e0e13", as8);
    			accionesSemanticas.put("e13e13",as2);
    			accionesSemanticas.put("e0e17", as20);
    			accionesSemanticas.put("e0e0",as9);
    			accionesSemanticas.put("e0e14",as10);
    			accionesSemanticas.put("e14e15", as10);
    			accionesSemanticas.put("e15e15",as9);
    			accionesSemanticas.put("e15e16",as10);
    			accionesSemanticas.put("e16e0", as10);
    			
    }
    
    
    public void construyeFilaSimbolos() {
        filaSimbolos = new HashMap<String,Integer>();
        filaSimbolos.put("'",0);
        filaSimbolos.put("{",1);
        filaSimbolos.put("}",2);

        filaSimbolos.put("=",4);

        filaSimbolos.put(":",5);
        filaSimbolos.put("!",5);

        filaSimbolos.put("<",6);
        filaSimbolos.put(">",6);


        filaSimbolos.put("$",9);

        filaSimbolos.put("+",10);
        filaSimbolos.put("-",10);

        filaSimbolos.put("*",11);
        filaSimbolos.put("/",11);
        filaSimbolos.put(",",11);
        filaSimbolos.put(";",11);
        filaSimbolos.put("(",11);
        filaSimbolos.put(")",11);

        filaSimbolos.put(".",12);

        filaSimbolos.put(" ",13);
        int asciiTab = 9;
        filaSimbolos.put(String.valueOf((char)asciiTab),13);



        filaSimbolos.put("_", 15);


        int ascii = 'a';
        while (ascii <= 'z') { //registramos todas las minusculas
            filaSimbolos.put(String.valueOf((char)ascii), 16 );
            ascii++;
        }
        ascii = 'A';
        while (ascii <= 'Z') { //registramos todas las minusculas
            filaSimbolos.put(String.valueOf((char)ascii), 17 );
            ascii++;
        }
        ascii = '0';
        while (ascii <= '9') {
            filaSimbolos.put(String.valueOf((char)ascii), 18 );
            ascii++;
        }
        filaSimbolos.put("d",3);
        filaSimbolos.put("u",7);
        filaSimbolos.put("i",8);
        int asciiSaltoLinea = 10;
        filaSimbolos.put(String.valueOf((char)asciiSaltoLinea),14);

    }
    public int devuelveTokenLexema(String lexema) {
        //discriminar entre palabras reservadas e identificadores

        if (!tokens.containsKey(lexema.toLowerCase()) || lexema.length() > 1
                || (lexema.length() == 1 && lexema.charAt(0) >= 'a' && lexema.charAt(0) <= 'z' )){
            return tokens.get("id");
        } else if (tokens.containsKey(lexema)) {
            return tokens.get(lexema);
        } else {
            return lexema.charAt(0);
        }
    }

    public static Token getToken() {
        Token token = new Token();
    	tokenOutput = -1;
        String lexema = "";
        ArrayList<Integer> estadosPasados = new ArrayList<>();
        int estadoActual = 0;
        estadosPasados.add(estadoActual);

        while (estadoActual != ESTADO_FINAL && estadoActual != TRANSICION_INVALIDA && indexFile<sourceCode.length()){
        	String transicion = "e"+estadoActual;
            char simbolo = sourceCode.charAt(indexFile);
            int filaSimbolo = filaSimbolos.get(String.valueOf(simbolo));
            estadoActual = matriz_transiciones[filaSimbolo][estadoActual];
            transicion=transicion+"e"+estadoActual;
            estadosPasados.add(estadoActual);

            indexFile++;
            if (estadoActual == TRANSICION_INVALIDA) {
            //    String error = detectarError(estadosPasados.get(estadosPasados.size()-1));
             //   System.out.println(error);
                token.setToken(-1);
                return token;
            }
            //System.out.println(indexFile);
            
            lexema=accionesSemanticas.get(transicion).aplicarAccion(lexema, simbolo);
        }
        token.token = tokenOutput;
        token.lexema = lexema;
        return token;
    }
    public static String detectarError(ArrayList<Integer> estadosPasados) {
        String error = "";
        return error;
    }

}
