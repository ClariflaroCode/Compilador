//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 ".\src\gramatica.y"
        import java.io.*;
    
//#line 20 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short ID=257;
public final static short UNINTEGER=258;
public final static short DOUBLEF=259;
public final static short STRING=260;
public final static short CTE_ENTERA=261;
public final static short CTE_DOUBLE=262;
public final static short WHILE=263;
public final static short REPEAT=264;
public final static short AUTO=265;
public final static short TYPEDEF=266;
public final static short FRIEND=267;
public final static short PRIVATE=268;
public final static short EXTENDS=269;
public final static short MAYOR_IGUAL=270;
public final static short MENOR_IGUAL=271;
public final static short DISTINTO=272;
public final static short IGUALDAD=273;
public final static short OP_ASSIGN=274;
public final static short IF=275;
public final static short ELSE=276;
public final static short END_IF=277;
public final static short BEGIN=278;
public final static short END=279;
public final static short POUT=280;
public final static short RET=281;
public final static short CLASS=282;
public final static short FUNCTION=283;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    3,    3,    3,    3,    3,    3,    2,    6,    6,
    5,    5,    5,    5,    5,    1,    1,   11,   11,   11,
   11,   14,   14,   16,   16,   17,   17,   17,   17,   18,
   18,   19,   19,   19,   19,   20,   21,   12,   12,   22,
   22,   24,   24,   23,   23,   23,   13,   13,   26,   26,
   27,   28,   28,    9,   10,   10,   31,   15,    8,    8,
    4,    4,    4,    4,    4,    4,    7,    7,   29,   29,
   30,   30,   30,   32,   32,   32,   33,   33,   33,   34,
   34,   34,   25,   25,
};
final static short yylen[] = {                            2,
    3,    6,    8,    6,    8,    8,    8,    3,    1,    2,
    2,    2,    2,    2,    2,    1,    2,    2,    2,    2,
    2,    5,    4,    1,    2,    2,    2,    2,    2,    2,
    1,    1,    1,    2,    2,    2,    2,    2,    2,    1,
    3,    1,    3,    1,    1,    1,    8,    8,    2,    4,
    4,    1,    3,    4,    4,    4,    3,    6,    6,    6,
    3,    3,    3,    3,    3,    3,    3,    3,    1,    3,
    3,    3,    1,    3,    3,    1,    1,    1,    1,    1,
    3,    3,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,   46,   44,   45,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    1,
   17,   18,   19,   20,   21,    0,    0,   38,    0,    0,
    0,    0,    0,    0,   32,   31,   33,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   36,
   35,   30,   34,    0,   37,    0,   23,   25,   26,   27,
   28,   29,    0,    0,    0,    0,    0,    0,   12,   10,
    8,   11,   13,   14,   15,   41,    0,    0,    0,   83,
   84,    0,    0,   22,    0,   78,   79,   67,    0,    0,
   76,   77,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   58,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   55,   56,   54,    0,    0,    0,   43,    0,    0,
    0,   82,    0,    0,    0,   74,   75,    0,   63,   64,
   65,   66,   61,   62,    0,    0,   57,    0,   50,   48,
   51,    0,   59,   60,    0,    2,    0,    4,   47,   53,
    0,    0,    0,    0,    3,    6,    7,    5,
};
final static short yydgoto[] = {                          2,
    9,   20,   49,  104,   50,   51,   52,   53,   54,   55,
   10,   11,   12,   13,   14,   38,   39,   40,   41,   42,
   43,   28,   15,   92,   96,   89,   97,  139,  105,   99,
  109,  100,  101,  102,
};
final static short yysindex[] = {                      -240,
 -151,    0,    0,    0,    0, -259, -212, -179, -190, -151,
   33,   36,   50,   53, -196, -141,   59, -117, -153,    0,
    0,    0,    0,    0,    0,   82, -127,    0,  126,   42,
 -112,  -84,  -65,  -89,    0,    0,    0,  -82,  -89,  139,
  144,  145,  146,  -56,  161,  162,  166,  167,  149, -153,
  -70,  151,  152,  153,  154,  -65,  174,  -68, -106,    0,
    0,    0,    0,   82,    0,  -64,    0,    0,    0,    0,
    0,    0, -160, -160, -160, -160, -163, -160,    0,    0,
    0,    0,    0,    0,    0,    0,  -68,  -40,  175,    0,
    0,  127,  177,    0,   30,    0,    0,    0,   93,   77,
    0,    0,  120,  178,   11,  181,  -37,  142,  183,  143,
  184,  182, -151,    0, -106, -160,  -30, -160, -160, -160,
 -160, -160,  -31, -160, -160, -160, -160, -160, -160, -146,
  -57,    0,    0,    0, -151,  -68, -190,    0,  193,  191,
  200,    0,  120,   77,   77,    0,    0, -146,    0,    0,
    0,    0,    0,    0, -105,  -83,    0, -190,    0,    0,
    0, -160,    0,    0, -146,    0, -146,    0,    0,    0,
  -32,  -27,  -26,  -25,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -24,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  187,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -22,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -21,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  188,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  155,    0,  -41,    0,    0,    0,    3,  -29,
    0,    0,  190,    0,    0,    0,    0,    0,    0,    0,
    0,  212,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  214,
  -34,    0,    7,   -7,   -2,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   12,  -62,    0,  180,  -74,  209,    0,    0,    0,    0,
    0,  105,  125,    0,  128,  119,    0,    0,    0,    0,
    0,   44,  -23,  147,  -36,  -47,  150,   98,  -44,  -28,
    0,   76,   78,    0,
};
final static int YYTABLESIZE=284;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         80,
   80,   80,   80,   80,   74,   80,   81,   81,   81,   81,
   81,   73,   81,   73,   73,   73,    1,   80,   80,   80,
   80,   21,   93,   16,   81,   81,   81,   81,   98,   73,
   73,   73,   73,   71,   88,   71,   71,   71,   72,  111,
   72,   72,   72,   69,   17,  103,   69,   70,  108,  110,
   70,   71,   71,   71,   71,  156,   72,   72,   72,   72,
   26,   69,   69,   88,   69,   70,   70,  155,   70,  116,
  128,  140,  129,  164,  160,  117,   65,   18,   93,  149,
  150,  151,  152,  153,  154,  163,   27,   19,  159,  143,
  172,   22,  174,   95,   23,  169,   95,   90,   91,   86,
   90,   91,  171,   44,  173,    3,    4,    5,   24,   45,
   44,   25,   88,    6,    7,   29,   45,  140,  122,   30,
  107,   46,   35,  121,  137,   56,   47,   48,   46,   57,
    8,   19,   59,   47,   48,  119,   61,  120,   35,    3,
    4,    5,   36,   35,   60,   37,  158,    6,    7,   31,
   32,   33,   66,  118,   90,   91,   62,   68,   36,   63,
   34,   37,  119,   36,  120,   58,   37,    3,    4,    5,
  165,  166,    3,    4,    5,    6,    7,   31,   32,   33,
    6,    7,  132,  134,  119,  119,  120,  120,    3,    4,
    5,   64,  167,  168,  144,  145,   67,   69,  146,  147,
   75,   76,   70,   71,   72,   77,   78,   79,   81,   82,
   83,   84,   85,   87,   94,  113,  112,   73,  123,  114,
  115,  130,  131,  133,  135,  136,  141,  157,   80,   80,
   80,   80,  148,  161,  162,   81,   81,   81,   81,  116,
   73,   73,   73,   73,  175,   39,   40,   42,   68,  176,
  177,  178,   49,   16,   52,  106,   24,    9,   80,  170,
    0,  138,   71,   71,   71,   71,  142,   72,   72,   72,
   72,    0,   69,   69,   69,   69,   70,   70,   70,   70,
  124,  125,  126,  127,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   61,   47,   41,   42,   43,   44,
   45,   41,   47,   43,   44,   45,  257,   59,   60,   61,
   62,   10,   59,  283,   59,   60,   61,   62,   73,   59,
   60,   61,   62,   41,   58,   43,   44,   45,   41,   87,
   43,   44,   45,   41,  257,   74,   44,   41,   77,   78,
   44,   59,   60,   61,   62,  130,   59,   60,   61,   62,
  257,   59,   60,   87,   62,   59,   60,  130,   62,   40,
   60,  116,   62,  148,  137,   46,   33,  257,  115,  124,
  125,  126,  127,  128,  129,  148,  283,  278,  136,  118,
  165,   59,  167,  257,   59,  158,  257,  261,  262,   56,
  261,  262,  165,  257,  167,  257,  258,  259,   59,  263,
  257,   59,  136,  265,  266,  257,  263,  162,   42,   61,
  284,  275,   18,   47,  113,   44,  280,  281,  275,  257,
  282,  278,   91,  280,  281,   43,   32,   45,   34,  257,
  258,  259,   18,   39,  257,   18,  135,  265,  266,  267,
  268,  269,   34,   61,  261,  262,   32,   39,   34,   32,
  278,   34,   43,   39,   45,   40,   39,  257,  258,  259,
  276,  277,  257,  258,  259,  265,  266,  267,  268,  269,
  265,  266,   41,   41,   43,   43,   45,   45,  257,  258,
  259,  257,  276,  277,  119,  120,  279,   59,  121,  122,
   40,   40,   59,   59,   59,   40,   40,   59,  279,   59,
   59,   59,   59,   40,  279,   41,  257,  274,   41,   93,
   44,   41,  260,   41,   41,   44,  257,  285,  270,  271,
  272,  273,  264,   41,   44,  270,  271,  272,  273,   40,
  270,  271,  272,  273,  277,   59,   59,   93,   59,  277,
  277,  277,   41,  278,   41,   76,  279,  279,   50,  162,
   -1,  115,  270,  271,  272,  273,  117,  270,  271,  272,
  273,   -1,  270,  271,  272,  273,  270,  271,  272,  273,
  270,  271,  272,  273,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=285;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"ID","UNINTEGER","DOUBLEF","STRING",
