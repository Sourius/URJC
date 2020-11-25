//imports
package Practica_Obligatoria;
import java.util.LinkedList;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

%%
//opciones
%class AnalizadorLexico
%standalone
%line
%column
%debug
%ignorecase
%state YYINITIAL,incremento,decremento,library

/*eofval*/
%eofval{
    FileReader fr;
    BufferedReader br;
    String filename;
    PrintStream ls;
    
    //cerrar las salidas abiertas
    ps.close();
    programPs.close();
    
    filename = pName.concat(".c");
    ls = new PrintStream(
    new BufferedOutputStream(
        new FileOutputStream(
            new File(filename),true)),true);
    System.setOut(ls);
    
    //concatenar constantes con el resto de cosas
    filename = pName.concat(".txt");
    fr = new FileReader(filename);
    String line;
    br = new BufferedReader(fr);
    
    //añadir libreria basica
    System.out.println("#include <stdin.h>");
    
    //importar las bibliotecas utilizadas
    for(int i = 0; i < usedLib.size(); i++){
            System.out.println("#include \" "+usedLib.get(i)+" \""); 
    }
	
    //añadir constantes
    for(int i = 0; i < constantes.size(); i++){
        line = constantes.get(i);
        System.out.println(line);
    }

    //añadir el resto que esta en nombrePrograma.txt
    line = br.readLine();    
    while(line !=null) {
        System.out.println(cadena);
    }
    br.close();
    System.setOut(System.out);
    ls.close();
%eofval}

/*variables globales*/
%{ 
    //nombre del programa
    private String pName;
	
    //fichero de salidas
    private PrintStream programPS;
    private PrintStream ps;

    //lista de constantes
    private LinkedList<String> constantes;
	
    //lista de librerias
    private LinkedList<String> usedLib;

    //para los condicionales
    private boolean conRepeat;

    //para librerias
    private boolean library;
    private boolean publicHeader;
%}  

/*init*/
%init{
    //inicializar variables del programa
    conRepeat = false;
    library = false;
    publicHeader = false;
    constantes = new LinkedList<>();
%init}

/*codigo*/
%{
    //metodo que dado un string con parametros los separa y los traduce
    void getParam(String s){
        String []par = s.split(";"); // separar por ;
        int len = par.length;
        for (int i= 0; i<len; i++){
            System.out.print(getType(par[i]));
            if(y < len) System.out.print(",");
        }
    }
	
    //Metodo que imprime el tipo y devuelve lo que queda por delante del ultimo :
    String getType(String s){
        String aux;
        String tipo;
        for(int i = s.length-1; i>=0; i--){
            if(s.charAt(i) == ":"){
                tipo = s.substring(i)+" ";
                aux=s.substring(0,i);
                break;
            }
        }
        System.out.print(tipo);
        return aux;
    }

    //metodos para mostrar mensajes de error
    void printError(int line, int column){
        System.setOut(System.out);
        System.out.print("Error en linea "+line+" y columna "+column);
        System.setOut(ps);
    }
	
    void printError(int line, int column, String message){
        printError(line,column);
        System.setOut(System.out);
        System.out.println(": "message);
        System.setOut(ps);
    }
%}


//macros
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
identifier=[a-z]('_'|{WORD}|{DIGIT})*
ID={identifier}

/*Comentarios*/
COMMENT=("{"(.)*"}")|("(*"(([^"*)"])*"*)"))
ʎ="lambda"

//Zona de Declaraciones
SIMPVALUE={numeric_integer_const}|{numeric_real_const}|{string_const}
CTELIST={ID}" = "{SIMPVALUE}";"|{CTELIST}{ID}" = "{SIMPVALUE}";"
DEFCTE="const "{CTELIST}
VARLIST={ID}|{ID}", "{VARLIST}
TBAS="INTEGER"|"REAL"
DEFVARLIST={VARLIST}":"{TBAS}|{DEFVARLIST}";"{VARLIST}":"{TBAS}
DEFVAR="var "{DEFVARLIST}";"
FORMAL_PARAM={VARLIST}":"{TBAS}|{VARLIST}":"{TBAS}";"{FORMAL_PARAM}
FORMAL_PARAMLIST={ʎ}|"("{FORMAL_PARAM}")"
DEFPROC="procedure "{ID}{FORMAL_PARAMLIST}";"{BLQ}";"
DEFFUN="function "{ID}{FORMAL_PARAMLIST}":"{TBAS}";"{BLQ}";"
DCL={DEFCTE}|{DEFVAR}|{DEFPROC}|{DEFFUN} 

//Zona de Sentencias
OPARIT=" + "|" - "|" * "|" div "|" mod "
OP={OPARIT}

