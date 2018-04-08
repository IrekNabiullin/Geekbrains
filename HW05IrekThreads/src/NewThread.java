public class NewThread implements Runnable {

    Thread t; // поток
    float [] halfArray; // массив на входе
    int iStart;
    int iFinish;

    NewThread (float[] halfArray, int iStart, int iFinish) {
// создать новый поток
        t = new Thread(this, "New thread");
        this.halfArray  = halfArray ;
        this.iStart = iStart;
        this.iFinish = iFinish;
        t.start(); // запустить поток исполнения
    }

    // точка входа в поток
    public void run() {
        arrayCalc(halfArray, iStart, iFinish);
    }
    public static float[] arrayCalc(float[] halfArray, int iStart, int iFinish) {

        for (int i = iStart, j = 0; i < iFinish; i++) {
            halfArray[j] = (float) (halfArray[j] * Math.sin(0.2f + i / 5) * (Math.cos(0.4f + i / 2)));
            }

        return halfArray;
    }
}