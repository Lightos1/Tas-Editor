/* TODO: Rename this package. */
package main;

import ui.UI;

import java.awt.*;

public class Main {

    public Main() {
        MainPanel mainPanel = new MainPanel();
        UI ui = new UI(mainPanel);
        Window window = new Window(mainPanel, ui, "TAS-Editor", new Dimension(800, 800));
        ui.addTopBar(window.getJFrame());
    }

    public static void main(String[] args) {
        new Main();
    }

}
