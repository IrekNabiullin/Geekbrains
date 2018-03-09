import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class Encoding {
    String name;
    public Encoding (String name) {
        this.name = name;
    }

        void encoding (String name) {
        byte[] byteArr = new byte[1];
        try {
                byteArr = name.getBytes("utf-8");
            } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Your string is: "+name);
        System.out.println("Your string in bytes is: "+ Arrays.toString(byteArr));
        System.out.println();
        int result = 0;
        int position = 1;
        int hCode = 0;
        for (int i=byteArr.length; i>0; i--){
            position = position*100^i+(int)byteArr[i];
            System.out.println(position);
            hCode = hCode + position;
            position = 1;
        }
            System.out.println(hCode);
    }
}


