package controller;

import model.ClientConnection;
import model.GameRoomConnection;
import view.GameRoomStartGameMenu;

import java.util.ArrayList;
import java.util.HashMap;

public class GameRoomController
{
    public static GameRoomStartGameMenu serverStartGameMenu;
    public static HashMap < String, GameRoomConnection> ServersMap = new HashMap<>();
    public static ArrayList <GameRoomConnection> Rooms = new ArrayList<>();
    public static ArrayList <ClientConnection > Clients = new ArrayList<>();
    public static boolean AddServer( GameRoomConnection serverConnection)
    {
        for( GameRoomConnection now : Rooms)
        {
            if(now.getServerName().equals(serverConnection.getServerName()))
            {
                return false;
            }
        }
        Rooms.add(serverConnection);
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
