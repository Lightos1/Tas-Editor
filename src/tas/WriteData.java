package tas;

import ui.InputField;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public class WriteData {

    public static void saveInputs(InputField inputs) {
        int row = inputs.getTableModel().getRowCount();
        /* Excluding left and right stick positions. */
        int col = inputs.getTableModel().getColumnCount() - 2;

        /* Convert the inputs of the tas editor field to an array. */
        String[][] data = convertInputs(inputs, row, col - 1);

        /* Convert the array to usable instructions. */
        String[] instructions = convertToInstructions(data, row);
        addDelays(instructions);
        addStickInputs(instructions, inputs, row, col);

        /* TODO: Add file selector, for now this is hardcoded. */
        String path = "C:\\Users\\user\\Desktop\\inputs.txt";
        writeToFile(path, instructions);
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

    /* FIXME: There is an issue with the delay when having multiple inputs on col x and inputs on col x-n while being on the same row. */
    private static void addDelays(String[] instructions) {
        int lastNotEmpty = -1;
        int j = 0;

        for (int i = 0; i < instructions.length; i++) {
            if (instructions[i].isEmpty()) {
                j++;
            } else {
                if (lastNotEmpty != -1 && j > 1) {
                    /* TODO: The delay time is hardcoded right now, will depend on the actual config later. */
                    instructions[lastNotEmpty] += ",W" + (30 * j);
                    j = 0;
                }
                lastNotEmpty = i;
            }
        }
    }

    private static void addStickInputs(String[] instructions, InputField inputs, int row, int col) {
        int k = col;

        String[] charSet = {"%", ",&"};

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < 2; j++) {
                if (inputs.getTableModel().getValueAt(i,k) != null && !inputs.getTableModel().getValueAt(i,k).toString().isEmpty()) {
                    if (j == 0) {
                        if (instructions[i].isEmpty()) {
                            instructions[i] += "clickSeq ";
                        } else {
                            instructions[i] += ",";
                        }
                    }

                    instructions[i] += charSet[j] + inputs.getTableModel().getValueAt(i, k);
                    k++;
                }
            }
            k = col;
        }

        for (int i = 0; i < instructions.length; i++) {
            System.out.println(instructions[i]);
        }

    }

    private static String[] convertToInstructions(String[][] rawData, int row) {
        String[] instructions = new String[row];
        /* extended inputs just means that there is more than 1 input in a single row/more than one input left, bad naming, I know :p */
        boolean extendedInputs;

        for (int i = 0; i < rawData.length; i++) {
            for (int j = 0; j < rawData[i].length; j++) {
                boolean hold = false;

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
                    if (i == 0 && i == rawData.length - 1) {
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
                    } else if (previousRowEmpty && nextRowEmpty) {
                        /* One frame -> nop. */
                    } else {
                        hold = true;
                    }

                    /* Add the input if we aren't holding anything. */
                    if (!hold) {
                        instructions[i] += rawData[i][j];
                    }

                }

                extendedInputs = isLastInput(rawData, i, j);

                /* If there is more than one input, separate them with a comma, unless it's the last input or held. */
                /* FIXME: This is a bit of a mess, clean up? */
                if (extendedInputs && j != rawData[0].length - 1 && !rawData[i][j].isEmpty() && !hold) {
                    instructions[i] += ",";
                }

            }
            /* Remove clickSeq in case the entire input row stays the same */
            if (instructions[i].trim().equals("clickSeq")) {
                instructions[i] = "";
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
