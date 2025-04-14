package inputs;

import ui.InputField;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInputs implements MouseListener{

    private final InputField inputField;

    public MouseInputs(InputField inputField) {
        this.inputField = inputField;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        /* TODO: Improve readability */
        int row = inputField.getInputs().rowAtPoint(e.getPoint());
        int col = inputField.getInputs().columnAtPoint(e.getPoint());

        inputField.setHighlightedRow(row);
        if (row >= 0 && col > 0 && col < 17) {
            Object value = inputField.getTableModel().getValueAt(row, col);
            SwingUtilities.invokeLater(() -> {
                if (value == null || value.toString().isEmpty()) {
                    inputField.getTableModel().setValueAt(inputField.getTableModel().getColumnName(col), row, col);
                } else {
                    inputField.getTableModel().setValueAt("", row, col);
                }
            });
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
