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






//#line 2 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
        import java.io.*;
//#line 19 "Parser.java"




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
    0,    0,    3,    3,    5,    5,    2,    2,    2,    7,
    7,    7,    6,    6,    6,    6,    6,    1,    1,    1,
   12,   12,   12,   12,   15,   15,   17,   17,   18,   18,
   18,   18,   18,   19,   19,   19,   19,   20,   20,   20,
   20,   21,   22,   22,   13,   25,   25,   26,   26,   23,
   23,   23,   14,   14,   24,   24,   28,   29,   29,   11,
   32,   10,   10,   10,   16,    9,    9,    4,    4,    4,
    4,    4,    4,    8,   30,   30,   31,   31,   31,   31,
   31,   31,   33,   33,   33,   34,   34,   34,   35,   35,
   35,   27,   27,
};
final static short yylen[] = {                            2,
    3,    3,    6,    8,    1,    1,    3,    2,    2,    1,
    2,    2,    2,    2,    2,    2,    2,    1,    2,    2,
    2,    2,    2,    2,    5,    4,    1,    2,    2,    2,
    2,    2,    2,    8,    8,    7,    7,    1,    1,    2,
    2,    2,    2,    2,    2,    1,    3,    1,    3,    1,
    1,    1,    8,    8,    2,    4,    4,    1,    3,    4,
    1,    4,    4,    4,    6,    6,    6,    3,    3,    3,
    3,    3,    3,    3,    1,    3,    3,    3,    2,    3,
    3,    1,    3,    3,    1,    1,    1,    1,    1,    3,
    3,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,   52,   50,   51,    0,    0,    0,    0,
   18,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   20,    0,    0,    0,    0,    0,    0,    2,    0,   10,
    0,    0,    0,    0,    0,   19,   21,   22,   23,   24,
    0,    0,   45,    1,    0,    0,    0,    0,    0,    0,
    0,    0,   38,   39,    0,   27,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   14,   12,
    8,   11,   13,   15,   16,   17,    0,    0,    0,    0,
   33,    0,   42,    0,   41,   40,    0,   44,   43,    0,
   26,   28,   29,   30,   31,   32,    0,    0,   92,   93,
   87,   88,   74,    0,    0,   85,   86,    0,    0,    0,
    7,    0,   61,    0,    0,    0,   47,    0,    0,    0,
    0,    0,    0,    0,    0,   25,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   64,   62,   63,   60,    0,    0,    0,
   65,    0,    0,    0,    0,    0,    0,    0,    0,   91,
    0,   80,    0,   81,    0,   84,   83,    0,   70,   71,
   72,   73,   68,   69,    5,    0,    0,    0,    0,    0,
   49,    0,    0,    0,    0,   57,    0,   66,    0,    0,
    3,    0,   56,   54,    0,    0,    0,    0,   59,    0,
   53,   37,    0,    0,   36,    4,   35,   34,
};
final static short yydgoto[] = {                          3,
   10,  175,   29,  108,  176,   30,   31,   32,   33,   34,
   35,   11,   12,   13,   14,   15,   55,   56,   57,   58,
   59,   60,   16,  120,   43,  121,  101,  102,  157,  109,
  104,  115,  105,  106,  107,
};
final static short yysindex[] = {                      -175,
 -104, -104,    0,    0,    0,    0, -269, -241, -225,   86,
    0,    5,   39,   50,   56, -209,   86, -211,   81,  183,
    0, -208,   77,   89,  129,  130,  137,    0,  120,    0,
  100,  123,  141,  144,  147,    0,    0,    0,    0,    0,
  152,  -69,    0,    0,  168,  135,  162,  -33,  -30,  -73,
 -120,  188,    0,    0,  159,    0,  169,  176,  177,  186,
   -1,   63,   63,   63, -208,  126,  -97,   63,    0,    0,
    0,    0,    0,    0,    0,    0,    2,  224, -214,  -71,
    0,  225,    0,   13,    0,    0,   14,    0,    0,  164,
    0,    0,    0,    0,    0,    0,  105,   53,    0,    0,
    0,    0,    0,   -7,   74,    0,    0,  231,   68,  235,
    0,  241,    0,   51,  242,   69,    0, -214,   27,  246,
  199,  249, -214,  263,  107,    0, -214,   63,   49,   63,
  -42,  -13,   74,   63,   63,   43,   63,   63,   63,   63,
   63,   63,  133,    0,    0,    0,    0,  268,  266, -104,
    0,  -71,  273, -214, -214,  274,  280,  278,  293,    0,
   75,    0,   74,    0,   74,    0,    0,  133,    0,    0,
    0,    0,    0,    0,    0, -119,    0, -104, -214,   86,
    0, -104,  294,  305, -104,    0,   63,    0,    0,  133,
    0,   86,    0,    0,   86, -104, -104,   86,    0,   57,
    0,    0,   86,   86,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -182,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  288,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    1,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  288,  -39,    0,    0,
    0,    0,    0,   29,  -10,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  255,    0,    0,  288,    0,    0,    0,    0,    0,
    0,    0,   -4,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  309,    0,
    0,    0,    0,    0,    0,    0,    0,  314,  -32,    0,
   46,    0,   18,    0,   24,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   97,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   48,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   -2,    7,    0,  295,  172,  -24,  333,    0,    0,    0,
    0,    9,   83,    0,    0,   91,  313,  -37,    0,    0,
    0,    0,  119,  -23,   25,  217,  -55,  256,  184,  -15,
    4,    0,   36,   82,    0,
};
final static int YYTABLESIZE=461;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         17,
    9,   89,   89,   89,   89,   89,   72,   89,   90,   90,
   90,   90,   90,   18,   90,   19,   28,   92,   36,   89,
   89,   89,   89,   44,  122,   36,   90,   90,   90,   90,
   82,   20,   82,   82,   82,  131,   79,  132,   79,   79,
   79,   72,    4,    5,    6,   45,  103,   41,   82,   82,
   82,   82,   92,  130,   79,   79,   79,   79,   77,    9,
   77,   77,   77,   37,   78,   62,   78,   78,   78,   75,
  114,  116,   75,   42,   52,   89,   77,   77,   77,   77,
    1,    2,   78,   78,   78,   78,   76,   75,   75,   76,
   75,  145,  128,  131,  148,  132,  122,   38,  129,  153,
   52,  117,   53,  156,   76,   76,   67,   76,   39,  147,
   54,  131,  158,  132,   40,  134,   63,  131,  177,  132,
  135,  169,  170,  171,  172,  173,  174,  141,   64,  142,
  183,  184,   85,  161,   53,   88,   41,   53,   61,  133,
   86,   46,   54,  189,  127,   54,  155,  180,   77,  133,
   77,  133,    4,    5,    6,  193,  190,  191,  112,   98,
    7,    8,  113,   99,  100,  177,  163,  165,   87,   67,
   61,  158,   53,   61,  188,  192,   68,    9,   69,  195,
   54,   73,  198,    4,    5,    6,  194,   78,   36,   99,
  100,   84,    8,  203,  204,   77,  133,  119,  201,   74,
   36,  202,   75,   36,  205,   76,   36,   79,   61,  207,
  208,   36,   36,  162,   98,  166,  167,   89,   99,  100,
   81,   89,   89,   82,   90,   80,   83,   93,   90,   90,
   89,   89,   89,   89,   94,   95,  119,   90,   90,   90,
   90,  119,  164,   98,   96,  119,   82,   99,  100,   98,
   82,   82,   79,   99,  100,   97,   79,   79,   41,   82,
   82,   82,   82,  118,  123,   79,   79,   79,   79,  124,
  125,  136,  119,  119,   77,  143,    9,    9,   77,   77,
   78,  144,  146,  149,   78,   78,  150,   77,   77,   77,
   77,  151,  152,   78,   78,   78,   78,  119,   75,   75,
   75,   75,  154,   10,   10,  159,  168,   98,  178,  179,
   10,   99,  100,  182,  185,   76,   76,   76,   76,   98,
  186,  187,   10,   99,  100,   98,   10,   10,   10,   99,
  100,   98,  128,  206,  196,   99,  100,  137,  138,  139,
  140,   21,   22,    5,    6,  197,   46,   48,   23,   55,
    7,    8,   10,   10,   58,   70,   65,   66,  110,   10,
   24,  200,   23,   25,   90,   26,   27,    9,  181,    0,
  199,   10,    6,    6,   24,   10,   10,   10,   71,   26,
   27,   70,   65,    0,  160,   65,    0,    0,   23,   65,
    0,   23,    0,    0,    0,   23,    0,    0,    0,    0,
   24,    0,    0,   24,  111,   26,   27,   24,   26,   27,
   25,    0,   26,   27,   47,    4,    5,    6,    0,   47,
    4,    5,    6,   48,    8,   49,   50,   51,   48,    8,
   49,   50,   51,    0,    0,    0,    0,   91,   47,    4,
    5,    6,  126,   47,    4,    5,    6,   48,    8,   49,
   50,   51,   48,    8,   49,   50,   51,    0,    0,    0,
   52,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
    0,   41,   42,   43,   44,   45,   31,   47,   41,   42,
   43,   44,   45,  283,   47,  257,   10,   55,   10,   59,
   60,   61,   62,   17,   80,   17,   59,   60,   61,   62,
   41,  257,   43,   44,   45,   43,   41,   45,   43,   44,
   45,   66,  257,  258,  259,  257,   62,  257,   59,   60,
   61,   62,   90,   61,   59,   60,   61,   62,   41,   59,
   43,   44,   45,   59,   41,  274,   43,   44,   45,   41,
   67,   68,   44,  283,  257,   51,   59,   60,   61,   62,
  256,  257,   59,   60,   61,   62,   41,   59,   60,   44,
   62,   41,   40,   43,  118,   45,  152,   59,   46,  123,
  283,   77,   20,  127,   59,   60,   59,   62,   59,   41,
   20,   43,  128,   45,   59,   42,   40,   43,  143,   45,
   47,  137,  138,  139,  140,  141,  142,   60,   40,   62,
  154,  155,   50,  130,   52,  256,  257,   55,   20,  104,
   50,   61,   52,  168,   40,   55,   40,  150,   44,  114,
   44,  116,  257,  258,  259,  179,  276,  277,  256,  257,
  265,  266,  260,  261,  262,  190,  131,  132,   50,   40,
   52,  187,   90,   55,  168,  178,   40,  282,   59,  182,
   90,   59,  185,  257,  258,  259,  180,  257,  180,  261,
  262,  265,  266,  196,  197,   44,  161,   79,  192,   59,
  192,  195,   59,  195,  198,   59,  198,   40,   90,  203,
  204,  203,  204,  256,  257,  134,  135,  257,  261,  262,
   59,  261,  262,  257,  257,   91,  257,   59,  261,  262,
  270,  271,  272,  273,   59,   59,  118,  270,  271,  272,
  273,  123,  256,  257,   59,  127,  257,  261,  262,  257,
  261,  262,  257,  261,  262,  257,  261,  262,  257,  270,
  271,  272,  273,   40,   40,  270,  271,  272,  273,  257,
  257,   41,  154,  155,  257,   41,  276,  277,  261,  262,
  257,   41,   41,  257,  261,  262,   41,  270,  271,  272,
  273,   93,   44,  270,  271,  272,  273,  179,  270,  271,
  272,  273,   40,  256,  257,  257,  264,  257,   41,   44,
  263,  261,  262,   41,   41,  270,  271,  272,  273,  257,
   41,   44,  275,  261,  262,  257,  279,  280,  281,  261,
  262,  257,   40,  277,   41,  261,  262,  270,  271,  272,
  273,  256,  257,  258,  259,   41,   59,   93,  263,   41,
  265,  266,  256,  257,   41,  256,  257,   25,   64,  263,
  275,  190,  263,  278,   52,  280,  281,  282,  152,   -1,
  187,  275,  276,  277,  275,  279,  280,  281,  279,  280,
  281,  256,  257,   -1,  129,  257,   -1,   -1,  263,  257,
   -1,  263,   -1,   -1,   -1,  263,   -1,   -1,   -1,   -1,
  275,   -1,   -1,  275,  279,  280,  281,  275,  280,  281,
  278,   -1,  280,  281,  256,  257,  258,  259,   -1,  256,
  257,  258,  259,  265,  266,  267,  268,  269,  265,  266,
  267,  268,  269,   -1,   -1,   -1,   -1,  279,  256,  257,
  258,  259,  279,  256,  257,  258,  259,  265,  266,  267,
  268,  269,  265,  266,  267,  268,  269,   -1,   -1,   -1,
  278,
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
"if : IF '(' comp ')' cuerpo_if END_IF",
"if : IF '(' comp ')' cuerpo_if ELSE cuerpo_if END_IF",
"cuerpo_if : bloque_ejecutable",
"cuerpo_if : sentencia_ejecutable",
"bloque_ejecutable : BEGIN sentencias_ejecutables END",
"bloque_ejecutable : sentencias_ejecutables END",
"bloque_ejecutable : BEGIN sentencias_ejecutables",
"sentencias_ejecutables : sentencia_ejecutable",
"sentencias_ejecutables : sentencias_ejecutables sentencia_ejecutable",
"sentencias_ejecutables : sentencias_ejecutables error",
"sentencia_ejecutable : assign ';'",
"sentencia_ejecutable : if ';'",
"sentencia_ejecutable : while_repeat ';'",
"sentencia_ejecutable : print ';'",
"sentencia_ejecutable : retorno ';'",
"sentencias_declarativas : sentencia_declarativa",
"sentencias_declarativas : sentencias_declarativas sentencia_declarativa",
"sentencias_declarativas : sentencias_declarativas error",
"sentencia_declarativa : declaracion_var ';'",
"sentencia_declarativa : funcion ';'",
"sentencia_declarativa : clase ';'",
"sentencia_declarativa : enum ';'",
"clase : CLASS ID BEGIN cuerpo_clase END",
"clase : CLASS ID cuerpo_clase END",
"cuerpo_clase : sent_clase",
"cuerpo_clase : cuerpo_clase sent_clase",
"sent_clase : metodo ';'",
"sent_clase : atributo ';'",
"sent_clase : friendly ';'",
"sent_clase : herencia ';'",
"sent_clase : error ';'",
"metodo : PRIVATE tipo ID '(' parametros_formales ')' sentencias_declarativas bloque_ejecutable",
"metodo : PRIVATE AUTO ID '(' parametros_formales ')' sentencias_declarativas bloque_ejecutable",
"metodo : tipo ID '(' parametros_formales ')' sentencias_declarativas bloque_ejecutable",
"metodo : AUTO ID '(' parametros_formales ')' sentencias_declarativas bloque_ejecutable",
"atributo : declaracion_var",
"atributo : enum",
"atributo : PRIVATE enum",
"atributo : PRIVATE declaracion_var",
"friendly : FRIEND ID",
"herencia : EXTENDS lista_de_variables",
"herencia : EXTENDS error",
"declaracion_var : tipo lista_de_variables",
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

