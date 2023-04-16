; assembler code that continually computes and 
; outputs Fibonacci numbers (in hex) starting with F(0) = 0, F(1) = 1.
; must output both initial values of the sequence in your output

start:  not r0 r1
        and r1 r0 r1  ; obtained 0 value in r1
        not r0 r1
        add r2 r0 r0
        not r2 r2     ; obtained 1 in r2
; send both initial values r1 and r2 to output in r3
        and r3 r1 r1
        and r3 r2 r2
; computation loop that computes result in r3
loop:   add r3 r2 r1  ; compute next fibonacci
        and r1 r2 r2  ; move r2 to r1
        and r2 r3 r3  ; move r3 to r2
        bnz loop      ; rd is never 0 so loops forever
