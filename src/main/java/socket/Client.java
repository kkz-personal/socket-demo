package socket;

public class Client {

    public static void main(String[] args) {
        for (int i = 1; i < 10; i++) {
            System.out.println("微秒" + System.currentTimeMillis());
            System.out.println("纳秒" + System.nanoTime());
        }
    }
}
