Main:
    ADDI X0, X0, #0
    ADDI X1, X1, #9
    
    BL Fill
    BL InsertionSort

    DUMP
    B End
    // I MIGHT BE DOING UNNECESSARY SP SHIT
  
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

ShiftRight:
    SUBI X2, X2 , #8     //finalpos-1
    LSL X2, X2, #3
    ADD X2, X2, X0
    ShiftRightLoop:
        SUBS XZR, X1, X2     // comparing &pos and x9
        B.LT ShiftRightEnd
        LDUR X9, [X2, #0]
        SUBI X2, X2, #8
        STUR X9, [X2, #0]
        B ShiftRightLoop    
ShiftRightEnd:
    BR LR

//Takes addr, val, final_pos in the array
FindSortedPos:
    FindSortedLoop:
        LDUR X13,[X0, #0]     // val at [i]
        SUBS XZR, X13, X1     // Comparing i and val
        B.GE FindSorted_End
        ADDI X0, X0, #8
        B FindSortedLoop
    FindSorted_End:
    BR LR

// Takes addr, pos, final_pos
InsertSortedPos:
    ADD X19, XZR, X0    //addr
    ADD X20, XZR, X1    // pos
    ADD X21, XZR, X2    // final_pos
    // loading addr[pos]
    ADD X9, XZR, X1
    LSL X9, X9, #3
    ADD X9, X0, X9     //stores address of v
    LDUR X10, [X9, #0]  //X10 = v = addr[pos]
    //X0 already has addr
    ADD X1, XZR, X10
    //X2 already has final_pos
    BL FindSortedPos
    ADD X22, XZR, X0    //&p = X22
    //X0 should have the return addr from FindSortedPos
    ADD X1, XZR, X0     
    ADD X0, XZR, X19
    ADD X2, XZR, X20
    BL ShiftRight
    STUR X10, [X22, #0]
    BR LR

// Gets starting address and length of array
InsertionSort:
    ADDI X25, XZR, #1     //X25 = i = 1
    InsertionSort_while:
        SUBS XZR, X25, X1
        B.GE End
        //Set The right values in the registers X0, X1, X2
        SUBI X2, X2, #1
        ADD X2, XZR, X1
        ADD X1, XZR, X25
        BL InsertSortedPos
        ADDI X25, X25, #1
        B InsertionSort_while

End:
DUMP