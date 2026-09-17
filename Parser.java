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
    0,    0,    0,    0,    0,    0,    4,    2,    6,    6,
    3,    3,    3,    3,    3,    5,    5,   11,   11,   11,
   11,   14,   14,   16,   16,   17,   17,   17,   17,   18,
   18,   19,   19,   19,   19,   20,   21,   12,   12,   22,
   22,   24,   24,   23,   23,   13,   13,   26,   26,   27,
   28,   28,    9,   10,   10,   31,   15,    8,    8,    1,
    1,    1,    1,    1,    1,    7,    7,   29,   29,   30,
   30,   30,   32,   32,   32,   33,   33,   33,   34,   34,
   34,   25,   25,
};
final static short yylen[] = {                            2,
    6,    8,    6,    8,    8,    8,    3,    3,    1,    2,
    2,    2,    2,    2,    2,    1,    2,    2,    2,    2,
    2,    5,    4,    1,    2,    2,    2,    2,    2,    2,
    1,    1,    1,    2,    2,    2,    2,    2,    2,    1,
    3,    1,    3,    1,    1,    8,    8,    2,    4,    4,
    1,    3,    4,    4,    4,    3,    6,    6,    6,    3,
    3,    3,    3,    3,    3,    3,    3,    1,    3,    3,
    3,    1,    3,    3,    1,    1,    1,    1,    1,    3,
    3,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,   82,   83,    0,   77,   78,    0,
    0,   75,   76,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   81,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   73,   74,   50,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   12,    0,    1,    0,    3,   11,   13,
   14,   15,   52,    0,   66,    0,    0,   10,    8,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   54,
   55,   53,    2,    5,    6,    4,    0,   56,   58,   59,
};
final static short yydgoto[] = {                         37,
    7,   38,   60,    0,    0,   61,   40,   41,   42,   43,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    8,    0,    9,   27,   28,   29,
   82,   11,   12,   13,
};
final static short yysindex[] = {                      -253,
  -16,    0, -168,  -17,    0,    0,   -6,    0,    0,    2,
   50,    0,    0, -168, -217, -179, -168, -168, -168, -168,
 -168, -168, -168, -168, -168, -168,   28,   39,    6,   51,
    0,  -56,   55, -157,   70,   80,   27, -197, -169,   63,
   68,   69,   71,    5,    5,    5,    5,    5,    5,   50,
   50,    0,    0,    0, -168, -168, -168, -168, -168, -157,
 -150, -180, -168,    0, -179,    0, -179,    0,    0,    0,
    0,    0,    0,    5,    0,    5,   91,    0,    0, -129,
   20,   92,   76, -143, -142, -141, -140, -126, -146,    0,
    0,    0,    0,    0,    0,    0, -179,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,  -41,    0,    0,    0,    0,    0,    0,
  -29,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   99,   44,  -34,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  101,  102,  103,  104,  105,  106,   -7,
   -2,    0,    0,    0,    0,    0,    0,    0,    0, -138,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   46,    0,   89,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                       149,
   93,  -21,    1,    0,    0,   94,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  135,   96,   98,   53,
    0,   90,  100,    0,
};
final static int YYTABLESIZE=275;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         79,
   79,   79,   79,   79,   58,   79,   80,   80,   80,   80,
   80,   72,   80,   72,   72,   72,   39,   79,   79,   79,
   79,    1,   14,    3,   80,   80,   80,   80,   15,   72,
   72,   72,   72,   70,   16,   70,   70,   70,   71,   30,
   71,   71,   71,   84,   23,   86,   24,   23,   23,   24,
   24,   70,   70,   70,   70,   10,   71,   71,   71,   71,
   90,   21,   23,   22,   24,   85,   56,   87,   54,   44,
   45,   46,   47,   48,   49,   99,    4,   32,   65,   66,
    5,    6,   55,   33,   68,   64,   69,   68,    4,   69,
   14,   26,    5,    6,   59,    1,   25,  100,   34,   32,
   35,   36,   68,   80,   69,   33,   67,   68,   74,   62,
   76,   10,   50,   51,   81,   83,   92,    1,   23,   63,
   24,   69,   35,   36,   52,   53,   70,   71,   79,   72,
   89,   88,   91,   93,   94,   95,   96,   97,   98,   51,
    9,   62,   63,   64,   65,   60,   61,   67,    2,   31,
   73,   77,    0,   78,   75,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   57,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   79,   79,
   79,   79,    0,    0,    0,   80,   80,   80,   80,    0,
   72,   72,   72,   72,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   70,   70,   70,   70,    0,   71,   71,   71,
   71,   17,   18,   19,   20,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   61,   47,   41,   42,   43,   44,
   45,   41,   47,   43,   44,   45,   16,   59,   60,   61,
   62,  275,   40,   40,   59,   60,   61,   62,   46,   59,
   60,   61,   62,   41,   41,   43,   44,   45,   41,  257,
   43,   44,   45,   65,   43,   67,   45,   43,   43,   45,
   45,   59,   60,   61,   62,    3,   59,   60,   61,   62,
   41,   60,   43,   62,   45,   65,   61,   67,   41,   17,
   18,   19,   20,   21,   22,   97,  257,  257,  276,  277,
  261,  262,   44,  263,   41,   59,   41,   44,  257,   44,
   40,   42,  261,  262,   40,  275,   47,   97,  278,  257,
  280,  281,   59,  284,   59,  263,  276,  277,   56,   40,
   58,   59,   23,   24,   62,   63,   41,  275,   43,   40,
   45,   59,  280,  281,   25,   26,   59,   59,  279,   59,
  260,   41,   41,  277,  277,  277,  277,  264,  285,   41,
  279,   41,   41,   41,   41,   41,   41,   59,    0,   15,
   55,   59,   -1,   60,   57,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  274,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  270,  271,
  272,  273,   -1,   -1,   -1,  270,  271,  272,  273,   -1,
  270,  271,  272,  273,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  270,  271,  272,  273,   -1,  270,  271,  272,
  273,  270,  271,  272,  273,
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
"$accept : if",
"if : IF '(' comp ')' bloque_ejecutable END_IF",
"if : IF '(' comp ')' bloque_ejecutable ELSE bloque_ejecutable END_IF",
"if : IF '(' comp ')' sentencia_ejecutable END_IF",
"if : IF '(' comp ')' sentencia_ejecutable ELSE sentencia_ejecutable END_IF",
"if : IF '(' comp ')' bloque_ejecutable ELSE sentencia_ejecutable END_IF",
"if : IF '(' comp ')' sentencia_ejecutable ELSE bloque_ejecutable END_IF",
"programa : ID sentencias_declarativas bloque_ejecutable",
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
"comp : expr '<' expr",
"comp : expr '>' expr",
"comp : expr MAYOR_IGUAL expr",
"comp : expr MENOR_IGUAL expr",
"comp : expr DISTINTO expr",
"comp : expr IGUALDAD expr",
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

