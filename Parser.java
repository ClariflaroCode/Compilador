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
    0,    0,    3,    3,    3,    3,    3,    3,    2,    2,
    2,    6,    6,    5,    5,    5,    5,    5,    1,    1,
   10,   10,   10,   10,   10,   13,   13,   15,   15,   16,
   16,   16,   16,   17,   17,   17,   17,   23,   23,   24,
   26,   26,   25,   25,   27,   27,   27,   27,   28,   28,
   32,   32,   32,   32,   29,   29,   30,   30,   18,   18,
   18,   18,   19,   20,   20,   11,   11,   33,   33,   34,
   34,   21,   21,   21,   12,   12,   22,   22,   36,   37,
   37,   31,   40,    9,    9,    9,   14,    8,    8,    4,
    4,    4,    4,    4,    4,    7,    7,   38,   38,   39,
   39,   39,   39,   39,   39,   41,   41,   41,   42,   42,
   42,   43,   43,   43,   35,   35,
};
final static short yylen[] = {                            2,
    3,    3,    6,    8,    6,    8,    8,    8,    3,    2,
    2,    1,    2,    2,    2,    2,    2,    2,    1,    2,
    2,    2,    2,    2,    2,    5,    4,    1,    2,    2,
    2,    2,    2,    8,    8,    7,    7,    1,    1,    3,
    1,    2,    1,    2,    1,    1,    2,    2,    2,    2,
    8,    8,    8,    8,    6,    6,    6,    6,    1,    1,
    2,    2,    2,    2,    2,    2,    2,    1,    3,    1,
    3,    1,    1,    1,    8,    8,    2,    4,    4,    1,
    3,    4,    1,    4,    4,    4,    6,    6,    6,    3,
    3,    3,    3,    3,    3,    3,    3,    1,    3,    3,
    3,    2,    3,    3,    1,    3,    3,    1,    1,    1,
    1,    1,    3,    3,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,   74,   72,   73,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   25,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    2,    0,
    0,    0,    0,    0,    0,   20,   21,   22,   23,   24,
    0,    0,   66,    1,    0,    0,    0,    0,    0,    0,
    0,   59,   60,    0,    0,    0,    0,    0,    0,    0,
   18,    0,    0,    0,    0,    0,    0,   15,   13,   10,
   14,   16,   17,    0,    0,    0,    0,    0,   63,    0,
   62,   61,    0,   65,    0,   64,    0,   27,   29,   30,
   31,   32,   33,    0,    0,  115,  116,  110,  111,   96,
    0,    0,  108,  109,    0,    0,    0,    0,    9,    0,
   83,    0,    0,   69,    0,    0,    0,    0,    0,    0,
    0,    0,   26,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   86,   84,   85,    0,    0,    0,   87,    0,    0,    0,
    0,    0,    0,    0,    0,  114,    0,  103,    0,  104,
    0,  106,  107,    0,   92,   93,   94,   95,   90,   91,
    0,    0,    0,    0,    0,   71,    0,    0,    0,    0,
   79,    0,   88,    0,    0,    3,    0,    5,    0,   78,
    0,   76,    0,    0,    0,    0,   81,    0,    0,    0,
    0,   75,    0,    0,    0,   45,    0,    0,   41,    0,
    0,    0,    0,    0,   37,    0,    0,    0,   39,   36,
   38,    4,    7,    8,    6,    0,    0,    0,   40,   42,
    0,   47,   48,   49,   50,   35,   34,    0,    0,    0,
    0,    0,    0,   82,    0,    0,    0,    0,    0,    0,
   46,    0,   39,    0,   57,   58,   55,    0,   56,    0,
    0,    0,    0,    0,   51,   53,   54,   52,
};
final static short yydgoto[] = {                          3,
   11,  219,   30,  106,   31,   32,   33,   34,   35,   12,
   13,   14,   15,   16,   54,   55,   56,   57,   58,   59,
   17,  117,  220,  221,  207,  208,  209,  210,  211,  212,
  213,  214,   43,  118,   98,   99,  153,  107,  101,  113,
  102,  103,  104,
};
final static short yysindex[] = {                      -207,
  -55,  -55,    0,  -21,    0,    0,    0, -221, -189, -180,
  145,  -55,   27,   33,   49,   57, -205,  145,    0, -159,
   65,  215,   86,  -41,  115,  127,  104,  143,    0,  131,
  104,  -80,  160,  189,  192,    0,    0,    0,    0,    0,
  208,    8,    0,    0,  222,  180,   15,   16,   89,  -97,
  220,    0,    0,   -5,  220,  216,  217,  227,  228,   31,
    0, -160, -160, -160, -160,   17,  -44,    0,    0,    0,
    0,    0,    0,   34,  257,  -52,  -82,  262,    0,   48,
    0,    0,   53,    0,  208,    0,   40,    0,    0,    0,
    0,    0,    0,   92,   39,    0,    0,    0,    0,    0,
   82,   81,    0,    0,   -1,  272,   44,  293,    0,  296,
    0,    2,  301,    0,  -52,   88,  317,  266,  319,  -52,
  324,   95,    0,  -52, -160,  108, -160,   -7,   47,   81,
 -160, -160,  102, -160, -160, -160, -160, -160, -160,  145,
    0,    0,    0,  328,  326,  -55,    0,  -82,  336,  -52,
  -52,  349,  350,  352,  363,    0,   -1,    0,   81,    0,
   81,    0,    0,  145,    0,    0,    0,    0,    0,    0,
  -68,  156,  -55,  -52,  126,    0,  -55,  366,  373,  -55,
    0, -160,    0,  104,  145,    0,  145,    0,  145,    0,
  -93,    0,  126,  -55,  -55,  171,    0,  138,  181,  141,
  191,    0,  381,  384,  389,    0,  151,  -93,    0,    0,
  376,  380,  383,  386,    0,  126,  171,  -93,    0,    0,
    0,    0,    0,    0,    0, -160, -160, -160,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  104,  399,  409,
   79,  193,   93,    0,   93,  -68,  156,  178,  183,  185,
    0,  -46,    0,  104,    0,    0,    0, -106,    0, -106,
  186,  187,  188,  190,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  117,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    1,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  401,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  196,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    5,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  131,    0,    0,    0,    0,    0,
    0,    0,    0,  401,  -33,    0,    0,    0,    0,    0,
   55,   -4,    0,    0,  410,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  377,    0,
    0,  401,    0,    0,    0,    0,    0,    0,    0,   22,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  449,    0,    0,    0,    0,    0,
    0,    0,    0,  450,  -26,    0,   59,    0,   28,    0,
   50,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  213,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -57,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  213,    0,
  213,    0,    0,    0,    0,    0,    0,    0,    0,  119,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  130,    0,    0,    0,    0,    0,    0,  -56,    0,    0,
    0,    0,    0,    0,    0,    0,  213,    0,  218,    0,
    0,    0,    0,  -53,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   20,  -11,    0,  -42,  198,  134,    0,    0,    0,    0,
  122,    0,    0,  140,  101,    0,    0,    0,    0,    0,
  302,   18, -192,  -62,    0,    0, -184, -138,    0,    0,
 -212,    0,  -20,  346,  -73,  370,  315,   12,   13,    0,
   29,  111,    0,
};
final static int YYTABLESIZE=497;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         29,
   12,   89,   12,  119,   11,   45,   44,  112,  112,  112,
  112,  112,  234,  112,  113,  113,  113,  113,  113,   63,
  113,   18,  108,  230,  237,  112,  112,  112,  112,   86,
  252,   36,  113,  113,  113,  113,  105,   19,  105,  105,
  105,  128,  142,  129,  128,  262,  129,  264,    1,    2,
  248,   41,  255,  114,  105,  105,  105,  105,  250,   12,
  256,   20,  102,   11,  102,  102,  102,   21,  100,  231,
  100,  100,  100,  100,  119,  105,   22,   42,  125,  112,
  102,  102,  102,  102,  126,   37,  100,  100,  100,  100,
  101,   38,  101,  101,  101,   98,   95,   45,   98,   99,
   96,   97,   99,  138,  251,  139,  251,   39,  101,  101,
  101,  101,  192,   98,   98,   40,   98,   99,   99,  244,
   99,  128,  132,  129,  128,   46,  129,  131,  171,  130,
  215,  124,  144,  130,  151,   74,  154,  149,   74,  157,
  130,  152,  127,   52,   61,  165,  166,  167,  168,  169,
  170,   87,  183,  236,   64,   89,  159,  161,   84,   85,
   66,   53,   23,   24,   69,  175,   65,  178,  179,  203,
   81,  191,   52,  198,  205,  200,   52,  202,   96,   97,
  249,  204,   67,  239,  240,  130,   28,  205,   82,   68,
   53,  190,  189,  154,   53,  261,  193,  263,   70,  196,
    4,    5,    6,    7,    5,    6,    7,  185,  186,    8,
    9,  110,   95,  216,  217,  111,   96,   97,   71,   12,
   12,   12,   12,  112,   45,   12,   10,  112,  112,  260,
  113,  246,   62,  253,  113,  113,  112,  112,  112,  112,
  241,  162,  163,  113,  113,  113,  113,   72,  158,   95,
   73,   74,  105,   96,   97,   95,  105,  105,   95,   96,
   97,   76,   96,   97,   75,  105,  105,  105,  105,  130,
   77,   78,   79,   88,   90,   91,   12,   12,  102,   12,
   11,   11,  102,  102,  100,   92,   93,   94,  100,  100,
   85,  102,  102,  102,  102,  109,  115,  100,  100,  100,
  100,  120,  160,   95,  121,   69,  101,   96,   97,  122,
  101,  101,  133,  134,  135,  136,  137,   69,  123,  101,
  101,  101,  101,   60,   98,   98,   98,   98,   99,   99,
   99,   99,   69,  140,   69,   95,  141,  172,   95,   96,
   97,  143,   96,   97,  145,    5,    6,    7,   23,   24,
   83,   66,   60,   80,    9,  203,   60,  146,  147,   23,
   24,  184,  148,  150,  155,  164,   25,  204,  173,  174,
  218,   69,   28,  205,   46,   46,  177,  116,   26,   19,
   69,   46,  199,   28,  201,   46,   46,   69,  206,  180,
  181,   19,   46,   46,   19,  182,   19,   43,   46,   46,
   23,   24,  125,  191,   46,  206,  194,   25,   44,   46,
   46,   23,   24,  195,  222,  238,  116,  224,   25,   26,
  226,  116,   27,  227,   28,  116,   23,   24,  228,  229,
   26,  187,  188,   25,  232,   28,   23,   24,  233,  242,
  247,  234,  254,   25,  235,   26,   23,   24,  218,  243,
   28,  116,  116,   25,  257,   26,  245,  223,  258,   67,
   28,  259,  265,  266,  267,   26,  268,  225,   97,   70,
   28,    5,    6,    7,   28,  116,    5,    6,    7,   47,
    9,   48,   49,   50,   47,    9,   48,   49,   50,   77,
   80,   12,   51,  176,   38,  156,  197,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         11,
    0,   59,   59,   77,    0,   59,   18,   41,   42,   43,
   44,   45,   59,   47,   41,   42,   43,   44,   45,   61,
   47,    2,   65,  208,  217,   59,   60,   61,   62,   50,
  243,   12,   59,   60,   61,   62,   41,   59,   43,   44,
   45,   43,   41,   45,   43,  258,   45,  260,  256,  257,
  243,  257,  245,   74,   59,   60,   61,   62,  243,   59,
  245,  283,   41,   59,   43,   44,   45,  257,   41,  208,
   43,   44,   45,   62,  148,   63,  257,  283,   40,   67,
   59,   60,   61,   62,   46,   59,   59,   60,   61,   62,
   41,   59,   43,   44,   45,   41,  257,  257,   44,   41,
  261,  262,   44,   60,  243,   62,  245,   59,   59,   60,
   61,   62,  175,   59,   60,   59,   62,   59,   60,   41,
   62,   43,   42,   45,   43,   61,   45,   47,  140,  101,
  193,   40,  115,  105,   40,   44,  125,  120,   44,  127,
  112,  124,   61,   22,   59,  134,  135,  136,  137,  138,
  139,   51,  164,  216,   40,   55,  128,  129,  256,  257,
   27,   22,  256,  257,   31,  146,   40,  150,  151,  263,
   49,  278,   51,  185,  281,  187,   55,  189,  261,  262,
  243,  275,   40,  226,  227,  157,  280,  281,   49,   59,
   51,  174,  173,  182,   55,  258,  177,  260,  279,  180,
  256,  257,  258,  259,  257,  258,  259,  276,  277,  265,
  266,  256,  257,  194,  195,  260,  261,  262,   59,  276,
  277,  279,  279,  257,  281,  279,  282,  261,  262,  276,
  257,  243,  274,  245,  261,  262,  270,  271,  272,  273,
  228,  131,  132,  270,  271,  272,  273,   59,  256,  257,
   59,   44,  257,  261,  262,  257,  261,  262,  257,  261,
  262,   40,  261,  262,  257,  270,  271,  272,  273,  241,
   91,  257,  257,  279,   59,   59,  276,  277,  257,  279,
  276,  277,  261,  262,  257,   59,   59,  257,  261,  262,
  257,  270,  271,  272,  273,  279,   40,  270,  271,  272,
  273,   40,  256,  257,  257,  172,  257,  261,  262,  257,
  261,  262,   41,  270,  271,  272,  273,  184,  279,  270,
  271,  272,  273,   22,  270,  271,  272,  273,  270,  271,
  272,  273,  199,   41,  201,  257,   41,  140,  257,  261,
  262,   41,  261,  262,  257,  257,  258,  259,  256,  257,
   49,  218,   51,  265,  266,  263,   55,   41,   93,  256,
  257,  164,   44,   40,  257,  264,  263,  275,   41,   44,
  278,  238,  280,  281,  256,  257,   41,   76,  275,  263,
  247,  263,  185,  280,  187,  256,  257,  254,  191,   41,
   41,  275,  263,  275,  278,   44,  280,  279,  280,  281,
  256,  257,   40,  278,  275,  208,   41,  263,  279,  280,
  281,  256,  257,   41,  277,  218,  115,  277,  263,  275,
   40,  120,  278,   40,  280,  124,  256,  257,   40,  279,
  275,  276,  277,  263,   59,  280,  256,  257,   59,   41,
  243,   59,  245,  263,   59,  275,  256,  257,  278,   41,
  280,  150,  151,  263,  277,  275,  264,  277,  276,   59,
  280,  277,  277,  277,  277,  275,  277,  277,   59,   93,
  280,  257,  258,  259,  279,  174,  257,  258,  259,  265,
  266,  267,  268,  269,  265,  266,  267,  268,  269,   41,
   41,  279,  278,  148,  277,  126,  182,
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
"bloque_ejecutable : sentencias_ejecutables END",
"bloque_ejecutable : BEGIN sentencias_ejecutables",
"sentencias_ejecutables : sentencia_ejecutable",
"sentencias_ejecutables : sentencia_ejecutable sentencias_ejecutables",
"sentencia_ejecutable : assign ';'",
"sentencia_ejecutable : if ';'",
"sentencia_ejecutable : while_repeat ';'",
"sentencia_ejecutable : print ';'",
"sentencia_ejecutable : error ';'",
"sentencias_declarativas : sentencia_declarativa",
"sentencias_declarativas : sentencia_declarativa sentencias_declarativas",
"sentencia_declarativa : declaracion_var ';'",
"sentencia_declarativa : funcion ';'",
"sentencia_declarativa : clase ';'",
"sentencia_declarativa : enum ';'",
"sentencia_declarativa : error ';'",
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
"expr : expr term",
"expr : expr '+' error",
"expr : expr '-' error",
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

