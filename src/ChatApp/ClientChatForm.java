package ChatApp;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class ClientChatForm extends JFrame implements ActionListener{
    JPanel panel;
    JTextField NewMsg;
    JTextArea ChatHistory;
    JButton Send;

    private DataInputStream dis;
    private DataOutputStream dos;
    private Socket socket;

    public ClientChatForm() throws IOException{
        panel = new JPanel();
        NewMsg = new JTextField();
        ChatHistory = new JTextArea();
        Send = new JButton("Send");
        this.setSize(500, 500);
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel.setLayout(null);
        this.add(panel);
        ChatHistory.setBounds(20, 20, 450, 360);
        panel.add(ChatHistory);
        NewMsg.setBounds(20, 400, 340, 30);
        panel.add(NewMsg);
        Send.setBounds(375, 400, 95, 30);
        ;
        panel.add(Send);
        this.setTitle("Client");

        Send.addActionListener(this);
        NewMsg.addActionListener(this);

        socket=new Socket("127.0.0.1",8080);

        while(true){
            dis=new DataInputStream(socket.getInputStream());
            ChatHistory.append("\nServer:"+dis.readUTF());
        }



    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            dos=new DataOutputStream(socket.getOutputStream());
            dos.flush();
            dos.writeUTF(NewMsg.getText());
            ChatHistory.append("\n\t\t\tMe: "+NewMsg.getText());
            NewMsg.setText("");

        }catch (IOException e1){
            e1.printStackTrace();
        }

    }
    public static void main(String[] args) throws IOException{
        new ClientChatForm();
    }
}