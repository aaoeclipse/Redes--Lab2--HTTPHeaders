import objectProyect.HeaderRequest;

public class test{
    HeaderRequest request;
    public test(){
        request = new HeaderRequest();
    }
    public static void main(String[] args){
        String toDecompile = "GET /somedir/page.html HTTP/1.1\n"+
        "Host: www.someschool.edu\n"+
        "User-agent: Mozilla/4.0\n"+
        "Connection: close\n"+
        "Accept-language:fr\n";
        test something = new test();
        something.decomlpile(toDecompile);

    }

    public void decomlpile(String decomlpile){
        String[] lines = decomlpile.split("\\r?\\n");
        // REMOVE SPACES
        for(int i=1;i<lines.length;i++){
            lines[i] = lines[i].replaceAll(" ","");

        }
        // GET FIRST LINE, HEAD OR GET
        String[] Getter = lines[0].split("\\ ");
        if (!Getter[0].equalsIgnoreCase("GET") && !Getter[0].equalsIgnoreCase("HEAD"))
            return;
        request.setGetOrHead(Getter[0]);
        // GET PATH
        if (Getter[1] != null && !Getter[1].isEmpty())
            request.setPath(Getter[1]);
        // GET VERSION
        if (Getter[2] != null && !Getter[2].isEmpty())
            request.setHttpVersion(Getter[2]);
        // GET CONTENT
        String contentOfHeader[][] = new String[lines.length-1][2];
        for (int i = 1; i < lines.length; i++){
            contentOfHeader[i-1] = lines[i].split(":");
        }
        for (String[] var : contentOfHeader) {
            if (var[0].equalsIgnoreCase("HOST"))
                request.setHOST(var[1]);
            if (var[0].equalsIgnoreCase("User-Agent"))
                request.setUserAgent(var[1]);
            if (var[0].equalsIgnoreCase("connection"))
                if(var[1].equalsIgnoreCase("close"))
                    request.setConnectionClosed(true);
            if (var[0].equalsIgnoreCase("Accept-language"))
                request.setLenguage(var[1]);
        }

        System.out.println(request);

    }
}