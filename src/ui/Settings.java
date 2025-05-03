package ui;

import main.Window;
import tas.Configs;

import javax.swing.*;
import java.awt.*;

public class Settings {

    public Settings(TopBar topBar) {
        Window settingsWindow = new Window(null, null, "Settings", new Dimension(500, 500));
        settingsWindow.addCloseListenerSettings(topBar);
        settingsWindow.getJFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel settingsPanel = new JPanel(new GridLayout(0, 2, 0, 0));

        JTextField ip = new JTextField();
        JTextField port = new JTextField();
        JTextField delay = new JTextField();
        Checkbox invertLX = new Checkbox();
        Checkbox invertLY = new Checkbox();
        Checkbox invertRX = new Checkbox();
        Checkbox invertRY = new Checkbox();

        loadSettings(ip, port, delay, invertLX, invertLY, invertRX, invertRY);

        JButton save = new JButton();

        settingsPanel.add(new JLabel("IP:"));
        settingsPanel.add(ip);

        settingsPanel.add(new JLabel("Port:"));
        settingsPanel.add(port);

        settingsPanel.add(new JLabel("Button press time and delay:"));
        settingsPanel.add(delay);

        settingsPanel.add(new JLabel("Invert X left stick:"));
        settingsPanel.add(invertLX);
        settingsPanel.add(new JLabel("Invert Y left stick:"));
        settingsPanel.add(invertLY);

        settingsPanel.add(new JLabel("Invert X right stick:"));
        settingsPanel.add(invertRX);
        settingsPanel.add(new JLabel("Invert Y right stick:"));
        settingsPanel.add(invertRY);

        save.setText("Save Settings");

        save.addActionListener(_ -> {
            saveSettings(ip, port, delay, invertLX, invertLY, invertRX, invertRY);
        });

        settingsPanel.add(save);
        settingsWindow.getJFrame().add(settingsPanel);
    }

    /* FIXME: There is a bug with this. */
    private void loadSettings(JTextField ip, JTextField port, JTextField delay, Checkbox invertLX, Checkbox invertLY, Checkbox invertRX, Checkbox invertRY) {
        ip.setText(Configs.ip);
        port.setText(Integer.toString(Configs.port));
        delay.setText(Integer.toString(Configs.delay));

        invertLX.setState(Configs.invertLX != 1);
        invertLY.setState(Configs.invertLY != -1);
        invertRX.setState(Configs.invertRX != -1);
        invertRY.setState(Configs.invertRY != 1);
    }

    private void saveSettings(JTextField ip, JTextField port, JTextField delay, Checkbox invertLX, Checkbox invertLY, Checkbox invertRX, Checkbox invertRY) {
        if (!ip.getText().isEmpty()) {
            Configs.ip = ip.getText();
        }

        if (!port.getText().isEmpty()) {
            Configs.port = Integer.parseInt(port.getText());
        }

        if (!delay.getText().isEmpty()) {
            Configs.delay = Integer.parseInt(delay.getText());
        }

        Configs.invertLX = invert(invertLX.getState(), -1, 1);
        Configs.invertLY = invert(invertLY.getState(), 1, -1);
        Configs.invertRX = invert(invertRX.getState(), 1, -1);
        Configs.invertRY = invert(invertRY.getState(), -1, 1);
    }

    private static int invert(boolean state, int isInverted, int notInverted) {
        if (state) {
            return isInverted;
        }
        return notInverted;
    }

}
