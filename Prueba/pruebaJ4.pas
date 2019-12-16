program pruebaJ4

type
    pro := record begin
        puntos:integer;
        inicial: char;
    end;
end;

var
    p: pro;
    a: integer;
    b: char;

function retPro(m: pro): pro
var
    ret: pro;
begin
    write('\n antes: ', m.puntos);
    write('\n antes: ', m.inicial);
    ret.puntos := -100;
    ret.inicial := 'j';
    retPro := ret;
end;

begin
    write('ingrese puntos: ');
    read(p.puntos);
    write('ingrese inicial: ');
    read(p.inicial);
    p:= retPro(p);
    write('\n desps: ', p.puntos);
    write('\n desps: ', p.inicial);
end;