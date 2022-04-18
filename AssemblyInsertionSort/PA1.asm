Main:
    MOVZ X0, #27, LSL 0
    MOVZ X1, #5, LSL 0
    BL Fill


Fill:
    LSL X1, X1, #3
    ADD X9, X1, X0
    
    ADDI X14, X14, #256
    Fill_loop:
        SUB X15, X9, X10
        CBZ X15, Fill_End
        STUR X14, [X9, #0]
        ADDI X9, X9, #8
        SUBI X14, X14, #1 
        B Fill_loop
    Fill_End:
        BR LR


InsertionSort:
    STUR X0, SP, #0
    STUR X1, SP, #8
    LDUR X9, [X0, #0]  // Param 1 is the address of the array
    LDUR X10, [X1, #0] //Param 2 is the final position of the array
    LSL X11, X0, #3    // length * 8
    ADD X11, X11, X1
    InsertionSort_while:
        CMP X9, X11
        B.GT End // ?? To the one who called this. Means its over
        STUR X0, SP, #0
        STUR X1, SP, #8
        SUBI X1, X1, #1
        BL InsertSortedPosition


InsertSortedPosition:
    LSL X9, X1, #3
    ADD X9, X9, X0

    


FindSorted:
    //Load the 3 params from memory: addr, value, final_element in the array 
    LDUR X9, [X0, #0]    //Value stored in X0 is copied into X9
    LDUR X10, [X1, #0]
    LDUR X11, [X2, #0]
    FindSorted_While:
        CMP X9, X11
        B.GT FindSorted_End
            LDUR X12, [X9, #0]
            CMP X12, X10
            B.GE
        ADDI X9, X9, #8

    FindSorted_End:
        BR LR

End:

// Read SP and FP
// Whoever calls this method will have to store the params in X19-X27
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
        B.GE FindSorted_End
        ADDI X10, X10, #1
        ADDI X0, X0, #8
        B FindSortedLoop
    FindSorted_End:
        ORR X0, X10, X0
        BL LR


    // [1, 2, 3, 6, 5, 4]

ShiftRight:
    
    


