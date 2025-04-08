/* TODO: Rename this package. */
package main;

import ui.UI;

public class Main {

    public Main() {
        MainPanel mainPanel = new MainPanel();
        UI ui = new UI(mainPanel);
        new Window(mainPanel, ui);
    }

    public static void main(String[] args) {
        new Main();
    }

}
