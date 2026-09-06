
/*Asumimos que el estado final es el ESTADO_FINAL
 Una celda invalida esta representada con un TRANSICION_INVALIDA
*/

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;



// to-do list
// algo que lea un archivo por lineas
// una funcion que traiga la siguiente linea y le de un simbolo a yylex


public class Lexer {
    private static final int ESTADO_FINAL = 17;
    private static final int TRANSICION_INVALIDA = -1;
    private static int indexFile = 0;

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

    public Lexer() {
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
                Map.entry("uninteger", 267),
                Map.entry("doublef", 268),
                Map.entry(">=", 269),
                Map.entry("<=", 270),
                Map.entry("!=", 271),
                Map.entry("==", 272),
                Map.entry(":=", 273),
                Map.entry("string", 274)
        );
        construyeFilaSimbolos();



    }
    public static void construyeFilaSimbolos() {
        filaSimbolos.put("'",0);
        filaSimbolos.put("{",1);
        filaSimbolos.put("}",2);
        filaSimbolos.put("d",3);
        filaSimbolos.put("=",4);

        filaSimbolos.put(":",5);
        filaSimbolos.put("!",5);

        filaSimbolos.put("<",6);
        filaSimbolos.put(">",6);

        filaSimbolos.put("u",7);
        filaSimbolos.put("i",8);
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

        int asciiSaltoLinea = 10;
        filaSimbolos.put(String.valueOf((char)asciiSaltoLinea),14);

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

    }
    public static int devuelveTokenLexema(String lexema) {
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

    public static int yylex(FileReader file, Map<String, Integer> tablaSimbolos ) {

        String lexema = "";
        ArrayList<Integer> estadosPasados = new ArrayList<>();
        int estadoActual = 0;
        estadosPasados.add(estadoActual);

        while (estadoActual != ESTADO_FINAL && estadoActual != TRANSICION_INVALIDA ) {

            char simbolo = file.toString().charAt(indexFile);
            lexema = lexema + simbolo;
            int filaSimbolo = filaSimbolos.get(simbolo);
            estadoActual = matriz_transiciones[filaSimbolo][estadoActual];
            estadosPasados.add(estadoActual);
            //if (accionSemanticaATomar(estadosPasados) != null) {

            //}
            indexFile++;
            if (estadoActual == TRANSICION_INVALIDA) {
                String error = detectarError(estadosPasados);
                System.out.println(error);
                return -1;
            }
            if (estadoActual == ESTADO_FINAL) {
                //ver si agregar a la tabla de simbolos
                //tablaSimbolos.put(lexema, tokens.get(lexema)); //algo asi ???

                //ver si hay que hacer alguna accion semantica


                //registrar token y guardar lexema
                int token = devuelveTokenLexema(lexema);

                return token;
            }

        }
        return -1;
    }
    public static String detectarError(ArrayList<Integer> estadosPasados) {
        String error = "";
        return error;
    }

    /*
    public static int yylex2(int estadoInicial, int filaSimbolo, String cadenaActual) {
        if (estadoInicial == ESTADO_FINAL) {
            //llamar al Parser, devolver lexema y ver tema de acciones semanticas
            //int token = Parser.yylValue();

            int token =  tokens.get()
            return token;
        } else if (estadoInicial == TRANSICION_INVALIDA) {
            System.out.print("ERROR");
            return filaSimbolo;
        } else {
            int proximoEstado = matriz_transiciones[filaSimbolo][estadoInicial];
            //al archivo lo convertis a un string, vas recorriendo avanzando en el indice,
            //tenemos que incrementar la posicion en la lectura????

            yylex(proximoEstado, pedirProximoSimboloDelCodigo());

            //dado las acciones semanticas que reconoció sé que simbolo es?
            //guardar el camino
        }


        return 0;
    }
*/

}