suma(X,0,X).
suma(X,s(Y),s(Z)) :-
	suma(X,Y,Z).

persona(socrates).
mortal(X):-
    persona(X).

progenitor(pepa,pepito).
progenitor(pepito,pepita).
progenitor(pepito,pepon).
abuela(X,Y):-
    progenitor(X,Z),
    progenitor(Z,Y).

enemigo(abel,cain).
enemigo(abel, cain).
enemigo(cain, blas).
enemigo(cain, dolores).
enemigo(blas, abilio).

amigo(abilio, X) :-
    amigo(abel, X).

amigo(X,Y) :-
    enemigo(X,Z),
    enemigo(Z,Y).

factorial(0, s(0)).
factorial(s(X), Y*s(X)) :-
	factorial(X,Y).