package ru.geekbrains;

//задание 3. Написать класс МФУ, на котором возможно одновременно выполнять печать и сканирование
//  документов, но нельзя одновременно печатать или сканировать два документа. При печати в
//  консоль выводится сообщения «Отпечатано 1, 2, 3,... страницы», при сканировании –
//  аналогично «Отсканировано...». Вывод в консоль с периодом в 50 мс.
public class MFU {


    public static Object monitorMFU = new Object();
    public static Object monitorScan = new Object();
    public static Object monitorPrint = new Object();

    public void scanDocument (String doc) {
        synchronized (monitorScan) {
            for (int i = 0; i < 5; i++) {
                try{
 //                   monitorScan.wait();
                    System.out.println("Scanning document " + doc + " page " + i + "...");
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                notifyAll();
            }
        }
    }

    public void printDocument (String doc) {
        synchronized (monitorPrint) {
            for (int i = 0; i < 5; i++) {

                try {
 //                   monitorPrint.wait();
                    System.out.println("Printing document "  + doc + " page " + i + "...");
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
 //               notifyAll();
            }
        }
    }

    public void workWithDocument (String doc, int mode) {
 //       this.mode = mode;
        if (mode == 1) {
            scanDocument(doc);
            /*
            synchronized (monitorMFU) {
                try{
                    monitorMFU.wait();
                    scanDocument(doc);
                } catch (InterruptedException e) {
                e.printStackTrace();
            }
            notifyAll();
            }
            */
        } else if (mode == 2) {
            printDocument(doc);
            /*
            synchronized (monitorMFU) {
                try{
                    monitorMFU.wait();
                    printDocument(doc);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                notifyAll();
            }
            */
        } else if (mode == 3) {

            scanDocument(doc);
            printDocument(doc);
            /*
            synchronized (monitorMFU) {
                try{
                    monitorMFU.wait();
                    scanDocument(doc);
                    printDocument(doc);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                notifyAll();
            }
            */
        } else
            System.out.println("MFU mode is not correct");
    }





    public static void main (String[] args ) {
        String doc1 = "Document1";
        String doc2 = "Document2";
        String doc3 = "Document3";
        String doc4 = "Document4";
        MFU mfu = new MFU();

        new Thread(() -> mfu.workWithDocument(doc1,1)).start();
        new Thread(() -> mfu.workWithDocument(doc2,2)).start();
        new Thread(() -> mfu.workWithDocument(doc3,3)).start();
        new Thread(() -> mfu.workWithDocument(doc4,3)).start();

        }
}
