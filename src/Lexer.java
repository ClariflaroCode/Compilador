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
            {13, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, ESTADO_FINAL, 15, 15, 15},
            {14, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, 13, 15, 15, 15},
            {TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 16, 0},
            {9, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, ESTADO_FINAL, 6, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, 9, 10, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {11, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, 13, TRANSICION_INVALIDA, 15, 15},
            {12, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {11, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {9, 3, 3, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, 9, 10, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {9, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, 9, 10, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {TRANSICION_INVALIDA, 2, ESTADO_FINAL, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, 7, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {4, 4, ESTADO_FINAL, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {0, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {0, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, ESTADO_FINAL, TRANSICION_INVALIDA, 15, 15},
            {TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, 9, 10, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {9, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, 9, 10, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {10, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, 10, 10, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {1, 1, ESTADO_FINAL, TRANSICION_INVALIDA, 5, 5, 8, 8, 8, 9, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
            {TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, TRANSICION_INVALIDA, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, ESTADO_FINAL, TRANSICION_INVALIDA, 13, TRANSICION_INVALIDA, 15, 15},
    };
    private static Map<String, Integer> tokens;
    private static Map<String, Integer> filaSimbolos;
    private static Map<String, AccionSemantica> accionesSemanticas; 
    private static String sourceCode;
    public static Map<String, Integer> tablaSimbolos;


    public Lexer(String path) {
            this.recibirPath(path);
        
			//String path ="src/codigo.txt";
			
			//lexLuthor.recibirPath(path);
        tokens = Map.ofEntries(
                Map.entry("if", (int)Parser.IF),
                Map.entry("else", (int)Parser.ELSE),
                Map.entry("end_if", (int)Parser.END_IF),
                Map.entry("begin", (int)Parser.BEGIN),
                Map.entry("end", (int)Parser.END),
                Map.entry("pout", (int)Parser.POUT),
                Map.entry("ret", (int)Parser.RET),
                Map.entry("class", (int)Parser.CLASS),
                Map.entry("function", (int)Parser.FUNCTION),
                Map.entry("id", (int)Parser.ID),
                Map.entry("cte_entera",(int) Parser.CTE_ENTERA),
                Map.entry("cte_double", (int)Parser.CTE_DOUBLE),
                Map.entry(">=", (int)Parser.MAYOR_IGUAL),
                Map.entry("<=", (int)Parser.MENOR_IGUAL),
                Map.entry("!=", (int)Parser.DISTINTO),
                Map.entry("==", (int)Parser.IGUALDAD),
                Map.entry(":=", (int)Parser.OP_ASSIGN),
                Map.entry("string", (int)Parser.STRING),
                Map.entry("while", (int)Parser.WHILE),
                Map.entry("repeat", (int)Parser.REPEAT),
                Map.entry("auto", (int)Parser.AUTO),
                Map.entry("typedef", (int)Parser.TYPEDEF),
                Map.entry("friend", (int)Parser.FRIEND),
                Map.entry("private", (int)Parser.PRIVATE),
                Map.entry("extends", (int)Parser.EXTENDS),
                Map.entry("uninteger", (int)Parser.UNINTEGER),
                Map.entry("doublef", (int)Parser.DOUBLEF)
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
					if (tablaSimbolos.containsKey(lexema)) {
                    	tablaSimbolos.put(lexema,tokenOutput);
                    }
    				return lexema;};
    			AccionSemantica as5 = (lexema,entrada)->{indexFile--;
    				lexema = lexema+"d+1";
					return as4.aplicarAccion(lexema,entrada);
                };
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
                    //indexFile--;
                    //Parser.yylval = new ParserVal(lexema);
					if (tablaSimbolos.containsKey(lexema)) {
                    	tablaSimbolos.put(lexema,tokenOutput);
                    }
                    tokenOutput = tokens.get("cte_entera");
                    return lexema;
                };

                AccionSemantica as15=(lexema,entrada)->{
                    //Parser.yylval = new ParserVal(lexema);
                    tokenOutput = tokens.get("string");
                    if (entrada=='\n'){
                        line++;
                        System.out.println("Warning: Cadena terminada con salto de linea");
                    }
					if (tablaSimbolos.containsKey(lexema)) {
                    	tablaSimbolos.put(lexema,tokenOutput);
                    }
                    return lexema;
                };

    			AccionSemantica as17=(lexema,entrada)->{
    				Integer esteToken = tokens.get(lexema.toLowerCase());
    				if (esteToken!=null)
    					tokenOutput=esteToken;
    				else {
    					//Agregar a tabla de simbolos y
    					tokenOutput = tokens.get("id");
						if (tablaSimbolos.containsKey(lexema)) {
                    		tablaSimbolos.put(lexema,tokenOutput);
                    	}
    				}
    				return as4.aplicarAccion(lexema, entrada);
    			};
    			AccionSemantica as18=(lexema,entrada)->{
    				Integer esteToken = tokens.get(lexema.toLowerCase());
    				if (esteToken!=null)
    					tokenOutput=esteToken;
    				else {
    					tokenOutput = tokens.get("id");
    					lexema=lexema.toLowerCase();
    					System.out.println("Warning: identificador escrito en mayusculas");//ERROR //mayuscula y no es palabra reservada, rescatado pasandolo a minusculas
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
                AccionSemantica as21=(lexema,entrada)->{
                    indexFile--;
                    System.out.println("Warning: Constante entera sin sufijo");
                    lexema = lexema+"$ui";
                    if (tablaSimbolos.containsKey(lexema)) {
                    	tablaSimbolos.put(lexema,tokenOutput);
                    }
                    tokenOutput = tokens.get("cte_entera");
                    return lexema; //
                };
                AccionSemantica as22=(lexema,entrada)->{
                    lexema=lexema+"$";
                    System.out.println("Warning: falta $ en constante entera");
                    return as2.aplicarAccion(lexema,entrada);
                };
                AccionSemantica as23=(lexema,entrada)->{
                    System.out.println("Warning: falta ui en constante entera");
                    lexema=lexema+"ui";
                    return as4.aplicarAccion(lexema,entrada);
                };
                AccionSemantica as24=(lexema,entrada)->{
                    System.out.println("Warning: falta parte decimal en constante flotante");
                    lexema=lexema+"0";
                    return as5.aplicarAccion(lexema,entrada);
                };
                AccionSemantica as25 = (lexema,entrada)->{
                    System.out.println("Warning: pontencia incompleta en constante flotante");
                    lexema = lexema+"+1";
                    return as4.aplicarAccion(lexema,entrada);
                };
                AccionSemantica as26 = (lexema,entrada)->{
                    System.out.println("Warning: pontencia incompleta en constante flotante");
                    lexema = lexema +"1";
                    return as4.aplicarAccion(lexema,entrada);
                };
    			accionesSemanticas = new HashMap<String,AccionSemantica>();
    			accionesSemanticas.put("e0e1", as11);
    			accionesSemanticas.put("e1e1", as2);
    			accionesSemanticas.put("e1e2", as2);
    			accionesSemanticas.put("e1e3",as22);
    			accionesSemanticas.put("e1e17",as21);
    			accionesSemanticas.put("e2e3",as2);
    			accionesSemanticas.put("e2e17",as23);
    			accionesSemanticas.put("e3e17", as14);
    			//accionesSemanticas.put("e3e17",as2);
    			accionesSemanticas.put("e0e4",as12);
    			accionesSemanticas.put("e1e4",as13);//4
    			accionesSemanticas.put("e4e5", as2);
    			accionesSemanticas.put("e4e17",as24);
    			accionesSemanticas.put("e5e5", as2);
    			accionesSemanticas.put("e5e17", as5);
    			accionesSemanticas.put("e5e6",as2);
    			accionesSemanticas.put("e6e7", as2);
    			accionesSemanticas.put("e6e8", as6);
    			accionesSemanticas.put("e6e17",as25);
    			accionesSemanticas.put("e7e8", as2);
    			accionesSemanticas.put("e7e17",as26);
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
                accionesSemanticas.put("e13e17",as15);
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
        if (estadoActual == 9 || estadoActual == 10) { //solo nos metemos a este if si estamos en el final del archivo. 
            //habría que revisar que el lexema sea una de las palabras reservadas. 
            if (lexema.toLowerCase().equals("end")) {
                tokenOutput = tokens.get("end");
            }
        }
        token.token = tokenOutput;
        System.out.println("Imprimiendo token: " +token.token);
        token.lexema = lexema;
        System.out.println("Imprimiendo lexema: " + token.lexema);
        return token;
    }
    public static String detectarError(ArrayList<Integer> estadosPasados) {
        String error = "";
        return error;
    }

}
