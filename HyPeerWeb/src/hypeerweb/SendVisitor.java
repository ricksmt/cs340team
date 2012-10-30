package hypeerweb;

/**
 * Used to send a message from a source node to a target node.
 * The actual message is the targetOperation to be performed on the target Node.
 * The "targetOperation" is abstract and is to be overridden in a visitor that does the actual work.
 * There is also an intermediateOperation that may be performed on nodes visited on the way to the target node.
 *
 * Domain: None
 *
 * @author Matthew
 */
public abstract class SendVisitor implements Visitor
{
    /** The key used in a key-value pair of the parameters to identify the target webId. */
    protected static String TARGET_KEY;

    /**
     * The default constructor
     * 
     * @pre None
     * @post True
     */
    public SendVisitor()
    {
    }
    
    /**
     * The SendVisitor visitor expects the parameters to contain a target.
     * This method initializes the parameters with the key-pair (TARGET_KEY, target).
     * If more parameters are required in a subclass this method is overridden.
     * Normally this method is only called by the source node before sending the "message".
     * 
     * @param target The webId of the node we are to perform the target operation on.
     * @pre There exists and node in the HyPeerWeb with the given ID (target)
     * @post See return
     * @return A Parameters of size one containing (TARGET_KEY, target)
     */
    public static Parameters createInitialParameters(final int target)
    {
        final Parameters params = new Parameters();
        params.set(TARGET_KEY, target);
        return params;
    }
    
    @Override
    public void visit(final Node node, final Parameters parameters)
    {
        assert node != null && parameters != null && node != Node.NULL_NODE;
        if(node.getWebId() == (Integer) parameters.get(TARGET_KEY)) targetOperation(node, parameters);
        else intermediateOperation(node, parameters);
    }

    /**
     * The abstract operation to be performed on the targetNode.
     * Must be overridden in any concrete subclass.
     * 
     * @param node The target node this operation is to be performed on.
     * @param parameters The list of parameters to be passed to the target operation.
     * @pre node is not null and parameters is not null.
     * @post True
     */
    protected abstract void targetOperation(Node node, Parameters parameters);
    
    /**
     * The intermediate operation to be performed on a
     * node as we traverse to the target node.
     * Often this method does nothing.
     * 
     * @param node The intermediate node.
     * @param parameters The parameters to be used when executing
     *                      the intermediate operation.
     * @pre node is not null and parameters is not null.
     * @post True
     */
    protected void intermediateOperation(final Node node, final Parameters parameters)
    {
    }
    
}
