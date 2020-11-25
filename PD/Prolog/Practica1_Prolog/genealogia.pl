/***********************************************************
Nombre: genealogia.pl
Autor: PD-PL
Descripci�n: programa l�gico para el manejo de relaciones
familiares. Se debe completar de acuerdo con lo pedido en el
enunciado de la pr�ctica
***********************************************************/

% PREDICADOS B�SICOS

% progenitor(?X, ?Y)
% Cierto si X es progenitor de Y

progenitor(pepa, pepe1).
progenitor(pepe, pepe1).
progenitor(pepe, pepa2).
progenitor(pepe1, pepa11).
progenitor(pepe1, pepa12).
progenitor(pepa12, pepe121).

% mujer(?X)
% Cierto si X es una mujer

mujer(pepa).
mujer(pepa2).
mujer(pepa11).
mujer(pepa12).

% varon(?X)
% Cierto si X es un var�n

varon(pepe).
varon(pepe1).
varon(pepe121).


% madre(?X,?Y)
% cierto si X es la madre de Y
madre(X,Y):-
    progenitor(X,Y),
    mujer(X).

% madre(?X)
% cierto si X es madre
madre(X) :-
    madre(X,_).

% hija(?X,?Y)
% cierto si X es la hija de Y
hija(X,Y) :-
    progenitor(Y,X),
    mujer(X).

% abuelo(?X,?Y)
% cierto si X es el abuelo de Y.
abuelo(X,Y):-
    progenitor(X,Z),
    progenitor(Z,Y),
    varon(X).

% abuela(?X,?Y)
% cierto si X es la abuela de Y.
abuela(X,Y):-
    progenitor(X,Z),
    progenitor(Z,Y),
    mujer(X).

% hermana(?X,?Y)
% cierto si X es hermana de Y.
hermana(X,Y):-
    progenitor(Z,X),
    progenitor(Z,Y),
    mujer(X).

% tia(?X,?Y)
% cierto si X es la tia de Y.
tia(X,Y):-
    progenitor(Z,Y),
    hermana(X,Z).

% ancestro(?X,?Y),
% cierto si X es ancestro de Y.
ancestro(X,Y):-
    progenitor(X,Y).

ancestro(X,Y):-
    progenitor(X,Z),
    ancestro(Z,Y).

% pariente(?X,?Y),
% cierto si X es pariente de Y.

pariente(X,X):- false.

pariente(X,Y):-
    ancestro(X,Y).

pariente(X,Y):-
    ancestro(Y,X).

pariente(X,Y):-
    ancestro(Z,X),
    ancestro(Z,Y),
	X\=Y.

