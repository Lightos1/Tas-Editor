package ui;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import main.MainPanel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;
import java.util.ArrayList;

public class InputField {

    private final JScrollPane scrollPane;
    private final MainPanel mainPanel;
    private final JTable inputs;
    private final Table tableModel;
    private int highlightedRow = 0;

    public InputField(MainPanel mainPanel) {
        this.mainPanel = mainPanel;

        final int COLS = 19;
        int rows = 1;

        tableModel = new Table(rows, COLS);
        inputs = new JTable(tableModel);
        setLook(inputs);

        addListeners();

        scrollPane = new JScrollPane(inputs, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        setSize();
    }

    private void addListeners() {
        inputs.addMouseListener(new MouseInputs(this));
        inputs.addKeyListener(new KeyboardInputs(this));
        mainPanel.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                inputs.requestFocusInWindow();
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
    }

    private void setLook(JTable inputs) {
        inputs.setRowHeight(30);
        inputs.getTableHeader().setReorderingAllowed(false);
        inputs.setBackground(Color.BLACK);
        inputs.setForeground(Color.WHITE);
        inputs.setSelectionBackground(Color.BLACK);
        inputs.setSelectionForeground(Color.WHITE);
    }

    public JTable getInputs() {
        return inputs;
    }

    public Table getTableModel() {
        return tableModel;
    }

    public int getHighlightedRow() {
        return highlightedRow;
    }

    public void setHighlightedRow(int highlightedRow) {
        this.highlightedRow = highlightedRow;
    }

    public void setSize() {
        Dimension panelSize = mainPanel.getSize();
        scrollPane.setPreferredSize(panelSize);
        scrollPane.setSize(panelSize);
    }

    public void addRow() {
        int col = inputs.getSelectedColumn();
        tableModel.addRow(highlightedRow + 1);
        inputs.changeSelection(highlightedRow, col, false, false);
        inputs.editCellAt(highlightedRow, col);
        Component editor = inputs.getEditorComponent();
        if (editor != null) {
            editor.requestFocusInWindow();
        }
        highlightedRow++;
    }

    public void deleteRow() {
        tableModel.deleteRow(highlightedRow);
        highlightedRow--;
    }

    public static class Table extends AbstractTableModel {

        private final List<Object[]> inputs;
        private final String[] columnNames = {"Frame", "A", "B", "X", "Y", "ZR", "ZL", "R", "L", "PLUS", "MINUS", "DLEFT", "DUP", "DRIGHT", "DDOWN", "LSTICK", "RSTICK", "LEFT", "RIGHT"};

        public Table(int row, int cols) {
            inputs = new ArrayList<>();
            for (int i = 0; i < row; i++) {
                inputs.add(new Object[cols]);
            }
        }

        @Override
        public int getRowCount() {
            return inputs.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column % columnNames.length];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return inputs.get(rowIndex)[columnIndex];
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            inputs.get(row)[col] = value;
            fireTableCellUpdated(row, col);
        }

        public void addRow(int highlightedRow) {
            if (highlightedRow < 0 || highlightedRow > inputs.size()) {
                return;
            }

            inputs.add(highlightedRow, new Object[getColumnCount()]);
            fireTableRowsInserted(highlightedRow, highlightedRow);
        }

        public void deleteRow(int highlightedRow) {
            if (highlightedRow < 0 || highlightedRow > inputs.size() || inputs.isEmpty()) {
                return;
            }

            inputs.remove(highlightedRow);
            fireTableRowsDeleted(highlightedRow, highlightedRow);
        }
    }
}
