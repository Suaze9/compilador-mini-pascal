echo off
cd "..\arbol\"
CALL javac AssigNode.java AttrNode.java BoolAndNode.java BoolMathNode.java BoolNode.java BoolOrNode.java DeclNode.java ForNode.java FuncCallNode.java FunctionNode.java IfNode.java MathMult.java MathNode.java MathSum.java ParamsNode.java ProcedureNode.java ProgramNode.java ReadNode.java RepeatNode.java Value.java WhileNode.java WriteNode.java RecordNode.java
cd "..\Semantico"
pause
rem AGREGAR CUALQUIER CLASE NUEVA EN LA LINEA DE ABAJO :V
CALL javac -cp "..";. Main.java
pause