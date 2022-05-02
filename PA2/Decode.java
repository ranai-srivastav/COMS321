import java.io.*;
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files


public class Decode
{
    static ArrayList<String> list = new ArrayList<>();
    static ArrayList<String> inst = new ArrayList<>();

    public static void main(String[] args) throws IOException
    {
        readBitsFromFile();
        readOpcodesFromFile();

        ArrayList<Instruction> allInstructions = parseInstruction();

        Tree opcodeTree = treeGen(allInstructions);

        decode(opcodeTree, inst);

    }

    public static void decode (Tree opcodeTree, ArrayList<String> listOfInstructions)
    {
        try
        {
            File toWrite = new File("decompiledCode.txt");
            FileWriter fw = new FileWriter(toWrite);
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
                    decodeRType(currInst, binaryInst, fw);
                }
                else if(currInst.getType().equals("I"))
                {
                    decodeIType(currInst, binaryInst, fw);
                }
                else if(currInst.getType().equals("D"))
                {
                    decodeDType(currInst, binaryInst, fw);
                }
                else if(currInst.getType().equals("B"))
                {
                    decodeBType(currInst, binaryInst, fw);
                }
                else if(currInst.getType().equals("CB"))
                {
                    decodeCBType(currInst, binaryInst, fw);
                }

            }
        }
        catch(IOException e)
        {
            System.err.println(e.getMessage());
        }
    }

    public static void decodeRType(Instruction currInst, String binInst, FileWriter outputFile) throws IOException
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
        if(binInst.length() != 32)
            throw new IllegalStateException("decodeRType binInstruction is not 32. Length is " + binInst.length());

        String rm = binInst.substring(11, 15);
        String shamt = binInst.substring(16, 21);
        String rn = binInst.substring(22, 26);
        String rd = binInst.substring(27, 31);

        int rmInt = convertBinToDec(rm);
        int shamtInt = convertBinToDec(shamt);
        int rnInt = convertBinToDec(rn);
        int rdInt = convertBinToDec(rd);


        outputFile.write(String.format("%s X%d, X%d, X%d", currInst.getName(), rmInt, rnInt, rdInt));
    }

    public static void decodeIType(Instruction currInst, String binInst, FileWriter outputFile) throws IOException
    {
        // 0                              31
        // ________________________________
        // 31                             0
        // Name    R - L    L - R
        // opcode 31 - 22   0  - 9
        //AUL-Imm 21 - 10   10 - 21
        // rn      9 - 5    22 - 26
        // rd      4 - 0    27 - 31

        if(binInst.length() != 32)
            throw new IllegalStateException("decodeRType binInstruction is not 32. Length is " + binInst.length());

        String AUL = binInst.substring(10, 21);
        String rn = binInst.substring(22, 26);
        String rd = binInst.substring(27, 31);

        int AULInt = convertBinToDec(AUL);
        int rnInt = convertBinToDec(rn);
        int rdInt = convertBinToDec(rd);

        outputFile.write(String.format("%s X%d, X%d, #%d", currInst.getName(), AULInt, rnInt, rdInt));
    }

    public static void decodeDType(Instruction currInst, String binInst, FileWriter outputFile) throws IOException
    {
        // 0                              31
        // ________________________________
        // 31                             0
        // Name    R - L    L - R
        // opcode 31 - 21   0  - 10
        // Dt     20 - 12   11 - 19
        // op      11 - 10   20 - 21
        // rn      9 - 5    22 - 26
        // rt      4 - 0    27 - 31
        if(binInst.length() != 32)
            throw new IllegalStateException("decodeRType binInstruction is not 32. Length is " + binInst.length());

        String Dt = binInst.substring(11, 19);
        String op = binInst.substring(20, 21);
        String rn = binInst.substring(22, 26);
        String rt = binInst.substring(27, 31);

        int rmInt = convertBinToDec(Dt);
        int shamtInt = convertBinToDec(op);
        int rnInt = convertBinToDec(rn);
        int rdInt = convertBinToDec(rt);

        outputFile.write(String.format("%s X%d, X%d, X%d", currInst.getName(), rmInt, rnInt, rdInt));
    }
    public static void decodeBType(Instruction currInst, String binInst, FileWriter outputFile) throws IOException
    {
        // 0                              31
        // ________________________________
        // 31                             0
        // Name    R - L    L - R
        // opcode   31 - 26   0  - 5
        // Br        20 - 12   6 - 31

        if(binInst.length() != 32)
            throw new IllegalStateException("decodeRType binInstruction is not 32. Length is " + binInst.length());


        String bin = binInst.substring(6,31);


        int dec = convertBinToDec(bin);
        outputFile.write(String.format("%s %d", currInst.getName(), dec));

    }

    public static String decodeCBType(Instruction currInst, String binInst, FileWriter outputFile)
    {
        // 0                              31
        // ________________________________
        // 31                             0
        // Name      R - L     L - R
        // opcode    31 - 24   0  - 7
        // Br        23 - 5    8 - 26
        // rt        4 - 0     27 - 31

        if(binInst.length() != 32)
            throw new IllegalStateException("decodeRType binInstruction is not 32. Length is " + binInst.length());

        int rt = convertBinToDec(binInst.substring(27, 31));
        int branchTo = convertBinToDec(binInst.substring(8 ,26));

        String cond = "";
        switch(rt)
        {
            case 0: cond = "EQ"; break;

            case 1: cond = "NE"; break;

            case 2: cond = "HS"; break;

            case 3: cond = "LO"; break;

            case 4: cond = "MI"; break;

            case 5: cond = "PL"; break;

            case 6: cond = "VS"; break;

            case 7: cond = "VC"; break;

            case 8: cond = "HI"; break;

            case 9: cond = "LS"; break;

            case 10: cond = "GE"; break;

            case 11: cond = "LT"; break;

            case 12: cond = "GT"; break;

            case 13: cond = "LE"; break;

            default: throw new IllegalStateException("CB rt value is should be less than 16 but is " + rt);
        }



        return String.format("%s %d", "B." + cond, branchTo);

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
       // ArrayList<ArrayList<Character>>instructions = new ArrayList<Character>();
//        try
//        {
//            File myObj = new File("machineinputfile");
//            Scanner myReader = new Scanner(myObj);
//            while(myReader.hasNext())
//            {
//                String one = "";
//                String data = myReader.next();
//
//                for(int i = 0; i < data.length(); i++)
//                {
//                    one += data.charAt(i);
//                    if(one.length() % 32 == 0)
//                    {
//                        inst.add(one);
//                       // instructions.add(inst)
//
//                        one = "";
//                    }
//                }
//            }
//           // System.out.println(inst);
//            myReader.close();
//        }
//
//        catch(FileNotFoundException e)
//        {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//
//        return inst;
        try {
            // create a reader
            FileInputStream fis = new FileInputStream(new File("machineinputfile"));

            // read one byte at a time
            int ch;
            while ((ch = fis.read()) != -1) {
                System.out.print((char) ch);
            }

            // close the reader
            fis.close();

        } catch (IOException ex) {
            ex.printStackTrace();
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