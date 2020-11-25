module Practica1 where
import Data.Char

--Ej 1--
convertirBD :: [[Int]]->[Int]
convertirBD xs = [sol| x <- xs, let sol = convertir (zip (reverse x) [0..])]

convertir :: [(Int,Int)] -> Int
convertir ys = sum [x*(2^y)| (x,y) <- ys]

--Ej 2--
cifrarMensaje :: String -> Int -> String
cifrarMensaje fr n = if (n > 26) then cifrarMensaje fr (n-26)
			else cifrar (zip fr [0..]) n

cifrar :: [(Char,Int)]-> Int ->[Char]
cifrar xs n = [ sol| (x,y)<-xs, 
		let sol = if mod y 2 == 0 then toUpper x else chr (ord x + n) ]
	
-- Ej 3 --
numerosAbundantes :: Int -> [Int]
numerosAbundantes n = take n ([ x| x <- [1..], let y = sum (divisores x), y > x])

divisores :: Int -> [Int]
divisores n = [x|x<-[1..n-1], mod n x == 0]

-- Ej 4  --
transformarTexto :: String -> String
transformarTexto fr = [sol| c <- fr, 
		let sol = if (isLower c) then (toUpper c) else (toLower c)]

-- Ej 4 recursividad no final --
convertirRNF :: [Char] -> [Char]
convertirRNF (x:fr) = if isLower x then [toUpper x] ++ convertirRNF fr
					  else [toLower x] ++ convertirRNF fr 
			 
-- Ej 4 recursividad final --
convertirRF :: [Char] -> [Char]
convertirRF fr = trRF fr ""

trRF :: [Char] -> [Char] -> [Char]
trRF [] sol = sol
trRF (c:fr) sol = if isLower c then trRF fr (sol++[toUpper c])
				  else trRF fr (sol ++ [toLower c])

--Ej 5--
familyCC :: Int -> Int
familyCC n = if (n == inverted n 0) then n
			 else familyCC (n +(inverted n 0))

inverted :: Int -> Int -> Int
inverted n sol = if n < 10 then (sol*10+n) else inverted (div n 10) (sol*10 + mod n 10)

--Ej 6--
serieRara :: Int -> Float
serieRara n = sum[(1/x)| y <- [1..n], let val = fromIntegral' y,
			  let x = if (mod y 2 == 0) then -val else val]

fromIntegral' :: Int -> Float
fromIntegral' n = fromIntegral n

-- Ej 7--
sumaPrimos :: Int -> [(Int,Int)]
sumaPrimos n = if n <= 2 then [] else
				if (length fun > 0) then fun else [(n,(n-n))]
				where fun = sumaPrimos' n

sumaPrimos' :: Int -> [(Int,Int)]
sumaPrimos' n = [(y,x)|y <-[1..(div n 2)], 
			let x = n-y , esPrimo x, esPrimo y, x+y == n, x /= y]

esPrimo :: Int -> Bool
esPrimo 1 = True
esPrimo n 
 		| sum (divisores n) == 1 = True
 		| otherwise = False

-- Ej 7 Recursivo --
primos :: Int -> [Int]
primos n = [x| x<-[0..n], esPrimo x]

sumaPrimosR :: Int -> [(Int,Int)]
sumaPrimosR n = if n <= 2 then [] else
				if (length fun > 0) then fun else [(n,(n-n))]
				where fun = sumaPrimosR' n (primos n) []

sumaPrimosR' :: Int -> [Int] -> [(Int,Int)] -> [(Int,Int)]
sumaPrimosR' _ [] sol = sol
sumaPrimosR' n (x:xs) sol = sumaPrimosR' n xs ([(x,y)| y <- xs, x+y == n]++sol)  

---Ej 8--
tokenizar :: String -> Char -> [String]
tokenizar xs del = (tokenizar' xs del [])

tokenizar' :: String -> Char -> String -> [String]
tokenizar' [] del sol = [sol]
tokenizar' (x:xs) del sol
			| x == del = sol:(tokenizar' xs del []) 
			| otherwise = tokenizar' xs del (sol++[x])  
		
-- ej 8 b --
-- tokenizar2 :: String -> [Char] -> [String]
-- tokenizar2 xs (d:del) = tokenizar2' (tokenizar xs d) del

-- tokenizar2' :: [String] -> [Char] -> [String]
-- tokenizar2' xs (d:del) = tokenizar2' (([sol| x <- xs, let sol = tokenizar x d]) del)

--Ej 9--
intercambiar :: Int -> Int -> Int -> Int
intercambiar num m n = juntarDigitos (exchange (separarDigitos num) m n) 0

separarDigitos :: Int -> [Int]
separarDigitos 0 = []
separarDigitos n = separarDigitos (div n 10) ++ [mod n 10]

exchange :: [Int] -> Int -> Int -> [Int]
exchange xs m n = ((take m xs) ++ [(xs !! n)] ++ (take (n-m-1) (drop (1+m) xs)) ++[(xs !! m)] ++ (drop (n+1) xs))

juntarDigitos :: [Int]-> Int -> Int
juntarDigitos [] sol = sol
juntarDigitos (x:xs) sol = juntarDigitos xs (sol*10+x)

--Ej 10--
ordenarIn :: [Int] -> [Int]
ordenarIn xs = order xs []
				
order :: [Int] -> [Int] -> [Int]	
order [] sol = sol
order (x:xs) sol = order xs ([y | y <- sol, y < x] ++ [x] ++ [y | y <- sol, y > x])



