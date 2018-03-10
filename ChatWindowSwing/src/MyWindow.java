import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MyWindow extends JFrame {
    public MyWindow () {
        /*
        В первом блоке кода настраиваем параметры окна и создаём четыре панели для размещения
        элементов, при этом задаём им отличный друг от друга цвет. Располагаем эти панели на форме в
        виде таблицы (2, 2), с помощью GridLayout.
         */
        setBounds(500, 200, 800, 600);
        setTitle("GUI Demo");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridLayout(2,2));
        JPanel [] jp = new JPanel[4];
        for (int i=0; i<4; i++) {
            jp[i] = new JPanel();
            add(jp[i]);
            jp[i].setBackground(new Color (100 + i*40, 100 + i*40,100 + i*40 ) );
        }
        /*
        На первой панели расположено многострочное текстовое поле, которое находится внутри элемента
        JScrollPane, что позволяет пролистывать контент этого поля.
         */
        jp[0].setLayout(new BorderLayout());
        JTextArea jta = new JTextArea();
        JScrollPane jsp = new JScrollPane(jta);
        jp[0].add(jsp);

        /*
        Во второй панели содержится 2 типа элементов: JCheckBox и JRadioButton. JCheckBox предназначен
        для вкл/выкл каких-либо опций (флажков), при этом одновременно может быть включено несколько
        JCheckBox. JRadioButton предоставляет выбор только одного пункта из набора, т.е. может быть
        выбрана только одна опция. Для корректной работы связанных RadioButton их необходимо заносить в ButtonGroup.
         */
        jp[1].setLayout(new FlowLayout());
        JRadioButton[] jrb = new JRadioButton[4];
        ButtonGroup bgr = new ButtonGroup();
        for (int i =0; i < jrb.length; i++) {
            jrb[i] = new JRadioButton("Option #" + i);
            bgr.add(jrb[i]);
            jp[1].add(jrb[i]);
        }
        JCheckBox[] jcb = new JCheckBox[4];
        for (int i=0; i < jcb.length; i++) {
            jcb[i] = new JCheckBox("Check #" + i);
            jp[1].add(jcb[i]);
        }

        /*
        На третьей панели расположена пара элементов типа JComboBox, которые представляют собой
        выпадающие списки. ActionListener для JComboBox проверяет событие выбора пользователем одного из пунктов.
         */
        jp[2].setLayout(new FlowLayout());
        String[] comboStr = {"Item #1", "Item #2", "Item #3", "Item #4"};
        JComboBox<String> jcombo1 = new JComboBox<String >(comboStr);
        JComboBox<String> jcombo2 = new JComboBox<String >(comboStr);
        jp[2].add(jcombo1);
        jp[2].add(jcombo2);
        jcombo1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(jcombo1.getSelectedItem().toString());
            }
        });

        /*
        Четвёртая панель представляет собой пример расстановки элементов с использованием абсолютных
        координат. На ней расположено обычное нередактируемое текстовое поле, которое показывает
        значение, выбранное на JSlider.
         */
        jp[3].setLayout(null);
        JSlider js = new JSlider();
        JLabel jlab = new JLabel("Value: 50");
        js.setMaximum(100);
        js.setMinimum(0);
        js.setValue(50);
        jp[3].add(jlab);
        jp[3].add(js);
        js.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                jlab.setText("Value: " + js.getValue());
            }
        });
        jlab.setBounds(10,10,100,50);
        js.setBounds(20,40,300,100);
        js.setBackground(new Color(160,255,160));
 //       js.setOpaque(false); прозрачность фона

        /*
        Последним пунктом идёт создание верхнего меню окна. Для этого создаются элементы JMenuBar -> JMenu -> JMenuItem.
        Как видно из кода ниже, для обработки нажатия на один из подпунктов меню достаточно «повесить» на него ActionListener.
         */

        JMenuBar mainMenu​ = new JMenuBar();
        JMenu mFile = new JMenu("File");
        JMenu mEdit = new JMenu("Edit");
        JMenuItem miFileNew​ = new JMenuItem("New");
        JMenuItem miFileExit = new JMenuItem("Exit");
        setJMenuBar(mainMenu​);
        mainMenu​.add(mFile);
        mainMenu​.add(mEdit);
        mFile.add(miFileNew​);
        mFile.addSeparator();
        mFile.add(miFileExit);

        miFileExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("BYE");
            }
        });
    setVisible(true);
    }
}
