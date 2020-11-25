module Ejercicios_Tema_4 where

-- Ej 1 --
type Nombre = String
type Edad = Integer
type Persona = (Nombre, Edad)

descuentoAbono :: Persona -> Bool
descuentoAbono (_, edad) = (edad > 18 && edad <= 25) || (edad >= 65)

-- Ej 2 --
type Dni = String
type Nota = Float
type NumExp = Integer
type Alumno = (NumExp, Dni, Nota)

aprovado :: Alumno -> Bool
aprovado (numE, numDNI, cal) = cal >= 5

-- Ej 2 b --
data Alumnos = Al	{	dni::String,
						numExp :: Integer,
						nota :: Float} deriving Show

instance Eq Alumnos where
	a1 == a2 = (nota a1) == (nota a2)
	a1 /= a2 = not (a1 == a2)					

instance Ord Alumnos where
		a1 <= a2 = ((nota a1) <= (nota a2))
					|| (((nota a1) == (nota a2)) && ((numExp a1) <= (numExp a2)))
		
		a1 >= a2 = ((nota a1) >= (nota a2))
					|| (((nota a1) == (nota a2)) && ((numExp a1) >= (numExp a2)))
					
		a1 < a2 = ((nota a1) < (nota a2))
					|| (((nota a1) == (nota a2)) && ((numExp a1) < (numExp a2)))
		
		a1 > a2 = ((nota a1) > (nota a2))
					|| (((nota a1) == (nota a2)) && ((numExp a1) > (numExp a2)))
		
