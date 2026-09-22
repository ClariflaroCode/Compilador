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
   11,   11,   33,   33,   34,   34,   21,   21,   21,   12,
   12,   22,   22,   36,   37,   37,   31,   40,    9,    9,
    9,   14,    8,    8,    4,    4,    4,    4,    4,    4,
    7,    7,   38,   38,   39,   39,   39,   41,   41,   41,
   42,   42,   42,   43,   43,   43,   35,   35,
};
final static short yylen[] = {                            2,
    3,    3,    6,    8,    6,    8,    8,    8,    3,    1,
    2,    2,    2,    2,    2,    1,    2,    2,    2,    2,
    2,    5,    4,    1,    2,    2,    2,    2,    2,    8,
    8,    7,    7,    1,    1,    3,    1,    2,    1,    2,
    1,    1,    2,    2,    2,    2,    8,    8,    8,    8,
    6,    6,    6,    6,    1,    1,    2,    2,    2,    2,
    2,    2,    1,    3,    1,    3,    1,    1,    1,    8,
    8,    2,    4,    4,    1,    3,    4,    1,    4,    4,
    4,    6,    6,    6,    3,    3,    3,    3,    3,    3,
    3,    3,    1,    3,    3,    3,    1,    3,    3,    1,
    1,    1,    1,    1,    3,    3,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   69,   67,   68,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    2,   17,   18,   19,   20,   21,    0,    0,   61,
    1,    0,    0,    0,    0,    0,    0,    0,   55,   56,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   59,    0,   58,   57,    0,    0,   60,    0,
   23,   25,   26,   27,   28,   29,    0,    0,    0,    0,
    0,    0,   13,   11,    9,   12,   14,   15,   64,    0,
    0,    0,  107,  108,    0,    0,    0,    0,    0,   22,
    0,    0,  102,  103,   91,    0,    0,  100,  101,    0,
    0,    0,    0,    0,   78,    0,    0,    0,    0,    0,
   82,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   81,   79,   80,    0,    0,    0,   66,    0,    0,
    0,    0,    0,    0,    0,  106,    0,    0,    0,   98,
   99,    0,   87,   88,   89,   90,   85,   86,    0,    0,
    0,   73,    0,   71,    0,    0,    0,    0,   74,    0,
   83,   84,    0,    3,    0,    5,   70,    0,    0,    0,
   41,    0,    0,   37,    0,    0,    0,    0,    0,   33,
    0,    0,    0,   35,   32,   34,   76,    0,    0,    0,
    0,    0,    0,    0,   36,   38,    0,   43,   44,   45,
   46,   31,   30,    0,    4,    7,    8,    6,    0,    0,
    0,    0,    0,   77,    0,    0,    0,    0,    0,    0,
   42,    0,   35,   41,   53,   54,   51,    0,   52,    0,
    0,    0,    0,    0,   47,   49,   50,   48,
};
final static short yydgoto[] = {                          3,
   10,  204,   52,  111,   53,   54,   55,   56,   57,   11,
   12,   13,   14,   15,   41,   42,   43,   44,   45,   46,
   16,   92,  205,  206,  192,  193,  194,  195,  196,  197,
  198,  199,   30,   95,  103,  104,  153,  112,  106,  117,
  107,  108,  109,
};
final static short yysindex[] = {                      -182,
  -96,  -96,    0,    0,    0,    0, -259, -168, -144, -154,
  -96,   71,   76,  100,  105, -201, -154,  -73,  131,   29,
   28,    0,    0,    0,    0,    0,    0,  165,  -42,    0,
    0,  178,  155,   -8,   -4,  -53,    5,   47,    0,    0,
  -12,   47,  242,  243,  250,  251,   15,  -26,  271,  277,
  278,  260,   28,   42,  261,  263,  264,    5,  284,    2,
  -61,  285,    0,   69,    0,    0,   70,  165,    0,   49,
    0,    0,    0,    0,    0,    0,  163, -157, -157, -157,
 -157,  -85,    0,    0,    0,    0,    0,    0,    0,    2,
   72,  289,    0,    0,  238,  288,    2,  293,  180,    0,
    2,   44,    0,    0,    0,   89,   56,    0,    0,  104,
  294,   11,  295,  296,    0,   84,  297,  298,  290,  -96,
    0,  -61,  299,    2,    2,  300, -157,   85, -157, -157,
 -157, -157, -157,   79, -157, -157, -157, -157, -157, -157,
  -23,    0,    0,    0,  -96,    2,   66,    0,  -96,  304,
  305,  -96,  306,  307,  308,    0,  104,   56,   56,    0,
    0,  -23,    0,    0,    0,    0,    0,    0,  -49,   13,
 -154,    0, -124,    0,   66,  -96,  -96,   75,    0, -157,
    0,    0,  -23,    0,  -23,    0,    0,  309,  310,  312,
    0,   77, -124,    0,    0,  301,  302,  303,  311,    0,
   66,   75, -124,    0,    0,    0,    0,   78,   80,   81,
   82, -157, -157, -157,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   28,    0,    0,    0,    0,  313,  322,
  213,  101,  -64,    0,  -64,  -49,   13,   87,   90,   91,
    0,  -54,    0,    0,    0,    0,    0,  -99,    0,  -99,
   92,   94,   95,   96,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   97,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  315,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   88,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   98,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  317,    0,    0,
    0,    0,    0,    0,    0,    0,  315,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  286,    0,    0,  315,    0,
    0,  -41,    0,    0,    0,    3,  -29,    0,    0,  319,
    0,    0,    0,    0,    0,    0,    0,    0,  339,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  340,  -34,    0,    7,   -7,   -2,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -135,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -90,    0,    0,    0,
    0,    0,    0,  -46,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  106,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   74,   12,    0,  -58,  -25,  -36,    0,    0,    0,    0,
   50,    0,    0,  160,  183,    0,    0,    0,    0,    0,
   41,  -10, -138, -107,    0,    0, -115, -112,    0,    0,
 -131,    0,   35,  262,  -16,  254,  205,  -28,  -33,    0,
  162,  167,    0,
};
final static int YYTABLESIZE=385;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        104,
  104,  104,  104,  104,  220,  104,  105,  105,  105,  105,
  105,   97,  105,   97,   97,   97,   84,  104,  104,  104,
  104,   22,  113,   18,  105,  105,  105,  105,   31,   97,
   97,   97,   97,   95,   79,   95,   95,   95,   96,  174,
   96,   96,   96,   93,   96,  110,   93,   94,  116,  105,
   94,   95,   95,   95,   95,   28,   96,   96,   96,   96,
   47,   93,   93,  223,   93,   94,   94,  200,   94,   39,
  139,   69,  140,    1,    2,   17,   67,  216,   47,  118,
  217,   29,   47,  127,   23,   65,  123,   39,   19,  128,
  126,   39,   89,  222,  238,  157,  245,  133,  154,  102,
   91,  242,  132,   93,   94,   96,  163,  164,  165,  166,
  167,  168,   20,  150,  151,  170,  252,  240,  254,  246,
  241,   42,  241,   21,  143,  239,  130,   42,  131,   24,
   91,  130,   48,  131,   25,  172,  182,   91,  188,   42,
  251,   91,  253,   39,   42,   42,  130,  191,  131,  129,
  189,  154,  169,  229,  230,   51,  190,  209,   26,  211,
    4,    5,    6,   27,   91,   91,   42,  191,    7,    8,
  114,  102,   42,  181,  115,   93,   94,  224,  173,   40,
  231,  190,  187,   32,   42,    9,   91,   84,   40,   42,
   42,   33,   48,  147,  208,   66,  210,   40,  188,   93,
   94,   40,  101,    4,    5,    6,   58,  237,   58,  244,
  189,   64,    8,  203,   59,   51,  190,   60,  171,  125,
   70,  250,  175,   58,   72,  178,  183,  184,  104,  104,
  104,  104,   10,   48,   41,  105,  105,  105,  105,   49,
   97,   97,   97,   97,  236,   61,  243,   78,   62,  201,
  202,   50,   63,  234,   21,  130,   51,  131,    4,    5,
    6,   68,   95,   95,   95,   95,   71,   96,   96,   96,
   96,   77,   93,   93,   93,   93,   94,   94,   94,   94,
  135,  136,  137,  138,   48,    4,    5,    6,  185,  186,
   49,  158,  159,   34,    8,   35,   36,   37,  160,  161,
   73,   74,   50,    4,    5,    6,   38,   51,   75,   76,
   80,   34,    8,   35,   36,   37,   81,   82,   83,   86,
   85,   87,   88,   90,   97,   98,   99,  100,  119,  120,
  121,  122,  124,  146,  134,  141,  142,  144,  145,  149,
  152,  155,  162,  173,  176,  177,  179,  127,  212,  213,
  180,  214,  203,  232,  225,  215,  226,  227,  228,  218,
  219,  220,  233,  247,  235,  248,   24,  249,  255,  221,
  256,  257,  258,   62,   16,   63,   10,   92,   65,   72,
   75,  156,   34,  148,  207,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   59,   47,   41,   42,   43,   44,
   45,   41,   47,   43,   44,   45,   53,   59,   60,   61,
   62,   10,   81,  283,   59,   60,   61,   62,   17,   59,
   60,   61,   62,   41,   61,   43,   44,   45,   41,  147,
   43,   44,   45,   41,   61,   79,   44,   41,   82,   78,
   44,   59,   60,   61,   62,  257,   59,   60,   61,   62,
   20,   59,   60,  202,   62,   59,   60,  175,   62,   20,
   60,   37,   62,  256,  257,    2,   36,  193,   38,   90,
  193,  283,   42,   40,   11,   36,   97,   38,  257,   46,
  101,   42,   58,  201,  233,  129,  235,   42,  127,  257,
   60,  233,   47,  261,  262,  122,  135,  136,  137,  138,
  139,  140,  257,  124,  125,  141,  248,  233,  250,  235,
  233,  257,  235,  278,   41,  233,   43,  263,   45,   59,
   90,   43,  257,   45,   59,  146,  162,   97,  263,  275,
  248,  101,  250,  279,  280,  281,   43,  173,   45,   61,
  275,  180,  141,  212,  213,  280,  281,  183,   59,  185,
  257,  258,  259,   59,  124,  125,  257,  193,  265,  266,
  256,  257,  263,  162,  260,  261,  262,  203,  278,   20,
  214,  281,  171,  257,  275,  282,  146,  224,  279,  280,
  281,   61,  257,  120,  183,   36,  185,   38,  263,  261,
  262,   42,   40,  257,  258,  259,   44,  233,   44,  235,
  275,  265,  266,  278,  257,  280,  281,   40,  145,   40,
   38,  276,  149,   44,   42,  152,  276,  277,  270,  271,
  272,  273,  279,  257,  281,  270,  271,  272,  273,  263,
  270,  271,  272,  273,  233,   91,  235,  274,  257,  176,
  177,  275,  257,   41,  278,   43,  280,   45,  257,  258,
  259,  257,  270,  271,  272,  273,  279,  270,  271,  272,
  273,  257,  270,  271,  272,  273,  270,  271,  272,  273,
  270,  271,  272,  273,  257,  257,  258,  259,  276,  277,
  263,  130,  131,  265,  266,  267,  268,  269,  132,  133,
   59,   59,  275,  257,  258,  259,  278,  280,   59,   59,
   40,  265,  266,  267,  268,  269,   40,   40,   59,   59,
  279,   59,   59,   40,   40,  257,  257,  279,  257,   41,
   93,   44,   40,   44,   41,   41,   41,   41,   41,   41,
   41,  257,  264,  278,   41,   41,   41,   40,   40,   40,
   44,   40,  278,   41,  277,  279,  277,  277,  277,   59,
   59,   59,   41,  277,  264,  276,  279,  277,  277,   59,
  277,  277,  277,   59,  278,   59,  279,   59,   93,   41,
   41,  128,  277,  122,  180,
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

