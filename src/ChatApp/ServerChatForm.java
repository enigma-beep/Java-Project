package ChatApp;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerChatForm extends JFrame implements ActionListener{
    JPanel panel;
    JTextField NewMsg;
    JTextArea ChatHistory;
    JButton Send;

    private DataInputStream dis;
    private DataOutputStream dos;
    private ServerSocket server;
    private Socket clientSocket;

    public ServerChatForm() throws IOException {
        panel = new JPanel();
        NewMsg = new JTextField();
        ChatHistory = new JTextArea();
        Send = new JButton("Send");
        this.setSize(500, 500);
        this.setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel.setLayout(null);
        this.add(panel);
        ChatHistory.setBounds(20, 20, 450, 360);
        panel.add(ChatHistory);
        NewMsg.setBounds(20, 400, 340, 30);
        panel.add(NewMsg);
        Send.setBounds(375, 400, 95, 30);
        panel.add(Send);
        this.setTitle("Server");

        Send.addActionListener(this);
        NewMsg.addActionListener(this);

        ChatHistory.setText("Waiting For a client");
        server = new ServerSocket(8080);
        clientSocket = server.accept();
        ChatHistory.append("\nA server has Connected\n\n");

        while (true) {
            dis = new DataInputStream(clientSocket.getInputStream());
            ChatHistory.append("\nClient: " + dis.readUTF());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            dos=new DataOutputStream(clientSocket.getOutputStream());
            dos.flush();
            dos.writeUTF(NewMsg.getText());
            ChatHistory.append("\n\t\t\tMe: "+NewMsg.getText());
            NewMsg.setText("");
        }catch (IOException e1){
            e1.printStackTrace();
        }

    }
    public static void main(String[] args) throws IOException{
        ServerChatForm A = new ServerChatForm();
    }
}