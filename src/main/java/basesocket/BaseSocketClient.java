package basesocket;

import java.io.IOException;
import java.net.Socket;

/**
 * @author maozhi.k
 * @date 2019/9/12
 */
public class BaseSocketClient extends BaseSocket {


    BaseSocketClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 获取连接
     */
    public void connectServer() {
        try {
            this.socket = new Socket(this.host, this.port);
            this.outputStream = this.socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 单向通信
     *
     * @param message 消息内容
     */
    public void sendSingle(String message) {
        try {
            this.outputStream.write(message.getBytes("UTF-8"));

            this.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 双向通信
     * @param message 消息
     */
    public void sendMessage(String message) {
        try {

            this.outputStream.write(message.getBytes("UTF-8"));

            //发送完毕
            this.socket.shutdownOutput();

            this.inputStream = this.socket.getInputStream();
            byte[] readBytes = new byte[MAX_BUFFER_SIZE];
            int msgLen;
            StringBuilder stringBuilder = new StringBuilder();

            while ((msgLen = inputStream.read(readBytes)) != -1) {
                stringBuilder.append(new String(readBytes, 0, msgLen, "UTF-8"));
            }
            System.out.println("got receipt: " + stringBuilder.toString());

            this.inputStream.close();
            this.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        BaseSocketClient baseSocketClient = new BaseSocketClient("127.0.0.1", 8888);
        baseSocketClient.connectServer();
        //单向通信
        //baseSocketClient.sendSingle("hello");
        //双向通信
        baseSocketClient.sendMessage("hello");

    }
}
