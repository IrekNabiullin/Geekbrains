package ru.geekbrains.chat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Класс кнопки:
public class ChatButton extends JButton implements ActionListener {

        // Описание метода из интерфейса ActionPerformed:
        public void actionPerformed(ActionEvent e) {
            this. setText("Send");
        }

        // Конструктор:
        ChatButton(){
            // Вызов конструктора суперкласса. Называем кнопку "Добавить еды":
            super("Message");
            // Положение и размеры кнопки:
 //           setBounds(xb,yb,wb,hb);
            // Отмена отображения рамки фокуса:
            setFocusPainted(false);
            // Регистрация обработчика в кнопке:
            addActionListener(this);
            // Добавим еще один обработчик для вывода текста в консоль. Раскомментировать для отладки
            addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //               myFrame.repaint();
                    //   catVoice [i] = "Button pressed...";
                    //  plate.callMama();
                }
            });
        }
    }

