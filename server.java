import java.net.*;

import javax.swing.*;

import java.awt.*;
// import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;




import java.io.*;

public class server extends JFrame{
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;
    private JLabel heading=new JLabel("Server  Area");
    private JTextArea messageArea=new JTextArea();
    private JTextField messageInput=new JTextField();
    private Font font=new Font("Roboto",Font.PLAIN,20); 

    public server(){
        try{
            server=new ServerSocket(7770);
            
            System.out.println("Server is ready to pair");
            
            System.out.println("waiting...");
            
            socket= server.accept();
            
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            out=new PrintWriter(socket.getOutputStream());
             createGUI();  
            handleEvents();
            startReading();
            // startWriting();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }


    }
    private void createGUI()
         {
           this.setTitle("Server {Messenger}");
          this.setSize(500,500);
          this.setLocationRelativeTo(null);
          this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         //    ....
         heading.setFont(font);
         heading.setBackground(Color.BLACK);
          messageArea.setFont(font);
         messageInput.setFont(font);
         // heading.setIcon(new ImageIcon("logo.png"));
         heading.setHorizontalAlignment(SwingConstants.CENTER);
          heading.setVerticalAlignment(SwingConstants.BOTTOM);
         heading.setHorizontalAlignment(SwingConstants.CENTER);

          heading.setBorder(BorderFactory.createEmptyBorder(20,40,20,40));
        messageArea.setEditable(false);
        messageInput.setHorizontalAlignment(SwingConstants.LEFT);
         // ....
         this.setLayout(new BorderLayout());
         // adding the component to   frame......
          this.add(heading,BorderLayout.NORTH);
          this.add(messageArea,BorderLayout.CENTER);
         this.add(messageInput,BorderLayout.SOUTH);


         this.setVisible(true);


         }
         public void handleEvents()
         {
           messageInput.addKeyListener(new KeyListener(){

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //  TODO Auto-generated method stub
                // System.out.println("key released "+ e.getKeyCode());
                if(e.getKeyCode()==10)
                {
                    String contentToSend=messageInput.getText();
                    messageArea.append("Me : "+contentToSend+"\n");
                    out.println(contentToSend);
                    out.flush();
                    messageInput.setText("");
                    messageInput.requestFocus();
                }
                
            }

           });
            


         }
    public void startReading()
    {
    //   thread- read krke deta rahega;
         Runnable r1=()->{
            System.out.println("reader started...");
                 try{
                 while(true){
                 
                 String msg= br.readLine();

                 if (msg.equals("bye"))
                 {
                   System.out.println("client terminated the chat");
                    JOptionPane.showMessageDialog(this, "Server terminatedthe chat");
                   messageInput.setEnabled(false);
                   socket.close();
                    break;
                 }
                
                  messageArea.append("client:-"+msg+"\n");
                    //  System.out.println("client: "+msg);
                  
            }
        }
        
        catch(Exception e)
        {
            // e.printStackTrace();
            System.out.println("connection closed");

        }
             

         };
         new Thread(r1).start();

    }
    public void startWriting()
    {
    //    thread- data user lega and then client tak pahuchayega
         Runnable r2=()->{
            System.out.println("writer started");
                  try{
                  while(!socket.isClosed())
                 {
                    
                     BufferedReader br1= new BufferedReader(new InputStreamReader(System.in));
                     String content= br1.readLine(); 
                     out.println(content);
                     out.flush();
                     if(content.equals("bye"))
                     {
                        socket.close();
                        break;
                     }
                 }
                //  System.out.println("connection closed");

                }
             
             catch(Exception e)
             {
                //  e.printStackTrace();
                System.out.println("connection closed");

             }
            //  System.out.println("connection closed");

            
       
         }; 
             new Thread(r2).start();

    }
     public static void main(String args[])
     {
        System.out.println("this is server ....going to start server");
        new server();
     }
 }
