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
    2,    6,    6,    5,    5,    5,    5,    1,    1,   10,
   10,   10,   10,   13,   13,   15,   15,   16,   16,   16,
   16,   17,   17,   17,   17,   23,   23,   24,   26,   26,
   25,   25,   27,   27,   27,   27,   28,   28,   32,   32,
   32,   32,   29,   29,   30,   30,   18,   18,   18,   18,
   19,   20,   20,   11,   11,   33,   33,   34,   34,   21,
   21,   21,   12,   12,   22,   22,   36,   37,   37,   31,
   40,    9,    9,    9,   14,    8,    8,    4,    4,    4,
    4,    4,    4,    7,    7,   38,   38,   39,   39,   39,
   39,   39,   39,   41,   41,   41,   42,   42,   42,   43,
   43,   43,   35,   35,
};
final static short yylen[] = {                            2,
    3,    3,    6,    8,    6,    8,    8,    8,    3,    2,
    2,    1,    2,    2,    2,    2,    2,    1,    2,    2,
    2,    2,    2,    5,    4,    1,    2,    2,    2,    2,
    2,    8,    8,    7,    7,    1,    1,    3,    1,    2,
    1,    2,    1,    1,    2,    2,    2,    2,    8,    8,
    8,    8,    6,    6,    6,    6,    1,    1,    2,    2,
    2,    2,    2,    2,    2,    1,    3,    1,    3,    1,
    1,    1,    8,    8,    2,    4,    4,    1,    3,    4,
    1,    4,    4,    4,    6,    6,    6,    3,    3,    3,
    3,    3,    3,    3,    3,    1,    3,    3,    3,    2,
    3,    3,    1,    3,    3,    1,    1,    1,    1,    1,
    3,    3,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   72,   70,   71,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    2,    0,    0,    0,    0,
    0,    0,   19,   20,   21,   22,   23,    0,    0,   64,
    1,    0,    0,    0,    0,    0,    0,    0,   57,   58,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   15,   13,   10,   14,   16,   17,    0,
    0,    0,    0,    0,   61,    0,   60,   59,    0,   63,
    0,   62,    0,   25,   27,   28,   29,   30,   31,    0,
    0,  113,    0,  108,  109,   94,    0,    0,  106,  107,
    0,    0,    0,    0,    9,    0,   81,    0,    0,   67,
    0,    0,    0,    0,    0,    0,    0,    0,   24,    0,
    0,    0,  114,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   84,   82,   83,
    0,    0,    0,   85,    0,    0,    0,    0,    0,    0,
    0,    0,  112,    0,  101,    0,  102,    0,  104,  105,
    0,   90,   91,   92,   93,   88,   89,    0,    0,    0,
    0,    0,   69,    0,    0,    0,    0,   77,    0,   86,
    0,    0,    3,    0,    5,    0,   76,    0,   74,    0,
    0,    0,    0,   79,    0,    0,    0,    0,   73,    0,
    0,    0,   43,    0,    0,   39,    0,    0,    0,    0,
    0,   35,    0,    0,    0,   37,   34,   36,    4,    7,
    8,    6,    0,    0,    0,   38,   40,    0,   45,   46,
   47,   48,   33,   32,    0,    0,    0,    0,    0,    0,
   80,    0,    0,    0,    0,    0,    0,   44,    0,   37,
    0,   55,   56,   53,    0,   54,    0,    0,    0,    0,
    0,   49,   51,   52,   50,
};
final static short yydgoto[] = {                          3,
   10,  216,   27,  102,   28,   29,   30,   31,   32,   11,
   12,   13,   14,   15,   51,   52,   53,   54,   55,   56,
   16,  113,  217,  218,  204,  205,  206,  207,  208,  209,
  210,  211,   40,  114,   94,   95,  150,  103,   97,  109,
   98,   99,  100,
};
final static short yysindex[] = {                      -174,
  -57,  -57,    0,    0,    0,    0, -242, -192, -162,  144,
  -57,   39,   62,   77,   89, -208,  144, -124,  103,  205,
  -33,  126,  128,  186,  130,    0,  127,  186,  -87,  138,
  146,  148,    0,    0,    0,    0,    0,  162,  -47,    0,
    0,  175,  125,  -40,  -29,   27,  -68,  210,    0,    0,
  -58,  210,  165,  173,  180,  188,   -9,  -27,  -27,  -27,
  -27,  -30,   13,    0,    0,    0,    0,    0,    0,   -7,
  213,  -79,  -15,  216,    0,    4,    0,    0,   15,    0,
  162,    0,   -8,    0,    0,    0,    0,    0,    0,   88,
   -4,    0,   32,    0,    0,    0,   74,   54,    0,    0,
   79,  235,   44,  254,    0,  259,    0,    2,  262,    0,
  -79,   47,  266,  215,  288,  -79,  297,  106,    0,  -79,
  -27,   81,    0,  -27,   45,  -43,   54,  -27,  -27,   78,
  -27,  -27,  -27,  -27,  -27,  -27,  144,    0,    0,    0,
  298,  299,  -57,    0,  -15,  303,  -79,  -79,  311,  313,
  314,  320,    0,   79,    0,   54,    0,   54,    0,    0,
  144,    0,    0,    0,    0,    0,    0, -225,  141,  -57,
  -79,   85,    0,  -57,  325,  327,  -57,    0,  -27,    0,
  186,  144,    0,  144,    0,  144,    0,  119,    0,   85,
  -57,  -57,  170,    0,   98,  151,  100,  178,    0,  339,
  340,  343,    0,  105,  119,    0,    0,  328,  329,  333,
  334,    0,   85,  170,  119,    0,    0,    0,    0,    0,
    0,    0,  -27,  -27,  -27,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  186,  345,  356,   73,  139,   84,
    0,   84, -225,  141,  129,  133,  135,    0,  -36,    0,
  186,    0,    0,    0, -205,    0, -205,  157,  159,  160,
  161,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   70,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    1,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  351,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  123,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    5,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  364,    0,    0,    0,    0,    0,    0,    0,    0,  351,
  -35,    0,    0,    0,    0,    0,   40,   -6,    0,    0,
  380,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  322,    0,    0,  351,    0,    0,
    0,    0,    0,    0,    0,    0,   18,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  399,    0,    0,    0,    0,    0,    0,    0,    0,
  401,  -28,    0,   53,    0,   26,    0,   48,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  167,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -56,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  167,    0,  167,    0,    0,
    0,    0,    0,    0,    0,    0, -104,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  110,    0,    0,
    0,    0,    0,    0,  -19,    0,    0,    0,    0,    0,
    0,    0,    0,  167,    0,  174,    0,    0,    0,    0,
  -48,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  179,   12,    0,  -41,  190,  -24,    0,    0,    0,    0,
  117,    0,    0,  147,   99,    0,    0,    0,    0,    0,
  309,   14,  -88,  156,    0,    0, -137,  -82,    0,    0,
 -183,    0,  -26,  302,  -25,  330,  265,    8,  -13,    0,
   30,   75,    0,
};
final static int YYTABLESIZE=483;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         62,
   12,   93,   87,   65,   11,  110,  110,  110,  110,  110,
   43,  110,  111,  111,  111,  111,  111,   93,  111,  104,
   82,   26,  231,  110,  110,  110,  110,   59,   41,   93,
  111,  111,  111,  111,  103,  121,  103,  103,  103,   12,
   18,  122,  139,  110,  125,  101,  126,  115,   38,  108,
  182,  183,  103,  103,  103,  103,  249,   93,  100,   12,
  100,  100,  100,   11,   19,   96,   98,  227,   98,   98,
   98,  259,  188,  261,   39,  202,  100,  100,  100,  100,
   96,    1,    2,   96,   98,   98,   98,   98,   99,   93,
   99,   99,   99,   97,   20,  129,   97,   34,   96,   96,
  128,   96,  247,  135,  253,  136,   99,   99,   99,   99,
  154,   97,   97,  241,   97,  125,  125,  126,  126,  115,
   35,  125,  228,  126,  141,  234,  127,  120,  151,  146,
  127,   70,   42,  149,  124,   36,   49,  127,  162,  163,
  164,  165,  166,  167,   65,  148,   83,   37,  168,   70,
   85,  245,   44,  252,  156,  158,   65,  248,   44,  248,
  175,  176,   77,   43,   49,   60,   50,   61,   49,   63,
   44,   65,  180,   65,   41,   44,   44,    4,    5,    6,
   17,  236,  237,  127,  187,   64,  151,   80,   81,   33,
   62,   66,   78,  195,   50,  197,   67,  199,   50,    4,
    5,    6,  159,  160,   68,   70,   69,    7,    8,   71,
   65,  238,  157,   91,   72,   73,   74,   92,  123,   65,
   84,  110,   12,   86,    9,  110,   65,   75,  111,   91,
   12,   87,  111,   92,  110,  110,  110,  110,   88,  257,
   58,  111,  111,  111,  111,   92,   89,   90,  105,   81,
  103,  243,  111,  250,  103,  116,   12,   12,   91,   12,
  117,   43,   92,  103,  103,  103,  103,  127,  106,   91,
  119,  118,  107,   92,  100,  130,   12,   12,  100,   12,
   11,   11,   98,    4,    5,    6,   98,  100,  100,  100,
  100,   76,    8,  123,  137,   98,   98,   98,   98,  138,
  155,   91,  140,  142,   99,   92,  143,  144,   99,   96,
   96,   96,   96,  131,  132,  133,  134,   99,   99,   99,
   99,  172,   97,   97,   97,   97,  169,  189,   57,   91,
   91,  145,   18,   92,   92,   91,  147,  152,  170,   92,
   21,  161,  171,  174,   18,  212,  200,   18,  186,   18,
  181,  177,  190,  178,   79,  193,   57,  179,  201,  121,
   57,  215,  188,   25,  202,  191,   44,  192,  233,  213,
  214,  196,   44,  198,  219,   21,  221,  203,  223,  224,
  112,  200,  225,  226,   44,  239,  229,  230,   42,   44,
   44,  231,  232,  201,  203,  246,  240,   21,   25,  202,
   21,   26,  242,   22,  235,  254,   22,   21,  255,   65,
  258,  256,  260,   22,   68,   23,  184,  185,   23,  112,
   25,   24,   66,   25,  112,   23,   21,  220,  112,  244,
   25,  251,   22,  262,   21,  263,  264,  265,   95,   75,
   22,   78,   21,  194,   23,   12,  173,  215,   22,   25,
   36,  153,   23,    0,  222,  112,  112,   25,    0,    0,
   23,    4,    5,    6,    0,   25,    4,    5,    6,   44,
    8,   45,   46,   47,   44,    8,   45,   46,   47,  112,
    0,    0,   48,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         24,
    0,   45,   59,   28,    0,   41,   42,   43,   44,   45,
   59,   47,   41,   42,   43,   44,   45,   45,   47,   61,
   47,   10,   59,   59,   60,   61,   62,   61,   17,   45,
   59,   60,   61,   62,   41,   40,   43,   44,   45,   59,
  283,   46,   41,   70,   43,   59,   45,   73,  257,   63,
  276,  277,   59,   60,   61,   62,  240,   45,   41,   59,
   43,   44,   45,   59,  257,   58,   41,  205,   43,   44,
   45,  255,  278,  257,  283,  281,   59,   60,   61,   62,
   41,  256,  257,   44,   59,   60,   61,   62,   41,   45,
   43,   44,   45,   41,  257,   42,   44,   59,   59,   60,
   47,   62,  240,   60,  242,   62,   59,   60,   61,   62,
  124,   59,   60,   41,   62,   43,   43,   45,   45,  145,
   59,   43,  205,   45,  111,  214,   97,   40,  121,  116,
  101,   44,  257,  120,   61,   59,   20,  108,  131,  132,
  133,  134,  135,  136,  169,   40,   48,   59,  137,   44,
   52,  240,  257,  242,  125,  126,  181,  240,  263,  242,
  147,  148,   46,   61,   48,   40,   20,   40,   52,   40,
  275,  196,  161,  198,  279,  280,  281,  257,  258,  259,
    2,  223,  224,  154,  171,   59,  179,  256,  257,   11,
  215,  279,   46,  182,   48,  184,   59,  186,   52,  257,
  258,  259,  128,  129,   59,   44,   59,  265,  266,  257,
  235,  225,  256,  257,   40,   91,  257,  261,  262,  244,
  279,  257,  279,   59,  282,  261,  251,  257,  257,  257,
  279,   59,  261,  261,  270,  271,  272,  273,   59,  276,
  274,  270,  271,  272,  273,  261,   59,  257,  279,  257,
  257,  240,   40,  242,  261,   40,  276,  277,  257,  279,
  257,  281,  261,  270,  271,  272,  273,  238,  256,  257,
  279,  257,  260,  261,  257,   41,  276,  277,  261,  279,
  276,  277,  257,  257,  258,  259,  261,  270,  271,  272,
  273,  265,  266,  262,   41,  270,  271,  272,  273,   41,
  256,  257,   41,  257,  257,  261,   41,   93,  261,  270,
  271,  272,  273,  270,  271,  272,  273,  270,  271,  272,
  273,  143,  270,  271,  272,  273,  137,  172,   20,  257,
  257,   44,  263,  261,  261,  257,   40,  257,   41,  261,
  257,  264,   44,   41,  275,  190,  263,  278,  170,  280,
  161,   41,  174,   41,   46,  177,   48,   44,  275,   40,
   52,  278,  278,  280,  281,   41,  257,   41,  213,  191,
  192,  182,  263,  184,  277,  257,  277,  188,   40,   40,
   72,  263,   40,  279,  275,   41,   59,   59,  279,  280,
  281,   59,   59,  275,  205,  240,   41,  257,  280,  281,
  257,  279,  264,  263,  215,  277,  263,  257,  276,   59,
  255,  277,  257,  263,   93,  275,  276,  277,  275,  111,
  280,  278,   59,  280,  116,  275,  257,  277,  120,  240,
  280,  242,  263,  277,  257,  277,  277,  277,   59,   41,
  263,   41,  257,  179,  275,  279,  145,  278,  263,  280,
  277,  122,  275,   -1,  277,  147,  148,  280,   -1,   -1,
  275,  257,  258,  259,   -1,  280,  257,  258,  259,  265,
  266,  267,  268,  269,  265,  266,  267,  268,  269,  171,
   -1,   -1,  278,
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
"cte : '-' CTE_DOUBLE",
};

