/*******************************************************
Nombre: ancestros.pl
Autor: PD-PL
Descripción: Distintas formas de definir el predicado
"ancestro" en función  del orden de las cláusulas
en el programa y del orden de los literales en las
cláusulas.
*******************************************************/

% progenitor_a(?X, ?Y)
% cierto si X es progenitor/a de Y

progenitor_a(pepa, pepito).
progenitor_a(pepito, pepon).

% ancestro(?X, ?Y)
% cierto si X es un ancestro de Y

% VERSIÓN 1
ancestro1(X, Y) :- progenitor_a(X,Y).
ancestro1(X, Y) :- progenitor_a(X,Z), ancestro1(Z, Y).

% VERSIÓN 2
ancestro2(X, Y) :- progenitor_a(X, Z), ancestro2(Z, Y).
ancestro2(X, Y) :- progenitor_a(X, Y).

