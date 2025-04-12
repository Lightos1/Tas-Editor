package inputs;

import ui.InputField;

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
        if (row >= 0 && col > 0) {
            if (inputField.getTableModel().getValueAt(row, col) == "") {
                inputField.getTableModel().setValueAt(inputField.getTableModel().getColumnName(col), row, col);
            } else {
                inputField.getTableModel().setValueAt("", row, col);
            }
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
