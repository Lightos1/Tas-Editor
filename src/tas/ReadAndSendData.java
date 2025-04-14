package tas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ReadAndSendData {

    private static int port = 6000;
    private static String ip = "192.168.0.20";

    /* TODO: Improve this. */
    public static void playTas() {

        String[] inputs = readFile();

        try (Socket socket = new Socket(ip, port)) {

            for (int i = 0; i < inputs.length; i++) {
                sendInput(socket, inputs[i]);
            }
        } catch (IOException e) {

        }
    }

    public static void disconnect() throws IOException {
        Socket socket = new Socket(ip, port);

        /* You *probably* don't have to send it 100 times, but this works for now. */
        for (int i = 0; i < 100; i++) {
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
