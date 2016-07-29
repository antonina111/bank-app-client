import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;

/**
 * Created by Admin on 08.06.2016.
 */
public class Tansaction extends JFrame {

    public static Main main;

    JLabel lreciver, lacc, lmoney, linfo;
    JTextField treciver, tacc, tmoney;
    JButton back, send;
    JPanel mainPanel, buttonPanel, infoPanel;
    static BufferedReader in;
    static PrintWriter out;
    String moneyt;
    int money;

    public Tansaction(Main main){
        super("Przelew");
        this.main=main;

        setSize(300,300);
        mainPanel = new JPanel(new FlowLayout());
        buttonPanel = new JPanel(new FlowLayout());
        infoPanel =  new JPanel(new FlowLayout());

        lreciver = new JLabel("Nazwa odbiorcy: ");
        lacc = new JLabel("Numer Konta: ");
        lmoney = new JLabel("Kwota: ");
        linfo = new JLabel("Przelew");

        treciver = new JTextField();
        treciver.setPreferredSize(new Dimension(170, 20));
        tacc = new JTextField();
        tacc.setPreferredSize(new Dimension(170, 20));
        tmoney = new JTextField();
        tmoney.setPreferredSize(new Dimension(170, 20));
        Service service = new Service();
        back = new JButton("Powrot");
        back.addActionListener(service);
        send = new JButton("Wyslij");
        send.addActionListener(service);

        infoPanel.add(linfo);
        add(infoPanel, BorderLayout.NORTH);

        mainPanel.add(lreciver);
        mainPanel.add(treciver);
        mainPanel.add(lacc);
        mainPanel.add(tacc);
        mainPanel.add(lmoney);
        mainPanel.add(tmoney);
        add(mainPanel, BorderLayout.CENTER);

        buttonPanel.add(back);
        buttonPanel.add(send);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        try{
            out = new PrintWriter(main.socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(main.socket.getInputStream()));
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private class Service implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            m: if(e.getActionCommand().equals("Wyslij")){
                try {

                    String moneys = in.readLine();
                    System.out.println(moneys);
                    money = Integer.parseInt(moneys);
                    String reciver = treciver.getText();
                    String acc = tacc.getText();
                    moneyt = tmoney.getText();

                    if(money<(Integer.parseInt(moneyt))){
                        linfo.setText("Brak srodkow na koncie!");
                        linfo.repaint();

                        out.println("N");
                        out.println("N");
                        out.println("N");

                        Thread.sleep(5000);

                        treciver.setText("");
                        treciver.repaint();
                        tmoney.setText("");
                        tmoney.repaint();
                        tacc.setText("");
                        tacc.repaint();


                    }else{
                        out.println(reciver);
                        out.println(acc);
                        out.println(moneyt);

                        treciver.setText("");
                        treciver.repaint();
                        tmoney.setText("");
                        tmoney.repaint();
                        tacc.setText("");
                        tacc.repaint();

                        linfo.setText("Transakcja zakonczona!");
                        linfo.repaint();
                    }


                }catch (IOException ex){
                    System.out.println("IO Exception");
                }
                catch (InterruptedException ex){
                    System.out.println("Interr");
                }
            }
            if(e.getActionCommand().equals("Powrot"));{
                setVisible(false);
                int m = money - Integer.parseInt(moneyt);
                main.profile.labelInfo.setText("Witaj, "+main.name+ ". Na koncie  masz "+m);
                main.profile.setVisible(false);
                main.profile.labelInfo.repaint();
                main.profile.setVisible(false);
                main.profile.setVisible(true);
            }
        }
    }

}
