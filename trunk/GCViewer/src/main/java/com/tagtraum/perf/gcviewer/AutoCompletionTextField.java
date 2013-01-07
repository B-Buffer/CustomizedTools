package com.tagtraum.perf.gcviewer;


import javax.swing.*;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListDataEvent;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;

/**
 * AutoCompletionTextField.
 * <p/>
 * Date: Oct 6, 2005
 * Time: 9:49:21 AM
 *
 * @author <a href="mailto:hs@tagtraum.com">Hendrik Schreiber</a>
 */
public class AutoCompletionTextField extends JTextField implements ComboBoxEditor {

    private RecentURLsModel recentURLsModel;
    private AutoCompletionComboBoxModel comboBoxModel;
    private List suggestions;

    public AutoCompletionTextField() {
        super();
        this.suggestions = new ArrayList();
        this.comboBoxModel = new AutoCompletionComboBoxModel();
        setDocument(createDefaultModel());
        this.recentURLsModel = new RecentURLsModel();
    }

    public ComboBoxModel getComboBoxModel() {
        return comboBoxModel;
    }

    public Component getEditorComponent() {
        return this;
    }

    public void setItem(Object anObject) {
    }

    public Object getItem() {
        return getText();
    }

    public void setRecentURLsModel(RecentURLsModel recentURLsModel) {
        this.recentURLsModel = recentURLsModel;
    }

    protected Document createDefaultModel() {
        return new PlainDocument() {
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                final String text = this.getText(0, offs) + str;
                List oldSuggestions = suggestions;
                suggestions = recentURLsModel.getURLsStartingWith(text);
                String appendString = "";
                if (!suggestions.isEmpty()) {
                    final String suggestion = (String)suggestions.get(0);
                    appendString = suggestion.substring(text.length());
                }
                super.insertString(offs, str+appendString, a);
                setCaretPosition(offs);
                setSelectionStart(text.length());
                setSelectionEnd(getLength());
                if (!oldSuggestions.equals(suggestions)) {
                    comboBoxModel.fireContentsChanged();
                }
            }

            public void remove(int offs, int len) throws BadLocationException {
                super.remove(offs, len);
                final List oldSuggestions = suggestions;
                suggestions = recentURLsModel.getURLsStartingWith(AutoCompletionTextField.this.getText());
                if (!oldSuggestions.equals(suggestions)) {
                    comboBoxModel.fireContentsChanged();
                }
            }

        };
    }

    private class AutoCompletionComboBoxModel implements ComboBoxModel {

        private List listDataListeners;
        private Object selected;

        public AutoCompletionComboBoxModel() {
            this.listDataListeners = new ArrayList();
        }

        public void setSelectedItem(Object anItem) {
            this.selected = anItem;
        }

        public Object getSelectedItem() {
            return this.selected;
        }

        public int getSize() {
            return suggestions.size();
        }

        public Object getElementAt(int index) {
            return suggestions.get(index);
        }

        public void addListDataListener(ListDataListener l) {
            listDataListeners.add(l);
        }

        public void removeListDataListener(ListDataListener l) {
            listDataListeners.remove(l);
        }

        private void fireContentsChanged() {
            for (int i=0; i<listDataListeners.size(); i++) {
                ((ListDataListener)listDataListeners.get(i)).contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, getSize()-1));
            }
        }

    }

}
