package inputs;

import ui.InputField;
import tas.SaveData;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/* NOTE: This is temporary. */
public class KeyboardInputs implements KeyListener {

    private final InputField inputField;

    public KeyboardInputs(InputField inputField) {
        this.inputField = inputField;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER -> inputField.addRow();
            case KeyEvent.VK_BACK_SPACE -> inputField.deleteRow();
            case KeyEvent.VK_W -> SaveData.saveInputs(inputField);
            case KeyEvent.VK_R -> SaveData.playTas();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
