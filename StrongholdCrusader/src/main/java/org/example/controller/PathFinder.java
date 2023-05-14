package org.example.controller;

import java.util.ArrayList;

public class PathFinder
{
    public static int SZ = 400;
    private int [][][] gameMap = new int[SZ][SZ][2];
    public int [][][] distance = new int [SZ][SZ][2];

    public int [] dx = new int[] {-1, 1, 0, 0, 0};
    public int [] dy = new int[] {0, 0, -1, 1, 0};
    public int [] dz = new int[] {0, 0, 0, 0, 1};
    public static boolean validPos(int x, int y)
    {
        return x >= 0 && x < SZ && y >= 0 && y < SZ;
    }

    public void setGameMap( int[][] maskedMap1 , int [][] maskedMap2, int size )
    {
        for(int i = 0 ; i < size ; i++) {
            for (int j = 0; j < size; j++) {
                gameMap[i][j][0] = maskedMap1[i][j];
                gameMap[i][j][1] = maskedMap2[i][j];
            }
        }
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
                    if(X == i && Y == j && gameMap[i][j][k] != 1)
                    {
                        distance[i][j][k] = 0;
                        Q.add(i);
                        Q.add(j);
                        Q.add(k);
                    }
                    else
                    {
                        distance[i][j][k] = SZ * SZ + 10;
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
            int curDis = distance[f][s][t] + 1;
            i += 3;
            for(int d = 0; d < 5; d ++)
            {
                int nx = f + dx[d], ny = s + dy[d], nz = t ^ dz[d];
                if(!validPos(nx,ny))continue ;
                boolean condition = (dz[d] == 1 && gameMap[nx][ny][nz] == 2) || (dz[d] == 0 && gameMap[nx][ny][nz] != 1);
                if(validPos(nx, ny) && condition && distance[nx][ny][nz] > curDis)
                {
                    distance[nx][ny][nz] = curDis;
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
        if(gameMap[x][y][0] != 1)
        {
            z = 0;
        }
        if(gameMap[x][y][1] != 1)
        {
            z = 1;
        }
        if(z!=-1 && distance[x][y][z] > distance[x][y][1-z]){
            z = 1 - z ;
        }
        if(z == -1)
        {
            return -2;
        }
        for(int d = 0; d < 5; d ++)
        {
            int nx = x + dx[d], ny = y + dy[d], nz = dz[d] ^ z;
            if(validPos(nx, ny) && distance[x][y][z] == distance[nx][ny][nz] + 1)
            {
                return d;
            }
        }
        return -1;
    }
}
