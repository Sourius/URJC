module Practica4_Tema4 where

-- Ej 1 --
data Item = Item {
	author :: String,
	title :: String,
	year :: Integer,
	vNumber :: Integer
} deriving Show

-- a --
instance Eq Item where
	a1 == a2 =  (author a1 == author a2) 
				&& (year a1 == year a2)
				&& (title a1 == title a2)
					
instance Ord Item where 
	a1 < a2 =   (author a1 < author a2)  
				|| ((author a1 == author a2) && (year a1 < year a2))
				|| ((year a1 == year a2) && (title a1 < title a2))
					
	a1 <= a2 = (author a1 <= author a2) 
				|| ((author a1 > author a2) && (year a1 <= year a2)) 
				|| ((year a1 > year a2) && (title a1 <= title a2))  
	
	
-- b --
mostrar :: Item -> String 
-- añadir saltos de linea --
mostrar it = "Autor: "++(author it)++", Titulo: "++(title it)++
				", Anyo: "++ show(year it)++
				", Volumen: "++ show(vNumber it)


