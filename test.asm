Main:
    
    BL Dumbo

    DUMP
    B End
    // I MIGHT BE DOING UNNECESSARY SP SHIT


//Takes addr, val, final_pos in the array
Dumbo:
    PRNT X10
    FindSortedLoop:
        LDUR X13,[X0, #0]     // val at [i]
        SUBS XZR, X13, X1     // Comparing i and val
        B.GE FindSorted_End
        ADDI X0, X0, #8
        B FindSortedLoop
    FindSorted_End:
    BR LR

End: