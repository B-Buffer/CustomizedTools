package com.customized.tools.dbtester.metdata;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

import com.customized.tools.util.EquivalenceUtil;
import com.customized.tools.util.StringUtil;

public abstract class AbstractMetadataRecord implements Serializable {
	
	public interface Modifiable {
		long getLastModified();
	}
	
	public interface DataModifiable {
		public static final String DATA_TTL = AbstractMetadataRecord.RELATIONAL_URI + "data-ttl"; 
		
		long getLastDataModification();
	}
	
	private static final long serialVersionUID = -1582484498802165518L;
	
	private static final Collection<AbstractMetadataRecord> EMPTY_INCOMING = Collections.emptyList();
	
	public static final String RELATIONAL_URI = "{http://www.ksoong.org/cst/ext/relational}"; 
	
	public final static char NAME_DELIM_CHAR = '.';
	
	
	private static AtomicLong UUID_SEQUENCE = new AtomicLong();
	
	private String name; 
	private String uuid;
	
	private String nameInSource;
	
	private volatile Map<String, String> properties;
	private String annotation;
	
	private transient Collection<AbstractMetadataRecord> incomingObjects;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUUID() {
		if (uuid == null) {
			uuid = String.valueOf(UUID_SEQUENCE.getAndIncrement());
		}
		return uuid;
	}

	public void setUUID(String uuid) {
		this.uuid = uuid;
	}

	public String getNameInSource() {
		return nameInSource;
	}

	public void setNameInSource(String nameInSource) {
		this.nameInSource = nameInSource;
	}
	
	public String getSourceName() {
		if (this.nameInSource != null && this.nameInSource.length() > 0) {
			return this.nameInSource;
		}
		return getName();
	}
	
	public String getFullName() {
        AbstractMetadataRecord parent = getParent();
        if (parent != null) {
        	String result = parent.getFullName() + NAME_DELIM_CHAR + getName();
        	return result;
        }
        return name;
	}
	
	public void getSQLString(StringBuilder sb) {
		AbstractMetadataRecord parent = getParent();
		if (parent != null) {
        	parent.getSQLString(sb);
        	sb.append(NAME_DELIM_CHAR);
        }
		sb.append('"').append(StringUtil.replace(name, "\"", "\"\"")).append('"'); 
	}
	
	public String getSQLString() {
		StringBuilder sb = new StringBuilder();
		getSQLString(sb);
		return sb.toString();
	}
	
	public AbstractMetadataRecord getParent() {
		return null;
	}
	
	public String getCanonicalName() {
		return name.toUpperCase();
	}
	
	public String toString() {
    	StringBuffer sb = new StringBuffer(100);
        sb.append(getClass().getSimpleName());
        sb.append(" name="); //$NON-NLS-1$
        sb.append(getName());
        sb.append(", nameInSource="); //$NON-NLS-1$
        sb.append(getNameInSource());
        sb.append(", uuid="); //$NON-NLS-1$
        sb.append(getUUID());
        return sb.toString();
    }
	
	/**
     * Return the extension properties for this record - may be unmodifiable
     * if {@link #setProperties(LinkedHashMap)} or {@link #setProperty(String, String)}
     * has not been called.
     * @return
     */
    public Map<String, String> getProperties() {
    	if (properties == null) {
    		return Collections.emptyMap();
    	}
    	return properties;
	}
    
    public String getProperty(String key, boolean checkUnqualified) {
    	String value = getProperties().get(key);
    	if (value != null || !checkUnqualified) {
    		return value;
    	}
    	int index = key.indexOf('}');
    	if (index > 0 && index < key.length() &&  key.charAt(0) == '{') {
    		key = key.substring(index + 1, key.length());
    	}
    	return getProperties().get(key);
    }
    
    /**
     * The preferred setter for extension properties.
     * @param key
     * @param value, if null the property will be removed
     */
    public String setProperty(String key, String value) {
//    	if (this.properties == null) {
//    		synchronized (this) {
//    			if (this.properties == null && value == null) {
//    				return null;
//    			}
//    			this.properties = new ConcurrentSkipListMap<String, String>(String.CASE_INSENSITIVE_ORDER);
//    		}
//		}
//    	if (value == null) {
//    		return this.properties.remove(key);
//    	}
//    	return this.properties.put(DataTypeManager.getCanonicalString(key), DataTypeManager.getCanonicalString(value));
    	return "";
    }
    
    public synchronized void setProperties(Map<String, String> properties) {
    	if (this.properties == null) {
    		this.properties = new ConcurrentSkipListMap<String, String>(String.CASE_INSENSITIVE_ORDER);
    	} else {
    		this.properties.clear();
    	}
		if (properties != null) {
			this.properties.putAll(properties);
		}
	}

    public String getAnnotation() {
		return annotation;
	}
    
    public void setAnnotation(String annotation) {
//		this.annotation = DataTypeManager.getCanonicalString(annotation);
	}
    
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }

        if(obj.getClass() != this.getClass()) {
            return false;
        }

        AbstractMetadataRecord other = (AbstractMetadataRecord)obj;

        return EquivalenceUtil.areEqual(this.getUUID(), other.getUUID());
    }
    
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
    	in.defaultReadObject();
    	if (this.properties != null && !(this.properties instanceof ConcurrentSkipListMap<?, ?>)) {
    		this.properties = new ConcurrentSkipListMap<String, String>(this.properties);
    	}
    }

    public int hashCode() {
        return getUUID().hashCode();
    }
    
    public Collection<AbstractMetadataRecord> getIncomingObjects() {
    	if (incomingObjects == null) {
    		return EMPTY_INCOMING;
    	}
		return incomingObjects;
	}
    
    public void setIncomingObjects(Collection<AbstractMetadataRecord> incomingObjects) {
		this.incomingObjects = incomingObjects;
	}
    
    public boolean isUUIDSet() {
    	return this.uuid != null && this.uuid.length() > 0 && !Character.isDigit(this.uuid.charAt(0));
    }
	
	

}
