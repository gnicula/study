; A simple Example
;
start:  not r0 r1       ; whatever is in r1, its negation is stored in r0
        and r0 r0 r1    ; and a value with its negation, you have zero.
        not r1 r0       ; r1 = 0xFF
        add r1 r1 r1    ; r1 = 0xFE = 2 * 0xFF => all bits 1 but LSB 
        not r1 r1       ; r1 = 1
loop:   and r3 r0 r0    ; store the current value of r0 in r3 and show on display   
        add r0 r0 r1    ; increment r0 by 1
        bnz loop        ; jump to loop if the result is not zero
        and r3 r3 r3    ; clear the Z flag, as the result is non-zero, and show the value in r3 
                        ; on the display, it's not zero 
stop:   bnz stop        ; loop forever