/* TODO: Rename this package. */
package main;

import tas.ReadData;
import tas.SendData;
import ui.UI;

import java.awt.*;
import java.io.IOException;

public class Main {

    public Main() {
        ReadData.readConf();
        MainPanel mainPanel = new MainPanel();
        UI ui = new UI(mainPanel);
        Window window = new Window(mainPanel, ui, "TAS-Editor", new Dimension(800, 800));
        ui.addTopBar(window.getJFrame());
    }

    public static void main(String[] args) {
        new Main();
    }

}
