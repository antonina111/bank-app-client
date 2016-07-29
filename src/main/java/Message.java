import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Vector;

/**
 * Created by Admin on 08.06.2016.
 */
public class Message extends JFrame {

   static Main main;
    JPanel panelM, panelA, panelB;
    JTextField answer, to;
    JButton back, send;
    JLabel info;
    Vector<Messag> list = new Vector<>();
    boolean ok = false;
    static BufferedReader in;
    static PrintWriter out;

    public Message(Main main){
        super("Wiadomosci");
        this.main=main;



        setSize(1000,700);
        panelM= new JPanel(new FlowLayout());
        panelA = new JPanel(new FlowLayout());
        panelB = new JPanel(new FlowLayout());

        answer = new JTextField("Answer");
        answer.setPreferredSize(new Dimension(300, 20));
        to = new JTextField();
        to.setPreferredSize(new Dimension(100, 20));

        Service service = new Service();

        back = new JButton("Powrot");
        back.addActionListener(service);
        send = new JButton("Wyslij");
        send.addActionListener(service);

        info = new JLabel();

        add(panelM, BorderLayout.NORTH);

        panelA.add(info);
        panelA.add(answer);
        panelA.add(to);
        add(panelA, BorderLayout.CENTER);

        panelB.add(back);
        panelB.add(send);
        add(panelB, BorderLayout.SOUTH);

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        try{
            out = new PrintWriter(main.socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(main.socket.getInputStream()));

            int i = Integer.parseInt(in.readLine());

            for(int ia = 0; ia<i; ia++){
                try {
                    String text=in.readLine();
                    System.out.println(text +"Message");
                    String idM = in.readLine();
                    System.out.println(idM +"Message");
                    JLabel area = new JLabel("Wiadomosc: "+text);
                    area.setText(text);

                    JButton delete = new JButton("Usun wiadomosc");
                    delete.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(e.getActionCommand().equals("Usun wiadomosc")){
                                area.setVisible(false);
                                delete.setVisible(false);
                                out.println("DELETE");
                                out.println(idM);
                                list.remove(this);
                            }
                        }
                    });

                    panelM.add(area);
                    panelM.add(delete);
                    panelM.repaint();

                }catch (IOException e){
                    System.out.println("IO Exception");
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private class Messag implements Runnable {
        @Override
        public void run() {
           m: while(!ok){
                try {
                    String text=in.readLine();
                    System.out.println(text +"Message");
                   String idM = in.readLine();
                    System.out.println(idM +"Message");
                    JLabel area = new JLabel("Wiadomosc: "+text);
                    area.setText(text);

                   JButton delete = new JButton("Usun wiadomosc");
                    delete.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(e.getActionCommand().equals("Usun wiadomosc")){
                                area.setVisible(false);
                                delete.setVisible(false);
                                out.println("DELETE");
                                out.println(idM);
                                list.remove(this);
                            }
                        }
                    });

                    panelM.add(area);
                    panelM.add(delete);
                    panelM.repaint();


                }catch (IOException e){
                    System.out.println("IO Exception");
                }
            }
        }
    }


    private class Service implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Wyslij")){

                String message = answer.getText();
                String tto = to.getText();

                out.println(message);
                out.println(tto);

                info.setText("Wiadomosc wysalana do uzytkownika: "+tto);

            }
            if(e.getActionCommand().equals("Powrot")){
                setVisible(false);
                main.profile.setVisible(true);
                out.println("N");
                out.println("N");
                ok=true;
            }
        }
    }
}
