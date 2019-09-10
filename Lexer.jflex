import java_cup.runtime.*;
import java.io.Reader;

import java_cup.runtime.Symbol;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;

%%
%unicode
%int
%line
%column
%caseless
%cup

%class lexer

%state COMMENT
%state TEXT

%{
  public static String texto = "";
%}

comentarioIn = \{
comentarioOut = \}

comillas= \'

int = [iI][nN][tT][eE][gG][eE][rR]
char = [cC][hH][aH][rR]
boolean = [bB][oO][oO][lL][eE][aA][nN]
record = [rR][eE][cC][oO][rR][dD]

read = [rR][eE][aA][dD]
write = [wW][rR][iI][tT][eE]

program = [pP][rR][oO][gG][rR][aA][mM]
function = [fF][uU][nN][cC][tT][iI][oO][nN]

begin = [bB][eE][gG][iI][nN]
end = [eE][nN][dD]

while = [wW][hH][iI][lL][eE]
for = [fF][oO][rR]
repeat = [rR][eE][pP][eE][aA][tT]
if = [iI][fF]
else = [eE][lL][sS][eE] 
parIzq = "("
parDer = ")"


espacios = [ \n\t]+

opsum = [+-]
opmult = [*\/]|[dD][iI][vV]|[mM][oO][dD]
oprel = <>|=|>|<|>=|<=|[aA][nN][dD]|[oO][rR]|[nN][oO][tT]
assig = :=

id = {letra}({letra}|{num})*

num = [0-9]
nums = {num}+

coma = ,

letra = [a-zA-Z_]


%%
<YYINITIAL>{
  {comentarioIn}  {yybegin(COMMENT);}            
  {espacios}      {}
  {comillas}      {yybegin(TEXT);}            
  {function}      {return symbol("FUNCTION", sym.FUNCTION);}
  {program}       {return symbol("PROGRAM", sym.PROGRAM);}       
  {begin}         {return symbol("BEGIN", sym.BEGIN);}       
  {end}           {return symbol("END", sym.END);}       
  {for}           {return symbol("FOR", sym.FOR);}
  {while}         {return symbol("WHILE", sym.WHILE);}
  {repeat}        {return symbol("REPEAT", sym.REPEAT);}
  {if}            {return symbol("IF", sym.IF);}
  {else}          {return symbol("ELSE", sym.ELSE);}
  {parIzq}        {return symbol("PARIZQ", sym.PARIZQ);}
  {parDer}        {return symbol("PARDER", sym.PARDER);}
  {read}          {return symbol("READ", sym.READ);}
  {write}         {return symbol("WRITE", sym.WRITE);}            
  {parDer}        {return symbol("PARDER", sym.PARDER);}            
  {opsum}         {return symbol("OPSUM", sym.OPSUM, yytext());}
  {opmult}        {return symbol("OPMULT", sym.OPMULT, yytext());}
  {oprel}         {return symbol("OPREL", sym.OPREL, yytext());}
  {assig}         {return symbol("ASSIG", sym.ASSIG);}            
  {int}           {return symbol("INT", sym.INT);}
  {char}          {return symbol("CHAR", sym.CHAR);}
  {boolean}       {return symbol("BOOLEAN", sym.BOOLEAN);}
  {record}        {return symbol("RECORD", sym.RECORD);}            
  {nums}          {return symbol("NUMS", sym.NUMS);}            
  {id}            {return symbol("ID", sym.ID, yytext());}            
  {coma}          {return symbol("COMA", sym.COMA);}            
  .               {System.out.println("Unrecognized token: " + yytext() + " at line " + yyline + " column " + yycolumn);}
}

<COMMENT>{
  {comentarioOut} { texto = "";
                    yybegin(YYINITIAL);
                    return symbol("COMMENT", sym.COMMENT, texto);
                  }
  .               {texto += yytext();}
}

<TEXT>{
  {comillas}  { texto = "";
                yybegin(YYINITIAL);
                return symbol("TEXT", sym.TEXT, texto);
              }
  .           {texto += yytext();}
}