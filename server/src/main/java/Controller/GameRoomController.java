package Controller;

import Model.ClientConnection;
import Model.GameRoomConnection;
import View.GameRoomStartGameMenu;

import java.util.ArrayList;
import java.util.HashMap;

public class GameRoomController
{
    public static GameRoomStartGameMenu serverStartGameMenu;
    public static HashMap < String, GameRoomConnection> ServersMap = new HashMap<>();
    public static ArrayList <GameRoomConnection> Servers = new ArrayList<>();
    public static ArrayList <ClientConnection > Clients = new ArrayList<>();
    public static boolean AddServer( GameRoomConnection serverConnection)
    {
        for( GameRoomConnection now : Servers)
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
