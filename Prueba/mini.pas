program test
var 
    x, y, z, i, res : integer;
    boo, lean : boolean;
    c : char;

begin
    x := 3;
    y := 4;
    z := 5;

    res := (9 + 3 - 5) * z;

    while(not(x > 10)) do begin
        x:= x + 1;
    end;

    write('\nx: ', x);
    write('\ny: ', y);
    write('\nz: ', z);

    repeat begin
        write('\nIngrese el valor de res (un numero cuyo cuadrado sea 25): ');
        read(res);
        write('ingerso el numero: ', res);
        i := res * res;
        write('\ncuyo cuadrado es: ', i);
        if(i <> 25) then
            write('\nel cuadrado de res no es 25...\n');
    end;
    until(i = 25);
    write('\nBien!!!!');
    
end;