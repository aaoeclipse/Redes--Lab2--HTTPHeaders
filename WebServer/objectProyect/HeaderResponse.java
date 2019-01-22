package objectProyect;
import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HeaderResponse {
    String httpVersion;
    int statusCode;
    boolean success;
    boolean connectionClosed;
    String date;
    String server;
    String cast;
    int contentLength;
    String contentType;
    String lastModified;
    SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");

    byte[] body;


    public HeaderResponse(String httpVersion, int statusCode, boolean success, boolean connectionClosed, String date, String server, String cast, int contentLength, String contentType) {
        this.httpVersion = httpVersion;
        this.statusCode = statusCode;
        this.success = success;
        this.connectionClosed = connectionClosed;
        this.date = date;
        this.server = server;
        this.cast = cast;
        this.contentLength = contentLength;
        this.contentType = contentType;
    }

    public HeaderResponse(){
        this.httpVersion = "HTTP/1.1";
        this.connectionClosed = true;
        this.statusCode = 200;
        this.connectionClosed = false;
        
        Date now = new Date();
        this.date = format.format(now);
        this.server = "Eclipse's Server";
        this.lastModified = "" + LocalDateTime.now();
    }

    public void decompile(String decomlpile){
        // Split by newlines
        String[] lines = decomlpile.split("\\r?\\n");
        // REMOVE SPACES
        for(int i=1;i<lines.length;i++){
            lines[i] = lines[i].replaceAll(" ","");
        }
        // GET FIRST LINE, HEAD OR GET
        String[] Getter = lines[0].split("\\s+");
        setStatusCode(Integer.parseInt(Getter[1]));

        // GET CONTENT
        String contentOfHeader[][] = new String[lines.length-1][2];
        for (int i = 1; i < lines.length; i++){
            contentOfHeader[i-1] = lines[i].split(":");
        }
        for (String[] var : contentOfHeader) {
            if (var[0].equalsIgnoreCase("DATE"))
                setDate(var[1]);
            if (var[0].equalsIgnoreCase("Server"))
                setServer(var[1]);
            if (var[0].equalsIgnoreCase("Last-modified"))
                setLastMod(var[1]);
            if (var[0].equalsIgnoreCase("Content-length"))
                setContentLength(Integer.parseInt(var[1]));
            if (var[0].equalsIgnoreCase("content-type"))
                setContentType(var[1]);
        }
    }

    @Override
    public String toString(){
        Date now = new Date();
        String date = format.format(now);
        String headerInString = "";
        if (httpVersion.contentEquals("") || httpVersion == null)
            httpVersion = "HTML 1.1";
        headerInString += httpVersion + " "+ this.statusCode +" OK" + "\n";
        if (connectionClosed)
            headerInString += "connection: close"+ "\n";
        headerInString += "Date: " + date + "\n";
        headerInString += "Server: Eclipse's Server \n";
        headerInString += "Last-Modified: " + date + "\n";
        headerInString += "Content-Length: " + contentLength + "\n";
        headerInString += "Content-Type: text/html\n";
        headerInString += "\n";
        if (body != null)
            for (int i = 0; i < body.length; i++)
               headerInString += (char) body[i];
        return headerInString;
    }
    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public boolean isConnectionClosed() {
        return connectionClosed;
    }

    public void setConnectionClosed(boolean connectionClosed) {
        this.connectionClosed = connectionClosed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public void setBody(byte[] body){
        this.body = body;
        contentLength = body.length;
    }
    public byte[] getBody(){
        return body;
    }
    public String getLastMod() {
        return lastModified;
    }

    public void setLastMod(String lastModified) {
        this.lastModified = lastModified;
    }

    private byte[] stringToByte(String userInput){
        byte[] charsOfString = new byte[userInput.length()];
        for(int i = 0; i < userInput.length(); i++){
            charsOfString[i] = (byte) userInput.charAt(i);
        }
        return charsOfString;
    }
}
