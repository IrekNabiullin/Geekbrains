public class NewThread implements Runnable {

    Thread t; // поток
    float [] halfArray; // массив на входе

    NewThread (float[] halfArray) {
// создать новый поток
        t = new Thread(this, "New thread");
        this.halfArray  = halfArray ;
        t.start(); // запустить поток исполнения
    }

    // точка входа в поток
    public void run() {
        arrayCalc(halfArray);
    }
    public static float[] arrayCalc(float[] halfArray) {
        for (int i = 0; i < halfArray.length; i++) {
            halfArray[i] = (float) (halfArray[i] * Math.sin(0.2f + i / 5) * (Math.cos(0.4f + i / 2)));
        }

        return halfArray;
    }
}