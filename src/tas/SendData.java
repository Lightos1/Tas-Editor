package tas;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class SendData {

    /* TODO: Improve this. */
    public static void playTas() {
        String[] inputs = ReadData.readFile();

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



}
