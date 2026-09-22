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
    0,    0,    3,    3,    3,    3,    3,    3,    2,    6,
    6,    5,    5,    5,    5,    1,    1,   10,   10,   10,
   10,   13,   13,   15,   15,   16,   16,   16,   16,   17,
   17,   17,   17,   23,   23,   24,   26,   26,   25,   25,
   27,   27,   27,   27,   28,   28,   32,   32,   32,   32,
   29,   29,   30,   30,   18,   18,   18,   18,   19,   20,
   20,   11,   11,   33,   33,   34,   34,   21,   21,   21,
   12,   12,   22,   22,   36,   37,   37,   31,   40,    9,
    9,    9,   14,    8,    8,    4,    4,    4,    4,    4,
    4,    7,    7,   38,   38,   39,   39,   39,   41,   41,
   41,   42,   42,   42,   43,   43,   43,   35,   35,
};
final static short yylen[] = {                            2,
    3,    3,    6,    8,    6,    8,    8,    8,    3,    1,
    2,    2,    2,    2,    2,    1,    2,    2,    2,    2,
    2,    5,    4,    1,    2,    2,    2,    2,    2,    8,
    8,    7,    7,    1,    1,    3,    1,    2,    1,    2,
    1,    1,    2,    2,    2,    2,    8,    8,    8,    8,
    6,    6,    6,    6,    1,    1,    2,    2,    2,    2,
    2,    2,    2,    1,    3,    1,    3,    1,    1,    1,
    8,    8,    2,    4,    4,    1,    3,    4,    1,    4,
    4,    4,    6,    6,    6,    3,    3,    3,    3,    3,
    3,    3,    3,    1,    3,    3,    3,    1,    3,    3,
    1,    1,    1,    1,    1,    3,    3,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   70,   68,   69,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    2,   17,   18,   19,   20,   21,    0,    0,   62,
    1,    0,    0,    0,    0,    0,    0,    0,   55,   56,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   59,    0,   58,   57,    0,   61,    0,   60,
    0,   23,   25,   26,   27,   28,   29,    0,    0,    0,
    0,    0,    0,   13,   11,    9,   12,   14,   15,   65,
    0,    0,    0,  108,  109,    0,    0,    0,    0,    0,
   22,    0,    0,  103,  104,   92,    0,    0,  101,  102,
    0,    0,    0,    0,    0,   79,    0,    0,    0,    0,
    0,   83,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   82,   80,   81,    0,    0,    0,   67,    0,
    0,    0,    0,    0,    0,    0,  107,    0,    0,    0,
   99,  100,    0,   88,   89,   90,   91,   86,   87,    0,
    0,    0,   74,    0,   72,    0,    0,    0,    0,   75,
    0,   84,   85,    0,    3,    0,    5,   71,    0,    0,
    0,   41,    0,    0,   37,    0,    0,    0,    0,    0,
   33,    0,    0,    0,   35,   32,   34,   77,    0,    0,
    0,    0,    0,    0,    0,   36,   38,    0,   43,   44,
   45,   46,   31,   30,    0,    4,    7,    8,    6,    0,
    0,    0,    0,    0,   78,    0,    0,    0,    0,    0,
    0,   42,    0,   35,   41,   53,   54,   51,    0,   52,
    0,    0,    0,    0,    0,   47,   49,   50,   48,
};
final static short yydgoto[] = {                          3,
   10,  205,   52,  112,   53,   54,   55,   56,   57,   11,
   12,   13,   14,   15,   41,   42,   43,   44,   45,   46,
   16,   93,  206,  207,  193,  194,  195,  196,  197,  198,
  199,  200,   30,   96,  104,  105,  154,  113,  107,  118,
  108,  109,  110,
};
final static short yysindex[] = {                         4,
 -139, -139,    0,    0,    0,    0, -248, -211, -201, -195,
 -139,   54,   62,   98,  106, -189, -195, -107,  112,   28,
   -8,    0,    0,    0,    0,    0,    0,  136,  -70,    0,
    0,  155,  109,  -50,  -39,  -53,   32,   42,    0,    0,
  -32,   42,  162,  166,  186,  200,    5,  -21,  252,  258,
  279,  261,   -8,   43,  262,  264,  265,   68,  286, -156,
   29,  287,    0,   71,    0,    0,   72,    0,  136,    0,
   51,    0,    0,    0,    0,    0,    0,  128, -132, -132,
 -132, -132,   56,    0,    0,    0,    0,    0,    0,    0,
 -156,   74,  291,    0,    0,  240,  290, -156,  295,  142,
    0, -156,   47,    0,    0,    0,   36,   58,    0,    0,
  190,  296,   11,  297,  298,    0,   91,  299,  300,  292,
 -139,    0,   29,  301, -156, -156,  302, -132,   87, -132,
 -132, -132, -132, -132,   81, -132, -132, -132, -132, -132,
 -132,  -97,    0,    0,    0, -139, -156,   69,    0, -139,
  305,  307, -139,  308,  306,  311,    0,  190,   58,   58,
    0,    0,  -97,    0,    0,    0,    0,    0,    0,  -49,
   26, -195,    0,  -23,    0,   69, -139, -139,   75,    0,
 -132,    0,    0,  -97,    0,  -97,    0,    0,  312,  314,
  315,    0,   77,  -23,    0,    0,  303,  304,  309,  310,
    0,   69,   75,  -23,    0,    0,    0,    0,   80,   82,
   83,   84, -132, -132, -132,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   -8,    0,    0,    0,    0,  317,
  323,   96,  101, -104,    0, -104,  -49,   26,   89,   94,
   90,    0,  -54,    0,    0,    0,    0,    0, -129,    0,
 -129,   95,   97,   99,  100,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   93,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  316,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  102,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  103,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  319,    0,
    0,    0,    0,    0,    0,    0,    0,  316,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  280,    0,    0,  316,
    0,    0,  -41,    0,    0,    0,    3,  -29,    0,    0,
  320,    0,    0,    0,    0,    0,    0,    0,    0,  339,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  342,  -34,    0,    7,   -7,   -2,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -78,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -64,    0,    0,
    0,    0,    0,    0,  -25,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  107,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   73,   12,    0,  -58,  -46,  -36,    0,    0,    0,    0,
  125,    0,    0,  172,  182,    0,    0,    0,    0,    0,
   44,   -3, -158,  -87,    0,    0, -144, -122,    0,    0,
 -118,    0,   33,  263,  -38,  256,  206,  -30,   -6,    0,
  173,  181,    0,
};
final static int YYTABLESIZE=387;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        105,
  105,  105,  105,  105,  221,  105,  106,  106,  106,  106,
  106,   98,  106,   98,   98,   98,   85,  105,  105,  105,
  105,   22,   97,  114,  106,  106,  106,  106,   31,   98,
   98,   98,   98,   96,   18,   96,   96,   96,   97,   80,
   97,   97,   97,   94,  224,   19,   94,   95,  106,  217,
   95,   96,   96,   96,   96,   20,   97,   97,   97,   97,
  175,   94,   94,   47,   94,   95,   95,   28,   95,   70,
  140,  218,  141,  111,   17,  239,  117,  246,  131,   67,
  132,   47,   21,   23,   97,   47,  128,  119,  201,  241,
   90,  247,  129,   29,  124,  171,  130,  155,  127,  134,
    4,    5,    6,   92,  133,  164,  165,  166,  167,  168,
  169,  242,   24,  242,  223,  243,  183,    4,    5,    6,
   25,  151,  152,  158,  103,    7,    8,  192,   94,   95,
  253,  144,  255,  131,   92,  132,  235,  210,  131,  212,
  132,   92,    9,  173,   39,   92,  240,  192,  174,   32,
  155,  191,   48,  170,  230,  231,   26,  225,  189,   48,
   65,  252,   39,  254,   27,   49,   39,  102,   92,   92,
  190,   58,   33,  204,  182,   51,  191,   50,   42,   58,
   21,  126,   51,  188,   42,   58,   59,  238,   85,  245,
   92,   40,   42,  148,   60,  209,   42,  211,   42,   61,
   39,   42,   42,    4,    5,    6,   62,   66,  232,   40,
   42,   64,    8,   40,   40,   42,   42,   63,  172,   71,
   74,  251,  176,   73,   75,  179,  184,  185,  105,  105,
  105,  105,  131,   48,  132,  106,  106,  106,  106,  189,
   98,   98,   98,   98,   76,  237,   72,  244,   48,  202,
  203,  190,   79,   10,   49,   41,   51,  191,   77,    1,
    2,   78,   96,   96,   96,   96,   50,   97,   97,   97,
   97,   51,   94,   94,   94,   94,   95,   95,   95,   95,
  136,  137,  138,  139,    4,    5,    6,   68,   69,   94,
   95,   81,   34,    8,   35,   36,   37,   82,    4,    5,
    6,  186,  187,  159,  160,   38,   34,    8,   35,   36,
   37,  115,  103,  161,  162,  116,   94,   95,   83,   84,
   87,   86,   88,   89,   69,   91,   98,   99,  100,  101,
  120,  121,  122,  123,  125,  147,  135,  142,  143,  145,
  146,  150,  153,  156,  163,  177,  174,  178,  180,  181,
  128,  213,  204,  214,  215,  216,  226,  233,  227,  228,
  229,  219,  220,  234,  236,  248,  250,  221,  222,  249,
   16,  256,   66,  257,   63,  258,  259,   64,   93,   73,
   24,   10,   76,   34,  157,  149,  208,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   59,   47,   41,   42,   43,   44,
   45,   41,   47,   43,   44,   45,   53,   59,   60,   61,
   62,   10,   61,   82,   59,   60,   61,   62,   17,   59,
   60,   61,   62,   41,  283,   43,   44,   45,   41,   61,
   43,   44,   45,   41,  203,  257,   44,   41,   79,  194,
   44,   59,   60,   61,   62,  257,   59,   60,   61,   62,
  148,   59,   60,   20,   62,   59,   60,  257,   62,   37,
   60,  194,   62,   80,    2,  234,   83,  236,   43,   36,
   45,   38,  278,   11,  123,   42,   40,   91,  176,  234,
   58,  236,   46,  283,   98,  142,   61,  128,  102,   42,
  257,  258,  259,   60,   47,  136,  137,  138,  139,  140,
  141,  234,   59,  236,  202,  234,  163,  257,  258,  259,
   59,  125,  126,  130,  257,  265,  266,  174,  261,  262,
  249,   41,  251,   43,   91,   45,   41,  184,   43,  186,
   45,   98,  282,  147,   20,  102,  234,  194,  278,  257,
  181,  281,  257,  142,  213,  214,   59,  204,  263,  257,
   36,  249,   38,  251,   59,  263,   42,   40,  125,  126,
  275,   44,   61,  278,  163,  280,  281,  275,  257,   44,
  278,   40,  280,  172,  263,   44,  257,  234,  225,  236,
  147,   20,  257,  121,   40,  184,  275,  186,  263,   91,
  279,  280,  281,  257,  258,  259,  257,   36,  215,   38,
  275,  265,  266,   42,  279,  280,  281,  257,  146,   38,
   59,  276,  150,   42,   59,  153,  276,  277,  270,  271,
  272,  273,   43,  257,   45,  270,  271,  272,  273,  263,
  270,  271,  272,  273,   59,  234,  279,  236,  257,  177,
  178,  275,  274,  279,  263,  281,  280,  281,   59,  256,
  257,  257,  270,  271,  272,  273,  275,  270,  271,  272,
  273,  280,  270,  271,  272,  273,  270,  271,  272,  273,
  270,  271,  272,  273,  257,  258,  259,  256,  257,  261,
  262,   40,  265,  266,  267,  268,  269,   40,  257,  258,
  259,  276,  277,  131,  132,  278,  265,  266,  267,  268,
  269,  256,  257,  133,  134,  260,  261,  262,   40,   59,
   59,  279,   59,   59,  257,   40,   40,  257,  257,  279,
  257,   41,   93,   44,   40,   44,   41,   41,   41,   41,
   41,   41,   41,  257,  264,   41,  278,   41,   41,   44,
   40,   40,  278,   40,   40,  279,  277,   41,  277,  277,
  277,   59,   59,   41,  264,  277,  277,   59,   59,  276,
  278,  277,   93,  277,   59,  277,  277,   59,   59,   41,
  279,  279,   41,  277,  129,  123,  181,
};
}
final static short YYFINAL=3;
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
"programa : error sentencias_declarativas bloque_ejecutable",
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
"metodo : PRIVATE tipo ID '(' parametros_formales ')' sentencias_declarativas bloque_func",
"metodo : PRIVATE AUTO ID '(' parametros_formales ')' sentencias_declarativas bloque_retorno",
"metodo : tipo ID '(' parametros_formales ')' sentencias_declarativas bloque_func",
"metodo : AUTO ID '(' parametros_formales ')' sentencias_declarativas bloque_retorno",
"bloque_func : bloque_retorno",
"bloque_func : bloque_ejecutable",
"bloque_retorno : BEGIN sentencias_retornables END",
"sentencias_func : sent_func",
"sentencias_func : sentencias_func sent_func",
"sentencias_retornables : sentencia_retornable",
"sentencias_retornables : sentencias_func sentencia_retornable",
"sent_func : sentencia_ejecutable",
"sent_func : sentencia_retornable",
"sent_func : if_func ';'",
"sent_func : while_repeat_func ';'",
"sentencia_retornable : retorno ';'",
"sentencia_retornable : if_retorno_seguro ';'",
"if_retorno_seguro : IF '(' comp ')' bloque_retorno ELSE bloque_retorno END_IF",
"if_retorno_seguro : IF '(' comp ')' retorno ELSE retorno END_IF",
"if_retorno_seguro : IF '(' comp ')' bloque_retorno ELSE retorno END_IF",
"if_retorno_seguro : IF '(' comp ')' retorno ELSE bloque_retorno END_IF",
"if_func : IF '(' comp ')' bloque_func END_IF",
"if_func : IF '(' comp ')' sent_func END_IF",
"while_repeat_func : WHILE '(' comp ')' REPEAT bloque_func",
"while_repeat_func : WHILE '(' comp ')' REPEAT sent_func",
"atributo : declaracion_var",
"atributo : enum",
"atributo : PRIVATE enum",
"atributo : PRIVATE declaracion_var",
"friendly : FRIEND ID",
"herencia : EXTENDS lista_de_variables",
"herencia : EXTENDS error",
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
"funcion : AUTO FUNCTION ID '(' parametros_formales ')' sentencias_declarativas bloque_retorno",
"parametros_formales : tipo ID",
"parametros_formales : tipo ID ',' parametros_formales",
"invocacion_funcion : ID '(' parametros_reales ')'",
"parametros_reales : expr_asig",
"parametros_reales : expr_asig ',' parametros_reales",
"retorno : RET '(' expr ')'",
"cadena : STRING",
"print : POUT '(' expr ')'",
"print : POUT '(' cadena ')'",
"print : POUT '(' error ')'",
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

