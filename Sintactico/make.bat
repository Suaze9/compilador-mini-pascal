CALL jflex .\Lexer.jflex
pause
CALL java -cp "..\jars\*"; java_cup.Main -expect 3 < parser.cup
pause
cd "..\arbol\"
CALL javac AssigNode.java Node.java AttrNode.java BoolAndNode.java BoolMathNode.java BoolNode.java BoolOrNode.java DeclNode.java ForNode.java FuncCallNode.java FunctionNode.java IfNode.java MathMult.java MathNode.java MathSum.java ParamsNode.java ProcedureNode.java ProgramNode.java ReadNode.java RepeatNode.java Value.java WhileNode.java WriteNode.java RecordNode.java
cd "..\Sintactico"
pause
rem AGREGAR CUALQUIER CLASE NUEVA EN LA LINEA DE ABAJO :V
CALL javac -cp "..";"..\jars\*";. Main.java TablaSym.java Tupla.java parser.java lexer.java sym.java
pause