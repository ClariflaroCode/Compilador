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
        : BEGIN sentencias_ejecutables END {System.out.println("Es un bloque ejecutable");}
        ;
   sentencias_ejecutables
        : sentencia_ejecutable {System.out.println("Es una sentencia ejecutablesss");}
        | sentencia_ejecutable sentencias_ejecutables {System.out.println("Es una sentencia ejecutablesss");}
        ;
   sentencia_ejecutable
        : assign ';' {System.out.println("Es una sentencia ejecutable");}
        | if ';'
        | while_repeat ';'
        | retorno ';'
        | print ';' {System.out.println("Es una sentencia ejecutable");}
        ;
	
   sentencias_declarativas
        : sentencia_declarativa {System.out.println("Es una sentencia declarativas");}
        | sentencia_declarativa sentencias_declarativas {System.out.println("Es una sentencia declarativas");}
        ;
   sentencia_declarativa
        : declaracion_var ';'  {System.out.println("Es una sentencia declarativa");}
        | funcion ';'
        | clase ';'
        | enum ';'
        ;
   clase
        : CLASS ID BEGIN cuerpo_clase END
        | CLASS ID cuerpo_clase END
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
        : PRIVATE funcion {System.out.println("estamos admitiendo metodos con auto");}
        | funcion
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
        : tipo FUNCTION ID '(' parametros_formales ')' sentencias_declarativas bloque_ejecutable
        | AUTO FUNCTION ID '(' parametros_formales ')' sentencias_declarativas bloque_ejecutable
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
        ;

   enum
        : TYPEDEF ID '=' '[' lista_valores ']'
        ;
   while_repeat
        : WHILE '(' comp ')' REPEAT bloque_ejecutable
        | WHILE '(' comp ')' REPEAT sentencia_ejecutable
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
        : expr {System.out.println("Es una expr");}
        | expr "=" expr  {System.out.println("Es una asignacion de expr");}
        ;

    expr
        : expr '+' term
            { System.out.println("Es una suma de los valores: " + $1+ " y " + $2);}
        | expr '-' term
            { System.out.println("Es una resta de los valores: " + $1+ " y " + $2);}
        | term
            { System.out.println("Es un término");}
        ;

    term
        : term '/' factor
            { System.out.println("Es una división de los valores: " +$1+ " y " + $2);}
        | term '*' factor
            { System.out.println("Es una multiplicación de los valores: " + $1+ " y " + $2);}
        | factor
            { System.out.println("Es un factor");}
        ;

    factor
        : variable
                { System.out.println("Es un factor");}
        | cte  {System.out.println("Es un cte factor");}
        | invocacion_funcion {System.out.println("Es una invocacion_funcion factor");}
        ;
    variable
        : ID  {System.out.println("Es un variable");}
        | ID '.' ID  {System.out.println("Es un atributo");}
        | ID '.' invocacion_funcion {System.out.println("Es un acceso a metodo");}

    cte
        : CTE_ENTERA {System.out.println("Detecte un entero");}
        | CTE_DOUBLE {System.out.println("Es un double");}
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