SUBPPARAMLIST="ʎ"|"("{EXPLIST}")"
FACTOR={SIMPVALUE}|"("{EXP}")"|{ID}{SUBPPARAMLIST}
EXP={EXP}{OP}{EXP}|{FACTOR}
EXPLIST={EXP}|{EXP}";"{EXPLIST}
ASIG={ID}":="{EXP}
PROC_CALL={ID}{SUBPPARAMLIST}

//estructuras de control
OPLOG=" or "|" and "
OPCOMP=" < "|" > "|" <= "|" >= "|" = "
NOT="not"
FACTORCOND={EXP}{OPCOMP}{EXP}|"("{EXP}")"|"not"{FACTORCOND}
EXPCOND={EXPCOND}{OPLOG}{EXPCOND}|{FACTORCOND}

INC=" to "|" downto "
SENT={ASIG}";"|{PROC_CALL}";"
|"if "{EXPCOND}" then "{BLQ}" else"{BLQ}
|"while "{EXPCOND}" do"{BLQ}
|"repeat "{BLQ}" until "{EXPCOND}";"
|"for "{ID}":="{EXP}{INC}{EXP}" do"{BLQ}

//Estructura del programa
DCLLIST={ʎ}|{DCL}|({DCLLIST}{DCL})
SENTLIST={SENT}|({SENTLIST}{SENT}) 
BE="begin"{SENTLIST}"end";
MAIN="begin"{SENTLIST}"end.";
BLQ={DCLLIST}{BE}
LIB="UNIT "{ID}";"
LIBCALL="USES "{ID}";"
PRG="program "{ID}";"{BLQ}"."|"UNIT "{ID}";"{DCLLIST}"."


%%
//reglas
<YYINITIAL, library>{ʎ} {}

//literales
<YYINITIAL, library> {STRING} {}

//comentarios
//<YYINITIAL,library> {COMMENT} {}

//programa
<YYINITIAL> {PRG} {
    String s = yytext();
    String filename;
    int len;
    // separar por el primer begin
    String [] aux = s.split(";",2);
    len = aux[0].length;
    yypushback(yylength-len);

    //obtener nombre del programa
    pName = aux[0].substring(7,len-1);
    filename = prName+".txt";
    
    //cambiamos la salida del sistema a un fichero
    programPS= new PrintStream(
    new BufferedOutputStream(
        new FileOutputStream(
            new File(filename),true)),true);
    ps= programPS;
    System.setOut(ps);
}

//programa principal
<YYINITIAL> {MAIN} {
    if(library){
        printError(yyline(), yycolumn(),yytext());
    }else{
        System.out.println("void main (){")
        yypushback(4);//end.
        yypushback(yylength-5);//sentlist
    }
}

//bloques begin ... end
<YYINITIAL,library> {BLQ} {
    // si no hay declaraciones
    if(yytext().substring(0,5).compareTo("lambda") == 0){
        System.out.println("{");        
        yypushback(yylength-5);
    }
    //si hay declaraciones
    else{ 
        // analizamos el bloque
        String aux,[]start;
        int eval;
        start = yytext.split("begin",2);//separar la cadena en 2 por el primer begin
        if(start.length > 1){
            eval= yylength - start[0].length;
            yypushback(eval); // analizar desdel primer begin, incluido
        }
        //analizar declaraciones
        yypushback(yylength);
    }
}

<YYINITIAL,library> "end" {
    System.out.println("}");
}

<YYINITIAL, library> "end." {
    if(library){
        library = false;
        yybegin(YYINITIAL);
    }
	//final del programa o libreria
	System.out.println("}");
}

//zona de declaraciones

//lista de declaraciones
<YYINITIAL,library> {DCLLIST} {
    LinkedList<String> sol = new LinkedList <>();
    String []pR = {"procedure ","function ","const ","var "}
    String []aux, s = yytext();
    int []pos, min, len;
    boolean done = false;

    while(!done){
        min = -1;

        //obtener posiciones
        pos[0] = s.indexOf(pR[0]);
        pos[1] = s.indexOf(pE[1]);
        pos[0] = s.indexOf(pR[2]);
        pos[1] = s.indexOf(pE[3]);

        //obtener el minimo
        for (int i = 0; i < 4 ; i++){
            if(pos[0] != -1){
                if(min > pos[i] || min == -1) min = i;
            }
        }
		
        //si hay alguna palabra reservada (las de pR)
        if(min != -1){
            aux = s.split(pR[min], 2);//separar por la primera aparición
            if(sol.size() == 0){
                sol.add(aux[0]);				
            }else{
                s = sol.removeLast();
                s = s.concat(aux[0]);
                sol.add(s);
            }
            sol.add(pR[min]);
            s = aux[1];
        }else{
            done = true;
        }
    }
	
    //processar
    while (sol.size() > 0){
        s = sol.removeLast();
        len = s.length;
        yypushback(len);
    }
}

