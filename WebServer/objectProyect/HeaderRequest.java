package objectProyect;
import java.io.*;
import java.net.*;
public class HeaderRequest {
    String getOrhead;
    String path;
    String httpVersion;
    String HOST;
    String userAgent;
    boolean connectionClosed;
    String lenguage;
    int length;


    byte[] body;

    public HeaderRequest(String getOrHead, String path, String httpVersion, String HOST, String userAgent, boolean connectionClosed, String lenguage, byte[] body) {
        this.getOrhead = getOrHead;
        this.path = path;
        this.httpVersion = httpVersion;
        this.HOST = HOST;
        this.userAgent = userAgent;
        this.connectionClosed = connectionClosed;
        this.lenguage = lenguage;
        this.body = body;
    }

    public HeaderRequest(String userAgent, boolean connectionClosed, String lenguage) {
        this.userAgent = userAgent;
        this.connectionClosed = connectionClosed;
        this.lenguage = lenguage;
    }

    public HeaderRequest() {
        this.path = "index.html";
        this.httpVersion = "HTTP/1.1";
        this.HOST = "eclipesPC";
        this.userAgent = "something";
        this.connectionClosed = true;
        this.lenguage = "en";
        // this.body = body;
    }

   public void decomlpile(String decomlpile){
    //    System.out.println("init\n" + decomlpile);
       // Split by newlines
        String[] lines = decomlpile.split("\\r?\\n");
        // REMOVE SPACES
    //    System.out.println("split");
        for(int i=1;i<lines.length;i++){
            lines[i] = lines[i].replaceAll(" ","");
            // System.out.println(lines[i]);

        }
        // GET FIRST LINE, HEAD OR GET
        // System.out.println("HEAD");
        String[] Getter = lines[0].split("\\s+");
        if (!(Getter[0].equalsIgnoreCase("GET") || Getter[0].equalsIgnoreCase("HEAD"))){
            return;
        }
        setGetOrHead(Getter[0]);
        // GET PATH
        if (Getter[1] != null && !Getter[1].isEmpty())
            setPath(Getter[1]);
        // GET VERSION
        if (Getter[2] != null && !Getter[2].isEmpty())
            setHttpVersion(Getter[2]);
        // GET CONTENT
        String contentOfHeader[][] = new String[lines.length-1][2];
        for (int i = 1; i < lines.length; i++){
            contentOfHeader[i-1] = lines[i].split(":");
        }
        for (String[] var : contentOfHeader) {
            if (var[0].equalsIgnoreCase("HOST"))
                setHOST(var[1]);
            if (var[0].equalsIgnoreCase("User-Agent"))
                setUserAgent(var[1]);
            if (var[0].equalsIgnoreCase("connection"))
                if(var[1].equalsIgnoreCase("close"))
                    setConnectionClosed(true);
            if (var[0].equalsIgnoreCase("Accept-language"))
                setLenguage(var[1]);
        }
    }

    @Override
    public String toString(){
        String headerInString = "";
        if (path.contentEquals("") || path == null)
            path = "index.html";
        if (httpVersion.contentEquals("") || httpVersion == null)
            httpVersion = "HTML/1.1";

        headerInString += "GET "  + path + " " + httpVersion + "\n";
        headerInString += "HOST: " + HOST+ "\n";
        headerInString += "User-agent: " + userAgent+ "\n";
        if (connectionClosed)
            headerInString += "Connection: close"+ "\n";
        headerInString += "Accept-language: " + lenguage+ "\n";
        // Two separates the shit
        headerInString += "\n";
        return headerInString;
    }


    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    public String getHOST() {
        return HOST;
    }

    public void setHOST(String HOST) {
        this.HOST = HOST;
    }

    public boolean isConnectionClosed() {
        return connectionClosed;
    }

    public void setConnectionClosed(boolean connectionClosed) {
        this.connectionClosed = connectionClosed;
    }

    public String getLenguage() {
        return lenguage;
    }

    public void setLenguage(String lenguage) {
        this.lenguage = lenguage;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
    public int getLength(){
        return this.length;
    }
    public void setGetOrHead(String getOrHead){
        this.getOrhead = getOrHead;
    }
    public String getGetOrHead(){
        return getOrhead;
    }
}
