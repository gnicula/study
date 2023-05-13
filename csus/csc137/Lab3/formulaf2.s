; Load 1 in R0 and 0 in R2
	MVI R0 0x1	; 0xC04
; Load 0 degrees Fahrenheit in R2
	MVI R2 0x0	; 0xC02
; Loop begin
; Using C = F/2 - 15
	MVI R1 0xF	; 0xC3D
; Fahrenheit kept in R2
; Celsius computed in R3
	MOV R3 R2	; 0x223
	ASR R3		; 0x183
	SUB R3 R3 R1	; 0x077
; Load first port address in R1
; Send Fahrenheit to port R1
	MVI R1 0xF0	; 0xFC1
	ST R2 [R1]	; 0x312
; Increment port address
	ADD R1 R1 R0	; 0x011
; Send Celsius to port in R1
	ST R3 [R1]	; 0x313
	ADD R2 R2 R0	; 0x022
; Jump to Loop: 9 instructions back
	BNZ 0xF7	; 0x7F7