Main:
    ADDI X0, X0, #10
    ADDI X1, X1, #5
    BL Fill

    DUMP
    B End
  
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

FindSortedPos:
    SUBI SP, SP, #24
    STUR X0, [SP, #0]
    STUR X1, [SP, #8]
    STUR X2, [SP, #16]
    SUB X10, X10, X10
    // X9 is i and init to 0
    FindSortedLoop:
        LDUR X9,[X0, #0]
        SUBS XZR, X9, X1
        B.G FindSorted_End
        ADDI X10, X10, #1
        ADDI X0, X0, #8
        B FindSortedLoop
    FindSorted_End:
        ORR X0, X10, X0
        BL LR

ShiftRight:
    SUBI X9, X2 , #1
    ShiftRightLoop:
    SUBS XZR, X1, X9
    B.L ShiftRightEnd
    LDUR X10, [X0, #0]
    ADDI X0, X0, #8
    STUR X10, [X0, #0]
    B ShiftRightLoop    
    ShiftRightEnd:


InsertionSort:
    ORRI X9, XZR, #1
    InsertionSort_while:
    SUBS XZR, X9, X1
    B.GTE InsertionSort_exit
    ORRI X2,X1,XZR
    ORRI X1,X9, XZR
    BL InsertSortedPosition
    ADDI X9, #1
    InsertionSort_exit:
    
End: