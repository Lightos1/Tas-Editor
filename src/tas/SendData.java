package tas;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SendData {

    /* TODO: FIXME: Buttons now get dropped after changes :( */
    public static void playTas() {
        String[] inputs = ReadData.readFile();
        if (inputs == null) {
            return;
        }

        try (Socket socket = new Socket(Configs.ip, Configs.port)) {
            OutputStream out = socket.getOutputStream();
            socket.setTcpNoDelay(true);

            for (int i = 0; i < inputs.length; i++) {
                out.write((inputs[i] + "\r\n").getBytes(StandardCharsets.UTF_8));
                out.flush();
            }
        } catch (IOException _) {

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
        OutputStream out = socket.getOutputStream();
        out.write((content + "\r\n").getBytes(StandardCharsets.UTF_8));
    }

}
