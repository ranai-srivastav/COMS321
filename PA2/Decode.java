import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

public class Decode
{
    static ArrayList<String> list = new ArrayList<>();
    static ArrayList<String> inst = new ArrayList<>();

    public static void main(String[] args)
    {
        readBitsFromFile();
        readOpcodesFromFile();

        ArrayList<Instruction> allInstructions = parseInstruction();

        Tree opcodeTree = treeGen(allInstructions);

//        decode(opcodeTree, inst);

//        decodeRType(new Instruction("ADD", "1000101100", "R"));
    }

    public static void decode(Tree opcodeTree, ArrayList<String> listOfInstructions)
    {
        File toWrite = new File("decompiledCode.txt");
        try
        {
            Instruction currInst;
            Node iterator = opcodeTree.root;
           for(int k = 0; k < listOfInstructions.size(); k++)
           {
               String binaryInst = listOfInstructions.get(k);
               int i = 0;
               while(iterator.getInstruction() != null)
               {
                   char c = binaryInst.charAt(i);
                   if(c == '0')
                   {
                       iterator = iterator.getZero();
                   }
                   else
                   {
                       iterator = iterator.getOne();
                   }
                   i++;
                   if(i > 11) throw new IllegalStateException("You fucked up " +
                           "\n   k = " + k
                   );

               }
               currInst = iterator.getInstruction();

               if(currInst.getType().equals("R"))
               {
                   decodeRType(currInst, binaryInst, toWrite);
               }
           }
        }
        catch(IOException e)
        {
            System.err.println(e.getMessage());
        }
    }

    public static String decodeRType(Instruction currInst, String binInst, File outputFile) throws IOException
    {
        // 0                              31
        // ________________________________
        // 31                             0
        // Name    R - L    L - R
        // opcode 31 - 21   0  - 10
        // Rm     20 - 16   11 - 15
        // shamt  15 - 10   16 - 21
        // rn      9 - 5    22 - 26
        // rd      4 - 0    27 - 31


        String rm = binInst.substring(11, 15);
        String shamt = binInst.substring(16, 21);
        String rn = binInst.substring(5, 9);
        String rd = binInst.substring(0, 4);

        int rmInt = convertBinToDec(rm);
        int shamtInt = convertBinToDec(shamt);
        int rnInt = convertBinToDec(rn);
        int rdInt = convertBinToDec(rd);

        return String.format("%s %d, %d, %d", currInst.getName(), rmInt, rnInt, rdInt);
    }

    public static int convertBinToDec(String toConvert)
    {
        int returnable = 0;
        for(int i = 0; i < toConvert.length(); i++)
        {
            char c = toConvert.charAt(toConvert.length() - i - 1);
            int bit = Integer.parseInt(String.valueOf(c));
            double pow = (bit == 0) ? 0 : Math.pow(2 * bit, i);
            returnable += pow;
        }
        return returnable;
    }

    public static ArrayList<Instruction> parseInstruction()
    {
        ArrayList<Instruction> allInstructions = new ArrayList<>();

        for(int i = 1; i < list.size() - 1; i++)
        {
            Instruction instruction = new Instruction();
            String s = list.get(i);

            int start = s.indexOf("\"");
            int end = s.indexOf("\"", start + 1);
            instruction.setName(s.substring(start + 1, end));

            int opcodeEnd = s.lastIndexOf("}");
            int opcodeBegin = s.lastIndexOf("b");
            instruction.setOpcode(s.substring(opcodeBegin + 1, opcodeEnd - 1).trim());

            // list of insts here
            //
            // allInstructions.setType("XYZ")''

            allInstructions.add(instruction);
        }

        return allInstructions;
    }

    public static void readBitsFromFile()
    {
        try
        {
            File myObj = new File("input.txt");
            Scanner myReader = new Scanner(myObj);
            while(myReader.hasNext())
            {
                String one = "";
                String data = myReader.next();
                for(int i = 0; i < data.length(); i++)
                {
                    one += data.charAt(i);
                    if(one.length() % 32 == 0)
                    {
                        inst.add(one);
                        one = "";
                    }
                }
            }
            myReader.close();
        }

        catch(FileNotFoundException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void readOpcodesFromFile()
    {
        String[] name = new String[100];
        try
        {
            File myObj = new File("opcodes.txt");
            Scanner myReader = new Scanner(myObj);
            while(myReader.hasNext())
            {
                String data = myReader.nextLine();
                list.add(data);
            }
            myReader.close();
        } catch(FileNotFoundException e)
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static Tree treeGen(ArrayList<Instruction> instructions)
    {
        Tree t = new Tree();
        for(Instruction inst: instructions)
        {
            Node iterator = t.root;
            String opCode = inst.getOpcode();
            for(int i = 0; i<opCode.length(); i++)
            {
                char bit = opCode.charAt(i);
                if(bit == '0')
                {
                    if(iterator.getZero() == null)
                        iterator.setZero(new Node(iterator, null, null));
                    iterator = iterator.getZero();
                }
                else
                {
                    if(iterator.getOne() == null)
                        iterator.setOne(new Node(iterator, null, null));
                    iterator = iterator.getOne();
                }
            }
//            iterator = iterator.getParent();
            iterator.setOne(null);
            iterator.setZero(null);
            iterator.setInstruction(inst);
        }
        return t;
    }
}