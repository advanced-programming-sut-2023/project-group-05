package org.example.controller;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class MapGenerator /// 0 -> ground, 1 -> water, 2 -> grass, 3 -> rock
{
    public static Integer SZ = 400;

    public static Integer outLayer = 10;

    public ArrayList < ArrayList < Integer > > A;

    public void Change(int x1, int y1, int x2, int y2, int tp)
    {
        for(int i = x1; i < x2; i ++)
        {
            for(int j = y1; j < y2; j ++)
            {
                A.get(i).set(j, tp);
            }
        }
    }
    public MapGenerator()
    {
        int type = 0;
        A = new ArrayList <ArrayList<Integer>>();
        for(int i = 0; i < SZ; i ++)
        {
            ArrayList < Integer > cur = new ArrayList< Integer >();
            for(int j = 0; j < SZ; j ++)
            {
                cur.add((j < outLayer || SZ - j < outLayer || i < outLayer || SZ - i < outLayer)? 1 : 0);
            }
            A.add(cur);
        }
        if(type == 0)
        {
            Change(110, 215, 127, 260, 1);
            Change(110, 240, 170, 260, 1);
            Change(295, 170, 356, 201, 2);
            Change(317, 156, 330, 266, 2);

        }
        else if(type == 1)
        {

        }
        else if(type == 2)
        {

        }
    }
}
