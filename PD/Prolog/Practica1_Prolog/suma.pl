/**************************************************************
Nombre: suma.pl
Autor: PD-PL
Descripci�n: programa l�gico para la suma de n�meros naturales,
realizado en programaci�n l�gica pura (sin utilizar la aritm�tica
de Prolog).
**************************************************************/

% suma(?X, ?Y, ?Z)
% cierto si Z es la suma de X e Y.
suma(X,0,X).
suma(X,s(Y),s(Z)) :- suma(X,Y,Z).





