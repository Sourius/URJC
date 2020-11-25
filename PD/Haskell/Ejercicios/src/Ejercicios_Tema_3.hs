module Ejercicios_Tema_3 where
import Data.Char
import Prelude

-- Ej 1 --
cribarL :: [Int] -> Int -> [Int]
cribarL ns x = [n| n <- ns, mod n x /= 0]

cribarRnf :: [Int] -> Int -> [Int]
cribarRnf [] x = []
cribarRnf (n:ns) x = if (mod n x /= 0) then [n]++ (cribarRnf ns x) else cribarRnf ns x 

cribarRf :: [Int] -> Int -> [Int]
cribarRf (n:ns) x = if (mod n x /= 0) 
						then cribarRf' ns x [n]
						else cribarRf' ns x []

cribarRf' :: [Int] -> Int -> [Int] -> [Int]
cribarRf' [] n sol = sol
cribarRf' (n:ns) x sol = if (mod n x /= 0) 
						then cribarRf' ns x ([n]++sol)
						else cribarRf' ns x sol				
						
-- Ej 2 --
doble :: Int -> Int
doble = \x -> x+x	

-- Ej 3 --
sumaDobles :: [Int] -> Int
sumaDobles = \x -> sum (map (*2) x)

-- Ej 3 Recusividad No Final --
sumaDoblesRnf :: [Int] -> Int
sumaDoblesRnf (x:xs) = (2*x) + sumaDobles xs 

-- Ej 3 Recursividad Final --
sumaDoblesRf :: [Int] -> Int
sumaDoblesRf xs = sumaDoblesRF xs 0

sumaDoblesRF :: [Int] -> Int -> Int
sumaDoblesRF [] sol = sol
sumaDoblesRF (x:xs) sol = sumaDoblesRF xs (sol+2*x)

-- Ej 3 FS foldr --
sumaDoblesFr :: [Int] -> Int
sumaDoblesFr = \x -> foldr (+)(sum x) x

-- Ej 4 --
sumaCP :: [Int]-> Int
sumaCP = \xs -> foldr (+)0 (map (^2) (filter even xs))

-- Ej 4 L --
sumaL :: [Int] -> Int
sumaL = \xs -> sum [y*y| y <- xs, even y]

-- Ej 5 --
contarZeros :: [Int] -> Int
contarZeros [] = 0
contarZeros (x:xs) = if x == 0 then  1+contarZeros (quitarZeros xs)
					 else 0+contarZeros xs

quitarZeros :: [Int] -> [Int]
quitarZeros [] = []
quitarZeros (y:ys) = if (y == 0) then quitarZeros ys
					  else ys

-- Ej 6 --
repeticiones :: [Int] -> ([Int],[Int])
repeticiones xs = separarRep xs ([],[])

separarRep :: [Int] -> ([Int],[Int]) -> ([Int],[Int])
separarRep [] sol = sol
separarRep (x:xs) sol = if (any (==x) xs  || any (==x) (snd sol))
						  then 
								if any (==x) (snd sol)
								then separarRep xs (fst sol , snd sol)
								else separarRep xs (fst sol, [x]++snd sol)
						  else separarRep xs ([x]++fst sol, snd sol)

-- Ej 7 --
incluye :: [Int] -> [Int] -> Bool
incluye [] _ = True
incluye x y = if (length x <= length y) then
				if (contains (zip x y)) then True else incluye x (tail y)
			  else False

contains :: [(Int,Int)] -> Bool
contains [] = True
contains ((x,y):xs) = if (x == y) then (contains xs) else False

-- Ej 8 --
sumaCifras :: Int -> Int
sumaCifras = \num -> foldr (+)0 (separarDigitos num)

separarDigitos :: Int -> [Int]
separarDigitos 0 = []
separarDigitos x =  (separarDigitos (div x 10)) ++ [mod x 10]

-- Ej 9 --
contieneCifra :: Int -> Int -> Bool
contieneCifra = \x xs -> any (==x) (separarDigitos xs)

-- Ej 10 --
invertirNumero :: Int -> Int
invertirNumero x =  juntarDigitos (separarDigitos x) 0

juntarDigitos :: [Int] -> Int -> Int
juntarDigitos [] n = n
juntarDigitos (x:xs) n = (juntarDigitos xs (n*10+x) )

-- Ej 11 --
eliminarUHPF :: [Int] -> Int -> [Int]
eliminarUHPF = \ xs n -> take (length xs - n) xs

eliminarURNF :: [Int] -> Int -> [Int]
eliminarURNF (x:xs) n = if length xs >= n then [x]++(eliminarURNF xs n) else []