//#line 187 ".\src\gramatica.y"

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
//#line 396 "Parser.java"
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
//#line 65 ".\src\gramatica.y"
{System.out.println("estamos admitiendo metodos con auto");}
break;
case 35:
//#line 72 ".\src\gramatica.y"
{System.out.println("estamos permitiendo declaracion multiple de atributos")}
break;
case 60:
//#line 133 ".\src\gramatica.y"
{System.out.println(“Es una comparacion <”)}
break;
case 61:
//#line 134 ".\src\gramatica.y"
{System.out.println(“Es una comparacion >”)}
break;
case 62:
//#line 135 ".\src\gramatica.y"
{System.out.println(“Es una comparacion >=”)}
break;
case 63:
//#line 136 ".\src\gramatica.y"
{System.out.println(“Es una comparacion <=”)}
break;
case 64:
//#line 137 ".\src\gramatica.y"
{System.out.println(“Es una desigualdad”)}
break;
case 65:
//#line 138 ".\src\gramatica.y"
{System.out.println(“Es una igualdad”)}
break;
case 66:
//#line 144 ".\src\gramatica.y"
{System.out.println(“Es una asignación con := ”)}
break;
case 67:
//#line 146 ".\src\gramatica.y"
{System.out.println(“Es una asignación con = ”)}
break;
case 70:
//#line 155 ".\src\gramatica.y"
{ System.out.print(“Es una suma de los valores: “ , val_peek(2), “ y ” , val_peek(1))}
break;
case 71:
//#line 157 ".\src\gramatica.y"
{ System.out.print(“Es una resta de los valores: “ , val_peek(2), “ y ” , val_peek(1))}
break;
case 72:
//#line 159 ".\src\gramatica.y"
{ System.out.println(“Es un término”)}
break;
case 73:
//#line 164 ".\src\gramatica.y"
{ System.out.print(“Es una división de los valores: “ , val_peek(2), “ y ” , val_peek(1)”)}
break;
case 74:
//#line 166 ".\src\gramatica.y"
{ System.out.print(“Es una multiplicación de los valores: “ , val_peek(2), “ y ” , val_peek(1)”)}
break;
case 75:
//#line 168 ".\src\gramatica.y"
{ System.out.print(“Es un factor”)}
break;
case 76:
//#line 173 ".\src\gramatica.y"
{ System.out.println(“Es un id”)}
break;
//#line 613 "Parser.java"
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
