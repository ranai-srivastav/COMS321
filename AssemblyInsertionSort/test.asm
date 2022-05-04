//FILL WORKS
Fill:
    SUBI SP, SP, #16
    STUR X0, [SP, #0]
    STUR X1, [SP, #8]
    Fill_loop:
        CBZ X1, Fill_End
        STUR X1, [X0, #0]
        ADDI X0, X0, #8
        SUBI X1, X1, #1
        B Fill_loop
    Fill_End: 
        LDUR X0, [SP, #0]
        LDUR X1, [SP, #8]
        ADDI SP, SP, #16
    BR LR