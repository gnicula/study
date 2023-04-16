; assembler code that computes and outputs only the 
; first 12 Fibonacci numbers (in hex) starting with F(0) = 0, F(1) = 1. 
; Must output both initial values of the sequence in your output.

start:  not r0 r1
        and r1 r0 r1  ; obtained 0 value in r1
        not r0 r1
        add r2 r0 r0
        not r2 r2     ; obtained 1 in r2
; obtain 6 in r0 because two fibonacci increments per loop
        add r0 r2 r2  ; r0 = 2
        add r0 r0 r2  ; r0 = 3
        add r0 r0 r0  ; r0 = 6
        not r0 r0     ; r0 = 249

; send both initial values r1 and r2 to output in r3
        and r3 r1 r1
        and r3 r2 r2
; hold r1 with value 1
        and r2 r1 r1  ; put 0 in r2
        and r1 r3 r3  ; put 1 in r1
        
; computation loop that alternatively computes result in r3 and r2
loop:   add r3 r2 r3  ; compute next fibonacci
        add r2 r2 r3  ; compute next fibonacci again
        add r0 r0 r1  ; increment r0 with one 
        bnz loop      ; when r0 becomes 0 loop ends

        and r3 r3 r3  ; side effect, sets z to false
end:    bnz end       ; loops forever because z is false