cubos:-
    write('Introduzca el numero (fin para terminar): '),
    nl,
    read(N),
    cubo(N).

cubo(N):-
    N \= fin,
    number(N),
    V is N*N*N,
    write('El cubo de '),
    write(N),
    write(' es :'),
    write(V),
    nl,
    cubos.

cubo(N):-
    N = fin.

prueba(0,_):-!.
prueba(N,T):-
    number(N),
    N > 0,
    N1 is N-1,
    write(T),
    nl,
    prueba(N1,T).