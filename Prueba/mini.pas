program test
var x, y, z, i, res : integer;

begin
    x := 3;
    y := 4;
    z := 5;

    res := (9 + 3 - 5) * z;

    while(not( z > 10 and x > 3)) do begin
        x:= 5;
        x:= y;
        y := 10 * 55;
        z := x + 10 * (3*5+33);
    end;

    z := x + 10 * (3*5+33);

    repeat 
        begin
        x:= 5;
        x:= 5;
        y := 10 * 55;
        z := x + 10 * not(3*5+33);
        end;
    until (not (x < 15 + 2)) ;

    for i := 0 to 10 do
        begin
            write('pene chiquito');
            i:= i + 1;
        end;

    write('',res);
    
    res := z / 2;

    write('',res);
end;