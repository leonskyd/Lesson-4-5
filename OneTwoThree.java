package DeadLock;

public class OneTwoThree {  // 3 потока должны напечатать "один,два ,три" в правильном порядке
    static Object mon = new Object(); // 3 потока будут синхронизированы по 1 объекту
    static volatile int currentNum = 1; // переменная не будет кешироваться внутри процесса.
    // volatile можно использовать когда один процесс меняет переменную, остальные ее читают. Здесь использовать ее бессмысленно.
    static final int num = 6;

    public static void main(String[] args) {

        new Thread (()-> {
            try {
                for (int i = 0; i < num; i++) {
                    synchronized (mon) {
                        while (currentNum != 1) {
                            mon.wait();
                        }
                        System.out.print("A");
                        currentNum = 2;
                        mon.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread (()-> {
            try {
                for (int i = 0; i < num; i++) {
                    synchronized (mon) {
                        while (currentNum != 2) { // пока переменная не приняла нужное значение
                            mon.wait(); // поток монитор не занимает
                        }
                        System.out.print("B"); // когда переменная приняла нужное значение, печатаем "два"
                        currentNum = 3; // переназначаем переменную для следующего потока
                        mon.notifyAll(); // ПРОСНИТЕСЬ ВСЕ! будим все 3 потока. Нужный поток захватит переменную а лишние лягут "спать wait ()" .
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread (()-> {
            try {
                for (int i = 0; i < num; i++) {
                    synchronized (mon) {
                        while (currentNum != 3) {
                            mon.wait();
                        }
                        System.out.print("C");
                        currentNum = 1;
                        mon.notifyAll();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
