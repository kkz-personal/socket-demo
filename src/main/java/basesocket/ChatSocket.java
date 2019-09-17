package basesocket;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * @author maozhi.k
 * @date 2019/9/17
 */
public class ChatSocket extends BaseSocket {

    //ExecutorService threadPool = Executors.newFixedThreadPool(100);

    ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();

    /**
     * 使用factory
     * 创建线程池
     */
    ExecutorService threadPool = new ThreadPoolExecutor(5,10,1,
            TimeUnit.SECONDS,new ArrayBlockingQueue<>(1024),threadFactory,new ThreadPoolExecutor.AbortPolicy());

    public void runAsServer(int port) {
        try {
            this.serverSocket = new ServerSocket(port);

            System.out.println("server started at port " + port);

            //等待客户端的加入
            this.socket = this.serverSocket.accept();
            System.out.println("successful connected with " + socket.getInetAddress());

            //启动监听线程
             this.threadPool.submit(new ListenThread(this.socket));

            waitAndSend();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runAsClient(String host, int port) {
        try {
            this.socket = new Socket(host, port);
            System.out.println("successful connected to server " + socket.getInetAddress());
            this.threadPool.submit(new ListenThread(this.socket));
            waitAndSend();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waitAndSend() {
        try {
            this.outputStream = this.socket.getOutputStream();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                sendMessage(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMessage(String message) {
        try {
            byte[] bytes = message.getBytes("UTF-8");
            int length = bytes.length;
            this.outputStream.write(length >> 8);
            this.outputStream.write(length);
            this.outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ChatSocket chatSocket = new ChatSocket();
        System.out.println("select connect type: 1 for server and 2 for client");
        int type = scanner.nextInt();

        if (type == 1) {
            System.out.print("input server port :");
            int port = scanner.nextInt();
            chatSocket.runAsServer(port);
        } else if (type == 2) {
            System.out.print("input server host: ");
            String host = scanner.next();
            System.out.print("input server port: ");
            int port = scanner.nextInt();
            chatSocket.runAsClient(host, port);
        }
    }
}
