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
   41,   41,   41,   42,   42,   42,   43,   43,   43,   35,
   35,
};
final static short yylen[] = {                            2,
    3,    3,    6,    8,    6,    8,    8,    8,    3,    3,
    3,    1,    2,    2,    2,    2,    2,    1,    2,    2,
    2,    2,    2,    5,    4,    1,    2,    2,    2,    2,
    2,    8,    8,    7,    7,    1,    1,    3,    1,    2,
    1,    2,    1,    1,    2,    2,    2,    2,    8,    8,
    8,    8,    6,    6,    6,    6,    1,    1,    2,    2,
    2,    2,    2,    2,    2,    1,    3,    1,    3,    1,
    1,    1,    8,    8,    2,    4,    4,    1,    3,    4,
    1,    4,    4,    4,    6,    6,    6,    3,    3,    3,
    3,    3,    3,    3,    3,    1,    3,    3,    3,    1,
    3,    3,    1,    1,    1,    1,    1,    3,    3,    1,
    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   72,   70,   71,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    2,   19,   20,   21,   22,   23,    0,    0,
   64,    1,    0,    0,    0,    0,    0,    0,    0,   57,
   58,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   61,    0,   60,   59,    0,   63,
    0,   62,    0,   25,   27,   28,   29,   30,   31,    0,
    0,    0,    0,    0,    0,   15,   13,   10,   14,   16,
   17,   11,    9,   67,    0,    0,    0,  110,  111,    0,
    0,    0,    0,    0,   24,    0,    0,  105,  106,   94,
    0,    0,  103,  104,    0,    0,    0,    0,    0,   81,
    0,    0,    0,    0,    0,   85,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   84,   82,   83,    0,
    0,    0,   69,    0,    0,    0,    0,    0,    0,    0,
  109,    0,    0,    0,  101,  102,    0,   90,   91,   92,
   93,   88,   89,    0,    0,    0,   76,    0,   74,    0,
    0,    0,    0,   77,    0,   86,   87,    0,    3,    0,
    5,   73,    0,    0,    0,   43,    0,    0,   39,    0,
    0,    0,    0,    0,   35,    0,    0,    0,   37,   34,
   36,   79,    0,    0,    0,    0,    0,    0,    0,   38,
   40,    0,   45,   46,   47,   48,   33,   32,    0,    4,
    7,    8,    6,    0,    0,    0,    0,    0,   80,    0,
    0,    0,    0,    0,    0,   44,    0,   37,   43,   55,
   56,   53,    0,   54,    0,    0,    0,    0,    0,   49,
   51,   52,   50,
};
final static short yydgoto[] = {                          3,
   10,  209,   53,  116,   54,   59,   56,   57,   58,   11,
   12,   13,   14,   15,   42,   43,   44,   45,   46,   47,
   16,   97,  210,  211,  197,  198,  199,  200,  201,  202,
  203,  204,   31,  100,  108,  109,  158,  117,  111,  122,
  112,  113,  114,
};
final static short yysindex[] = {                       -33,
  -92,  -92,    0,    0,    0,    0, -243, -212, -161, -117,
  -92,   65,   69,   76,  109, -170, -117, -157,  125,   29,
  -59,  -59,    0,    0,    0,    0,    0,    0,  145,  -11,
    0,    0,  156,  158,   10,   15,   56,  -30,   43,    0,
    0,  -20,   43,  234,  240,  246,  247,   61,  -26,  287,
  289,  293,  275,  -59,   57,  276,  278,  279, -145,   82,
  300,  -24, -120,  301,    0,   85,    0,    0,   86,    0,
  145,    0,   66,    0,    0,    0,    0,    0,    0,   35,
 -108, -108, -108, -108,   63,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -24,   87,  212,    0,    0,  253,
  303,  -24,  308,   46,    0,  -24,   36,    0,    0,    0,
   49,  173,    0,    0,  169,  309,   11,  310,  311,    0,
  213,  312,  313,  305,  -92,    0, -120,  314,  -24,  -24,
  315, -108,  100, -108, -108, -108, -108, -108,   94, -108,
 -108, -108, -108, -108, -108,  -81,    0,    0,    0,  -92,
  -24,   81,    0,  -92,  319,  320,  -92,  321,  322,  323,
    0,  169,  173,  173,    0,    0,  -81,    0,    0,    0,
    0,    0,    0,  -15,   14, -117,    0,  -70,    0,   81,
  -92,  -92, -101,    0, -108,    0,    0,  -81,    0,  -81,
    0,    0,  324,  325,  327,    0,   89,  -70,    0,    0,
  316,  317,  318,  326,    0,   81, -101,  -70,    0,    0,
    0,    0,   92,   93,   95,   96, -108, -108, -108,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -59,    0,
    0,    0,    0,  330,  333,  285,  114, -130,    0, -130,
  -15,   14,  102,  104,  105,    0,  -54,    0,    0,    0,
    0,    0, -109,    0, -109,  106,  107,  110,  111,    0,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -97,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  331,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  112,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0, -127,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  334,    0,    0,    0,    0,    0,    0,    0,    0,  331,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  288,    0,    0,  331,    0,    0,  -41,    0,    0,    0,
    3,  -29,    0,    0,  335,    0,    0,    0,    0,    0,
    0,    0,    0,  345,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  348,  -34,
    0,    7,   -7,   -2,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -72,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -62,    0,    0,    0,    0,    0,    0, -184,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  115,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
  135,   12,    0,  -61,  -76,   -4,    0,    0,    0,    0,
  101,    0,    0,  208,   50,    0,    0,    0,    0,    0,
   41,  -21, -139,  -75,    0,    0, -134, -115,    0,    0,
    2,    0,  146,  268,  -39,  263,  214,  -25,  -36,    0,
  168,  194,    0,
};
final static int YYTABLESIZE=399;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        107,
  107,  107,  107,  107,  225,  107,  108,  108,  108,  108,
  108,  100,  108,  100,  100,  100,   55,  107,  107,  107,
  107,   23,  118,  101,  108,  108,  108,  108,   32,  100,
  100,  100,  100,   98,   82,   98,   98,   98,   99,   18,
   99,   99,   99,   96,   19,  115,   96,   97,  121,   87,
   97,   98,   98,   98,   98,  110,   99,   99,   99,   99,
   48,   96,   96,  221,   96,   97,   97,  228,   97,  175,
  144,   12,  145,  123,  106,  132,  179,   69,   60,   48,
  128,  133,  222,   48,  131,  130,   29,  101,   73,   60,
  187,  135,   75,  136,   12,   20,   43,  162,  243,   33,
  250,  196,   96,  245,  205,  251,  159,  155,  156,  134,
   92,  214,   30,  216,  168,  169,  170,  171,  172,  173,
   40,  196,  246,   25,  246,   21,   49,   26,   12,  177,
  227,  229,  193,   93,   27,   96,   17,   67,   21,   40,
   98,   99,   96,   40,  194,   24,   96,  208,  107,   52,
  195,   12,   98,   99,   21,  234,  235,  174,   18,  159,
   22,  242,  244,  249,    4,    5,    6,   28,  178,   96,
   96,  195,    7,    8,   21,   49,  208,  256,  186,  258,
   18,   50,  236,   72,   44,   34,   49,  192,   60,    9,
   44,   96,  193,   51,   44,   62,   22,   49,   52,  213,
   44,  215,   44,   50,  194,   94,   41,   44,   44,   52,
  195,  135,   44,  136,  138,   51,   42,   44,   44,  137,
   52,  255,    1,    2,   87,   70,   71,   41,  107,  107,
  107,  107,    4,    5,    6,  108,  108,  108,  108,  247,
  100,  100,  100,  100,   68,   61,   41,   81,   63,  241,
   41,  248,  125,  148,  257,  135,  259,  136,   74,  152,
  188,  189,   98,   98,   98,   98,   64,   99,   99,   99,
   99,   65,   96,   96,   96,   96,   97,   97,   97,   97,
  140,  141,  142,  143,  176,    4,    5,    6,  180,  190,
  191,  183,   76,   35,    8,   36,   37,   38,   77,    4,
    5,    6,  163,  164,   78,   79,   39,   35,    8,   36,
   37,   38,    4,    5,    6,  206,  207,   80,  119,  107,
   66,    8,  120,   98,   99,  239,   83,  135,   84,  136,
  165,  166,   85,   86,   89,   88,   90,   91,   71,   95,
  102,  103,  104,  124,  105,  126,  127,  129,  151,  139,
  146,  147,  149,  150,  154,  157,  160,  167,  178,  181,
  182,  184,  132,  217,  218,  185,  219,  220,  230,  231,
  237,  232,  233,  238,  223,  224,  225,  240,  252,  253,
   68,  254,  260,  261,  226,   75,  262,  263,   78,   65,
   26,   36,   66,   95,  153,  161,    0,    0,  212,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   59,   47,   41,   42,   43,   44,
   45,   41,   47,   43,   44,   45,   21,   59,   60,   61,
   62,   10,   84,   63,   59,   60,   61,   62,   17,   59,
   60,   61,   62,   41,   61,   43,   44,   45,   41,  283,
   43,   44,   45,   41,  257,   82,   44,   41,   85,   54,
   44,   59,   60,   61,   62,   81,   59,   60,   61,   62,
   20,   59,   60,  198,   62,   59,   60,  207,   62,  146,
   60,  256,   62,   95,   40,   40,  152,   37,   44,   39,
  102,   46,  198,   43,  106,   40,  257,  127,   39,   44,
  167,   43,   43,   45,  279,  257,  281,  134,  238,  257,
  240,  178,   62,  238,  180,  240,  132,  129,  130,   61,
  256,  188,  283,  190,  140,  141,  142,  143,  144,  145,
   20,  198,  238,   59,  240,  256,  257,   59,  256,  151,
  206,  208,  263,  279,   59,   95,    2,   37,  256,   39,
  261,  262,  102,   43,  275,   11,  106,  278,  257,  280,
  281,  279,  261,  262,  256,  217,  218,  146,  256,  185,
  278,  238,  238,  240,  257,  258,  259,   59,  278,  129,
  130,  281,  265,  266,  256,  257,  278,  253,  167,  255,
  278,  263,  219,   38,  257,   61,  257,  176,   44,  282,
  263,  151,  263,  275,  257,   40,  278,  257,  280,  188,
  263,  190,  275,  263,  275,   60,  279,  280,  281,  280,
  281,   43,  275,   45,   42,  275,  279,  280,  281,   47,
  280,  276,  256,  257,  229,  256,  257,   20,  270,  271,
  272,  273,  257,  258,  259,  270,  271,  272,  273,  238,
  270,  271,  272,  273,   37,  257,   39,  274,   91,  238,
   43,  240,   41,   41,  253,   43,  255,   45,  279,  125,
  276,  277,  270,  271,  272,  273,  257,  270,  271,  272,
  273,  257,  270,  271,  272,  273,  270,  271,  272,  273,
  270,  271,  272,  273,  150,  257,  258,  259,  154,  276,
  277,  157,   59,  265,  266,  267,  268,  269,   59,  257,
  258,  259,  135,  136,   59,   59,  278,  265,  266,  267,
  268,  269,  257,  258,  259,  181,  182,  257,  256,  257,
  265,  266,  260,  261,  262,   41,   40,   43,   40,   45,
  137,  138,   40,   59,   59,  279,   59,   59,  257,   40,
   40,  257,  257,  257,  279,   93,   44,   40,   44,   41,
   41,   41,   41,   41,   41,   41,  257,  264,  278,   41,
   41,   41,   40,   40,   40,   44,   40,  279,  277,  277,
   41,  277,  277,   41,   59,   59,   59,  264,  277,  276,
   93,  277,  277,  277,   59,   41,  277,  277,   41,   59,
  279,  277,   59,   59,  127,  133,   -1,   -1,  185,
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
"bloque_ejecutable : error sentencias_ejecutables END",
"bloque_ejecutable : BEGIN sentencias_ejecutables error",
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

