%%
%unicode
%int
%caseless
%standalone
%class lexer
%state NOMBRE
%state NOMSAL
%state NOMFECH

comentario = "{"[{}]*"}"

int = [iI][nN][tT]
char = [cC][hH][aH][rR]
boolean = [bB][oO][oO][lL][eE][aA][nN]
record = [rR][eE][cC][oO][rR][dD]

function = [fF][uU][nN][cC][tT][iI][oO][nN]

while = [wW][hH][iI][lL][eE]
for = [fF][oO][rR]
repeat = [rR][eE][pP][eE][aA][tT]
if = [iI][fF]
else = [eE][lL][sS][eE] 
parIzq = "("
parDer = ")"

espacios = [ \n\t]+

id = {letra}({letra}|{num})*

opsum = [+-]
opmult = [*\/]|[dD][iI][vV]|[mM][oO][dD]
oprel = <>|=|>|<|>=|<=|[aA][nN][dD]|[oO][rR]|[nN][oO][tT]
assig = :=

num = [0-9]
nums = {num}+

coma = ,

letra = [a-zA-Z_]


%%
<YYINITIAL>{
            {comentario}                                  {}
            
            {espacios}                                    {}
            
            {function}                                    {System.out.println("<FUNCTION>");}
            
            {while}                                       {System.out.println("<WHILE>");}
            {for}                                         {System.out.println("<FOR>");}
            {repeat}                                      {System.out.println("<REPEAT>");}
            {if}                                          {System.out.println("<IF>");}
            {else}                                        {System.out.println("<ELSE>");}
            {parIzq}                                      {System.out.println("<PARIZQ>");}
            {parDer}                                      {System.out.println("<PARDER>");}
            
            {parDer}                                      {System.out.println("<PARDER>");}
            
            {opsum}                                       {System.out.println("<OPSUM, \"" + yytext() + "\" >");}
            {opmult}                                      {System.out.println("<OPMULT, \"" + yytext() + "\" >");}
            {oprel}                                       {System.out.println("<OPREL, \"" + yytext() + "\" >");}
            {assig}                                       {System.out.println("<ASSIG>");}
            
            {int}                                         {System.out.println("<INT>");}
            {char}                                        {System.out.println("<CHAR>");}
            {boolean}                                     {System.out.println("<BOOLEAN>");}
            {record}                                      {System.out.println("<RECORD>");}
            
            {nums}                                        {System.out.println("<NUMS>");}
            
            {id}                                          {System.out.println("<ID, \"" + yytext() + "\" >");}
            
            {coma}                                        {System.out.println("<COMA>");}
            
            .            {}
}
