package hypeerweb;

import java.util.HashMap;
import java.util.Map;

/**
 * An arbitrary collection of named objects.
 * 
 * @author Matthew
 */
public class Contents implements java.io.Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 6804549108318040302L;
    protected Map<String, Object> map = new HashMap<String, Object>();

    /**
     * The default constructor
     * 
     * @pre None
     * @post |contents| = 0
     */
    public Contents() {}
    
    public Contents(Contents contents)
    {
        map = new HashMap<String, Object>(contents.map);
    }

    /**
     * Returns true or false depending on whether or not
     * there exists a key-value pair with the given key.
     * 
     * @param key The key we are searching for.
     * @pre None
     * @post The return value is true if contents contains this key; otherwise, false.
     * @return Whether or not contents contains this key.
     */
    public boolean containsKey(final String key)
    {
        return map.containsKey(key);
    }

    /**
     * Retrieves an object from the contents with the given key.
     * 
     * @param key The key to the object we want.
     * @pre None
     * @post if contents contains this key, then its corresponding value
     *      is returned; otherwise, null is returned.
     * @return Object or null
     */
    public Object get(final String key)
    {
        return map.get(key);
    }
    
    /**
     * Inserts the key-value pair (key, value) into the contents.
     * If there was a key-value pair with the given key,
     * it is removed from the contents before inserting the new key-value pair.
     * The value may be null.
     * 
     * @param key The key associated with the given object.
     * @param value The object to be associated with the given key.
     * @pre key is not null.
     * @post The given key-value pair is in contents and
     *      any previous pair with the same key is absent.
     */
    public void set(final String key, final Object value)
    {
        if(key == null) return;
        map.put(key, value);
    }
}
