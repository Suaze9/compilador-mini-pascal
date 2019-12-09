program test
var 
    a, b, c, d: integer;

procedure exponent(e, f: integer)

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
    a:= 2;
    b:= 3;
    c:= 4;
    d:= 5;

    exponent(2, 2);
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
