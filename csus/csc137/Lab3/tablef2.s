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

	MVI R0 0xF1	; 0xFC4
	ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019
	
	MVI R0 0xF2	; 0xFC8
	ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xF2     ; 0xFC8
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xF3     ; 0xFCC
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xF3     ; 0xFC8
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xF4     ; 0xFD0
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xF4     ; 0xFD0
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xF5     ; 0xFD4
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xF5     ; 0xFD4
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xF6     ; 0xFD8
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xF7     ; 0xFDC
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xF7     ; 0xFDC
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xF8     ; 0xFE0
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xF8     ; 0xFE0
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xF9     ; 0xFE4
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xF9     ; 0xFE4
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xFA     ; 0xFE8
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xFA     ; 0xFE8
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xFB     ; 0xFEC
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xFC     ; 0xFF0
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xFC     ; 0xFF0
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xFD     ; 0xFF4
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xFD     ; 0xFF4
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xFE     ; 0xFD8
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xFE     ; 0xFD8
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xFF     ; 0xFFC
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xFF     ; 0xFFC
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x00     ; 0xC00
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x01     ; 0xC04
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x01     ; 0xC04
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x02     ; 0xC08
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x02     ; 0xC08
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x03     ; 0xC0C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x03     ; 0xC0C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x04     ; 0xC10
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x04     ; 0xC10
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x05     ; 0xC14
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x06     ; 0xC18
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x06     ; 0xC18
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x07     ; 0xC1C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x07     ; 0xC1C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x08     ; 0xC20
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x08     ; 0xC20
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x09     ; 0xC24
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x09     ; 0xC24
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x0A     ; 0xC28
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x0B     ; 0xC2C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x0B     ; 0xC2C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x0C     ; 0xC30
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x0C     ; 0xC30
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x0D     ; 0xC34
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x0D     ; 0xC34
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x0E     ; 0xC38
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x0E     ; 0xC38
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x0F     ; 0xC3C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x10     ; 0xC40
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x10     ; 0xC40
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x11     ; 0xC44
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x11     ; 0xC44
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x12     ; 0xC48
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x12     ; 0xC48
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x13     ; 0xC4C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x13     ; 0xC4C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x14     ; 0xC50
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x15     ; 0xC54
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x15     ; 0xC54
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x16     ; 0xC58
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x16     ; 0xC58
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x17     ; 0xC5C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x17     ; 0xC5C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x18     ; 0xC60
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x18     ; 0xC60
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x19     ; 0xC64
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x1A     ; 0xC68
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x1A     ; 0xC68
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x1B     ; 0xC6C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x1B     ; 0xC6C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x1C     ; 0xC70
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x1C     ; 0xC70
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x1D     ; 0xC74
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x1D     ; 0xC74
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x1E     ; 0xC78
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x1F     ; 0xC7C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x1F     ; 0xC7C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x20     ; 0xC80
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x20     ; 0xC80
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x21     ; 0xC84
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x21     ; 0xC84
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x22     ; 0xC88
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x22     ; 0xC88
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x23     ; 0xC8C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x24     ; 0xC90
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x24     ; 0xC90
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x25     ; 0xC94
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x25     ; 0xC94
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x26     ; 0xC98
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x26     ; 0xC98
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x27     ; 0xC9C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x27     ; 0xC9C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x28     ; 0xCA0
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x29     ; 0xCA4
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x29     ; 0xCA4
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x2A     ; 0xCA8
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x2A     ; 0xCA8
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x2B     ; 0xCAC
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x2B     ; 0xCAC
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x2C     ; 0xCB0
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x2C     ; 0xCB0
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x2D     ; 0xCB4
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x2E     ; 0xCB8
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x2E     ; 0xCB8
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x2F     ; 0xCBC
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x2F     ; 0xCBC
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x30     ; 0xCC0
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x30     ; 0xCC0
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x31     ; 0xCC4
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x31     ; 0xCC4
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x32     ; 0xCC8
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x33     ; 0xCCC
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x33     ; 0xCCC
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x34     ; 0xCD0
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x34     ; 0xCD0
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0x35     ; 0xCD4
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

