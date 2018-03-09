public class Main {

    public static void main(String[] args) {
//        new NewThread ("Thread1", 100); // create second Thread
//        new NewThread ("Thread2", 200); // create second Thread
        for (int i = 0 ; i<100; i++) {
            new
                    NewThread ("Thread#"+i, 100+i);
        }
        try {
            for(int i = 0; i<=6; i++) {
                System.out.println ("Main thread :" + i);
                Thread.sleep(400);
            }
        } catch (InterruptedException e) {
            System.out.println ("Main thread interrupted.");
        }
        System.out.println ("Main thread finished");


    }
}
