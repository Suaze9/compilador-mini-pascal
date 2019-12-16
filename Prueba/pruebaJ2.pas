program pruebaJ2

var
    a, b, c, d:integer;

function factorial(e: integer): integer
var 
    temp: integer;
begin
    if e = 1 then
    begin
        write('caso base\n');
        temp := 1;
    end;
    else
    begin

        write('\nrecursion',e);
        temp:= e * factorial(e-1);
        write('\ntemp:', temp);
    end;
    write('antes de retornar: ', temp);
    factorial := temp;
end;

begin
    write('Ingrese un numero: ');
    read(a);
    b := factorial(a);
    write('El factorial es: ',b);
end;