package hypeerweb;

public abstract class ProxyableObject implements java.io.Serializable
{
   /**
     * 
     */
   //private static final long serialVersionUID = 1L;

   private transient command.GlobalObjectId id;
   public ProxyableObject()
   {
       id = new command.GlobalObjectId();
       command.ObjectDB.getSingleton().store(id.getLocalObjectId(), this);
   }
   
   public ProxyableObject(command.GlobalObjectId globalObjectId)
   {
       id = globalObjectId;
       command.ObjectDB.getSingleton().store(id.getLocalObjectId(), this);
   }
   
   public command.GlobalObjectId getId()
   {
       return id;
   }

   //abstract public int compareTo(Object o);
   
  

}
