package Model;

public class ClientConnection
{
    public String userName;
    public String serverName;
    int port;
    public ClientConnection(String _userName, String _serverName, int _port)
    {
        userName = _userName;
        serverName = _serverName;
        port = _port;
    }
}
