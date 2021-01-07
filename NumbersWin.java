package DeadLock;

public class NumbersWin {
    private static Object mon = new Object();
    private static int printed = 1;

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) { // we are making 10 threads
            final int w = i;
            new Thread(() -> {
                synchronized (mon) { // synchronized by one object
                    try { // wait, notify, notifyAll are available only in synchronized sections
                        while (printed != w)
                            mon.wait(); // освобождает монитор и переводит вызывающий поток
                        printed ++; // в состояние ожидания до тех пор, пока другой поток не вызовет метод notify()
                        System.out.println(w);
                        mon.notifyAll(); // возобновляет работу всех потоков, у которых ранее был вызван метод wait()
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }
}
