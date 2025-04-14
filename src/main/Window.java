package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import ui.TopBar;
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

    /* This is to prevent multiple stick input windows from being open at the same time. */
    public void addCloseListener(TopBar topBar) {
        jFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                topBar.setClosed(true);
            }
        });
    }

    public JFrame getJFrame() {
        return jFrame;
    }

}
