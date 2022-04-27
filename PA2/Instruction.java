public class Instruction
{
    private String name;
    private String opcode;
    public String type;
    public Sring[] R = {"ADD","ADDS","ANDS","BR","EOR","LSL","LSR","ORR","SUB","SUBS","UDIV","UMULT"} ;
    public Sring[] D = {"LDUR","LDURB",""};

    public Instruction(){}

    public Instruction(String name, String opcode)
    {
        this.name = name;
        this.opcode = opcode;
        if(opcode.length()== 10){
            type = "R-type/D-type/IW-type";

        }
        if(opcode.length()== 9){
            type = "I-type";
        }
        if(opcode.length()== 5){
            type = "B-type";
        }
        if(opcode.length()== 7){
            type = "CB-type";
        }
    
        
    }

    public String toString()
    {
        return this.name + ": " + this.opcode;
    }
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getOpcode()
    {
        return opcode;
    }

    public void setOpcode(String opcode)
    {
        this.opcode = opcode;
    }

    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }

    



}
