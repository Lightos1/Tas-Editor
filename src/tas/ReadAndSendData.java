package tas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ReadAndSendData {

    /* TODO: Improve this. */
    public static void playTas() {
        String[] inputs = readFile();

        try (Socket socket = new Socket(Configs.ip, Configs.port)) {
            for (int i = 0; i < inputs.length; i++) {
                sendInput(socket, inputs[i]);
            }
        } catch (IOException e) {

        }
    }

    public static void connectAndConfigure() throws IOException {
        Socket socket = new Socket(Configs.ip, Configs.port);

        for (int i = 0; i < Configs.connectionScript.length; i++) {
            sendInput(socket, Configs.connectionScript[i]);
        }
    }

    public static void disconnect() throws IOException {
        Socket socket = new Socket(Configs.ip, Configs.port);

        /* You *probably* don't have to send it 50 times, but this works for now. */
        for (int i = 0; i < 50; i++) {
            sendInput(socket, "detachController");
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
