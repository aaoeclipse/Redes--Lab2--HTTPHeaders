import objectProyect.*;

import java.io.*;
import java.net.*;

class WebServer
{
     private static int serverPort = 2407; 
     private static String mainPath = "/home/eclipse/UVG/Redes/Lab2/html/";
     byte newlineUF8 = ((byte) '\n')+3;
     public static void main(String argv[]) {

        WebServer server = new WebServer();
        ServerSocket serverSocket;
        Socket clientSocket;
        HttpRequest request;
        Thread thread;
         try {
            serverSocket = new ServerSocket(serverPort);
        while (true){
            clientSocket = serverSocket.accept();
            request = new HttpRequest(clientSocket, server);
            thread = new Thread(request);
            thread.start();
            }
         }
         catch(Exception e){
          System.err.println("Error en el socket");
          System.err.println(e);
         } 
     }
      public void sendBytes(byte[] myByteArray, Socket clientSocket) throws IOException {
          int len = myByteArray.length;
          if (len < 0)
              throw new IllegalArgumentException("Negative length not allowed");
          OutputStream out = clientSocket.getOutputStream();           
          DataOutputStream dos = new DataOutputStream(out);
          int counter = 0;
          for (byte character : myByteArray) {
              System.out.print((char) character);
              dos.write(character);
          }
          
     }

     public byte[] stringToByte(String userInput){
        byte[] charsOfString = new byte[userInput.length()];
        for(int i = 0; i < userInput.length(); i++){
            charsOfString[i] = (byte) userInput.charAt(i);
        }
        return charsOfString;
    }

    public String byteToString(byte[] charsOfByte){
        String message = "";
        for (byte chars : charsOfByte) {
            message += (char) chars;
        }
        return message;
    }
}

final class HttpRequest implements Runnable{
    Socket clientSocket;
    WebServer server;

   public HttpRequest(Socket clientSocket, WebServer server){
       this.clientSocket = clientSocket;
       this.server = server;
   }
   @Override
   public void run() {
       byte[] usersChars;
       byte[] messageToSend;
       boolean doubleEnter = false;
       String toSend;
       int counter=0;
       int newLineCounter = 0;
       int len;
       HeaderRequest headerRequest = new HeaderRequest();
       HeaderResponse headerResponse;
       byte newlineUF8 = ((byte) '\n')+3;
       try {
        InputStream in = clientSocket.getInputStream();
        DataInputStream dis = new DataInputStream(in);
        while (true) {
            doubleEnter = false;
            newLineCounter = 0;
            headerResponse = new HeaderResponse();
            usersChars = new byte[1024];
            counter = 0;
            // System.out.println("NL =  "+ (byte)'\n');
            // READS header
            while (!doubleEnter){
                usersChars[counter] = dis.readByte();
                // System.out.println((char) usersChars[counter] + " : " + usersChars[counter]);
                if (usersChars[counter] == newlineUF8 || usersChars[counter] == (byte) '\n'){
                    newLineCounter++;
                } else if(!(usersChars[counter] == (byte) '\n')){
                    newLineCounter = 0;
                }

                if (newLineCounter > 1){
                    doubleEnter = true;
                } else {
                    counter++;
                }
            }
            
            headerRequest.decomlpile(server.byteToString(usersChars));
            System.out.println(headerRequest);
            
            headerResponse.setBody(server.stringToByte("<HTML>Hola</HTML>"));


            /* toSend = "HTTP/1.1 304 Not Modified\n"+
                "Date: Sun, 20 Jan 2019 18:25:25 GMT\n"+
                "Server: Apache/2.4.29 (Ubuntu)\n" +
                "Connection: Keep-Alive\n"+
                "Keep-Alive: timeout=5, max=100\n"+
                "ETag: \"13-57fe6ebe0aed2\"\n\n<HTML>Hello</HTML>\n\n"; */
            messageToSend = server.stringToByte(headerResponse.toString());
            server.sendBytes(messageToSend, clientSocket);
        }
       } catch (Exception e) {
            try {
                clientSocket.close();
                System.out.println("connection closed");
        } catch (Exception e1) {
                System.out.println("ERROR: couldn't close connection");
            }
       }
   }
}