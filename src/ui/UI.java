package ui;

import main.MainPanel;

public class UI {

    private final InputField editorField;

    public UI(MainPanel mainPanel) {
        editorField = new InputField(mainPanel);
        new StickInputWindow();
    }

    public InputField getEditorField() {
        return editorField;
    }

}
