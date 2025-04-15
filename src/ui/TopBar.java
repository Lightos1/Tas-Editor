package ui;

import tas.ReadAndSendData;

import javax.swing.*;
import java.io.IOException;

public class TopBar {

    private boolean closed = true;

    public TopBar(JFrame jFrame, InputField inputField) {
        JMenuBar jMenuBar = new JMenuBar();

        addFileOption(jMenuBar);
        addTasOption(jMenuBar, inputField);

        jFrame.setJMenuBar(jMenuBar);
    }

    private void addFileOption(JMenuBar jMenuBar) {
        JMenu fileMenu = new JMenu("File");
        JMenuItem save = new JMenuItem("Save");
        JMenuItem open = new JMenuItem("Open");

        fileMenu.add(save);
        fileMenu.add(open);

        jMenuBar.add(fileMenu);
    }

    private void addTasOption(JMenuBar jMenuBar, InputField inputField) {
        JMenu tasMenu = new JMenu("TAS");
        JMenuItem playTas = new JMenuItem("Play");
        JMenuItem stickInputs = new JMenuItem("Stick inputs");
        JMenuItem connectTas = new JMenuItem("Connect");
        JMenuItem disconnectController = new JMenuItem("Disconnect");
        JMenuItem config = new JMenuItem("Configure");

        tasMenu.add(playTas);
        tasMenu.add(stickInputs);
        tasMenu.add(connectTas);
        tasMenu.add(disconnectController);
        tasMenu.add(config);

        listenerStickInputs(stickInputs, inputField);
        detachListener(disconnectController);
        connectListener(connectTas);

        jMenuBar.add(tasMenu);
    }

    private void listenerStickInputs(JMenuItem stickInputs, InputField inputField) {
        stickInputs.addActionListener(_ -> {
            if (closed) {
                new StickInputWindow(this, inputField);
                closed = false;
            }
        });
    }

    /* TODO: Better exception handling */
    private void detachListener(JMenuItem disconnectController) {
        disconnectController.addActionListener(_ -> {
            try {
                ReadAndSendData.disconnect();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /* TODO: Better exception handling */
    private void connectListener(JMenuItem connectTas) {
        connectTas.addActionListener(_ -> {
            try {
                ReadAndSendData.connectAndConfigure();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

}
