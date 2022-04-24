public class Tree
{
    Node root = new Node();
}

class Node
{
    Node(){}

    Node(Node parent, Node one, Node zero)
    {
        this.parent = parent;
        this.one = one;
        this.zero = zero;
    }
    Node parent;

    /**
     * The left child
     */
    Node zero;

    /**
     * The right child
     */
    Node one;

    Instruction i;

    public Node getOne()
    {
        return one;
    }

    public void setOne(Node one)
    {
        this.one = one;
    }

    public Node getParent()
    {
        return parent;
    }

    public void setParent(Node parent)
    {
        this.parent = parent;
    }

    public Node getZero()
    {
        return zero;
    }

    public void setZero(Node zero)
    {
        this.zero = zero;
    }
    public Instruction getInstruction()
    {
        return i;
    }

    public void setInstruction(Instruction i)
    {
        this.i = i;
    }


}
