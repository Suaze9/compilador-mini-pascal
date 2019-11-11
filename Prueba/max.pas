program exFunction
type
   olo := record begin
      olo1 : integer;
      olo2, olo3 : char;
   end;
   olo2 := record begin
      olo1 : integer;
      olo2, olo3 : char;
      olo4, olo5 : boolean;
   end;
end;
var
   a, b, ret : integer;
   c, d, e:char;
   pruebaOlo : olo;

function max(num1, num2: integer): integer
var
   result: integer;

begin
   if (olo()) then begin
      result := 2 * a;
      result := num2 - 4 / b;
   end;
   else
      result := num2;
   max := result;
end;
function maxa(num1a, num2a: integer): integer
var
   result: integer;

begin
   if (olo()) then begin
      result := 2 * a;
      result := num2 - 4 / b;
   end;
   else
      result := maxa(a);
   max := result;
end;

begin
   a := 100;
   b := 'a';
   ret := max(' ', '   ', c, 1+2, 5<7, a AND b, a or c);
   
   write( 'Max value is : ', ret );
end;