//#line 231 "src/gramatica.y"

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
//#line 496 "Parser.java"
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
case 78:
//#line 160 "src/gramatica.y"
{System.out.println("Es una cadena");}
break;
case 80:
//#line 164 "src/gramatica.y"
{System.out.println("es una cadena que se imprime");}
break;
case 81:
//#line 165 "src/gramatica.y"
{yyerror("Error: falta argumento en sentencia POUT.");}
break;
case 85:
//#line 176 "src/gramatica.y"
{System.out.println("Es una comparacion <");}
break;
case 86:
//#line 177 "src/gramatica.y"
{System.out.println("Es una comparacion >");}
break;
case 87:
//#line 178 "src/gramatica.y"
{System.out.println("Es una comparacion >=");}
break;
case 88:
//#line 179 "src/gramatica.y"
{System.out.println("Es una comparacion <=");}
break;
case 89:
//#line 180 "src/gramatica.y"
{System.out.println("Es una desigualdad");}
break;
case 90:
//#line 181 "src/gramatica.y"
{System.out.println("Es una igualdad");}
break;
case 91:
//#line 187 "src/gramatica.y"
{System.out.println("Es una asignación con := ");}
break;
case 92:
//#line 189 "src/gramatica.y"
{System.out.println("Es una asignación con = ");}
break;
case 93:
//#line 193 "src/gramatica.y"
{System.out.println("Es una expr");}
break;
case 94:
//#line 194 "src/gramatica.y"
{System.out.println("Es una asignacion de expr");}
break;
case 95:
//#line 199 "src/gramatica.y"
{ System.out.println("Es una suma de los valores: " + val_peek(2)+ " y " + val_peek(1));}
break;
case 96:
//#line 201 "src/gramatica.y"
{ System.out.println("Es una resta de los valores: " + val_peek(2)+ " y " + val_peek(1));}
break;
case 97:
//#line 203 "src/gramatica.y"
{ System.out.println("Es un término");}
break;
case 98:
//#line 208 "src/gramatica.y"
{ System.out.println("Es una división de los valores: " +val_peek(2)+ " y " + val_peek(1));}
break;
case 99:
//#line 210 "src/gramatica.y"
{ System.out.println("Es una multiplicación de los valores: " + val_peek(2)+ " y " + val_peek(1));}
break;
case 100:
//#line 212 "src/gramatica.y"
{ System.out.println("Es un factor");}
break;
case 101:
//#line 217 "src/gramatica.y"
{ System.out.println("Es un factor");}
break;
case 102:
//#line 218 "src/gramatica.y"
{System.out.println("Es un cte factor");}
break;
case 103:
//#line 219 "src/gramatica.y"
{System.out.println("Es una invocacion_funcion factor");}
break;
case 104:
//#line 222 "src/gramatica.y"
{System.out.println("Es un variable");}
break;
case 105:
//#line 223 "src/gramatica.y"
{System.out.println("Es un atributo");}
break;
case 106:
//#line 224 "src/gramatica.y"
{System.out.println("Es un acceso a metodo");}
break;
case 107:
//#line 227 "src/gramatica.y"
{System.out.println("Detecte un entero");}
break;
case 108:
//#line 228 "src/gramatica.y"
{System.out.println("Es un double");}
break;
//#line 821 "Parser.java"
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
