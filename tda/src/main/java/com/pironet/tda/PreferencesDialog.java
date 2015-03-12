/*
 * PreferencesDialog.java
 *
 * This file is part of TDA - Thread Dump Analysis Tool.
 *
 * TDA is free software; you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * TDA is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the Lesser GNU General Public License
 * along with TDA; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * $Id: PreferencesDialog.java,v 1.22 2008-04-30 09:03:33 irockel Exp $
 */

package com.pironet.tda;

import com.pironet.tda.utils.PrefManager;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 *
 * @author irockel
 */
public class PreferencesDialog extends JDialog {
    private JTabbedPane prefsPane;
    private GeneralPanel generalPanel;
    private RegExPanel regExPanel;
    private JPanel buttonPanel;
    private JButton okButton;
    private JButton cancelButton;
    private Frame frame;
    
    /**
     * Creates a new instance of PreferencesDialog
     */
    public PreferencesDialog(Frame owner) {
        super(owner, "Preferences");
        try {
            this.setIconImage(TDA.createImageIcon("Preferences.gif").getImage());
        } catch (NoSuchMethodError nsme) {
        // ignore, for 1.4 backward compatibility
        }

        frame = owner;
        getContentPane().setLayout(new BorderLayout());
        initPanel();        
    }
    
    public JTabbedPane getPane() {
        return(prefsPane);
    }
    
