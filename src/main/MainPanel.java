package main;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    public MainPanel() {
        setFocusable(true);
        requestFocusInWindow();
        setRequestFocusEnabled(true);
        setPanelSize();
        setLayout(new BorderLayout());
    }

    private void setPanelSize() {
        Dimension size = new Dimension(800,800);
        setPreferredSize(size);
    }

}
