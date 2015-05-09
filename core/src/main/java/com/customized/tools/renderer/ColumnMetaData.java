package com.customized.tools.renderer;

/**
 * own wrapper for the column meta data.
 */
public final class ColumnMetaData {
    public static final int ALIGN_LEFT   = 1;
    public static final int ALIGN_CENTER = 2;
    public static final int ALIGN_RIGHT  = 3;

    /** alignment; one of left, center, right */
    private final int alignment;

    /** the header of this column */
    private final String label;

    /** minimum width of this column; ususally set by the header width */
    private final int initialWidth;

    /** wrap columns automatically at this column; -1 = disabled */
    private int autoWrapCol;

    private int width;
    private boolean display;

    public ColumnMetaData(String header, int align) {
        this(header, align, -1);
    }

    /**
     * publically available constructor for the user.
     */
    public ColumnMetaData(String header, int align, int autoWrap) {
    	label = header;
    	initialWidth = header.length();
    	width = initialWidth;
    	alignment = align;
    	display = true;
        autoWrapCol = autoWrap;
    }
    
    public ColumnMetaData(String header) {
    	this(header, ALIGN_LEFT);
    }
    
    public void resetWidth() { 
    	width = initialWidth; 
    }

    /**
     * set, whether a specific column should be displayed.
     */
    public void setDisplay(boolean val) {
    	display = val; 
    }
    
    public boolean doDisplay() {
    	return display; 
    }

    public void setAutoWrap(int col) {
        autoWrapCol = col;
    }
    public int getAutoWrap() { 
        return autoWrapCol; 
    }

    int getWidth() { 
    	return width; 
    }
    
    String getLabel() { 
    	return label; 
    }
    
    public int getAlignment() { 
    	return alignment; 
    }
    
    void updateWidth(int w) {
		if (w > width) {
		    width = w;
		}
    }
    
    public static class Factory {
    	
    	public static ColumnMetaData[] create(int align, String... items) {
    		ColumnMetaData[] metadata = new ColumnMetaData [items.length];
    		for(int i = 0 ; i < items.length ; i ++) {
    			metadata[i] = new ColumnMetaData(items[i], ColumnMetaData.ALIGN_CENTER);
    		}
    		return metadata;
    	}
    	
    	public static ColumnMetaData[] create(String... items) {
    		return create(ALIGN_CENTER, items);
    	}
    }
}
