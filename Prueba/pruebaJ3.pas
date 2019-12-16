program pruebaJ3

var 
    a,b,c,d,e,f,a1,a2,a3,ho: integer;

function justAdd(g,h,i,j,k,l,m,n,o: integer):integer
    begin
        g:= g+1;
        h:= h+2;
        i:= i+3;
        j:= j+4;
        k:= k+5;
        l:= l+6;
        m:= m+7;
        n:= n + 8;
        o := o + 9;
        justAdd:= g + h + i + j + k + l + m + n + o;
end;

begin 
    write('ingrese a: ');
    read(a);
    write('ingrese b: ');
    read(b);
    write('ingrese c: ');
    read(c);
    write('ingrese d: ');
    read(d);
    write('ingrese e: ');
    read(e);
    write('ingrese f: ');
    read(f);
    write('ingrese a1: ');
    read(a1);
    write('ingrese a2: ');
    read(a2);
    write('ingrese a3: ');
    read(a3);
    ho := justAdd(a,b,c,d,e,f,a1,a2,a3);
    write('la suma es: ', ho);
end;