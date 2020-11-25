enesimo(X,1,[X|_]):-!.
enesimo(X,N,[_|R]):-
    number(N),
    N>1,
    N1 is N-1,
    enesimo(X,N1,R).

eliminaN([],_,[]).    
eliminaN([_|R],1,R).
eliminaN([C|R],N,L):-
	number(N),
    N > 1,
    N1 is N-1,
    eliminaN(R,N1,L2),
    L = [C|L2].

borrarTodos([],_,[]).
borrarTodos([E|R],E,L):- 
    !,
    borrarTodos(R,E,L).
borrarTodos([C|R],E,[C|L]):-
    borrarTodos(R,E,L).

asceriscos([]).
asceriscos([C|R]):-
    number(C),
    C > 0,
    escribe(C),
    asceriscos(R).

escribe(0):-
    !,
    nl.

escribe(N):-
    N1 is N-1,
    write('*'),
    escribe(N1).