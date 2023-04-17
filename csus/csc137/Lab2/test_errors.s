; A simple Example
;
start:  nt r0 r1        ; invalid instruction
        add r0 r0 r4   ; invalid register
start:  not r1 r0       ; duplicate label
        add r1 r1 r1 r2 ; invalid number of registers
        bnz loop        ; undefined label
stop:   bnz stop        ; loop forever