package ui;

import main.Window;

import javax.swing.*;
import java.awt.*;

public class StickInputWindow {

    public StickInputWindow(TopBar topBar, InputField inputField) {
        /* This is ugly, but I don't care. */
        Window stickInputsWindow = new Window(null, null, "Stick-Inputs", new Dimension(500, 500));
        stickInputsWindow.addCloseListener(topBar);
        JPanel stickPanel = new JPanel(new GridLayout(1, 2));

        StickPanel leftStick = new StickPanel(inputField, Sticktype.LEFT, 1, -1);
        StickPanel rightStick = new StickPanel(inputField, Sticktype.RIGHT, -1, 1);

        stickPanel.add(leftStick);
        stickPanel.add(rightStick);
        stickInputsWindow.getJFrame().add(stickPanel, BorderLayout.CENTER);
        stickInputsWindow.getJFrame().pack();

        /* Overwrite close operation. */
        stickInputsWindow.getJFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

}
