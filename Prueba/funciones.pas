program test
var 
    a, b, c, d: integer;

function exponent(e, f: integer):integer

var
    resp : integer;
    i : integer;

begin
    resp := e;
    for i := 1 to f - 1 do
        begin
        resp := resp * e;
        end;
    write('El numero ', e);
    write('\nElevado a la potencia de ', f);
    write('\nEs igual a :', resp);  
    exponent := resp;
end;

procedure numerosPares(e, f: integer)

var
    i : integer;

begin
    for i := e to f do
        begin
        if (not(i/2 = (i-1)/2)) then
            write('\nEl numero par: ', i);
        end;
end;

begin
    c:= 4;
    d:= 5;

    write('\nIngrese el numero base: ');
    read(a);
    write('\nIngrese el exponente: ');
    read(b);
    write('\n');
    d := exponent(a, b);
    write('\nA Ver Prroooo: ', d);
    write('\n');
    numerosPares(4, 20);
    {
    exponent(2, 6);
    write('\n');
    exponent(a, b);
    write('\n');
    exponent( c + 3, 5 / 2);
    write('\n');
    exponent(a, 2);
    write('\n');
    }
    
end;