//#line 238 "src/gramatica.y"

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
//#line 531 "Parser.java"
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
case 10:
//#line 26 "src/gramatica.y"
{yyerror("Error sintactico: Falta el BEGIN");}
break;
case 11:
//#line 27 "src/gramatica.y"
{yyerror("Errror sintactico: Falta el END");}
break;
case 18:
//#line 38 "src/gramatica.y"
{yyerrflag=0;}
break;
case 25:
//#line 50 "src/gramatica.y"
{yyerrflag=0;}
break;
case 49:
//#line 95 "src/gramatica.y"
{System.out.println("Soy una sent retornable");}
break;
case 50:
//#line 96 "src/gramatica.y"
{System.out.println("Soy una sent retornable ");}
break;
case 51:
//#line 99 "src/gramatica.y"
{System.out.println("Soy un if retornable");}
break;
case 52:
//#line 100 "src/gramatica.y"
{System.out.println("Soy un if retornable");}
break;
case 53:
//#line 101 "src/gramatica.y"
{System.out.println("Soy un if retornable");}
break;
case 54:
//#line 102 "src/gramatica.y"
{System.out.println("Soy un if retornable");}
break;
case 62:
//#line 116 "src/gramatica.y"
{System.out.println("estamos permitiendo declaracion multiple de atributos");}
break;
case 65:
//#line 124 "src/gramatica.y"
{yyerror("Error sintactico: ausencia de nombre o lista de clases");}
break;
case 83:
//#line 165 "src/gramatica.y"
{System.out.println("Es una cadena");}
break;
case 85:
//#line 169 "src/gramatica.y"
{System.out.println("es una cadena que se imprime");}
break;
case 86:
//#line 170 "src/gramatica.y"
{yyerror("Error: falta argumento en sentencia POUT.");}
break;
case 90:
//#line 181 "src/gramatica.y"
{System.out.println("Es una comparacion <");}
break;
case 91:
//#line 182 "src/gramatica.y"
{System.out.println("Es una comparacion >");}
break;
case 92:
//#line 183 "src/gramatica.y"
{System.out.println("Es una comparacion >=");}
break;
case 93:
//#line 184 "src/gramatica.y"
{System.out.println("Es una comparacion <=");}
break;
case 94:
//#line 185 "src/gramatica.y"
{System.out.println("Es una desigualdad");}
break;
case 95:
//#line 186 "src/gramatica.y"
{System.out.println("Es una igualdad");}
break;
case 96:
//#line 192 "src/gramatica.y"
{System.out.println("Es una asignación con := ");}
break;
case 97:
//#line 194 "src/gramatica.y"
{System.out.println("Es una asignación con = ");}
break;
case 99:
//#line 199 "src/gramatica.y"
{System.out.println("Es una asignacion de expr");}
break;
case 100:
//#line 204 "src/gramatica.y"
{ System.out.println("Es una suma de los valores: " + val_peek(2)+ " y " + val_peek(1));}
break;
case 101:
//#line 206 "src/gramatica.y"
{ System.out.println("Es una resta de los valores: " + val_peek(2)+ " y " + val_peek(1));}
break;
case 102:
//#line 207 "src/gramatica.y"
{yyerror("Error sintactico: Falta operador en expresión");}
break;
case 103:
//#line 208 "src/gramatica.y"
{ yyerror("Error sintactico: Falta operando en expr");}
break;
case 104:
//#line 209 "src/gramatica.y"
{ yyerror("Error sintactico: Falta operando en expr");}
break;
case 106:
//#line 216 "src/gramatica.y"
{ System.out.println("Es una división de los valores: " +val_peek(2)+ " y " + val_peek(1));}
break;
case 107:
//#line 218 "src/gramatica.y"
{ System.out.println("Es una multiplicación de los valores: " + val_peek(2)+ " y " + val_peek(1));}
break;
case 113:
//#line 229 "src/gramatica.y"
{System.out.println("Es un atributo");}
break;
case 114:
//#line 230 "src/gramatica.y"
{System.out.println("Es un acceso a metodo");}
break;
//#line 820 "Parser.java"
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
