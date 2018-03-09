/**
        * Java 1. Home work 7. Cats and Plate by Swing
        *
        * @author Irek Nabiullin
        * @version dated Feb 18, 2018
        * @link https://github.com/IrekNabiullin
        *
        */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class IrekHW007CatsAndPlateSwing {
    // ball current coordinates
    public int x=70;
    public int y=70;
    public static void main(String[] args) {
        // Главный метод:
        public static void main(String[] args){
            // Объект для изображения:
            ImageIcon img=new ImageIcon("c:/book/pictures/Ilyas01.png");
            // Текстовое значение:
            String txt="<html>Пасть порву! Моргалы выколю!<br>Бу-га-га!!!</html>";
            // Создание объекта окна:
            new MyFrame("Демо",txt,img);
    }
}


// Класс кнопки:
class MyButton extends JButton implements ActionListener{
    // Описание метода из интерфейса ActionPerformed:
    public void actionPerformed(ActionEvent e){
        System.exit(0);
    }
    // Конструктор:
    MyButton(int xb,int yb,int w,int h){
        // Вызов конструктора суперкласса:
        super("Закрыть окно");
        // Положение и размеры кнопки:
        setBounds(xb,yb,w,h);
        // Отмена отображения рамки фокуса:
        setFocusPainted(false);
        // Регистрация обработчика в кнопке:
        addActionListener(this);
    }
}
// Класс панели:
class MyPanel extends JPanel{
    // Конструктор:
    MyPanel(String txt,ImageIcon img){
        // Вызов конструктора суперкласса:
        super();
        // Положение и размеры панели:
        setBounds(10,10,570,220);
        // Рамка вокруг панели:
        setBorder(BorderFactory.createEtchedBorder());
        // Отключение менеджера компоновки:
        setLayout(null);
        // Создание объекта для метки с изображением:
        JLabel imgLbl=new JLabel(img);
        // Положение и размеры метки:
        imgLbl.setBounds(20,20,180,180);
        // Рамка вокруг метки:
        imgLbl.setBorder(BorderFactory.createEtchedBorder());
        // Создание объекта для метки с текстом:
        JLabel txtLbl=new JLabel(txt,JLabel.CENTER);
        // Положение и размеры метки:
        txtLbl.setBounds(220,20,330,180);
        // Рамка вокруг метки:
        txtLbl.setBorder(BorderFactory.createEtchedBorder());
        // Добавление меток на панель:
        add(txtLbl);
        add(imgLbl);

    }
}


//Класс второй панели:
class MyDrawPanel extends JPanel{
    int x=200;
    int y = 20;
    public void paintComponent (Graphics g){
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(Color.blue);
        g.fillOval(x,y,40,40);
    }

    // Конструктор:
    MyDrawPanel(){
        // Вызов конструктора суперкласса:
        super();
        // Положение и размеры панели:
        setBounds(600,10,570,220);
        // Рамка вокруг панели:
        setBorder(BorderFactory.createEtchedBorder());
        // Отключение менеджера компоновки:
        setLayout(null);
        int x=20;
        int y=20;
        for (int i=0;i<100;i++) {
            repaint();
            x=x+1;
            y=y+1;

            try {
                Thread.sleep(6);
            }
            catch (Exception ex)
            {}
        }
    }
}

// Класс для окна:
class MyFrame extends JFrame{
    // Конструктор:
    MyFrame(String name,String txt,ImageIcon img){
        // Вызов конструктора суперкласса:
        super(name);

        // Положение и размеры окна:
        setBounds(100,100,1200,800);
        // Окно постоянных размеров:
        setResizable(false);
        // Реакция на щелчок системной пиктограммы:
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // Отключение менеджера компоновки для окна:
        setLayout(null);
        // Создание объекта панели:
        MyPanel pnl=new MyPanel(txt,img);
        // Создание объекта кнопки:
        MyButton btn=new MyButton(100,240,400,60);
        // Добавление панели в окно:
        add(pnl);
        // Добавление кнопки в окно:
        add(btn);

        //Создание второй панели:
        MyDrawPanel pnltwo = new MyDrawPanel();
        // Добавление панели в окно:
        add(pnltwo);
        int x=20;
        int y=20;
        for (int i=0;i<100;i++) {
            pnltwo.repaint();
            x=x+1;
            y=y+1;
        }
        try {
            Thread.sleep(6);
        }
        catch (Exception ex)
        {}

        // Отображение окна на экране:
        setVisible(true);
    }
}

