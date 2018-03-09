package ru.geekbrains.chat;

import javax.swing.*;
import java.awt.*;

// Класс для окна:
public class WindowChat  extends JFrame{

    // Конструктор:
    WindowChat() {

        //наименование окна:
        setTitle("Java Chat v.1.0");
        // Положение и размеры окна:
        setBounds(100, 100, 400, 600);
        // Окно постоянных размеров:
 //       setResizable(false);
        // Реакция на щелчок системной пиктограммы:
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Отключение менеджера компоновки для окна:
        // setLayout(null);

        // Создание объекта кнопки:
        JButton btn = new JButton(BorderLayout.SOUTH);
        JButton btn2 = new JButton(BorderLayout.NORTH);

        // Добавление кнопки в окно:
        add(btn);
        add(btn2);

                // Отображение окна на экране:
        setVisible(true);
    }
}