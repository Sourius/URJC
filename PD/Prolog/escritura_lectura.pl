% Operaciones de escritura/lectura
pide_numero(X):-
    write("Insert a number: "),
    nl,
    read(X).

escribe_cuadrado(X):-
    write(X).

cuadrado :-
  pide_numero(X),
  open("Documentos/Prolog/prueba.txt", write, Prueba),
  set_output(Prueba),
  escribe_cuadrado(X),
  close(Prueba),
  set_output(user),
  %user es el identificador de la pantalla
  write('el cuadrado se ha escrito en prueba.txt').

addNumbers(X,Y):-
    number(X),
    number(Y),
    Z is X+Y,
    write(X), write(" + "),write(Y), write(" is "), write(Z).

sumar(X,Y):-
    write("Enter the first number:"),nl,
    read(X),nl,
    write("Enter the second number"),nl,
    read(Y),nl,
    addNumbers(X,Y).