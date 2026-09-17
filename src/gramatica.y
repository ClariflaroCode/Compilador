%{
        import java.io.*;
    %}

    %token ID, UNINTEGER, DOUBLEF, STRING, CTE_ENTERA, CTE_DOUBLE
    %token WHILE, REPEAT, AUTO, TYPEDEF, FRIEND, PRIVATE, EXTENDS
    %token MAYOR_IGUAL, MENOR_IGUAL, DISTINTO, IGUALDAD, OP_ASSIGN
    %token IF, ELSE, END_IF, BEGIN, END, POUT, RET, CLASS, FUNCTION

    %%
   if
    	: IF '(' comp ')' bloque_ejecutable END_IF
    	| IF '(' comp ')' bloque_ejecutable ELSE bloque_ejecutable END_IF 
        | IF '(' comp ')' sentencia_ejecutable END_IF
        | IF '(' comp ')' sentencia_ejecutable ELSE sentencia_ejecutable END_IF
        | IF '(' comp ')' bloque_ejecutable ELSE sentencia_ejecutable END_IF
        | IF '(' comp ')' sentencia_ejecutable ELSE bloque_ejecutable END_IF
        ;

   programa
        : ID sentencias_declarativas bloque_ejecutable
        ;

   bloque_ejecutable
        : BEGIN sentencias_ejecutables END
        ;
   sentencias_ejecutables
        : sentencia_ejecutable
        | sentencia_ejecutable sentencias_ejecutables
        ;
   sentencia_ejecutable
        : assign ';' 
        | if ';'
        | while_repeat ';'
        | retorno ';'
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
        | PRIVATE declaracion_var {System.out.println("estamos permitiendo declaracion multiple de atributos")}
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
   print
        : POUT '(' expr ')'
        | POUT '(' cadena ')'
        ;
   cadena
        : '‘' STRING '’'
        ;
   enum
        : TYPEDEF ID '=' '[' lista_valores ']'
        ;
   while_repeat
        : WHILE '(' comp ')' REPEAT bloque_ejecutable
        | WHILE '(' comp ')' REPEAT sentencia_ejecutable
        ;
   comp
        : expr_asig '<' expr_asig {System.out.println(“Es una comparacion <”)}
        | expr_asig '>' expr_asig {System.out.println(“Es una comparacion >”)}
        | expr_asig MAYOR_IGUAL expr_asig {System.out.println(“Es una comparacion >=”)}
        | expr_asig MENOR_IGUAL expr_asig {System.out.println(“Es una comparacion <=”)}
        | expr_asig DISTINTO expr_asig {System.out.println(“Es una desigualdad”)}
        | expr_asig IGUALDAD expr_asig {System.out.println(“Es una igualdad”)}
        ;


    assign 
        : ID OP_ASSIGN expr_asig 
	    {System.out.println(“Es una asignación con := ”)}
        |  ID '=' expr
        {System.out.println(“Es una asignación con = ”)}
        ;

    expr_asig
        : expr
        | expr "=" expr

    expr
        : expr '+' term
            { System.out.print(“Es una suma de los valores: “ , $1, “ y ” , $2)}
        | expr '-' term
            { System.out.print(“Es una resta de los valores: “ , $1, “ y ” , $2)}
        | term
            { System.out.println(“Es un término”)}
        ;

    term
        : term '/' factor
            { System.out.print(“Es una división de los valores: “ , $1, “ y ” , $2”)}
        | term '*' factor
            { System.out.print(“Es una multiplicación de los valores: “ , $1, “ y ” , $2”)}
        | factor
            { System.out.print(“Es un factor”)}
        ;

    factor
        : variable
                { System.out.println(“Es un id”)}
        | cte
        | invocacion_funcion
        ;
    variable
        : ID 
        | ID '.' ID
        | ID '.' invocacion_funcion

    cte
        : CTE_ENTERA
        | CTE_DOUBLE
        ;
%%

static Lex lex = null;

static Parser par = null;

public static void main (String [] args) {

System.out.println("Iniciando compilación...");

lex = new Lexer (args[0]);

par = new Parser (false);

par.run();

System.out.println("Fin compilación");
}

int yylex () {
        int token = lex.getToken();
        yylval = new ParserVal(lex.punteroTS);
        return token;
}

void yyerror (String s){
System.out.println(s);
}