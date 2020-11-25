module Ejercicios_Tema_2 where
-- Ej 1 --
ordenados :: [Int] -> Bool
ordenados [] = False
ordenados (x:xs) = if length xs >= 1 then 
						if x > (head xs) then False 
						else ordenados xs
					else True
					
-- Ej 2 --
ordenarTupla :: (Int,Int,Int) -> (Int,Int,Int)
ordenarTupla (x,y,z)
		| (x < y  && x < z) =
			if (y < z) then (x,y,z) else (x,z,y)
		| (y < z && y < x) = 
			if (x < z) then (y,x,z) else (y,z,x)
		| otherwise = if (x < y) then (z,x,y) else (z,y,x)

-- Ej 4 --
lArea :: Float -> (Float, Float)
lArea ra = 	let piNum = 3.14 
			in (2*piNum*ra, piNum * ra^2) 

lArea2 :: Float -> (Float, Float)
lArea2 ra = (2*piNum*ra, piNum * ra^2) 
			where piNum = 3.14

-- Ej 43 -
codifica :: [Int] -> [Char]
codifica numS = [sol| x <- [0..length numS -1], 
				let sol = if (x `mod` 2) == 0 then 'p' else 'i']
 
