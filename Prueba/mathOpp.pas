program test
var x, y, z, res : integer

procedure xaddyz()
begin
    x := y+z;
end;

procedure zmulxy()
begin
    z := x*y;
end;

function sum(a, b: integer):integer
begin
    sum := a + b;
end;

begin
    x := 3;
    y := 4;
    z := 5;
    xaddyz();
    zmulxy();

    res := x + 6 / y * (9 + 2 - 4) * z;

    write('',res);
    
    res := sum(x, y) * z / sum(2, y);

    write('',res);
end;