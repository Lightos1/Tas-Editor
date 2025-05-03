package tas;

import ui.InputField;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
        try (BufferedReader read = new BufferedReader(new FileReader(Configs.path))) {
            String buff;
            boolean firstLine = true;

            while ((buff = read.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                inputs.addRow();
            }

        } catch (IOException e) {
            System.out.println("Failed to restore from file: " + e.getMessage());
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
