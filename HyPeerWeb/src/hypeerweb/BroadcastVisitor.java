package hypeerweb;

/**
 * Broadcasts a message from a source node to all nodes in the HyPeerWeb.
 * The message is actually the method operation(Node, Parameters) to be performed on all nodes.
 * The method operation(Node, Parameters) must be overridden in an implementing subclass.
 * 
 * Domain: None
 * 
 * @author Matthew
 */
public abstract class BroadcastVisitor implements Visitor
{
    /** The key used to identify a key-value pair in the parameters list. */
    protected static String STARTED_KEY;

    /**
     * The default constructor
     * 
     * @pre None
     * @post True
     */
    public BroadcastVisitor()
    {
        
    }
    
    /**
     * Creates the minimum set of parameters needed when invoking an accept method during a broadcast.
     * At the top level (this level) there are no required parameters.
     * If there are more required parameters in a subclass, this method is overridden.
     * 
     * @pre None
     * @post See return
     * @return A Parameters of size zero.
     */
    public static Parameters createInitialParameters()
    {
        return new Parameters();
    }
    
    @Override
    public void visit(final Node node, final Parameters parameters)
    {
        // TODO Auto-generated method stub
        // TODO Do we need to do the javadoc?
    }

    /**
     * The abstract operation to be performed on all nodes.
     * This operation must be implemented in all concrete subclasses.
     * 
     * @param node The node the operation is to be performed on
     * @param parameters The parameters needed to perform the operation.
     * @pre node is not null and parameters is not null.
     * @post True
     */
    protected abstract void operation(Node node, Parameters parameters);
}
