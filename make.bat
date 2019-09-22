CALL jflex .\Lexer.jflex
pause
CALL java -classpath ".\jars\java-cup-11b.jar"; java_cup.Main -expect 2 < parser.cup
pause
rem AGREGAR CUALQUIER CLASE NUEVA EN LA LINEA DE ABAJO :V
CALL javac -classpath ".\jars\java-cup-11b.jar";. Main.java parser.java lexer.java sym.java  BoolAndNode.java BoolNode.java BoolOrNode.java MathMult.java MathNode.java MathSum.java Value.java Identifier.java
pause