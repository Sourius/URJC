//codigo de usuario
package Practica_Obligatoria;
import java_cup.*;
import java.util.LinkedList;

%%
//codigo java constructor

//opciones y macros
%class Analizador_Lexico
%cup
%line
%column
%ignorecase
%unicode
%full

%init{
    errorList = new LinkedList<>();
%init}

//codigo java
%{	
    LinkedList<ErrorClass> errorList;
    void printError(int line, int column, String message){
        System.out.print("Error en linea "+line+" y columna "+column);
        System.out.println(": "+message);
    }
%}

//expresiones regulares
/* Numeros */
SIGN=('+'|'-')
DIGIT=[0-9]
INTEGER={SIGN}?{DIGIT}+
EXPONENT=[eE]
REAL_F={INTEGER}'.'{DIGIT}+
REAL_E={INTEGER}{EXPONENT}{INTEGER}
REAL_M={REAL_F}{EXPONENT}{INTEGER}
REAL={REAL_F}|{REAL_E}|{REAL_M}
numeric_integer_const={INTEGER}
numeric_real_const={REAL}

/*Literales*/
SPACE=' '
WORD=([a-zA-Z])
TEXT={WORD}({WORD}|{SPACE})+
STRING=("'"({TEXT}(("''"){TEXT})?)"'")|('"'{TEXT}'"')
string_const={STRING}

//identificador
identifier=[a-z]('_'|{WORD}|{DIGIT})*
ID={identifier}

/*Comentarios*/
COMMENT=("{"(.)*"}")|("(*"(([^"*)"])*"*)"))
LAMBDA="lambda"|"ʎ"

//Operadores aritmeticos
SUMA="+ "
RESTA="- "
PRODUCTO="*"
DIVISION="div"
MODULO="mod"

//IGUALnacion
IGUAL=":="

//operadores logicos y de comparacion
NOT="not"
AND="and"
OR="or"
MENOR="<"
MAYOR=">"
MENOR_IGUAL="<="
MAYOR_IGUAL=">="
IGUAL_QUE= "="

//otros elementos
LPAR="("
RPAR=")"
LKEY="begin"
RKEY="end"|"."
COMA=","
PCOMA=";"
PUNTO2=":"

//control
IF="if"
THEB="then"
ELSE="else"
WHILE="while"
DO="do"
REPEAT="repeat"
UNTIL="until"
FOR="for"
TO="to"
DOWNTO="downto"
TRUE="true"
FALSE="false"
END="."

VOID="procedure"|
FUNCT="function"
CONST="const"
VAR="var"
PROG="program"
LIBRARY="UNIT"
LIBCALL="USES"
RETURN="return"

%%
{numeric_integer_const}     {return new Symbol(sym.numeric_integer_const);}
{numeric_real_const}        {return new Symbol(sym.numeric_real_const);}
{string_const}              {return new Symbol(sym.string_const);}
{ID}                        {return new Symbol(sym.ID);}
{SUMA}                      {return new Symbol(sym.SUMA);}
{RESTA}                     {return new Symbol(sym.RESTA);}
{PRODUCTO}                  {return new Symbol(sym.PRODUCTO);}
{DIVISION}                  {return new Symbol(sym.DIVISION);}
{MODULO}                    {return new Symbol(sym.MODULO);}
{IGUAL}                     {return new Symbol(sym.IGUAL);}
{NOT}                       {return new Symbol(sym.NOT);}
{AND}                       {return new Symbol(sym.AND);}
{OR}                        {return new Symbol(sym.OR);}
{MENOR}                     {return new Symbol(sym.MENOR);}
{MAYOR}                     {return new Symbol(sym.MAYOR);}
{MENOR_IGUAL}               {return new Symbol(sym.MENOR_IGUAL);}
{MAYOR_IGUAL}               {return new Symbol(sym.MAYOR_IGUAL);}
{IGUAL_QUE}                 {return new Symbol(sym.IGUAL_QUE);}

{LPAR}                      {return new Symbol(sym.LPAR);}
{RPAR}                      {return new Symbol(sym.RPAR);}
{LKEY}                      {return new Symbol(sym.LKEY);}
{RKEY}                      {return new Symbol(sym.RKEY);}
{COMA}                      {return new Symbol(sym.COMA);}
{PCOMA}                     {return new Symbol(sym.PCOMA);}
{PUNTO2}                    {return new Symbol(sym.PUNTO2);}

{IF}                        {return new Symbol(sym.IF);}
{THEB}                      {return new Symbol(sym.THEN);}
{ELSE}                      {return new Symbol(sym.ELSE);}
{WHILE}                     {return new Symbol(sym.WHILE);}
{DO}                        {return new Symbol(sym.DO);}
{REPEAT}                    {return new Symbol(sym.REPEAT);}
{UNTIL}                     {return new Symbol(sym.UNTIL);}
{FOR}                       {return new Symbol(sym.FOR);}
{TO}                        {return new Symbol(sym.TO);}
{DOWNTO}                    {return new Symbol(sym.DOWNTO);}
{TRUE}                      {return new Symbol(sym.TRUE);}
{FALSE}                     {return new Symbol(sym.FALSE);}
{VOID}                      {return new Symbol(sym.VOID);}
{CONST}                     {return new Symbol(sym.CONST);}
{VAR}                       {return new Symbol(sym.VAR);}
{LAMBDA}                    {return new Symbol(sym.LAMBDA);}
{COMMENT}                   {}
{PROG}                      {return new Symbol(sym.PROG);}
{LIBRARY}                   {return new Symbol(sym.LIBRARY);}
{RETURN}                    {return new Symbol(sym.RETURN);}
{END}                       {return new Symbol(sym.RETURN);}
{LIBCALL}                   {return new Symbol(sym.LIBCALL);}
.                           {
                                printError(yyline,yycolumn,yytext());//gestión de errores
                                ErrorClass e = new ErrorClass(yytext(), yyline, yycolumn,"Error Lexico","Simbolo no reconocido!");
                                errorList.add(e);
                            }
"[\t\r\n\f]"                {}