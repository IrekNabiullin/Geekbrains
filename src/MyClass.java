import java.io.*;

public class MyClass {

public static void main (String[] args ) {
	class WriteToFile implements Runnable {
		private PrintWriter pw;
		private String str;
		
		public WriteToFile (PrintWriter pw, String str) {
			this.pw = pw;
			this.str = str;			
		}
		
		@Override
		public void run(){
			for (int i=0; i<10; i++) {
				try{
					Thread.sleep(200);
					pw.println(str);
					pw.flush();
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
					
		}
	}
	
	 PrintWriter pw = null;
	try {
		pw = new PrintWriter("files/test1.txt");
		Thread t1 = new Thread(new WriteToFile(pw, "Thread1"));
		Thread t2 = new Thread(new WriteToFile(pw, "Thread2"));
		Thread t3 = new Thread(new WriteToFile(pw, "Thread3"));
		try{
			t1.start();
			t2.start();
			t3.start();
			t1.join();
			t2.join();
			t3.join();
		} catch (Exception e){
			e.printStackTrace();
		}
	}catch (FileNotFoundException e) {
		e.printStackTrace();
	} finally {
			pw.close();
		}
	}	
}
