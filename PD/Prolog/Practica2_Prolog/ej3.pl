factorial(0,1):-!.
factorial(X,Y):-
    number(X),
    X > 0,
    X1 is X-1,
    factorial(X1,Y1),
    Y is X*Y1.

eval(N,N):- number(N).
eval(A+B,Suma):-
    eval(A,VA),
    eval(B,VB),
    Suma is VA+VB.

eval(A-B,Resta):-
    eval(A,VA),
    eval(B,VB),
    Resta is VA-VB.

eval(A*B,Producto):-
    eval(A,VA),
    eval(B,VB),
    Producto is VA*VB.

eval(A/B,Division):-
    eval(A,VA),
    eval(B,VB),
    Division is VA/VB.

eval(A//B,DivisionEntera):-
    eval(A,VA),
    eval(B,VB),
    DivisionEntera is VA//VB.

eval(fact(N),V):-
	number(N),
    factorial(N,V).


