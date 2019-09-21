program rel

var
a, b, c: integer

{  
  Comentario multilinea 
  Wow
}

begin
  a := 21
  b := 10

  if a = b then
    write('a es igual que b')
  else
    write('a es distinto que b')

  { otro comentario entre codigo }
  if a < b then
    write('a es menor que b')
  else
    write('a no es menor que b')

  if a > b then
    write('a es mayor que b')
  else
    write('a no es mayor que b')

  { Lets change value of a and b }
  a := 5
  b := 20

  if a <= b then
    write('a es menor que o igual que b')

  if (b >= a) then
    write('b es mayor que o igual que a')

  { while loop }
  c := 10
  while c < 20 do
  begin
    write('[while] valor de c: ', c)
    c := c + 1
  end

  { for loop }
  for c := 10 to 20 do
  begin
    write('(for) valor de c: ', c)
  end

  { repeat until }
  c := 10
  repeat
    write('#repeat# valor de c: ', c)
    c := c + 1
  until c = 20
end
