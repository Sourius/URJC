% natural(+X), 
% cierto si X es un numero natural. 
natural(X):-
    integer(X), X >= 0.

% fib(+X,?Y), 
% cierto si Y es el nuumero de Fibonacci asociado con X.
fib(0,Y):- !, Y = 1.
fib(1,Y):- !, Y = 1.

fib(X,Y):-
    natural(X),
    X1 is X-1,
    X2 is X-2,
    fib(X1,Y1),
    fib(X2,Y2),
    Y is Y1+Y2.

% mcd(+M,+N,?MCD), 
% cierto si MCD es el maximo comun divisor (mcd) de M y N.


mcd(0,X,Z):-
    !,
    number(X),
    Z = X.

mcd(X,Y,Z):-
    number(X),
    number(Y),
    Y =\= 0,
    X mod Y =< 0,
    !,
    Z = Y.

mcd(X,Y,Z):-
	number(X),
    number(Y),
    Y =\= 0,
    Y1 is X mod Y,
    mcd(Y,Y1,Z).
    
%exp(+M,+N,?E), 
%cierto si E es igual a M elevado a N.
exp(M,0,E):-
    !,
    number(M),
    E = 1.

% para numeros positivos
exp(M,N,E) :-
    number(M),
    natural(N),
    X is N-1,
    !,
    exp(M,X,Y),
    E is M*Y.
    
% para numeros negativos
exp(M,N,E):-
    number(M),
    number(N),
    X is N+1,
    exp(M,X,Y),
    Z is 1/M,
    E is Z*Y.
    
% num_t(+N,?T),
% cierto si T es el numero triangular asociado con N
num_t(0,0):-!.

num_t(X,Y):-
    number(X),
    X>0,
    X1 is X-1,
    numt_t(X1,Y1),
    Y is X+Y1.

