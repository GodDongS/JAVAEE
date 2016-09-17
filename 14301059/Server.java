package pra;
 

import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.net.ServerSocket;  
import java.net.Socket;  
  
public class Server {  
    public static final int PORT = 3333;  
      
    public static void main(String[] args) {    
        System.out.println("����������...\n");    
        Server server = new Server();    
        server.init();    
    }    
    
    public void init() {    
        try {    
            ServerSocket serverSocket = new ServerSocket(PORT);    
            while (true) {      
                Socket client = serverSocket.accept();    
                new HandlerThread(client);    
            }    
        } catch (Exception e) {    
            System.out.println("�������쳣: " + e.getMessage());    
        }    
    }    
    
    private class HandlerThread implements Runnable {    
        private Socket socket;    
        public HandlerThread(Socket client) {    
            socket = client;    
            new Thread(this).start();    
        }    
    
        public void run() {    
            try {    
                // ��ȡ�ͻ�������    
                DataInputStream input = new DataInputStream(socket.getInputStream());  
                String clientInputStr = input.readUTF();//�ͻ����������д������Ӧ,������� EOFException  
                // ����ͻ�������    
                System.out.println("�ͻ��˷�����������:" + clientInputStr);    
    
                // ��ͻ��˻ظ���Ϣ    
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());     
                // ��������
                int len=clientInputStr.length();
                char[] s=new char[len];
                for(int i=0;i<len;i++){
                	s[i]=clientInputStr.charAt(len-1-i);
                }
                String str=	String.valueOf(s);
                
                out.writeUTF(str);
                out.close();    
                input.close();    
            } catch (Exception e) {    
                System.out.println("������ run �쳣: " + e.getMessage());    
            } finally {    
                if (socket != null) {    
                    try {    
                        socket.close();    
                    } catch (Exception e) {    
                        socket = null;    
                        System.out.println("����� finally �쳣:" + e.getMessage());    
                    }    
                }    
            }   
        }    
    }    
}    