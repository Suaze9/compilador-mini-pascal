program test
var 
    a, b: integer;


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
    exponent := resp;
end;

function exponentRec(e, f: integer):integer
var
    resp :integer;
begin
    if(f = 0) then begin
        resp := 1;
    end;
    else begin
        resp := e * exponentRec(e, f - 1);
    end;
    exponentRec := resp;
end;

procedure numerosPares(e, f: integer)

var
    i : integer;

begin
    for i := e to f do
        begin
        if ( not( i/2 = (i-1)/2 ) ) then
            write('\nEl numero par: ', i);
        end;
end;

begin

    write('\nIngrese el numero base: ');
    read(a);
    write('\nIngrese el exponente: ');
    read(b);
    write('\n\nExponente Recursivo');
    d := exponentRec(a, b);
    write('\n\nResp: ', d);
    write('\n\nExponente Iterativo');
    d := exponent(a, b);
    write('\n\nResp: ', d);
    write('\n');
    numerosPares(a, b);
    write('\n');

    
end;
