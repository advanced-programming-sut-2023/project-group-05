package org.example.controller;

import java.util.ArrayList;

public class PathFinder
{ // TODO : static pathfinder
    public static int SZ = 400;
    private int [][][] gameMap = new int[2][][] ;
    public int [][][] distance = new int[2][SZ][SZ];

    public int [] dx = new int[] {-1, 1, 0, 0, 0};
    public int [] dy = new int[] {0, 0, -1, 1, 0};
    public int [] dz = new int[] {0, 0, 0, 0, 1};
    public static boolean validPos(int x, int y)
    {
        return x >= 0 && x < SZ && y >= 0 && y < SZ;
    }

    public void setGameMap( int[][] maskedMap1 , int[][] maskedMap2, int size ){
        gameMap[0] = maskedMap1 ;
        gameMap[1] = maskedMap2 ;
    }

    public void Run(Integer x, Integer y)
    {
        int n = SZ, m = SZ; /// this might change too
        int X = x, Y = y;
        ArrayList< Integer > Q = new ArrayList< Integer >();
        for(int i = 0; i < n; i ++)
        {
            for(int j = 0; j < m; j ++)
            {
                for(int k = 0; k < 2; k ++)
                {
                    if(X == i && Y == j && gameMap[k][i][j] != 1)
                    {
                        distance[k][i][j] = 0;
                        Q.add(i);
                        Q.add(j);
                        Q.add(k);
                    }
                    else
                    {
                        distance[k][i][j] = SZ * SZ + 10;
                    }
                }
            }
        }

        int i = 0;
        while(i < Q.size())
        {
            int f = (int) Q.get(i);
            int s = (int) Q.get(i + 1);
            int t = (int) Q.get(i + 2);
            int curDis = distance[t][f][s] + 1;
            i += 3;
            for(int d = 0; d < 5; d ++)
            {
                int nx = f + dx[d], ny = s + dy[d], nz = t ^ dz[d];
                if(!validPos(nx,ny))continue ;
                boolean condition = (dz[d] == 1 && gameMap[nz][nx][ny] == 2) || (dz[d] == 0 && gameMap[nz][nx][ny] != 1);
                if(validPos(nx, ny) && condition && distance[nz][nx][ny] > curDis)
                {
                    distance[nz][nx][ny] = curDis;
                    Q.add(nx);
                    Q.add(ny);
                    Q.add(nz);
                }
            }
        }

//        for(int ii = 0 ; ii < 15 ; ii++)
//            for(int j = 0 ; j < 15 ; j++)
//                System.out.print(distance[ii][j][0] + " " + ( j == 14 ? "\n" : "" ) ) ;
//
//        for(int ii = 0 ; ii < 15 ; ii++)
//            for(int j = 0 ; j < 15 ; j++)
//                System.out.print(distance[ii][j][1] + " " + ( j == 14 ? "\n" : "" ) ) ;


    }
    public int goInDirectionFrom(Integer x, Integer y) /// up, down, left, right, otherMap (0, 1, 2, 3, 4) and -1 if it is fucked!
    {
        int z = -1;
        if(gameMap[0][x][y] != 1)
        {
            z = 0;
        }
        if(gameMap[1][x][y] != 1)
        {
            z = 1;
        }
        if(z!=-1 && distance[z][x][y] > distance[1-z][x][y]){
            z = 1 - z ;
        }
        if(z == -1)
        {
            return -2;
        }
        for(int d = 0; d < 5; d ++)
        {
            int nx = x + dx[d], ny = y + dy[d], nz = dz[d] ^ z;
            if(validPos(nx, ny) && distance[z][x][y] == distance[nz][nx][ny] + 1)
            {
                return d;
            }
        }
        return -1;
    }
}
