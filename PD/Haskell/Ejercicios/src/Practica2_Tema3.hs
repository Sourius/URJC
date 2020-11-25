module Practica2 where
import Data.Char

-- Ej 1 --
eliminarFil :: Eq e => [e]-> e -> [e]
eliminarFil xs n = filter (/= n) xs

eliminarFL :: Eq e => [e]-> e -> [e]
eliminarFL xs n = foldl (\acc x -> if x /= n then acc++[x] else acc)[] xs

eliminarFR :: Eq e => [e]-> e -> [e]
eliminarFR xs n = foldr (\x acc -> if x /= n then x:acc else acc)[] xs

-- Ej 2 --
partirString :: String -> (String, String)
partirString xs = separarVC (filter (isAlpha) xs) -- Quitar caracteres que no sean del alfabeto 

separarVC :: String -> (String, String)
separarVC xs = (filter (esConstante) xs, filter (esVocal) xs)

partirStringFL :: String -> (String, String)
partirStringFL xs = separarVCFL (filter (isAlpha) xs) -- Quitar caracteres que no sean del alfabeto 

separarVCFL :: String -> (String, String)
separarVCFL xs = (  foldl (\acc x -> if (esConstante x) then acc++[x] else acc)[] xs, 
					foldl (\acc x -> if (esVocal x) then acc++[x] else acc)[] xs		)

partirStringFR :: String -> (String, String)
partirStringFR xs = separarVCFR (filter (isAlpha) xs) -- Quitar caracteres que no sean del alfabeto 											

separarVCFR :: String -> (String, String)
separarVCFR xs = (  foldr (\x acc -> if (esConstante x) then x:acc else acc)[] xs, 
					foldr (\x acc -> if (esVocal x) then x:acc else acc)[] xs		)

esConstante :: Char -> Bool
esConstante = not.esVocal 

esVocal :: Char -> Bool
esVocal c = if isUpper c then esVocal (toLower c) 
			else c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u'		

-- Ej 3 --
convertirCadenas :: [String] -> [String]
convertirCadenas listaCad = map (\ cad -> convertirCadena cad) listaCad

convertirCadena :: String -> String
convertirCadena cad = foldl (\acc f ->  if isLower f then acc ++ [toUpper f] 
										else acc ++ [toLower f]	)"" cad

-- Ej 4 --
filtrarFL ::Eq a => (a -> Bool) -> [a] -> [a]
filtrarFL f xs = foldl (\acc x -> if (f x) then acc++[x] else acc)[] xs 
 
filtrarFR :: (a-> Bool)-> [a] -> [a]
filtrarFR f xs = foldr (\x acc -> if (f x) then [x]++acc else acc)[] xs 
 
-- Ej 5 --
 --mmap :: Eq a => [(a->a)] -> [a] -> [a]
 --mmap (y:fs) xs = foldl(\acc f -> filterFL f acc)(filterFL y xs) fs
 
-- Ej 6 --
filtrarListas :: [[a]] -> Int -> [[a]]
filtrarListas ls n = filter (\l -> (length l) > n) ls
 
filtrarListasFR :: [[a]] -> Int -> [[a]]
filtrarListasFR ls n = foldr (\l acc -> if (length l > n) then l:acc else acc)[] ls
 
filtrarListasFL :: [[a]] -> Int -> [[a]]
filtrarListasFL ls n = foldl (\acc l -> if (length l > n) then acc++[l] else acc)[] ls
 
 -- Ej 7 --
primeraAparicion :: Eq a => [a] -> [(a,Int)]
primeraAparicion xs = primeraAparicion' xs xs 
 
primeraAparicion' :: Eq a => [a] -> [a] -> [(a,Int)]
primeraAparicion' xs ys = filter (\x -> not (isRepeated (fst x) (snd x -1) ys)) (zip xs [1..])

isRepeated :: Eq a => a -> Int -> [a] -> Bool
isRepeated x n xs = if (n <= 1) then False
					else any (== True)[sol | y <- (take n xs),
								let sol = if (x == y) then True else False]
								
-- Ej 8 --
contarApariciones :: Eq a => [a] -> [(a,Int)]
contarApariciones [] = []
contarApariciones (x:xs) = [(x, contarAp x xs 1)]++contarApariciones (eliminarFL xs x)

contarAp :: Eq a => a -> [a] -> Int -> Int
contarAp _ [] ap = ap
contarAp x (y:ys) ap = if (x == y)  then contarAp x ys (ap+1)
									else contarAp x ys (ap)  

-- Ej 8 b --
moda :: Eq a => [a] -> [a]
moda xs = seleccionarModa apa (obtenerMax apa)
				where apa = (contarApariciones xs)

seleccionarModa :: [(a,Int)] -> Int -> [a]
seleccionarModa xs n = foldl (\acc (x1,x2)-> if x2==n then acc++[x1] else acc)[] xs

obtenerMax ::[(a,Int)] -> Int
obtenerMax xs = foldl (\ acc z -> if (z > acc) then z else acc)0 [y|(x,y) <- xs] 

-- Ej 9 --
partirPorMedia :: [Int] -> ([Int],[Int])
partirPorMedia xs = foldl (\acc x -> if (x <= media) then ((fst acc)++[x], snd acc)
						else (fst acc, (snd acc)++[x]))([],[]) xs
						where media = calcularMedia xs

calcularMedia :: [Int] -> Int
calcularMedia xs = div (sum xs) (length xs)

-- Ej 10 --
sustituir :: Eq a => [a]-> a -> a -> [a]
sustituir xs m n = foldr (\(x,y) acc -> if (x == m && mod y 2 /= 0)then ([n]++acc)
										else ([x]++acc))[] (zip xs [1..])



