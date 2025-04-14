/* TODO: Rename this package. */
package main;

import ui.UI;

import java.awt.*;

public class Main {

    public Main() {
        MainPanel mainPanel = new MainPanel();
        UI ui = new UI(mainPanel);
        new Window(mainPanel, ui, "TAS-Editor", new Dimension(800, 800));
    }

    public static void main(String[] args) {
        new Main();
    }

}
