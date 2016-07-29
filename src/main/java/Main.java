import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Admin on 08.06.2016.
 */
public class Main extends JFrame {

    static BufferedReader in;
    static PrintWriter out;
    JPanel logoPanel, loginPanel, infoPanel;
    JLabel labelLogo, labelLogin, labelPassword, labelInfo;
    JTextField textLogin, textPassword;
    JButton login;
    static boolean ok = false;

    String id, name, accNum, money;
    static Main main;
    static Socket socket;
    Profile profile;

    public Main(){
        super("Priwat 24");

        setSize(1050, 350);
        setLayout(new BorderLayout());
        logoPanel = new JPanel(new FlowLayout());
        loginPanel = new JPanel(new FlowLayout());
        infoPanel = new JPanel(new FlowLayout());

        labelLogo = new JLabel();

        labelLogo.setIcon(new ImageIcon("src/main/resources/logo.png"));
        labelLogin = new JLabel("Wpisz login: ");
        labelPassword = new JLabel("wpisz haslo: ");
        labelInfo = new JLabel("Logowanie do systemu");

        textLogin = new JTextField();
        textLogin.setPreferredSize(new Dimension(150, 20));
        textPassword = new JTextField();
        textPassword.setPreferredSize(new Dimension(150, 20));

        Service service = new Service();
        login = new JButton("Zaloguj");
        login.addActionListener(service);

        logoPanel.add(labelLogo);

        infoPanel.add(labelInfo);

        loginPanel.add(labelLogin);
        loginPanel.add(textLogin);
        loginPanel.add(labelPassword);
        loginPanel.add(textPassword);
        loginPanel.add(login);

        add(logoPanel, BorderLayout.NORTH);
        add(infoPanel, BorderLayout.CENTER);
        add(loginPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private class Service implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Zaloguj")){
                String login = textLogin.getText();
                String password = textPassword.getText();

                out.println(login);
                out.println(password);

                System.out.println(login+password);

                try {
                    id = in.readLine();
                    name = in.readLine();
                    accNum = in.readLine();
                    money=in.readLine();


                    if(!id.equals("N")){
                        labelInfo.setText("Witaj, "+name+ "!");
                        labelInfo.repaint();
                        setVisible(false);


                        profile = new Profile(main);
                        ok=true;
                    }
                    else{
                        labelInfo.setText("Nieprawidlowe haslo lub login!");
                        labelInfo.repaint();
                        labelLogin.setText("");
                        labelPassword.setText("");
                    }

                }catch (IOException ex){
                    System.out.println("IO Exception");
                }


            }

        }
    }

    public void start(){

    }

    public void wyswietlKomunikat(String tekst){
        labelInfo.setText(tekst);
    }

    public static void main(String[] args) {

        try {
            socket = new Socket("localhost", 1234);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            main = new Main();

            /*while (!ok){

            }*/

        }catch (IOException e){
            System.out.println("IO Exception");
        }

    }
}
