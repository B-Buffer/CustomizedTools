package com.tagtraum.perf.gcviewer.imp;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tagtraum.perf.gcviewer.model.AbstractGCEvent;
import com.tagtraum.perf.gcviewer.model.GCEvent;
import com.tagtraum.perf.gcviewer.model.GCModel;
import com.tagtraum.perf.gcviewer.model.AbstractGCEvent.Generation;

/**
 * DataReaderJRockit1_4_2.
 * <p/>
 * Date: Jan 5, 2006
 * Time: 5:31:50 AM
 *
 * @author <a href="mailto:hs@tagtraum.com">Hendrik Schreiber</a>
 */
public class DataReaderJRockit1_4_2 implements DataReader {
    private static Logger LOG = Logger.getLogger(DataReaderJRockit1_4_2.class.getName());

    private LineNumberReader in;
    private static final String MEMORY_MARKER = "[memory ] ";
    private static final String NURSERY_SIZE = "nursery size: ";

    public DataReaderJRockit1_4_2(InputStream in) {
        this.in = new LineNumberReader(new InputStreamReader(in));
    }

    public GCModel read() throws IOException {
        if (LOG.isLoggable(Level.INFO)) LOG.info("Reading JRockit 1.4.2 format...");
        boolean gcSummary = false;
        try {
            GCModel model = new GCModel(true);
            model.setFormat(GCModel.Format.SUN_X_LOG_GC);
            String line = null;
            GCEvent event = null;
            int nurserySize = -1;
            while ((line = in.readLine()) != null) {
                final int memoryIndex = line.indexOf(MEMORY_MARKER);
                if (memoryIndex == -1) {
                    if (LOG.isLoggable(Level.FINE)) LOG.fine("Ignoring line " + in.getLineNumber() + ". Missing \"[memory ]\" marker: " + line);
                    continue;
                }
                if (line.endsWith(MEMORY_MARKER)) {
                    continue;
                }
                final int startTimeIndex = memoryIndex + MEMORY_MARKER.length();

                // print some special statements to the log.
                if (!gcSummary) {
                    gcSummary = line.endsWith("Memory usage report");
                }
                if (gcSummary) {
                    if (LOG.isLoggable(Level.INFO)) LOG.info(line.substring(startTimeIndex));
                    continue;
                }
                else if (line.indexOf("Prefetch distance") != -1) {
                    if (LOG.isLoggable(Level.INFO)) LOG.info(line.substring(startTimeIndex));
                    continue;
                }
                else if (line.indexOf("GC strategy") != -1) {
                    if (LOG.isLoggable(Level.INFO)) LOG.info(line.substring(startTimeIndex));
                    continue;
                }
                else if (line.toLowerCase().indexOf("heap size:") != -1) {
                    if (LOG.isLoggable(Level.INFO)) LOG.info(line.substring(startTimeIndex));
                    final int nurserySizeStart = line.indexOf(NURSERY_SIZE);
                    final int nurserySizeEnd = line.indexOf('K', nurserySizeStart + NURSERY_SIZE.length());
                    if (nurserySizeStart != -1) {
                        nurserySize = Integer.parseInt(line.substring(nurserySizeStart + NURSERY_SIZE.length(), nurserySizeEnd));
                    }
                    continue;
                }
                else if (line.substring(startTimeIndex).startsWith("<")) {
                    // ignore
                    if (LOG.isLoggable(Level.FINE)) LOG.fine(line.substring(startTimeIndex));
                    continue;
                }

                final int colon = line.indexOf(':', startTimeIndex);
                if (colon == -1) {
                    if (LOG.isLoggable(Level.WARNING)) LOG.warning("Malformed line (" + in.getLineNumber() + "). Missing colon after start time: " + line);
                    continue;
                }
                event = new GCEvent();

                // set timestamp
                final String timestampString = line.substring(startTimeIndex, colon);
                final int minus = timestampString.indexOf('-');
                if (minus == -1) {
                    event.setTimestamp(Double.parseDouble(timestampString));
                }
                else {
                    event.setTimestamp(Double.parseDouble(timestampString.substring(0, minus)));
                }

                // set type
                final int typeStart = skipSpaces(colon+1, line);
                int typeEnd = typeStart;
                while (!Character.isDigit(line.charAt(++typeEnd))) {}
                final AbstractGCEvent.Type type = AbstractGCEvent.Type.parse("jrockit." + line.substring(typeStart, typeEnd).trim());
                if (type == null) {
                    if (LOG.isLoggable(Level.INFO)) LOG.info("Failed to determine type: " + line.substring(startTimeIndex));
                    continue;
                }
                event.setType(type);

                // before
                final int startBefore = typeEnd;
                final int endBefore = line.indexOf('K', startBefore);
                event.setPreUsed(Integer.parseInt(line.substring(startBefore, endBefore)));

                // after
                final int startAfter = endBefore+3;
                final int endAfter = line.indexOf('K', startAfter);
                event.setPostUsed(Integer.parseInt(line.substring(startAfter, endAfter)));

                // total
                final int startTotal = line.indexOf('(', endAfter) + 1;
                final int endTotal = line.indexOf('K', startTotal);
                event.setTotal(Integer.parseInt(line.substring(startTotal, endTotal)));

                // pause
                final int startPause = line.indexOf(',', endTotal) + 2;
                final int endPause = line.indexOf(' ', startPause);
                event.setPause(Double.parseDouble(line.substring(startPause, endPause)) / 1000.0d);
                model.add(event);

                // add artificial detail events
                if (nurserySize != -1 && event.getType().getGeneration() == Generation.YOUNG) {
                    GCEvent detailEvent = new GCEvent();
                    detailEvent.setType(event.getType());
                    detailEvent.setTimestamp(event.getTimestamp());
                    detailEvent.setTotal(nurserySize);
                    event.add(detailEvent);
                }
                if (nurserySize != -1 && event.getType().getGeneration() == Generation.TENURED) {
                    GCEvent detailEvent = new GCEvent();
                    detailEvent.setType(event.getType());
                    detailEvent.setTimestamp(event.getTimestamp());
                    detailEvent.setTotal(event.getTotal() - nurserySize);
                    event.add(detailEvent);
                }
            }
            return model;
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException ioe) {
                }
            if (LOG.isLoggable(Level.INFO)) LOG.info("Reading done.");
        }
    }

    private static int skipSpaces(int start, String line) {
        int i = start;
        while (line.charAt(i) == ' ') {
            i++;
        }
        return i;
    }
}
