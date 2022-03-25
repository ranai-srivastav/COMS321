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

//Takes addr, val, final_pos in the array
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
        B.GT FindSorted_End
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
    B.LE ShiftRightEnd
    LDUR X10, [X0, #0]
    ADDI X0, X0, #8
    STUR X10, [X0, #0]
    B ShiftRightLoop    
    ShiftRightEnd:


// Gets starting address and length of array
InsertionSort:
    ADDI X9, XZR, #1
        InsertionSort_while:
        SUBS XZR, X9, X1
        B.GE InsertionSort_exit
        //Set The right values in the registers X0, X1, X2
        ORR X2,X1,XZR
        ORR X1,X9, XZR
        BL InsertSortedPosition
        ADDI X9, X9, #1
        B InsertionSort_while
    InsertionSort_exit:
    
End:

// Takes addr, pos, final_pos
InsertSortedPosition:
    ADD X19, XZR, X0    //addr
    ADD X20, XZR, X1    // pos
    ADD X21, XZR, X2    // final_pos
    // loading addr[pos]
    ADD X9, XZR, X1
    LSL X9, #3
    ADD X9, X0, X9 X9   //stores address of v
    LDUR X10, [X9, #0]  //X10 = v = addr[pos]
    //X0 already has addr
    ADD X1, XZR, X10
    //X2 already has final_pos
    BL FindSortedPosition
    ADD X22, XZR, X0    //p = X22
    //X0 should have the return val from FindSortedPos
>>>>//TODO Does FindSorted actually place it in X0?
    ADD X1, XZR, X0
    ADD X0, XZR, X19
    ADD X2, XZR, X20
    BL ShiftRight
    //X9 = Address of addr[p]
    LSL X9, X22, #3
    ADD X9, X9, X19
    STUR X10, [X9, #0]
>>>>//TODO Assuming that X10 still has p
    BR LR

