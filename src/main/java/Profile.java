import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.PrintWriter;

/**
 * Created by Admin on 08.06.2016.
 */
public class Profile extends JFrame {

    //private String id, name, accNum;
    Main main;
    JPanel panelInfo, panelButtons, panelLogOut;
    JLabel labelInfo;
    JButton przelw, messages, history, logout;
    Profile profile;


    public Profile(Main main /*Profile profile*/){
        super("Profile "+main.name);
        this.main=main;
        //this.profile=profile;
        setSize(300,300);

        panelInfo = new JPanel(new FlowLayout());
        panelButtons = new JPanel(new FlowLayout());
        panelLogOut = new JPanel(new FlowLayout());

        labelInfo = new JLabel("Witaj, "+ main.name +"\n Na koncie masz: "+main.money);

        Service service = new Service();
        przelw = new JButton("Przelew");
        przelw.addActionListener(service);
        messages = new JButton("Wiadomosci");
        messages.addActionListener(service);
        history = new JButton("History");
        history.addActionListener(service);
        logout = new JButton("Wyloguj");
        logout.addActionListener(service);

        panelInfo.add(labelInfo);
        add(panelInfo, BorderLayout.NORTH);

        panelButtons.add(przelw);
        panelButtons.add(messages);
        panelButtons.add(history);
        add(panelButtons, BorderLayout.CENTER);

        panelLogOut.add(logout);
        add(panelLogOut, BorderLayout.SOUTH);

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);



    }

    private class Service implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Przelew")){
                main.out.println("TRANSACTION");
                setVisible(false);
                new Tansaction(main);
            }
            if(e.getActionCommand().equals("Wiadomosci")){
                main.out.println("MESSAGE");
                setVisible(false);
                new Message(main);
            }
            if(e.getActionCommand().equals("History")){
                main.out.println("HISTORY");
                setVisible(false);
                new History(main);
            }
            if(e.getActionCommand().equals("Wyloguj")){
                main.out.println("LOGOUT");
                setVisible(false);
                main.setVisible(true);
            }
        }
    }
}
