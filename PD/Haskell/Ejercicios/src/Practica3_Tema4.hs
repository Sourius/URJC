module Practica3_Tema4 where
import Data.Char

-- Ej 1 --
data Direccion = Este | Oeste | Norte | Sur 
type Coordenada = (Float, Float)
mover:: Direccion -> Coordenada -> Coordenada 
mover Este  (a,b) = (1+a,b)
mover Oeste (a,b) = (a-1,b)
mover Norte (a,b) = (a,b+1)
mover Sur   (a,b) = (a,b-1)

-- Ej 1, b --
masALSur :: Coordenada -> Coordenada -> Coordenada
masALSur (a,b) (c,d) = if b < d then (a,b) else (c,d)

-- Ej 1, c --
distancia :: Coordenada -> Coordenada -> Float
distancia (a,c) (b,d) = sqrt((b-a)^2+(d-c)^2)

-- Ej 1, d --
masCerca :: Coordenada -> [Coordenada] -> Coordenada
masCerca (a,b) ((x,y):xs) = foldl (\(e,f) (c,d) -> 
	if (distancia (a,b) (c,d)) < (distancia (a,b) (e,f)) then (c,d)
	else (e,f))(x,y) xs 
	
-- Ej 1, e --
camino :: Coordenada -> [Direccion] -> [Coordenada]
camino ini dir = tail (foldl (\acc d-> acc++[mover d (last acc)])[ini] dir)

----
-- Ej 2 --
data Moneda = Euro | Dolar | Libra

euroToDolar :: Moneda -> Float  -> Float
euroToDolar Euro valor = valor * 1.0599999
euroToDolar Dolar valor = valor * 0.9433963

euroToLibras :: Moneda -> Float -> Float
euroToLibras Euro valor = valor * 0.86
euroToLibras Libra valor = valor* 1.1627907

libraToDolar :: Moneda -> Float -> Float
libraToDolar Libra valor = euroToDolar Euro (euroToLibras Libra valor)
libraToDolar Dolar valor = euroToLibras Euro (euroToDolar Dolar valor)

----
-- Ej 3 --
data Arbol a = AV | Rama (Arbol a) a (Arbol a) deriving (Show,Eq)

hojas :: Eq a => Arbol a -> Int 
hojas (AV) = 0
hojas (Rama i a d) = 	if (i == AV && d == AV) then 1	
						else if( i == AV) then hojas d 
						else if (d == AV) then hojas i
						else hojas i + hojas d
					
-- Ej 3 b --
nodos :: Eq a => Arbol a -> Int
nodos AV = 0
nodos (Rama i a d) = 	if (i == AV && d /= AV) then 1+(nodos d) 
						else if (i /= AV && d == AV)then 1+(nodos i)
						else 1 +(nodos i)+(nodos d)

-- Ej 3 c --
profundidad :: Eq a => Arbol a -> Int
profundidad AV = 0
profundidad (Rama i a d) = 	if (i == AV && d /= AV) then 1+(nodos d) 
						else
							if (i /= AV && d == AV)then 1+(nodos i)
							else 1 +(max (nodos i) (nodos d))

espejo :: Eq a => Arbol a -> Arbol a
espejo AV = AV 
espejo (Rama i a d)= (Rama (espejo d) a (espejo i))

-- Ej 4 --
--separarNodos :: Eq a => Arbol a -> ([a],[a])
--separarNodos ar = sepNodos ar ([],[])

--sepNodos :: Eq a => Arbol a -> ([a],[a]) -> ([a],[a])
--sepNodos AV sol = sol
--sepNodos (Rama i a d) sol = 
							
--isRep :: Eq a => a -> Arbol a -> Bool
--isRep b (Rama i a d) = (isRep a i) || (a == b) || (isRep a d)

-- Ej 5 --
arbolPar :: Integral a => Arbol a -> Bool
arbolPar AV = False
arbolPar (Rama i a d)= 
		if (((cuantosPares i) + (cuantosPares d)) > (div (nodos (Rama i a d)) 2))
			then True
			else False			

cuantosPares :: Integral a => Arbol a -> Int
cuantosPares AV = 0
cuantosPares (Rama i a d) = (cuantosPares i) + (cuantosPares d) + val
		where val = if (mod a 2 == 0) then 1 else 0

-- Ej 6 --





