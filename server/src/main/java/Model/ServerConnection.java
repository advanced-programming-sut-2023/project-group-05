package Model;

import java.util.ArrayList;

public class ServerConnection
{
    private final String serverName;
    private final String serverPassword;
    private final int serverPort;
    private final int maxCapacity;
    private ArrayList < ClientConnection > Clients = new ArrayList<>();
    public ServerConnection(String _serverName, String _serverPassword, int _serverPort, int _maxCapacity)
    {
        serverName = _serverName;
        serverPassword = _serverPassword;
        serverPort = _serverPort;
        maxCapacity = _maxCapacity;
    }

    public ArrayList < ClientConnection > getClients ()
    {
        return this.Clients;
    }

    public boolean AddNewClient(ClientConnection clientConnection)
    {
        if(maxCapacity <= Clients.size()) return false;
        Clients.add(clientConnection);
        return true;
    }

    public void RemoveClient(ClientConnection clientConnection)
    {
        Clients.remove(clientConnection);
    }

    public int getServerPort()
    {
        return serverPort;
    }

    public String getServerPassword()
    {
        return serverPassword;
    }

    public String getServerName()
    {
        return serverName;
    }
    public int getMaxCapacity()
    {
        return maxCapacity;
    }
}
