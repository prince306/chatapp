import java.net.*;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;
// import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class client extends JFrame {
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    // declare component
    private JLabel heading=new JLabel("Client Area");
    private JTextArea messageArea=new JTextArea();
    private JTextField messageInput=new JTextField();
    private Font font=new Font("Roboto",Font.PLAIN,20); 
    public client() 
    {
     try{
        // while(!socket.isClosed()){
              System.out.println("Sending request to server");
              socket=new Socket("10.11.14.228",7770);
              System.out.println("connection done");
              br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
              out=new PrintWriter(socket.getOutputStream());


            createGUI();  
            handleEvents();



              startReading();
            //   startWriting();

        // }
        } 
            catch(Exception e)
            {
                e.printStackTrace();
            }
    }

         private void createGUI()
         {
           this.setTitle("Client {Messenger}");
          this.setSize(500,500);
          this.setLocationRelativeTo(null);
          this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

         //    ....
         heading.setFont(font);
        //  heading.setBackground(Color.BLACK);
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
          JScrollPane js=new JScrollPane(messageArea);
          this.add(js,BorderLayout.CENTER);
         this.add(messageInput,BorderLayout.SOUTH);


         this.setVisible(true);


         }

         /**
         * 
         */
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
                   System.out.println("Server terminated the chat");
                   JOptionPane.showMessageDialog(this, "Server terminatedthe chat");
                   messageInput.setEnabled(false);
                   socket.close();
                    break;
                 }
                
                  
                    //  System.out.println("server: "+msg);
                  messageArea.append("Server :"+msg+"\n");
                 
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
                  while( !socket.isClosed())
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
                 e.printStackTrace();
                // System.out.println("connection closed");

             }
             System.out.println("connection closed");

            
       
         }; 
             new Thread(r2).start();

    }



    public static void main(String args[])
    {
       System.out.println("this is client....");
       new client();
    }
    
}
