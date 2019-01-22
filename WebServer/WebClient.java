import objectProyect.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

class WebClient
{
    Socket serverSocket;
    OutputStream out; 
    InputStream in;
    DataOutputStream dos;
    DataInputStream dis;
    // Header
     public static void main(String argv[]) {
        HeaderRequest headerRequest;
        HeaderResponse headResponse = new HeaderResponse();
        WebClient client = new WebClient();
        Scanner scanner;
        byte[] toSend;
        byte[] recivedBytes;
        int serverPort = 2407;
        String ipServer = "127.0.0.1";
        String userInput;
        String recivedString;
        scanner = new Scanner(System.in);
        userInput = scanner.nextLine();
        client.connect(ipServer, serverPort);
        headerRequest = new HeaderRequest();
        headerRequest.setPath("hello.com");
        while (true){
            headerRequest.setPath(userInput);
            userInput = headerRequest.toString();
            toSend = client.stringToByte(userInput);
            try {
             client.sendBytes(toSend, toSend.length);
             recivedBytes = client.readBytes();
             recivedString = client.byteToString(recivedBytes);
             headResponse.decompile(recivedString);
             headResponse.setBody(client.readBody(headResponse.getContentLength()));
             System.out.println(headResponse);
            } catch (Exception e) {
             System.err.println("ERROR: client sending mssg");
            }
            System.out.print("User: ");
            scanner = new Scanner(System.in);
            userInput = scanner.nextLine();
        }

     }

     public void connect(String ipServer, int port){
         try {
            serverSocket = new Socket(ipServer, port);
            // input stream
            in = serverSocket.getInputStream();
            dis = new DataInputStream(in);
            // output stream
            out = serverSocket.getOutputStream(); 
            dos = new DataOutputStream(out);
         } catch (Exception e) {
             System.err.println("ERROR: En la conexion de sockets");
         }
        
     }
     
     public String byteToString(byte[] charsOfByte){
        String message = "";
        for (byte chars : charsOfByte) {
            message += (char) chars;
        }
        return message;
    }

     public void sendBytes(byte[] myByteArray, int len) throws IOException {
        if (len < 0)
            throw new IllegalArgumentException("Negative length not allowed");
        for (byte character : myByteArray) {
            // System.out.print((char) character);
            dos.write(character);
        }
   }
    public byte[] readBytes() throws IOException {
        // READS HEADER
        byte newlineUF8 = ((byte) '\n')+3;
        boolean doubleNL = false;
        int counter = 0;
        int newLineCounter = 0;
        byte[] data = new byte[1024];
        // System.out.println((char)dis.readByte());
        while (!doubleNL){
            data[counter] = dis.readByte();
            // System.out.print((char) data[counter]);
            if (data[counter] == newlineUF8 || data[counter] == (byte)'\n'){
                newLineCounter++;
            } else if(!(data[counter] == (byte) '\n')){
                newLineCounter = 0;
            }
            if (newLineCounter > 1){
                doubleNL = true;
            } else {
                counter++;
            }
        }
        return data;
        }

    public byte[] readBody(int len) throws IOException {
        byte[] data = new byte[len];
        if (len > 0) {
            dis.readFully(data);
        }
        return data;
    }
    // W: no puede ser null 
    public byte[] stringToByte(String userInput){
        byte[] charsOfString = new byte[userInput.length()];
        for(int i = 0; i < userInput.length(); i++){
            charsOfString[i] = (byte) userInput.charAt(i);
        }
        return charsOfString;
    }
}