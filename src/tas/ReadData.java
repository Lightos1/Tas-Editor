package tas;

import ui.InputField;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class ReadData {

    public static String[] readFile() {
        String path = "C:\\Users\\user\\Desktop\\inputs.txt";
        ArrayList<String> inputs = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String buffer;
            while ((buffer = reader.readLine()) != null) {
                buffer = buffer.trim();

                if (!buffer.isEmpty()) {
                    inputs.add(buffer);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
        return inputs.toArray(new String[0]);
    }

    public static void restoreFromFile(InputField inputs) {
        Configs.path = getPath();

        if (Configs.path == null) {
            return;
        }

        int rows = addRows(inputs);

        /* If the rows value is invalid, return. */
        if (rows == -1) {
            return;
        }

        String[] fileInputData = getFileInputData(rows);

        if (fileInputData == null) {
            return;
        }

        trimData(fileInputData);
        String[][] inputTableData = new String[rows][inputs.getCols() - 1];
        emptyInputTableData(inputTableData);
        processData(fileInputData, inputTableData);
        fixInputs(inputTableData);
        /* Yet another lazy fix. */
        fixStickInputs(inputTableData);
        applyInputs(inputTableData, inputs);
    }

    private static int addRows(InputField inputs) {
        /* Clear it so they don't get added instead of loaded. */
        inputs.clearAllRows();
        int rows;

        try (Stream<String> lines = Files.lines(Paths.get(Configs.path))) {
            rows = (int) lines.count();

            /* Don't add the first row. */
            for (int i = 1; i < rows; i++) {
                inputs.addRow();
            }
            return rows;
        } catch (IOException e) {
            System.out.println("Failed to read row count: " + e.getMessage());
            /* Return invalid value. */
            return -1;
        }
    }

    private static String[] getFileInputData(int rows) {
        try (BufferedReader read = new BufferedReader(new FileReader(Configs.path))) {
            String buff;
            String[] data = new String[rows];
            int i = 0;

            while ((buff = read.readLine()) != null) {
                data[i] = buff;
                i++;
            }

            return data;
        } catch (IOException e) {
            System.out.println("Failed to load input data from file: " + e.getMessage());
            return null;
        }
    }

    private static void trimData(String[] fileInputData) {
        for (int i = 0; i < fileInputData.length; i++) {
            if (fileInputData[i].startsWith("clickSeq ")) {
                fileInputData[i] = fileInputData[i].replace("clickSeq ", "");
            }
        }
    }

    /* TODO: This is sort of ugly, do I care fixing it? */
    private static void processData(String[] fileInputData, String[][] inputTableData) {
        for (int i = 0; i < fileInputData.length; i++) {
            char charBuff;
            String buff = "";
            boolean detectedStickEndOfLine = false;

            /* Empty inputs get ignored. */
            if (fileInputData[i].isEmpty()) {
                continue;
            }

            for (int j = 0; j < fileInputData[i].length(); j++) {
                charBuff = fileInputData[i].charAt(j);

                /* Check for detectedStickEndOfLine inputs. */
                /* If detectedStickEndOfLine inputs are detected, we reached the end of the line. */
                if (charBuff == '%' || charBuff == '&') {
                    detectedStickEndOfLine = true;
                    j++;
                } else {
                    buff += charBuff;
                }

                /* If we are not at the end of the line AND charBuff is either , or j is at the end of the line, if charBuff is equal to , take the substring from the start of the buffer to the end, minus 1? WTF. */
                if (!detectedStickEndOfLine && (charBuff == ',' || j == fileInputData[i].length() - 1)) {
                    /* Trim trailing commas. */
                    if (charBuff == ',') {
                        buff = buff.substring(0, buff.length() - 1);
                    }

                    /* Find index for 2D-Array. */
                    int index = getIndex(buff);

                    if (index == -1) {
                        buff = "";
                        continue;
                    }

                    inputTableData[i][index] = buff;
                    /* Move on to the next input. */
                    buff = "";
                } else {
                    if (charBuff == '%') {
                        /* Note: Adding charBuff is to fix a fix that I added earlier, but I am stupid and forgot what I "fixed". */
                        buff += charBuff;
                        while (j < fileInputData[i].length()) {
                            charBuff = fileInputData[i].charAt(j);
                            buff += charBuff;
                            if (buff.charAt(buff.length() - 2) == ',' &&  charBuff == '&') {
                                buff = buff.substring(0, buff.length() - 2);
                                j--;
                                break;
                            }
                            j++;
                        }
                        inputTableData[i][inputTableData[i].length - 2] = buff;
                        buff = "";
                    } else if (charBuff == '&') {
                        buff += charBuff;
                        while (j < fileInputData[i].length() - 1) {
                            charBuff = fileInputData[i].charAt(j);
                            /* This is not particularly efficient, but I am not fixing it :) */
                            buff += charBuff;
                            j++;
                        }
                        inputTableData[i][inputTableData[i].length - 1] = buff;
                    }
                }
                detectedStickEndOfLine = false;
            }
        }
    }

    private static void emptyInputTableData(String[][] inputTableData) {
        for (int i = 0; i < inputTableData.length; i++) {
            Arrays.fill(inputTableData[i], "");
        }
    }

    private static int getIndex(String input) {
        String[] lookupArray = {"A", "B", "X", "Y", "ZR", "ZL", "R", "L", "PLUS", "MINUS", "DLEFT", "DUP", "DRIGHT", "DDOWN", "LSTICK", "RSTICK"};
        String value;

        /* Trim the input. */
        if (input.startsWith("-") || input.startsWith("+")) {
            value = input.substring(1);
        } else {
            /* It's a click and we do nothing. */
            value = input;
        }

        if (value.startsWith("W")) {
            return -1;
        }

        /* Return the index */
        for (int i = 0; i < lookupArray.length; i++) {
            if (value.equals(lookupArray[i])) {
                return i;
            }
        }

        /* Failure. */
        System.out.println("Failed to get index!");
        return -1;
    }

    private static void fixInputs(String[][] inputs) {
        boolean set = false;
        String value = "";

        for (int i = 0; i < inputs[0].length - 2; i++) {
            for (int j = 0; j < inputs.length; j++) {
                /* Could trim it earlier, but I don't care. */
                if (inputs[j][i].startsWith("+")) {
                    inputs[j][i] = inputs[j][i].substring(1);
                    set = true;
                    value = inputs[j][i];
                    continue;
                }

                if (inputs[j][i].startsWith("-")) {
                    set = false;
                    inputs[j][i] = value;
                    continue;
                }

                if (set) {
                    inputs[j][i] = value;
                }
            }
            set = false;
            value = "";
        }
    }

    private static void fixStickInputs(String[][] inputs) {
        for (int i = 0; i < inputs.length; i++) {
            for (int j = inputs[0].length - 2; j < inputs[0].length; j++) {
                if (inputs[i][j].startsWith("%") || inputs[i][j].startsWith("&")) {
                    inputs[i][j] = inputs[i][j].substring(1);
                }
            }
        }
    }

    private static void applyInputs(String[][] inputs, InputField inputField) {
        for (int i = 0; i < inputs.length; i++) {
            for (int j = 0; j < inputs[0].length; j++) {
                inputField.getTableModel().setValueAt(inputs[i][j], i, j + 1);
            }
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