    private void initPanel() {
        prefsPane = new JTabbedPane();
        generalPanel = new GeneralPanel();
        regExPanel = new RegExPanel();
        prefsPane.addTab("General", generalPanel);
        prefsPane.addTab("Date Parsing", regExPanel);
        getContentPane().add(prefsPane,BorderLayout.CENTER);
        
        // only add buttons if there is an owner frame
        // otherwise we are running in visualvm
        if(frame != null) {
            okButton = new JButton("Ok");
            cancelButton = new JButton("Cancel");
            buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            buttonPanel.add(okButton);
            buttonPanel.add(cancelButton);
            getContentPane().add(buttonPanel, BorderLayout.SOUTH);

            okButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    frame.setEnabled(true);
                    saveSettings();
                }
            });

            cancelButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    frame.setEnabled(true);
                    dispose();
                }
            });
        }
        reset();
    }
    
    public void reset() {
        getRootPane().setDefaultButton(okButton);
        loadSettings();
    }
    
    public void loadSettings() {
        generalPanel.forceLoggcLoading.setSelected(PrefManager.get().getForceLoggcLoading());
        generalPanel.maxLinesField.setText(String.valueOf(PrefManager.get().getMaxRows()));
        generalPanel.bufferField.setText(String.valueOf(PrefManager.get().getStreamResetBuffer()));
        generalPanel.showHotspotClasses.setSelected(PrefManager.get().getShowHotspotClasses());
        generalPanel.useGTKLF.setSelected(PrefManager.get().isUseGTKLF());
        generalPanel.maxLogfileSizeField.setText(String.valueOf(PrefManager.get().getMaxLogfileSize()));
        
        DefaultComboBoxModel boxModel = new DefaultComboBoxModel();
        String[] regexs = PrefManager.get().getDateParsingRegexs();
        for(int i = 0; i < regexs.length; i++) {
            boxModel.addElement(regexs[i]);
        }
        regExPanel.dateParsingRegexs.setModel(boxModel);
        regExPanel.dateParsingRegexs.setSelectedItem(PrefManager.get().getDateParsingRegex());
        
        regExPanel.isJDK16DefaultParsing.setSelected(PrefManager.get().getJDK16DefaultParsing());
        regExPanel.isMillisTimeStamp.setSelected(PrefManager.get().getMillisTimeStamp());
    }
    
    public void saveSettings() {
        PrefManager.get().setForceLoggcLoading(generalPanel.forceLoggcLoading.isSelected());
        PrefManager.get().setMaxRows(Integer.parseInt(generalPanel.maxLinesField.getText()));
        PrefManager.get().setStreamResetBuffer(Integer.parseInt(generalPanel.bufferField.getText()));
        PrefManager.get().setShowHotspotClasses(generalPanel.showHotspotClasses.isSelected());
        PrefManager.get().setDateParsingRegex((String) regExPanel.dateParsingRegexs.getSelectedItem());
        PrefManager.get().setDateParsingRegexs(regExPanel.dateParsingRegexs.getModel());
        PrefManager.get().setMillisTimeStamp(regExPanel.isMillisTimeStamp.isSelected());
        PrefManager.get().setUseGTKLF(generalPanel.useGTKLF.isSelected());
        PrefManager.get().setJDK16DefaultParsing(regExPanel.isJDK16DefaultParsing.isSelected());
        PrefManager.get().setMaxLogfileSize(Integer.parseInt(generalPanel.maxLogfileSizeField.getText()));
        dispose();
    }
    
    class GeneralPanel extends JPanel {
        JTextField maxLinesField;
        JTextField bufferField;
        JTextField maxLogfileSizeField;
        JCheckBox forceLoggcLoading;
        JCheckBox showHotspotClasses;
        JCheckBox useGTKLF;
        
        public GeneralPanel() {
            setLayout(new FlowLayout(FlowLayout.RIGHT));
            JPanel innerPanel = new JPanel();
            innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
                        
            JPanel layoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            layoutPanel.add(new JLabel("Maximum amount of lines to check for\n class histogram or possible deadlock informations"));
            maxLinesField = new JTextField(3);
            layoutPanel.add(maxLinesField);
            innerPanel.add(layoutPanel);
            
            layoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            layoutPanel.add(new JLabel("Stream Reset Buffer Size (in bytes)"));
            bufferField = new JTextField(10);
            layoutPanel.add(bufferField);
            bufferField.setHorizontalAlignment(JTextField.RIGHT);
            innerPanel.add(layoutPanel);
            
            layoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            layoutPanel.add(new JLabel("Force Open Loggc Option even if class histograms were found in general logfile"));
            forceLoggcLoading = new JCheckBox();
            layoutPanel.add(forceLoggcLoading);
            innerPanel.add(layoutPanel);
            
            layoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            layoutPanel.add(new JLabel("Maximum logfile size in kbytes to display\n full logfile (set to 0 for unlimited size)"));
            maxLogfileSizeField = new JTextField(10);
            maxLogfileSizeField.setHorizontalAlignment(JTextField.RIGHT);
            layoutPanel.add(maxLogfileSizeField);
            innerPanel.add(layoutPanel);
            
            
            layoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            layoutPanel.add(new JLabel("Show internal hotspot classes in class histograms"));
            showHotspotClasses = new JCheckBox();
            layoutPanel.add(showHotspotClasses);
            innerPanel.add(layoutPanel);
            
            layoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            layoutPanel.add(new JLabel("Use GTK Look and Feel on Unix/Linux (only recommended with JDK 1.6)"));
            useGTKLF = new JCheckBox();
            layoutPanel.add(useGTKLF);
            innerPanel.add(layoutPanel);
            add(innerPanel);
        }
    }
    
    public class RegExPanel extends JPanel implements ActionListener {
        JComboBox dateParsingRegexs;
        JCheckBox isMillisTimeStamp;
        JCheckBox isJDK16DefaultParsing;
        JButton clearButton;
        String lastSelectedItem = null;
        
        RegExPanel() {
            setLayout(new BorderLayout());
            //setPreferredSize(new Dimension(580, 190));
            
            JPanel layoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            
            layoutPanel.add(new JLabel("Regular Expression for parsing timestamps in logs files"));
            dateParsingRegexs = new JComboBox();
            dateParsingRegexs.setEditable(true);
            dateParsingRegexs.addActionListener(this);
            layoutPanel.add(dateParsingRegexs);
            clearButton = new JButton("Clear");
            clearButton.addActionListener(this);
            layoutPanel.add(clearButton);
            
            add(layoutPanel,BorderLayout.CENTER);
            
            JPanel lowerPanel = new JPanel(new BorderLayout());
            layoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            isMillisTimeStamp = new JCheckBox();
            layoutPanel.add(new JLabel("Parsed timestamp is a long representing msecs since 1970"));
            layoutPanel.add(isMillisTimeStamp);
            lowerPanel.add(layoutPanel,BorderLayout.NORTH);
            
            layoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            isJDK16DefaultParsing = new JCheckBox();
            layoutPanel.add(new JLabel("Perform Parsing for Default Thread Dump Timestamps of Sun JDK 1.6"));
            layoutPanel.add(isJDK16DefaultParsing);
            lowerPanel.add(layoutPanel,BorderLayout.CENTER);
            add(lowerPanel, BorderLayout.SOUTH);
        }
        
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == dateParsingRegexs) {
                if((lastSelectedItem == null) || !((String) dateParsingRegexs.getSelectedItem()).equals(lastSelectedItem)) {
                    dateParsingRegexs.addItem(dateParsingRegexs.getSelectedItem());
                    lastSelectedItem = (String) dateParsingRegexs.getSelectedItem();
                }
            } else if (e.getSource() == clearButton) {
                dateParsingRegexs.setModel(new DefaultComboBoxModel());
            }
        }
    }
    
    //Must be called from the event-dispatching thread.
    public void resetFocus() {
    }
    
    
}
