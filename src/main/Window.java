package main;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import ui.UI;

public class Window {

    public Window(MainPanel panel, UI ui) {
        JFrame jFrame = new JFrame("TAS-Editor");
        jFrame.add(panel);
        jFrame.pack();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(800,800);
        jFrame.requestFocusInWindow();
        jFrame.setFocusable(true);
        jFrame.setResizable(true);
        jFrame.setVisible(true);

        jFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                panel.setSize(jFrame.getSize());
                panel.setPreferredSize(jFrame.getSize());
                ui.getEditorField().setSize();
            }
        });
    }

}
