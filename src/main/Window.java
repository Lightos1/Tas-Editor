package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import ui.UI;

public class Window {

    private final JFrame jFrame;

    /* checking for null is ugly, but I don't care. */
    public Window(MainPanel panel, UI ui, String title, Dimension size) {
        jFrame = new JFrame();

        if (panel != null) {
            jFrame.add(panel);
            jFrame.pack();
        }

        jFrame.setTitle(title);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setSize(size);
        jFrame.requestFocusInWindow();
        jFrame.setFocusable(true);
        jFrame.setResizable(true);
        jFrame.setVisible(true);

        if (panel != null && ui != null) {
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

    public JFrame getJFrame() {
        return jFrame;
    }

}
