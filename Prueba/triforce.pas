program triforce
var
   i,j,k,fila,cont: integer;

begin
    cont := 0;
    write('Ingrese fila: ');
    read(fila);
    while cont < fila do
    begin
        for i := 0 to 2 do
        begin
            for j := 0  to 3-i do
            begin
                write(' ');
            end;
            for k := 0 to i*2+1 do
            begin
                write('*');
            end;
            write('\n');
        end;
        cont := cont + 1;
    end;
end;