//#line 240 "src/gramatica.y"

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
//#line 527 "Parser.java"
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
case 24:
//#line 51 "src/gramatica.y"
{System.out.println("Soy una clase");}
break;
case 25:
//#line 52 "src/gramatica.y"
{System.out.println("Soy una clase");}
break;
case 47:
//#line 93 "src/gramatica.y"
{System.out.println("Soy una sent retornable");}
break;
case 48:
//#line 94 "src/gramatica.y"
{System.out.println("Soy una sent retornable ");}
break;
case 49:
//#line 97 "src/gramatica.y"
{System.out.println("Soy un if retornable");}
break;
case 50:
//#line 98 "src/gramatica.y"
{System.out.println("Soy un if retornable");}
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
//#line 104 "src/gramatica.y"
{System.out.println("Es un IF");}
break;
case 54:
//#line 105 "src/gramatica.y"
{System.out.println("Es un IF");}
break;
case 55:
//#line 108 "src/gramatica.y"
{System.out.println("Es un WHILE");}
break;
case 56:
//#line 109 "src/gramatica.y"
{System.out.println("Es un WHILE");}
break;
case 60:
//#line 115 "src/gramatica.y"
{System.out.println("estamos permitiendo declaracion multiple de atributos");}
break;
case 63:
//#line 123 "src/gramatica.y"
{yyerror("Error sintactico: ausencia de nombre o lista de clases");}
break;
case 73:
//#line 146 "src/gramatica.y"
{System.out.println("Soy una funcion");}
break;
case 74:
//#line 147 "src/gramatica.y"
{System.out.println("Soy una funcion auto");}
break;
case 81:
//#line 164 "src/gramatica.y"
{System.out.println("Es una cadena");}
break;
case 83:
//#line 168 "src/gramatica.y"
{System.out.println("es una cadena que se imprime");}
break;
case 84:
//#line 169 "src/gramatica.y"
{yyerror("Error: falta argumento en sentencia POUT.");}
break;
case 86:
//#line 176 "src/gramatica.y"
{System.out.println("Es un WHILE");}
break;
case 87:
//#line 177 "src/gramatica.y"
{System.out.println("Es un WHILE");}
break;
case 88:
//#line 180 "src/gramatica.y"
{System.out.println("Es una comparacion <");}
break;
case 89:
//#line 181 "src/gramatica.y"
{System.out.println("Es una comparacion >");}
break;
case 90:
//#line 182 "src/gramatica.y"
{System.out.println("Es una comparacion >=");}
break;
case 91:
//#line 183 "src/gramatica.y"
{System.out.println("Es una comparacion <=");}
break;
case 92:
//#line 184 "src/gramatica.y"
{System.out.println("Es una desigualdad");}
break;
case 93:
//#line 185 "src/gramatica.y"
{System.out.println("Es una igualdad");}
break;
case 94:
//#line 191 "src/gramatica.y"
{System.out.println("Es una asignación con := ");}
break;
case 95:
//#line 193 "src/gramatica.y"
{System.out.println("Es una asignación con = ");}
break;
case 97:
//#line 198 "src/gramatica.y"
{System.out.println("Es una asignacion de expr");}
break;
case 98:
//#line 203 "src/gramatica.y"
{ System.out.println("Es una suma de los valores: " + val_peek(2)+ " y " + val_peek(1));}
break;
case 99:
//#line 205 "src/gramatica.y"
{ System.out.println("Es una resta de los valores: " + val_peek(2)+ " y " + val_peek(1));}
break;
case 100:
//#line 206 "src/gramatica.y"
{yyerror("Error sintactico: Falta operador en expresión");}
break;
case 101:
//#line 207 "src/gramatica.y"
{ yyerror("Error sintactico: Falta operando en expr");}
break;
case 102:
//#line 208 "src/gramatica.y"
{ yyerror("Error sintactico: Falta operando en expr");}
break;
case 104:
//#line 215 "src/gramatica.y"
{ System.out.println("Es una división de los valores: " +val_peek(2)+ " y " + val_peek(1));}
break;
case 105:
//#line 217 "src/gramatica.y"
{ System.out.println("Es una multiplicación de los valores: " + val_peek(2)+ " y " + val_peek(1));}
break;
case 111:
//#line 228 "src/gramatica.y"
{System.out.println("Es un atributo");}
break;
case 112:
//#line 229 "src/gramatica.y"
{System.out.println("Es un acceso a metodo");}
break;
case 114:
//#line 234 "src/gramatica.y"
{
                              System.out.println("Es una constante negativa");
                              
                         }
break;
//#line 855 "Parser.java"
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
