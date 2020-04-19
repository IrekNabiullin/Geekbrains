/**
 * Java 1. Home work 08. Java1 animation frame
 *
 * @author Irek Nabiullin
 * @version dated Feb 24, 2018
 * @link https://github.com/IrekNabiullin
 *
 */

import javax.swing.*;
import java.awt.*;

public class Java1{

// frame width and heigh
    int aaa = 900;
    int bbb = 935;
// ball  width and heigh at start
    int xx1 = 300;
    int yy1 = 350;
// ball start coordinates 
    int xx = 0;
    int yy = bbb/2-yy1/2;
// ball current coordinates
    int x=xx;
    int y=yy;
// ball current width and heigh
    int x1=xx1;
    int y1=yy1;
// loop coordinates variable
    int aa = aaa;
    
    
    public static void main (String[] args) {
       Java1 gui = new Java1();
       gui.go();      
    }
    public void go() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        MyDrawPanel drawPanel = new MyDrawPanel();
        
        frame.getContentPane().add(drawPanel);
        frame.setSize(aaa,bbb);
        frame.setVisible(true);
        
        int a=1; // X-direction a=1 to the rigt, a=-1 to the left
        int b=1; // Y- direction b=1 down, b=-1 up
        int c=0; // variable to change direction
        int d=0; // variable to change angle of y
        int k=1; // infinite loop
        int j=1; // loop counter
        int i=0; //one direction counter
        while (k==1){
            while (i<aaa-xx1) {
                
                c=j%2;
                if (c==0){
                a=-1;
            }
                else {
                        a=1;
                        }
                x=x+a;
                           
                if (x>=0&&x<(aaa/2)-(xx1/2)&&y>=(bbb/2)&&y<bbb-(yy1/2)&&a==1){
                    b=1;
                }
                if (x>=(aaa/2)-(xx1/2)&&x<aaa&&y>=(bbb/2)-(yy1/2)&&y<bbb&&a==1){
                    b=-1;
                }
                if (x>(aaa/2)+(xx1/2)&&x<aaa&&y>0&&y<=(bbb/2)-(yy1/2)&&a==-1){
                    b=-1;
                }
                if (x>0&&x<=(aaa/2)-(xx1/2)&&y>=-50&&y<=(bbb/2)-(yy1/2)&&a==-1){
                    b=1;
                }

                y=y+b;

// ball compression:  
    //heigh
                if (y>=0&&y<30&&b==1){
                    y1=y1+1;
                }
                if (y>=bbb-yy1-30&&y<bbb-yy1&&b==1){
                    y1=y1-1;
                }
                if (y>=bbb-yy1-30&&y<bbb-yy1&&b==-1){
                    y1=y1+1;
                }
                if (y>0&&y<30&&b==-1){
                    y1=y1-1;
                }
                
    //widht
                if (i>=0&&i<30&&a==1){
                    x1=x1+1;
                }
                if (x>=aaa-xx1-30&&x<aaa-xx1&&a==1){
                    x1=x1-1;
                }
                if (x>=aaa-xx1-30&&x<aaa-xx1&&a==-1){
                    x1=x1+1;
                }
                if (x>=0&&x<30&&a==-1){
                    x1=x1-1;
                }                
                                
// repaint with sleep                
                drawPanel.repaint();
            
                try {
                    Thread.sleep(6);             
                }
                catch (Exception ex)
                {}
            i++;
            }
            i=0;
            j++;
        }
    }
    
    class MyDrawPanel extends JPanel{
        public void paintComponent (Graphics g){          
            g.setColor(Color.blue);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gradient = new GradientPaint (0,500,Color.red,500,500,Color.orange);
            g2d.setPaint(gradient);
            g2d.fillOval(x, y, x1, y1);
                        

            Color newColor = new Color(0, 0, 255);//blue color
            newColor = new Color(0, 0, 255);
                    g.setColor(newColor);
                    Font font = new Font("Tahoma", Font.BOLD|Font.ITALIC, 40);
                    g.setFont(font); 
                    g.drawString("Geekbrains.ru", 450, 700);
                    g.drawString("Java-1", 150, 600);
                    g.drawString("New learning system", 300, 250);
					char[] ch4 = {76, 101, 97, 118, 101, 32, 105, 102, 32, 98, 111, 114, 101, 100};
                    g.drawString(String.valueOf(ch4), 300, 430);
        }
    }
}