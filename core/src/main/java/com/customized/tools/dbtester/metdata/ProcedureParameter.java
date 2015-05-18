package com.customized.tools.dbtester.metdata;

public class ProcedureParameter extends BaseColumn {

	private static final long serialVersionUID = 6060340981061838858L;

	public enum Type {
		In,
		InOut,
		Out,
		ReturnValue
	}
	
	private Type type;
	private boolean optional;
	private Procedure procedure;
	private boolean isVarArg;
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
	
	@Deprecated
	public void setOptional(boolean optional) {
		this.optional = optional;
	}
	
	@Deprecated
	public boolean isOptional() {
		return optional;
	}
	
	public void setProcedure(Procedure procedure) {
		this.procedure = procedure;
	}
	
	@Override
	public Procedure getParent() {
		return this.procedure;
	}
	
	public void setVarArg(boolean isVarArg) {
		this.isVarArg = isVarArg;
	}

	public boolean isVarArg() {
		return isVarArg;
	}
	
    public String toString() { 
        return getName()+(isVarArg?"... ":" ")+" "+getType(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }	
    
    @Override
    public String getNativeType() {
    	String nativeType = super.getNativeType();
    	if (nativeType != null) {
    		return nativeType;
    	}
    	nativeType = getProperty(AbstractMetadataRecord.RELATIONAL_URI + "native_type" , false); //$NON-NLS-1$
    	if (nativeType != null) {
    		this.setNativeType(nativeType);
    	}
    	return nativeType;
    }

}
