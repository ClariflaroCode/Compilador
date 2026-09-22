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
    5,    5,    5,    5,    1,    1,   10,   10,   10,   10,
   13,   13,   15,   15,   16,   16,   16,   16,   17,   17,
   17,   17,   23,   23,   24,   26,   26,   25,   25,   27,
   27,   27,   27,   28,   28,   32,   32,   32,   32,   29,
   29,   30,   30,   18,   18,   18,   18,   19,   20,   11,
   11,   33,   33,   34,   34,   21,   21,   21,   12,   12,
   22,   22,   36,   37,   37,   31,   40,    9,    9,   14,
    8,    8,    4,    4,    4,    4,    4,    4,    7,    7,
   38,   38,   39,   39,   39,   41,   41,   41,   42,   42,
   42,   43,   43,   43,   35,   35,
};
final static short yylen[] = {                            2,
    3,    6,    8,    6,    8,    8,    8,    3,    1,    2,
    2,    2,    2,    2,    1,    2,    2,    2,    2,    2,
    5,    4,    1,    2,    2,    2,    2,    2,    8,    8,
    7,    7,    1,    1,    3,    1,    2,    1,    2,    1,
    1,    2,    2,    2,    2,    8,    8,    8,    8,    6,
    6,    6,    6,    1,    1,    2,    2,    2,    2,    2,
    2,    1,    3,    1,    3,    1,    1,    1,    8,    8,
    2,    4,    4,    1,    3,    4,    1,    4,    4,    6,
    6,    6,    3,    3,    3,    3,    3,    3,    3,    3,
    1,    3,    3,    3,    1,    3,    3,    1,    1,    1,
    1,    1,    3,    3,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,   68,   66,   67,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    1,
   16,   17,   18,   19,   20,    0,    0,   60,    0,    0,
    0,    0,    0,    0,    0,   54,   55,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   58,
    0,   57,   56,    0,    0,   59,    0,   22,   24,   25,
   26,   27,   28,    0,    0,    0,    0,    0,    0,   12,
   10,    8,   11,   13,   14,   63,    0,    0,    0,  105,
  106,    0,    0,    0,    0,    0,   21,    0,    0,  100,
  101,   89,    0,    0,   98,   99,    0,    0,    0,    0,
   77,    0,    0,    0,    0,    0,   80,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   78,   79,    0,
    0,    0,   65,    0,    0,    0,    0,    0,    0,    0,
  104,    0,    0,    0,   96,   97,    0,   85,   86,   87,
   88,   83,   84,    0,    0,    0,   72,    0,   70,    0,
    0,    0,    0,   73,    0,   81,   82,    0,    2,    0,
    4,   69,    0,    0,    0,   40,    0,    0,   36,    0,
    0,    0,    0,    0,   32,    0,    0,    0,   34,   31,
   33,   75,    0,    0,    0,    0,    0,    0,    0,   35,
   37,    0,   42,   43,   44,   45,   30,   29,    0,    3,
    6,    7,    5,    0,    0,    0,    0,    0,   76,    0,
    0,    0,    0,    0,    0,   41,    0,   34,   40,   52,
   53,   50,    0,   51,    0,    0,    0,    0,    0,   46,
   48,   49,   47,
};
final static short yydgoto[] = {                          2,
    9,  199,   49,  108,   50,   51,   52,   53,   54,   10,
   11,   12,   13,   14,   38,   39,   40,   41,   42,   43,
   15,   89,  200,  201,  187,  188,  189,  190,  191,  192,
  193,  194,   28,   92,  100,  101,  148,  109,  103,  113,
  104,  105,  106,
};
final static short yysindex[] = {                      -228,
 -139,    0,    0,    0,    0, -219, -178, -171, -176, -139,
   54,   69,  119,  135, -196,  -54,  150,  -11, -141,    0,
    0,    0,    0,    0,    0,  189,  -12,    0,  200,  159,
    5,   15,  -50,   43,   29,    0,    0,   23,   29,  193,
  248,  250,  251,   55,  -39,  271,  273,  274,  256, -141,
   37,  258,  259,  260,   43,  280,    2,  -93,  281,    0,
   65,    0,    0,   66,  189,    0,   45,    0,    0,    0,
    0,    0,    0,   38, -126, -126, -126, -126,  -40,    0,
    0,    0,    0,    0,    0,    0,    2,   68,  285,    0,
    0,  234,  284,    2,  289,   81,    0,    2,   34,    0,
    0,    0,  106,  108,    0,    0,  154,  290,   11,  291,
    0,  169,  292,  293,  286, -139,    0,  -93,  294,    2,
    2,  295, -126,   80, -126, -126, -126, -126, -126,   74,
 -126, -126, -126, -126, -126, -126,   28,    0,    0, -139,
    2,   61,    0, -139,  299,  300, -139,  301,  302,  303,
    0,  154,  108,  108,    0,    0,   28,    0,    0,    0,
    0,    0,    0,  -49,   13, -176,    0,  -62,    0,   61,
 -139, -139,   67,    0, -126,    0,    0,   28,    0,   28,
    0,    0,  304,  307,  308,    0,   70,  -62,    0,    0,
  296,  297,  298,  305,    0,   61,   67,  -62,    0,    0,
    0,    0,   73,   75,   77,   82, -126, -126, -126,    0,
    0,    0,    0,    0,    0,    0,    0,    0, -141,    0,
    0,    0,    0,  310,  312,  208,   94,  -91,    0,  -91,
  -49,   13,   84,   86,   88,    0,  -42,    0,    0,    0,
    0,    0, -101,    0, -101,   89,   90,   91,   92,    0,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   85,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  311,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   93,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   95,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  314,    0,    0,    0,    0,    0,
    0,    0,    0,  311,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  267,    0,    0,  311,    0,    0,  -41,    0,
    0,    0,    3,  -29,    0,    0,  316,    0,    0,    0,
    0,    0,    0,    0,  330,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  335,  -34,
    0,    7,   -7,   -2,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -134,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -75,    0,    0,    0,    0,    0,    0,   20,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  100,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
   14,   -4,    0,  -43,   -5,  -27,    0,    0,    0,    0,
   76,    0,    0,  163,  121,    0,    0,    0,    0,    0,
   50,  -17, -122,  -86,    0,    0, -138, -116,    0,    0,
 -152,    0,   83,  261,  -13,  254,  205,  -35,  -30,    0,
  166,  176,    0,
};
final static int YYTABLESIZE=380;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        102,
  102,  102,  102,  102,   20,  102,  103,  103,  103,  103,
  103,   95,  103,   95,   95,   95,  215,  102,  102,  102,
  102,   76,   81,   21,  103,  103,  103,  103,    1,   95,
   95,   95,   95,   93,  110,   93,   93,   93,   94,  102,
   94,   94,   94,   91,   93,  107,   91,   92,  112,  211,
   92,   93,   93,   93,   93,  169,   94,   94,   94,   94,
   26,   91,   91,   16,   91,   92,   92,   44,   92,  114,
  135,  212,  136,  123,  218,  237,  119,   98,   17,  124,
  122,   55,   64,  195,   44,   18,   27,  149,   44,  235,
  247,  241,  249,   36,  152,  158,  159,  160,  161,  162,
  163,   19,  145,  146,   93,  233,   88,  240,   62,  217,
   36,  236,   22,  236,   36,   45,   66,    3,    4,    5,
  121,   46,   41,  167,   55,    6,    7,   23,   41,  142,
   99,  165,  164,   47,   90,   91,   88,   86,   48,  149,
   41,  234,    8,   88,   38,   41,   41,   88,  126,  129,
  127,  177,  176,  166,  128,   67,  246,  170,  248,   69,
  173,  182,  186,  224,  225,   45,  125,   90,   91,   88,
   88,  183,  204,  203,  206,  205,  168,   24,  226,  185,
   37,   41,  186,  184,  196,  197,  198,   41,   48,  185,
   88,   81,  219,   25,   45,   63,  126,   37,  127,   41,
  183,   37,   29,   39,   41,   41,    3,    4,    5,  138,
   30,  126,  184,  127,   61,    7,   99,   48,  185,  111,
   90,   91,  232,  231,  239,  238,  178,  179,  102,  102,
  102,  102,   55,  245,   75,  103,  103,  103,  103,   57,
   95,   95,   95,   95,   56,    3,    4,    5,  229,   58,
  126,   70,  127,   31,    7,   32,   33,   34,    3,    4,
    5,   59,   93,   93,   93,   93,   35,   94,   94,   94,
   94,   60,   91,   91,   91,   91,   92,   92,   92,   92,
  131,  132,  133,  134,   45,    3,    4,    5,  180,  181,
   46,  153,  154,   31,    7,   32,   33,   34,    9,   65,
   40,   68,   47,  155,  156,   19,   71,   48,   72,   73,
   77,   74,   78,   79,   80,   82,   83,   84,   85,   87,
   94,   95,   96,   97,  115,  116,  117,  118,  120,  141,
  130,  137,  139,  140,  144,  147,  150,  157,  168,  171,
  172,  174,  123,  207,  198,  175,  208,  209,  210,  220,
  227,  221,  228,  222,  213,  214,  215,  230,  223,   64,
  242,  243,   15,  216,  244,  250,  251,  252,  253,   61,
   71,   23,   62,    9,   90,   74,   33,  151,  143,  202,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,    9,   47,   41,   42,   43,   44,
   45,   41,   47,   43,   44,   45,   59,   59,   60,   61,
   62,   61,   50,   10,   59,   60,   61,   62,  257,   59,
   60,   61,   62,   41,   78,   43,   44,   45,   41,   75,
   43,   44,   45,   41,   58,   76,   44,   41,   79,  188,
   44,   59,   60,   61,   62,  142,   59,   60,   61,   62,
  257,   59,   60,  283,   62,   59,   60,   18,   62,   87,
   60,  188,   62,   40,  197,  228,   94,   40,  257,   46,
   98,   44,   33,  170,   35,  257,  283,  123,   39,  228,
  243,  230,  245,   18,  125,  131,  132,  133,  134,  135,
  136,  278,  120,  121,  118,  228,   57,  230,   33,  196,
   35,  228,   59,  230,   39,  257,   34,  257,  258,  259,
   40,  263,  257,  141,   44,  265,  266,   59,  263,  116,
  257,  137,  137,  275,  261,  262,   87,   55,  280,  175,
  275,  228,  282,   94,  279,  280,  281,   98,   43,   42,
   45,  157,  157,  140,   47,   35,  243,  144,  245,   39,
  147,  166,  168,  207,  208,  257,   61,  261,  262,  120,
  121,  263,  178,  178,  180,  180,  278,   59,  209,  281,
   18,  257,  188,  275,  171,  172,  278,  263,  280,  281,
  141,  219,  198,   59,  257,   33,   43,   35,   45,  275,
  263,   39,  257,  279,  280,  281,  257,  258,  259,   41,
   61,   43,  275,   45,  265,  266,  257,  280,  281,  260,
  261,  262,  228,  228,  230,  230,  276,  277,  270,  271,
  272,  273,   44,  276,  274,  270,  271,  272,  273,   40,
  270,  271,  272,  273,  257,  257,  258,  259,   41,   91,
   43,   59,   45,  265,  266,  267,  268,  269,  257,  258,
  259,  257,  270,  271,  272,  273,  278,  270,  271,  272,
  273,  257,  270,  271,  272,  273,  270,  271,  272,  273,
  270,  271,  272,  273,  257,  257,  258,  259,  276,  277,
  263,  126,  127,  265,  266,  267,  268,  269,  279,  257,
  281,  279,  275,  128,  129,  278,   59,  280,   59,   59,
   40,  257,   40,   40,   59,  279,   59,   59,   59,   40,
   40,  257,  257,  279,  257,   41,   93,   44,   40,   44,
   41,   41,   41,   41,   41,   41,  257,  264,  278,   41,
   41,   41,   40,   40,  278,   44,   40,   40,  279,  277,
   41,  277,   41,  277,   59,   59,   59,  264,  277,   93,
  277,  276,  278,   59,  277,  277,  277,  277,  277,   59,
   41,  279,   59,  279,   59,   41,  277,  124,  118,  175,
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

