package basesocket;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author maozhi.k
 * @date 2019/9/16
 */
public class CycleSocketClient extends BaseSocket {


    CycleSocketClient(String host, int port) {
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

    public void sendForSign(String message) {
        //约定 \n为消息结束标记
        String sendMsg = message + "\n";

        try {
            this.outputStream.write(sendMsg.getBytes("UTF-8"));


        } catch (IOException e) {
            System.out.println(sendMsg);
            e.printStackTrace();
        }
    }

    public void sendMessage(String message){

        try {
            //将message转化为bytes数组
            byte[] bytes = message.getBytes("UTF-8");

            //传输两个字节长度。采用位移实现
            int length = bytes.length;
            this.outputStream.write(length >> 8);
            this.outputStream.write(length);

            //传输完长度之后
            this.outputStream.write(bytes);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        CycleSocketClient cycleSocketClient = new CycleSocketClient("127.0.0.1", 8888);
        cycleSocketClient.connectServer();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            String message = scanner.nextLine();

            //cycleSocketClient.sendForSign(message);
            cycleSocketClient.sendMessage(message);
        }
    }

}