eliminarURF :: [Int] -> Int -> [Int]
eliminarURF xs 0 = xs
eliminarURF xs n = eliminarURF (init xs) (n-1)
 
-- Ej 12 --
ordenados :: [Int] -> Bool
ordenados (x:xs) = if length xs == 0 then True else
						 if (x > (head xs)) then False else (ordenados xs)

-- Ej 14 --
elimApFl :: Int -> [Int] -> [Int]
elimApFl n xs = foldl (\acc x-> if x == n then acc else acc++[x] )[] xs

-- Ej 15 --
sinDuplicados :: [Int] -> [Int]
sinDuplicados [] = []
sinDuplicados (x:xs) = [x]++(sinDuplicados (eliminarDuplicados x xs))

eliminarDuplicados ::Int -> [Int] -> [Int]
eliminarDuplicados n xs = foldl(\acc y -> if (y/= n) then acc++[y] else acc)[] xs
 
-- Ej 17 --
soloPrimosR :: [Int]-> [Int]
soloPrimosR [] = []
soloPrimosR (x:xs) = 	if (sum (divisores x) == 1) then [x]++soloPrimosR xs
						else soloPrimosR xs

soloPrimosF :: [Int]-> [Int]
soloPrimosF xs = filter(\x -> esPrimo x) xs

soloPrimosFL :: [Int] -> [Int]
soloPrimosFL = \ xs -> foldl (\acc x -> if (esPrimo x) then acc++[x] else acc)[] xs

soloPrimosFR :: [Int] -> [Int]
soloPrimosFR = \ xs -> foldr (\x acc -> if (esPrimo x) then [x]++acc else acc)[] xs

esPrimo :: Int -> Bool
esPrimo 1 = True
esPrimo n 
 		| sum (divisores n) == 1 = True
 		| otherwise = False
 
divisores :: Int -> [Int]
divisores n = [x|x<-[1..n-1], mod n x == 0]

-- Ej 18 --
mezclarEnTernas :: [a] -> [b] -> [(a,b,b)]
mezclarEnTernas [] _ = []
mezclarEnTernas _ [] = []
mezclarEnTernas _ [b] = []
mezclarEnTernas (x:xs)(y:z:ys) = (x,y,z) : (mezclarEnTernas xs ys)

-- Ej 19 --
alFinal :: Int -> [Int] -> [Int]
alFinal n [] = [n]
alFinal n xs = xs ++ [n]

alFinalFR :: Int -> [Int] -> [Int]
alFinalFR n xs = foldr (\x acc -> x:acc)[n] xs

-- Ej 20 --
invertirListaRNF :: [a] -> [a]
invertirListaRNF [] = []
invertirListaRNF (x:xs) = invertirListaRNF xs ++ [x]

invertirListaRF :: [a] -> [a]
invertirListaRF xs = invertirListaRF' xs []

invertirListaRF' :: [a] -> [a] -> [a]
invertirListaRF' [] sol = sol
invertirListaRF' (x:xs) sol = invertirListaRF' xs (x:sol)

invertirListaFR :: [a] -> [a]
invertirListaFR xs = foldr (\x acc -> acc++[x])[] xs

-- Ej 21 --
invertirListas :: [[a]] -> [[a]]
invertirListas xs = foldr (\ys acc -> acc++[invertirListaRF ys])[] xs

-- Ej 22 -- 
longitudCadenas :: [String] -> [Int]
longitudCadenas xs = map (\x -> length x) xs

longitudCLC :: [String] -> [Int]
longitudCLC xs = [length x| x <- xs]

longitudCFR :: [String] -> [Int]
longitudCFR xs = foldr (\x acc -> [length x]++acc)[] xs

longitudCR :: [String] -> [Int]
longitudCR [] = []
longitudCR (x:xs) = [length x]++ longitudCR xs

-- Ej 23 --
obtenerMCD :: Int -> Int -> Int
obtenerMCD a b = if (mod a b == 0) then b 
				else if (a < b) then (mcd a b a)
				else (mcd a b b)
 					
mcd :: Int -> Int -> Int -> Int 
mcd a b c = if (mod a c == 0 && mod b c == 0) then c 
			else mcd a b (c-1)
			
-- Ej 24 --
productoRNF :: Int -> Int -> Int
productoRNF a 0 = 0
productoRNF 0 a = 0
productoRNF a b = a+(productoRNF a (b-1))

productoRF :: Int -> Int -> Int
productoRF a b = if (a > b) then productoRF' a b 0 else productoRF' b a 0

productoRF' :: Int -> Int -> Int -> Int
productoRF'  a 0 sol = sol
productoRF'  0 b sol= sol
productoRF' a b sol = productoRF' a (b-1) (sol+a)

