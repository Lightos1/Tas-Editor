package tas;

import ui.InputField;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class IO {

    public static void restoreFromFile(InputField inputs) {
        if (Configs.path.isEmpty()) {
            Configs.path = getPath();

            if (Configs.path == null) {
                return;
            }
        }

        addRows(inputs);
    }

    private static void addRows(InputField inputs) {
        /* Clear it so they don't get added instead of loaded. */
        inputs.clearAllRows();
        int rows;

        try (Stream<String> lines = Files.lines(Paths.get(Configs.path))) {
            rows = (int) lines.count();

            /* Don't add the first row. */
            for (int i = 1; i < rows; i++) {
                inputs.addRow();
            }
        } catch (IOException e) {
            System.out.println("Failed to read row count: " + e.getMessage());
        }
    }

    public static String getPath() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Failed to open explorer: " + e.getMessage());
        }

        JFileChooser explorer = new JFileChooser();
        explorer.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = explorer.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            return explorer.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

}
