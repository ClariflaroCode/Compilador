%{
        import java.io.*;
    %}

    %token ID, UNINTEGER, DOUBLEF, STRING, CTE_ENTERA, CTE_DOUBLE
    %token WHILE, REPEAT, AUTO, TYPEDEF, FRIEND, PRIVATE, EXTENDS
    %token MAYOR_IGUAL, MENOR_IGUAL, DISTINTO, IGUALDAD, OP_ASSIGN
    %token IF, ELSE, END_IF, BEGIN, END, POUT, RET, CLASS, FUNCTION

    %%
   programa
        : ID sentencias_declarativas bloque_ejecutable {System.out.println("Es un programa");}
        | error sentencias_declarativas bloque_ejecutable {yyerror("Error: falta nombre de programa");}
        ;
    if
          : IF '(' comp ')' bloque_ejecutable END_IF
          | IF '(' comp ')' bloque_ejecutable ELSE bloque_ejecutable END_IF 
          | IF '(' comp ')' sentencia_ejecutable END_IF
          | IF '(' comp ')' sentencia_ejecutable ELSE sentencia_ejecutable END_IF
          | IF '(' comp ')' bloque_ejecutable ELSE sentencia_ejecutable END_IF
          | IF '(' comp ')' sentencia_ejecutable ELSE bloque_ejecutable END_IF
          ;

   bloque_ejecutable
        : BEGIN sentencias_ejecutables END 
        | sentencias_ejecutables END {yyerror("Error sintactico: Falta el BEGIN");}
        | BEGIN sentencias_ejecutables {yyerror("Errror sintactico: Falta el END");}
        ;
   sentencias_ejecutables
        : sentencia_ejecutable 
        | sentencia_ejecutable sentencias_ejecutables 
        ;
   sentencia_ejecutable
        : assign ';' 
        | if ';'
        | while_repeat ';'
        | print ';' 
        ;

   sentencias_declarativas
        : sentencia_declarativa 
        | sentencia_declarativa sentencias_declarativas 
        ;
   sentencia_declarativa
        : declaracion_var ';'  
        | funcion ';'
        | clase ';'
        | enum ';'
        ;
   clase
        : CLASS ID BEGIN cuerpo_clase END {System.out.println("Soy una clase");}
        | CLASS ID cuerpo_clase END {System.out.println("Soy una clase");}
        ;
   cuerpo_clase
        : sent_clase
        | sent_clase cuerpo_clase
        ;
   sent_clase
        : metodo ';'
        | atributo ';'
        | friendly ';'
        | herencia ';'
        ;
	
   metodo
        : PRIVATE tipo ID '(' parametros_formales ')' sentencias_declarativas bloque_func
        | PRIVATE AUTO  ID '(' parametros_formales ')' sentencias_declarativas bloque_retorno
        | tipo ID '(' parametros_formales ')' sentencias_declarativas bloque_func
        | AUTO ID '(' parametros_formales ')' sentencias_declarativas bloque_retorno
        ;
     bloque_func
        : bloque_retorno
        | bloque_ejecutable
        ;
     bloque_retorno
        : BEGIN sentencias_retornables END  
        ;   
     sentencias_func
        : sent_func
        | sentencias_func sent_func
        ;
     sentencias_retornables
        : sentencia_retornable
        | sentencias_func sentencia_retornable 
        ;
     sent_func
        : sentencia_ejecutable
        | sentencia_retornable
        | if_func ';'
        | while_repeat_func ';'
        ;
     sentencia_retornable
        : retorno ';' {System.out.println("Soy una sent retornable");}
        | if_retorno_seguro';' {System.out.println("Soy una sent retornable ");}
        ;
     if_retorno_seguro
        : IF '(' comp ')' bloque_retorno ELSE bloque_retorno END_IF {System.out.println("Soy un if retornable");}
        | IF '(' comp ')' retorno ELSE retorno END_IF {System.out.println("Soy un if retornable");}
        | IF '(' comp ')' bloque_retorno ELSE retorno END_IF {System.out.println("Soy un if retornable");}
        | IF '(' comp ')' retorno ELSE bloque_retorno END_IF {System.out.println("Soy un if retornable");}

        ;
     if_func
        : IF '(' comp ')' bloque_func END_IF {System.out.println("Es un IF");}
        | IF '(' comp ')' sent_func END_IF {System.out.println("Es un IF");}
        ;
     while_repeat_func
        : WHILE '(' comp ')' REPEAT bloque_func {System.out.println("Es un WHILE");}
        | WHILE '(' comp ')' REPEAT sent_func {System.out.println("Es un WHILE");}
        ;
    atributo
        : declaracion_var
        | enum
        | PRIVATE enum
        | PRIVATE declaracion_var {System.out.println("estamos permitiendo declaracion multiple de atributos");}
        ;
	
   friendly
        : FRIEND ID
        ;
   herencia
        : EXTENDS lista_de_variables
        | EXTENDS error {yyerror("Error sintactico: ausencia de nombre o lista de clases");}
        ;

   declaracion_var
        : tipo lista_de_variables
        | tipo ID
        ;
   
   lista_de_variables
        : ID
        | ID ',' lista_de_variables
        ;
   lista_valores
        : cte
        | cte ',' lista_valores
        ;
	
   tipo	
        : UNINTEGER
        | DOUBLEF
        | ID
        ;
   funcion 
        : tipo FUNCTION ID '(' parametros_formales ')' sentencias_declarativas bloque_ejecutable {System.out.println("Soy una funcion");}
        | AUTO FUNCTION ID '(' parametros_formales ')' sentencias_declarativas bloque_retorno {System.out.println("Soy una funcion auto");}
        ;
   parametros_formales
        : tipo ID
        | tipo ID ',' parametros_formales
        ;
   invocacion_funcion
        : ID '(' parametros_reales ')'
        ;
   parametros_reales
        : expr_asig
        | expr_asig ',' parametros_reales
        ;
   retorno
        : RET '(' expr ')'
        ;
   cadena
        : STRING {System.out.println("Es una cadena");}
        ;
   print
        : POUT '(' expr ')'
        | POUT '(' cadena ')' {System.out.println("es una cadena que se imprime");}
        | POUT '(' error ')' {yyerror("Error: falta argumento en sentencia POUT.");}
        ;

   enum
        : TYPEDEF ID '=' '[' lista_valores ']'
        ;
   while_repeat
        : WHILE '(' comp ')' REPEAT bloque_ejecutable {System.out.println("Es un WHILE");}
        | WHILE '(' comp ')' REPEAT sentencia_ejecutable {System.out.println("Es un WHILE");}
        | WHILE error comp ')' REPEAT bloque_ejecutable {yyerror("Error, falta parentesis.");}
        | WHILE '('comp error REPEAT bloque_ejecutable {yyerror("Error, falta parentesis.");}
        | WHILE comp REPEAT bloque_ejecutable {yyerror("Error, faltan parentesis.");}
        | WHILE '('comp ')' bloque_ejecutable {yyerror("Error, falta REPEAT");}
        | '(' comp ')' REPEAT bloque_ejecutable {yyerror("Error, falta WHILE");}
        | WHILE '(' ')' REPEAT bloque_ejecutable {yyerror("Error, falta condicion del WHILE");}
        | WHILE '(' comp ')' REPEAT {yyerror("Error, falta cuerpo del while");}
        ;
   comp
        : expr_asig '<' expr_asig {System.out.println("Es una comparacion <");}
        | expr_asig '>' expr_asig {System.out.println("Es una comparacion >");}
        | expr_asig MAYOR_IGUAL expr_asig {System.out.println("Es una comparacion >=");}
        | expr_asig MENOR_IGUAL expr_asig {System.out.println("Es una comparacion <=");}
        | expr_asig DISTINTO expr_asig {System.out.println("Es una desigualdad");}
        | expr_asig IGUALDAD expr_asig {System.out.println("Es una igualdad");}
        ;


    assign 
        : ID OP_ASSIGN expr_asig 
	    {System.out.println("Es una asignación con := ");}
        |  ID '=' expr
        {System.out.println("Es una asignación con = ");}
        ;

    expr_asig
        : expr 
        | expr "=" expr  {System.out.println("Es una asignacion de expr");}
        ;

    expr
        : expr '+' term
            { System.out.println("Es una suma de los valores: " + $1+ " y " + $2);}
        | expr '-' term
            { System.out.println("Es una resta de los valores: " + $1+ " y " + $2);}
        | expr term {yyerror("Error sintactico: Falta operador en expresión");}
        | expr '+' error { yyerror("Error sintactico: Falta operando en expr");}
        | expr '-' error { yyerror("Error sintactico: Falta operando en expr");}
        | term
            
        ;

    term
        : term '/' factor
            { System.out.println("Es una división de los valores: " +$1+ " y " + $2);}
        | term '*' factor
            { System.out.println("Es una multiplicación de los valores: " + $1+ " y " + $2);}
        | factor
        ;

    factor
        : variable             
        | cte
        | invocacion_funcion
        ;
    variable
        : ID  
        | ID '.' ID  {System.out.println("Es un atributo");}
        | ID '.' invocacion_funcion {System.out.println("Es un acceso a metodo");}
        ;

    cte
        : CTE_ENTERA 
        | '-'CTE_DOUBLE  {
                              System.out.println("Es una constante negativa");
                              
                         } 
        ;
%%

static Lexer lex = null;

static Parser par = null;

public static void main (String [] args) {

System.out.println("Iniciando compilación...");

lex = new Lexer (args[0]);

par = new Parser (false);

par.run();

System.out.println("Fin compilación");
}

int yylex () {
        Token megaToken= lex.getToken();
        yylval = new ParserVal(megaToken.lexema);
        return megaToken.token;
}

void yyerror (String s){
System.out.println(s);
}
