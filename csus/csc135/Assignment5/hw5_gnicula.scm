; --------------------
; (a) [15 points] inc_n: a higher-order function that takes an integer n
; as a parameter and returns an n-th increment function,
; which increments its parameter by n.
(define (inc_n n)
  (lambda (x) (+ x n)))

; (a) test (inc n)
;((inc_n 3) 2)
;((inc_n -2) 3)

; --------------------
; (b) [15 points] len: a tail-recursive function that takes a list
; as a parameters and returns its length.
(define (len lst)
  (if (null? lst) 0
      (+ 1 (len (cdr lst)))))

; (b) test len
;(len '(2 1))

; --------------------
; (c) [15 points] maxmin: a function that computes and returns
; the maximum and minimum of a list of integers.
(define (maxmin lst)
  (define (max_fun elm lst)
    (cond ((null? lst) elm)
          ((>= elm (car lst)) (max_fun elm (cdr lst)))
          ((< elm (car lst)) (max_fun (car lst) (cdr lst)))))
  (define (min_fun elm lst)
    (cond ((null? lst) elm)
          ((<= elm (car lst)) (min_fun elm (cdr lst)))
          ((> elm (car lst)) (min_fun (car lst) (cdr lst)))))
  (if (null? lst) '()
      (list (max_fun (car lst) (cdr lst)) (min_fun (car lst) (cdr lst)))))

; (c) test maxmin
;(maxmin '(4 2 -1 10))

; --------------------
; (d) [15 points] mem: a Boolean function that takes two parameters
; (the first one has any data type but the second one will be a list),
; and returns true/false if the first data is/is not found in the list.
(define (mem elm lst)
  (cond ((null? lst) '#f)
        ((eq? elm (car lst)) '#t)
        (else (mem elm (cdr lst)))))

; (d) test mem
;(mem '(1) '(1 4 -2))
;(mem 1 '(1 4 -2))

; --------------------
; (e) [10 points] ins: a function that takes two parameters (similar to mem),
; and inserts the data in the list if it is not already there.
(define (ins elm lst)
  (if (not (mem elm lst)) (cons elm lst)
      lst))

; (e) test ins
;(ins 5 '(2 10 -3))

; --------------------
; (f) [15 points] numT: a function that takes two parameters, a Boolean function and a list,
; calls the Boolean function for each element in the list and returns the number of times
; that the function returned true.
(define (numT fun lst)
  (if (null? lst) 0
      (if (fun (car lst)) (+ 1 (numT fun (cdr lst)))
          (numT fun (cdr lst)))))
                 
; (f) test numT
;(numT number? '(1 -5 -4 (2 1) 7))

; --------------------
; (g) [15 points] moreT: a function that takes three parameters, a Boolean function and two lists,
; and outputs which list returns more trues. In other words, it runs the Boolean function
; for each value in each list, and counts the number of times that each list of values returned true.
; It should return 1 (2) if the first (second) list returns more trues.
; If both lists return true the same number of times, your function should return 0.
(define (moreT fun lst1 lst2)
  (let ((nt1 (numT fun lst1)) (nt2 (numT fun lst2)))
    (cond ((> nt1 nt2) 1)
          ((eq? nt1 nt2) 0)
          (else 2))))

; (g) test moreT
;(moreT negative? '(8 -4 3 8) '(7 -3 -2 1 -5))
;(moreT even? '(8 -4 3 8) '(6 3 2 1 -4))