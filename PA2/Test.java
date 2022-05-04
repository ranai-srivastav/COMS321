import java.io.*;
import java.nio.MappedByteBuffer;
import java.util.Arrays;

public class Test
{
    public static void main(String[] args) throws Exception
    {

//        FileInputStream fis = new FileInputStream(new File("assignment1.legv8asm.machine"));

//        System.out.println("bufferedReaderLineByLine");
//        bufferedReaderLineByLine();
//        System.out.println();

        System.out.println("bufferedReaderCharbyChar");
        bufferedReaderCharByChar();
        System.out.println();

    }

    public static void inputStreamReader() throws Exception
    {
        InputStreamReader isr = new InputStreamReader(new FileInputStream("assignment1.legv8asm.machine"));
        System.out.println();
    }

    public static void bufferedReaderLineByLine() throws Exception
    {
        BufferedReader br = new BufferedReader(new FileReader(new File("assignment1.legv8asm.machine")));
        while( br.readLine() != null)
        {
            System.out.println(br.readLine());
        }
    }

    public static void bufferedReaderCharByChar() throws Exception
    {

        BufferedReader br = new BufferedReader(new FileReader(new File("assignment1.legv8asm.machine")));
        int x = br.read();
        while( br.read() != -1)
        {
            x = br.read();
            for(int i = 0; i<x; i++)
            {
                String s = Integer.toBinaryString(0xFF & x | 0x100);
            }

            String s2 = Integer.toBinaryString(x);
//            System.out.print(s);
//            System.out.print(s2);
        }
    }

    public static void stackoverflow
}
