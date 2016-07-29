
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * Created by Admin on 08.06.2016.
 */
public class History extends JFrame {
    Main main;
    JPanel infoPanel, messagesPanel, buttonPanel;
    JButton back, clerHistory;
    JLabel info;
    static BufferedReader in;
    static PrintWriter out;

    public History(Main main){
        super("Historia");
        this.main=main;

        setSize(300,300);

        infoPanel = new JPanel(new FlowLayout());
        messagesPanel = new JPanel(new FlowLayout());
        buttonPanel = new JPanel(new FlowLayout());

        info = new JLabel("Historia uzytkownika "+main.name);
        infoPanel.add(info);

        back = new JButton("Powrot");
        clerHistory = new JButton("Wyczysc historie");

        Service service = new Service();
        back.addActionListener(service);
        clerHistory.addActionListener(service);

        buttonPanel.add(back);
        buttonPanel.add(clerHistory);

        add(infoPanel, BorderLayout.NORTH);
        add(messagesPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);


        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        try {
            out = new PrintWriter(main.socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(main.socket.getInputStream()));

            int count = Integer.parseInt(in.readLine());

            for(int i=0; i<count; i++){
                String opis, value;
                try {
                    opis = in.readLine();
                    value = in.readLine();

                    JLabel label = new JLabel("Przelew: "+opis+" kwota: "+value+"\n");
                    messagesPanel.add(label);
                    messagesPanel.repaint();

                }catch (IOException e){
                    e.printStackTrace();
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private class Service implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Wyczysc historie")){

                out.println("DELETE");
                messagesPanel.setVisible(false);

            }
            if(e.getActionCommand().equals("Powrot")){
                out.println("END");
                setVisible(false);
                main.profile.setVisible(true);
            }
        }
    }
}