//lambda
<YYINITIAL, library> {ʎ} {System.out.println()}

//variables
<YYINITIAL,library> {DEFVAR} {
    yypushback(yylength-4);
}

<YYINITIAL, library> {DEFVARLIST} {
    String []s = yytext().split(";");
    String nombres;
    // id1, id2, id3, ... : tipo
    for(int i = 0; i < s.length; i++){
        nombres = getType(s[i]); 
        nombres = nombres.concat(";");
        System.out.println(nombres);
    }
}

//constantes
<YYINITIAL,library> {CTELIST} {
    String []s;
    String []s2;
    String aux;
    
    ps.close();
    PrintStream ls = new PrintStream(
    new BufferedOutputStream(
        new FileOutputStream(
            new File("constantes.txt"),true)),true);
    ps= ls;
    System.setOut(ps);
    
    s = yytext.split(";");
    for(int i = 0; i < s.length; i++){
        s2 = s[i].split("=");
        aux=("#define "+s2[0]);
        aux = aux.concat(" "+s2[1]);
        constantes.add(aux);
    }
}

<YYINITIAL, library> {DEFCTE} {
    yypushback(yylength-6);
}

<YYINITIAL, library> {DEFVAR} {
    yypushback(yylength-4);
}

<YYINITIAL, library> {DEFPROC} {
    System.out.println(“void ”);
    String aux = yytext.substring(10, yylength-1); 
    String[] s2;
    S2=aux.split(“(”,2)
    System.out.print(s2[0]+” (”);
    if(s2[1].charAt(0) != ‘)’){
        getParam(s2[1]);
        System.out.println(“) ”);
    }else{
        System.out.println(“void )”);
    }	
}

<YYINITIAL, library> {DEFFUN} {
    String aux=yytext().getType();
    String []s;		
    aux = aux.substring(8, yylength-1);
    s=aux.split(“(”,2);
    System.out.print(s[0]+” (”);
    if (s[1].charAt(0)!= ’)’){	 	
        getParam(s[1]);
        System.out.println(“) ”);
    }else{
        System.out.println(“void )”);
    }
}

//parametros
<YYINITIAL, library> {FORMAL_PARAMLIST} {
    if(!yytext().equals("lambda")){
        //parametros
        yypushback(1); // (
        yypushback(yylength-1); // formal param
        yypushback(1); // )
    }
}

<YYINITIAL, library> {FORMAL_PARAM} {
    String []s = yytext().split(";");
    String nombres;
    for(int i = 0; i < s.length; i++){
        nombres = getType(s[i]); 
        nombres = nombres.concat(";");
        System.out.println(nombres);
    }
}

//zona de sentencias
//expresiones
<YYINITIAL,library> {EXP} {// EXP OP EXP
    String[] opArit = ["+ ","- ","*","/"," mod "," div "];
    String s;
    String []s2;
    s = yytext();
    for(int i = 0; i < opArit.length; i++){
        if(s.contains(opArit[i])){
            s2 = s.split(opArit[i],2); // separamos por la primera aparición
            yypushback(s2[1].length); // procesar lo que viene detras
            yypushback(opArit[i].length);// procesar operador
            yypushback(yylength); // procesar lo que viene antes
        }
    }
}

<YYINITIAL,library> {EXPLIST} { // EXP ; EXP; ... ;EXP
    String s = yytext();
    String []s2;
    if(s.contains(";")){
        s2 = s.split(";");
        for(int i = s2.length -1; i >= 0; i--){
            yypushback(s2[i].length+1);
        }
    }
}

<YYINITIAL,library> {FACTOR} {
    if(yycharat(0)=='(' && yycharat(yylength-1)==')'){//exp
        yypushback(1);// parentesisi final
        yypushback(yylength-1);
        yypushback(1);// parentesis inicial
    }
    else if(yycharat(yylength-1)==')'){// id subparamlist
        String [] s, s2;
        s = yytext().split(" ",2);
        System.out.print(s[0]);
        if(s.length ){ 
            // si es lambda, vacio no hacer nada
            s2 = s[1].substring(0,7);
            if(!s2.equals(lambda))yypushback(yylength-s[0].length);
        }
    }
    else System.out.print(yytext()); //simpvalue
}