//#line 234 "src/gramatica.y"

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
//#line 506 "Parser.java"
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
//#line 26 "src/gramatica.y"
{ yyerror("Error: falta delimitador BEGIN");}
break;
case 11:
//#line 27 "src/gramatica.y"
{ yyerror("Error: falta delimitador END");}
break;
case 12:
//#line 30 "src/gramatica.y"
{System.out.println("Es una sentencia ejecutablesss");}
break;
case 13:
//#line 31 "src/gramatica.y"
{System.out.println("Es una sentencia ejecutablesss");}
break;
case 14:
//#line 34 "src/gramatica.y"
{System.out.println("Es una sentencia ejecutable");}
break;
case 17:
//#line 37 "src/gramatica.y"
{System.out.println("Es una sentencia ejecutable");}
break;
case 18:
//#line 41 "src/gramatica.y"
{System.out.println("Es una sentencia declarativas");}
break;
case 19:
//#line 42 "src/gramatica.y"
{System.out.println("Es una sentencia declarativas");}
break;
case 20:
//#line 45 "src/gramatica.y"
{System.out.println("Es una sentencia declarativa");}
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
case 60:
//#line 114 "src/gramatica.y"
{System.out.println("estamos permitiendo declaracion multiple de atributos");}
break;
case 63:
//#line 122 "src/gramatica.y"
{yyerror("Error sintactico: ausencia de nombre o lista de clases");}
break;
case 81:
//#line 163 "src/gramatica.y"
{System.out.println("Es una cadena");}
break;
case 83:
//#line 167 "src/gramatica.y"
{System.out.println("es una cadena que se imprime");}
break;
case 84:
//#line 168 "src/gramatica.y"
{yyerror("Error: falta argumento en sentencia POUT.");}
break;
case 88:
//#line 179 "src/gramatica.y"
{System.out.println("Es una comparacion <");}
break;
case 89:
//#line 180 "src/gramatica.y"
{System.out.println("Es una comparacion >");}
break;
case 90:
//#line 181 "src/gramatica.y"
{System.out.println("Es una comparacion >=");}
break;
case 91:
//#line 182 "src/gramatica.y"
{System.out.println("Es una comparacion <=");}
break;
case 92:
//#line 183 "src/gramatica.y"
{System.out.println("Es una desigualdad");}
break;
case 93:
//#line 184 "src/gramatica.y"
{System.out.println("Es una igualdad");}
break;
case 94:
//#line 190 "src/gramatica.y"
{System.out.println("Es una asignación con := ");}
break;
case 95:
//#line 192 "src/gramatica.y"
{System.out.println("Es una asignación con = ");}
break;
case 96:
//#line 196 "src/gramatica.y"
{System.out.println("Es una expr");}
break;
case 97:
//#line 197 "src/gramatica.y"
{System.out.println("Es una asignacion de expr");}
break;
case 98:
//#line 202 "src/gramatica.y"
{ System.out.println("Es una suma de los valores: " + val_peek(2)+ " y " + val_peek(1));}
break;
case 99:
//#line 204 "src/gramatica.y"
{ System.out.println("Es una resta de los valores: " + val_peek(2)+ " y " + val_peek(1));}
break;
case 100:
//#line 206 "src/gramatica.y"
{ System.out.println("Es un término");}
break;
case 101:
//#line 211 "src/gramatica.y"
{ System.out.println("Es una división de los valores: " +val_peek(2)+ " y " + val_peek(1));}
break;
case 102:
//#line 213 "src/gramatica.y"
{ System.out.println("Es una multiplicación de los valores: " + val_peek(2)+ " y " + val_peek(1));}
break;
case 103:
//#line 215 "src/gramatica.y"
{ System.out.println("Es un factor");}
break;
case 104:
//#line 220 "src/gramatica.y"
{ System.out.println("Es un factor");}
break;
case 105:
//#line 221 "src/gramatica.y"
{System.out.println("Es un cte factor");}
break;
case 106:
//#line 222 "src/gramatica.y"
{System.out.println("Es una invocacion_funcion factor");}
break;
case 107:
//#line 225 "src/gramatica.y"
{System.out.println("Es un variable");}
break;
case 108:
//#line 226 "src/gramatica.y"
{System.out.println("Es un atributo");}
break;
case 109:
//#line 227 "src/gramatica.y"
{System.out.println("Es un acceso a metodo");}
break;
case 110:
//#line 230 "src/gramatica.y"
{System.out.println("Detecte un entero");}
break;
case 111:
//#line 231 "src/gramatica.y"
{System.out.println("Es un double");}
break;
//#line 843 "Parser.java"
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
