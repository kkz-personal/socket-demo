package basesocket;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author maozhi.k
 * @date 2019/9/12
 */
public class BaseSocketServer extends BaseSocket {


    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    BaseSocketServer(int port) {
        this.port = port;
    }

    /**
     * 单次通信
     */
    public void runServerSingle() {
        try {
            this.serverSocket = new ServerSocket(this.port);
            System.out.println("----------base socket server started------------");
            this.socket = serverSocket.accept();
            this.inputStream = socket.getInputStream();
            byte[] readBytes = new byte[MAX_BUFFER_SIZE];

            int msgLen;
            StringBuilder stringBuilder = new StringBuilder();

            while ((msgLen = inputStream.read(readBytes)) != -1) {
                stringBuilder.append(new String(readBytes, 0, msgLen, "UTF-8"));
            }

            System.out.println("Get message from client : " + stringBuilder.toString());

            this.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 双向通信
     */
    public void runServer() {
        try {
            this.serverSocket = new ServerSocket(port);
            System.out.println("----------base socket server started------------");
            this.socket = serverSocket.accept();
            this.inputStream = socket.getInputStream();
            byte[] readBytes = new byte[MAX_BUFFER_SIZE];

            int msgLen;
            StringBuilder stringBuilder = new StringBuilder();

            while ((msgLen = this.inputStream.read(readBytes)) != -1) {
                stringBuilder.append(new String(readBytes, 0, msgLen, "UTF-8"));
            }

            System.out.println("received message: " + stringBuilder.toString());

            //告诉客户端接受完毕，之后只能发送
            this.socket.shutdownInput();

            this.outputStream = socket.getOutputStream();

            String receipt = "we received your message : " + stringBuilder.toString();

            this.outputStream.write(receipt.getBytes("UTF-8"));

            this.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        BaseSocketServer baseSocketServer = new BaseSocketServer(8888);
        //单向通信
        //baseSocketServer.runServerSingle();
        //双向通信
        baseSocketServer.runServer();
    }
}
