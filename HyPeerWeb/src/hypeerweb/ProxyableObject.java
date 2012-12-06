package hypeerweb;

import java.util.Observable;
import java.util.Observer;

public abstract class ProxyableObject extends Observable implements java.io.Serializable
{
   /**
     * 
     */
   //private static final long serialVersionUID = 1L;

   private transient command.GlobalObjectId id;
   //private transient hypeerweb.ObservabilityObject observable;
   public ProxyableObject()
   {
       id = new command.GlobalObjectId();
       command.ObjectDB.getSingleton().store(id.getLocalObjectId(), this);
       
       //observable = new ObservabilityObject();
   }
   
   public ProxyableObject(command.GlobalObjectId globalObjectId)
   {
       id = globalObjectId;
       command.ObjectDB.getSingleton().store(id.getLocalObjectId(), this);
       
       //observable = new ObservabilityObject();
   }
   
   public command.GlobalObjectId getId()
   {
       return id;
   }

   //abstract public int compareTo(Object o);
   public void notifyChange()
   {
       setChanged();
       notifyObservers();
   }
   
   public void notifyChange(Object o)
   {
       setChanged();
       if(o instanceof Integer) notifyObservers((Integer) o);
       else if(o instanceof Contents) notifyObservers((Contents) o);
   }
   
   public void addNewObserver(Observer o)
   {
       addObserver(o);
   }
   
   public int getCountObservers()
   {
       return countObservers();
   }
  

}
