public class NewThread implements Runnable {
    Thread t;
    private String threadName;
    private int sleepTime;

    NewThread (String threadName, int sleepTime) { // constructor
            t = new Thread (this, threadName);
            this.threadName = threadName;
            this.sleepTime = sleepTime;
            System.out.println ("New thread creared: " + t + threadName);
            t.start(); //run thread
        }
        //point of enter to thread
        public void run() {
            try {
                for(int i = 0; i<=5; i++) {
                    System.out.println (threadName + ": "+ i);
                    Thread.sleep(sleepTime*i);
                }
            } catch (InterruptedException e) { System.out.println (threadName + " interrupted.");
            }
            System.out.println (threadName + " finished");
        }

    }