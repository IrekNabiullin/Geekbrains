
public class Array1 {
	public static void main(String[] args) {
		int [] arr = {1,2,3,4,5};
		task1 (arr);
	}
    static int[] task1(int[] arr) {
		System.out.println ("arr length: " + arr.length);
        for (int i = arr.length-1; i > 0; i--) {
			System.out.println (arr[i] + " index: " + i);
            if (arr[i] == 4) {
//				System.out.println(" найдена " + arr[i]);
 //               int[] outArray = new int[arr.length - i];
 //               int k=0;
 //               for (int j = i+1; j < arr.length; j++) {
 //                   outArray[k] = arr[j];
 //                   System.out.println(outArray[k] + ", ");
 //                   k++;
 //               }
 //               return outArray;
				return arr;
            }
        }
        throw new RuntimeException("4 not found");
    }
}
