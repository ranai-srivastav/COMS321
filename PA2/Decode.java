import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
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

