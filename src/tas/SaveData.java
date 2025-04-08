package tas;

import ui.InputField;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class SaveData {

    public static void saveInputs(InputField inputs) {
        int row = inputs.getTableModel().getRowCount();
        int col = inputs.getTableModel().getColumnCount();

        /* Convert the inputs of the tas editor field to an array. */
        String[][] data = convertInputs(inputs, row, col - 1);

        /* Convert the array to usable instructions. */
        String[] instructions = convertToInstructions(data, row);

        /* TODO: Add file selector, for now this is hardcoded. */
        String path = "C:\\Users\\user\\Desktop\\inputs.txt";
        writeToFile(path, instructions);
    }

    private static String[][] convertInputs(InputField inputs, int row, int col) {
        String[][] inputData = new String[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                inputData[i][j] = (String) inputs.getTableModel().getValueAt(i, j + 1);
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

                if (rawData[i][j] != null) {
                    instructions[i] += rawData[i][j];
                }

                extendedInputs = isLastInput(rawData, i, j);

                /* If there is more than one input, separate them with a comma, unless it's the last input. */
                if (extendedInputs && j != rawData[0].length - 1 && rawData[i][j] != null) {
                    instructions[i] += ",";
                }
            }
        }
        return instructions;
    }

    private static boolean isLastInput(String[][] rawData, int row, int i) {
        int count = 0;

        while (i < rawData[row].length) {
            if (rawData[row][i] != null) {
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
            if (rawData[i][j] != null) {
                return true;
            }
        }
        return false;
    }

    /* TODO: Improve this. */
    public static void playTas() {
        int port = 6000;
        String ip = "192.168.0.18";

        String[] inputs = readFile();


        try (Socket socket = new Socket(ip, port)) {

            for (int i = 0; i < inputs.length; i++) {
                sendInput(socket, inputs[i]);
            }
        } catch (IOException e) {

        }
    }

    private static void sendInput(Socket socket, String content) throws IOException {
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(content + "\r\n");
    }

    private static String[] readFile() {
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

}
