package basesocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author maozhi.k
 * @date 2019/9/17
 */
public class BaseSocket {

    public int port;
    public String host;
    public static final int MAX_BUFFER_SIZE = 1024;
    public ServerSocket serverSocket;
    public Socket socket;
    public InputStream inputStream;
    public OutputStream outputStream;

    public void close() {

        try {
            if (this.inputStream != null) {
                this.inputStream.close();
            }
            if (this.outputStream != null) {
                this.outputStream.close();
            }
            if (this.socket != null) {
                this.socket.close();
            }
            if (this.serverSocket != null) {
                this.serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
