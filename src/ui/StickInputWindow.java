package ui;

import main.Window;
import tas.Configs;

import javax.swing.*;
import java.awt.*;

public class StickInputWindow {

    public StickInputWindow(TopBar topBar, InputField inputField) {
        /* This is ugly, but I don't care. */
        Window stickInputsWindow = new Window(null, null, "Stick-Inputs", new Dimension(500, 500));
        stickInputsWindow.addCloseListenerConfig(topBar);
        JPanel stickPanel = new JPanel(new GridLayout(1, 2));

        StickPanel leftStick = new StickPanel(inputField, Sticktype.LEFT, Configs.invertLX, Configs.invertLY);
        StickPanel rightStick = new StickPanel(inputField, Sticktype.RIGHT, Configs.invertRX, Configs.invertRY);

        stickPanel.add(leftStick);
        stickPanel.add(rightStick);
        stickInputsWindow.getJFrame().add(stickPanel, BorderLayout.CENTER);
        stickInputsWindow.getJFrame().pack();

        /* Overwrite close operation. */
        stickInputsWindow.getJFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

}
