package Practica_Complementaria;
/*imports*/
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

%%
/*Opciones*/
%line
%column
%standalone
%debug

/*Declaraciones*/
%eofval{ 
{
    System.out.println("    </body>");
    System.out.println("</html>");
} 
%eofval}

%{ 
    boolean isCode, isQuote, isBold, isItal;
%}

%init{
    PrintStream ps = new PrintStream(
        new BufferedOutputStream(
            new FileOutputStream(
                new File("salida.html"),true)),true);
    System.setOut(ps);

    System.out.println("<!DOCTYPE html>");
    System.out.println("<html>");
    System.out.println("    <head>");
    System.out.println("        <title>MarkDown2HTML</title>");
    System.out.println("        <style>");
    System.out.println("            .bold {font-weight: bold;}");
    System.out.println("            .ital {font-style: italic;}");
    System.out.println("        </style>");
    System.out.println("    </head>");
    System.out.println("    <body>");
    
%init}

/*MACROS*/
SPACE=' '
TEXT=([a-zA-Z]|SPACE)*
NEWLINE='---'
LINE_BREAK={SPACE}*"\n"
CODE='~~~'
QUOTE='>'{SPACE}
BOLD=('**'|'__'){TEXT}('**'|'__')
ITALIC=('*'|'_){TEXT}('*'|'_')
BOLD_ITALIC={BOLD}{TEXT}*{ITALIC}{TEXT}{ITALIC}{TEXT}*{BOLD}
ITALIC_BOLD={ITALIC}{TEXT}*{BOLD}{TEXT}{BOLD}{TEXT}*{ITALIC}
LINK="["{TEXT}"]("{TEXT}"://"{TEXT}")"
LIST=("- "{TEXT}{LINE_BREAK})+

H1='# '{TEXT}*
H2='## '{TEXT}*
H3='### '{TEXT}*
H4='#### '{TEXT}*
H5='##### '{TEXT}*
H6='###### '{TEXT}*

%%

/*Reglas*/
{H1} {System.out.println("<H1>"+yytext().substring(1)+"</H1>");}
{H2} {System.out.println("<H2>"+yytext().substring(2)+"</H2>");}
{H3} {System.out.println("<H3>"+yytext().substring(3)+"</H3>");}
{H4} {System.out.println("<H4>"+yytext().substring(4)+"</H4>");}
{H5} {System.out.println("<H5>"+yytext().substring(5)+"</H5>");}
{H6} {System.out.println("<H6>"+yytext().substring(6)+"</H6>");}

{NEWLINE} {System.out.println("<HR/>"); }

{BOLD} {
    if(isBold) {
        System.out.println("</SPAN>");
        isBold = false;
    } else{
        System.out.println("<SPAN class='bold'>");
        isBold = true;
    }
}

{ITALIC} {
    if(isItal) {
        System.out.println("</SPAN>");
        isItal = false;
    } else{
        System.out.println("<SPAN class='ital'>");
        isItal = true;
    }
}

{CODE} {
    if(isCode) {
        System.out.println("</pre> </code>");
        isCode = false;
    } else{
        System.out.println("<code> <pre>");
        isCode = true;
    }
}

{QUOTE} {
    if(isQuote){
        System.out.println("</BLOCKQUOTE>");
        isQuote = false;
    }else{
        System.out.println("<BLOCKQUOTE>");
        isQuote = true;
    }
}

{LINK} {
    int t=0, l=0, l2=0;
    int aux, aux2;
    String form;
    String s = yytext();
    for(int i = 0; i < yylength() && l2==0; i++){
        if(s.charAt(i) == ']') t = i;
        if(s.charAt(i) == '(') l= i+1;
        if(s.charAt(i) == ':') l2 = i;
    }
    System.out.print("<A HREF='"+s.substring(1,yylength()-1)+">");
    System.out.print(s.substring(1,t)+" </A>");
    form = s.substring(l,l2);
    aux = form.compareTo("http");
    aux2 = form.compareTo("https");
    if(aux != 0 || aux2 != 0) System.out.println("(URL aparentemente incorrecta)");
    else System.out.println();
}

{BOLD_ITALIC} {
    char aux;
    boolean ital = false;
    int len = yylength()-2;
    String s = yytext().substring(2,len);
    System.out.print("<SPAN class='bold'>");
    for(int i = 2; i < len; i++){
        aux = s.charAt(i);
        if(aux!='*' && aux !='_') System.out.print(aux);
        else{
            if(ital){
                ital = false;
                System.out.print("</SPAN");
            }else{
                ital=true;
                System.out.print("<SPAN class='ital'>");
            }
        }
    }
    System.out.print("</SPAN>");
}

{ITALIC_BOLD} {
    int c;	
    System.out.print("<SPAN class='ital'>");
    c=0; 
    for (int i=1; i<yylength()-1; i++){
        if(c == 0){
           if( (yycharat(i)=='_') && (yycharat(i+1)=='_') ){ 
                c++;
                System.out.print("<SPAN class='bold'>"); 
                i++;
            }
            else if( (yycharat(i)=='*') && (yycharat(i+1)=='*') ){ 
                c++;
                i++;
                System.out.print("<SPAN class='bold'>"); 
            } 
            else System.out.print(yycharat(i));
        }
        else{ // cierre de bold
            if( (yycharat(i)=='_') && (yycharat(i+1)=='_') ){
                System.out.print("</SPAN>"); 
                c = 0;
                i++;
            }
            else if( (yycharat(i)=='*') && (yycharat(i+1)=='*')  ){
                c = 0;
                System.out.print("</SPAN>"); 
                i++;
            }
            else System.out.print(yycharat(i));
        }
    }
    // cierre de ital
    System.out.print("</SPAN>");
}

{LIST} {
    String s = yytext();
    System.out.println("<UL>");
    String [] aux = s.split("- "); 
    for(int i = 0; i < aux.length; i++){
        System.out.println("<LI>"+aux[i]+"</LI>");
    }
    System.out.println("</UL>");
}

{LINE_BREAK} {
    System.out.println();
}
