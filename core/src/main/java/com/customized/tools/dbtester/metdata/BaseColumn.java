package com.customized.tools.dbtester.metdata;

import java.util.Collections;

import com.customized.tools.util.StringUtil;

public abstract class BaseColumn extends AbstractMetadataRecord {

	private static final long serialVersionUID = -4606029800709538332L;
	
	public enum NullType {
		No_Nulls {
			@Override
			public String toString() {
				return "No Nulls"; //$NON-NLS-1$
			}
		},
		Nullable,
		Unknown		
	}
	
	private String datatypeUUID;
    private String runtimeType;
    private String defaultValue;
    private int length;
    private int scale;
    private int radix;
    private int precision;
    private NullType nullType;
    private int position;
    private Datatype datatype;
    private int arrayDimensions;
    private String nativeType;

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getDatatypeUUID() {
        return datatypeUUID;
    }

    public String getRuntimeType() {
        return runtimeType;
    }
    
    public Class<?> getJavaType() {
    	return /*TypeFacility.getDataTypeClass(runtimeType)*/ null;
    }

    public int getLength() {
        return length;
    }

    public int getPrecision() {
        return precision;
    }

    public int getScale() {
        return scale;
    }

    public int getRadix() {
        return radix;
    }

    /**
     * 1 based ordinal position
     * @return
     */
    public int getPosition() {
        return position;
    }

    public NullType getNullType() {
    	if (nullType == null) {
    		return NullType.Unknown;
    	}
        return nullType;
    }

	public void setLength(int i) {
		length = i;
	}

	public void setPrecision(int i) {
		precision = i;
	}

	public void setScale(int i) {
		scale = i;
	}

	public void setRadix(int i) {
		radix = i;
	}

    public void setNullType(NullType i) {
        nullType = i;
    }

	public void setPosition(int i) {
		position = i;
	}

	public void setRuntimeType(String string) {
//		runtimeType = DataTypeManager.getCanonicalString(string);
	}

	public void setDatatypeUUID(String string) {
//		datatypeUUID = DataTypeManager.getCanonicalString(string);
	}

	public void setDefaultValue(String object) {
//		defaultValue = DataTypeManager.getCanonicalString(object);
	}

	/**
	 * Get the type.  Represents the component type if {@link #getArrayDimensions()} > 0 
	 * @return
	 */
    public Datatype getDatatype() {
		return datatype;
	}
    
    public void setDatatype(Datatype datatype) {
    	setDatatype(datatype, false, 0);
    }
    
    public void setDatatype(Datatype datatype, boolean copyAttributes) {
    	setDatatype(datatype, copyAttributes, 0);
    }
    
    public void setDatatype(Datatype datatype, boolean copyAttributes, int arrayDimensions) {
		this.datatype = datatype;
		this.arrayDimensions = arrayDimensions;
		if (datatype != null) {
			this.datatypeUUID = this.datatype.getUUID();
			this.runtimeType = this.datatype.getRuntimeTypeName();
			if (arrayDimensions > 0) {
				this.runtimeType += StringUtil.join(Collections.nCopies(arrayDimensions, "[]"), ""); //$NON-NLS-1$ //$NON-NLS-2$
			}
			if (copyAttributes) {
				this.radix = this.datatype.getRadix();
				this.length = this.datatype.getLength();
				this.precision = this.datatype.getPrecision();
				this.scale = this.datatype.getScale();
				this.nullType = this.datatype.getNullType();
			}
		}
	}
    
    /**
     * Get the array dimensions.
     * @return
     */
    public int getArrayDimensions() {
		return arrayDimensions;
	}
    
    public String getNativeType() {
        return nativeType;
    }
    
    /**
     * @param nativeType The nativeType to set.
     * 
     */
    public void setNativeType(String nativeType) {
//        this.nativeType = DataTypeManager.getCanonicalString(nativeType);
    }

}