-- Ej 25 --
primeros :: [(Char, Int)] -> String
primeros xs = foldr (\(c,i) acc -> [c]++acc)[] xs

-- Ej 26 --
primerosPares :: [(Char, Int)] -> String
primerosPares xs = foldr (\(c,i) acc -> if (mod i 2 == 0) then [c]++acc
										else acc) [] xs
										
-- Ej 27 --
takeWhile2 :: Eq a => (a-> Bool) -> [a] -> [a]
takeWhile2 f (x:xs) = if (f x) then [x]++takeWhile2 f xs else []

-- Ej 28 --
dropWhile2 :: Eq a=> (a->Bool) -> [a] -> [a]
dropWhile2 f (x:xs) = if (f x) then (dropWhile2 f xs) else foldl(\acc y -> acc++[y])[x] xs

-- Ej 29 --
todosPares :: [Int] -> Bool
todosPares xs = foldr (\x acc -> x && acc)True (map even xs)

todosParesAll :: [Int] -> Bool
todosParesAll xs = all even xs

-- Ej 30 --
paresM5 :: [Int] -> [Int]
paresM5 xs = filter (>5) xs

paresM5' :: [Int] -> [Int]
paresM5' xs = filter (\x -> mayor x 5) xs

mayor :: Int -> Int -> Bool
mayor a b = a > b

mayorMSL :: [Int] -> [Int]
mayorMSL xs = filter (\x -> x > 5) xs

-- Ej 32 --
potencia :: Int -> Int -> Int
potencia m 0 = 1
potencia m n = m * potencia m (n-1)

potenciaRF :: Int -> Int -> Int
potenciaRF a b = potenciaRF' a b 1

potenciaRF' ::  Int -> Int -> Int -> Int
potenciaRF' _ 0 sol = sol
potenciaRF' m n sol = potenciaRF' m (n-1) (sol) 

-- Ej 33 --
longFR :: [a] -> Int
longFR xs = foldr (\x acc -> acc+1)0 xs

longFL :: [a] -> Int
longFL xs = foldl (\acc x -> acc+1)0 xs

-- Ej 34 --
contieneFR :: Int -> [Int] -> Bool
contieneFR y xs = foldr (\x acc -> if x == y then True else acc)False xs

contieneFL :: Int -> [Int] -> Bool
contieneFL y xs = foldl (\acc x -> if x == y then True else acc)False xs

-- Ej 35 --
map2 :: (a -> b) -> [a] -> [b]
map2 f xs = foldr (\x acc -> [f x]++acc)[] xs

-- Ej 36  ... Ej 41--

-- Ej 42 --
succesorN :: Int -> Char -> Char
succesorN n c = if (ord sol > ord 'z') then (succesorN 0 (chr (ord sol - ord 'z' + ord 'a' -1)))else sol
				where sol = chr (ord c + n) 

-- Ej 43 --
capicua :: Int -> Bool
capicua x = esCapicua (separarDigitos x)

esCapicua :: [Int] -> Bool
esCapicua [] = False
esCapicua [x] = True 
esCapicua xs = if (head xs == last xs) then esCapicua (subList) else False
				where subList = drop 1 (take (length xs -1) xs)

-- Ej 44 --
posicionesElem :: Eq a => (a,[a]) -> [Int]
posicionesElem (x,xs) = foldr (\(y,pos) acc -> if (y == x) then [pos]++acc else acc)[] (zip xs [0..])

-- Ej 45 --
posElementos :: [Char] -> String -> [[Int]]
posElementos cs f = foldl (\acc c -> acc++[posicionesElem (c,f)])[] cs

-- Ej 46 --
creacionListas :: [Int] -> [[Int]]
creacionListas xs = cListas 0 xs

cListas :: Int -> [Int] -> [[Int]]
cListas n ys = if (n > length ys) then [] else [drop (length ys -n) ys]++ cListas (n+1) ys

-- Ej 47 --
genContieneFL :: Eq a => a -> [a] -> Bool
genContieneFL x ys = foldl (\acc y ->  if (x == y) then True else acc)False ys

genContieneFR :: Eq a => a -> [a] -> Bool
genContieneFR x ys = foldr (\y acc->  if (x == y) then True else acc)False ys

-- Ej 48 --
sumaSerie :: Int -> Float
sumaSerie n = sumaSerieI (fromIntegral n)

sumaSerieI :: Float -> Float
sumaSerieI 0 = 0
sumaSerieI n = 1 / n + sumaSerieP (n-1)

sumaSerieP :: Float -> Float 
sumaSerieP 0 = 0
sumaSerieP n = - 1 / n + sumaSerieI (n-1)