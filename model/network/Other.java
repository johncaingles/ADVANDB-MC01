package model.network;
public class Other {

    String IPadd;
    String username;

    public Other(String IPadd, String username) {
        this.IPadd = IPadd;
        this.username = username;
    }
    
    public String getIP(){
        return IPadd;
    }

}
