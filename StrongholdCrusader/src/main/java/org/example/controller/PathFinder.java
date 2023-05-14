package org.example.controller;

import java.util.ArrayList;

public class PathFinder
{
    public static int SZ = 400;
    private int [][] gameMap = new int[SZ][SZ];
    public int [][] distance = new int [SZ][SZ];

    public int [] dx = new int[] {-1, 1, 0, 0};
    public int [] dy = new int[] {0, 0, -1, 1};

    public static boolean validPos(int x, int y)
    {
        return x >= 0 && x < SZ && y >= 0 && y < SZ;
    }

    public void setGameMap( int[][] maskedMap , int size ){
        for(int i = 0 ; i < size ; i++)
            for(int j = 0 ; j < size ; j++)
                gameMap[i][j] = maskedMap[i][j] ;
    }

    public void Run(Integer x, Integer y)
    {

        int n = SZ, m = SZ; /// this might change too
        int X = x, Y = y;
        for(int i = 0; i < n; i ++)
        {
            for(int j = 0; j < m; j ++)
            {
                if(i == X && j == Y)
                {
                    distance[i][j] = 0;
                }
                else
                {
                    distance[i][j] = SZ * SZ + 10;
                }
            }
        }
        ArrayList < Integer > Q = new ArrayList< Integer >();
        Q.add(x);
        Q.add(y);
        int i = 0;
        while(i < Q.size())
        {
            int f = (int) Q.get(i);
            int s = (int) Q.get(i + 1);
            int curDis = distance[f][s] + 1;
            i += 2;
            for(int d = 0; d < 4; d ++)
            {
                int nx = f + dx[d], ny = s + dy[d];
                if(validPos(nx, ny) && gameMap[nx][ny] == 0 && distance[nx][ny] > curDis)
                {
                    distance[nx][ny] = curDis;
                    Q.add(nx);
                    Q.add(ny);
                }
            }
        }
    }
    public int goInDirectionFrom(Integer x, Integer y) /// up, down, left, right (0, 1, 2, 3) and -1 if it is fucked!
    {
        for(int d = 0; d < 4; d ++)
        {
            int nx = x + dx[d], ny = y + dy[d];
            if(validPos(nx, ny) && distance[x][y] == distance[nx][ny] + 1)
            {
                return d;
            }
        }
        return -1;
    }

    public static boolean isConnected (int row1, int column1, int row2,int column2){
        return true;
    }
}
