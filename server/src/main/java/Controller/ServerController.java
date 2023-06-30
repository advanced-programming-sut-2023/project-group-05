package Controller;

import Model.ClientConnection;
import Model.ServerConnection;
import View.ServerStartGameMenu;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerController
{
    public static ServerStartGameMenu serverStartGameMenu;
    public static HashMap < String, ServerConnection > ServersMap = new HashMap<>();
    public static ArrayList <ServerConnection > Servers = new ArrayList<>();
    public static ArrayList <ClientConnection > Clients = new ArrayList<>();
    public static boolean AddServer(ServerConnection serverConnection)
    {
        for(ServerConnection now : Servers)
        {
            if(now.getServerName().equals(serverConnection.getServerName()))
            {
                return false;
            }
        }
        Servers.add(serverConnection);
        ServersMap.put(serverConnection.getServerName(), serverConnection);
        return true;
    }
    public static boolean AddClient(ClientConnection clientConnection)
    {
        Clients.add(clientConnection);
        ServersMap.get(clientConnection.serverName).AddNewClient(clientConnection);
        serverStartGameMenu.getVbox(serverStartGameMenu.vbox);
        return true;
    }
    public static void RemoveClient(ClientConnection clientConnection)
    {
        ServersMap.get(clientConnection.serverName).RemoveClient(clientConnection);
        Clients.remove(clientConnection);
    }
}
