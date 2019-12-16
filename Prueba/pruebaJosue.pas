program pruebaJosue
var
   i,j,fila,cont: integer;

begin
    cont := 0;
    write('Ingrese fila: ');
    read(fila);
   for i := 0  to fila-1 do
   begin
      for j := 0 to fila-1 do
      begin
        if cont <= i then
            begin
                write('*');
                cont:= cont + 1;
            end;
        else
            write(' ');
      end;
      cont := 0;
      write('\n');
   end;
end;