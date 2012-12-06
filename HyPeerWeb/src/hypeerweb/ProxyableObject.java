package hypeerweb;

import java.util.Observable;
import java.util.Observer;

public abstract class ProxyableObject implements java.io.Serializable
{
   /**
     * 
     */
   //private static final long serialVersionUID = 1L;

   private transient command.GlobalObjectId id;
   private transient hypeerweb.ObservabilityObject observable;
   public ProxyableObject()
   {
       id = new command.GlobalObjectId();
       command.ObjectDB.getSingleton().store(id.getLocalObjectId(), this);
       
       observable = new ObservabilityObject();
   }
   
   public ProxyableObject(command.GlobalObjectId globalObjectId)
   {
       id = globalObjectId;
       command.ObjectDB.getSingleton().store(id.getLocalObjectId(), this);
       
       observable = new ObservabilityObject();
   }
   
   public command.GlobalObjectId getId()
   {
       return id;
   }

   //abstract public int compareTo(Object o);
   public void notifyChange()
   {
       observable.changed();
       observable.notifyObservers();
       
   }
   
   public void addNewObserver(Observer o)
   {
       observable.addObserver(o);
   }
   
   public int getCountObservers()
   {
       return observable.countObservers();
   }
  

}
