program func
var
  nombre, a, b, c, d, res: integer

procedure hola(nombre: string)
var
  edad: integer
begin
  write('Hola ', nombre)
  read(edad)
  if edad < 18 then
    write('Aun no puedes manejar, ', nombre)
  else
    write('Ya puedes empezar a manejar, ', nombre)
end

function maths(a, b, c: integer): integer
var
  resultado: integer
begin
  resultado := c + a * b
  maths := resultado
end

begin
  nombre := 'Jose'
  hola(nombre)

  a := 3
  b := 5
  c := 10

  res := maths(a, b, c)

  write('El resultado es ', res)
  {, '+', a, '*', b, '=', maths(a, b, c))}
end