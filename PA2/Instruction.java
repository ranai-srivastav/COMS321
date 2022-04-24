public class Instruction
{
    private String name;
    private String opcode;

    public Instruction(){}

    public Instruction(String name, String opcode)
    {
        this.name = name;
        this.opcode = opcode;
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
}
