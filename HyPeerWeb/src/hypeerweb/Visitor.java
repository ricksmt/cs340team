package hypeerweb;

public interface Visitor
{
    /**
     * The visit method for the visitor pattern.
     * 
     * @param node The node visited.
     * @param parameters The parameters passed to the node,
     *                    then to the visitor.
     * @pre node is not null and parameters is not null
     * @post true
     */
    void visit(Node node, Parameters parameters);
}