//sentencias
<YYINITIAL,library> {SENT} {
    String command = yytext().substring(0,6);
	String s[];
    //if
    if(command.contains("if ")){
        System.out.print("if (");
        yypushback(yylength-3);
    }
    //for
    else if(command.contains("for")){
        System.out.print("for (");
        yypushback(yylength-4);
    }
    //while
    else if(command.contains("while ")){
        System.out.print("while (");
        pushback(yylength-5);
    }
    //repeat    
    else if (command.contains("repeat")){
        System.out.print("do {");
        yypushback(yylength-6);
        conRepeat = true;
    }	
    //asignación y/o proc_call
    else{
        //asignación
        if (yytext().contains(":= "))System.out.println(yytext().replace(":=","="));	
        //proc_call
        else System.out.println(yytext());
    }
}

<YYINITIAL,library> {SENTLIST} {
    String []s;
    int len;
    s = yytext().split(";",2);
    len = s[0].length;
    yypushback(yylength-len);
    yypushback(len);
}

// if ...(en sent), then ..., else
<YYINITIAL,library> "then" {System.out.print(")");}
<YYINITIAL,library> "else" {System.out.print("} else ");}

<YYINITIAL,library> {INC} {
    if(yytext().cointains("to")){
        System.out.print(" <= ");
        yybegin(incremento);
    }else{
        System.out.print(" >= ");
        yybegin(decremento);
    }
}

<YYINITIAL,library> ";" {
    if(conRepeat){
        System.out.print(");");
        conRepeat = false;
    }
    else{
        System.out.println(";");
    }
}

<incremento> "do" {
    System.out.println("+1){");
    if(library){ 
		yybegin(library);
    }else{ 
		yybegin(YYINITIAL);
	}
}

<decremento> "do" {
    System.out.println("-1){");
    if(library){
		yybegin(library);
	}
    else{
		yybegin(YYINITIAL);
	}
}

<YYINITIAL, library> {ASIG} {System.out.println(yytext().replace(":=","="));}

<YYINITIAL,library> {EXPCOND} {
    String []s;
    String aux;
    int len;
    aux = yytext();
    len = yylength;
    
	if(conRepeat){
		System.out.print("}(");
	}
    if(aux.contains(" and ")){
		s = aux.split("and",2);
    }
	else if(aux.contains(" or ")){
		s = aux.split("or",2);
    }
	yypushback(s[1].length);//evaluar segunda parte
    yypushback(yylength-s[0].length);//evaluar expresión logica
	yypushback(yylength);//evaluar primera parte
}

<YYINITIAL,library> {FACTORCOND} {
    String s;
    String[]opC ={"<",">","<=",">=","="};
    String [] s2;
    int k; 
    s = yytext();
    
    //not factorcond
    if(s.substring(0,4).equals("not")){
        System.out.print("!");
        yypushback(yylength-4);
    }else {
        //exp opComp exp
        for(int i = 0; i < opC.length; i++){
            if(s.contains(opC[i])){
                s2 = s.split(opC[i],2);
                yypushback(s2[1].length);//segunda expresion
                yypushback(yylength-s2[0].length);//el operador
                yypushback(yylength);//la primera expresion
            }
		}
    }
}

//<YYINITIAL,library> {NOT} { System.out.print("!"); }

//operadores logicos, aritmeticos y de comparacion
<YYINITIAL,library> {OPARIT} {
    String s = yytext();
    if(s.compareTo("div")==0){
		System.out.print("/");
	}
    else if(s.compareTo("mod")==0){
		System.out.print("%");
    }
	else {
		System.out.print(s); 
	}
}

<YYINITIAL,library> {OPLOG} {
    String s = yytext();
    if(s.compareTo("and")==0){
		System.out.print("&&");
    }
	else{
		System.out.print("||");
	}
}

<YYINITIAL,library> {OPCOMP} {
    String s = yytext();
    if(s.compareTo("=")==0){
		System.out.print("==");
    }
	else{
		System.out.print(s);
	}
}

<YYINITIAL,library> "(" {System.out.print("(");}
<YYINITIAL,library> ")" {System.out.print(")");}

//librerias o bibliotecas
<library> {LIB} {
    yybegin(library);
    String filename;
    filename = yytext().substring(5, yylength-1)/*+".c"*/;
    System.out.println("//library: "+filename);
}

<library> "interface" {publicHeader=true;}
<library> "implementation" {publicHeader = false;}

<YYYYINITIAL, library> {LIBCALL} { 
	String libName = yytext().substring(5,yylength-1);
    if(library) {
		if(publicHeader){
			printError(yyline(),yycolumn(),yytext());
		}else{
			System.out.println("#include "+libName);
		}
    }
	else{
		usedLib.add(libName);
		System.out.println("#include "+libName);
	}
}

[\t\r\n\f] {}
. {printError(yyline,yycolumn,yytext();)}