"CTE_ENTERA","CTE_DOUBLE","WHILE","REPEAT","AUTO","TYPEDEF","FRIEND","PRIVATE",
"EXTENDS","MAYOR_IGUAL","MENOR_IGUAL","DISTINTO","IGUALDAD","OP_ASSIGN","IF",
"ELSE","END_IF","BEGIN","END","POUT","RET","CLASS","FUNCTION",
"\"\\342\\200\\230\"","\"\\342\\200\\231\"",
};
final static String yyrule[] = {
"$accept : programa",
"programa : ID sentencias_declarativas bloque_ejecutable",
"if : IF '(' comp ')' bloque_ejecutable END_IF",
"if : IF '(' comp ')' bloque_ejecutable ELSE bloque_ejecutable END_IF",
"if : IF '(' comp ')' sentencia_ejecutable END_IF",
"if : IF '(' comp ')' sentencia_ejecutable ELSE sentencia_ejecutable END_IF",
"if : IF '(' comp ')' bloque_ejecutable ELSE sentencia_ejecutable END_IF",
"if : IF '(' comp ')' sentencia_ejecutable ELSE bloque_ejecutable END_IF",
"bloque_ejecutable : BEGIN sentencias_ejecutables END",
"sentencias_ejecutables : sentencia_ejecutable",
"sentencias_ejecutables : sentencia_ejecutable sentencias_ejecutables",
"sentencia_ejecutable : assign ';'",
"sentencia_ejecutable : if ';'",
"sentencia_ejecutable : while_repeat ';'",
"sentencia_ejecutable : retorno ';'",
"sentencia_ejecutable : print ';'",
"sentencias_declarativas : sentencia_declarativa",
"sentencias_declarativas : sentencia_declarativa sentencias_declarativas",
"sentencia_declarativa : declaracion_var ';'",
"sentencia_declarativa : funcion ';'",
"sentencia_declarativa : clase ';'",
"sentencia_declarativa : enum ';'",
"clase : CLASS ID BEGIN cuerpo_clase END",
"clase : CLASS ID cuerpo_clase END",
"cuerpo_clase : sent_clase",
"cuerpo_clase : sent_clase cuerpo_clase",
"sent_clase : metodo ';'",
"sent_clase : atributo ';'",
"sent_clase : friendly ';'",
"sent_clase : herencia ';'",
"metodo : PRIVATE funcion",
"metodo : funcion",
"atributo : declaracion_var",
"atributo : enum",
"atributo : PRIVATE enum",
"atributo : PRIVATE declaracion_var",
"friendly : FRIEND ID",
"herencia : EXTENDS lista_de_variables",
"declaracion_var : tipo lista_de_variables",
"declaracion_var : tipo ID",
"lista_de_variables : ID",
"lista_de_variables : ID ',' lista_de_variables",
"lista_valores : cte",
"lista_valores : cte ',' lista_valores",
"tipo : UNINTEGER",
"tipo : DOUBLEF",
"tipo : ID",
"funcion : tipo FUNCTION ID '(' parametros_formales ')' sentencias_declarativas bloque_ejecutable",
"funcion : AUTO FUNCTION ID '(' parametros_formales ')' sentencias_declarativas bloque_ejecutable",
"parametros_formales : tipo ID",
"parametros_formales : tipo ID ',' parametros_formales",
"invocacion_funcion : ID '(' parametros_reales ')'",
"parametros_reales : expr_asig",
"parametros_reales : expr_asig ',' parametros_reales",
"retorno : RET '(' expr ')'",
"print : POUT '(' expr ')'",
"print : POUT '(' cadena ')'",
"cadena : \"\\342\\200\\230\" STRING \"\\342\\200\\231\"",
"enum : TYPEDEF ID '=' '[' lista_valores ']'",
"while_repeat : WHILE '(' comp ')' REPEAT bloque_ejecutable",
"while_repeat : WHILE '(' comp ')' REPEAT sentencia_ejecutable",
"comp : expr_asig '<' expr_asig",
"comp : expr_asig '>' expr_asig",
"comp : expr_asig MAYOR_IGUAL expr_asig",
"comp : expr_asig MENOR_IGUAL expr_asig",
"comp : expr_asig DISTINTO expr_asig",
"comp : expr_asig IGUALDAD expr_asig",
"assign : ID OP_ASSIGN expr_asig",
"assign : ID '=' expr",
"expr_asig : expr",
"expr_asig : expr '=' expr",
"expr : expr '+' term",
"expr : expr '-' term",
"expr : term",
"term : term '/' factor",
"term : term '*' factor",
"term : factor",
"factor : variable",
"factor : cte",
"factor : invocacion_funcion",
"variable : ID",
"variable : ID '.' ID",
"variable : ID '.' invocacion_funcion",
"cte : CTE_ENTERA",
"cte : CTE_DOUBLE",
};

