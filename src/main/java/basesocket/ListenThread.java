package basesocket;

import java.io.IOException;
import java.net.Socket;

/**
 * @author maozhi.k
 * @date 2019/9/17
 */
public class ListenThread extends BaseSocket implements Runnable {
    ListenThread(Socket socket) {
        this.socket = socket;
        try {
            this.inputStream = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                int first = this.inputStream.read();

                if (first == -1) {
                    this.close();
                    throw new RuntimeException("disconnected");
                    //break;
                }

                int second = this.inputStream.read();
                int msglen = (first << 8) + second;
                byte[] bytes = new byte[msglen];

                this.inputStream.read(bytes);

                System.out.println("message from  [ " + this.socket.getInetAddress() + " ] is " + new String(bytes, "UTF-8"));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
