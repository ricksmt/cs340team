package hypeerweb;

import java.util.HashSet;
import java.util.Set;

public class HyPeerWebVisitor extends BroadcastVisitor
{
    
    Set<Node> nodes = new HashSet<Node>();

    @Override
    protected void operation(Node node, Parameters parameters)
    {
        nodes.add(node);
    }
    
    public Set<Node> getNodes()
    {
        return nodes;
    }
}
