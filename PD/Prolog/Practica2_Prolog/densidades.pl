/*******************************************************
Nombre: densidades.pl
Autores: PD. URJC.
Fecha:
Descripci�n: Programa que incluye predicados para
describir el n�mero de habitantes y el �rea de una serie
de pa�ses y calcular a partir de estos datos sus
densidades de poblaci�n.
*******************************************************/

% num_habitantes(?X, ?Y)
% cierto si Y es el n�mero de habitantes (en millones) de Y.

num_habitantes('India', 1140).
num_habitantes('China', 1333).
num_habitantes('Brasil', 192).
num_habitantes('Espania', 47).
 


% area(?X, ?Y)
% cierto si Y es el �rea (en millones de km. cuadrados) de X.
area('India', 3.288).
area('China', 9.597).
area('Brasil', 8.512).
area('Espania', 0.505).


% densidad(?X, ?D)
% cierto si D es la densidad de poblaci�n de X.
densidad(X,D):-
    num_habitantes(X,Y),
    area(X,Z),
    D is round(Y/Z).