//#line 250 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"

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
//#line 477 "Parser.java"
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
//#line 19 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{System.out.println("Es un programa");}
break;
case 2:
//#line 20 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{yyerror("Error: falta nombre de programa");}
break;
case 8:
//#line 34 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{yyerror("Error sintactico: Falta el BEGIN");}
break;
case 9:
//#line 35 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{yyerror("Errror sintactico: Falta el END");}
break;
case 12:
//#line 40 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{ yyerrflag = 0; }
break;
case 20:
//#line 53 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{ yyerrflag = 0; }
break;
case 33:
//#line 74 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{ yyerrflag = 0; }
break;
case 41:
//#line 131 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{System.out.println("estamos permitiendo declaracion multiple de atributos");}
break;
case 44:
//#line 139 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{yyerror("Error sintactico: ausencia de nombre o lista de clases");}
break;
case 61:
//#line 179 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{System.out.println("Es una cadena");}
break;
case 63:
//#line 183 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{System.out.println("es una cadena que se imprime");}
break;
case 64:
//#line 184 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{yyerror("Error: falta argumento en sentencia POUT.");}
break;
case 68:
//#line 196 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{System.out.println("Es una comparacion <");}
break;
case 69:
//#line 197 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{System.out.println("Es una comparacion >");}
break;
case 70:
//#line 198 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{System.out.println("Es una comparacion >=");}
break;
case 71:
//#line 199 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{System.out.println("Es una comparacion <=");}
break;
case 72:
//#line 200 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{System.out.println("Es una desigualdad");}
break;
case 73:
//#line 201 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{System.out.println("Es una igualdad");}
break;
case 74:
//#line 206 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{System.out.println("Es una asignación con := ");}
break;
case 76:
//#line 211 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{System.out.println("Es una asignacion de expr");}
break;
case 77:
//#line 216 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{ System.out.println("Es una suma de los valores: " + val_peek(2) + " y " + val_peek(0));}
break;
case 78:
//#line 218 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{ System.out.println("Es una resta de los valores: " + val_peek(2) + " y " + val_peek(0));}
break;
case 79:
//#line 219 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{yyerror("Error sintactico: Falta operador en expresión");}
break;
case 80:
//#line 220 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{ yyerror("Error sintactico: Falta operando en expr");}
break;
case 81:
//#line 221 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{ yyerror("Error sintactico: Falta operando en expr");}
break;
case 83:
//#line 228 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{ System.out.println("Es una división de los valores: " +val_peek(2) + " y " + val_peek(0));}
break;
case 84:
//#line 230 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{ System.out.println("Es una multiplicación de los valores: " + val_peek(2) + " y " + val_peek(0));}
break;
case 90:
//#line 241 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{System.out.println("Es un atributo");}
break;
case 91:
//#line 242 "C:\Users\Usuario\OneDrive\Desktop\Compilador\src\gramatica.y"
{System.out.println("Es un acceso a metodo");}
break;
//#line 742 "Parser.java"
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
