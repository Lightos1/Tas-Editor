package ui;

import javax.swing.*;

public class TopBar {

    private boolean closed = true;

    public TopBar(JFrame jFrame) {
        JMenuBar jMenuBar = new JMenuBar();

        addFileOption(jMenuBar);
        addTasOption(jMenuBar);

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

    private void addTasOption(JMenuBar jMenuBar) {
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

        listenerStickInputs(stickInputs);

        jMenuBar.add(tasMenu);
    }

    private void listenerStickInputs(JMenuItem stickInputs) {
        stickInputs.addActionListener(_ -> {
            if (closed) {
                new StickInputWindow(this);
                closed = false;
            }
        });
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

}
