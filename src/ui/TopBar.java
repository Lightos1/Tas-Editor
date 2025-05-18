package ui;

import tas.ReadData;
import tas.SendData;
import tas.WriteData;

import javax.swing.*;
import java.io.IOException;

public class TopBar {

    private boolean closedStick = true;
    private boolean closedSettings = true;

    public TopBar(JFrame jFrame, InputField inputField) {
        JMenuBar jMenuBar = new JMenuBar();

        addFileOption(jMenuBar, inputField);
        addTasOption(jMenuBar, inputField);

        jFrame.setJMenuBar(jMenuBar);
    }

    private void addFileOption(JMenuBar jMenuBar, InputField inputField) {
        JMenu fileMenu = new JMenu("File");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem open = new JMenuItem("Open");

        fileMenu.add(save);
        fileMenu.add(open);

        addSaveListener(save, inputField);
        addOpenListener(open, inputField);

        jMenuBar.add(fileMenu);
    }

    private void addTasOption(JMenuBar jMenuBar, InputField inputField) {
        JMenu tasMenu = new JMenu("TAS");
        JMenuItem playTas = new JMenuItem("Play");
        JMenuItem stickInputs = new JMenuItem("Stick inputs");
        JMenuItem connectTas = new JMenuItem("Connect");
        JMenuItem disconnectController = new JMenuItem("Disconnect");
        JMenuItem settings = new JMenuItem("Settings");

        tasMenu.add(playTas);
        tasMenu.add(stickInputs);
        tasMenu.add(connectTas);
        tasMenu.add(disconnectController);
        tasMenu.add(settings);

        listenerStickInputs(stickInputs, inputField);
        detachListener(disconnectController);
        connectListener(connectTas);
        listenerSettings(settings);
        playListener(playTas);

        jMenuBar.add(tasMenu);
    }

    private void addSaveListener(JMenuItem save, InputField inputField) {
        save.addActionListener(_ -> {
            WriteData.saveInputs(inputField);
        });
    }

    private void addOpenListener(JMenuItem open, InputField inputField) {
        open.addActionListener(_ -> {
            ReadData.restoreFromFile(inputField);
        });
    }

    private void listenerStickInputs(JMenuItem stickInputs, InputField inputField) {
        stickInputs.addActionListener(_ -> {
            if (closedStick) {
                new StickInputWindow(this, inputField);
                closedStick = false;
            }
        });
    }

    private void listenerSettings(JMenuItem settings) {
        settings.addActionListener(_ -> {
            if (closedSettings) {
                new Settings(this);
                closedSettings = false;
            }
        });
    }

    /* TODO: Better exception handling */
    private void detachListener(JMenuItem disconnectController) {
        disconnectController.addActionListener(_ -> {
            try {
                SendData.disconnect();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /* TODO: Better exception handling */
    private void connectListener(JMenuItem connectTas) {
        connectTas.addActionListener(_ -> {
            try {
                SendData.connectAndConfigure();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void playListener(JMenuItem playTas) {
        playTas.addActionListener(_ -> {
            SendData.playTas();
        });
    }

    public void setClosedStickInputs(boolean closedStick) {
        this.closedStick = closedStick;
    }

    public void setClosedSettings(boolean closedSettings) {
        this.closedSettings = closedSettings;
    }

}
