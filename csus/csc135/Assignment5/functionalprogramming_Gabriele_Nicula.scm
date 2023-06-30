;inc n: a higher-order function that takes an integer n as a parameter and
;returns an n-th increment function, which increments its parameter by n. Thus, in Scheme
;syntax, ((inc n 3) 2) and ((inc n -2) 3) return 5 and 1, respectively.
(define (inc n)
  (lambda (x) (+ x n)))

;len: a tail-recursive function that takes a list as a parameters and returns its
;length. For example, (len ’(2 1)) returns 2
(define (len lst)
  (letrec ((len-iter (lambda (lst count)
                      (if (null? lst)
                          count
                          (len-iter (cdr lst) (+ count 1))))))
    (len-iter lst 0)))

;maxmin: a function that computes and returns the maximum and minimum of
;a list of integers. For example, (maxmin ’(4 2 -1 10)) should return (10 -1).
(define (maxmin lst)
  (let loop ((lst (cdr lst))
             (max (car lst))
             (min (car lst)))
    (cond ((null? lst) (list max min))
          ((> (car lst) max) (loop (cdr lst) (car lst) min))
          ((< (car lst) min) (loop (cdr lst) max (car lst)))
          (else (loop (cdr lst) max min)))))

;mem: a Boolean function that takes two parameters (the first one has any data
;type but the second one will be a list), and returns true/false if the first data is/is not
;found in the list. For example, (mem ’(1) ’(1 4 -2)) returns #f.

(define (mem item lst)
  (cond ((null? lst) #f)
        ((equal? item (car lst)) #t)
        (else (mem item (cdr lst)))))

;ins: a function that takes two parameters (similar to mem), and inserts the
;data in the list if it is not already there. For example, (ins 5 ’(2 10 -3)) returns
;(5 2 10 -3).
;Hint: use mem in your function.

(define (ins item lst)
  (if (mem item lst) lst
      (cons item lst)))

;numT: a function that takes two parameters, a Boolean function and a list, calls
;the Boolean function for each element in the list and returns the number of times that the
;function returned true. For example, (numT number? ’(1 -5 -4 (2 1) 7)) returns 4.
      
(define (numT func lst)
  (define (countTrue lst)
  (if (null? lst) 0
      (if (car lst) (+ 1 (countTrue (cdr lst)))
      (countTrue (cdr lst)))))

  (countTrue (map func lst)))

;moreT: a function that takes three parameters, a Boolean function and two
;lists, and outputs which list returns more true values. In other words, it runs the Boolean
;function for each value in each list, and counts the number of times that each list of values
;returned true. It should return 1 (or 2) if the first (or second) list returns more true values.
;If both lists return true the same number of times, your function should return 0.
;For example, (moreT negative? ’(8 -4 3 8) ’(7 -3 -2 1 -5)) should return 2. As
;another example, (moreT even? ’(8 -4 3 8) ’(6 3 2 1 -4)) should return 0.
;Hint: use numT in your function.

(define (moreT func lst1 lst2)
  (let ((count1 (numT func lst1))
        (count2 (numT func lst2)))
    (if (> count1 count2)
        1
        (if (< count1 count2)
            2
            0))))

  