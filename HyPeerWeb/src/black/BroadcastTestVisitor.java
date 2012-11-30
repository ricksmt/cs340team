/**
 * 
 */
package black;

import java.util.Set;
import java.util.TreeSet;

import hypeerweb.BroadcastVisitor;
import hypeerweb.Contents;
import hypeerweb.Node;
import hypeerweb.Parameters;

/**
 * @author ricksmt
 *
 */
public class BroadcastTestVisitor extends BroadcastVisitor {
    
    
    
    
    public BroadcastTestVisitor()
    { 
        super();
    }
    
    
    
    
    @Override
    protected void operation(Node node, Parameters parameters){
        
            Contents contents = node.getContents();
            Set<String> keySet = parameters.getKeys();
            
            for(String key : keySet){
                if (contents.containsKey(key)) System.err.println("Node " + node.getWebId() + " has already been visited ");
                contents.set(key, parameters.get(key));
            }
        
    }
}
