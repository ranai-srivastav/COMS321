public class Instruction
{
    private String name;
    private String opcode;
    public String type;

    public Instruction(){}

    public Instruction(String name, String opcode,String type)
    {
        this.name = name;
        this.opcode = opcode;
        this.type = type;
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

