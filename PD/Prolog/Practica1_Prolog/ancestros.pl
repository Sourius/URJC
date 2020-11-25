/*******************************************************
Nombre: ancestros.pl
Autor: PD-PL
Descripci�n: Distintas formas de definir el predicado
"ancestro" en funci�n  del orden de las cl�usulas
en el programa y del orden de los literales en las
cl�usulas.
*******************************************************/

% progenitor_a(?X, ?Y)
% cierto si X es progenitor/a de Y

progenitor_a(pepa, pepito).
progenitor_a(pepito, pepon).

% ancestro(?X, ?Y)
% cierto si X es un ancestro de Y

% VERSI�N 1
ancestro1(X, Y) :- progenitor_a(X,Y).
ancestro1(X, Y) :- progenitor_a(X,Z), ancestro1(Z, Y).

% VERSI�N 2
ancestro2(X, Y) :- progenitor_a(X, Z), ancestro2(Z, Y).
ancestro2(X, Y) :- progenitor_a(X, Y).

