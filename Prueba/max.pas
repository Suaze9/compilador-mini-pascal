program exFunction
var
   a, b, ret : integer
   c, d, e:char

function max(num1, num2: integer): integer
var
   result: integer

begin
   if (num1 > num2) then begin
      result := num1 + 2 * a;
      result := num2 - 4 / b;
   end
   else
      result := num2;
   max := result;
end;

begin
   a := 100;
   b := 200;
   ret := max(a, b);
   
   write( 'Max value is : ', ret )
end;