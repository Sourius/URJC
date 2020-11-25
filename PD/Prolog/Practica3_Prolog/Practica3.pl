% enesimo(?L, +N, ?E),
% cierto si E es el elemento de la lista L situado en la posición N
% La cabeza de la lista se considera posición 1
enesimo([E|_],1,E):-!.
enesimo([_|R],N,E):-
    number(N),
    N >= 1,
    X is N-1,
    enesimo(R,X,E).

% elimina_n(?L, +N, ?NL),
% cierto si NL es la lista sin el elemento enesimo de L
elimina([_|R],1,NL):-
    NL=R,!.

elimina([H|R],N,NL):-
    number(N),
    N >=1,
    X is N-1,
    elimina(R,X,NL2),
    NL = [H|NL2].

% borrartodos(+L,+E,?NL),
% cierto si NL es la lista L si las repeticiones de E
borrartodos([E|R],E,NL):-
    !,
    borrartodos(R,E,NL).

borrartodos([H|R],E,NL):-
    borrartodos(R,E,NL2),
    NL = [H|NL2].































