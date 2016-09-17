package pra;
 

import java.io.DataInputStream;  
import java.io.DataOutputStream;  
import java.net.ServerSocket;  
import java.net.Socket;  
  
public class Server {  
    public static final int PORT = 3333;  
      
    public static void main(String[] args) {    
        System.out.println("服务器启动...\n");    
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
            System.out.println("服务器异常: " + e.getMessage());    
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
                // 读取客户端数据    
                DataInputStream input = new DataInputStream(socket.getInputStream());  
                String clientInputStr = input.readUTF();//客户端输出流的写方法对应,否则会抛 EOFException  
                // 处理客户端数据    
                System.out.println("客户端发过来的内容:" + clientInputStr);    
    
                // 向客户端回复信息    
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());     
                // 发送数据
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
                System.out.println("服务器 run 异常: " + e.getMessage());    
            } finally {    
                if (socket != null) {    
                    try {    
                        socket.close();    
                    } catch (Exception e) {    
                        socket = null;    
                        System.out.println("服务端 finally 异常:" + e.getMessage());    
                    }    
                }    
            }   
        }    
    }    
}    