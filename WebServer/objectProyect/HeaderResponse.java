package objectProyect;
import java.io.*;
import java.net.*;
import java.time.LocalDateTime;

public class HeaderResponse {
    private static final String[][] HttpReplies = {{"100", "Continue"},
        {"101", "Switching Protocols"},
        {"200", "OK"},
        {"201", "Created"},
        {"202", "Accepted"},
        {"203", "Non-Authoritative Information"},
        {"204", "No Content"},
        {"205", "Reset Content"},
        {"206", "Partial Content"},
        {"300", "Multiple Choices"},
        {"301", "Moved Permanently"},
        {"302", "Found"},
        {"303", "See Other"},
        {"304", "Not Modified"},
        {"305", "Use Proxy"},
        {"306", "(Unused)"},
        {"307", "Temporary Redirect"},
        {"400", "Bad Request"},
        {"401", "Unauthorized"},
        {"402", "Payment Required"},
        {"403", "Forbidden"},
        {"404", "Not Found"},
        {"405", "Method Not Allowed"},
        {"406", "Not Acceptable"},
        {"407", "Proxy Authentication Required"},
        {"408", "Request Timeout"},
        {"409", "Conflict"},
        {"410", "Gone"},
        {"411", "Length Required"},
        {"412", "Precondition Failed"},
        {"413", "Request Entity Too Large"},
        {"414", "Request-URI Too Long"},
        {"415", "Unsupported Media Type"},
        {"416", "Requested Range Not Satisfiable"},
        {"417", "Expectation Failed"},
        {"500", "Internal Server Error"},
        {"501", "Not Implemented"},
        {"502", "Bad Gateway"},
        {"503", "Service Unavailable"},
        {"504", "Gateway Timeout"},
        {"505", "HTTP Version Not Supported"}};
    String httpVersion;
    int statusCode;
    boolean success;
    boolean connectionClosed;
    String date;
    String server;
    String cast;
    String contentLength;
    String contentType;
    String lastModified;

    byte[] body;


    public HeaderResponse(String httpVersion, int statusCode, boolean success, boolean connectionClosed, String date, String server, String cast, String contentLength, String contentType) {
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
        this.statusCode = 200;
        this.connectionClosed = false;
        this.date = "" + LocalDateTime.now();
        this.server = "Eclipse's Server";
        this.lastModified = "" + LocalDateTime.now();
    }

    @Override
    public String toString(){
        String headerInString = "";
        if (httpVersion.contentEquals("") || httpVersion == null)
            httpVersion = "HTML 1.1";
        headerInString += httpVersion + " "+ this.statusCode +" OK" + "\n";
        if (connectionClosed)
            headerInString += "connection: close"+ "\n";

        headerInString += "Date: " + date + "\n";
        headerInString += "Server: Eclipse's Server \n";
        
        headerInString += "Last-Modified: " + date + "\n";
        headerInString += "Content-Length: " + this.contentLength + "\n";
        headerInString += "Content-Type: text/html \n";

        headerInString += "\n";
        if (body != null)
            for (int i = 0; i < body.length; i++){
               headerInString += body[i];
                }
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

    public String getContentLength() {
        return contentLength;
    }

    public void setContentLength(String contentLength) {
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
    }
    public byte[] getBody(){
        return body;
    }

    private byte[] stringToByte(String userInput){
        byte[] charsOfString = new byte[userInput.length()];
        for(int i = 0; i < userInput.length(); i++){
            charsOfString[i] = (byte) userInput.charAt(i);
        }
        return charsOfString;
    }
}
