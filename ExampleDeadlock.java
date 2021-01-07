package DeadLock;

public class ExampleDeadlock {  // example of a deadlock
    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        DeadThreadOne t1 = new DeadThreadOne();
        DeadThreadTwo t2 = new DeadThreadTwo();
        t1.start();t2.start();

    }
    private static class DeadThreadOne extends Thread {
        public void run() {
            synchronized (lock1) {
                System.out.println("t1 is holding lock 1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("System is waiting for lock 2");
                synchronized(lock2) {
                    System.out.println(" t1 is holding lock 1 and lok 2");
                }
            }
        }
    }

    private static class DeadThreadTwo extends Thread{
        public void run() {
            synchronized (lock2) {
                System.out.println("t2 is holding lock 2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("System is waiting for lock 1");
                synchronized(lock1) {
                    System.out.println(" t2 is holding lock 1 and lok 2");
                }
            }
        }
    }
}
