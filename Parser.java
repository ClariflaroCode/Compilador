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






//#line 2 "src/gramatica.y"
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
   27,   28,   28,    9,   31,   10,   10,   15,    8,    8,
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
    4,    1,    3,    4,    1,    4,    4,    6,    6,    6,
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
   76,   77,    0,    0,    0,    0,   55,    0,    0,    0,
    0,    0,    0,   58,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   56,   57,   54,    0,    0,    0,   43,    0,    0,    0,
   82,    0,    0,    0,   74,   75,    0,   63,   64,   65,
   66,   61,   62,    0,    0,    0,   50,   48,   51,    0,
   59,   60,    0,    2,    0,    4,   47,   53,    0,    0,
    0,    0,    3,    6,    7,    5,
};
final static short yydgoto[] = {                          2,
    9,   20,   49,  104,   50,   51,   52,   53,   54,   55,
   10,   11,   12,   13,   14,   38,   39,   40,   41,   42,
   43,   28,   15,   92,   96,   89,   97,  138,  105,   99,
  109,  100,  101,  102,
};
final static short yysindex[] = {                      -240,
 -159,    0,    0,    0,    0, -259, -212, -168, -185, -159,
   55,   67,   75,   85, -189, -126,   97, -118, -162,    0,
    0,    0,    0,    0,    0,  121,  -70,    0,  153,  105,
  -59,  -90,  -58,  -95,    0,    0,    0,  -82,  -95,  141,
  142,  144,  145,  -56,  162,  165,  166,  167,  149, -162,
  -69,  150,  152,  154,  155,  -58,  172,  -74, -184,    0,
    0,    0,    0,  121,    0,  -64,    0,    0,    0,    0,
    0,    0, -133, -133, -133, -133, -105, -133,    0,    0,
    0,    0,    0,    0,    0,    0,  -74,  -40,  175,    0,
    0,  126,  176,    0,   71,    0,    0,    0,   59,   14,
    0,    0,  143,  180,   11,  181,    0,  136,  182,  137,
  183,  184, -159,    0, -184, -133,  -32, -133, -133, -133,
 -133, -133,  -38, -133, -133, -133, -133, -133, -133, -148,
    0,    0,    0, -159,  -74, -185,    0,  186,  189,  194,
    0,  143,   14,   14,    0,    0, -148,    0,    0,    0,
    0,    0,    0, -123,  -87, -185,    0,    0,    0, -133,
    0,    0, -148,    0, -148,    0,    0,    0,  -42,  -37,
  -31,  -30,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -33,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  190,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -27,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -26,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  191,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  158,    0,  -41,    0,    0,    0,    3,  -29,
    0,    0,  195,    0,    0,    0,    0,    0,    0,    0,
    0,  207,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  214,  -34,
    0,    7,   -7,   -2,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   12,  -60,    0,  185,  -55,  206,    0,    0,    0,    0,
    0,  103,  104,    0,  127,   52,    0,    0,    0,    0,
    0,   41,  -23,  147,  -36,  -47,  140,   98,  -44,  -28,
    0,   72,   73,    0,
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
   70,   71,   71,   71,   71,  122,   72,   72,   72,   72,
  121,   69,   69,   88,   69,   70,   70,   26,   70,  154,
  128,  139,  129,   65,  155,  158,   90,   91,   93,  148,
  149,  150,  151,  152,  153,   66,  161,  157,   18,  142,
   68,  162,   19,   27,   44,  167,   86,    3,    4,    5,
   45,  119,  169,  120,  171,    6,    7,  170,   44,  172,
  116,   88,   46,   22,   45,  139,  117,   47,   48,  118,
   35,   36,    8,   95,  136,   23,   46,   90,   91,   19,
   29,   47,   48,   24,   61,   62,   35,   36,    3,    4,
    5,   35,   36,   25,   37,  156,    6,    7,   31,   32,
   33,   95,  163,  164,  107,   90,   91,   30,   63,   34,
   37,    3,    4,    5,   56,   37,    3,    4,    5,    6,
    7,   31,   32,   33,    6,    7,  131,  133,  119,  119,
  120,  120,    3,    4,    5,  119,   57,  120,  165,  166,
  143,  144,   58,  145,  146,   59,   67,   60,   64,   69,
   70,   75,   71,   72,   76,   77,   78,   79,   82,   81,
   83,   87,   84,   85,   94,  113,  112,   73,  114,  115,
  123,  130,  132,  134,  140,  147,  159,  135,   80,   80,
   80,   80,  160,  116,  173,   81,   81,   81,   81,  174,
   73,   73,   73,   73,   16,  175,  176,   49,   39,   40,
   42,   24,    9,   68,   52,   80,  141,  168,    0,    0,
  106,  137,   71,   71,   71,   71,    0,   72,   72,   72,
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
   44,   59,   60,   61,   62,   42,   59,   60,   61,   62,
   47,   59,   60,   87,   62,   59,   60,  257,   62,  130,
   60,  116,   62,   33,  130,  136,  261,  262,  115,  124,
  125,  126,  127,  128,  129,   34,  147,  135,  257,  118,
   39,  147,  278,  283,  257,  156,   56,  257,  258,  259,
  263,   43,  163,   45,  165,  265,  266,  163,  257,  165,
   40,  135,  275,   59,  263,  160,   46,  280,  281,   61,
   18,   18,  282,  257,  113,   59,  275,  261,  262,  278,
  257,  280,  281,   59,   32,   32,   34,   34,  257,  258,
  259,   39,   39,   59,   18,  134,  265,  266,  267,  268,
  269,  257,  276,  277,  260,  261,  262,   61,   32,  278,
   34,  257,  258,  259,   44,   39,  257,  258,  259,  265,
  266,  267,  268,  269,  265,  266,   41,   41,   43,   43,
   45,   45,  257,  258,  259,   43,  257,   45,  276,  277,
  119,  120,   40,  121,  122,   91,  279,  257,  257,   59,
   59,   40,   59,   59,   40,   40,   40,   59,   59,  279,
   59,   40,   59,   59,  279,   41,  257,  274,   93,   44,
   41,   41,   41,   41,  257,  264,   41,   44,  270,  271,
  272,  273,   44,   40,  277,  270,  271,  272,  273,  277,
  270,  271,  272,  273,  278,  277,  277,   41,   59,   59,
   93,  279,  279,   59,   41,   50,  117,  160,   -1,   -1,
   76,  115,  270,  271,  272,  273,   -1,  270,  271,  272,
  273,   -1,  270,  271,  272,  273,  270,  271,  272,  273,
  270,  271,  272,  273,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=283;
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
"cadena : STRING",
"print : POUT '(' expr ')'",
"print : POUT '(' cadena ')'",
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

//#line 189 "src/gramatica.y"

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
//#line 422 "Parser.java"
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
case 1:
//#line 12 "src/gramatica.y"
{System.out.println("Es un programa");}
break;
case 8:
//#line 24 "src/gramatica.y"
{System.out.println("Es un bloque ejecutable");}
break;
case 9:
//#line 27 "src/gramatica.y"
{System.out.println("Es una sentencia ejecutablesss");}
break;
case 10:
//#line 28 "src/gramatica.y"
{System.out.println("Es una sentencia ejecutablesss");}
break;
case 11:
//#line 31 "src/gramatica.y"
{System.out.println("Es una sentencia ejecutable");}
break;
case 15:
//#line 35 "src/gramatica.y"
{System.out.println("Es una sentencia ejecutable");}
break;
case 16:
//#line 39 "src/gramatica.y"
{System.out.println("Es una sentencia declarativas");}
break;
case 17:
//#line 40 "src/gramatica.y"
{System.out.println("Es una sentencia declarativas");}
break;
case 18:
//#line 43 "src/gramatica.y"
{System.out.println("Es una sentencia declarativa");}
break;
case 30:
//#line 64 "src/gramatica.y"
{System.out.println("estamos admitiendo metodos con auto");}
break;
case 35:
//#line 71 "src/gramatica.y"
{System.out.println("estamos permitiendo declaracion multiple de atributos");}
break;
case 55:
//#line 119 "src/gramatica.y"
{System.out.println("Es una cadena");}
break;
case 57:
//#line 123 "src/gramatica.y"
{System.out.println("es una cadena que se imprime");}
break;
case 61:
//#line 134 "src/gramatica.y"
{System.out.println("Es una comparacion <");}
break;
case 62:
//#line 135 "src/gramatica.y"
{System.out.println("Es una comparacion >");}
break;
case 63:
//#line 136 "src/gramatica.y"
{System.out.println("Es una comparacion >=");}
break;
case 64:
//#line 137 "src/gramatica.y"
{System.out.println("Es una comparacion <=");}
break;
case 65:
//#line 138 "src/gramatica.y"
{System.out.println("Es una desigualdad");}
break;
case 66:
//#line 139 "src/gramatica.y"
{System.out.println("Es una igualdad");}
break;
case 67:
//#line 145 "src/gramatica.y"
{System.out.println("Es una asignación con := ");}
break;
case 68:
//#line 147 "src/gramatica.y"
{System.out.println("Es una asignación con = ");}
break;
case 69:
//#line 151 "src/gramatica.y"
{System.out.println("Es una expr");}
break;
case 70:
//#line 152 "src/gramatica.y"
{System.out.println("Es una asignacion de expr");}
break;
case 71:
//#line 157 "src/gramatica.y"
{ System.out.println("Es una suma de los valores: " + val_peek(2)+ " y " + val_peek(1));}
break;
case 72:
//#line 159 "src/gramatica.y"
{ System.out.println("Es una resta de los valores: " + val_peek(2)+ " y " + val_peek(1));}
break;
case 73:
//#line 161 "src/gramatica.y"
{ System.out.println("Es un término");}
break;
case 74:
//#line 166 "src/gramatica.y"
{ System.out.println("Es una división de los valores: " +val_peek(2)+ " y " + val_peek(1));}
break;
case 75:
//#line 168 "src/gramatica.y"
{ System.out.println("Es una multiplicación de los valores: " + val_peek(2)+ " y " + val_peek(1));}
break;
case 76:
//#line 170 "src/gramatica.y"
{ System.out.println("Es un factor");}
break;
case 77:
//#line 175 "src/gramatica.y"
{ System.out.println("Es un factor");}
break;
case 78:
//#line 176 "src/gramatica.y"
{System.out.println("Es un cte factor");}
break;
case 79:
//#line 177 "src/gramatica.y"
{System.out.println("Es una invocacion_funcion factor");}
break;
case 80:
//#line 180 "src/gramatica.y"
{System.out.println("Es un variable");}
break;
case 81:
//#line 181 "src/gramatica.y"
{System.out.println("Es un atributo");}
break;
case 82:
//#line 182 "src/gramatica.y"
{System.out.println("Es un acceso a metodo");}
break;
case 83:
//#line 185 "src/gramatica.y"
{System.out.println("Detecte un entero");}
break;
case 84:
//#line 186 "src/gramatica.y"
{System.out.println("Es un double");}
break;
//#line 719 "Parser.java"
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
