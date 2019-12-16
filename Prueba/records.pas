program records

type
  persona := record begin
    edad, peso, altura: integer;
    inicNom, inicAp: char;
  end;
end;

var
  p: persona;
  resp, tip, fin: integer;

function BMI(a, b, c, d: integer, per : persona, tipo:integer) : integer {Si es tipo 1, el peso esta en kg, sino, esta en lb}
var                                                 {El peso tiene que estar en centimetros}
  res, peso, altura : integer;
begin
  altura := per.altura * per.altura;
  altura := altura / 10000;
  if(tipo = 1) then begin
    res := per.peso / altura;
  end; else
  begin
    peso := (per.peso/2);
    peso := peso - peso/10;
    res := peso / altura;
  end;
  BMI := res;
end;

begin
  repeat begin
    write('\nIngrese Inicial del nombre: ');
    read(p.inicNom);
    write('\nIngrese inicial del apellido: ');
    read(p.inicAp);
    write('\nIngrese una edad: ');
    read(p.edad);


    repeat  begin
      write('\nEn que unidad desea ingresar el peso? (1 = kg / 2 = lb): ');
      read(tip);
      if(tip = 1) then
      begin
        write('\nIngrese un peso (kg): ');
        read(p.peso);
      end; else if (tip = 2) then
      begin
        write('\nIngrese un peso (lb): ');
        read(p.peso);
      end; else
        write('El valor ingresado es invalido');
    end; until (tip = 1 or tip = 2);

    write('\nIngrese unaa altura (cm): ');
    read(p.altura);

    write('\nNombre: ', p.inicNom);
    write('. ', p.inicAp);
    write('.\nEdad: ', p.edad);
    write('\nPeso: ', p.peso);
    write('\nAltura: ', p.altura);

    write('\n');

    resp := BMI(1, 2, 3, 4, p, tip);

    write('\nSu BMI es de: ' , resp);

    write('\n\nDesea salir del programa? (1 = si / 2 = no)');
    read(fin);
    write('\n');
  end; until (fin = 1);
  write('\nGracias por utilizar el programa :D');
end;
