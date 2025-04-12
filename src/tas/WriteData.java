package tas;

import ui.InputField;

import java.io.*;
import java.util.Objects;

public class WriteData {

    public static void saveInputs(InputField inputs) {
        int row = inputs.getTableModel().getRowCount();
        int col = inputs.getTableModel().getColumnCount();

        /* Convert the inputs of the tas editor field to an array. */
        String[][] data = convertInputs(inputs, row, col - 1);

        print(data);

        /* Convert the array to usable instructions. */
        String[] instructions = convertToInstructions(data, row);

        /* TODO: Add file selector, for now this is hardcoded. */
        String path = "C:\\Users\\user\\Desktop\\inputs.txt";
        writeToFile(path, instructions);
    }

    private static void print(String[][] data) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                System.out.print(data[i][j] + " | ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static String[][] convertInputs(InputField inputs, int row, int col) {
        String[][] inputData = new String[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                inputData[i][j] = (String) inputs.getTableModel().getValueAt(i, j + 1);

                /* Convert to an empty string, easier to work with later than null. */
                if (inputData[i][j] == null) {
                    inputData[i][j] = "";
                }
            }
        }
        return inputData;
    }

    private static String[] convertToInstructions(String[][] rawData, int row) {
        String[] instructions = new String[row];
        /* extended inputs just means that there is more than 1 input in a single row/more than one input left, bad naming, I know :p */
        boolean extendedInputs;

        for (int i = 0; i < rawData.length; i++) {
            for (int j = 0; j < rawData[i].length; j++) {
                if (j == 0) {
                    if (isNotEmpty(rawData, i)) {
                        instructions[i] = "clickSeq ";
                    } else {
                        instructions[i] = "";
                    }
                }

                /* If there is an input, check if we have to release, press, press for a few frames or do nothing. */
                if (!rawData[i][j].isEmpty()) {
                    boolean previousRowEmpty;
                    boolean nextRowEmpty;

                    /* The first input in the tas will always be considered a press or short press, the final input a release. */
                    /* If the tas is only one frame long, it's a one frame input. */
                    if (i == 0 && i == rawData.length -1) {
                        previousRowEmpty = true;
                        nextRowEmpty = true;
                    } else if (i == 0) {
                        previousRowEmpty = true;
                        nextRowEmpty = rawData[i + 1][j].isEmpty();
                    } else if (i == rawData.length - 1) {
                        previousRowEmpty = rawData[i - 1][j].isEmpty();
                        nextRowEmpty = true;
                    } else {
                        previousRowEmpty = rawData[i - 1][j].isEmpty();
                        nextRowEmpty = rawData[i + 1][j].isEmpty();
                    }

                    if (previousRowEmpty && !nextRowEmpty) {
                        /* Press. */
                        instructions[i] += "+";
                    } else if (!previousRowEmpty && nextRowEmpty) {
                        /* Release. */
                        instructions[i] += "-";
                    }

                    /* If both of these fail, it will be pressed and released for one frame. */

                    /* Add the input. */
                    instructions[i] += rawData[i][j];
                }

                extendedInputs = isLastInput(rawData, i, j);

                /* If there is more than one input, separate them with a comma, unless it's the last input. */
                if (extendedInputs && j != rawData[0].length - 1 && !rawData[i][j].isEmpty()) {
                    instructions[i]
                            += ",";
                }
            }
        }
        return instructions;
    }

    private static boolean isLastInput(String[][] rawData, int row, int i) {
        int count = 0;

        while (i < rawData[row].length) {
            if (!rawData[row][i].isEmpty()) {
                count++;
            }

            if (count > 1) {
                return true;
            }
            i++;
        }
        return false;
    }

    private static void writeToFile(String path, String[] instructions) {
        File inputFile = new File(path);

        try (FileWriter writer = new FileWriter(inputFile)) {

            for (int i = 0; i < instructions.length; i++) {
                writer.write(instructions[i]);
                writer.write("\n");
            }

        } catch (IOException e) {

        }
    }

    private static boolean isNotEmpty(String[][] rawData, int i) {
        for (int j = 0; j < rawData[i].length; j++) {
            if (!Objects.equals(rawData[i][j], "")) {
                return true;
            }
        }
        return false;
    }

}