//#line 229 "src/gramatica.y"

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
//#line 492 "Parser.java"
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
case 14:
//#line 34 "src/gramatica.y"
{System.out.println("Es una sentencia ejecutable");}
break;
case 15:
//#line 38 "src/gramatica.y"
{System.out.println("Es una sentencia declarativas");}
break;
case 16:
//#line 39 "src/gramatica.y"
{System.out.println("Es una sentencia declarativas");}
break;
case 17:
//#line 42 "src/gramatica.y"
{System.out.println("Es una sentencia declarativa");}
break;
case 44:
//#line 90 "src/gramatica.y"
{System.out.println("Soy una sent retornable");}
break;
case 45:
//#line 91 "src/gramatica.y"
{System.out.println("Soy una sent retornable ");}
break;
case 46:
//#line 94 "src/gramatica.y"
{System.out.println("Soy un if retornable");}
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
case 57:
//#line 111 "src/gramatica.y"
{System.out.println("estamos permitiendo declaracion multiple de atributos");}
break;
case 77:
//#line 159 "src/gramatica.y"
{System.out.println("Es una cadena");}
break;
case 79:
//#line 163 "src/gramatica.y"
{System.out.println("es una cadena que se imprime");}
break;
case 83:
//#line 174 "src/gramatica.y"
{System.out.println("Es una comparacion <");}
break;
case 84:
//#line 175 "src/gramatica.y"
{System.out.println("Es una comparacion >");}
break;
case 85:
//#line 176 "src/gramatica.y"
{System.out.println("Es una comparacion >=");}
break;
case 86:
//#line 177 "src/gramatica.y"
{System.out.println("Es una comparacion <=");}
break;
case 87:
//#line 178 "src/gramatica.y"
{System.out.println("Es una desigualdad");}
break;
case 88:
//#line 179 "src/gramatica.y"
{System.out.println("Es una igualdad");}
break;
case 89:
//#line 185 "src/gramatica.y"
{System.out.println("Es una asignación con := ");}
break;
case 90:
//#line 187 "src/gramatica.y"
{System.out.println("Es una asignación con = ");}
break;
case 91:
//#line 191 "src/gramatica.y"
{System.out.println("Es una expr");}
break;
case 92:
//#line 192 "src/gramatica.y"
{System.out.println("Es una asignacion de expr");}
break;
case 93:
//#line 197 "src/gramatica.y"
{ System.out.println("Es una suma de los valores: " + val_peek(2)+ " y " + val_peek(1));}
break;
case 94:
//#line 199 "src/gramatica.y"
{ System.out.println("Es una resta de los valores: " + val_peek(2)+ " y " + val_peek(1));}
break;
case 95:
//#line 201 "src/gramatica.y"
{ System.out.println("Es un término");}
break;
case 96:
//#line 206 "src/gramatica.y"
{ System.out.println("Es una división de los valores: " +val_peek(2)+ " y " + val_peek(1));}
break;
case 97:
//#line 208 "src/gramatica.y"
{ System.out.println("Es una multiplicación de los valores: " + val_peek(2)+ " y " + val_peek(1));}
break;
case 98:
//#line 210 "src/gramatica.y"
{ System.out.println("Es un factor");}
break;
case 99:
//#line 215 "src/gramatica.y"
{ System.out.println("Es un factor");}
break;
case 100:
//#line 216 "src/gramatica.y"
{System.out.println("Es un cte factor");}
break;
case 101:
//#line 217 "src/gramatica.y"
{System.out.println("Es una invocacion_funcion factor");}
break;
case 102:
//#line 220 "src/gramatica.y"
{System.out.println("Es un variable");}
break;
case 103:
//#line 221 "src/gramatica.y"
{System.out.println("Es un atributo");}
break;
case 104:
//#line 222 "src/gramatica.y"
{System.out.println("Es un acceso a metodo");}
break;
case 105:
//#line 225 "src/gramatica.y"
{System.out.println("Detecte un entero");}
break;
case 106:
//#line 226 "src/gramatica.y"
{System.out.println("Es un double");}
break;
//#line 809 "Parser.java"
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
