import java.util.Arrays;

// &a gets the address of a
// *a gets the value stored in a

public class InsertionSort
{
    public static void main(String args[])
    {
        int len = 9;
        int[] a = new int[len];
        fill(a, len);
        System.out.println(Arrays.toString(a));
        InsertionSorter(a, len);
    }

    /**
     * will create an array at address addr of length elements 
     * containing length unique integers in reverse sorted order
     */
    public static void fill(int[] addr, int length)
    {
        int i = length;
        int j = 0;
        while(j<length)
        {
            addr[j] = i;
            i--; j++;
        }
    }
    /**
     * @brief This procedure takes three parameters, the address of an array of ints, the index of the final element in the array, 
     * and a position in the array.  It overwrites the final element, shifting intermediate elements to the right, leaving a whole at the position.
     * 
     * @param addr 
     * @param pos 
     * @param end_pos
     * @return int 
     */
    public static void ShiftRight(int[] addr, int pos, int end_pos)
    {
        //load the value at the memory address
        // *addr gives the value in a[0]
        // addr[pos] = 0;
        // 4 3 2 1 0
//        for(int i = end_pos-1; i >= pos; i--)
//        {
//            addr[i+1] = addr[i];
//        }

          end_pos = end_pos - 1;
          while(true)
          {
              if(end_pos < pos)
                  return;
             // end_pos = end_pos +1;

              int temp = addr[end_pos];
              end_pos++;
              addr[end_pos] = temp; // end_pos + 1 = end_pos
              end_pos -= 2;

//              addr[end_pos+1] = addr[end_pos];
//              end_pos--;
          }

    }

    /**
 * @brief This procedure takes three parameters, the address of an array of sorted ints, a value, 
 * and the index of the last element in the array.  It searches the array for the sorted position of the value and returns that index.
 * 
 * @param addr 
 * @param val 
 */
public static int FindSortedPos(int[] addr, int val, int final_pos)
{
    int i = 0;
    while(true)
    {
        if(addr[i] > val)
            return i;
        i += 1;
    }
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
    public static void InsertSortedPosition(int[] addr, int pos, int final_pos)
    {
        int v = addr[pos];
        int p = FindSortedPos(addr, v, final_pos);
        ShiftRight(addr, p, pos);
        addr[p] = v;
    }

    public static void InsertionSorter(int[] addr, int length)
    {
        int i = 1;
        while(i < length)
        {
            InsertSortedPosition(addr, i, length - 1);
            i = i + 1;
        }
        System.out.println(Arrays.toString(addr));
    }
}