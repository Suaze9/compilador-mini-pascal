program relOpp

var
a, b, c, d, e: integer
f, g : Boolean
function comparacion(x, y:integer):Boolean
begin
    if(x <= 30) then
      if(x >= y) then 
        {mientras y sea menor o igual que x, sumar 1 a y}
        while (y <= x) do
          y := y + 1
      else
        while (x <= x) do
        begin
          x := x + 1
          write('while multilinea')
        end
    else
      write('x es muy grande: ', x)
end
{
⠀⠀⠀⣠⣾⣿⣿⣿⣷⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⣾⣿⣿⣿⣿⣷⣄⠀
⠀⠀⠀⣿⣿⡇⠀⠀⢸⣿⢰⣿⡆⠀⣾⣿⡆⠀⣾⣷⠀⣿⣿⡇⠀⠀⢸⣿⣿⠀
⠀⠀⠀⣿⣿⡇⠀⠀⢸⣿⠘⣿⣿⣤⣿⣿⣿⣤⣿⡇⠀⢻⣿⡇⠀⠀⢸⣿⣿⠀
⠀⠀⠀⣿⣿⡇⠀⠀⢸⡿⠀⢹⣿⣿⣿⣿⣿⣿⣿⠁⠀⢸⣿⣇⠀⠀⢸⣿⣿⠀
⠀⠀⠀⠙⢿⣷⣶⣶⡿⠁⠀⠈⣿⣿⠟⠀⣿⣿⠇⠀⠀⠈⠻⣿⣿⣿⣿⡿⠋
}

begin
  a := 10
  b := 20
  c := 15
  d := 30
  f := true

  IF(comparacion(a, b) AND 2 = a) then
  begin
    write('Entro')
    repeat begin
      f :=  (c > d)
      c := c + 1
      d := d - 1
      end
    until ( f and c < d )
  end

end
