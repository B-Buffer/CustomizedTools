package com.customized.tools.dbtester.view;


import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;


public class TableRenderer {
    
    private static final int MAX_CACHE_ELEMENTS = 500;

    private final List<Column[]> cacheRows;
    private boolean alreadyFlushed;
    private int writtenRows;
    private int separatorWidth;

    private boolean enableHeader;
    private boolean enableFooter;

    protected final ColumnMetaData meta[];
    protected final OutputDevice out;
    protected final String colSeparator;
    protected final String colPreSeparator;
    
    public TableRenderer(ColumnMetaData[] meta,
                         OutputDevice out,
                         String separator, 
                         boolean enableHeader,
                         boolean enableFooter) {
        this.meta = meta;
        this.out = out;
        this.enableHeader = enableHeader;
        this.enableFooter = enableFooter;

        /*
         * we cache the rows in order to dynamically determine the
         * output width of each column.
         */
        this.cacheRows = new ArrayList<>(MAX_CACHE_ELEMENTS);
        this.alreadyFlushed = false;
        this.writtenRows = 0;
        this.colSeparator = " " + separator;
        this.colPreSeparator = separator;
        this.separatorWidth = separator.length();
    }

    public TableRenderer(ColumnMetaData[] meta, OutputDevice out) {
        this(meta, out, "|", true, true);
    }

    public void addRow(Column[] row) {
        updateColumnWidths(row);
        addRowToCache(row);
    }

    protected void addRowToCache(Column[] row) {
        cacheRows.add(row);
        if (cacheRows.size() >= MAX_CACHE_ELEMENTS) {
            flush();
            cacheRows.clear();
        }
    }
    
    /**
     * return the meta data that is used to display this table.
     */
    public ColumnMetaData[] getMetaData() {
        return meta;
    }

    /**
     * Overwrite this method if you need to handle customized columns.
     * @param row
     */
    protected void updateColumnWidths(Column[] row) {
        for (int i = 0; i < meta.length; ++i) {
            row[i].setAutoWrap(meta[i].getAutoWrap());
            meta[i].updateWidth(row[i].getWidth());
        }
    }

    public void closeTable() {
        flush();
        if (writtenRows > 0 && enableFooter) {
            printHorizontalLine();
        }
    }

    /**
     * flush the cached rows.
     */
    public void flush() {
        if (!alreadyFlushed) {
            if (enableHeader) {
                printTableHeader();
            }
            alreadyFlushed = true;
        }
        Iterator<Column[]> rowIterator = cacheRows.iterator();
        while (rowIterator.hasNext()) {
            Column[] currentRow = rowIterator.next();
            boolean hasMoreLines;
            do {
                hasMoreLines = false;
                hasMoreLines = printColumns(currentRow, hasMoreLines);
                out.println();
            }
            while (hasMoreLines);
            ++writtenRows;
        }
    }

    protected boolean printColumns(Column[] currentRow, boolean hasMoreLines) {
    	boolean first = true;
        for (int i = 0; i < meta.length; ++i) {
            if (!meta[i].doDisplay())
                continue;
            if(first){
            	out.print(this.colPreSeparator);
            	first = false;
            }
            hasMoreLines = printColumn(currentRow[i], hasMoreLines, i);
        }
        return hasMoreLines;
    }

    protected boolean printColumn(Column col,
                                  boolean hasMoreLines,
                                  int i) {
        String txt;
        out.print(" ");
        txt = formatString( col.getNextLine(), ' ', meta[i].getWidth(),meta[i].getAlignment());
        hasMoreLines |= col.hasNextLine();
        if (col.isNull())
            out.attributeGrey();
        out.print(txt);
        if (col.isNull())
            out.attributeReset();
        out.print(colSeparator);
        return hasMoreLines;
    }

    private void printHorizontalLine() {
    	boolean first = true;
        for (int i = 0; i < meta.length; ++i) {
            if (!meta[i].doDisplay())
                continue;
            String txt = null;;
            if(first){
            	out.print("+");
            	first= false;
            }
            
            txt = formatString("", '-', meta[i].getWidth() + separatorWidth + 1, ColumnMetaData.ALIGN_LEFT);
            
            out.print(txt);
            out.print("+");
        }
        out.println();
    }

    private void printTableHeader() {
        printHorizontalLine();
        boolean first = true;
        for (int i = 0; i < meta.length; ++i) {
            if (!meta[i].doDisplay())
                continue;
            
            if(first) {
            	out.print(colPreSeparator);
            	first = false;
            }
            
            String txt;
            txt = formatString(meta[i].getLabel(), ' ', meta[i].getWidth() + 1, ColumnMetaData.ALIGN_CENTER);
            out.attributeBold();
            out.print(txt);
            out.attributeReset();
            out.print(colSeparator);
        }
        out.println();
        printHorizontalLine();
    }

    protected String formatString(String text,
                                  char fillchar,
                                  int len,
                                  int alignment) {
        // System.out.println("[formatString] len: " + len + ", text.length: " + text.length());
        // text = "hi";
        StringBuffer fillstr = new StringBuffer();

        if (len > 4000) {
            len = 4000;
        }

        if (text == null) {
            text = "[NULL]";
        }
        int slen = text.length();

        if (alignment == ColumnMetaData.ALIGN_LEFT) {
            fillstr.append(text);
        }
        int fillNumber = len - slen;
        int boundary = 0;
        if (alignment == ColumnMetaData.ALIGN_CENTER) {
            boundary = fillNumber / 2;
        }
        while (fillNumber > boundary) {
            fillstr.append(fillchar);
            --fillNumber;
        }
        if (alignment != ColumnMetaData.ALIGN_LEFT) {
            fillstr.append(text);
        }
        while (fillNumber > 0) {
            fillstr.append(fillchar);
            --fillNumber;
        }
        return fillstr.toString();
    }
}