//#line 232 "src/gramatica.y"

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
//#line 497 "Parser.java"
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
case 2:
//#line 13 "src/gramatica.y"
{yyerror("Error: falta nombre de programa");}
break;
case 9:
//#line 25 "src/gramatica.y"
{System.out.println("Es un bloque ejecutable");}
break;
case 10:
//#line 28 "src/gramatica.y"
{System.out.println("Es una sentencia ejecutablesss");}
break;
case 11:
//#line 29 "src/gramatica.y"
{System.out.println("Es una sentencia ejecutablesss");}
break;
case 12:
//#line 32 "src/gramatica.y"
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
case 45:
//#line 91 "src/gramatica.y"
{System.out.println("Soy una sent retornable");}
break;
case 46:
//#line 92 "src/gramatica.y"
{System.out.println("Soy una sent retornable ");}
break;
case 47:
//#line 95 "src/gramatica.y"
{System.out.println("Soy un if retornable");}
break;
case 48:
//#line 96 "src/gramatica.y"
{System.out.println("Soy un if retornable");}
break;
case 49:
//#line 97 "src/gramatica.y"
{System.out.println("Soy un if retornable");}
break;
case 50:
//#line 98 "src/gramatica.y"
{System.out.println("Soy un if retornable");}
break;
case 58:
//#line 112 "src/gramatica.y"
{System.out.println("estamos permitiendo declaracion multiple de atributos");}
break;
case 61:
//#line 120 "src/gramatica.y"
{yyerror("Error sintactico: ausencia de nombre o lista de clases");}
break;
case 79:
//#line 161 "src/gramatica.y"
{System.out.println("Es una cadena");}
break;
case 81:
//#line 165 "src/gramatica.y"
{System.out.println("es una cadena que se imprime");}
break;
case 82:
//#line 166 "src/gramatica.y"
{yyerror("Error: falta argumento en sentencia POUT.");}
break;
case 86:
//#line 177 "src/gramatica.y"
{System.out.println("Es una comparacion <");}
break;
case 87:
//#line 178 "src/gramatica.y"
{System.out.println("Es una comparacion >");}
break;
case 88:
//#line 179 "src/gramatica.y"
{System.out.println("Es una comparacion >=");}
break;
case 89:
//#line 180 "src/gramatica.y"
{System.out.println("Es una comparacion <=");}
break;
case 90:
//#line 181 "src/gramatica.y"
{System.out.println("Es una desigualdad");}
break;
case 91:
//#line 182 "src/gramatica.y"
{System.out.println("Es una igualdad");}
break;
case 92:
//#line 188 "src/gramatica.y"
{System.out.println("Es una asignación con := ");}
break;
case 93:
//#line 190 "src/gramatica.y"
{System.out.println("Es una asignación con = ");}
break;
case 94:
//#line 194 "src/gramatica.y"
{System.out.println("Es una expr");}
break;
case 95:
//#line 195 "src/gramatica.y"
{System.out.println("Es una asignacion de expr");}
break;
case 96:
//#line 200 "src/gramatica.y"
{ System.out.println("Es una suma de los valores: " + val_peek(2)+ " y " + val_peek(1));}
break;
case 97:
//#line 202 "src/gramatica.y"
{ System.out.println("Es una resta de los valores: " + val_peek(2)+ " y " + val_peek(1));}
break;
case 98:
//#line 204 "src/gramatica.y"
{ System.out.println("Es un término");}
break;
case 99:
//#line 209 "src/gramatica.y"
{ System.out.println("Es una división de los valores: " +val_peek(2)+ " y " + val_peek(1));}
break;
case 100:
//#line 211 "src/gramatica.y"
{ System.out.println("Es una multiplicación de los valores: " + val_peek(2)+ " y " + val_peek(1));}
break;
case 101:
//#line 213 "src/gramatica.y"
{ System.out.println("Es un factor");}
break;
case 102:
//#line 218 "src/gramatica.y"
{ System.out.println("Es un factor");}
break;
case 103:
//#line 219 "src/gramatica.y"
{System.out.println("Es un cte factor");}
break;
case 104:
//#line 220 "src/gramatica.y"
{System.out.println("Es una invocacion_funcion factor");}
break;
case 105:
//#line 223 "src/gramatica.y"
{System.out.println("Es un variable");}
break;
case 106:
//#line 224 "src/gramatica.y"
{System.out.println("Es un atributo");}
break;
case 107:
//#line 225 "src/gramatica.y"
{System.out.println("Es un acceso a metodo");}
break;
case 108:
//#line 228 "src/gramatica.y"
{System.out.println("Detecte un entero");}
break;
case 109:
//#line 229 "src/gramatica.y"
{System.out.println("Es un double");}
break;
//#line 826 "Parser.java"
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