; After this address fahrenheit 
; becomes negative
        MVI R0 0xA7     ; 0xE9C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xA8     ; 0xEA0
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xA8     ; 0xEA0
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xA9     ; 0xEA4
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xA9     ; 0xEA4
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xAA     ; 0xEA8
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xAA     ; 0xEA8
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xAB     ; 0xEAC
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xAC     ; 0xEB0
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xAC     ; 0xEB0
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xAD     ; 0xEB4
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xAD     ; 0xEB4
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xAE     ; 0xEB8
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xAE     ; 0xEB8
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xAF     ; 0xEBC
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xAF     ; 0xEBC
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xB0     ; 0xEC0
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xB1     ; 0xEC4
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xB1     ; 0xEC4
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xB2     ; 0xEC8
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xB2     ; 0xEC8
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xB3     ; 0xECC
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xB3     ; 0xECC
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xB4     ; 0xED0
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xB4     ; 0xED0
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xB5     ; 0xED4
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xB6     ; 0xED8
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xB6     ; 0xED8
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xB7     ; 0xEDC
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xB7     ; 0xEDC
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xB8     ; 0xEE0
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xB8     ; 0xEE0
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xB9     ; 0xEE4
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xB9     ; 0xEE4
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xBA     ; 0xEE8
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xBB     ; 0xEEC
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xBB     ; 0xEEC
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xBC     ; 0xEF0
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xBC     ; 0xEF0
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xBD     ; 0xEF0
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xBD     ; 0xEF4
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xBE     ; 0xEF8
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xBE     ; 0xEF8
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xBF     ; 0xEFC
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xC0     ; 0xF00
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xC0     ; 0xF00
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xC1     ; 0xF04
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xC1     ; 0xF04
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xC2     ; 0xF08
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xC2     ; 0xF08
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xC3     ; 0xF0C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xC3     ; 0xF0C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xC4     ; 0xF10
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xC5     ; 0xF10
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xC5     ; 0xF14
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xC6     ; 0xF18
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xC6     ; 0xF18
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xC7     ; 0xF1C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xC7     ; 0xF1C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xC8     ; 0xF20
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xC8     ; 0xF20
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xC9     ; 0xF24
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xCA     ; 0xF28
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xCA     ; 0xF28
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xCB     ; 0xF2C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xCB     ; 0xF2C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xCC     ; 0xF30
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xCC     ; 0xF30
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xCD     ; 0xF34
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xCD     ; 0xF34
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xCE     ; 0xF38
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xCF     ; 0xF3C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xCF     ; 0xF3C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xD0     ; 0xF40
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xD1     ; 0xF44
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xD1     ; 0xF44
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xD2     ; 0xF48
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xD2     ; 0xF48
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xD3     ; 0xF4C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xD4     ; 0xF50
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xD4     ; 0xF50
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xD5     ; 0xF54
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xD5     ; 0xF54
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xD6     ; 0xF58
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xD6     ; 0xF58
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xD7     ; 0xF5C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xD7     ; 0xF5C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xD8     ; 0xF60
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xD9     ; 0xF64
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xD9     ; 0xF64
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xDA     ; 0xF68
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xDA     ; 0xF68
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xDB     ; 0xF6C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xDB     ; 0xF6C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xDC     ; 0xF70
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xDC     ; 0xF70
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xDD     ; 0xF74
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xDE     ; 0xF78
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xDE     ; 0xF78
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xDF     ; 0xF7C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xDF     ; 0xF7C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xE0     ; 0xF80
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xE0     ; 0xF80
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xE1     ; 0xF84
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xE1     ; 0xF84
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xE2     ; 0xF88
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xE3     ; 0xF8C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xE3     ; 0xF8C
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xE4     ; 0xF90
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

        MVI R0 0xE4     ; 0xF90
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

; This is the last valid RAM address EF
        MVI R0 0xE5     ; 0xF94
        ST R0 [R1]      ; 0x310
        ADD R1 R1 R2    ; 0x019

; Reached address F0 stopping.
; The next 16 RAM addresses are inaccessible

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
	BNZ -6	        ; 0x7FA
