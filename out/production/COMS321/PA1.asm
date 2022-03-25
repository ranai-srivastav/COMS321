


MOVZ X0, #27, LSL 0
MOVZ X1, #5, LSL 0
BL 

Fill:
LDUR X0, 
MOVZ X27, #10, LSL 0
MOVZ X28, #8, LSL 0
MOVZ X29, #7, LSL 0
MOVZ X30, #3, LSL 0
MOVZ X31, #1, LSL 0


InsertionSort:
    LDUR X9, [X0, #0]
    LDUR X10, [X1, #0]
    LSL X11, X0, #3    // length * 8
    ADD X11, X11, X1
    while:
        CMP X9, X11
        B.GTE End // ?? To the one who called this. Means its over
        BL InsertSortedPosition

InsertSortedPosition:
    LSL X9, X1, #3
    ADD X9, X9, X0

    


FindSorted:
    //Load the 3 params from memory: X0, X1, X2
    LDUR X9, [X0, #0]    //Value stored in X0 is copied into X9
    PRNT X9
    B End

End:
