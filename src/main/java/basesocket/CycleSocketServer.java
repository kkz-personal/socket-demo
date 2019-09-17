package basesocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

/**
 * @author maozhi.k
 * @date 2019/9/16
 */
public class CycleSocketServer extends BaseSocket{

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    CycleSocketServer(int port) {
        this.port = port;
    }

    public void runServerForSign() {
        try {
            this.serverSocket = new ServerSocket(this.port);
            System.out.println("base socket server started.");

            this.socket = serverSocket.accept();

            this.inputStream = socket.getInputStream();

            Scanner scanner = new Scanner(inputStream);

            //循环接收并打印消息
            while (scanner.hasNextLine()) {
                System.out.println("get info from client: " + scanner.nextLine());
            }
            scanner.close();
            this.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据长度界定，消息传递分为两步
     * 1.发送长度
     * 2.获取消息
     */
    public void runServer() {
        try {

            this.serverSocket = new ServerSocket(this.port);
            this.socket = serverSocket.accept();
            System.out.println("server socket running!");
            this.inputStream = socket.getInputStream();
            byte[] bytes;

            while (true) {
                //先读取第一个字节
                int first = this.inputStream.read();
                //是-1则表示输入流已经关闭
                if (first == -1) {
                    this.close();
                    break;
                }
                //读取第二个字节
                int second = this.inputStream.read();

                //用位运算将两个字节拼起来成为真正的长度
                int length = (first <<8 ) +second;

                bytes = new byte[length];

                this.inputStream.read(bytes);

                System.out.println("receive message : " + new String(bytes,"UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        CycleSocketServer cycleSocketServer = new CycleSocketServer(8888);

        //cycleSocketServer.runServerForSign();

        cycleSocketServer.runServer();

    }
}
