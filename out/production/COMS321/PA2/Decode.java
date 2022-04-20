import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files

import javax.print.DocFlavor.STRING;

public class Decode {
    static ArrayList<String> list = new ArrayList<>(); 
    static ArrayList<String> inst = new ArrayList<>();
    public static void main(String[] args) {
         String[] name = new String[100];
        try {
            File myObj = new File("opcodes.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNext()) {
                String data = myReader.nextLine();
                list.add(data);

            }
            System.out.println(list.get(1));
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        // Read input text 
        try {
            File myObj = new File("input.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNext()) {
                String one = "";
                String data = myReader.next();
                for (int i = 0  ; i < data.length() ; i++){
                         one += data.charAt(i);
                    if( one.length() % 32 == 0){
                        inst.add(one);
                        one = "";
                    }
                }
                //System.out.println(inst.get(2));
            //    System.out.println(inst);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    

}