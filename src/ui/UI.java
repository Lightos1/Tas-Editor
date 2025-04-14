package ui;

import main.MainPanel;

import javax.swing.*;

public class UI {

    private final InputField editorField;

    public UI(MainPanel mainPanel) {
        editorField = new InputField(mainPanel);
    }

    public void addTopBar(JFrame jFrame) {
        new TopBar(jFrame, editorField);
    }

    public InputField getEditorField() {
        return editorField;
    }

}
