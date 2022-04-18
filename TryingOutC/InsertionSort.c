
// &a gets the address of a
// *a gets the value stored in a

/**
 * @brief This procedure takes three parameters, the address of an array of ints, the index of the final element in the array, 
 * and a position in the array.  It overwrites the final element, shifting intermediate elements to the right, leaving a whole at the position.
 * 
 * @param addr 
 * @param pos 
 * @param final_pos 
 * @return int 
 */
void ShiftRight(int addr, int pos, int final_pos)
{
    //load the value at the memory address
    // *addr gives the value in a[0]
    int arr_start = addr + 8 * pos;

    while(arr_start <= (addr + 8 * final_pos))
    {
        *addr+8 =  *addr;
        arr_start += 8;
    }
}

/**
 * @brief This procedure takes three parameters, the address of an array of sorted ints, a value, 
 * and the index of the last element in the array.  It searches the array for the sorted position of the value and returns that index.
 * 
 * @param addr 
 * @param val 
 */
int FindSortedPos(int addr, int val, int final)
{
    int start_addr = addr;
    while(addr <= final && *addr <= val )
    {
        addr += 8;
    }
    return addr;
}

/**
 * @brief CThis procedure takes three parameters, the address of a partially-sorted array of ints, 
 * the index of the first item in the array that is not in sorted position, and the index of the last element of the array.  
 * It moves the first unsorted element into its sorted position, shifting elements to the right as 
 * necessary such that the entire element is in sorted order and no data is lost.
 * 
 * @param addr 
 * @param pos 
 */
int InsertSortedPosition(int addr, int pos, int final)
{
    int v = addr + pos * 8;
    int p = FindSortedPos(addr, v, final);
    ShiftRight(addr, i, final);
    addr[p] = v;
}

void InsertionSort(int addr, int length)
{
    start = addr;
    int final = addr + length * 8;
    while addr <= final
        InsertionSort(start, addr, final);
        addr += 8;
}