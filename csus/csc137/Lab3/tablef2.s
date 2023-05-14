; Load initial RAM address 0 in R1
	MVI R1 0	; 0xC01
; Load 1 into R2 for increment
	MVI R2 1	; 0xC06
; Move immediate celsius value in R0
	MVI R0 0xEE	; 0xFB8
; Store celsius value in RAM at address in R1
	ST R0 [R1]	; 0x310
; Increment register containing RAM address
	ADD R1 R1 R2	; 0x019
; Repeat move from table to memory
	MVI R0 0xEF	; 0xFBC
	ST R0 [R1]	; 0x310
	ADD R1 R1 R2	; 0x019
	
	MVI R0 0xEF	; 0xFBC
	ST R0 [R1]	; 0x310
	ADD R1 R1 R2	; 0x019

	MVI R0 0xEF	; 0xFBC
	ST R0 [R1]	; 0x310
	ADD R1 R1 R2	; 0x019

	MVI R0 0xF0	; 0xFC0
	ST R0 [R1]	; 0x310
	ADD R1 R1 R2	; 0x019

	MVI R0 0xF0	; 0xFC0
	ST R0 [R1]	; 0x310
	ADD R1 R1 R2	; 0x019

; Wrap around for negative values
; Temporary debug REMOVE BEFORE SUBMIT
	MVI R1 0x80	; 0xE01

	MVI R0 0xA7	; 0xE9C
	ST R0 [R1]	; 0x310
	ADD R1 R1 R2	; 0x019

	MVI R0 0xA8	; 0xEA0
	ST R0 [R1]	; 0x310
	ADD R1 R1 R2	; 0x019
	
	MVI R0 0xA8	; 0xEA0
	ST R0 [R1]	; 0x310
	ADD R1 R1 R2	; 0x019

	MVI R0 0xA9	; 0xEA4
	ST R0 [R1]	; 0x310
	ADD R1 R1 R2	; 0x019

	MVI R0 0xA9	; 0xEA4
	ST R0 [R1]	; 0x310
	ADD R1 R1 R2	; 0x019	

	MVI R0 0xAA	; 0xEA8
	ST R0 [R1]	; 0x310
	ADD R1 R1 R2	; 0x019

; Now the table is loaded in RAM
; Iterate through RAM addresses and 
; display the conversion
; Load 1 in R0 and 0 in R2
	MVI R0 0x1	; 0xC04
	MVI R2 0x00	; 0xC02
	LD R3 [R2]	; 0x363
; Send fahrenheit to port address 0xF0
	MVI R1 0xF0	; 0xFC1
	ST R2 [R1]	; 0x312
; Increment port address
	ADD R1 R1 R0	; 0x011
; Send Celsius to port in R1
	ST R3 [R1]	; 0x313
	ADD R2 R2 R0	; 0x022
	BNZ 0x7FA	; 0x7FA