//#line 188 ".\src\gramatica.y"

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
        int token = lex.getToken();
        yylval = new ParserVal(lex.punteroTS);
        return token;
}

void yyerror (String s){
System.out.println(s);
}
//#line 423 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 30:
//#line 64 ".\src\gramatica.y"
{System.out.println("estamos admitiendo metodos con auto");}
break;
case 35:
//#line 71 ".\src\gramatica.y"
{System.out.println("estamos permitiendo declaracion multiple de atributos");}
break;
case 61:
//#line 133 ".\src\gramatica.y"
{System.out.println("Es una comparacion <");}
break;
case 62:
//#line 134 ".\src\gramatica.y"
{System.out.println("Es una comparacion >");}
break;
case 63:
//#line 135 ".\src\gramatica.y"
{System.out.println("Es una comparacion >=");}
break;
case 64:
//#line 136 ".\src\gramatica.y"
{System.out.println("Es una comparacion <=");}
break;
case 65:
//#line 137 ".\src\gramatica.y"
{System.out.println("Es una desigualdad");}
break;
case 66:
//#line 138 ".\src\gramatica.y"
{System.out.println("Es una igualdad");}
break;
case 67:
//#line 144 ".\src\gramatica.y"
{System.out.println("Es una asignación con := ");}
break;
case 68:
//#line 146 ".\src\gramatica.y"
{System.out.println("Es una asignación con = ");}
break;
case 71:
//#line 156 ".\src\gramatica.y"
{ System.out.println("Es una suma de los valores: " + val_peek(2)+ " y " + val_peek(1));}
break;
case 72:
//#line 158 ".\src\gramatica.y"
{ System.out.println("Es una resta de los valores: " + val_peek(2)+ " y " + val_peek(1));}
break;
case 73:
//#line 160 ".\src\gramatica.y"
{ System.out.println("Es un término");}
break;
case 74:
//#line 165 ".\src\gramatica.y"
{ System.out.println("Es una división de los valores: " +val_peek(2)+ " y " + val_peek(1));}
break;
case 75:
//#line 167 ".\src\gramatica.y"
{ System.out.println("Es una multiplicación de los valores: " + val_peek(2)+ " y " + val_peek(1));}
break;
case 76:
//#line 169 ".\src\gramatica.y"
{ System.out.println("Es un factor");}
break;
case 77:
//#line 174 ".\src\gramatica.y"
{ System.out.println("Es un id");}
break;
//#line 640 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
